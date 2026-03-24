package com.stelmods.lightsabers.capabilities;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

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

        return storage;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        this.setLightningMode(nbt.getBoolean("lightning_mode"));
    }


    boolean lightningMode;
    int grabbedID;

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
}