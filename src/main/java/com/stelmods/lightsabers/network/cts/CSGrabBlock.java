package com.stelmods.lightsabers.network.cts;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.capabilities.PlayerCapabilities;
import com.stelmods.lightsabers.common.block.BlockCrystal;
import com.stelmods.lightsabers.common.block.ModBlocks;
import com.stelmods.lightsabers.common.lightsaber.CrystalColor;
import com.stelmods.lightsabers.network.Packet;
import com.stelmods.lightsabers.network.PacketHandler; // Import PacketHandler for syncing
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack; // Import ItemStack for dropping
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks; // Import Blocks
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.core.registries.BuiltInRegistries; // Import BuiltInRegistries
import net.minecraft.core.HolderGetter; // Import HolderGetter

import javax.annotation.Nullable;

public record CSGrabBlock(@Nullable BlockPos blockPos) implements Packet {
    public static final CustomPacketPayload.Type<CSGrabBlock> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Lightsabers.MODID, "cs_grab_block"));
    public static final StreamCodec<FriendlyByteBuf, CSGrabBlock> STREAM_CODEC = new StreamCodec<FriendlyByteBuf, CSGrabBlock>() {
        @Override
        public CSGrabBlock decode(FriendlyByteBuf pBuffer) {
            return new CSGrabBlock(pBuffer.readBoolean() ? pBuffer.readBlockPos() : null);
        }

        @Override
        public void encode(FriendlyByteBuf pBuffer, CSGrabBlock pValue) {
            pBuffer.writeBoolean(pValue.blockPos != null);
            if (pValue.blockPos != null) {
                pBuffer.writeBlockPos(pValue.blockPos);
            }
        }
    };

    @Override
    public void handle(IPayloadContext context) {
        Player senderPlayer = context.player();
        if (senderPlayer == null || senderPlayer.level().isClientSide) {
            return;
        }

        ServerLevel level = (ServerLevel) senderPlayer.level();
        PlayerCapabilities playerData = PlayerCapabilities.get(senderPlayer);

        if (playerData == null) {
            Lightsabers.LOGGER.warn("PlayerCapabilities not found for player {}", senderPlayer.getName().getString());
            return;
        }

        if (blockPos != null) { // Attempt to grab a block
            // Release any existing grab first (entity or block)
            if (playerData.getGrabbedID() != -1) {
                // Logic to release entity grab (already handled by CSGrabEntity(-1) in InputHandler)
                playerData.setGrabbedID(-1);
            }
            if (playerData.getGrabbedBlockPos() != null) {
                // Release existing block grab
                releaseGrabbedBlock(level, senderPlayer, playerData);
            }

            BlockState blockState = level.getBlockState(blockPos);
            if (!blockState.isAir() && blockState.getDestroySpeed(level, blockPos) >= 0) { // Check if not air and breakable
                // Store block data in capabilities
                playerData.setGrabbedBlockPos(blockPos);
                playerData.setGrabbedBlockId(BuiltInRegistries.BLOCK.getKey(blockState.getBlock()));
                
                // Apply crystal bleed mechanic if grabbing a crystal
                BlockState displayBlockState = blockState;
                if (blockState.getBlock() instanceof BlockCrystal crystal) {
                    displayBlockState = applyCrystalBleed(crystal, blockState, playerData);
                }
                
                playerData.setGrabbedBlockStateNBT(NbtUtils.writeBlockState(displayBlockState));

                // Remove the block from the world
                level.removeBlock(blockPos, false);

                // Spawn a held BlockDisplay: smooth visual control with no falling physics.
                Vec3 eyePos = senderPlayer.getEyePosition();
                Vec3 look = senderPlayer.getLookAngle();
                Vec3 holdPos = eyePos.add(look.scale(4.0));
                HitResult holdHit = level.clip(new ClipContext(eyePos, holdPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, senderPlayer));
                if (holdHit.getType() == HitResult.Type.BLOCK) {
                    holdPos = holdHit.getLocation().subtract(look.scale(1.2)).add(0.0, 0.55, 0.0);
                }
                Display.BlockDisplay blockDisplay = new Display.BlockDisplay(EntityType.BLOCK_DISPLAY, level);
                blockDisplay.setPos(holdPos.x - 0.5, holdPos.y - 0.5, holdPos.z - 0.5);
                blockDisplay.setBlockState(displayBlockState);
                blockDisplay.setNoGravity(true);
                level.addFreshEntity(blockDisplay);

                playerData.setGrabbedBlockEntityId(blockDisplay.getId());
                System.out.println("Server: Player " + senderPlayer.getName().getString() + " grabbed block " + blockState.getBlock().getName().getString() + " at " + blockPos + ". Entity ID: " + blockDisplay.getId());
            } else {
                System.out.println("Server: Cannot grab block at " + blockPos.toString() + " (air or unbreakable).");
                // If cannot grab, ensure capabilities are clear
                playerData.setGrabbedBlockPos(null);
                playerData.setGrabbedBlockId(null);
                playerData.setGrabbedBlockStateNBT(null);
                playerData.setGrabbedBlockEntityId(-1);
            }
        } else { // Release grab signal (blockPos is null)
            releaseGrabbedBlock(level, senderPlayer, playerData);
        }

        // Synchronize capabilities to client
        PacketHandler.syncToAllAround(senderPlayer, playerData);
    }

    /**
     * Applies crystal bleed mechanic when grabbing a crystal:
     * - Dark alignment (-50 or lower) converts non-red crystals to red
     * - Light alignment (+50 or higher) converts red crystals to white
     */
    private BlockState applyCrystalBleed(BlockCrystal crystal, BlockState originalState, PlayerCapabilities playerData) {
        CrystalColor currentColor = crystal.getCrystalColor();
        int alignment = playerData.getAlignment();
        CrystalColor newColor = currentColor;
        
        // Dark side bleed: convert to red if alignment is dark enough
        if (alignment <= -50 && currentColor != CrystalColor.RED) {
            newColor = CrystalColor.RED;
            Lightsabers.LOGGER.info("Crystal bled to RED due to dark alignment ({})", alignment);
        }
        // Light side bleed: convert to white if alignment is light enough and crystal is red
        else if (alignment >= 50 && currentColor == CrystalColor.RED) {
            newColor = CrystalColor.WHITE;
            Lightsabers.LOGGER.info("Crystal purified to WHITE due to light alignment ({})", alignment);
        }
        
        // If color changed, get the new crystal block variant
        if (newColor != currentColor) {
            BlockState newBlockState = getRedeemedCrystalBlockState(newColor, originalState);
            if (newBlockState != null) {
                return newBlockState;
            }
        }
        
        return originalState;
    }

    /**
     * Gets the BlockState for the redeemed/bled crystal color
     */
    @Nullable
    private BlockState getRedeemedCrystalBlockState(CrystalColor color, BlockState originalState) {
        return switch (color) {
            case RED -> ModBlocks.redCrystal.get().defaultBlockState().setValue(BlockCrystal.FACING, originalState.getValue(BlockCrystal.FACING));
            case WHITE -> ModBlocks.whiteCrystal.get().defaultBlockState().setValue(BlockCrystal.FACING, originalState.getValue(BlockCrystal.FACING));
            default -> null;
        };
    }

    private void releaseGrabbedBlock(ServerLevel level, Player player, PlayerCapabilities playerData) {
        BlockPos oldBlockPos = playerData.getGrabbedBlockPos();
        ResourceLocation oldBlockId = playerData.getGrabbedBlockId();
        CompoundTag oldBlockStateNBT = playerData.getGrabbedBlockStateNBT();
        int oldBlockEntityId = playerData.getGrabbedBlockEntityId();

        if (oldBlockPos != null && oldBlockId != null && oldBlockStateNBT != null && oldBlockEntityId != -1) {
            Block block = BuiltInRegistries.BLOCK.get(oldBlockId);
            if (block != Blocks.AIR) {
                HolderGetter<Block> blockGetter = level.registryAccess().lookupOrThrow(Registries.BLOCK);
                BlockState oldBlockState = NbtUtils.readBlockState(blockGetter, oldBlockStateNBT);

                // Remove the temporary entity and get its current position for placement
                Entity grabbedEntity = level.getEntity(oldBlockEntityId);
                BlockPos placePos;
                if (grabbedEntity != null) {
                    // BlockDisplay uses corner position; place by center.
                    placePos = BlockPos.containing(grabbedEntity.position().add(0.5, 0.5, 0.5));
                    grabbedEntity.discard();
                } else {
                    // Fallback: place near the player
                    placePos = player.blockPosition().above();
                }

                // Try to place the block at the entity's position; drop as item if blocked
                if (level.getBlockState(placePos).canBeReplaced()) {
                    level.setBlock(placePos, oldBlockState, 3);
                    System.out.println("Server: Player " + player.getName().getString() + " released block " + oldBlockId.getPath() + " and placed it at " + placePos);
                } else {
                    Block.popResource(level, player.blockPosition(), new ItemStack(block));
                    System.out.println("Server: Player " + player.getName().getString() + " released block " + oldBlockId.getPath() + " and dropped it as item.");
                }
            }
        }

        // Clear capabilities
        playerData.setGrabbedBlockPos(null);
        playerData.setGrabbedBlockId(null);
        playerData.setGrabbedBlockStateNBT(null);
        playerData.setGrabbedBlockEntityId(-1);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
