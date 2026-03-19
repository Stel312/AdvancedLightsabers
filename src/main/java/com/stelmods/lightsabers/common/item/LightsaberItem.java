package com.stelmods.lightsabers.common.item;

import com.stelmods.lightsabers.client.render.item.RenderItemLightsaber;
import com.stelmods.lightsabers.common.component.LightsaberDataComponents;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class LightsaberItem extends SwordItem implements IClientItemExtensions {

    public LightsaberItem() {
        super(Tiers.NETHERITE,  new Item.Properties().stacksTo(1));
    }

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return RenderItemLightsaber.BEWLR;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(!stack.has(LightsaberDataComponents.LIGHTSABER_LENGTH)){
            stack.set(LightsaberDataComponents.LIGHTSABER_ACTIVE, false);
            stack.set(LightsaberDataComponents.LIGHTSABER_LENGTH, 0f);
        }

        float len = stack.get(LightsaberDataComponents.LIGHTSABER_LENGTH);
        if(!stack.get(LightsaberDataComponents.LIGHTSABER_ACTIVE))
        {
            if(len > 0){
                stack.set(LightsaberDataComponents.LIGHTSABER_LENGTH, Math.max(len - 0.2f, 0));
            }
        }else{
            if(len < 1){
                stack.set(LightsaberDataComponents.LIGHTSABER_LENGTH, Math.min(len + 0.3f, 1));
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if(!stack.get(LightsaberDataComponents.LIGHTSABER_ACTIVE))
        {
            stack.set(LightsaberDataComponents.LIGHTSABER_ACTIVE, false);
            stack.set(LightsaberDataComponents.LIGHTSABER_LENGTH, 0f);
        }
        return super.onEntityItemUpdate(stack, entity);
    }
}
