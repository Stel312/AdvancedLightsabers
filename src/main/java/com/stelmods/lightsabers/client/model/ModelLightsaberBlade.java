package com.stelmods.lightsabers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.stelmods.lightsabers.common.lightsaber.FocusingCrystal;
import net.minecraft.client.Minecraft;
import org.joml.Matrix4f;

import java.util.Random;

public class ModelLightsaberBlade {

    private static final float FINE_CUT_OUTER_WIDTH = 0.04f;
    private static final float FINE_CUT_OUTER_DEPTH = 0.005f;
    private static final float FINE_CUT_INNER_WIDTH = 0.013f;
    private static final float FINE_CUT_INNER_DEPTH = 0.0022f;
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

    private static boolean hasCrystal(FocusingCrystal c1, FocusingCrystal c2, FocusingCrystal crystal) {
        return c1 == crystal || c2 == crystal;
    }

    public static void renderInner(float[] rgb, VertexConsumer vc, PoseStack pose, float length, Random flickerRand, FocusingCrystal c1, FocusingCrystal c2) {
        // INVERTING swaps colors: black inner with white outer.
        float[] innerRgb = rgb;
        if (hasCrystal(c1, c2, FocusingCrystal.INVERTING)) {
            innerRgb = new float[]{0f, 0f, 0f}; // Black inner
        } else if (hasCrystal(c1, c2, FocusingCrystal.PRISMATIC)) {
            // Prismatic uses a hue-shifted inner core.
            float prismaticPhase = 0.33f + (flickerRand.nextFloat() - 0.5f) * 0.08f;
            innerRgb = getPrismaticColor(rgb, prismaticPhase);
        }

        float jitter = 0.94f + flickerRand.nextFloat() * 0.06f;
        float radiusMultiplier = hasCrystal(c1, c2, FocusingCrystal.COMPRESSED) ? 0.5f : 1.0f;
        float radius = 0.01f * jitter * radiusMultiplier;

        float alpha = hasCrystal(c1, c2, FocusingCrystal.INVERTING) ? 1.0f : 0.92f + flickerRand.nextFloat() * 0.08f;

        // Keep fine-cut as a flat core too, otherwise the inner cylinder clips outside the darksaber-like shell.
        if (hasCrystal(c1, c2, FocusingCrystal.FINE_CUT)) {
            renderFineCutCore(vc, pose, innerRgb, length * 1.05f, flickerRand, radiusMultiplier, alpha);
            return;
        }

        drawCylinder(vc, pose, innerRgb, radius, length * 1.05f, alpha, c1, c2);
    }

    private static float[] getPrismaticColor(float[] baseRgb, float phase) {
        float hue = (float) (Math.atan2(baseRgb[2] - baseRgb[1], 2f * baseRgb[0] - baseRgb[1] - baseRgb[2]) / (2 * Math.PI));
        hue = (hue + phase) % 1f;

        float h = hue * 6f;
        int i = (int) Math.floor(h);
        float f = h - i;
        float p = 0f;
        float q = 1f - f;

        float r;
        float g;
        float b;
        switch (i % 6) {
            case 0: r = 1f; g = f; b = p; break;
            case 1: r = q; g = 1f; b = p; break;
            case 2: r = p; g = 1f; b = f; break;
            case 3: r = p; g = q; b = 1f; break;
            case 4: r = f; g = p; b = 1f; break;
            default: r = 1f; g = p; b = q; break;
        }

        return new float[]{r, g, b};
    }

    public static void renderOuter(float[] rgb, VertexConsumer vc, PoseStack pose, float length, Random flickerRand, FocusingCrystal c1, FocusingCrystal c2) {
        // INVERTING swaps colors: white outer.
        float[] outerRgb = rgb;
        if (hasCrystal(c1, c2, FocusingCrystal.INVERTING)) {
            outerRgb = new float[]{1f, 1f, 1f};
        }

        float baseRadius = 0.02f;
        float radiusMultiplier = hasCrystal(c1, c2, FocusingCrystal.COMPRESSED) ? 0.7f : 1.0f;

        // FINE_CUT creates a flat sword-like blade instead of cylindrical
        if (hasCrystal(c1, c2, FocusingCrystal.FINE_CUT)) {
            renderFineCutBlade(vc, pose, outerRgb, length, flickerRand);
            return;
        }

        for (int i = 0; i < 4; i++) {
            float jitter = 0.96f + flickerRand.nextFloat() * 0.08f;
            float radius = baseRadius * (1f + i * 0.2f) * jitter * radiusMultiplier;

            radius *= (0.98f + flickerRand.nextFloat() * 0.04f);

            float alpha = 0.2f / (i + 1);
            drawCylinder(vc, pose, outerRgb, radius, length, alpha, c1, c2);
        }
    }

