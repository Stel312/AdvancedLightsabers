package com.stelmods.lightsabers.lib;

import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.BlockState;

public class Utils {
    public static boolean isBlockStateInteractable(BlockState blockState){
        return blockState.getValues().containsKey(LeverBlock.POWERED);
    }
}
