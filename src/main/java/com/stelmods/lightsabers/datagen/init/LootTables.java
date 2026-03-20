package com.stelmods.lightsabers.datagen.init;

import com.stelmods.lightsabers.common.block.BlockCrystal;
import com.stelmods.lightsabers.common.block.ModBlocks;
import com.stelmods.lightsabers.datagen.provider.BaseLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

public class LootTables extends BlockLootSubProvider {
    public LootTables(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, provider);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getRegistry().get();
    }
    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> pOutput) {
        this.generate();
        Set<ResourceKey<LootTable>> set = new HashSet<>();

        for(Block block : getKnownBlocks()) {
            if (block.isEnabled(this.enabledFeatures)) {
                ResourceKey<LootTable> resourcekey = block.getLootTable();
                if (resourcekey != BuiltInLootTables.EMPTY && set.add(resourcekey)) {
                    LootTable.Builder loottable$builder = this.map.remove(resourcekey);
                    if (loottable$builder != null) {
                        pOutput.accept(resourcekey, loottable$builder);
                    }
                }
            }
        }
        if (!this.map.isEmpty()) {
            throw new IllegalStateException("Created block loot tables for non-blocks: " + this.map.keySet());
        }
    }
    @Override
    protected void generate() {
        crystal();
        dropSelf(ModBlocks.lightsaberForge.get());
    }

    private void crystal()
    {
        for(DeferredHolder<Block, ? extends Block> block: ModBlocks.BLOCKS.getEntries())
        {
            if (block.get() instanceof BlockCrystal crystal)
            {
                dropSelf(crystal);
            }
        }
    }
}
