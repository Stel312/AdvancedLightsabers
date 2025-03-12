package com.stelmods.lightsabers.network.stc;

import com.stelmods.lightsabers.client.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.sql.Array;
import java.util.ArrayList;
import java.util.function.Supplier;

/**
 * Sync to own player
 */
public class SCSendLightningData {

    private int id;
    private int targetsSize;
    private ArrayList<Integer> targets = new ArrayList<>();

    public SCSendLightningData() {}

    public SCSendLightningData(int id, ArrayList<Integer> targets) {
        this.id = id;
        this.targetsSize = targets.size();
        this.targets = targets;
        System.out.println("Syncing data");
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(id);
        buffer.writeInt(targetsSize);
        for(int i = 0; i< targetsSize; i++){
            buffer.writeInt(targets.get(i));
        }
    }

    public static SCSendLightningData decode(FriendlyByteBuf buffer) {
        SCSendLightningData msg = new SCSendLightningData();

        msg.id = buffer.readInt();
        msg.targetsSize = buffer.readInt();
        for(int i = 0; i< msg.targetsSize;i++){
            msg.targets.add(buffer.readInt());
        }
        return msg;
    }

    public static void handle(final SCSendLightningData message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientUtils.setLightningMap(message.id, message.targetsSize, message.targets)));
        ctx.get().setPacketHandled(true);
    }
}
