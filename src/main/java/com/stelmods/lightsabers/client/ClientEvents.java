package com.stelmods.lightsabers.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stelmods.lightsabers.capabilities.PlayerCapabilities;
import com.stelmods.lightsabers.client.render.item.RenderItemLightsaber;
import com.stelmods.lightsabers.common.block.BlockCrystal;
import com.stelmods.lightsabers.common.block.ModBlocks;
import com.stelmods.lightsabers.common.item.ModItems;
import com.stelmods.lightsabers.lib.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.stelmods.lightsabers.client.ClientUtils.random;

public class ClientEvents {


    @EventBusSubscriber(value = Dist.CLIENT)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void colourTint(RegisterColorHandlersEvent.Block event) {
            for (DeferredHolder<Block, ? extends Block> block : ModBlocks.BLOCKS.getEntries()) {
                if (block.get() instanceof BlockCrystal crystal) {
                    event.register((state, level, pos, tintIndex) -> crystal.getCrystalColor().color, crystal);
                }
            }
        }

        @SubscribeEvent
        public static void itemTint(RegisterColorHandlersEvent.Item event) {
            for (DeferredHolder<Block, ? extends Block> block : ModBlocks.BLOCKS.getEntries()) {
                if (block.get() instanceof BlockCrystal crystal) {
                    event.register((itemStack, tint) -> crystal.getCrystalColor().color, crystal.asItem());
                }
            }
        }

