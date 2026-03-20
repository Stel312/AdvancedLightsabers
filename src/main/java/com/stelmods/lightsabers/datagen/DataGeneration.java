package com.stelmods.lightsabers.datagen;

import com.stelmods.lightsabers.datagen.init.ItemModels;
import com.stelmods.lightsabers.datagen.init.LootTables;
import com.stelmods.lightsabers.datagen.init.Recipes;
import com.stelmods.lightsabers.datagen.provider.BaseLootTableProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;


public class DataGeneration {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        final ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        generator.addProvider(event.includeServer(), new Recipes(generator, event.getLookupProvider()));
        generator.addProvider(event.includeClient(), new ItemModels(generator, existingFileHelper));
        //generator.addProvider(event.includeClient(), new BlockStates(generator, existingFileHelper));
        //generator.addProvider(event.includeClient(), new BlockModels(generator, existingFileHelper));

        generator.addProvider(event.includeServer(), new BaseLootTableProvider(output, event.getLookupProvider()));

    }
}
