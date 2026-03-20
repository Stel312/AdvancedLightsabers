package com.stelmods.lightsabers.network.stc;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.capabilities.PlayerCapabilities;
import com.stelmods.lightsabers.client.ClientUtils;
import com.stelmods.lightsabers.network.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Sync to own player
 */
public record SCSyncCapabilityPacket(int player, CompoundTag data) implements Packet {

    public SCSyncCapabilityPacket(Player player) {
        this(player.getId(), PlayerCapabilities.get(player).serializeNBT(player.level().registryAccess()));
    }

    public static final CustomPacketPayload.Type<SCSyncCapabilityPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Lightsabers.MODID, "sc_sync_capability"));

    public static final StreamCodec<FriendlyByteBuf, SCSyncCapabilityPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SCSyncCapabilityPacket::player,
            ByteBufCodecs.COMPOUND_TAG,
            SCSyncCapabilityPacket::data,
            SCSyncCapabilityPacket::new
    );

    public SCSyncCapabilityPacket(Player player, PlayerCapabilities playerData) {
        this(player.getId(), playerData.serializeNBT(player.level().registryAccess()));
    }

    @Override
    public void handle(IPayloadContext context) {
        ClientUtils.syncCapability(this);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
