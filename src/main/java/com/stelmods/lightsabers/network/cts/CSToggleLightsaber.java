package com.stelmods.lightsabers.network.cts;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.common.component.LightsaberDataComponents;
import com.stelmods.lightsabers.common.item.LightsaberItem;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public record CSToggleLightsaber() implements Packet {

    public static final CustomPacketPayload.Type<CSToggleLightsaber> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Lightsabers.MODID, "cs_lightsaber_toggle"));

    @Override
    public static void handle(PacketListener packetListener) {
        Player senderPlayer = ctx.get().getSender();
        ItemStack main = senderPlayer.getMainHandItem();
        ItemStack off  = senderPlayer.getOffhandItem();

        // If main hand has a saber → toggle it AND sync offhand
        if (!main.isEmpty() && main.getItem() instanceof LightsaberItem) {

            boolean newState = !main.get(LightsaberDataComponents.LIGHTSABER_ACTIVE));
            main.set(LightsaberDataComponents.LIGHTSABER_ACTIVE, newState);

            // If offhand is also a saber → sync it
            if (!off.isEmpty() && off.getItem() instanceof LightsaberItem) {
                off.set(LightsaberDataComponents.LIGHTSABER_ACTIVE, newState);
            }

            return;
        }
        // If main hand is empty → toggle offhand normally
        if (!off.isEmpty() && off.getItem() instanceof LightsaberItem) {
            boolean newState = !off.get(LightsaberDataComponents.LIGHTSABER_ACTIVE));
            off.set(LightsaberDataComponents.LIGHTSABER_ACTIVE, newState);
        }

    }

    @Override
    public PacketType<? extends Packet> type() {
        return TYPE;
    }

}

