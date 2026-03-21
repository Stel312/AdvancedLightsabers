package com.stelmods.lightsabers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.stelmods.lightsabers.common.lightsaber.FocusingCrystal;
import net.minecraft.client.Minecraft;

import java.util.Random;

public class ModelLightsaberBlade {

    private ModelLightsaberBlade() {}

    // ============================================================
    // INNER BLADE — pure white emissive, thinner, longer
    // ============================================================
    public static void renderInner(
            float[] rgb,
            VertexConsumer vc,
            boolean isCrossguard,
            PoseStack pose,
            int light,
            FocusingCrystal c1,
            FocusingCrystal c2,
            float length
    ) {
        pose.pushPose();

        // Thinner inner core
        float radius = 0.045f;

        // Slight length extension (OT style)
        float finalLength = length * 1.05f;

        // Pure white emissive inner core
        float[] white = new float[]{1f, 1f, 1f};

        drawCylinder(vc, pose, white, radius, finalLength, 1f, 0xF000F0);

        pose.popPose();
    }

    // ============================================================
    // OUTER BLADE — RGB bloom, thinner, ANH micro‑jitter
    // ============================================================
    public static void renderOuter(
            float[] rgb,
            VertexConsumer vc,
            boolean isCrossguard,
            PoseStack pose,
            int light,
            FocusingCrystal c1,
            FocusingCrystal c2,
            float length
    ) {
        pose.pushPose();

        // Thinner outer glow
        float baseRadius = 0.09f;

        // Slight length extension
        float finalLength = length * 1.05f;

        // Smooth partial ticks (1.21+)
        float partial = Minecraft.getInstance().getTimer().getGameTimeDeltaTicks();
        float t = Minecraft.getInstance().level.getGameTime() + partial;

        // ANH micro‑jitter: random, subtle, fast, non‑rhythmic
        Random flickerRand = new Random((long)(t * 1000));

        for (int i = 0; i < 4; i++) {
            float jitter = 0.97f + flickerRand.nextFloat() * 0.06f; // ±3%
            float radius = baseRadius * (1f + i * 0.12f) * jitter;
            float alpha = 0.30f / (i + 1);

            pose.pushPose();
            drawCylinder(vc, pose, rgb, radius, finalLength, alpha, 0xF000F0);
            pose.popPose();
        }

        pose.popPose();
    }

    // ============================================================
    // CRACKED BLADE LIGHTNING — unchanged
    // ============================================================
    public static void renderCrackedLightning(
            float[] rgb,
            VertexConsumer vc,
            PoseStack pose,
            float bladeLength,
            FocusingCrystal c1,
            FocusingCrystal c2
    ) {
        if (c1 != FocusingCrystal.CRACKED && c2 != FocusingCrystal.CRACKED)
            return;

        Random rand = new Random(Minecraft.getInstance().level.getGameTime());
        int shardCount = 3 + rand.nextInt(2);

        for (int i = 0; i < shardCount; i++) {
            pose.pushPose();

            float y = rand.nextFloat() * bladeLength;
            float shardLen = 0.15f + rand.nextFloat() * 0.30f;
            float radius = rand.nextBoolean() ? 0.02f : 0.05f;
            float alpha = 0.6f + rand.nextFloat() * 0.3f;

            pose.mulPose(Axis.YP.rotationDegrees(rand.nextFloat() * 360f));
            pose.mulPose(Axis.XP.rotationDegrees(rand.nextFloat() * 360f));
            pose.translate(0, y, 0);

            drawCylinder(vc, pose, rgb, radius, shardLen, alpha, 0xF000F0);

            pose.popPose();
        }
    }

    // ============================================================
    // CYLINDER DRAWING — 8‑sided prism
    // ============================================================
    private static void drawCylinder(
            VertexConsumer vc,
            PoseStack pose,
            float[] rgb,
            float radius,
            float height,
            float alpha,
            int light
    ) {
        int segments = 8;

        for (int i = 0; i < segments; i++) {
            float a1 = (float)(2 * Math.PI * i / segments);
            float a2 = (float)(2 * Math.PI * (i + 1) / segments);

            float x1 = radius * (float)Math.cos(a1);
            float z1 = radius * (float)Math.sin(a1);
            float x2 = radius * (float)Math.cos(a2);
            float z2 = radius * (float)Math.sin(a2);

            float nx = (float)Math.cos((a1 + a2) * 0.5f);
            float nz = (float)Math.sin((a1 + a2) * 0.5f);

            cylinderFace(vc, pose, rgb, x1, 0f, z1, x2, height, z2, nx, nz, alpha, light);
        }
    }

    // ============================================================
    // CYLINDER FACE (quad)
    // ============================================================
    private static void cylinderFace(
            VertexConsumer vc,
            PoseStack pose,
            float[] rgb,
            float x1, float y1, float z1,
            float x2, float y2, float z2,
            float nx, float nz,
            float alpha,
            int light
    ) {
        var m = pose.last().pose();
        float ny = 0f;

        int u1 = light & 0xFFFF;
        int v1 = (light >> 16) & 0xFFFF;

        int u2 = 0;
        int v2 = 10; // OverlayTexture.NO_OVERLAY

        vc.addVertex(m, x1, y1, z1)
                .setColor(rgb[0], rgb[1], rgb[2], alpha)
                .setUv(0f, 0f)
                .setUv1(u1, v1)
                .setUv2(u2, v2)
                .setNormal(nx, ny, nz);

        vc.addVertex(m, x2, y1, z2)
                .setColor(rgb[0], rgb[1], rgb[2], alpha)
                .setUv(1f, 0f)
                .setUv1(u1, v1)
                .setUv2(u2, v2)
                .setNormal(nx, ny, nz);

        vc.addVertex(m, x2, y2, z2)
                .setColor(rgb[0], rgb[1], rgb[2], alpha)
                .setUv(1f, 1f)
                .setUv1(u1, v1)
                .setUv2(u2, v2)
                .setNormal(nx, ny, nz);

        vc.addVertex(m, x1, y2, z1)
                .setColor(rgb[0], rgb[1], rgb[2], alpha)
                .setUv(0f, 1f)
                .setUv1(u1, v1)
                .setUv2(u2, v2)
                .setNormal(nx, ny, nz);
    }
}
