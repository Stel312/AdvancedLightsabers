package com.stelmods.lightsabers.network;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.capabilities.PlayerCapabilities;
import com.stelmods.lightsabers.network.cts.*;
import com.stelmods.lightsabers.network.stc.SCSendLightningData;
import com.stelmods.lightsabers.network.stc.SCSyncCapabilityPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber()
public class PacketHandler {

    private static PayloadRegistrar registrar;

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        registrar = event.registrar(Lightsabers.MODID);

        server(CSToggleLightsaber.TYPE, CSToggleLightsaber.STREAM_CODEC);
        server(CSForcePush.TYPE, CSForcePush.STREAM_CODEC);
        server(CSForcePull.TYPE, CSForcePull.STREAM_CODEC);
        server(CSInteractWithBlock.TYPE, CSInteractWithBlock.STREAM_CODEC);
        server(CSShootLightning.TYPE, CSShootLightning.STREAM_CODEC);

        client(SCSyncCapabilityPacket.TYPE, SCSyncCapabilityPacket.STREAM_CODEC);
        client(SCSendLightningData.TYPE, SCSendLightningData.STREAM_CODEC);
    }

    private static <T extends Packet> void client(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> reader) {
        registrar.playToClient(type, reader, PacketHandler::handlePacket);
    }

    private static <T extends Packet> void server(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> reader) {
        registrar.playToServer(type, reader, PacketHandler::handlePacket);
    }

    private static <T extends Packet> void bidirectional(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> reader) {
        registrar.playBidirectional(type, reader, PacketHandler::handlePacket);
    }

    public static void sendTo (Packet packet, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, packet);
    }

    public static void sendToAll (Packet packet) {
        PacketDistributor.sendToAllPlayers(packet);
    }

    public static void sendToServer(Packet packet) {
        PacketDistributor.sendToServer(packet);
    }

    public static <T extends Packet>void handlePacket(final T data, final IPayloadContext context) {
        context.enqueueWork(() -> data.handle(context)).exceptionally(e -> {
            Lightsabers.LOGGER.warn("Packet \"{}\" handling failed, something is likely broken", data.type());
            return null;
        });
        Packet reply = data.reply(context);
        if (reply != null) {
            context.reply(reply);
        }
    }

    public static void syncToAllAround(Player player, PlayerCapabilities playerData) {
        if (!player.level().isClientSide) {
            for (Player playerFromList : player.level().players()) {
                sendTo(new SCSyncCapabilityPacket(player), (ServerPlayer) playerFromList);
            }
        }
    }

}
