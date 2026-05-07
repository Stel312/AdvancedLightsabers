package com.stelmods.lightsabers.network.cts;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.capabilities.PlayerCapabilities;
import com.stelmods.lightsabers.network.Packet;
import com.stelmods.lightsabers.network.PacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CSForcePush() implements Packet {
    public static final CustomPacketPayload.Type<CSForcePush> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Lightsabers.MODID, "cs_force_push"));
    public static final StreamCodec<FriendlyByteBuf, CSForcePush> STREAM_CODEC = StreamCodec.of((pBuffer, pValue) -> {}, pBuffer -> new CSForcePush());

    private static final double MAX_RANGE    = 10.0;
    private static final double BASE_STRENGTH = 3.0;

    @Override
    public void handle(IPayloadContext context) {
        Player senderPlayer = context.player();
        Vec3 lookVec   = senderPlayer.getLookAngle();
        Level world    = senderPlayer.level();
        Vec3 playerPos = senderPlayer.position().add(0, 1, 0);

        Vec3 boxStart = playerPos.add(lookVec.scale(1));
        Vec3 boxEnd   = playerPos.add(lookVec.scale(MAX_RANGE));
        AABB boundingBox = new AABB(
                Math.min(boxStart.x, boxEnd.x), Math.min(boxStart.y, boxEnd.y), Math.min(boxStart.z, boxEnd.z),
                Math.max(boxStart.x, boxEnd.x), Math.max(boxStart.y, boxEnd.y), Math.max(boxStart.z, boxEnd.z)
        ).inflate(1.5);

        PlayerCapabilities playerData = PlayerCapabilities.get(senderPlayer);

        // Push entities — strength falls off linearly with distance (farther = weaker)
        for (Entity entity : world.getEntities(senderPlayer, boundingBox)) {
            if (playerData.getGrabbedID() == entity.getId()) {
                playerData.setGrabbedID(-1);
            }
            double distance = playerPos.distanceTo(entity.position());
            double strength = BASE_STRENGTH * Math.max(0, 1.0 - distance / MAX_RANGE);
            Vec3 pushDirection = entity.position().subtract(playerPos).normalize().scale(strength);
            entity.addDeltaMovement(pushDirection);
        }

        // Fling the grabbed block (if any)
        int grabbedBlockEntityId = playerData.getGrabbedBlockEntityId();
        if (grabbedBlockEntityId != -1) {
            Entity grabbedEntity = world.getEntity(grabbedBlockEntityId);
            if (grabbedEntity instanceof Display.BlockDisplay heldDisplay) {
                CompoundTag stateNBT = playerData.getGrabbedBlockStateNBT();
                if (stateNBT != null) {
                    HolderGetter<Block> blockGetter = world.registryAccess().lookupOrThrow(Registries.BLOCK);
                    BlockState blockState = NbtUtils.readBlockState(blockGetter, stateNBT);

                    Vec3 centerPos = heldDisplay.position().add(0.5, 0.5, 0.5);
                    heldDisplay.discard();

                    FallingBlockEntity flung = FallingBlockEntity.fall(world, BlockPos.containing(centerPos), blockState);
                    flung.setPos(centerPos.x, centerPos.y, centerPos.z);
                    flung.setNoGravity(false);
                    flung.dropItem = false;
                    flung.noPhysics = false;
                    flung.setDeltaMovement(lookVec.normalize().scale(2.2).add(0.0, 0.2, 0.0));
                    flung.hasImpulse = true;
                    flung.hurtMarked = true;
                    flung.getPersistentData().putBoolean("lightsabers_force_pushed_block", true);
                }
            }

            // Clear grabbed block capabilities
            playerData.setGrabbedBlockEntityId(-1);
            playerData.setGrabbedBlockPos(null);
            playerData.setGrabbedBlockId(null);
            playerData.setGrabbedBlockStateNBT(null);
            PacketHandler.syncToAllAround(senderPlayer, playerData);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