        @SubscribeEvent
        public static void registerClientExtensions(RegisterClientExtensionsEvent event){
            event.registerItem(new IClientItemExtensions() {
                                   @Override
                                   public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                                       return RenderItemLightsaber.BEWLR;
                                   }
                               },
                    ModItems.lightsaber.get() // your item
            );
            event.registerItem(new IClientItemExtensions() {
                                   @Override
                                   public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                                       return RenderItemLightsaber.BEWLR;
                                   }
                               },
                    ModItems.doubleLightsaber.get() // your item
            );
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGuiLayerEvent.Post event) {
        if (InputHandler.forceSense && event.getName().equals(VanillaGuiLayers.CROSSHAIR)) {
            Minecraft mc = Minecraft.getInstance();
            int width = mc.getWindow().getGuiScaledWidth();
            int height = mc.getWindow().getGuiScaledHeight();

            event.getGuiGraphics().fill(0, 0, width, height, 0x6b8ba9FF);
        }
    }

    public static Map<Integer, List<Integer>> lightningMap = new HashMap<>();
    /**
     * Renders force sense nearby interactable blocks
     * Uncomment the annotation to enable
     * Maybe add a requirement for a specific force sense level
     * @param event
     */

   /* @SubscribeEvent
    public void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES)
            return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null)
            return;

        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();

        float partialTicks = mc.getTimer().getGameTimeDeltaPartialTick(false);
        // Lock on
        if (ModConfigs.SERVER.softLockOnMode.get() && InputHandler.lockOn != null) {
            ClientUtils.drawLockOnIndicator(InputHandler.lockOn.getId(), poseStack, buffer, partialTicks);
        }


        buffer.endBatch();
    }*/
    @SubscribeEvent
    public void onRenderWorld(RenderLevelStageEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if(mc.level == null)
            return;

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            Player player = mc.player;

            if (player == null)
                return;

            if(!InputHandler.forceSense)
                return;

            PoseStack poseStack = event.getPoseStack();
            MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();
            float partialTicks = mc.getTimer().getGameTimeDeltaPartialTick(false);

            Level level = player.level();

            Vec3 eyePos = player.getEyePosition();
            Vec3 look = player.getLookAngle().normalize();

            BlockPos center = player.blockPosition();
            int radius = 20;
            int maxDistance = 20;
            double maxDistSqr = maxDistance * maxDistance;

            poseStack.pushPose();
            {
                for (int x = -radius; x <= radius; x++) {
                    for (int y = -2; y <= 2; y++) {
                        for (int z = -radius; z <= radius; z++) {
                            BlockPos pos = center.offset(x, y, z);
                            BlockState state = level.getBlockState(pos);

                            if (state.isAir())
                                continue;

                            if (!Utils.isBlockStateInteractable(state))
                                continue;

                            Vec3 blockCenter = Vec3.atCenterOf(pos);
                            Vec3 toBlockVec = blockCenter.subtract(eyePos);

                            if (toBlockVec.lengthSqr() > maxDistSqr)
                                continue;

                            Vec3 toBlock = toBlockVec.normalize();
                            double dot = look.dot(toBlock);

                            if (dot < 0.6)
                                continue;

                            HitResult hit = level.clip(new ClipContext(eyePos, blockCenter, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));

                            if (hit.getType() == HitResult.Type.BLOCK && !((BlockHitResult) hit).getBlockPos().equals(pos)) {
                                continue;
                            }

                            ClientUtils.drawLockOnIndicator(blockCenter, poseStack, buffer, partialTicks);
                        }
                    }
                }
            }
            poseStack.popPose();

            buffer.endBatch();
        }


        //Force lightning rendering
        for (Map.Entry<Integer, List<Integer>> entry : lightningMap.entrySet()) {
            Entity e = mc.level.getEntity(entry.getKey());
            if(e instanceof Player player){
                PlayerCapabilities playerData = PlayerCapabilities.get(player);
                if (playerData.isLightningMode()) {
                    MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();

                    PoseStack poseStack = event.getPoseStack();
                    if(entry.getValue().isEmpty()){
                        poseStack.pushPose();
                        {
                            Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();
                            poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

                            Vec3 start = player.getEyePosition(); // Posición de los ojos del jugador
                            Vec3 look = player.getViewVector(1.0F); // Dirección en la que mira
                            Vec3 end = start.add(look.scale(10)); // 10 bloques de distancia máxima
                            Level level = player.level();

                            HitResult hitResult = level.clip(new ClipContext(start, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));

                            if (hitResult.getType() == HitResult.Type.BLOCK) {
                                end = hitResult.getLocation();
                            }
                            ClientUtils.renderLightningBeam(poseStack, bufferSource, player.getEyePosition().add(random.nextFloat()-0.5F, random.nextFloat(0.1F)-0.2, random.nextFloat()-0.5F), end.add(random.nextFloat(5)-2.5,random.nextFloat(),random.nextFloat(5)-2.5), 20, 0.3F);
                        }
                        poseStack.popPose();
                    } else {
                        float thickness = Mth.clamp(0.5F / (float)Math.sqrt(entry.getValue().size()), 0.08F, 0.4F);

                        for(int tID : entry.getValue()){
                           Entity target = mc.level.getEntity(tID);
                           if (target != null) {
                               poseStack.pushPose();
                               {
                                   Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();
                                   poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

                                   ClientUtils.renderLightningBeam(poseStack, bufferSource, player.getEyePosition().add(0, -0.2, 0), target.position().add(random.nextFloat(target.getBbWidth()) - 0.5F, random.nextFloat(target.getBbHeight()), random.nextFloat(target.getBbWidth()) - 0.5F), 20, thickness);
                               }
                               poseStack.popPose();
                           }
                       }
                    }

                }
            }
        }

        //Block outline
       if (false && event.getStage() == RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS) {
            LocalPlayer player = mc.player;
            if (player == null)
                return;

            Level level = player.level();
            Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();
            PoseStack poseStack = event.getPoseStack();

            poseStack.pushPose();
            {
                poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

                Frustum frustum = mc.levelRenderer.getFrustum();

                int radius = 20;

                BlockPos playerPos = player.blockPosition();
                BlockPos minPos = playerPos.offset(-radius, -10, -radius);
                BlockPos maxPos = playerPos.offset(radius, 10, radius + 5);

                //TODO Maybe this can be further optimized with some maths ignoring the blocks outside player view
                for (BlockPos pos : BlockPos.betweenClosed(minPos, maxPos)) {
                    AABB blockAABB = new AABB(pos);
                    if (frustum.isVisible(blockAABB)) {  // Filters blocks outside of frustum
                        BlockState state = level.getBlockState(pos);

                        if (Utils.isBlockStateInteractable(state)) {
                            //drawCubeOutline(poseStack, pos);
                        }
                    }
                }

                /*int radius = 50;
                //TODO Make lines render through blocks and scan only visible blocks rather than radius around the player
                for (BlockPos pos : BlockPos.betweenClosed(player.blockPosition().offset(-radius, -radius, -radius), player.blockPosition().offset(radius, radius, radius))) {
                    AABB blockAABB = new AABB(pos);
                    if(mc.levelRenderer.getFrustum().isVisible(blockAABB)) {
                        BlockState state = level.getBlockState(pos);
                        if (Utils.isBlockStateInteractable(state)) {
                            drawCubeOutline(poseStack, pos);
                        }
                    }
                }*/
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

    private void drawCubeOutline(PoseStack poseStack, BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();
        MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.LINES);

       // ClientUtils.renderLightningBeam(poseStack, bufferSource, mc.player.getEyePosition().add(0,-0.3,0), pos.getCenter(), 20);

        /*float x = pos.getX();
        float y = pos.getY();
        float z = pos.getZ();

        vertexConsumer.vertex(poseStack.last().pose(), x+0.5F,y+0.5F,z+0.5F).color(255, 255, 0, 255).normal(1, 1, 1).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(), (float)mc.player.position().x(),(float)(mc.player.position().y()+mc.player.getEyeHeight()-0.1F),(float)mc.player.position().z()).color(100, 180, 255, 255).normal(1, 1, 1).endVertex();

        bufferSource.endBatch(RenderType.LINES);*/

        /**
         * Render block hitbox
         */
        // We define the 8 cube vertex
        /*float[][] vertices = {
                {x, y, z},
                {x + 1, y, z},
                {x + 1, y, z + 1},
                {x, y, z + 1},
                {x, y + 1, z},
                {x + 1, y + 1, z},
                {x + 1, y + 1, z + 1},
                {x, y + 1, z + 1}
        };

        // Connect the vertex to form the 12 cube edges
        int[][] edges = {
                {0, 1}, {1, 2}, {2, 3}, {3, 0}, // Base del cubo
                {4, 5}, {5, 6}, {6, 7}, {7, 4}, // Parte superior del cubo
                {0, 4}, {1, 5}, {2, 6}, {3, 7}  // Conexiones entre la base y la parte superior
        };

        RenderSystem.disableDepthTest();

        // Draw each edge
        for (int[] edge : edges) {
            int start = edge[0];
            int end = edge[1];

            // Get vertex
            float[] startVertex = vertices[start];
            float[] endVertex = vertices[end];

            // Draw yellow line
            vertexConsumer.vertex(poseStack.last().pose(), startVertex[0], startVertex[1], startVertex[2])
                    .color(255, 255, 0, 255)
                    .normal(1, 1, 1) // Normal arbitraria
                    .endVertex();
            vertexConsumer.vertex(poseStack.last().pose(), endVertex[0], endVertex[1], endVertex[2])
                    .color(255, 255, 0, 255)
                    .normal(1, 1, 1) // Normal arbitraria
                    .endVertex();
        }

        // Finish line rendering
        bufferSource.endBatch(RenderType.LINES);

        RenderSystem.enableDepthTest();*/
    }

}
