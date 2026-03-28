package com.stelmods.lightsabers.lib;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.BlockState;

public class Utils {
    public static boolean isBlockStateInteractable(BlockState blockState){
        if(blockState.getBlock() == Blocks.IRON_DOOR || blockState.getBlock() == Blocks.IRON_TRAPDOOR) { // Exclude iron doors and trapdors since they don't have an use method
            return false;
        }
        return blockState.getValues().containsKey(LeverBlock.POWERED);
    }
}

