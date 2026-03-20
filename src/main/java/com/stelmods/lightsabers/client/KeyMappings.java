package com.stelmods.lightsabers.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.stelmods.lightsabers.Lightsabers;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@EventBusSubscriber(value = net.neoforged.api.distmarker.Dist.CLIENT)
public class KeyMappings {
    public static final KeyMapping TOGGLE_LIGHTSABER = new KeyMapping("key." + Lightsabers.MODID + ".toggle_lightsaber", InputConstants.KEY_G, "key." + Lightsabers.MODID + ".force");
    public static final KeyMapping FORCE_PUSH = new KeyMapping("key." + Lightsabers.MODID + ".force.push", InputConstants.KEY_K, "key." + Lightsabers.MODID + ".force");
    public static final KeyMapping FORCE_PULL = new KeyMapping("key." + Lightsabers.MODID + ".force.pull", InputConstants.KEY_H, "key." + Lightsabers.MODID + ".force");
    public static final KeyMapping FORCE_PICKUP = new KeyMapping("key." + Lightsabers.MODID + ".force.pickup", InputConstants.KEY_Y, "key." + Lightsabers.MODID + ".force");
    public static final KeyMapping FORCE_ACTIVATE= new KeyMapping("key." + Lightsabers.MODID + ".force.activate", InputConstants.KEY_U, "key." + Lightsabers.MODID + ".force");
    public static final KeyMapping FORCE_LIGHTNING= new KeyMapping("key." + Lightsabers.MODID + ".force.lightning", InputConstants.KEY_L, "key." + Lightsabers.MODID + ".force");


    @SubscribeEvent
    public static void registerKeyMap(net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_LIGHTSABER);
        event.register(FORCE_PUSH);
        event.register(FORCE_PULL);
        event.register(FORCE_PICKUP);
        event.register(FORCE_ACTIVATE);
        event.register(FORCE_LIGHTNING);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {

        NeoForge.EVENT_BUS.addListener(ClientEvents::colourTint);
        NeoForge.EVENT_BUS.addListener(ClientEvents::itemTint);
        NeoForge.EVENT_BUS.addListener(KeyMappings::registerKeyMap);
        NeoForge.EVENT_BUS.register(new InputHandler());
    }
}
