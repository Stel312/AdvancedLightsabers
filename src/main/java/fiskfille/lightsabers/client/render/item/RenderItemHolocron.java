package fiskfille.lightsabers.client.render.item;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import fiskfille.lightsabers.common.tileentity.TileEntityHolocron;

public class RenderItemHolocron implements IItemRenderer
{
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return type != type.INVENTORY;
	}

	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}

	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		if (type == type.ENTITY)
		{
			GL11.glTranslatef(-0.5F, -0.25F, -0.5F);
		}
		else if (type == type.EQUIPPED)
		{
			GL11.glTranslatef(0.25F, 0.25F, 0.25F);
		}
		else if (type == type.EQUIPPED_FIRST_PERSON || type == type.FIRST_PERSON_MAP)
		{
			GL11.glTranslatef(0.25F, 0.25F, 0.25F);
		}
		
		TileEntityHolocron tile = new TileEntityHolocron();
		tile.blockMetadata = item.getItemDamage();
		TileEntityRendererDispatcher.instance.renderTileEntityAt(tile, 0.0F, 0.0F, 0.0F, 0.0F);
	}
}
