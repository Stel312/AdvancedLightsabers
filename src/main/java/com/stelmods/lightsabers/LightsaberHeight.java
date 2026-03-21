package com.stelmods.lightsabers;

import com.stelmods.lightsabers.common.component.LightsaberDataComponents;
import com.stelmods.lightsabers.common.item.LightsaberPart;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public record LightsaberHeight(float total, float emitterTop, float emitterPos, float switchPos, float hiltPos, float gripCenter) {
    public static  LightsaberHeight calculateHeights(LightsaberDataComponents.LightsaberData data) {
        float pommel = heightOf(data.pomel());
        float hilt = heightOf(data.hilt());
        float switchPart = heightOf(data.switch_());
        float emitter = heightOf(data.emitter());

        // Total physical height of all 4 parts
        float total = pommel + hilt + switchPart + emitter;
        float hiltPos = pommel;
        float switchPos = hiltPos + hilt;
        float emitterPos = switchPos + switchPart;
        // The Y-coordinate where the blade should start (sum of all parts)
        float emitterTop = emitterPos + emitter;


        // The point where the player's hand actually grips (usually middle of the hilt)
        float gripCenter = pommel + (hilt / 2f);

        return new LightsaberHeight(total, emitterTop, emitterPos, switchPos, hiltPos, gripCenter);
    }

    private static float heightOf(String data){
        if (data == null || data.isEmpty())
            return 0f;

        ResourceLocation id = ResourceLocation.tryParse(data);
        if (id == null)
            return 0f;

        Item item = BuiltInRegistries.ITEM.get(id);
        if (!(item instanceof LightsaberPart part))
            return 0f;
        return part.getHeight();
    }
}

