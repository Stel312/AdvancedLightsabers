package com.stelmods.lightsabers.common.worldgen;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.common.block.BlockCrystal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.ArrayList;
import java.util.List;

public class CrystalCaveFeature extends Feature<CrystalCaveFeatureConfig> {
    private static final boolean DEBUG_WORLDGEN = true;

    public CrystalCaveFeature(com.mojang.serialization.Codec<CrystalCaveFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CrystalCaveFeatureConfig> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos origin = context.origin();
        CrystalCaveFeatureConfig config = context.config();

        if (DEBUG_WORLDGEN) {
            Lightsabers.LOGGER.info("[CrystalWorldgen] try origin={} dim={}", origin, level.getLevel().dimension().location());
        }

        if (config.targets().isEmpty()) {
            return false;
        }

        List<BlockPos> exposedCandidates = new ArrayList<>();
        BlockState originState = level.getBlockState(origin);
        if (matchesAnyTarget(originState, random, config.targets()) && (!config.requireExposedAir() || touchesCaveAir(level, origin))) {
            exposedCandidates.add(origin);
        }

        for (int i = 0; i < config.attempts(); i++) {
            BlockPos candidate = origin.offset(
                    random.nextInt(config.horizontalRadius() * 2 + 1) - config.horizontalRadius(),
                    random.nextInt(config.verticalRadius() * 2 + 1) - config.verticalRadius(),
                    random.nextInt(config.horizontalRadius() * 2 + 1) - config.horizontalRadius()
            );

            BlockState candidateState = level.getBlockState(candidate);
            if (!matchesAnyTarget(candidateState, random, config.targets())) {
                continue;
            }
            if (config.requireExposedAir() && !touchesCaveAir(level, candidate)) {
                continue;
            }
            exposedCandidates.add(candidate);
        }

        if (exposedCandidates.isEmpty()) {
            return false;
        }

        BlockPos hostBlockPos = exposedCandidates.get(random.nextInt(exposedCandidates.size()));
        BlockState hostState = level.getBlockState(hostBlockPos);
        BlockState crystalState = chooseWeightedStateForHost(hostState, random, config.targets());
        if (crystalState == null) {
            return false;
        }

        // Find an air block adjacent to the host block and get the attachment direction
        AirBlockResult result = findAirBlockForCrystal(level, hostBlockPos, random);
        if (result == null) {
            return false;
        }

        // Rotate crystal so its bottom faces the host block
        BlockState rotatedCrystalState = rotateCrystalToFace(crystalState, result.attachmentDirection);

        level.setBlock(result.position, rotatedCrystalState, 3);

        if (DEBUG_WORLDGEN) {
            Lightsabers.LOGGER.info("[CrystalWorldgen] placed {} at {} on host {} facing {} (pool size {})", rotatedCrystalState.getBlock(), result.position, hostBlockPos, result.attachmentDirection, exposedCandidates.size());
        }
        return true;
    }

    private static AirBlockResult findAirBlockForCrystal(WorldGenLevel level, BlockPos hostBlockPos, RandomSource random) {
        // Prefer placing DOWN (stalactite effect)
        Direction[] preferredDirections = {Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
        
        for (Direction dir : preferredDirections) {
            BlockPos adjacentPos = hostBlockPos.relative(dir);
            BlockState adjacentState = level.getBlockState(adjacentPos);
            if (adjacentState.isAir() || adjacentState.canBeReplaced()) {
                return new AirBlockResult(adjacentPos, dir);
            }
        }
        Lightsabers.LOGGER.debug("[CrystalWorldgen] No adjacent air found for host at {}", hostBlockPos);
        return null;
    }

    private static BlockState rotateCrystalToFace(BlockState crystalState, Direction attachmentDirection) {
        // Orient the crystal in the direction it grows out from the host block.
        // Example: host above crystal => attachmentDirection DOWN => crystal points down.
        Direction facing = attachmentDirection;

        if (crystalState.hasProperty(BlockCrystal.FACING)) {
            return crystalState.setValue(BlockCrystal.FACING, facing);
        }

        // Fallback: return as-is if no facing property
        return crystalState;
    }

    // Helper class to return both position and attachment direction
    private static class AirBlockResult {
        BlockPos position;
        Direction attachmentDirection;

        AirBlockResult(BlockPos position, Direction attachmentDirection) {
            this.position = position;
            this.attachmentDirection = attachmentDirection;
        }
    }

    private static boolean matchesAnyTarget(BlockState hostState, RandomSource random, List<CrystalCaveFeatureConfig.TargetBlockState> targets) {
        for (CrystalCaveFeatureConfig.TargetBlockState target : targets) {
            if (target.target().test(hostState, random)) {
                return true;
            }
        }
        return false;
    }

    private static BlockState chooseWeightedStateForHost(BlockState hostState, RandomSource random, List<CrystalCaveFeatureConfig.TargetBlockState> targets) {
        for (CrystalCaveFeatureConfig.TargetBlockState target : targets) {
            if (target.target().test(hostState, random)) {
                return target.getRandomState(random);
            }
        }
        return null;
    }

    private static boolean touchesCaveAir(WorldGenLevel level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (level.getBlockState(pos.relative(direction)).isAir()) {
                return true;
            }
        }
        return false;
    }
}
