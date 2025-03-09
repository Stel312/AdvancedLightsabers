package com.stelmods.lightsabers.client;

import com.stelmods.lightsabers.common.item.LightsaberItem;
import com.stelmods.lightsabers.network.PacketHandler;
import com.stelmods.lightsabers.network.cts.ForcePull;
import com.stelmods.lightsabers.network.cts.ForcePush;
import com.stelmods.lightsabers.network.cts.SCToggleLightsaber;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class InputHandler {
    Minecraft mc;

    public InputHandler(){
        mc = Minecraft.getInstance();
    }

    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.Key event) {
        if(event.getAction() == 1) { //We only want to run it once the key has been pressed, not released
            if (event.getKey() == KeyMappings.FORCE_PUSH.getKey().getValue()) {
                PacketHandler.sendToServer(new ForcePush());
            } else if (event.getKey() == KeyMappings.FORCE_PULL.getKey().getValue()) {
                PacketHandler.sendToServer(new ForcePull());
            } else if (event.getKey() == KeyMappings.TOGGLE_LIGHTSABER.getKey().getValue()) {
                PacketHandler.sendToServer(new SCToggleLightsaber());
            }
        }
    }
}
