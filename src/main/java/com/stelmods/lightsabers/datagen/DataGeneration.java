package com.stelmods.lightsabers.datagen;

import com.stelmods.lightsabers.datagen.init.ItemModels;
import com.stelmods.lightsabers.datagen.init.LootTables;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneration {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        final ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        //generator.addProvider(event.includeServer(), new Recipes(generator));
        generator.addProvider(event.includeClient(), new ItemModels(generator, existingFileHelper));
        //generator.addProvider(event.includeClient(), new BlockStates(generator, existingFileHelper));
        //generator.addProvider(event.includeClient(), new BlockModels(generator, existingFileHelper));

        generator.addProvider(event.includeClient(), new LootTables(Collections.emptySet(), Collections.emptyList(), generator));
    }
}
