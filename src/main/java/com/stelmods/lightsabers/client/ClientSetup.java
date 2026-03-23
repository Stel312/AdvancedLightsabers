package com.stelmods.lightsabers.client;

import com.stelmods.lightsabers.Lightsabers;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientSetup {
    public enum Keybinds {
        TOGGLE_LIGHTSABER("key." + Lightsabers.MODID + ".toggle_lightsaber", GLFW.GLFW_KEY_G),
        FORCE_PUSH("key." + Lightsabers.MODID + ".force.push",GLFW.GLFW_KEY_K),
        FORCE_PULL("key." + Lightsabers.MODID + ".force.pull", GLFW.GLFW_KEY_H),
        FORCE_PICKUP("key." + Lightsabers.MODID + ".force.pickup",GLFW.GLFW_KEY_Y),
        FORCE_ACTIVATE("key." + Lightsabers.MODID + ".force.activate", GLFW.GLFW_KEY_U),
        FORCE_LIGHTNING("key." + Lightsabers.MODID + ".force.lightning",GLFW.GLFW_KEY_L);

        public final KeyMapping keybinding;
        public final String translationKey;

        Keybinds(String name, int defaultKey) {
            keybinding = new KeyMapping(name, defaultKey, "key." + Lightsabers.MODID + ".force");
            translationKey = name;
        }

        public KeyMapping getKeybind() {
            return keybinding;
        }

        private boolean isPressed() {
            return keybinding.consumeClick();
        }
    }

    private Keybinds getPressedKey() {
        for (Keybinds key : Keybinds.values())
            if (key.isPressed())
                return key;
        return null;
    }

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        for(Keybinds key : Keybinds.values()) {
            event.register(key.keybinding);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        NeoForge.EVENT_BUS.register(new ClientEvents());
        NeoForge.EVENT_BUS.register(new InputHandler());
    }
}
