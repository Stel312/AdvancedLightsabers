package com.stelmods.lightsabers.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.client.model.ModelLightsaberBlade;
import com.stelmods.lightsabers.common.block.BlockCrystal;
import com.stelmods.lightsabers.common.component.LightsaberDataComponents;
import com.stelmods.lightsabers.common.item.ItemFocusingCrystal;
import com.stelmods.lightsabers.common.item.LightsaberItem;
import com.stelmods.lightsabers.common.item.LightsaberPart;
import com.stelmods.lightsabers.common.lightsaber.FocusingCrystal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class RenderItemLightsaber extends BlockEntityWithoutLevelRenderer {

    private ItemRenderer renderItem;

    public static final RenderItemLightsaber BEWLR =
            new RenderItemLightsaber(
                    Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                    Minecraft.getInstance().getEntityModels()
            );

    public RenderItemLightsaber(BlockEntityRenderDispatcher dispatcher, EntityModelSet models) {
        super(dispatcher, models);
    }

    @Override
    public void renderByItem(ItemStack stack,
                             ItemDisplayContext ctx,
                             PoseStack pose,
                             MultiBufferSource buffer,
                             int light,
                             int overlay) {

        this.renderItem = Minecraft.getInstance().getItemRenderer();

        if (!(stack.getItem() instanceof LightsaberItem)) return;

        if (stack.has(LightsaberDataComponents.LIGHTSABER)) {
            renderSingle(ctx, pose, buffer, light, stack);
        } else if (stack.has(LightsaberDataComponents.DOUBLE_LIGHTSABER)) {
            renderDouble(ctx, pose, buffer, light, stack);
        }
    }

    // ------------------------------------------------------------
    // HEIGHT HELPERS
    // ------------------------------------------------------------

    private float heightOf(String id) {
        if (id == null || id.isEmpty()) return 0f;
        ResourceLocation rl = ResourceLocation.tryParse(id);
        if (rl == null) return 0f;

        Item item = BuiltInRegistries.ITEM.get(rl);
        return item instanceof LightsaberPart part ? part.getHeight() : 0f;
    }

    private float totalHeight(LightsaberDataComponents.LightsaberData d) {
        return heightOf(d.emitter()) +
                heightOf(d.switch_()) +
                heightOf(d.hilt()) +
                heightOf(d.pomel());
    }


    public void renderDouble(ItemDisplayContext ctx,
                              PoseStack pose,
                              MultiBufferSource buffer,
                              int light,
                              ItemStack stack) {

        var dbl = stack.get(LightsaberDataComponents.DOUBLE_LIGHTSABER);
        if (dbl == null) return;

        var upper = dbl.upper();
        var lower = dbl.lower();

        float upperHeight = totalHeight(upper) - heightOf(upper.pomel());
        float lowerHeight = totalHeight(lower) - heightOf(lower.pomel());

        boolean showBlade = false;

        switch (ctx) {
            case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND -> {
                pose.translate(0.5, 0.5, 0.55);
                showBlade = true;
            }
            case FIRST_PERSON_RIGHT_HAND -> {
                pose.translate(1, 0.1, 0);
                showBlade = true;
            }
            case FIRST_PERSON_LEFT_HAND -> showBlade = true;
            case GUI -> {
                pose.translate(0.5, 0.5, 0);
                pose.mulPose(Axis.ZN.rotationDegrees(-45));
                pose.scale(1.1f, 1.1f, 1.1f);
            }
            case FIXED -> {
                pose.mulPose(Axis.ZN.rotationDegrees(-45));
                pose.translate(-0.7, 0.5, 0.5);
            }
            case GROUND -> {
                pose.translate(0.5, 1, 0.5);
                pose.mulPose(Axis.ZN.rotationDegrees(45));
            }
        }

        // Build per-half stacks
        ItemStack upperStack = stack.copy();
        upperStack.set(LightsaberDataComponents.LIGHTSABER, upper);

        ItemStack lowerStack = stack.copy();
        lowerStack.set(LightsaberDataComponents.LIGHTSABER, lower);

        // Upper half
        pose.pushPose();
        if (showBlade || ctx == ItemDisplayContext.NONE)
            renderBlade(upperStack, stack, buffer, pose, upperHeight);

        upperHeight = renderPart(upper.emitter(), upperHeight, (byte) 1, ctx, pose, buffer, light);
        upperHeight = renderPart(upper.switch_(), upperHeight, (byte) 1, ctx, pose, buffer, light);
        renderPart(upper.hilt(), upperHeight, (byte) 1, ctx, pose, buffer, light);
        pose.popPose();

        // Lower half
        pose.pushPose();
        pose.mulPose(Axis.ZN.rotationDegrees(180));

        if (showBlade || ctx == ItemDisplayContext.NONE)
            renderBlade(lowerStack, stack, buffer, pose, lowerHeight);

        lowerHeight = renderPart(lower.emitter(), lowerHeight, (byte) 1, ctx, pose, buffer, light);
        lowerHeight = renderPart(lower.switch_(), lowerHeight, (byte) 1, ctx, pose, buffer, light);
        renderPart(lower.hilt(), lowerHeight, (byte) 1, ctx, pose, buffer, light);
        pose.popPose();
    }

    public void renderSingle(ItemDisplayContext ctx,
                              PoseStack pose,
                              MultiBufferSource buffer,
                              int light,
                              ItemStack stack) {

        var data = stack.get(LightsaberDataComponents.LIGHTSABER);
        if (data == null) return;

        boolean active = Boolean.TRUE.equals(stack.get(LightsaberDataComponents.LIGHTSABER_ACTIVE));
        float height = totalHeight(data);

        boolean showBlade = false;

        pose.pushPose();

        switch (ctx) {
            case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND -> {
                pose.translate(
                        0.5,
                        0.53 - (heightOf(data.hilt()) +
                                heightOf(data.switch_()) / 2f +
                                heightOf(data.emitter())),
                        0.55
                );
                showBlade = active;
            }
            case FIRST_PERSON_RIGHT_HAND -> {
                pose.translate(1, 0.1, 0);
                showBlade = active;
            }
            case FIRST_PERSON_LEFT_HAND -> showBlade = active;
            case GUI -> {
                pose.mulPose(Axis.ZN.rotationDegrees(45));
                pose.scale(1.9f, 1.9f, 1.9f);
            }
            case FIXED -> {
                pose.mulPose(Axis.ZN.rotationDegrees(-45));
                pose.mulPose(Axis.YP.rotationDegrees(180));
                pose.translate(-0.7, -0.5, -0.5);
                pose.scale(1.9f, 1.9f, 1.9f);
            }
            case GROUND -> {
                pose.translate(0.25, 0.5, 0.5);
                pose.mulPose(Axis.ZN.rotationDegrees(45));
            }
        }

        if (showBlade || ctx == ItemDisplayContext.NONE)
            renderBlade(stack, stack, buffer, pose, height);

        height = renderPart(data.emitter(), height, (byte) 1, ctx, pose, buffer, light);
        height = renderPart(data.switch_(), height, (byte) 1, ctx, pose, buffer, light);
        height = renderPart(data.hilt(), height, (byte) 1, ctx, pose, buffer, light);
        renderPart(data.pomel(), height, (byte) -1, ctx, pose, buffer, light);

        pose.popPose();
    }

    private void renderBlade(ItemStack bladeStack,
                             ItemStack parentStack,
                             MultiBufferSource buffer,
                             PoseStack pose,
                             float height) {

        var data = bladeStack.get(LightsaberDataComponents.LIGHTSABER);
        if (data == null) return;

        boolean active = Boolean.TRUE.equals(parentStack.get(LightsaberDataComponents.LIGHTSABER_ACTIVE));
        if (!active) return;

        float length = parentStack.getOrDefault(LightsaberDataComponents.LIGHTSABER_LENGTH, 1.0F);
        if (length <= 0.1F) return;

        // Focusing crystals
        FocusingCrystal c1 = FocusingCrystal.NONE;
        FocusingCrystal c2 = FocusingCrystal.NONE;

        ResourceLocation f1 = ResourceLocation.tryParse(data.focus1());
        if (f1 != null) {
            Item item = BuiltInRegistries.ITEM.get(f1);
            if (item instanceof ItemFocusingCrystal fc) c1 = fc.getFocusingCrystal();
        }

        ResourceLocation f2 = ResourceLocation.tryParse(data.focus2());
        if (f2 != null) {
            Item item = BuiltInRegistries.ITEM.get(f2);
            if (item instanceof ItemFocusingCrystal fc) c2 = fc.getFocusingCrystal();
        }

        // Kyber color
        ResourceLocation kyberId = ResourceLocation.tryParse(data.kyber());
        if (kyberId == null) return;

        Block block = BuiltInRegistries.BLOCK.get(kyberId);
        if (!(block instanceof BlockCrystal crystal)) return;

        float[] rgb = crystal.getCrystalColor().getRGB();

        // Outer blade
        pose.pushPose();
        pose.scale(1.2f, 1f, 1.2f);
        pose.translate(0, height, 0);

        ModelLightsaberBlade.renderOuter(
                rgb,
                buffer.getBuffer(RenderType.entityTranslucentEmissive(
                        ResourceLocation.fromNamespaceAndPath(Lightsabers.MODID, "textures/item/lightsaber/blade")
                )),
                false,
                pose,
                0x0,
                c1,
                c2,
                length
        );
        pose.popPose();

        // Inner blade
        pose.pushPose();
        pose.scale(0.5f, 0.95f * length, 0.5f);
        pose.translate(0, height * 1.06, 0);

        ModelLightsaberBlade.renderInner(
                rgb,
                buffer.getBuffer(RenderType.solid()),
                false,
                pose,
                0x0,
                c1,
                c2
        );
        pose.popPose();

        // Cracked blade lightning (KR3 + L3 + LS1.5)
        pose.pushPose();
        pose.translate(0, height, 0);

        ModelLightsaberBlade.renderCrackedLightning(
                rgb,
                buffer.getBuffer(RenderType.entityTranslucentEmissive(
                        ResourceLocation.fromNamespaceAndPath(Lightsabers.MODID, "textures/item/lightsaber/blade")
                )),
                pose,
                length,
                c1,
                c2
        );

        pose.popPose();
    }

    private float renderPart(String idStr,
                             float height,
                             byte yDir,
                             ItemDisplayContext ctx,
                             PoseStack pose,
                             MultiBufferSource buffer,
                             int light) {

        if (idStr == null || idStr.isEmpty()) return height;

        ResourceLocation id = ResourceLocation.tryParse(idStr);
        if (id == null) return height;

        Item item = BuiltInRegistries.ITEM.get(id);
        if (!(item instanceof LightsaberPart part)) return height;

        pose.pushPose();

        height -= part.getHeight();

        BakedModel model = renderItem.getModel(part.getDefaultInstance(), null, null, 0);
        pose.translate(0, yDir * height, 0);

        renderItem.render(
                part.getDefaultInstance(),
                ctx,
                false,
                pose,
                buffer,
                light,
                OverlayTexture.NO_OVERLAY,
                model
        );

        pose.popPose();
        return height;
    }
}