    public static void renderGuiBloom(float[] rgb, VertexConsumer vc, PoseStack pose, float length, FocusingCrystal c1, FocusingCrystal c2) {
        float[] bloomRgb = rgb;
        if (hasCrystal(c1, c2, FocusingCrystal.INVERTING)) {
            bloomRgb = new float[]{1f, 1f, 1f}; // White bloom
        }

        float[] radii = { 0.007f, 0.011f, 0.016f, 0.022f, 0.030f, 0.040f };
        float[] alphas = { 0.044f, 0.020f, 0.009f, 0.004f, 0.002f, 0.001f };

        for (int i = 0; i < radii.length; i++) {
            drawCylinder(vc, pose, bloomRgb, radii[i], length, alphas[i], c1, c2);
        }
    }

    public static void renderCrackedLightning(float[] rgb, VertexConsumer vc, PoseStack pose,
                                              float bladeLength, FocusingCrystal c1, FocusingCrystal c2) {

        if (!hasCrystal(c1, c2, FocusingCrystal.CRACKED)) return;

        long gameTime = Minecraft.getInstance().level != null ? Minecraft.getInstance().level.getGameTime() : 0L;
        Random rand = new Random(gameTime / 3L);

        // Cracked nubs: many tiny pyramid bursts that barely leave the blade edge.
        int nubCount = 10 + rand.nextInt(5);
        float spawnRadius = hasCrystal(c1, c2, FocusingCrystal.COMPRESSED) ? 0.014f : 0.021f;
        for (int i = 0; i < nubCount; i++) {
            float y = bladeLength * (0.08f + rand.nextFloat() * 0.84f);
            float outward = rand.nextFloat() * (float) (Math.PI * 2.0);
            float yaw = (float) Math.toDegrees(outward) + (rand.nextFloat() - 0.5f) * 26f;
            float pitch = 78f + rand.nextFloat() * 18f;
            float nubLength = 0.015f + rand.nextFloat() * 0.02f;
            float nubRadius = 0.0025f + rand.nextFloat() * 0.002f;
            float nubAlpha = 0.62f + rand.nextFloat() * 0.2f;

            float x = (float) Math.cos(outward) * spawnRadius;
            float z = (float) Math.sin(outward) * spawnRadius;

            pose.pushPose();
            pose.translate(x, y, z);
            pose.mulPose(Axis.YP.rotationDegrees(yaw));
            pose.mulPose(Axis.ZP.rotationDegrees(pitch));
            drawPyramidNub(vc, pose, rgb, nubRadius, nubLength, nubAlpha);
            pose.popPose();
        }
    }

    private static void drawPyramidNub(VertexConsumer vc, PoseStack pose,
                                       float[] rgb, float baseRadius, float length, float alpha) {
        Matrix4f mat = pose.last().pose();

        // Legacy quad order preserved exactly.
        v(vc, mat, baseRadius, 0f, 0f, rgb[0], rgb[1], rgb[2], alpha);
        v(vc, mat, 0f, 0f, baseRadius, rgb[0], rgb[1], rgb[2], alpha);
        v(vc, mat, 0f, length, 0f, rgb[0], rgb[1], rgb[2], alpha);
        v(vc, mat, 0f, length, 0f, rgb[0], rgb[1], rgb[2], alpha);

        v(vc, mat, 0f, 0f, baseRadius, rgb[0], rgb[1], rgb[2], alpha);
        v(vc, mat, -baseRadius, 0f, 0f, rgb[0], rgb[1], rgb[2], alpha);
        v(vc, mat, 0f, length, 0f, rgb[0], rgb[1], rgb[2], alpha);
        v(vc, mat, 0f, length, 0f, rgb[0], rgb[1], rgb[2], alpha);

        v(vc, mat, -baseRadius, 0f, 0f, rgb[0], rgb[1], rgb[2], alpha);
        v(vc, mat, 0f, 0f, -baseRadius, rgb[0], rgb[1], rgb[2], alpha);
        v(vc, mat, 0f, length, 0f, rgb[0], rgb[1], rgb[2], alpha);
        v(vc, mat, 0f, length, 0f, rgb[0], rgb[1], rgb[2], alpha);

        v(vc, mat, 0f, 0f, -baseRadius, rgb[0], rgb[1], rgb[2], alpha);
        v(vc, mat, baseRadius, 0f, 0f, rgb[0], rgb[1], rgb[2], alpha);
        v(vc, mat, 0f, length, 0f, rgb[0], rgb[1], rgb[2], alpha);
        v(vc, mat, 0f, length, 0f, rgb[0], rgb[1], rgb[2], alpha);
    }

