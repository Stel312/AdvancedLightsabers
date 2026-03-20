package com.stelmods.lightsabers.common.item;

import com.stelmods.lightsabers.common.lightsaber.PartType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public abstract class LightsaberPart extends Item {

    private final float height;
    private final PartType partType;

    public LightsaberPart(float height, PartType partType) {
        super(new Properties().stacksTo(1).durability(0));
        this.height = height;
        this.partType = partType;
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(
                Component.translatable("item.lightsabers." + BuiltInRegistries.ITEM.getKey(stack.getItem()) + ".desc")
        );
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public PartType getPartType() {
        return partType;
    }

    public float getHeight() {
        return height;
    }
}
