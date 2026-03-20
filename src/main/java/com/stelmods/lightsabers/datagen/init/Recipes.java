package com.stelmods.lightsabers.datagen.init;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

public class Recipes extends RecipeProvider {
    DataGenerator dataGenerator;

    public Recipes(DataGenerator dataGenerator, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(dataGenerator.getPackOutput(), pRegistries);
        this.dataGenerator = dataGenerator;
    }


    @Override
    protected void buildRecipes(RecipeOutput p_recipeOutput, HolderLookup.Provider holderLookup) {
        super.buildRecipes(p_recipeOutput, holderLookup);
    }

}
