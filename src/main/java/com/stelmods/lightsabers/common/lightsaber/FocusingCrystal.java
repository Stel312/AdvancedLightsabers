package com.stelmods.lightsabers.common.lightsaber;

import java.util.Locale;

public enum FocusingCrystal
{
    /** Makes the blade a thinner cylinder */
    COMPRESSED,
    /** Gives the blade an unstable look */
    CRACKED,
    /** Inverts the blade colors - black inner with white outer */
    INVERTING,
    /** Creates a flat sword-like blade (dark saber style) */
    FINE_CUT,
    /** Gives lighting style particles */
    //CHARGED,
    /** Colors the inner blade with prismatic effects */
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
