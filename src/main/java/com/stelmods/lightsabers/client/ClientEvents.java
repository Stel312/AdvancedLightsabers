package com.stelmods.lightsabers.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.common.block.BlockCrystal;
import com.stelmods.lightsabers.common.block.ModBlocks;
import com.stelmods.lightsabers.lib.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Lightsabers.MODID, value = Dist.CLIENT)
public class ClientEvents {
    public static void colourTint(RegisterColorHandlersEvent.Block event) {
        for (RegistryObject<Block> block : ModBlocks.BLOCKS.getEntries()) {
            if (block.get() instanceof BlockCrystal crystal) {
                event.register((state, level, pos, tintIndex) -> crystal.getCrystalColor().color, crystal);
            }
        }
    }

    public static void itemTint(RegisterColorHandlersEvent.Item event) {
        for (RegistryObject<Block> block : ModBlocks.BLOCKS.getEntries()) {
            if (block.get() instanceof BlockCrystal crystal) {
                event.register((itemStack, tint) -> crystal.getCrystalColor().color, crystal.asItem());
            }
        }
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        if (InputHandler.forceSense && event.getOverlay() == VanillaGuiOverlay.VIGNETTE.type()) {
            Minecraft mc = Minecraft.getInstance();
            int width = mc.getWindow().getGuiScaledWidth();
            int height = mc.getWindow().getGuiScaledHeight();

            event.getGuiGraphics().fill(0, 0, width, height, 0x6b8ba9FF); // Azul translúcido
        }
    }

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelStageEvent event) {
        if (InputHandler.forceSense && event.getStage() == RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS) {
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;
            if (player == null)
                return;

            Level level = player.level();
            Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();
            PoseStack poseStack = event.getPoseStack();

            poseStack.pushPose();
            poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

            //TODO Make lines render through blocks and scan only visible blocks rather than radius around the player
            for (BlockPos pos : BlockPos.betweenClosed(player.blockPosition().offset(-20, -20, -20), player.blockPosition().offset(20, 20, 20))) {
                BlockState state = level.getBlockState(pos);
                if (Utils.isBlockStateInteractable(state)) {
                    drawCubeOutline(poseStack, pos);
                }
            }

            poseStack.popPose();
        }
    }

    //This was to draw according to the block height, but it's unused so far, but good to keep just in case
    /*BlockState blockState =  mc.level.getBlockState(pos);
    VoxelShape form = blockState.getShape(mc.level,pos);
    float height = 1;
    if (form != Shapes.empty()) {
        double minY = form.bounds().minY;
        double maxY = form.bounds().maxY;

        height = (float) (maxY - minY);
    }*/

    private static void drawCubeOutline(PoseStack poseStack, BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();
        MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();

        // Usamos RenderType.LINES para dibujar las líneas del contorno
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.LINES);

        float x = pos.getX();
        float y = pos.getY();
        float z = pos.getZ();

        // Definimos los 8 vértices del cubo
        float[][] vertices = {
                {x, y, z},
                {x + 1, y, z},
                {x + 1, y, z + 1},
                {x, y, z + 1},
                {x, y + 1, z},
                {x + 1, y + 1, z},
                {x + 1, y + 1, z + 1},
                {x, y + 1, z + 1}
        };

        // Conectar los vértices para formar las 12 aristas del cubo
        int[][] edges = {
                {0, 1}, {1, 2}, {2, 3}, {3, 0}, // Base del cubo
                {4, 5}, {5, 6}, {6, 7}, {7, 4}, // Parte superior del cubo
                {0, 4}, {1, 5}, {2, 6}, {3, 7}  // Conexiones entre la base y la parte superior
        };

        // Desactivar la prueba de profundidad para asegurar que las líneas no sean bloqueadas por los bloques
        RenderSystem.disableDepthTest();

        // Dibujar cada arista
        for (int[] edge : edges) {
            int start = edge[0];
            int end = edge[1];

            // Obtener las coordenadas de los vértices
            float[] startVertex = vertices[start];
            float[] endVertex = vertices[end];

            // Dibujar la línea con el color amarillo (255, 255, 0)
            vertexConsumer.vertex(poseStack.last().pose(), startVertex[0], startVertex[1], startVertex[2])
                    .color(255, 255, 0, 255)
                    .normal(1, 1, 1) // Normal arbitraria
                    .endVertex();
            vertexConsumer.vertex(poseStack.last().pose(), endVertex[0], endVertex[1], endVertex[2])
                    .color(255, 255, 0, 255)
                    .normal(1, 1, 1) // Normal arbitraria
                    .endVertex();
        }

        // Finalizamos el renderizado de las líneas
        bufferSource.endBatch(RenderType.LINES);

        // Restaurar la prueba de profundidad (Z-buffer) para el resto del renderizado
        RenderSystem.enableDepthTest();
    }

}
