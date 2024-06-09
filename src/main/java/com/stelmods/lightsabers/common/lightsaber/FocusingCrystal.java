package com.stelmods.lightsabers.common.lightsaber;

import java.util.Locale;

public enum FocusingCrystal
{
    COMPRESSED,
    CRACKED,
    INVERTING,
    FINE_CUT,
    //CHARGED,
    PRISMATIC,
    NONE;
    
    public long getCode()
    {
        return (1 << ordinal()) & 0xFFFF;
    }

    public String getUnlocalizedName()
    {
        return "lightsaber.focusingCrystal." + name().toLowerCase(Locale.ROOT);
    }

}
