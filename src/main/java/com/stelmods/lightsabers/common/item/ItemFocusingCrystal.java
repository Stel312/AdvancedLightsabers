package com.stelmods.lightsabers.common.item;

import com.stelmods.lightsabers.common.lightsaber.FocusingCrystal;
import net.minecraft.world.item.Item;

public class ItemFocusingCrystal extends Item {
    FocusingCrystal focusingCrystal;

    public ItemFocusingCrystal(FocusingCrystal focusingCrystal) {
        super(new Properties().stacksTo(1).defaultDurability(0));
        this.focusingCrystal = focusingCrystal;
    }

    public FocusingCrystal getFocusingCrystal() {
        return focusingCrystal;
    }
}
