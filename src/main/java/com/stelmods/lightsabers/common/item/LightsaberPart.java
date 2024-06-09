package com.stelmods.lightsabers.common.item;

import com.stelmods.lightsabers.common.lightsaber.PartType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class LightsaberPart extends Item {

    private final float height;
    private final PartType partType;

    public LightsaberPart(float height, PartType partType) {
        super(new Properties().stacksTo(1).defaultDurability(0));
        this.height = height;
        this.partType = partType;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.add(Component.translatable("item.lightsabers." + ForgeRegistries.ITEMS.getKey(itemStack.getItem()).getPath() + ".desc"));
        super.appendHoverText(itemStack, level, tooltip, tooltipFlag);
    }

    public PartType getPartType() {
        return partType;
    }

    public float getHeight() {
        return height;
    }
}
