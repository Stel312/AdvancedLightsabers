package com.stelmods.lightsabers.network.cts;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.capabilities.PlayerCapabilities;
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

public record CSForcePush() implements Packet {
    public static final CustomPacketPayload.Type<CSForcePush> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Lightsabers.MODID, "cs_force_push"));
    public static final StreamCodec<FriendlyByteBuf, CSForcePush> STREAM_CODEC = StreamCodec.of((pBuffer, pValue) -> {}, pBuffer -> new CSForcePush());

    @Override
    public void handle(IPayloadContext context) {
        Player senderPlayer = context.player();
        Vec3 lookVec = senderPlayer.getLookAngle();// Get the direction the player is looking
        Level world = senderPlayer.level();
        // Calculate the bounding box coordinates based on the player's look vector
        Vec3 playerPos = senderPlayer.position();
        playerPos = playerPos.add(0, 1, 0);
        Vec3 boxStart = playerPos.add(lookVec.scale(1)); // Start the box a bit in front of the player
        Vec3 boxEnd = playerPos.add(lookVec.scale(10));

        AABB boundingBox = new AABB(
                Math.min(boxStart.x, boxEnd.x), Math.min(boxStart.y, boxEnd.y), Math.min(boxStart.z, boxEnd.z),
                Math.max(boxStart.x, boxEnd.x), Math.max(boxStart.y, boxEnd.y), Math.max(boxStart.z, boxEnd.z)
        );

        PlayerCapabilities playerData = PlayerCapabilities.get(senderPlayer);

        for (Entity entity : world.getEntities(senderPlayer, boundingBox)) {
            if(playerData.getGrabbedID() == entity.getId()){
                playerData.setGrabbedID(-1);
            }

            double distance = playerPos.distanceTo(entity.position());
            //double strength = 5 / distance;
            double strength = 3;
            Vec3 pushDirection = entity.position().subtract(playerPos).normalize();
            pushDirection = pushDirection.scale(strength);
            entity.addDeltaMovement(pushDirection);


;        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
