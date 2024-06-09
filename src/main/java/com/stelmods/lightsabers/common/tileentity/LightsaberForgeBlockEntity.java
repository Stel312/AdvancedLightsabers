package com.stelmods.lightsabers.common.tileentity;


import com.stelmods.lightsabers.common.container.LightsaberForgeContainer;
import com.stelmods.lightsabers.common.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class LightsaberForgeBlockEntity extends BaseContainerBlockEntity
{
    protected final LazyOptional<IItemHandler> inventory = LazyOptional.of(this::createInventory);

    public LightsaberForgeBlockEntity(BlockPos bPos, BlockState bState) {
            super(ModEntities.LIGHTSABER_FORGE.get(), bPos, bState);
    }

    protected IItemHandler createInventory() {
        return new ItemStackHandler(10) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return inventory.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.lightsaberforge");
    }

    @Override
    protected Component getDefaultName() {
        return null;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowID, Inventory playerInventory, Player Player) {
        return new LightsaberForgeContainer(windowID, playerInventory, this);
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowID, Inventory playerInventory) {
        return new LightsaberForgeContainer(windowID, playerInventory, this);
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int p_18941_) {
        return null;
    }

    @Override
    public ItemStack removeItem(int p_18942_, int p_18943_) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_18951_) {
        return null;
    }

    @Override
    public void setItem(int p_18944_, ItemStack p_18945_) {
        System.out.println("set");
    }

    @Override
    public boolean stillValid(Player p_18946_) {
        return false;
    }

    @Override
    public void clearContent() {

    }
}
