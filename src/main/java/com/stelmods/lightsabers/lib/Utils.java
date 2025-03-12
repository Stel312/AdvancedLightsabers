package com.stelmods.lightsabers.lib;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class Utils {
    public static boolean isBlockStateInteractable(BlockState blockState){
        if(blockState.getBlock() == Blocks.IRON_DOOR || blockState.getBlock() == Blocks.IRON_TRAPDOOR) { // Exclude iron doors and trapdors since they don't have an use method
            return false;
        }
        return blockState.getValues().containsKey(LeverBlock.POWERED);
    }

    private static final Random random = new Random();

   /* public static void renderLightningBeam(PoseStack poseStack, MultiBufferSource bufferSource, Vec3 start, Vec3 end, int segments) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.LINES);

        poseStack.pushPose();

        Vec3 direction = end.subtract(start).normalize();
        double length = start.distanceTo(end);

        Vec3[] points = new Vec3[segments + 1];
        points[0] = start;
        points[segments] = end;

        for (int i = 1; i < segments; i++) {
            double t = (double) i / segments;
            double offsetX = (random.nextDouble() - 0.5) * 0.2;
            double offsetY = (random.nextDouble() - 0.5) * 0.2;
            double offsetZ = (random.nextDouble() - 0.5) * 0.2;
            points[i] = start.add(direction.scale(t * length)).add(offsetX, offsetY, offsetZ);
        }

        for (int i = 0; i < segments; i++) {
            Vec3 p1 = points[i];
            Vec3 p2 = points[i + 1];

            vertexConsumer.vertex(poseStack.last().pose(), (float) p1.x, (float) p1.y, (float) p1.z)
                    .color(100, 100, 255, 255)
                    .normal(poseStack.last().normal(), 0, 1, 0)
                    .endVertex();

            vertexConsumer.vertex(poseStack.last().pose(), (float) p2.x, (float) p2.y, (float) p2.z)
                    .color(100, 200, 255, 255)
                    .normal(poseStack.last().normal(), 0, 1, 0)
                    .endVertex();
        }

        poseStack.popPose();
    }*/


}

