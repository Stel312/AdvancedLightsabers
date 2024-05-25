package com.stelmods.lightsabers.client.render.tile;

import com.stelmods.lightsabers.common.tileentity.LightsaberForgeBlockEntity;
import org.lwjgl.opengl.GL11;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.client.model.tile.ModelLightsaberForge;
import com.stelmods.lightsabers.common.block.BlockLightsaberForge;
import com.stelmods.lightsabers.common.block.ModBlocks;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderLightsaberForge extends TileEntitySpecialRenderer
{
    private final ResourceLocation textureLight = new ResourceLocation(Lightsabers.MODID, "textures/models/lightsaber_forge_light.png");
    private final ResourceLocation textureDark = new ResourceLocation(Lightsabers.MODID, "textures/models/lightsaber_forge_dark.png");
    private final ModelLightsaberForge model = new ModelLightsaberForge();

    public void render(LightsaberForgeBlockEntity tile, double x, double y, double z, float partialTicks)
    {
        int metadata = 0;

        if (tile.getWorldObj() != null)
        {
            metadata = tile.getBlockMetadata();
        }

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(1F, -1F, -1F);
        GL11.glRotatef(BlockLightsaberForge.getDirection(metadata) * 90 + 180, 0.0F, 1.0F, 0.0F);

        if (!BlockLightsaberForge.isBlockSideOfPanel(metadata))
        {
            bindTexture(tile.getBlockType() == ModBlocks.lightsaberForgeDark ? textureDark : textureLight);
            model.render();
        }

        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTicks)
    {
        render((LightsaberForgeBlockEntity) tileentity, x, y, z, partialTicks);
    }
}