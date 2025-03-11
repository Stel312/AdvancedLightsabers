package com.stelmods.lightsabers.network.cts;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ForcePull {
    public ForcePull()
    {}

    public void encode(FriendlyByteBuf buffer) {
    }

    public static ForcePull decode(FriendlyByteBuf buffer) {
        return new ForcePull();
    }
    public static void handle(ForcePull message, final Supplier<NetworkEvent.Context> ctx) {
        Player senderPlayer = ctx.get().getSender();
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
        // Get all entities within the bounding box
        for (Entity entity : world.getEntities(senderPlayer, boundingBox)) {
            // Pull entity
            double distance = playerPos.distanceTo(entity.position());
            double strength = 5 / distance;
            Vec3 pullDirection = entity.position().subtract(playerPos).normalize();
            pullDirection = pullDirection.scale(-strength);
            entity.addDeltaMovement(pullDirection);
        }
    }
}

