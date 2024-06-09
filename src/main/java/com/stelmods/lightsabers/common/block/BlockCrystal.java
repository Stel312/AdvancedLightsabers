package com.stelmods.lightsabers.common.block;

import com.stelmods.lightsabers.common.item.Rarity;
import com.stelmods.lightsabers.common.lightsaber.CrystalColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

public class BlockCrystal extends Block
{
    private final CrystalColor crystalColor;
    private final Rarity rarity;

    public BlockCrystal(Rarity rarity, CrystalColor crystalColor) {
        super(Block.Properties.of().mapColor(MapColor.METAL).strength(1.0F, 10.0F).noOcclusion());
        this.crystalColor = crystalColor;
        this.rarity = rarity;
    }


    public CrystalColor getCrystalColor() {
        return crystalColor;
    }

    public Rarity getRarity() {
        return rarity;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return true;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
