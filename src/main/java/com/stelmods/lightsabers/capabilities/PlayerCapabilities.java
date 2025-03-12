package com.stelmods.lightsabers.capabilities;

import net.minecraft.nbt.CompoundTag;

public class PlayerCapabilities implements IPlayerCapabilities {

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag storage = new CompoundTag();
        storage.putBoolean("lightning_mode", this.isLightningMode());

        return storage;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.setLightningMode(nbt.getBoolean("lightning_mode"));
    }


    boolean lightningMode;

    @Override
    public boolean isLightningMode() {
        return lightningMode;
    }

    @Override
    public void setLightningMode(boolean enabled) {
        this.lightningMode = enabled;
    }
}