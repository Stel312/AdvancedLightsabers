package com.stelmods.lightsabers.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stelmods.lightsabers.capabilities.IPlayerCapabilities;
import com.stelmods.lightsabers.capabilities.ModCapabilities;
import com.stelmods.lightsabers.network.stc.SCSyncCapabilityPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.DistExecutor;

import java.util.ArrayList;
import java.util.Random;

public class ClientUtils {
    public static DistExecutor.SafeRunnable syncCapability(SCSyncCapabilityPacket message) {
        return new DistExecutor.SafeRunnable() {
            @Override
            public void run() {
                IPlayerCapabilities playerData = ModCapabilities.getPlayer(Minecraft.getInstance().player);
                playerData.setLightningMode(message.lightningMode);
            }
        };
    }

    static Random random = new Random();

    public static void renderLightningBeam(PoseStack poseStack, MultiBufferSource bufferSource, Vec3 start, Vec3 end, int segments, float thickness) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.LINES);
        //System.out.println(poseStack+" "+bufferSource+" "+start+" "+end+" "+segments);

        poseStack.pushPose();
        {
            Vec3 direction = end.subtract(start).normalize();
            double length = start.distanceTo(end);

            Vec3 perp1 = new Vec3(-direction.z, 0, direction.x).normalize().scale(thickness);
            Vec3 perp2 = direction.cross(new Vec3(0, 1, 0)).normalize().scale(thickness);

            Vec3[] points = new Vec3[segments + 1];
            points[0] = start;
            points[segments] = end;

            for (int i = 1; i < segments; i++) {
                double t = (double) i / segments;

                double rand1 = (random.nextDouble() - 0.5) * 2;
                double rand2 = (random.nextDouble() - 0.5) * 2;
                double randY = (random.nextDouble() - 0.5) * thickness * 2;

                Vec3 offset = perp1.scale(rand1).add(perp2.scale(rand2)).add(0, randY, 0); // Offset ahora también afecta Y
                points[i] = start.add(direction.scale(t * length)).add(offset);
            }

            for (int i = 0; i < segments - 1; i++) {
                Vec3 p1 = points[i];
                Vec3 p2 = points[i + 1];

                vertexConsumer.vertex(poseStack.last().pose(), (float) p1.x, (float) p1.y, (float) p1.z)
                        .color(100, 150, 255, 255) // Azul eléctrico
                        .normal(poseStack.last().normal(), 0, 1, 0)
                        .endVertex();

                vertexConsumer.vertex(poseStack.last().pose(), (float) p2.x, (float) p2.y, (float) p2.z)
                        .color(100, 150, 255, 255) // Azul eléctrico
                        .normal(poseStack.last().normal(), 0, 1, 0)
                        .endVertex();
            }
        }
        poseStack.popPose();
    }


    public static DistExecutor.SafeRunnable setLightningMap(int id, int targetsSize, ArrayList<Integer> targets) {
        return new DistExecutor.SafeRunnable() {
            @Override
            public void run() {
                ClientEvents.lightningMap.put(id, targets);
            }
        };
    }
}
