package com.stelmods.lightsabers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.stelmods.lightsabers.common.lightsaber.FocusingCrystal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.joml.Matrix4f;

import java.util.Random;

public class ModelLightsaberBlade {

    private ModelLightsaberBlade() {}

    private static void v(VertexConsumer vc, Matrix4f mat,
                          float x, float y, float z,
                          float r, float g, float b, float a) {

        vc.addVertex(mat, x, y, z)
                .setColor(r, g, b, a)
                .setUv(0f, 0f)
                .setUv1(0, 0)
                .setUv2(0, 0)
                .setNormal(0f, 1f, 0f)
                .setLight(0xF000F0);
    }

    public static void renderInner(float[] rgb, VertexConsumer vc, PoseStack pose, float length, Random flickerRand) {

        float jitter = 0.94f + flickerRand.nextFloat() * 0.06f;
        float radius = 0.01f * jitter;

        float alpha = 0.92f + flickerRand.nextFloat() * 0.08f;

        drawCylinder(vc, pose, rgb, radius, length * 1.05f, alpha);
    }

    public static void renderOuter(float[] rgb, VertexConsumer vc, PoseStack pose, float length, Random flickerRand) {

        float baseRadius = 0.02f;

        for (int i = 0; i < 4; i++) {
            float jitter = 0.96f + flickerRand.nextFloat() * 0.08f;
            float radius = baseRadius * (1f + i * 0.2f) * jitter;

            radius *= (0.98f + flickerRand.nextFloat() * 0.04f);

            float alpha = 0.2f / (i + 1);
            drawCylinder(vc, pose, rgb, radius, length, alpha);
        }
    }

    public static void renderGuiBloom(float[] rgb, VertexConsumer vc, PoseStack pose, float length) {
        //float[] radii  = { 0.035f, 0.055f, 0.080f, 0.110f, 0.150f, 0.200f }; // 1
        //float[] radii = { 0.0117f, 0.0183f, 0.0267f, 0.0367f, 0.0500f, 0.0667f }; // 1/3
        //float[] radii = { 0.00875f, 0.01375f, 0.02000f, 0.02750f, 0.03750f, 0.05000f }; // 1/4
        float[] radii = { 0.007f, 0.011f, 0.016f, 0.022f, 0.030f, 0.040f }; // 1/5
        //float[] radii = { 0.0035f, 0.0055f, 0.0080f, 0.0110f, 0.0150f, 0.0200f }; // 1/10
        //float[] alphas = { 0.22f,  0.14f,  0.08f,  0.05f,  0.03f,  0.015f };// 1
        //float[] alphas = { 0.044f, 0.028f, 0.016f, 0.010f, 0.006f, 0.003f }; // 1/5 linear
        float[] alphas = { 0.044f, 0.020f, 0.009f, 0.004f, 0.002f, 0.001f }; // 1/5 sharper falloff

        for (int i = 0; i < radii.length; i++) {
            drawCylinder(vc, pose, rgb, radii[i], length, alphas[i]);
        }
    }

    public static void renderCrackedLightning(float[] rgb, VertexConsumer vc, PoseStack pose,
                                              float bladeLength, FocusingCrystal c1, FocusingCrystal c2) {

        if (c1 != FocusingCrystal.CRACKED && c2 != FocusingCrystal.CRACKED) return;

        Random rand = new Random(Minecraft.getInstance().level.getGameTime());

        for (int i = 0; i < 6; i++) {
            pose.pushPose();
            pose.translate(0, rand.nextFloat() * bladeLength, 0);
            pose.mulPose(Axis.YP.rotationDegrees(rand.nextFloat() * 360f));
            pose.mulPose(Axis.ZP.rotationDegrees(rand.nextFloat() * 15f));

            drawCylinder(vc, pose, rgb, 0.015f, 0.25f, 0.8f);

            pose.popPose();
        }
    }

    private static void drawCylinder(VertexConsumer vc, PoseStack pose,
                                     float[] rgb, float radius, float height, float alpha) {

        int segments = 8;
        Matrix4f mat = pose.last().pose();

        for (int i = 0; i < segments; i++) {

            float a1 = (float)(2 * Math.PI * i / segments);
            float a2 = (float)(2 * Math.PI * (i + 1) / segments);

            float x1 = radius * (float)Math.cos(a1);
            float z1 = radius * (float)Math.sin(a1);
            float x2 = radius * (float)Math.cos(a2);
            float z2 = radius * (float)Math.sin(a2);

            v(vc, mat, x1, 0f, z1, rgb[0], rgb[1], rgb[2], alpha);
            v(vc, mat, x1, height, z1, rgb[0], rgb[1], rgb[2], alpha);
            v(vc, mat, x2, height, z2, rgb[0], rgb[1], rgb[2], alpha);
            v(vc, mat, x2, 0f, z2, rgb[0], rgb[1], rgb[2], alpha);
        }

        drawTip(vc, pose, segments, rgb, radius, height, alpha);
    }

    private static void drawTip(VertexConsumer vc, PoseStack pose,
                                int segments, float[] rgb, float radius, float height, float alpha) {

        Matrix4f mat = pose.last().pose();
        float tipHeight = height + 0.02f;

        for (int i = 0; i < segments; i++) {

            float a1 = (float)(2 * Math.PI * i / segments);
            float a2 = (float)(2 * Math.PI * (i + 1) / segments);

            float x1 = radius * (float)Math.cos(a1);
            float z1 = radius * (float)Math.sin(a1);
            float x2 = radius * (float)Math.cos(a2);
            float z2 = radius * (float)Math.sin(a2);

            v(vc, mat, x2, height, z2, rgb[0], rgb[1], rgb[2], alpha);
            v(vc, mat, x1, height, z1, rgb[0], rgb[1], rgb[2], alpha);
            v(vc, mat, 0f, tipHeight, 0f, rgb[0], rgb[1], rgb[2], alpha);
            v(vc, mat, 0f, tipHeight, 0f, rgb[0], rgb[1], rgb[2], alpha);
        }
    }
}
