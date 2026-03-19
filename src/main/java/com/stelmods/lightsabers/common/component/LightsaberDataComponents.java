package com.stelmods.lightsabers.common.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stelmods.lightsabers.Lightsabers;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LightsaberDataComponents {
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Lightsabers.MODID);


    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> LIGHTSABER_ACTIVE = COMPONENTS.registerComponentType("lightsaber_active",
            builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> LIGHTSABER_LENGTH = COMPONENTS.registerComponentType("lightsaber_length",
            builder -> builder.persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT));


    public static final DeferredHolder<DataComponentType<?>, DataComponentType<LightsaberData>> LIGHTSABER =
            COMPONENTS.registerComponentType("lightsaber", builder ->
                    builder.persistent(LightsaberData.CODEC).networkSynchronized(LightsaberData.STREAM_CODEC).cacheEncoding());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<DoubleLightsaberData>> DOUBLE_LIGHTSABER =
            COMPONENTS.registerComponentType("double_lightsaber", builder ->
                    builder.persistent(DoubleLightsaberData.CODEC).networkSynchronized(DoubleLightsaberData.STREAM_CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> CROSSGUARD_LIGHTSABER =
            COMPONENTS.registerComponentType("crossguard_lightsaber", builder -> builder.persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8));

    public record LightsaberData(String hilt, String pomel, String emitter, String switch_, String focus1, String focus2, String kyber) {
        public static final Codec<LightsaberData> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            Codec.STRING.fieldOf("pomel").forGetter(LightsaberData::pomel),
            Codec.STRING.fieldOf("hilt").forGetter(LightsaberData::hilt),
            Codec.STRING.fieldOf("switch").forGetter(LightsaberData::switch_),
            Codec.STRING.fieldOf("emitter").forGetter(LightsaberData::emitter),
            Codec.STRING.fieldOf("focus1").forGetter(LightsaberData::focus1),
            Codec.STRING.fieldOf("focus2").forGetter(LightsaberData::focus2),
            Codec.STRING.fieldOf("kyber").forGetter(LightsaberData::kyber)
        ).apply(builder, LightsaberData::new));

        public static final StreamCodec<FriendlyByteBuf, LightsaberData> STREAM_CODEC = LightsaberStreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, LightsaberData::pomel,
            ByteBufCodecs.STRING_UTF8, LightsaberData::hilt,
            ByteBufCodecs.STRING_UTF8, LightsaberData::switch_,
            ByteBufCodecs.STRING_UTF8, LightsaberData::emitter,
            ByteBufCodecs.STRING_UTF8, LightsaberData::focus1,
            ByteBufCodecs.STRING_UTF8, LightsaberData::focus2,
            ByteBufCodecs.STRING_UTF8, LightsaberData::kyber,
            LightsaberData::new
        );
    }
    public record DoubleLightsaberData(LightsaberData upper, LightsaberData lower){
        public static final Codec<DoubleLightsaberData> CODEC = RecordCodecBuilder.create(
                (builder) -> builder.group(
                        LightsaberData.CODEC.fieldOf("upper").forGetter(DoubleLightsaberData::upper),
                        LightsaberData.CODEC.fieldOf("lower").forGetter(DoubleLightsaberData::lower)
                ).apply(builder, DoubleLightsaberData::new)
        );

        public static final StreamCodec<FriendlyByteBuf, DoubleLightsaberData> STREAM_CODEC = StreamCodec.composite(
                LightsaberData.STREAM_CODEC, DoubleLightsaberData::upper,
                LightsaberData.STREAM_CODEC, DoubleLightsaberData::lower,
                DoubleLightsaberData::new
        );
    }


}
