package com.stelmods.lightsabers.common.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.List;

public record CrystalCaveFeatureConfig(
        int attempts,
        int horizontalRadius,
        int verticalRadius,
        boolean requireExposedAir,
        List<TargetBlockState> targets
) implements FeatureConfiguration {
    public static final Codec<CrystalCaveFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("attempts").forGetter(CrystalCaveFeatureConfig::attempts),
            Codec.INT.fieldOf("horizontal_radius").forGetter(CrystalCaveFeatureConfig::horizontalRadius),
            Codec.INT.fieldOf("vertical_radius").forGetter(CrystalCaveFeatureConfig::verticalRadius),
            Codec.BOOL.optionalFieldOf("require_exposed_air", true).forGetter(CrystalCaveFeatureConfig::requireExposedAir),
            TargetBlockState.CODEC.listOf().fieldOf("targets").forGetter(CrystalCaveFeatureConfig::targets)
    ).apply(instance, CrystalCaveFeatureConfig::new));

    public record TargetBlockState(RuleTest target, List<BlockState> states, List<Integer> weights) {
        public static final Codec<TargetBlockState> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                RuleTest.CODEC.fieldOf("target").forGetter(TargetBlockState::target),
                BlockState.CODEC.listOf().fieldOf("states").forGetter(TargetBlockState::states),
                Codec.INT.listOf().fieldOf("weights").forGetter(TargetBlockState::weights)
        ).apply(instance, TargetBlockState::new));

        public BlockState getRandomState(RandomSource random) {
            if (states.isEmpty()) {
                return null;
            }

            int totalWeight = 0;
            for (int i = 0; i < states.size(); i++) {
                int weight = i < weights.size() ? Math.max(0, weights.get(i)) : 1;
                totalWeight += weight;
            }

            if (totalWeight <= 0) {
                return states.get(0);
            }

            int roll = random.nextInt(totalWeight);
            int running = 0;
            for (int i = 0; i < states.size(); i++) {
                int weight = i < weights.size() ? Math.max(0, weights.get(i)) : 1;
                running += weight;
                if (roll < running) {
                    return states.get(i);
                }
            }

            return states.get(0);
        }
    }
}
