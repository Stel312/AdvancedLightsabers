package com.stelmods.lightsabers.datagen.init;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.common.block.BlockCrystal;
import com.stelmods.lightsabers.common.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public class BlockStates extends BlockStateProvider {

    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Lightsabers.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (DeferredHolder<Block, ? extends Block> itemRegistryObject : ModBlocks.BLOCKS.getEntries()) {
            if(itemRegistryObject.get() instanceof BlockCrystal)
            {
                crystalBlockstate(itemRegistryObject);
            }
        }
    }
    public void crystalBlockstate(Supplier<? extends Block> blockSupplier) {
        directionalBlock((BlockCrystal) blockSupplier.get(), models().getExistingFile(modLoc("block/crystal")));
    }

    private void directionalBlock(BlockCrystal blockCrystal, net.neoforged.neoforge.client.model.generators.ModelFile modelFile) {
        getVariantBuilder(blockCrystal).forAllStates(state -> {
            Direction facing = state.getValue(BlockCrystal.FACING);
            int xRot = switch (facing) {
                case DOWN -> 180;
                case UP -> 0;
                default -> 90;
            };
            int yRot = switch (facing) {
                case NORTH -> 0;
                case SOUTH -> 180;
                case WEST -> 270;
                case EAST -> 90;
                default -> 0;
            };

            return ConfiguredModel.builder()
                    .modelFile(modelFile)
                    .rotationX(xRot)
                    .rotationY(yRot)
                    .uvLock(true)
                    .build();
        });
    }
}
