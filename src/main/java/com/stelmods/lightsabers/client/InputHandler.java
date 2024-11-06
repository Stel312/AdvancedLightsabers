package com.stelmods.lightsabers.client;

import com.stelmods.lightsabers.network.PacketHandler;
import com.stelmods.lightsabers.network.cts.ForcePush;
import net.minecraft.client.Minecraft;
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

       if(event.getKey() == KeyMappings.FORCE_PUSH.getKey().getValue())
       {
           PacketHandler.sendToServer(new ForcePush());
       }
    }
}
