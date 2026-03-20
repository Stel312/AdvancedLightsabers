package com.stelmods.lightsabers.network.cts;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.capabilities.PlayerCapabilities;
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
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;

public record CSShootLightning(boolean enabled) implements Packet {
    public static final CustomPacketPayload.Type<CSShootLightning> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Lightsabers.MODID, "cs_shoot_lightning"));
    public static final StreamCodec<FriendlyByteBuf, CSShootLightning> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            CSShootLightning::enabled,
            CSShootLightning::new
    );

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        PlayerCapabilities playerData = PlayerCapabilities.get(player);
        playerData.setLightningMode(enabled);

        for(ServerPlayer p : player.getServer().getPlayerList().getPlayers()) {
            if(!enabled) {
                PacketHandler.sendTo(new SCSendLightningData(player.getId(), new ArrayList<>()), p);
            }
            PacketHandler.syncToAllAround(player, playerData);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
