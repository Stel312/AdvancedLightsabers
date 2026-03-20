package com.stelmods.lightsabers.network.cts;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.network.Packet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CSInteractWithBlock(BlockPos pos, BlockHitResult hitResult) implements Packet {
    public static final StreamCodec<FriendlyByteBuf, net.minecraft.world.phys.Vec3> VEC3_STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.DOUBLE,
                    vec -> vec.x,
                    ByteBufCodecs.DOUBLE,
                    vec -> vec.y,
                    ByteBufCodecs.DOUBLE,
                    vec -> vec.z,
                    (x, y, z) -> new net.minecraft.world.phys.Vec3(x, y, z)
            );
    public static final StreamCodec<FriendlyByteBuf, BlockHitResult> BLOCK_HIT_RESULT_STREAM_CODEC =
            StreamCodec.composite(
                    VEC3_STREAM_CODEC,
                    BlockHitResult::getLocation,

                    ByteBufCodecs.INT,
                    hit -> hit.getDirection().ordinal(),

                    BlockPos.STREAM_CODEC,
                    BlockHitResult::getBlockPos,

                    ByteBufCodecs.BOOL,
                    BlockHitResult::isInside,

                    (location, dirOrdinal, pos, inside) ->
                            new BlockHitResult(location, Direction.values()[dirOrdinal], pos, inside)
            );

    public static final Type<CSInteractWithBlock> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Lightsabers.MODID, "cs_interact_block"));

    public static final StreamCodec<FriendlyByteBuf, CSInteractWithBlock> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            CSInteractWithBlock::pos,
            BLOCK_HIT_RESULT_STREAM_CODEC,
            CSInteractWithBlock::hitResult,
            CSInteractWithBlock::new
    );

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        BlockState blockState = player.level().getBlockState(pos);
        blockState.useWithoutItem(player.level(),player, hitResult);

        //senderPlayer.level().setBlockAndUpdate(message.pos,blockState.setValue(LeverBlock.POWERED,!blockState.getValue(LeverBlock.POWERED)));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
