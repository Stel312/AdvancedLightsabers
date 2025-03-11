package com.stelmods.lightsabers.datagen.init;

import com.stelmods.lightsabers.common.block.BlockCrystal;
import com.stelmods.lightsabers.common.block.ModBlocks;
import com.stelmods.lightsabers.datagen.provider.BaseLootTables;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Set;

public class LootTables extends BaseLootTables {
    public LootTables( Set<ResourceLocation> pRequiredTables, List<SubProviderEntry> pSubProviders, DataGenerator generator) {
        super(generator.getPackOutput(), pRequiredTables, pSubProviders, generator);
    }

    @Override
    protected void addTables() {
        crystal();
        standardBlockLoot(ModBlocks.lightsaberForge.get());
    }

    private void crystal()
    {
        for(RegistryObject<Block> block: ModBlocks.BLOCKS.getEntries())
        {
            if (block.get() instanceof BlockCrystal crystal)
            {
                standardBlockLoot(crystal);
            }
        }
    }

    void standardBlockLoot(Block block)
    {
        lootTables.put(block, createStandardTable(block.getDescriptionId(), block));
    }

}