    private static void renderFineCutCore(VertexConsumer vc, PoseStack pose,
                                           float[] rgb, float height, Random flickerRand,
                                           float radiusMultiplier, float alpha) {
        float jitter = 0.99f + flickerRand.nextFloat() * 0.08f;
        float xScale = (FINE_CUT_INNER_WIDTH * jitter * radiusMultiplier) / 0.013f;
        float zScale = (FINE_CUT_INNER_DEPTH * jitter * radiusMultiplier) / 0.0022f;

        pose.pushPose();
        pose.translate(0f, height, 0f);
        renderLegacyFineCutMesh(vc, pose, rgb, height / 0.0625f, xScale, zScale, alpha, alpha * 0.92f, alpha * 0.75f, alpha);
        pose.popPose();
    }

    /**
     * Renders a flat sword-like blade (FINE_CUT style - dark saber)
     */
    private static void renderFineCutBlade(VertexConsumer vc, PoseStack pose,
                                          float[] rgb, float height, Random flickerRand) {

        float jitter = 1.0f + flickerRand.nextFloat() * 0.10f;
        float xScale = (FINE_CUT_OUTER_WIDTH * jitter) / 0.04f;
        float zScale = (FINE_CUT_OUTER_DEPTH * jitter) / 0.005f;

        pose.pushPose();
        pose.translate(0f, height, 0f);
        renderLegacyFineCutMesh(vc, pose, rgb, height / 0.0625f, xScale, zScale, 0.9f, 0.7f, 0.5f, 0.8f);
        pose.popPose();
    }

