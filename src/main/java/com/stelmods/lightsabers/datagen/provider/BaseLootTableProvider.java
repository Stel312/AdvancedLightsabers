package com.stelmods.lightsabers.datagen.provider;

import com.stelmods.lightsabers.datagen.init.LootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

        public class BaseLootTableProvider extends LootTableProvider {
            public BaseLootTableProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> providerCompletableFuture) {
                super(pOutput, Set.of(), List.of(new SubProviderEntry(LootTables::new, LootContextParamSets.BLOCK)), providerCompletableFuture);
            }

        }
