package com.stelmods.lightsabers.datagen.init;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
    DataGenerator dataGenerator;

    public Recipes(DataGenerator dataGenerator) {
        super(dataGenerator.getPackOutput());
        this.dataGenerator = dataGenerator;
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> p_251297_) {

    }
}
