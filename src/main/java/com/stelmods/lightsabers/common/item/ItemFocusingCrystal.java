package com.stelmods.lightsabers.common.item;

import com.stelmods.lightsabers.common.lightsaber.FocusingCrystal;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemFocusingCrystal extends Item {
    FocusingCrystal focusingCrystal;

    public ItemFocusingCrystal(FocusingCrystal focusingCrystal) {
        super(new Properties().stacksTo(1).durability(0));
        this.focusingCrystal = focusingCrystal;
    }

    public FocusingCrystal getFocusingCrystal() {
        return focusingCrystal;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable(focusingCrystal.getUnlocalizedName()));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
