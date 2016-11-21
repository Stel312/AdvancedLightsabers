package fiskfille.lightsabers.client.model;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import fiskfille.lightsabers.common.config.ModConfig;
import fiskfille.lightsabers.common.helper.ALRenderHelper;
import fiskfille.lightsabers.common.helper.FocusingCrystals;
import fiskfille.lightsabers.common.helper.LightsaberHelper;

public class ModelLightsaberBlade extends ModelBase
{
	public ModelRenderer blade;
	public int bladeLength;

	public ModelLightsaberBlade(int length)
	{
		textureWidth = 64;
		textureHeight = 32;
		blade = new ModelRenderer(this, 0, 0);
		blade.addBox(-0.5F, -length, -0.5F, 1, length, 1);
		bladeLength = length;
	}

	public void renderInner(ItemStack stack)
	{
		if (LightsaberHelper.hasFocusingCrystal(stack, FocusingCrystals.INVERTING_FOCUSING_CRYSTAL))
		{
			GL11.glColor4f(0, 0, 0, 1);
		}
		else
		{
			GL11.glColor4f(1, 1, 1, 1);
		}
		
		if (LightsaberHelper.hasFocusingCrystal(stack, FocusingCrystals.COMPRESSED_FOCUSING_CRYSTAL))
		{
			GL11.glScalef(0.6F, 1, 0.6F);
		}
		
		if (LightsaberHelper.hasFocusingCrystal(stack, FocusingCrystals.CRACKED_KYBER_CRYSTAL))
		{
			int ticks = (Minecraft.getMinecraft().thePlayer.ticksExisted) % 100;
			float divider = 60;
			Random rand = new Random(ticks * 1000);

			for (int i = 0; i < 4; ++i)
			{
				GL11.glPushMatrix();

				if (i != 0)
				{
					GL11.glTranslatef((rand.nextFloat() - 0.5F) / divider, 0, (rand.nextFloat() - 0.5F) / divider);

					for (int j = 0; j < bladeLength; ++j)
					{
						GL11.glPushMatrix();
						GL11.glRotatef(rand.nextInt(360), 0, 1, 0);
						GL11.glRotatef(90, 1, 0, 0);
						GL11.glTranslatef(0, -0.0625F + 0.05F + (rand.nextFloat() * 0.0125F), 0.0625F + (rand.nextFloat() * bladeLength * 0.0625F));
						ALRenderHelper.drawTip(0.04F, 0.0F);
						GL11.glPopMatrix();
					}
				}

				if (!LightsaberHelper.hasFocusingCrystal(stack, FocusingCrystals.FINE_CUT_FOCUSING_CRYSTAL))
				{
					blade.render(0.0625F);
					GL11.glTranslatef(0, -0.0625F * (0.5F + bladeLength), 0.0625F / 2);
					ALRenderHelper.drawTip(0.03125F, 0.125F);
				}
				
				GL11.glPopMatrix();
			}
		}
		
		if (LightsaberHelper.hasFocusingCrystal(stack, FocusingCrystals.FINE_CUT_FOCUSING_CRYSTAL))
		{
	        Tessellator tessellator = Tessellator.instance;
	        float f = 0.0625F;
	        float length = f * bladeLength * 0.7F;
	        float edge = f * 1.5F;
	        float edgeAngle = -f * 1.5F;
	        float length1 = f * bladeLength * 0.3F;
	        float edge1 = f / 2;
	        float tip = f * 1.5F;
	        
	        tessellator.startDrawingQuads();
	        tessellator.addVertex(-f / 2, -length, f / 2);
	        tessellator.addVertex(0, -length, edge);
	        tessellator.addVertex(0, edgeAngle, edge);
	        tessellator.addVertex(-f / 2, -f, f / 2);
	        tessellator.addVertex(f / 2, -length, f / 2);
	        tessellator.addVertex(0, -length, edge);
	        tessellator.addVertex(0, edgeAngle, edge);
	        tessellator.addVertex(f / 2, -f, f / 2);
	        tessellator.addVertex(f / 2, -f, f / 2);
	        tessellator.addVertex(0, edgeAngle, edge);
	        tessellator.addVertex(0, edgeAngle, edge);
	        tessellator.addVertex(-f / 2, -f, f / 2);
	        tessellator.addVertex(-f / 2, 0 - length, f / 2);
	        tessellator.addVertex(-f / 2, -length1 - length, edge1);
	        tessellator.addVertex(0, -length1 - length, edge1);
	        tessellator.addVertex(0, 0 - length, edge);
	        tessellator.addVertex(f / 2, 0 - length, f / 2);
	        tessellator.addVertex(f / 2, -length1 - length, edge1);
	        tessellator.addVertex(0, -length1 - length, edge1);
	        tessellator.addVertex(0, 0 - length, edge);
	        tessellator.addVertex(-f / 2, 0 - f * bladeLength, f / 2);
	        tessellator.addVertex(0, -tip - f * bladeLength, -f / 2);
	        tessellator.addVertex(0, -tip - f * bladeLength, -f / 2);
	        tessellator.addVertex(-f / 2, 0 - f * bladeLength, -f / 2);
	        tessellator.addVertex(f / 2, 0 - f * bladeLength, f / 2);
	        tessellator.addVertex(0, -tip - f * bladeLength, -f / 2);
	        tessellator.addVertex(0, -tip - f * bladeLength, -f / 2);
	        tessellator.addVertex(f / 2, 0 - f * bladeLength, -f / 2);
	        tessellator.addVertex(-f / 2, 0 - f * bladeLength, -f / 2);
	        tessellator.addVertex(0, -tip - f * bladeLength, -f / 2);
	        tessellator.addVertex(0, -tip - f * bladeLength, -f / 2);
	        tessellator.addVertex(f / 2, 0 - f * bladeLength, -f / 2);
	        tessellator.addVertex(-f / 2, 0 - f * bladeLength, f / 2);
	        tessellator.addVertex(0, -tip - f * bladeLength, -f / 2);
	        tessellator.addVertex(0, -tip - f * bladeLength, -f / 2);
	        tessellator.addVertex(f / 2, 0 - f * bladeLength, f / 2);
	        tessellator.draw();
	        
			blade.render(0.0625F);
		}
		else
		{
			blade.render(0.0625F);
			GL11.glTranslatef(0, -0.0625F * (0.5F + bladeLength), 0.0625F / 2);
			ALRenderHelper.drawTip(0.03125F, 0.125F);
		}
	}

