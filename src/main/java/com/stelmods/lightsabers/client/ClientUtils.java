package com.stelmods.lightsabers.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.capabilities.PlayerCapabilities;
import com.stelmods.lightsabers.network.stc.SCSyncCapabilityPacket;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.List;
import java.util.Random;

public class ClientUtils {
    public static final RenderType LOCK_ON_INDICATOR = RenderType.create(Lightsabers.MODID + ":force_sense_indicator", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false,
            RenderType.CompositeState.builder().setShaderState(RenderStateShard.POSITION_TEX_SHADER).setTextureState(new RenderStateShard.TextureStateShard(ResourceLocation.fromNamespaceAndPath(Lightsabers.MODID, "textures/gui/force_sense.png"),
                            false, false)).setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY).setDepthTestState(RenderStateShard.NO_DEPTH_TEST).setWriteMaskState(RenderStateShard.COLOR_WRITE).setLightmapState(RenderStateShard.NO_LIGHTMAP)
                    .setOverlayState(RenderStateShard.NO_OVERLAY).createCompositeState(true));

    public static void syncCapability(SCSyncCapabilityPacket message) {
        PlayerCapabilities.get(message.data(), (Player) Minecraft.getInstance().level.getEntity(message.player()));
    }
    static Random random = new Random();

    public static void drawLockOnIndicator(Vec3 pos, PoseStack poseStack, MultiBufferSource buffer, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();

       /* double x = Mth.lerp(partialTicks, target.xOld, target.getX());
        double y = Mth.lerp(partialTicks, target.yOld, target.getY());
        double z = Mth.lerp(partialTicks, target.zOld, target.getZ());*/

        double x = pos.x;
        double y = pos.y;
        double z = pos.z;

        Camera camera = mc.gameRenderer.getMainCamera();
        Vec3 camPos = camera.getPosition();

        poseStack.pushPose();
        {
            poseStack.translate(x - camPos.x, y - camPos.y, z - camPos.z);
            poseStack.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());

            float size = 0.5f;
            Matrix4f mat = poseStack.last().pose();
            ClientUtils.drawTexturedModalRect2DPlane(mat, buffer.getBuffer(LOCK_ON_INDICATOR), -size, -size, size, size, 0, 0, 256, 256);
        }
        poseStack.popPose();
    }

    public static void drawTexturedModalRect2DPlane(Matrix4f matrix, VertexConsumer vertexBuilder, float minX, float minY, float maxX, float maxY, float minTexU, float minTexV, float maxTexU, float maxTexV) {
        RenderSystem.depthMask(false);
        drawTexturedModalRect3DPlane(matrix, vertexBuilder, minX, minY, 0, maxX, maxY, 0, minTexU, minTexV, maxTexU, maxTexV);
        RenderSystem.depthMask(true);
    }

    public static void drawTexturedModalRect3DPlane(Matrix4f matrix, VertexConsumer vertexBuilder, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float minTexU, float minTexV, float maxTexU, float maxTexV) {
        float cor = 0.00390625F;
        vertexBuilder.addVertex(matrix, minX, minY, maxZ).setUv((minTexU * cor), (maxTexV) * cor);
        vertexBuilder.addVertex(matrix, maxX, minY, maxZ).setUv((maxTexU * cor), (maxTexV) * cor);
        vertexBuilder.addVertex(matrix, maxX, maxY, minZ).setUv((maxTexU * cor), (minTexV) * cor);
        vertexBuilder.addVertex(matrix, minX, maxY, minZ).setUv((minTexU * cor), (minTexV) * cor);
    }

    public static void renderLightningBeam(PoseStack poseStack, MultiBufferSource bufferSource, Vec3 start, Vec3 end, int segments, float thickness) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.LINES);
        //System.out.println(poseStack+" "+bufferSource+" "+start+" "+end+" "+segments);


            Vec3 direction = end.subtract(start).normalize();
            double length = start.distanceTo(end);

            Vec3 perp1 = new Vec3(-direction.z, 0, direction.x).normalize().scale(thickness);
            Vec3 perp2 = direction.cross(new Vec3(0, 1, 0)).normalize().scale(thickness);

            Vec3[] points = new Vec3[segments + 1];
            points[0] = start;
            points[segments] = end;

            for (int i = 1; i < segments; i++) {
                double t = (double) i / segments;

                double rand1 = (random.nextDouble() - 0.5) * 4;
                double rand2 = (random.nextDouble() - 0.5) * 4;
                double randY = (random.nextDouble() - 0.5) * thickness * 2;

                Vec3 offset = perp1.scale(rand1).add(perp2.scale(rand2)).add(0, randY, 0); // Offset ahora también afecta Y
                points[i] = start.add(direction.scale(t * length)).add(offset);
            }
        poseStack.pushPose();
        {
            for (int i = 0; i < segments - 1; i++) {
                Vec3 p1 = points[i];
                Vec3 p2 = points[i + 1];

                vertexConsumer.addVertex(poseStack.last().pose(), (float) p1.x, (float) p1.y, (float) p1.z)
                        .setColor(100, 150, 255, 255) // Azul eléctrico
                        .setNormal(poseStack.last(), 0, 1, 0);

                vertexConsumer.addVertex(poseStack.last().pose(), (float) p2.x, (float) p2.y, (float) p2.z)
                        .setColor(100, 150, 255, 255) // Azul eléctrico
                        .setNormal(poseStack.last(), 0, 1, 0);
            }
        }
        poseStack.popPose();
    }

    /*
    public static DistExecutor.SafeRunnable setLightningMap(int id, int targetsSize, ArrayList<Integer> targets) {
        return new DistExecutor.SafeRunnable() {
            @Override
            public void run() {
                ClientEvents.lightningMap.put(id, targets);
            }
        };
    }*/
    public static void setLightningMap(int id, List<Integer> targets) {
        Minecraft.getInstance().execute(() -> {
            ClientEvents.lightningMap.put(id, targets);
        });
    }
}
