package com.stelmods.lightsabers.common.block;

import com.stelmods.lightsabers.common.item.Rarity;
import com.stelmods.lightsabers.common.lightsaber.CrystalColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

public class BlockCrystal extends Block
{
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    
    private final CrystalColor crystalColor;
    private final Rarity rarity;

    public BlockCrystal(Rarity rarity, CrystalColor crystalColor) {
        super(Block.Properties.of().mapColor(MapColor.METAL).strength(1.0F, 10.0F).noOcclusion());
        this.crystalColor = crystalColor;
        this.rarity = rarity;
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.DOWN));
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
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

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // Attach orientation to the face the player targeted.
        return this.defaultBlockState().setValue(FACING, context.getClickedFace());
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}
