package com.stelmods.lightsabers.capabilities;

import net.minecraft.nbt.CompoundTag;

import net.minecraftforge.common.util.INBTSerializable;

public interface IPlayerCapabilities extends INBTSerializable<CompoundTag> {
    boolean isLightningMode();

    void setLightningMode(boolean enabled);
}