package com.stelmods.lightsabers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.stelmods.lightsabers.common.lightsaber.FocusingCrystal;
import net.minecraft.client.Minecraft;

import java.util.Random;

public class ModelLightsaberBlade {

    private ModelLightsaberBlade() {}

    // ------------------------------------------------------------
    // INNER BLADE (solid core)
    // ------------------------------------------------------------
    public static void renderInner(
            float[] rgb,
            VertexConsumer vc,
            boolean isCrossguard,
            PoseStack pose,
            int light,
            FocusingCrystal c1,
            FocusingCrystal c2
    ) {
        pose.pushPose();

        float width = 0.06f;
        float length = 1.0f;

        if (c1 == FocusingCrystal.COMPRESSED || c2 == FocusingCrystal.COMPRESSED)
            width *= 0.7f;

        if (c1 == FocusingCrystal.FINE_CUT || c2 == FocusingCrystal.FINE_CUT)
            pose.scale(1f, 1.2f, 1f);

        drawCube(vc, pose, rgb, width, length, light);

        pose.popPose();
    }

    // ------------------------------------------------------------
    // OUTER BLADE (bloom glow)
    // ------------------------------------------------------------
    public static void renderOuter(
            float[] rgb,
            VertexConsumer vc,
            boolean isCrossguard,
            PoseStack pose,
            int light,
            FocusingCrystal c1,
            FocusingCrystal c2,
            float progress
    ) {
        pose.pushPose();

        float baseWidth = 0.12f;
        float length = progress;

        if (c1 == FocusingCrystal.COMPRESSED || c2 == FocusingCrystal.COMPRESSED)
            baseWidth *= 0.8f;

        if (c1 == FocusingCrystal.FINE_CUT || c2 == FocusingCrystal.FINE_CUT)
            pose.scale(0.9f, 1.1f, 0.9f);

        float flicker = 0.97f + (Minecraft.getInstance().level.getGameTime() % 3) * 0.015f;

        for (int i = 0; i < 4; i++) {
            float scale = 1f + i * 0.08f * flicker;
            float alpha = 0.25f / (i + 1);

            pose.pushPose();
            pose.scale(scale, 1f, scale);

            drawCubeAlpha(vc, pose, rgb, baseWidth, length, alpha, light);

            pose.popPose();
        }

        pose.popPose();
    }

    // ------------------------------------------------------------
    // CRACKED BLADE LIGHTNING (KR3 + L3 + LS1.5)
    // ------------------------------------------------------------
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
        int shardCount = 3 + rand.nextInt(2); // 3–4 shards

        for (int i = 0; i < shardCount; i++) {
            pose.pushPose();

            float y = rand.nextFloat() * bladeLength;
            float shardLen = 0.15f + rand.nextFloat() * 0.30f; // LS1.5
            boolean thin = rand.nextBoolean();
            float width = thin ? 0.02f : 0.05f;

            pose.mulPose(Axis.YP.rotationDegrees(rand.nextFloat() * 360f));
            pose.mulPose(Axis.XP.rotationDegrees(rand.nextFloat() * 360f));
            pose.translate(0, y, 0);

            float alpha = 0.6f + rand.nextFloat() * 0.3f;

            drawCubeAlpha(vc, pose, rgb, width, shardLen, alpha, 0xF000F0);

            pose.popPose();
        }
    }

    // ------------------------------------------------------------
    // BASIC CUBE DRAWING
    // ------------------------------------------------------------
    private static void drawCube(VertexConsumer vc, PoseStack pose, float[] rgb, float width, float height, int light) {
        float w = width;
        float h = height;

        cubeFace(vc, pose, rgb, -w, 0, -w,  w, h, -w, light);
        cubeFace(vc, pose, rgb, -w, 0,  w,  w, h,  w, light);
        cubeFace(vc, pose, rgb, -w, 0, -w, -w, h,  w, light);
        cubeFace(vc, pose, rgb,  w, 0, -w,  w, h,  w, light);
        cubeFace(vc, pose, rgb, -w, h, -w,  w, h,  w, light);
        cubeFace(vc, pose, rgb, -w, 0, -w,  w, 0,  w, light);
    }

    private static void drawCubeAlpha(VertexConsumer vc, PoseStack pose, float[] rgb, float width, float height, float alpha, int light) {
        float w = width;
        float h = height;

        cubeFaceAlpha(vc, pose, rgb, -w, 0, -w,  w, h, -w, alpha, light);
        cubeFaceAlpha(vc, pose, rgb, -w, 0,  w,  w, h,  w, alpha, light);
        cubeFaceAlpha(vc, pose, rgb, -w, 0, -w, -w, h,  w, alpha, light);
        cubeFaceAlpha(vc, pose, rgb,  w, 0, -w,  w, h,  w, alpha, light);
        cubeFaceAlpha(vc, pose, rgb, -w, h, -w,  w, h,  w, alpha, light);
        cubeFaceAlpha(vc, pose, rgb, -w, 0, -w,  w, 0,  w, alpha, light);
    }

    // ------------------------------------------------------------
    // FACE HELPERS (modern VertexConsumer API)
    // ------------------------------------------------------------
    private static void cubeFace(
            VertexConsumer vc,
            PoseStack pose,
            float[] rgb,
            float x1, float y1, float z1,
            float x2, float y2, float z2,
            int light
    ) {
        var m = pose.last().pose();
        float nx = 0f, ny = 0f, nz = 0f;

        vc.addVertex(m, x1, y1, z1)
                .setColor(rgb[0], rgb[1], rgb[2], 1f)
                .setLight(light)
                .setNormal(nx, ny, nz);

        vc.addVertex(m, x2, y1, z1)
                .setColor(rgb[0], rgb[1], rgb[2], 1f)
                .setLight(light)
                .setNormal(nx, ny, nz);

        vc.addVertex(m, x2, y2, z2)
                .setColor(rgb[0], rgb[1], rgb[2], 1f)
                .setLight(light)
                .setNormal(nx, ny, nz);

        vc.addVertex(m, x1, y2, z2)
                .setColor(rgb[0], rgb[1], rgb[2], 1f)
                .setLight(light)
                .setNormal(nx, ny, nz);
    }

    private static void cubeFaceAlpha(
            VertexConsumer vc,
            PoseStack pose,
            float[] rgb,
            float x1, float y1, float z1,
            float x2, float y2, float z2,
            float alpha,
            int light
    ) {
        var m = pose.last().pose();
        float nx = 0f, ny = 0f, nz = 0f;

        vc.addVertex(m, x1, y1, z1)
                .setColor(rgb[0], rgb[1], rgb[2], alpha)
                .setLight(light)
                .setNormal(nx, ny, nz);

        vc.addVertex(m, x2, y1, z1)
                .setColor(rgb[0], rgb[1], rgb[2], alpha)
                .setLight(light)
                .setNormal(nx, ny, nz);

        vc.addVertex(m, x2, y2, z2)
                .setColor(rgb[0], rgb[1], rgb[2], alpha)
                .setLight(light)
                .setNormal(nx, ny, nz);

        vc.addVertex(m, x1, y2, z2)
                .setColor(rgb[0], rgb[1], rgb[2], alpha)
                .setLight(light)
                .setNormal(nx, ny, nz);
    }
}
