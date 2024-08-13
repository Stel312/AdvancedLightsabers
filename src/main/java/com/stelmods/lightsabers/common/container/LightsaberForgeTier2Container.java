package com.stelmods.lightsabers.common.container;

import com.stelmods.lightsabers.common.block.ModBlocks;
import com.stelmods.lightsabers.common.block.BlockCrystal;
import com.stelmods.lightsabers.common.item.ItemFocusingCrystal;
import com.stelmods.lightsabers.common.item.LightsaberDoubleItem;
import com.stelmods.lightsabers.common.item.LightsaberItem;
import com.stelmods.lightsabers.common.item.ModItems;
import com.stelmods.lightsabers.common.item.parts.LightsaberBody;
import com.stelmods.lightsabers.common.item.parts.LightsaberEmiter;
import com.stelmods.lightsabers.common.item.parts.LightsaberPommel;
import com.stelmods.lightsabers.common.item.parts.LightsaberSwitch;
import com.stelmods.lightsabers.common.lightsaber.LightsaberType;
import com.stelmods.lightsabers.common.tileentity.LightsaberForgeTier2BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class LightsaberForgeTier2Container extends AbstractContainerMenu  {
    public final LightsaberForgeTier2BlockEntity TE;
    private final ContainerLevelAccess canInteractWith;
    public InventoryLightsaberForgeTier2 craftMatrix = new InventoryLightsaberForgeTier2(this);
    public Container craftResult = new ResultContainer();

    public static final int[][] SLOTS = { {43, 71}, {89, 71}, {107, 71}};
    private Slot outputSlot;
    private Slot outputSlot2;
    private Slot dualSlot;
    public LightsaberForgeTier2Container(int id, Inventory inventoryPlayer)
    {
        this(id, inventoryPlayer, (LightsaberForgeTier2BlockEntity) null);
    }

    public LightsaberForgeTier2Container(final int windowId, final Inventory playerInventory, final FriendlyByteBuf buf) {
        this(windowId, playerInventory, getTileEntity(playerInventory, buf));
    }

    public LightsaberForgeTier2Container(int id, Inventory inventoryPlayer, LightsaberForgeTier2BlockEntity tile)
    {
        super( ModContainers.LIGHTSABER_FORGE_TIER2.get(), id);
        TE = tile;
        canInteractWith = ContainerLevelAccess.create(TE.getLevel(), TE.getBlockPos());

        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih -> outputSlot = addSlot(new SingleOutput(iih,14, 28, 91)));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih -> outputSlot2 = addSlot(new SingleOutput2(iih, 15, 82, 91)));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih -> dualSlot = addSlot(new DoubleOutput(iih, 17, 138,88 )));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventoryPlayer, j + i * 9 + 9, 9 + j * 18, 114 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) { addSlot(new Slot(inventoryPlayer, i, 9 + i * 18, 172));}
        slotsChanged(craftMatrix);
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(Player playerIn) {
        boolean test = stillValid(canInteractWith, playerIn, ModBlocks.lightsaberForgeT2.get());
        return test;
    }

    private static LightsaberForgeTier2BlockEntity getTileEntity(final Inventory playerInventory, final FriendlyByteBuf buf) {
        final BlockEntity te = playerInventory.player.level().getBlockEntity(buf.readBlockPos());
        if (te instanceof LightsaberForgeTier2BlockEntity) {
            return (LightsaberForgeTier2BlockEntity) te;
        }
        throw new IllegalStateException("Tile Entity mismatch with container");
    }
    @Override
    public void removed(Player player) {
        super.removed(player);

        outputSlot.remove(1);
        outputSlot2.remove(1);
        dualSlot.remove(1);
        this.broadcastChanges();

    }

    private class SingleOutput extends SlotItemHandler
    {
        public SingleOutput(IItemHandler iih, int id, int x, int y)
        {
            super(iih, id, x, y);
        }

        @Override
        public void setByPlayer(ItemStack stack) {
            super.setByPlayer(stack);
            if(stack.getItem() instanceof LightsaberItem)
            {
                outputSlot2.getItem();
                if(outputSlot2.getItem().getItem() instanceof LightsaberItem)
                {
                    ItemStack itemStack = new ItemStack(ModItems.doubleLightsaber.get());
                    itemStack.setTag(new CompoundTag());
                    itemStack.getTag().putString("type", LightsaberType.DOUBLE.toString());
                    itemStack.getTag().put("upper", stack.getTag());
                    itemStack.getTag().put("lower", outputSlot2.getItem().getTag());
                    dualSlot.set(itemStack);
                }
            }
        }

        @Override
        public boolean mayPlace(ItemStack stack)
        {
            return stack.getItem() instanceof LightsaberItem && !(stack.getItem() instanceof LightsaberDoubleItem);
        }

        @Override
        public void onTake(Player player, ItemStack itemstack)
        {
            net.minecraftforge.event.ForgeEventFactory.firePlayerCraftingEvent(player, itemstack, this.container);
            itemstack.onCraftedBy(player.level(), player, 1);
            outputSlot.remove(1);
            dualSlot.remove(1);
            itemstack.getTag().putBoolean("active", false);
            craftMatrix.clearContent();
        }
    }

    private class SingleOutput2 extends SlotItemHandler
    {
        public SingleOutput2(IItemHandler iih, int id, int x, int y)
        {
            super(iih, id, x, y);
        }

        @Override
        public void setByPlayer(ItemStack stack) {
            super.setByPlayer(stack);
            if(stack != null && stack.getItem() instanceof LightsaberItem)
            {
                if(outputSlot.getItem() != null && outputSlot.getItem().getItem() instanceof LightsaberItem)
                {
                    ItemStack itemStack = new ItemStack(ModItems.doubleLightsaber.get());
                    itemStack.setTag(new CompoundTag());
                    itemStack.getTag().putString("type", LightsaberType.DOUBLE.toString());
                    itemStack.getTag().put("upper", outputSlot.getItem().getTag());
                    itemStack.getTag().put("lower", stack.getTag());
                    dualSlot.set(itemStack);
                }
            }
        }

        @Override
        public boolean mayPlace(ItemStack stack)
        {
            return stack.getItem() instanceof LightsaberItem && !(stack.getItem() instanceof LightsaberDoubleItem);
        }

        @Override
        public void onTake(Player player, ItemStack itemstack)
        {
            net.minecraftforge.event.ForgeEventFactory.firePlayerCraftingEvent(player, itemstack, this.container);
            itemstack.onCraftedBy(player.level(), player, 1);

            outputSlot2.remove(1);
            dualSlot.remove(1);
            itemstack.getTag().putBoolean("active", false);
            craftMatrix.clearContent();
        }
    }

    private class DoubleOutput extends SlotItemHandler
    {
        public DoubleOutput(IItemHandler iih, int id, int x, int y)
        {
            super(iih, id, x, y);
        }

        @Override
        public void setByPlayer(ItemStack stack) {
            super.setByPlayer(stack);

            if(stack != null && stack.getItem() instanceof LightsaberDoubleItem)
            {
                CompoundTag upperTag = stack.getTag().getCompound("upper");
                ItemStack itemStack = new ItemStack(ModItems.lightsaber.get());
                itemStack.setTag(upperTag);
                outputSlot.set(itemStack);
                itemStack = new ItemStack(ModItems.lightsaber.get());

                CompoundTag lower = stack.getTag().getCompound("lower");
                itemStack.setTag(lower);
                outputSlot2.set(itemStack);
            }
        }

        @Override
        public boolean mayPlace(ItemStack stack)
        {
            return stack.getItem() instanceof LightsaberItem && (stack.getItem() instanceof LightsaberDoubleItem);
        }

        @Override
        public void onTake(Player player, ItemStack itemstack)
        {
            net.minecraftforge.event.ForgeEventFactory.firePlayerCraftingEvent(player, itemstack, this.container);
            itemstack.onCraftedBy(player.level(), player, 1);

            outputSlot.remove(1);
            outputSlot2.remove(1);

            itemstack.getTag().getCompound("upper").putBoolean("active", false);
            itemstack.getTag().getCompound("lower").putBoolean("active", false);
            craftMatrix.clearContent();

        }
    }

    public Slot getDualSlot() {
        return dualSlot;
    }
}
