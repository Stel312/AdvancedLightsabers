package com.stelmods.lightsabers.network.cts;

import com.stelmods.lightsabers.capabilities.IPlayerCapabilities;
import com.stelmods.lightsabers.capabilities.ModCapabilities;
import com.stelmods.lightsabers.network.PacketHandler;
import com.stelmods.lightsabers.network.stc.SCSendLightningData;
import com.stelmods.lightsabers.network.stc.SCSyncCapabilityPacket;
import com.stelmods.lightsabers.network.stc.SCSyncCapabilityToAllPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public class CSShootLightning {
    boolean enabled;
    public CSShootLightning(){}

    public CSShootLightning(boolean enable){
        this.enabled = enable;
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBoolean(enabled);
    }

    public static CSShootLightning decode(FriendlyByteBuf buffer) {
        CSShootLightning msg = new CSShootLightning();
        msg.enabled = buffer.readBoolean();
        return msg;
    }

    public static void handle(CSShootLightning message, final Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Player player = ctx.get().getSender();
            //TODO set capability for lightning mode
            IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
            playerData.setLightningMode(message.enabled);

            for(ServerPlayer p : player.getServer().getPlayerList().getPlayers()) {
                if(!message.enabled) {
                    PacketHandler.sendTo(new SCSendLightningData(player.getId(), new ArrayList<>()), p);
                }
                PacketHandler.sendTo(new SCSyncCapabilityToAllPacket(player.getDisplayName().getString(),playerData), p);
            }


        });
        ctx.get().setPacketHandled(true);
    }
}
