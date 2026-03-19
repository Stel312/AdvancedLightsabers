package com.stelmods.lightsabers.common.entity;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.common.block.ModBlocks;
import com.stelmods.lightsabers.common.tileentity.LightsaberForgeBlockEntity;
import com.stelmods.lightsabers.common.tileentity.LightsaberForgeTier2BlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Lightsabers.MODID);

    public static final RegistryObject<BlockEntityType<LightsaberForgeBlockEntity>> LIGHTSABER_FORGE = TILE_ENTITIES.register("lightsaber_forge", () -> BlockEntityType.Builder.of(LightsaberForgeBlockEntity::new, ModBlocks.lightsaberForge.get()).build(null));
    public static final RegistryObject<BlockEntityType<LightsaberForgeTier2BlockEntity>> LIGHTSABER_FORGE_Tier2 = TILE_ENTITIES.register("lightsaber_forge_tier2", () -> BlockEntityType.Builder.of(LightsaberForgeTier2BlockEntity::new, ModBlocks.lightsaberForgeT2.get()).build(null));
}
