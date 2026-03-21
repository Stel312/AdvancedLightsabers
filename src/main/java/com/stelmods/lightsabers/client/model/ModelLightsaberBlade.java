package com.stelmods.lightsabers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.stelmods.lightsabers.common.lightsaber.FocusingCrystal;
import net.minecraft.client.Minecraft;
import org.joml.Matrix4f;

import java.util.Random;

public class ModelLightsaberBlade {

    private ModelLightsaberBlade() {}

    // ------------------------------------------------------------
    // INNER CORE (now flickers with the bloom)
    // ------------------------------------------------------------
    public static void renderInner(float[] rgb, VertexConsumer vc, PoseStack pose, float length, Random flickerRand) {

        // ANH-style micro jitter
        float jitter = 0.97f + flickerRand.nextFloat() * 0.06f; // 0.97–1.03
        float radius = 0.01f * jitter;

        // brightness flicker
        float alpha = 0.92f + flickerRand.nextFloat() * 0.08f; // 0.92–1.00

        drawCylinder(vc, pose, new float[]{1f, 1f, 1f}, radius, length * 1.05f, alpha);
    }

    // ------------------------------------------------------------
    // OUTER GLOW (already flickering)
    // ------------------------------------------------------------
    public static void renderOuter  (float[] rgb, VertexConsumer vc, PoseStack pose, float length) {
        float baseRadius = 0.03f;
        float partial = Minecraft.getInstance().getTimer().getGameTimeDeltaTicks();
        float t = Minecraft.getInstance().level.getGameTime() + partial;
        Random flickerRand = new Random((long)(t * 1000));

        for (int i = 0; i < 4; i++) {
            float jitter = 0.96f + flickerRand.nextFloat() * 0.08f;
            float radius = baseRadius * (1f + i * 0.15f) * jitter;

            // subtle bloom flicker
            radius *= (0.98f + flickerRand.nextFloat() * 0.04f);

            float alpha = 0.25f / (i + 1);
            drawCylinder(vc, pose, rgb, radius, length, alpha);
        }
    }

    // ------------------------------------------------------------
    // CRACKED LIGHTNING
    // ------------------------------------------------------------
    public static void renderCrackedLightning(float[] rgb, VertexConsumer vc, PoseStack pose, float bladeLength, FocusingCrystal c1, FocusingCrystal c2) {
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

    // ------------------------------------------------------------
    // CYLINDER + CAP + POINTED TIP
    // ------------------------------------------------------------
    private static void drawCylinder(VertexConsumer vc, PoseStack pose, float[] rgb, float radius, float height, float alpha) {
        int segments = 8;
        Matrix4f mat = pose.last().pose();

        // side wall
        for (int i = 0; i < segments; i++) {
            float a1 = (float)(2 * Math.PI * i / segments);
            float a2 = (float)(2 * Math.PI * (i + 1) / segments);

            float x1 = radius * (float)Math.cos(a1);
            float z1 = radius * (float)Math.sin(a1);
            float x2 = radius * (float)Math.cos(a2);
            float z2 = radius * (float)Math.sin(a2);

            vc.addVertex(mat, x1, 0f, z1).setColor(rgb[0], rgb[1], rgb[2], alpha);
            vc.addVertex(mat, x1, height, z1).setColor(rgb[0], rgb[1], rgb[2], alpha);
            vc.addVertex(mat, x2, height, z2).setColor(rgb[0], rgb[1], rgb[2], alpha);
            vc.addVertex(mat, x2, 0f, z2).setColor(rgb[0], rgb[1], rgb[2], alpha);
        }

        // top cap
        //drawCap(vc, pose, rgb, radius, height, alpha);

        // pointed tip
        drawTip(vc, pose, segments, rgb, radius, height, alpha);
    }

    // ------------------------------------------------------------
    // TOP CAP (prevents disappearing when viewed straight-on)
    // ------------------------------------------------------------
    private static void drawCap(VertexConsumer vc, PoseStack pose, int segment, float[] rgb, float radius, float height, float alpha) {
        int segments = 4;
        Matrix4f mat = pose.last().pose();

        for (int i = 0; i < segments; i++) {
            float a1 = (float)(2 * Math.PI * i / segments);
            float a2 = (float)(2 * Math.PI * (i + 1) / segments);

            float x1 = radius * (float)Math.cos(a1);
            float z1 = radius * (float)Math.sin(a1);
            float x2 = radius * (float)Math.cos(a2);
            float z2 = radius * (float)Math.sin(a2);

            vc.addVertex(mat, 0f, height, 0f).setColor(rgb[0], rgb[1], rgb[2], alpha);
            vc.addVertex(mat, x1, height, z1).setColor(rgb[0], rgb[1], rgb[2], alpha);
            vc.addVertex(mat, x2, height, z2).setColor(rgb[0], rgb[1], rgb[2], alpha);
        }
    }

    // ------------------------------------------------------------
    // POINTED TIP (cone)
    // ------------------------------------------------------------
    private static void drawTip(VertexConsumer vc, PoseStack pose, int segments, float[] rgb, float radius, float height, float alpha) {

        Matrix4f mat = pose.last().pose();

        float tipHeight = height + 0.05f; // small point extension

        for (int i = 0; i < segments; i++) {
            float a1 = (float)(2 * Math.PI * i / segments);
            float a2 = (float)(2 * Math.PI * (i + 1) / segments);

            float x1 = radius * (float)Math.cos(a1);
            float z1 = radius * (float)Math.sin(a1);
            float x2 = radius * (float)Math.cos(a2);
            float z2 = radius * (float)Math.sin(a2);

            vc.addVertex(mat, x1, height, z1).setColor(rgb[0], rgb[1], rgb[2], alpha);
            vc.addVertex(mat, x2, height, z2).setColor(rgb[0], rgb[1], rgb[2], alpha);
            vc.addVertex(mat, 0f, tipHeight, 0f).setColor(rgb[0], rgb[1], rgb[2], alpha);
        }
    }
}
