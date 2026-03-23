package com.stelmods.lightsabers.common.item;

import com.stelmods.lightsabers.common.component.LightsaberDataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class LightsaberDoubleItem extends LightsaberItem {

    public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> tooltip, TooltipFlag flag) {

        var data = this.components().get(LightsaberDataComponents.DOUBLE_LIGHTSABER.get());
        if (data == null) return;
        var upper = data.upper();
        var block = BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(upper.kyber()));
        var item = block.asItem();
        int uppercolor = item.getDefaultInstance().getBarColor();

        String name = block.getName().getString();

        tooltip.add(Component.literal(name).withStyle(s -> s.withColor(uppercolor)));

        var lower = data.lower();
        block = BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(lower.kyber()));
        item = block.asItem();
        int lowercolor = item.getDefaultInstance().getBarColor();

        name = block.getName().getString();

        tooltip.add(Component.literal(name).withStyle(s -> s.withColor(lowercolor)));
    }

    @Override
    public Component getDescription() {
        var data = this.components().get(LightsaberDataComponents.DOUBLE_LIGHTSABER.get());
        var upper = data.upper();
        var loower = data.lower();
        return Component.translatable(upper.kyber().toString()).append("\n").append(Component.translatable(loower.kyber()));
    }
}
