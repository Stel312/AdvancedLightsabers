package com.stelmods.lightsabers.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.client.render.item.RenderItemLightsaber;
import com.stelmods.lightsabers.common.container.LightsaberForgeContainer;
import com.stelmods.lightsabers.common.item.LightsaberItem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class LightsaberForgeScreen extends AbstractContainerScreen<LightsaberForgeContainer> {
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(Lightsabers.MODID, "textures/gui/container/lightsaber_forge.png");
    private float rotate = 0;

    public LightsaberForgeScreen(LightsaberForgeContainer menu, Inventory inventoryPlayer, Component component /*, TileEntityLightsaberForge tile */) {
        super(menu, inventoryPlayer, component);
        this.imageWidth = 175;
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
    protected void renderBg(GuiGraphics gui, float partialTicks, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bindForSetup(GUI_TEXTURES);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        gui.blit(GUI_TEXTURES, this.leftPos, this.topPos, 0, 0, imageWidth, 195);
        ItemStack itemStack = this.menu.getOutputSlot().getItem().copy();

        if(itemStack.getItem() instanceof LightsaberItem && itemStack.getItem() != Items.AIR){
            PoseStack matrixstack = gui.pose();
            matrixstack.pushPose();

            matrixstack.translate(this.leftPos + 150    , this.topPos + 36, 0);
            matrixstack.mulPose(Axis.ZP.rotationDegrees(90));
            matrixstack.mulPose(Axis.YP.rotationDegrees(rotate = (rotate % 360) + 2f));
            matrixstack.scale(75,75,75);
            gui.enableScissor(this.leftPos + 42, this.topPos + 20, this.leftPos + 156, this.topPos + 64);
            RenderItemLightsaber.bewlr.renderSingle(ItemDisplayContext.NONE, matrixstack, gui.bufferSource(), 0x0, itemStack);
            gui.disableScissor();
            RenderSystem.enableCull();

            matrixstack.popPose();
        }
    }
}
