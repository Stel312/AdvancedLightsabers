package com.stelmods.lightsabers.common.worldgen;

import com.stelmods.lightsabers.Lightsabers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, Lightsabers.MODID);

    public static final Supplier<Feature<CrystalCaveFeatureConfig>> CRYSTAL_CAVE = FEATURES.register(
            "crystal_cave",
            () -> new CrystalCaveFeature(CrystalCaveFeatureConfig.CODEC)
    );

    private ModFeatures() {
    }
}
