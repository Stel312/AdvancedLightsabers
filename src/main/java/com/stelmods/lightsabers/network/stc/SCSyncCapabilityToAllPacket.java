package com.stelmods.lightsabers.network.stc;

import com.stelmods.lightsabers.capabilities.IPlayerCapabilities;
import com.stelmods.lightsabers.capabilities.ModCapabilities;
import com.stelmods.lightsabers.client.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

/**
 * Sync to own player
 */
public class SCSyncCapabilityToAllPacket {
    private String name;
    private boolean lightningMode = false;

    public SCSyncCapabilityToAllPacket() {
    }

    public SCSyncCapabilityToAllPacket(String name, IPlayerCapabilities capability) {
        this.name = name;

        this.lightningMode = capability.isLightningMode();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(name);
        buffer.writeBoolean(this.lightningMode);
    }

    public static SCSyncCapabilityToAllPacket decode(FriendlyByteBuf buffer) {
        SCSyncCapabilityToAllPacket msg = new SCSyncCapabilityToAllPacket();

        msg.name = buffer.readUtf();

        msg.lightningMode = buffer.readBoolean();
        return msg;
    }

    public static void handle(final SCSyncCapabilityToAllPacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            List<AbstractClientPlayer> list = Minecraft.getInstance().level.players();
            Player player = null;
            for (int i = 0; i < list.size(); i++) { //Loop through the players
                String name = list.get(i).getName().getString();
                if (name.equals(message.name)) {
                    player = list.get(i);
                }
            }
            if (player != null) {
                IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);

                playerData.setLightningMode(message.lightningMode);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
