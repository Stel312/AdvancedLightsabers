package com.stelmods.lightsabers.network.cts;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.capabilities.PlayerCapabilities;
import com.stelmods.lightsabers.common.component.LightsaberDataComponents;
import com.stelmods.lightsabers.common.item.LightsaberItem;
import com.stelmods.lightsabers.network.Packet;
import com.stelmods.lightsabers.network.PacketHandler;
import com.stelmods.lightsabers.network.stc.SCSendLightningData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;

public record CSGrabEntity(int grabbedID) implements Packet {

    public static final Type<CSGrabEntity> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Lightsabers.MODID, "cs_grab_entity"));
    public static final StreamCodec<FriendlyByteBuf, CSGrabEntity> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            CSGrabEntity::grabbedID,
            CSGrabEntity::new
    );

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        PlayerCapabilities playerData = PlayerCapabilities.get(player);
        if(playerData.getGrabbedID() == grabbedID) {
            playerData.setGrabbedID(-1);
        } else {
            playerData.setGrabbedID(grabbedID);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}

