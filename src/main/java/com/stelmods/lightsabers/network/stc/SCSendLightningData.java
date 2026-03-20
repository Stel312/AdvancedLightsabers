package com.stelmods.lightsabers.network.stc;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.client.ClientUtils;
import com.stelmods.lightsabers.network.Packet;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Sync to own player
 */
public record SCSendLightningData(int id, List<Integer> targets) implements Packet {

    public static final Type<SCSendLightningData> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Lightsabers.MODID, "sc_send_lightning_data"));

    // 🔧 Codec de lista manual
    public static final StreamCodec<RegistryFriendlyByteBuf, List<Integer>> INT_LIST_CODEC =
            new StreamCodec<>() {
                @Override
                public List<Integer> decode(RegistryFriendlyByteBuf buf) {
                    int size = buf.readInt();
                    List<Integer> list = new ArrayList<>(size);
                    for (int i = 0; i < size; i++) {
                        list.add(buf.readInt());
                    }
                    return list;
                }

                @Override
                public void encode(RegistryFriendlyByteBuf buf, List<Integer> list) {
                    buf.writeInt(list.size());
                    for (int i : list) {
                        buf.writeInt(i);
                    }
                }
            };

    public static final StreamCodec<RegistryFriendlyByteBuf, SCSendLightningData> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT,
                    SCSendLightningData::id,
                    INT_LIST_CODEC,
                    SCSendLightningData::targets,
                    SCSendLightningData::new
            );

    @Override
    public void handle(IPayloadContext context) {
        context.enqueueWork(() ->
                ClientUtils.setLightningMap(id, targets)
        );
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}