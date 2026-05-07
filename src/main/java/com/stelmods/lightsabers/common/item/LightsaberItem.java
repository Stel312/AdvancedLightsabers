package com.stelmods.lightsabers.common.item;

import com.stelmods.lightsabers.client.render.item.RenderItemLightsaber;
import com.stelmods.lightsabers.common.component.LightsaberDataComponents;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.List;

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
                stack.set(LightsaberDataComponents.LIGHTSABER_LENGTH, Math.max(len - 0.1f, 0));
            }
        }else{
            if(len < 1){
                stack.set(LightsaberDataComponents.LIGHTSABER_LENGTH, Math.min(len + 0.2f, 1));
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if(!stack.has(LightsaberDataComponents.LIGHTSABER_ACTIVE))
        {
            stack.set(LightsaberDataComponents.LIGHTSABER_ACTIVE, false);
            stack.set(LightsaberDataComponents.LIGHTSABER_LENGTH, 0f);
        }
        return super.onEntityItemUpdate(stack, entity);
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> tooltip, TooltipFlag flag) {
        var data = stack.get(LightsaberDataComponents.LIGHTSABER.get());
        if (data == null) return;

        var block = BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(data.kyber()));
        var item = block.asItem();
        int color = item.getDefaultInstance().getBarColor();

        String name = block.getName().getString();

        tooltip.add(Component.literal(name).withStyle(s -> s.withColor(color)));
    }
    @Override
    public Component getDescription() {
        var data = this.components().get(LightsaberDataComponents.LIGHTSABER.get());
        return Component.translatable(data.kyber().toString());
    }
}
