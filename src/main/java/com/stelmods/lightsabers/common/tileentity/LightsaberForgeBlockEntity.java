package com.stelmods.lightsabers.common.tileentity;

import com.stelmods.lightsabers.common.container.LightsaberForgeContainer;
import com.stelmods.lightsabers.common.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

public class LightsaberForgeBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemStackHandler = new ItemStackHandler(12) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return true;
        }
        @Override
        protected void onContentsChanged(int slot) {
            LightsaberForgeBlockEntity.super.setChanged();
            //level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    };

    public final Lazy<IItemHandler> inventory = Lazy.of(() -> itemStackHandler);

    public LightsaberForgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModEntities.LIGHTSABER_FORGE.get(), pos, state);
    }

    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
        return new LightsaberForgeContainer(windowId, inv, this);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.lightsaberforge");
    }

}
