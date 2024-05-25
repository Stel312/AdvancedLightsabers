package com.stelmods.lightsabers.common.hilt;

import java.util.Collection;
import java.util.List;

import com.stelmods.lightsabers.common.lightsaber.CrystalColor;
import com.stelmods.lightsabers.common.lightsaber.FocusingCrystal;
import com.stelmods.lightsabers.common.lightsaber.PartType;

public class HiltRebel extends Hilt
{
    public Part[] parts = makeParts();
    
    private Part[] makeParts()
    {
        Part[] parts = new Part[4];
        parts[0] = new Part(PartType.EMITTER, 12.9F);
        parts[1] = new Part(PartType.SWITCH_SECTION, 7);
        parts[2] = new Part(PartType.BODY, 20);
        parts[3] = new Part(PartType.POMMEL, 6);
        
        return parts;
    }
    
    @Override
    public CrystalColor getColor()
    {
        return CrystalColor.MEDIUM_BLUE;
    }
    
    @Override
    public Collection<FocusingCrystal> getFocusingCrystals()
    {
        return List.of(FocusingCrystal.COMPRESSED);
    }

    @Override
    public Part[] getParts()
    {
        return parts;
    }
}