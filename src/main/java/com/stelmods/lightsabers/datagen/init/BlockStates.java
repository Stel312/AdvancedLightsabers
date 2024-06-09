package com.stelmods.lightsabers.datagen.init;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.common.block.ModBlocks;
import com.stelmods.lightsabers.common.block.BlockCrystal;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockStates extends BlockStateProvider {

    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Lightsabers.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (RegistryObject<Block> itemRegistryObject : ModBlocks.BLOCKS.getEntries()) {
            if(itemRegistryObject.get() instanceof BlockCrystal)
            {
                crystalBlockstate(itemRegistryObject);
            }
        }
    }
    public void crystalBlockstate(Supplier<? extends Block> blockSupplier) {

        simpleBlock(blockSupplier.get());
    }
}
