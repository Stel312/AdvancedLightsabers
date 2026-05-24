package com.stelmods.lightsabers.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stelmods.lightsabers.client.model.ModelLightsaberBlade;
import com.stelmods.lightsabers.client.render.ModelRenderTypes;
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

import java.util.Random;

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

        if (!(stack.getItem() instanceof LightsaberItem))
            return;

        if (stack.has(LightsaberDataComponents.LIGHTSABER)) {
            renderSingle(ctx, pose, buffer, light, stack);
        } else if (stack.has(LightsaberDataComponents.DOUBLE_LIGHTSABER)) {
            renderDouble(ctx, pose, buffer, light, stack);
        }
    }

    private float heightOf(String id) {
        if (id == null || id.isEmpty()) return 0f;
        ResourceLocation rl = ResourceLocation.tryParse(id);
        if (rl == null) return 0f;

        Item item = BuiltInRegistries.ITEM.get(rl);
        return item instanceof LightsaberPart part ? part.getHeight() : 0f;
    }

    private float emitterTop(LightsaberDataComponents.LightsaberData d) {
        return heightOf(d.pomel()) +
                heightOf(d.hilt()) +
                heightOf(d.switch_()) +
                heightOf(d.emitter());
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

        boolean showBlade = false;

        switch (ctx) {
            case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND -> {
                pose.translate(0.5, 0.40, 0.55);
                showBlade = true;
            }
            case FIRST_PERSON_RIGHT_HAND -> {
                pose.translate(0.75, 0.03, 0);
                showBlade = true;
            }
            case FIRST_PERSON_LEFT_HAND -> {
                pose.translate(-0.75, 0.03, 0);
                showBlade = true;
            }
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

        ItemStack upperStack = stack.copy();
        upperStack.set(LightsaberDataComponents.LIGHTSABER, upper);

        ItemStack lowerStack = stack.copy();
        lowerStack.set(LightsaberDataComponents.LIGHTSABER, lower);

        pose.pushPose();
        float h = 0f;

        h = renderPart(upper.hilt(),   h, (byte) 1, ctx, pose, buffer, light);
        h = renderPart(upper.switch_(), h, (byte) 1, ctx, pose, buffer, light);
        h = renderPart(upper.emitter(), h, (byte) 1, ctx, pose, buffer, light);
        if (showBlade || ctx == ItemDisplayContext.NONE) {
            pose.pushPose();
            pose.translate(0, emitterTop(upper), 0);
            renderBlade(upperStack, stack, buffer, pose, 0, false, ctx == ItemDisplayContext.NONE);
            pose.popPose();
        }

        pose.popPose();

        pose.pushPose();
        pose.mulPose(Axis.ZN.rotationDegrees(180));

        h = 0f;
        if (showBlade || ctx == ItemDisplayContext.NONE) {
            pose.pushPose();
            pose.translate(0, emitterTop(lower), 0);
            renderBlade(lowerStack, stack, buffer, pose, 0, true, ctx == ItemDisplayContext.NONE);
            pose.popPose();
        }
        h = renderPart(lower.hilt(),   h, (byte) 1, ctx, pose, buffer, light);
        h = renderPart(lower.switch_(), h, (byte) 1, ctx, pose, buffer, light);
        h = renderPart(lower.emitter(), h, (byte) 1, ctx, pose, buffer, light);


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
        boolean showBlade = false;

        // ⭐ Save item transform BEFORE part transforms


        pose.pushPose();

        switch (ctx) {
            case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND -> {
                float midOffset = heightOf(data.hilt()) / 2f;
                pose.translate(0.5, 0.53 - midOffset, 0.55);
                showBlade = active;
            }
            case FIRST_PERSON_RIGHT_HAND -> {
                pose.translate(0.75, 0.15, 0);
                showBlade = active;
            }
            case FIRST_PERSON_LEFT_HAND -> {
                pose.translate(-0.75, 0.15, 0);
                showBlade = active;
            }
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

        float h = 0f;

        h = renderPart(data.pomel(),  h, (byte) 1, ctx, pose, buffer, light);
        h = renderPart(data.hilt(),   h, (byte) 1, ctx, pose, buffer, light);
        h = renderPart(data.switch_(),h, (byte) 1, ctx, pose, buffer, light);
        h = renderPart(data.emitter(),h, (byte) 1, ctx, pose, buffer, light);

        // ⭐ Render blade using the ORIGINAL item transform
        if (showBlade || ctx == ItemDisplayContext.NONE) {
            pose.pushPose();
            pose.translate(0, emitterTop(data), 0);
            renderBlade(stack, stack, buffer, pose, 0, false, ctx == ItemDisplayContext.NONE);
            pose.popPose();
        }
        pose.popPose();


    }


    private void renderBlade(ItemStack bladeStack,
                             ItemStack parentStack,
                             MultiBufferSource buffer,
                             PoseStack pose,
                             float ignoredHeight,
                             boolean isLowerHalf,
                             boolean isGuiPreview) {

        var data = bladeStack.get(LightsaberDataComponents.LIGHTSABER);
        if (data == null) return;

        boolean active = Boolean.TRUE.equals(parentStack.get(LightsaberDataComponents.LIGHTSABER_ACTIVE));
        if (!active && !isGuiPreview) return;

        float baseLength = parentStack.getOrDefault(LightsaberDataComponents.LIGHTSABER_LENGTH, 1.0F);
        if (isGuiPreview && baseLength <= 0.1F) baseLength = 1.0F;
        float length = isLowerHalf ? baseLength * 1.05f : baseLength;

        if (length <= 0.1F) return;

        FocusCrystalState crystals = resolveFocusCrystals(data);

        // Kyber color
        ResourceLocation kyberId = ResourceLocation.tryParse(data.kyber());
        if (kyberId == null) return;

        Block block = BuiltInRegistries.BLOCK.get(kyberId);
        if (!(block instanceof BlockCrystal crystal)) return;

        float[] rgb = crystal.getCrystalColor().getRGB();

        float partial = Minecraft.getInstance().getTimer().getGameTimeDeltaTicks();
        long gameTime = Minecraft.getInstance().level != null ? Minecraft.getInstance().level.getGameTime() : 0L;
        float t = gameTime + partial;
        Random flickerRand = new Random((long)(t * 1000));

        // Default blade: white core, kyber-colored outer glow.
        boolean hasPrismatic = crystals.prismatic();
        boolean hasInverting = crystals.inverting();
        float[] innerRgb = hasPrismatic ? rgb : new float[]{1f, 1f, 1f};
        float[] outerRgb = rgb;

        RenderType innerType = hasInverting ? ModelRenderTypes.SABER_INNER_SOLID : ModelRenderTypes.SABER_INNER;
        RenderType outerType = isGuiPreview ? ModelRenderTypes.SABER_GUI : ModelRenderTypes.SABER_OUTER;

        ModelLightsaberBlade.renderInner(innerRgb, buffer.getBuffer(innerType), pose, length, flickerRand, crystals.c1(), crystals.c2());
        ModelLightsaberBlade.renderCrackedLightning(innerRgb, buffer.getBuffer(innerType), pose, length, crystals.c1(), crystals.c2());
        ModelLightsaberBlade.renderOuter(outerRgb, buffer.getBuffer(outerType), pose, length * 1.1f, flickerRand, crystals.c1(), crystals.c2());

        if (isGuiPreview) {
            ModelLightsaberBlade.renderGuiBloom(outerRgb, buffer.getBuffer(outerType), pose, length * 1.1f, crystals.c1(), crystals.c2());
        }
    }

    private static FocusCrystalState resolveFocusCrystals(LightsaberDataComponents.LightsaberData data) {
        return new FocusCrystalState(resolveCrystal(data.focus1()), resolveCrystal(data.focus2()));
    }

    private static FocusingCrystal resolveCrystal(String id) {
        ResourceLocation rl = ResourceLocation.tryParse(id);
        if (rl == null) return FocusingCrystal.NONE;

        Item item = BuiltInRegistries.ITEM.get(rl);
        return item instanceof ItemFocusingCrystal fc ? fc.getFocusingCrystal() : FocusingCrystal.NONE;
    }

    private record FocusCrystalState(FocusingCrystal c1, FocusingCrystal c2) {
        boolean prismatic() {
            return c1 == FocusingCrystal.PRISMATIC || c2 == FocusingCrystal.PRISMATIC;
        }

        boolean inverting() {
            return c1 == FocusingCrystal.INVERTING || c2 == FocusingCrystal.INVERTING;
        }
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

        float partHeight = part.getHeight();

        pose.pushPose();
        pose.translate(0, yDir * height, 0);

        BakedModel model = renderItem.getModel(part.getDefaultInstance(), null, null, 0);

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
        return height + partHeight;
    }


}
