package com.stelmods.lightsabers.network.cts;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CSInteractWithBlock {
    BlockPos pos;
    BlockHitResult hitResult;
    public CSInteractWithBlock(){}

    public CSInteractWithBlock(BlockPos pos, BlockHitResult hitResult){
        this.pos = pos;
        this.hitResult = hitResult;
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.pos);
        buffer.writeBlockHitResult(this.hitResult);
    }

    public static CSInteractWithBlock decode(FriendlyByteBuf buffer) {
        CSInteractWithBlock msg = new CSInteractWithBlock();
        msg.pos = buffer.readBlockPos();
        msg.hitResult = buffer.readBlockHitResult();
        return msg;
    }

    public static void handle(CSInteractWithBlock message, final Supplier<NetworkEvent.Context> ctx) {
        Player senderPlayer = ctx.get().getSender();
        BlockState blockState = senderPlayer.level().getBlockState(message.pos);
        blockState.use(senderPlayer.level(),senderPlayer, InteractionHand.MAIN_HAND, message.hitResult);
        //senderPlayer.level().setBlockAndUpdate(message.pos,blockState.setValue(LeverBlock.POWERED,!blockState.getValue(LeverBlock.POWERED)));
    }
}
