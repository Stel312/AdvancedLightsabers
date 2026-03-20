package com.stelmods.lightsabers.datagen.init;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.common.block.BlockCrystal;
import com.stelmods.lightsabers.common.item.ItemFocusingCrystal;
import com.stelmods.lightsabers.common.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ItemModels extends ItemModelProvider {

    public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator.getPackOutput(), Lightsabers.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (DeferredHolder<Item, ? extends Item> itemRegistryObject : ModItems.ITEM.getEntries()) {

            //item Name
            final Item item = itemRegistryObject.get();
            final String path = BuiltInRegistries.ITEM.getKey(item).getPath();
            if (item instanceof BlockItem blockItem && blockItem.getBlock() instanceof BlockCrystal) {
                standardBlockItem(path);
            }
            if (item instanceof ItemFocusingCrystal) {
                standardItem(path, "");
            }
        }
    }

    void standardBlockItem(String name) {
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(Lightsabers.MODID + ":block/crystal"));
    }

    void standardItem(String name, String path) {
        getBuilder(name).parent(new ModelFile.UncheckedModelFile("item/generated")).texture("layer0", "item/" + path + name);
    }
}