	public void renderOuter(ItemStack itemstack, float r, float g, float b, boolean flag)
	{
		int smooth = 10;
		float width = 0.6F;
		float f = 1;
		float f1 = 1;
		float f2 = 1;
		float f3 = 0.1F;
		
		if (LightsaberHelper.hasFocusingCrystal(itemstack, FocusingCrystals.COMPRESSED_FOCUSING_CRYSTAL))
		{
			width = 0.4F;
			smooth = 7;
			f3 = 0.07F;
		}
		
		if (LightsaberHelper.hasFocusingCrystal(itemstack, FocusingCrystals.FINE_CUT_FOCUSING_CRYSTAL))
		{
			f *= 0.55F;
			f1 *= 0.925F;
			f2 *= 1.1F;
		}
		
		if (flag)
		{
			width *= ModConfig.renderGlobalMultiplier * ModConfig.renderWidthMultiplier;
			smooth *= ModConfig.renderGlobalMultiplier * ModConfig.renderSmoothingMultiplier;
		}
		
		if (itemstack.getDisplayName().equals("jeb_"))
		{
			smooth *= 0.25F;
		}
		
		int layerCount = 5 * smooth;
		float opacityMultiplier = (flag ? ModConfig.renderGlobalMultiplier * ModConfig.renderOpacityMultiplier : 1);
		
		for (int i = 0; i < layerCount; ++i)
		{
			GL11.glColor4f(r, g, b, (f3 / smooth) * opacityMultiplier);
			float scale = 1 + i * (width / smooth);
			float f4 = ((float)i / layerCount) * 50;
			
			GL11.glPushMatrix();
	        GL11.glTranslatef(blade.offsetX, blade.offsetY, blade.offsetZ);
	        GL11.glTranslatef(blade.rotationPointX * 0.0625F, blade.rotationPointY * 0.0625F, blade.rotationPointZ * 0.0625F);
	        GL11.glScaled(scale * f, (1 - f4 * (LightsaberHelper.hasFocusingCrystal(itemstack, FocusingCrystals.FINE_CUT_FOCUSING_CRYSTAL) ? 0.003F : 0.005F) + 0.2F) * f1, scale * f2);
	        GL11.glTranslatef(-blade.offsetX, -blade.offsetY, -blade.offsetZ);
	        GL11.glTranslatef(-blade.rotationPointX * 0.0625F, -blade.rotationPointY * 0.0625F, -blade.rotationPointZ * 0.0625F);
	        GL11.glTranslatef(0, -(float)f4 / 400 + 0.06F, 0);
	        
	        if (LightsaberHelper.hasFocusingCrystal(itemstack, FocusingCrystals.FINE_CUT_FOCUSING_CRYSTAL))
	        {
	        	GL11.glTranslatef(0, 0, 0.005F + f4 * 0.00001F);
	        }

	        blade.render(0.0625F);
	        GL11.glPopMatrix();
		}
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	public void renderCrossguardInner(ItemStack itemstack)
	{
		if (LightsaberHelper.hasFocusingCrystal(itemstack, FocusingCrystals.FINE_CUT_FOCUSING_CRYSTAL))
		{
			GL11.glScalef(1, 1.2F, 1);
		}
		
		renderInner(itemstack);
	}
	
	public void renderCrossguardOuter(ItemStack itemstack, float r, float g, float b, boolean flag)
	{
		int smooth = 10;
		float width = 0.4F;
		float f = 1;
		float f1 = 1;
		float f2 = 1;
		float f3 = 0.1F;
		
		if (LightsaberHelper.hasFocusingCrystal(itemstack, FocusingCrystals.COMPRESSED_FOCUSING_CRYSTAL))
		{
			width = 0.2F;
			smooth = 7;
			f1 = 0.9F;
			f3 = 0.07F;
		}
		
		if (LightsaberHelper.hasFocusingCrystal(itemstack, FocusingCrystals.FINE_CUT_FOCUSING_CRYSTAL))
		{
			f *= 0.55F;
			f1 *= 0.925F;
			f2 *= 1.3F;
		}
		
		if (flag)
		{
			width *= ModConfig.renderGlobalMultiplier * ModConfig.renderWidthMultiplier;
			smooth *= ModConfig.renderGlobalMultiplier * ModConfig.renderSmoothingMultiplier;
		}
		
		if (itemstack.getDisplayName().equals("jeb_"))
		{
			smooth *= 0.25F;
		}
		
		int layerCount = 5 * smooth;
		
		for (int i = 0; i < layerCount; ++i)
		{
			GL11.glColor4f(r, g, b, (f3 / smooth) * (flag ? ModConfig.renderGlobalMultiplier * ModConfig.renderOpacityMultiplier : 1));
			float scale = 1 + i * (width / smooth);
			float f4 = ((float)i / layerCount) * 50;
			
			GL11.glPushMatrix();
	        GL11.glTranslatef(blade.offsetX, blade.offsetY, blade.offsetZ);
	        GL11.glTranslatef(blade.rotationPointX * 0.0625F, blade.rotationPointY * 0.0625F, blade.rotationPointZ * 0.0625F);
	        GL11.glScaled(scale * f, (1 - f4 * 0.05F + 2F) * f1, scale * f2);
	        GL11.glTranslatef(-blade.offsetX, -blade.offsetY, -blade.offsetZ);
	        GL11.glTranslatef(-blade.rotationPointX * 0.0625F, -blade.rotationPointY * 0.0625F, -blade.rotationPointZ * 0.0625F);
	        GL11.glTranslatef(0, -(float)f4 / 400 + 0.06F, 0);
	        
	        if (LightsaberHelper.hasFocusingCrystal(itemstack, FocusingCrystals.FINE_CUT_FOCUSING_CRYSTAL))
	        {
//	        	GL11.glTranslatef(0, -(float)f4 / 400 + 0.026F, 0);
	        	GL11.glTranslatef(0, 0, 0.005F + f4 * 0.00001F);
	        }
	        
	        blade.render(0.0625F);
	        GL11.glPopMatrix();
		}
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
