package com.stelmods.lightsabers.common.hilt;

import java.util.Collection;
import java.util.List;

import com.stelmods.lightsabers.common.lightsaber.CrystalColor;
import com.stelmods.lightsabers.common.lightsaber.FocusingCrystal;
import com.stelmods.lightsabers.common.lightsaber.PartType;

public class HiltKnighted extends Hilt
{
    public Part[] parts = makeParts();
    
    private Part[] makeParts()
    {
        Part[] parts = new Part[4];
        parts[0] = new Part(PartType.EMITTER, 12.6F).addCrossguard(0, 0.083F, 0.23F);
        parts[1] = new Part(PartType.SWITCH_SECTION, 8.4F);
        parts[2] = new Part(PartType.BODY, 20);
        parts[3] = new Part(PartType.POMMEL, 13.3F);
        
        return parts;
    }

    @Override
    public CrystalColor getColor()
    {
        return CrystalColor.RED;
    }

    @Override
    public Collection<FocusingCrystal> getFocusingCrystals()
    {
        return List.of(FocusingCrystal.CRACKED);
    }

    @Override
    public Part[] getParts()
    {
        return parts;
    }
}