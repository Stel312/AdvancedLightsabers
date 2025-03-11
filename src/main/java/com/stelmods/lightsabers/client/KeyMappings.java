package com.stelmods.lightsabers.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.stelmods.lightsabers.Lightsabers;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus=Mod.EventBusSubscriber.Bus.MOD)
public class KeyMappings {
    public static final KeyMapping TOGGLE_LIGHTSABER = new KeyMapping("key." + Lightsabers.MODID + ".toggle_lightsaber", InputConstants.KEY_G, "key." + Lightsabers.MODID + ".force");
    public static final KeyMapping FORCE_PUSH = new KeyMapping("key." + Lightsabers.MODID + ".force.push", InputConstants.KEY_K, "key." + Lightsabers.MODID + ".force");
    public static final KeyMapping FORCE_PULL = new KeyMapping("key." + Lightsabers.MODID + ".force.pull", InputConstants.KEY_H, "key." + Lightsabers.MODID + ".force");
    public static final KeyMapping FORCE_PICKUP = new KeyMapping("key." + Lightsabers.MODID + ".force.pickup", InputConstants.KEY_Y, "key." + Lightsabers.MODID + ".force");
    public static final KeyMapping FORCE_ACTIVATE= new KeyMapping("key." + Lightsabers.MODID + ".force.activate", InputConstants.KEY_U, "key." + Lightsabers.MODID + ".force");

    public static void registerKeyMap(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_LIGHTSABER);
        event.register(FORCE_PUSH);
        event.register(FORCE_PULL);
        event.register(FORCE_PICKUP);
        event.register(FORCE_ACTIVATE);
    }
}
