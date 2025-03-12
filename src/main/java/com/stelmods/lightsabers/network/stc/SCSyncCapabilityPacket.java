package com.stelmods.lightsabers.network.stc;

import com.stelmods.lightsabers.capabilities.IPlayerCapabilities;
import com.stelmods.lightsabers.client.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Sync to own player
 */
public class SCSyncCapabilityPacket {

    public boolean lightningMode = false;

    public SCSyncCapabilityPacket() {}

    public SCSyncCapabilityPacket(IPlayerCapabilities capability) {
        this.lightningMode = capability.isLightningMode();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.lightningMode);
    }

    public static SCSyncCapabilityPacket decode(FriendlyByteBuf buffer) {
        SCSyncCapabilityPacket msg = new SCSyncCapabilityPacket();

        msg.lightningMode = buffer.readBoolean();

        return msg;
    }

    public static void handle(final SCSyncCapabilityPacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientUtils.syncCapability(message)));
        ctx.get().setPacketHandled(true);
    }
}
