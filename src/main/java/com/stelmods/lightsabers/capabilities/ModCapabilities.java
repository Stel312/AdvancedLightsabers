package com.stelmods.lightsabers.capabilities;

import com.stelmods.lightsabers.Lightsabers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Lightsabers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCapabilities {

    public static final Capability<IPlayerCapabilities> PLAYER_CAPABILITIES = CapabilityManager.get(new CapabilityToken<>() {});


    public static IPlayerCapabilities getPlayer(Player player) {
        LazyOptional<IPlayerCapabilities> playerData = player.getCapability(ModCapabilities.PLAYER_CAPABILITIES, null);
        return playerData.orElse(null);
    }


    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.register(IPlayerCapabilities.class);
    }

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player)
            event.addCapability(new ResourceLocation(Lightsabers.MODID, "player_capabilities"), new PlayerCapabilitiesProvider());

    }



}