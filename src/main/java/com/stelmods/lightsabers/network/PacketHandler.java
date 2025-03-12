package com.stelmods.lightsabers.network;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.network.cts.*;
import com.stelmods.lightsabers.network.stc.SCSendLightningData;
import com.stelmods.lightsabers.network.stc.SCSyncCapabilityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = Integer.toString(1);

    private static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(Lightsabers.MODID, "main_channel")).clientAcceptedVersions(PROTOCOL_VERSION::equals).serverAcceptedVersions(PROTOCOL_VERSION::equals).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();

    public static void register() {
        int packetID = 0;
        HANDLER.registerMessage(packetID++, CSToggleLightsaber.class, CSToggleLightsaber::encode, CSToggleLightsaber::decode, CSToggleLightsaber::handle);
        HANDLER.registerMessage(packetID++, ForcePush.class, ForcePush::encode, ForcePush::decode, ForcePush::handle);
        HANDLER.registerMessage(packetID++, ForcePull.class, ForcePull::encode, ForcePull::decode, ForcePull::handle);
        HANDLER.registerMessage(packetID++, CSInteractWithBlock.class, CSInteractWithBlock::encode, CSInteractWithBlock::decode, CSInteractWithBlock::handle);
        HANDLER.registerMessage(packetID++, CSShootLightning.class, CSShootLightning::encode, CSShootLightning::decode, CSShootLightning::handle);

        HANDLER.registerMessage(packetID++, SCSyncCapabilityPacket.class, SCSyncCapabilityPacket::encode, SCSyncCapabilityPacket::decode, SCSyncCapabilityPacket::handle);
        HANDLER.registerMessage(packetID++, SCSendLightningData.class, SCSendLightningData::encode, SCSendLightningData::decode, SCSendLightningData::handle);

    }
    public static <MSG> void sendToServer(MSG msg) {
        HANDLER.sendToServer(msg);
    }

    public static <MSG> void sendTo(MSG msg, ServerPlayer player) {
        if (!(player instanceof FakePlayer)) {
            HANDLER.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

}
