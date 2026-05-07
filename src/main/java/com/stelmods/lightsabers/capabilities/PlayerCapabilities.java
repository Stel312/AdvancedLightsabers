package com.stelmods.lightsabers.capabilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.IntArrayTag; // Import IntArrayTag
import net.minecraft.nbt.Tag; // Import Tag
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.minecraft.core.registries.BuiltInRegistries; // Import BuiltInRegistries

import javax.annotation.Nullable;
import java.util.Optional; // Import Optional

public class PlayerCapabilities implements INBTSerializable<CompoundTag> {

    protected PlayerCapabilities() {}

    public static PlayerCapabilities get(CompoundTag nbt, Player player) {
        //Only deserialize on client, there shouldn't be a reason to do this on the server
        if (FMLEnvironment.dist.isClient()) {
            PlayerCapabilities data = new PlayerCapabilities();
            data.deserializeNBT(player.level().registryAccess(), nbt);
            player.setData(ModCapabilities.PLAYER_DATA, data);
            return data;
        } else {
            return get(player);
        }
    }

    @Nullable
    public static PlayerCapabilities get(Player player) {
        if (!player.hasData(ModCapabilities.PLAYER_DATA)) {
            player.setData(ModCapabilities.PLAYER_DATA, new PlayerCapabilities());
        }
        return player.getData(ModCapabilities.PLAYER_DATA);
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag storage = new CompoundTag();
        storage.putBoolean("lightning_mode", this.isLightningMode());
        storage.putInt("grabbed_entity_id", this.getGrabbedID());

        if (this.grabbedBlockPos != null) {
            // Corrected usage: NbtUtils.writeBlockPos returns an IntArrayTag.
            // We need to get the int[] from it and store it directly in the CompoundTag using putIntArray,
            // as NbtUtils.readBlockPos(CompoundTag, String) expects an int array directly.
            storage.putIntArray("grabbed_block_pos", ((IntArrayTag) NbtUtils.writeBlockPos(this.grabbedBlockPos)).getAsIntArray());
        }
        if (this.grabbedBlockId != null) {
            storage.putString("grabbed_block_id", this.grabbedBlockId.toString());
        }
        if (this.grabbedBlockStateNBT != null) {
            storage.put("grabbed_block_state_nbt", this.grabbedBlockStateNBT);
        }
        storage.putInt("grabbed_block_entity_id", this.grabbedBlockEntityId);

        return storage;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        this.setLightningMode(nbt.getBoolean("lightning_mode"));
        this.setGrabbedID(nbt.contains("grabbed_entity_id") ? nbt.getInt("grabbed_entity_id") : -1);

        if (nbt.contains("grabbed_block_pos")) {
            Optional<BlockPos> optionalBlockPos = NbtUtils.readBlockPos(nbt, "grabbed_block_pos");
            optionalBlockPos.ifPresent(this::setGrabbedBlockPos);
        } else {
            this.grabbedBlockPos = null;
        }
        if (nbt.contains("grabbed_block_id")) {
            this.setGrabbedBlockId(ResourceLocation.parse(nbt.getString("grabbed_block_id")));
        } else {
            this.grabbedBlockId = null;
        }
        if (nbt.contains("grabbed_block_state_nbt")) {
            this.setGrabbedBlockStateNBT(nbt.getCompound("grabbed_block_state_nbt"));
        } else {
            this.grabbedBlockStateNBT = null;
        }
        this.setGrabbedBlockEntityId(nbt.contains("grabbed_block_entity_id") ? nbt.getInt("grabbed_block_entity_id") : -1);
    }


    boolean lightningMode;
    int grabbedID = -1; // For entities — -1 means nothing held
    
    // For blocks
    private BlockPos grabbedBlockPos;
    private ResourceLocation grabbedBlockId;
    private CompoundTag grabbedBlockStateNBT;
    private int grabbedBlockEntityId = -1; // -1 means nothing held

    public boolean isLightningMode() {
        return lightningMode;
    }

    public void setLightningMode(boolean enabled) {
        this.lightningMode = enabled;
    }

    public int getGrabbedID() {
        return grabbedID;
    }

    public void setGrabbedID(int grabbedID) {
        this.grabbedID = grabbedID;
    }

    public BlockPos getGrabbedBlockPos() {
        return grabbedBlockPos;
    }

    public void setGrabbedBlockPos(BlockPos grabbedBlockPos) {
        this.grabbedBlockPos = grabbedBlockPos;
    }

    public ResourceLocation getGrabbedBlockId() {
        return grabbedBlockId;
    }

    public void setGrabbedBlockId(ResourceLocation grabbedBlockId) {
        this.grabbedBlockId = grabbedBlockId;
    }

    public CompoundTag getGrabbedBlockStateNBT() {
        return grabbedBlockStateNBT;
    }

    public void setGrabbedBlockStateNBT(CompoundTag grabbedBlockStateNBT) {
        this.grabbedBlockStateNBT = grabbedBlockStateNBT;
    }

    public int getGrabbedBlockEntityId() {
        return grabbedBlockEntityId;
    }

    public void setGrabbedBlockEntityId(int grabbedBlockEntityId) {
        this.grabbedBlockEntityId = grabbedBlockEntityId;
    }

    // Helper to reconstruct BlockState
    @Nullable
    public BlockState getGrabbedBlockState() {
        if (grabbedBlockId != null && grabbedBlockStateNBT != null) {
            Block block = BuiltInRegistries.BLOCK.get(grabbedBlockId);
            if (block != null) {
                return NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), grabbedBlockStateNBT);
            }
        }
        return null;
    }
}