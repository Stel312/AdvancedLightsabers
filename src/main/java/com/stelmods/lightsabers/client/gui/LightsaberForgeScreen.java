package com.stelmods.lightsabers.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.client.render.item.RenderItemLightsaber;
import com.stelmods.lightsabers.common.container.LightsaberForgeContainer;
import com.stelmods.lightsabers.common.item.LightsaberDoubleItem;
import com.stelmods.lightsabers.common.item.LightsaberItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class LightsaberForgeScreen extends AbstractContainerScreen<LightsaberForgeContainer> {

    private static final ResourceLocation SINGLE_GUI =
            ResourceLocation.fromNamespaceAndPath(Lightsabers.MODID, "textures/gui/container/lightsaber_forge.png");

    private static final ResourceLocation DOUBLE_GUI =
            ResourceLocation.fromNamespaceAndPath(Lightsabers.MODID, "textures/gui/container/lightsaber_forge_double.png");

    private static final ResourceLocation GHOSTS =
            ResourceLocation.fromNamespaceAndPath(Lightsabers.MODID, "textures/gui/container/lightsaber_ghosts.png");

    private static final ResourceLocation ARROWS =
            ResourceLocation.fromNamespaceAndPath(Lightsabers.MODID, "textures/gui/container/lightsaber_arrows.png");

    private float rotate = 0;
    private int arrowFrame = 0;

    public LightsaberForgeScreen(LightsaberForgeContainer menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 175;
        this.imageHeight = 195;
        this.height = 195;
        this.init();
    }

    // ------------------------------------------------------------
    // Dynamic Mode Label
    // ------------------------------------------------------------
    private Component getModeLabel() {
        ItemStack out = menu.getOutputSlot().getItem();
        ItemStack s1 = menu.focusCrystal1.getItem();
        ItemStack s2 = menu.focusCrystal2.getItem();

        if (!out.isEmpty()) {
            if (out.getItem() instanceof LightsaberDoubleItem)
                return Component.literal("Ready: Double Saber");
            if (out.getItem() instanceof LightsaberItem)
                return Component.literal("Ready: Single Saber");
        }

        if (s1.getItem() instanceof LightsaberItem && s2.getItem() instanceof LightsaberItem)
            return Component.literal("Combine Mode");

        if (s1.getItem() instanceof LightsaberDoubleItem)
            return Component.literal("Split Mode");

        return Component.literal("Single Saber Mode");
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        super.render(gui, mouseX, mouseY, partialTicks);
        this.renderTooltip(gui, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics gui, int mouseX, int mouseY) {
        Component label = getModeLabel();
        int x = (this.imageWidth - this.font.width(label)) / 2;
        gui.drawString(this.font, label, x, 6, 0xFFFFFF);
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTicks, int mouseX, int mouseY) {

        ItemStack out = this.menu.getOutputSlot().getItem();
        boolean isDouble = out.getItem() instanceof LightsaberDoubleItem;

        ResourceLocation tex = isDouble ? DOUBLE_GUI : SINGLE_GUI;

        this.minecraft.getTextureManager().bindForSetup(tex);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        gui.blit(tex, this.leftPos, this.topPos, 0, 0, imageWidth, 195);

        drawGhosts(gui, isDouble);
        drawArrows(gui, isDouble);
        drawPreview(gui, out, isDouble);
    }

    // ------------------------------------------------------------
    // Ghost Slot Overlays
    // ------------------------------------------------------------
    private void drawGhosts(GuiGraphics gui, boolean isDouble) {
        gui.pose().pushPose();
        this.minecraft.getTextureManager().bindForSetup(GHOSTS);

        // Focus slots (dual-purpose)
        if (menu.focusCrystal1.getItem().isEmpty()) {
            gui.blit(GHOSTS, leftPos + 89, topPos + 71, 0, 0, 16, 16);
        }
        if (menu.focusCrystal2.getItem().isEmpty()) {
            gui.blit(GHOSTS, leftPos + 107, topPos + 71, 16, 0, 16, 16);
        }

        // Output ghost
        if (menu.getOutputSlot().getItem().isEmpty()) {
            if (isDouble) {
                gui.blit(GHOSTS, leftPos + 136, topPos + 87, 32, 0, 16, 16);
            } else {
                gui.blit(GHOSTS, leftPos + 136, topPos + 87, 48, 0, 16, 16);
            }
        }

        gui.pose().popPose();
    }

    // ------------------------------------------------------------
    // Animated Combine / Split Arrows
    // ------------------------------------------------------------
    private void drawArrows(GuiGraphics gui, boolean isDouble) {
        gui.pose().pushPose();
        this.minecraft.getTextureManager().bindForSetup(ARROWS);

        arrowFrame = (arrowFrame + 1) % 4;
        int frameX = arrowFrame * 24;

        if (isDouble) {
            // Split arrow
            gui.blit(ARROWS, leftPos + 60, topPos + 40, frameX, 16, 24, 16);
        } else {
            // Combine arrow
            gui.blit(ARROWS, leftPos + 60, topPos + 40, frameX, 0, 24, 16);
        }

        gui.pose().popPose();
    }

    // ------------------------------------------------------------
    // 3D Preview Rendering
    // ------------------------------------------------------------
    private void drawPreview(GuiGraphics gui, ItemStack out, boolean isDouble) {
        if (out.isEmpty()) return;

        PoseStack pose = gui.pose();
        pose.pushPose();

        if (isDouble) {
            pose.translate(leftPos + 90, topPos + 36, 0);
            pose.mulPose(Axis.ZP.rotationDegrees(90));
            pose.mulPose(Axis.YP.rotationDegrees(rotate = (rotate % 360) + 2f));
            pose.scale(85, 85, 85);

            gui.enableScissor(leftPos + 12, topPos + 16, leftPos + 157, topPos + 63);

            Minecraft.getInstance().gameRenderer.lightTexture().turnOnLightLayer();
            RenderItemLightsaber.BEWLR.renderDouble(ItemDisplayContext.NONE, pose, gui.bufferSource(), 0xFFFFFF, out);
            RenderSystem.disableCull();
            gui.disableScissor();

        } else if (out.getItem() instanceof LightsaberItem) {
            pose.translate(leftPos + 150, topPos + 36, 0);
            pose.mulPose(Axis.ZP.rotationDegrees(90));
            pose.mulPose(Axis.YP.rotationDegrees(rotate = (rotate % 360) + 2f));
            pose.scale(110, 110, 110);

            gui.enableScissor(leftPos + 42, topPos + 20, leftPos + 156, topPos + 64);

            RenderItemLightsaber.BEWLR.renderSingle(ItemDisplayContext.NONE, pose, gui.bufferSource(), 0xFFFFFF, out);
            RenderSystem.disableCull();
            gui.disableScissor();
        }

        pose.popPose();
    }
}
