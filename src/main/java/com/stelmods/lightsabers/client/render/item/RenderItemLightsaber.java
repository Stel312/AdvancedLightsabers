package com.stelmods.lightsabers.client.render.item;

import com.mojang.blaze3d.platform.Lighting;
import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.client.model.ModelLightsaberBlade;
import com.stelmods.lightsabers.common.item.ItemFocusingCrystal;
import com.stelmods.lightsabers.common.block.BlockCrystal;
import com.stelmods.lightsabers.common.item.LightsaberPart;
import com.stelmods.lightsabers.common.lightsaber.FocusingCrystal;
import com.stelmods.lightsabers.common.lightsaber.LightsaberType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class RenderItemLightsaber extends BlockEntityWithoutLevelRenderer // implements IItemRenderer
{
    private ItemRenderer renderItem;
    public static final RenderItemLightsaber bewlr = new RenderItemLightsaber(Minecraft.getInstance().getBlockEntityRenderDispatcher(),Minecraft.getInstance().getEntityModels());
    public RenderItemLightsaber(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
        super(p_172550_, p_172551_);
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext itemDisplayContext, PoseStack matrixStack, MultiBufferSource buffer, int combinedLightIn, int p_108835_) {
        super.renderByItem(itemStack, itemDisplayContext, matrixStack, buffer, combinedLightIn, p_108835_);
        this.renderItem = Minecraft.getInstance().getItemRenderer();
        CompoundTag tag = itemStack.getTag();
        String type = tag.getString("type");

        if(!type.isEmpty())
        {
            LightsaberType typeL = LightsaberType.valueOf(type);

            switch (typeL) {
                case SINGLE -> renderSingle(itemDisplayContext, matrixStack, buffer, combinedLightIn, itemStack);
                case DOUBLE -> renderDouble(itemDisplayContext, matrixStack, buffer, combinedLightIn, itemStack);
            }
        }
    }

    public static float getTotalHeight(CompoundTag tag){
        return  getHeight(tag.getString("switch")) + getHeight(tag.getString("emitter")) +
                getHeight(tag.getString("grip"))+ getHeight(tag.getString("pommel"));
    }
    public static float getHeight(String name)
    {
        LightsaberPart part = (LightsaberPart) ForgeRegistries.ITEMS.getValue(new ResourceLocation(name));
        return part.getHeight();
    }

    public void renderDouble(ItemDisplayContext itemDisplayContext, PoseStack matrixStack, MultiBufferSource buffer, int combinedLightIn,ItemStack itemStack)
    {
        CompoundTag tag = itemStack.getTag();
        CompoundTag lowerTag = tag.getCompound("lower");
        float lowerHeight = getTotalHeight(lowerTag) - getHeight(lowerTag.getString("pommel"));
        CompoundTag upperTag = tag.getCompound("upper");
        float upperHeight = getTotalHeight(upperTag) - getHeight(upperTag.getString("pommel"));
        boolean shouldRenderBlade = false;

        switch (itemDisplayContext)
        {
            case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND ->
            {
                matrixStack.translate(.5,0.5, .55);
                shouldRenderBlade = true;
            }
            case FIRST_PERSON_RIGHT_HAND -> {
                matrixStack.translate(1,.1,0);
                shouldRenderBlade = true;
            }
            case FIRST_PERSON_LEFT_HAND -> {
                shouldRenderBlade = true;
            }
            case GUI -> {
                matrixStack.translate(0.5,0.5,0  );
                matrixStack.mulPose(Axis.ZN.rotationDegrees(-45));
                matrixStack.scale(1.1f, 1.1f, 1.1f);
            }
            case FIXED -> {
                matrixStack.mulPose(Axis.ZN.rotationDegrees(-45));
                matrixStack.translate(-.7,0.5,0.5);
            }
            case GROUND -> {
                matrixStack.translate(0.5,1,0.5);
                matrixStack.mulPose(Axis.ZN.rotationDegrees(45));
            }
        }

        matrixStack.pushPose();
        if((upperTag.getBoolean("active") && shouldRenderBlade) || itemDisplayContext.equals(ItemDisplayContext.NONE)) {
            renderBlade(upperTag, buffer, matrixStack, upperHeight);
        }
        upperHeight = renderPart(upperTag.getString("emitter"),upperHeight, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        upperHeight = renderPart(upperTag.getString("switch"),upperHeight, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        renderPart(upperTag.getString("grip"),upperHeight, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        matrixStack.popPose();

        matrixStack.pushPose();
        matrixStack.mulPose(Axis.ZN.rotationDegrees(180));
        if((lowerTag.getBoolean("active") && shouldRenderBlade) || itemDisplayContext.equals(ItemDisplayContext.NONE)) {
                    renderBlade(lowerTag, buffer, matrixStack, lowerHeight);
        }
        lowerHeight = renderPart(lowerTag.getString("emitter"),lowerHeight, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        lowerHeight = renderPart(lowerTag.getString("switch"),lowerHeight, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        renderPart(lowerTag.getString("grip"),lowerHeight, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        matrixStack.popPose();
    }

    public void renderSingle(ItemDisplayContext itemDisplayContext, PoseStack matrixStack, MultiBufferSource buffer, int combinedLightIn,ItemStack itemStack)
    {
        boolean shouldRenderBlade = false;
        CompoundTag tag = itemStack.getTag();
        float height = getTotalHeight(tag);
        matrixStack.pushPose();
        switch (itemDisplayContext)
        {
            case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND -> {
                matrixStack.translate(0.5,0.53-(getHeight(tag.getString("grip"))+  getHeight(tag.getString("switch")) /2 + getHeight(tag.getString("pommel"))), 0.55);
                shouldRenderBlade = true;
            }
            case FIRST_PERSON_RIGHT_HAND -> {
                matrixStack.translate(1,.1,0);
                shouldRenderBlade = true;
            }
            case FIRST_PERSON_LEFT_HAND -> {

                shouldRenderBlade = true;
            }
            case GUI -> {
                //matrixStack.translate(0,0f,0  );
                matrixStack.mulPose(Axis.ZN.rotationDegrees(45));
                matrixStack.scale(1.9f,1.9f,1.9f);
            }
            case FIXED -> {
                matrixStack.mulPose(Axis.ZN.rotationDegrees(-45));
                matrixStack.mulPose(Axis.YP.rotationDegrees(180));
                matrixStack.translate(-.7,-0.5,-0.5);
            }
            case GROUND -> {
                matrixStack.translate(.25,0.5,0.5);
                matrixStack.mulPose(Axis.ZN.rotationDegrees(45));
            }
        }
        if((tag.getBoolean("active") && shouldRenderBlade) || itemDisplayContext.equals(ItemDisplayContext.NONE)) {
                    renderBlade(tag, buffer, matrixStack, height);
        }
        height = renderPart(tag.getString("emitter"),height, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        height = renderPart(tag.getString("switch"),height, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        height = renderPart(tag.getString("grip"),height, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        renderPart(tag.getString("pommel"), height,(byte) -1,itemDisplayContext,matrixStack,buffer,combinedLightIn);

        matrixStack.popPose();
    }

    private void renderBlade(CompoundTag tag, MultiBufferSource buffer, PoseStack matrixStack, float height)
    {
        FocusingCrystal focusingCrystal1 = FocusingCrystal.NONE;
        FocusingCrystal focusingCrystal2 = FocusingCrystal.NONE;
        if(tag.contains("focus1"))
        {
            ItemFocusingCrystal itemFocusingCrystal =  (ItemFocusingCrystal)(ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("focus1"))));
            focusingCrystal1 = itemFocusingCrystal.getFocusingCrystal();
        }
        if(tag.contains("focus2"))
        {
            ItemFocusingCrystal itemFocusingCrystal =  (ItemFocusingCrystal)(ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("focus2"))));
            focusingCrystal2 = itemFocusingCrystal.getFocusingCrystal();
        }
        matrixStack.pushPose();
        BlockCrystal i  = (BlockCrystal) ForgeRegistries.BLOCKS.getValue(new ResourceLocation(tag.getString("color")));
        float[] rgb = i.getCrystalColor().getRGB();
        matrixStack.scale(1.4f, 1f, 1.4f);
        matrixStack.translate(0, height * 1, 0);
        ModelLightsaberBlade.renderOuter(rgb, buffer.getBuffer(
                RenderType.entityTranslucentEmissive(new ResourceLocation(Lightsabers.MODID, "textures/item/lightsaber/blade.png"), false)
        ), false, matrixStack, 0X0, focusingCrystal1, focusingCrystal2);
        matrixStack.popPose();

        //render inner blade
        matrixStack.pushPose();
        matrixStack.scale(.5f, .95f, .5f);
        matrixStack.translate(0, height * 1.0, 0);
        ModelLightsaberBlade.renderInner(rgb, buffer.getBuffer(RenderType.solid()),
                false, matrixStack, 0X0, focusingCrystal1, focusingCrystal2);

        matrixStack.popPose();
    }
    private float renderPart(String name,float height, byte y, ItemDisplayContext itemDisplayContext, PoseStack matrixStack, MultiBufferSource buffer, int combinedLightIn)
    {
        matrixStack.pushPose();
        LightsaberPart part = (LightsaberPart) ForgeRegistries.ITEMS.getValue(new ResourceLocation(name));
        height = height - part.getHeight();
        BakedModel bm = renderItem.getModel(part.getDefaultInstance(), null, null, 1);
        matrixStack.translate(0,y*height,0);
        renderItem.render(part.getDefaultInstance(), itemDisplayContext, false, matrixStack, buffer, combinedLightIn, OverlayTexture.NO_OVERLAY, bm);
        matrixStack.popPose();
        return height;
    }
}
