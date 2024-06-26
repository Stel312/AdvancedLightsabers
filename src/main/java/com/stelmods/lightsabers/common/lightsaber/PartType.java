package com.stelmods.lightsabers.common.lightsaber;

public enum PartType
{
    EMITTER("emitter"),
    SWITCH_SECTION("switch"),
    BODY("body"),
    POMMEL("pommel");
    
    public final String textureName;
    
    PartType(String name)
    {
        textureName = name;
    }

    public boolean isLowerPart()
    {
        return this == BODY || this == POMMEL;
    }
}