    private static void renderLegacyFineCutMesh(VertexConsumer vc, PoseStack pose,
                                                float[] rgb, float bladeLength,
                                                float xScale, float zScale,
                                                float frontAlpha, float backAlpha, float edgeAlpha, float tipAlpha) {

        Matrix4f mat = pose.last().pose();
        float f = 0.0625f;
        float length = f * bladeLength * 0.7F;
        float edge = f * 1.5F;
        float edgeAngle = -f * 1.5F;
        float length1 = f * bladeLength * 0.3F;
        float edge1 = f / 2F;
        float tip = f * 1.5F;

        float xHalf = (f / 2F) * xScale;
        float zHalf = (f / 2F) * zScale;
        float zEdge = edge * zScale;
        float zEdge1 = edge1 * zScale;

        v(vc, mat, -xHalf, -length, zHalf, rgb[0], rgb[1], rgb[2], frontAlpha);
        v(vc, mat, 0f, -length, zEdge, rgb[0], rgb[1], rgb[2], frontAlpha);
        v(vc, mat, 0f, edgeAngle, zEdge, rgb[0], rgb[1], rgb[2], frontAlpha);
        v(vc, mat, -xHalf, -f, zHalf, rgb[0], rgb[1], rgb[2], frontAlpha);

        v(vc, mat, xHalf, -length, zHalf, rgb[0], rgb[1], rgb[2], frontAlpha);
        v(vc, mat, 0f, -length, zEdge, rgb[0], rgb[1], rgb[2], frontAlpha);
        v(vc, mat, 0f, edgeAngle, zEdge, rgb[0], rgb[1], rgb[2], frontAlpha);
        v(vc, mat, xHalf, -f, zHalf, rgb[0], rgb[1], rgb[2], frontAlpha);

        v(vc, mat, xHalf, -f, zHalf, rgb[0], rgb[1], rgb[2], backAlpha);
        v(vc, mat, 0f, edgeAngle, zEdge, rgb[0], rgb[1], rgb[2], backAlpha);
        v(vc, mat, 0f, edgeAngle, zEdge, rgb[0], rgb[1], rgb[2], backAlpha);
        v(vc, mat, -xHalf, -f, zHalf, rgb[0], rgb[1], rgb[2], backAlpha);

        v(vc, mat, -xHalf, -length, zHalf, rgb[0], rgb[1], rgb[2], edgeAlpha);
        v(vc, mat, -xHalf, -length1 - length, zEdge1, rgb[0], rgb[1], rgb[2], edgeAlpha);
        v(vc, mat, 0f, -length1 - length, zEdge1, rgb[0], rgb[1], rgb[2], edgeAlpha);
        v(vc, mat, 0f, -length, zEdge, rgb[0], rgb[1], rgb[2], edgeAlpha);

        v(vc, mat, xHalf, -length, zHalf, rgb[0], rgb[1], rgb[2], edgeAlpha);
        v(vc, mat, xHalf, -length1 - length, zEdge1, rgb[0], rgb[1], rgb[2], edgeAlpha);
        v(vc, mat, 0f, -length1 - length, zEdge1, rgb[0], rgb[1], rgb[2], edgeAlpha);
        v(vc, mat, 0f, -length, zEdge, rgb[0], rgb[1], rgb[2], edgeAlpha);

        v(vc, mat, -xHalf, -f * bladeLength, zHalf, rgb[0], rgb[1], rgb[2], tipAlpha);
        v(vc, mat, 0f, -tip - f * bladeLength, -zHalf, rgb[0], rgb[1], rgb[2], tipAlpha);
        v(vc, mat, 0f, -tip - f * bladeLength, -zHalf, rgb[0], rgb[1], rgb[2], tipAlpha);
        v(vc, mat, -xHalf, -f * bladeLength, -zHalf, rgb[0], rgb[1], rgb[2], tipAlpha);

        v(vc, mat, xHalf, -f * bladeLength, zHalf, rgb[0], rgb[1], rgb[2], tipAlpha);
        v(vc, mat, 0f, -tip - f * bladeLength, -zHalf, rgb[0], rgb[1], rgb[2], tipAlpha);
        v(vc, mat, 0f, -tip - f * bladeLength, -zHalf, rgb[0], rgb[1], rgb[2], tipAlpha);
        v(vc, mat, xHalf, -f * bladeLength, -zHalf, rgb[0], rgb[1], rgb[2], tipAlpha);

        v(vc, mat, -xHalf, -f * bladeLength, -zHalf, rgb[0], rgb[1], rgb[2], tipAlpha);
        v(vc, mat, 0f, -tip - f * bladeLength, -zHalf, rgb[0], rgb[1], rgb[2], tipAlpha);
        v(vc, mat, 0f, -tip - f * bladeLength, -zHalf, rgb[0], rgb[1], rgb[2], tipAlpha);
        v(vc, mat, xHalf, -f * bladeLength, -zHalf, rgb[0], rgb[1], rgb[2], tipAlpha);

        v(vc, mat, -xHalf, -f * bladeLength, zHalf, rgb[0], rgb[1], rgb[2], tipAlpha);
        v(vc, mat, 0f, -tip - f * bladeLength, -zHalf, rgb[0], rgb[1], rgb[2], tipAlpha);
        v(vc, mat, 0f, -tip - f * bladeLength, -zHalf, rgb[0], rgb[1], rgb[2], tipAlpha);
        v(vc, mat, xHalf, -f * bladeLength, zHalf, rgb[0], rgb[1], rgb[2], tipAlpha);
    }


    private static void drawCylinder(VertexConsumer vc, PoseStack pose,
                                     float[] rgb, float radius, float height, float alpha, FocusingCrystal c1, FocusingCrystal c2) {

        int segments = hasCrystal(c1, c2, FocusingCrystal.CRACKED) ? 6 : 8; // Fewer segments for cracked look
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
