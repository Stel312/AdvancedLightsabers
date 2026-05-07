package com.stelmods.lightsabers.network.cts;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.capabilities.PlayerCapabilities;
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
                playerData.setGrabbedBlockStateNBT(NbtUtils.writeBlockState(blockState));

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
                blockDisplay.setBlockState(blockState);
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
