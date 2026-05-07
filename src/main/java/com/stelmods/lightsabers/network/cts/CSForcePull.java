package com.stelmods.lightsabers.network.cts;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.network.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CSForcePull() implements Packet {

    public static final CustomPacketPayload.Type<CSForcePull> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Lightsabers.MODID, "cs_force_pull"));
    public static final StreamCodec<FriendlyByteBuf, CSForcePull> STREAM_CODEC = StreamCodec.of((pBuffer, pValue) -> {}, pBuffer -> new CSForcePull());

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

        // Pull entities — strength increases with distance (vice versa of push: farther = stronger)
        for (Entity entity : world.getEntities(senderPlayer, boundingBox)) {
            double distance = playerPos.distanceTo(entity.position());
            double strength = BASE_STRENGTH * (distance / MAX_RANGE);
            Vec3 pullDirection = entity.position().subtract(playerPos).normalize().scale(-strength);
            entity.addDeltaMovement(pullDirection);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
