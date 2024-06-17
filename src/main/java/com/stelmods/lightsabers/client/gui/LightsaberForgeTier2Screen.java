package com.stelmods.lightsabers.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.client.render.item.RenderItemLightsaber;
import com.stelmods.lightsabers.common.container.LightsaberForgeTier2Container;
import com.mojang.blaze3d.systems.RenderSystem;
import com.stelmods.lightsabers.common.item.LightsaberDoubleItem;
import com.stelmods.lightsabers.common.item.LightsaberItem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class LightsaberForgeTier2Screen extends AbstractContainerScreen<LightsaberForgeTier2Container> {
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(Lightsabers.MODID, "textures/gui/container/lightsaber_forge_t2.png");
    private float rotate = 0;
    public LightsaberForgeTier2Screen(LightsaberForgeTier2Container menu, Inventory inventoryPlayer, Component component /*, TileEntityLightsaberForge tile */) {
        super(menu, inventoryPlayer, component);
        this.imageWidth = 193;
        this.imageHeight = 195;
        this.height = 195;
        this.init();

    }

    @Override
    public void render(GuiGraphics guiGraphics, int p_283661_, int p_281248_, float p_281886_) {
        super.render(guiGraphics, p_283661_, p_281248_, p_281886_);
        this.renderTooltip(guiGraphics, p_283661_, p_281248_);
    }

    @Override
    protected void renderLabels(GuiGraphics p_281635_, int mouseX, int mouseY) {

    }

    @Override
    protected void renderBg(GuiGraphics gui, float p_97788_, int p_97789_, int p_97790_) {
        this.minecraft.getTextureManager().bindForSetup(GUI_TEXTURES);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        gui.blit(GUI_TEXTURES, this.leftPos, this.topPos, 0, 0, imageWidth, 195);
        ItemStack itemStack = this.menu.getDualSlot().getItem();
        if(itemStack.getItem() instanceof LightsaberDoubleItem){
            PoseStack matrixstack = gui.pose();
            matrixstack.pushPose();
            matrixstack.translate(this.leftPos + 115, this.topPos + 36, 0);
            matrixstack.mulPose(Axis.ZP.rotationDegrees(90));
            matrixstack.mulPose(Axis.YP.rotationDegrees(rotate = (rotate % 360) + 2f));
            matrixstack.scale(65L,65,65);
            gui.enableScissor(this.leftPos + 60, this.topPos + 16, this.leftPos + 174, this.topPos + 64);
            RenderItemLightsaber.bewlr.renderDouble(ItemDisplayContext.NONE, matrixstack, gui.bufferSource(), 0x0, itemStack);
            gui.disableScissor();
            RenderSystem.enableCull();
            matrixstack.popPose();
        }
    }
}