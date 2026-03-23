package com.stelmods.lightsabers.common.container;

import com.stelmods.lightsabers.common.block.BlockCrystal;
import com.stelmods.lightsabers.common.block.ModBlocks;
import com.stelmods.lightsabers.common.component.LightsaberDataComponents;
import com.stelmods.lightsabers.common.item.ItemFocusingCrystal;
import com.stelmods.lightsabers.common.item.LightsaberDoubleItem;
import com.stelmods.lightsabers.common.item.LightsaberItem;
import com.stelmods.lightsabers.common.item.ModItems;
import com.stelmods.lightsabers.common.item.parts.LightsaberBody;
import com.stelmods.lightsabers.common.item.parts.LightsaberEmiter;
import com.stelmods.lightsabers.common.item.parts.LightsaberPommel;
import com.stelmods.lightsabers.common.item.parts.LightsaberSwitch;
import com.stelmods.lightsabers.common.tileentity.LightsaberForgeBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class LightsaberForgeContainer extends AbstractContainerMenu {

    public final LightsaberForgeBlockEntity TE;
    private final ContainerLevelAccess canInteractWith;

    private Slot outputSlot, bodySlot, switchSlot, emitterSlot, pommelSlot, crystalSlot;
    public Slot focusCrystal1, focusCrystal2;

    private boolean internalUpdate = false;

    public LightsaberForgeContainer(int id, Inventory inv) {
        this(id, inv, (LightsaberForgeBlockEntity) null);
    }

    public LightsaberForgeContainer(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, getTileEntity(inv, buf));
    }

    public LightsaberForgeContainer(int id, Inventory inv, LightsaberForgeBlockEntity tile) {
        super(ModContainers.LIGHTSABER_FORGE.get(), id);

        TE = tile;
        canInteractWith = ContainerLevelAccess.create(tile.getLevel(), tile.getBlockPos());

        IItemHandler iih = TE.inventory.get();

        emitterSlot = addSlot(new PartSlot(iih, 4, 20, 17, LightsaberEmiter.class));
        switchSlot = addSlot(new PartSlot(iih, 5, 20, 35, LightsaberSwitch.class));
        bodySlot = addSlot(new PartSlot(iih, 6, 20, 53, LightsaberBody.class));
        pommelSlot = addSlot(new PartSlot(iih, 7, 20, 71, LightsaberPommel.class));

        crystalSlot = addSlot(new CrystalSlot(iih, 3, 66, 71));

        focusCrystal1 = addSlot(new FocusSlot(iih, 9, 89, 71));
        focusCrystal2 = addSlot(new FocusSlot(iih, 8, 107, 71));

        outputSlot = addSlot(new OutputSlot(iih, 10, 136, 87));

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                addSlot(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 114 + i * 18));

        for (int i = 0; i < 9; i++)
            addSlot(new Slot(inv, i, 8 + i * 18, 172));
    }

    private static LightsaberForgeBlockEntity getTileEntity(Inventory inv, FriendlyByteBuf buf) {
        BlockEntity te = inv.player.level().getBlockEntity(buf.readBlockPos());
        if (te instanceof LightsaberForgeBlockEntity forge)
            return forge;
        throw new IllegalStateException("Tile Entity mismatch with container");
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(canInteractWith, player, ModBlocks.lightsaberForge.get());
    }

    private boolean hasAllParts() {
        return !bodySlot.getItem().isEmpty()
                && !switchSlot.getItem().isEmpty()
                && !emitterSlot.getItem().isEmpty()
                && !pommelSlot.getItem().isEmpty()
                && !crystalSlot.getItem().isEmpty();
    }

    private boolean isCompleteSaber(ItemStack stack) {
        return stack.getItem() instanceof LightsaberItem
                && stack.has(LightsaberDataComponents.LIGHTSABER);
    }

    private boolean isDoubleSaber(ItemStack stack) {
        return stack.getItem() instanceof LightsaberDoubleItem
                && stack.has(LightsaberDataComponents.DOUBLE_LIGHTSABER);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack original = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot == null || !slot.hasItem())
            return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        original = stack.copy();

        boolean isPlayerInv = index >= 11;
        boolean moved = false;

        if (!isPlayerInv) {
            moved = moveItemStackTo(stack, 11, this.slots.size(), false);
            if (!moved) return ItemStack.EMPTY;

            slot.setChanged();
            return original;
        }

        if (stack.getItem() instanceof LightsaberDoubleItem) {
            if (moveItemStackTo(stack, outputSlot.index, outputSlot.index + 1, false)) {
                slot.setChanged();
                return original;
            }
        }

        if (stack.getItem() instanceof LightsaberItem) {
            if (moveItemStackTo(stack, focusCrystal1.index, focusCrystal1.index + 1, false)
                    || moveItemStackTo(stack, focusCrystal2.index, focusCrystal2.index + 1, false)) {
                slot.setChanged();
                return original;
            }
        }

        if (stack.getItem() instanceof ItemFocusingCrystal) {
            if (moveItemStackTo(stack, focusCrystal1.index, focusCrystal1.index + 1, false)
                    || moveItemStackTo(stack, focusCrystal2.index, focusCrystal2.index + 1, false)) {
                slot.setChanged();
                return original;
            }
        }

        if (stack.getItem() instanceof BlockItem bi && bi.getBlock() instanceof BlockCrystal) {
            if (moveItemStackTo(stack, crystalSlot.index, crystalSlot.index + 1, false)) {
                slot.setChanged();
                return original;
            }
        }

        if (stack.getItem() instanceof LightsaberPommel) {
            if (moveItemStackTo(stack, pommelSlot.index, pommelSlot.index + 1, false)) {
                slot.setChanged();
                return original;
            }
        }

        if (stack.getItem() instanceof LightsaberBody) {
            if (moveItemStackTo(stack, bodySlot.index, bodySlot.index + 1, false)) {
                slot.setChanged();
                return original;
            }
        }

        if (stack.getItem() instanceof LightsaberSwitch) {
            if (moveItemStackTo(stack, switchSlot.index, switchSlot.index + 1, false)) {
                slot.setChanged();
                return original;
            }
        }

        if (stack.getItem() instanceof LightsaberEmiter) {
            if (moveItemStackTo(stack, emitterSlot.index, emitterSlot.index + 1, false)) {
                slot.setChanged();
                return original;
            }
        }

        int invStart = 11;
        int invEnd = invStart + 27;
        int hotbarStart = invEnd;
        int hotbarEnd = hotbarStart + 9;

        if (index >= invStart && index < invEnd) {
            moved = moveItemStackTo(stack, hotbarStart, hotbarEnd, false);
        } else if (index >= hotbarStart && index < hotbarEnd) {
            moved = moveItemStackTo(stack, invStart, invEnd, false);
        }

        if (!moved) return ItemStack.EMPTY;

        slot.setChanged();
        return original;
    }

    public Slot getOutputSlot() {
        return outputSlot;
    }

    public void updateOutput() {
        internalUpdate = true;
        try {
            IItemHandler handler = TE.inventory.get();

            ItemStack out = handler.getStackInSlot(10);
            ItemStack s1 = handler.getStackInSlot(9);
            ItemStack s2 = handler.getStackInSlot(8);

            if (isDoubleSaber(out)
                    && s1.isEmpty()
                    && s2.isEmpty()) {
                var data = out.get(LightsaberDataComponents.DOUBLE_LIGHTSABER);

                ItemStack upper = new ItemStack(ModItems.lightsaber.get());
                upper.set(LightsaberDataComponents.LIGHTSABER, data.upper());
                upper.set(LightsaberDataComponents.LIGHTSABER_ACTIVE, false);
                upper.set(LightsaberDataComponents.LIGHTSABER_LENGTH, 0f);

                ItemStack lower = new ItemStack(ModItems.lightsaber.get());
                lower.set(LightsaberDataComponents.LIGHTSABER, data.lower());
                lower.set(LightsaberDataComponents.LIGHTSABER_ACTIVE, false);
                lower.set(LightsaberDataComponents.LIGHTSABER_LENGTH, 0f);

                handler.extractItem(9, 64, false);
                handler.insertItem(9, upper, false);

                handler.extractItem(8, 64, false);
                handler.insertItem(8, lower, false);

                handler.extractItem(10, 64, false);
                return;
            }

            if (isCompleteSaber(out)
                    && bodySlot.getItem().isEmpty()
                    && switchSlot.getItem().isEmpty()
                    && emitterSlot.getItem().isEmpty()
                    && pommelSlot.getItem().isEmpty()
                    && crystalSlot.getItem().isEmpty()
                    && s1.isEmpty()
                    && s2.isEmpty()) {

                var data = out.get(LightsaberDataComponents.LIGHTSABER);

                ItemStack pommel = data.pomel().isEmpty() ? ItemStack.EMPTY :
                        new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(data.pomel())));
                ItemStack hilt = data.hilt().isEmpty() ? ItemStack.EMPTY :
                        new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(data.hilt())));
                ItemStack sw = data.switch_().isEmpty() ? ItemStack.EMPTY :
                        new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(data.switch_())));
                ItemStack emitter = data.emitter().isEmpty() ? ItemStack.EMPTY :
                        new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(data.emitter())));
                ItemStack kyber = data.kyber().isEmpty() ? ItemStack.EMPTY :
                        new ItemStack(BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(data.kyber())));

                ItemStack f1 = data.focus1().isEmpty() ? ItemStack.EMPTY :
                        new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(data.focus1())));
                ItemStack f2 = data.focus2().isEmpty() ? ItemStack.EMPTY :
                        new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(data.focus2())));

                handler.extractItem(4, 64, false);
                handler.insertItem(4, emitter, false);

                handler.extractItem(5, 64, false);
                handler.insertItem(5, sw, false);

                handler.extractItem(6, 64, false);
                handler.insertItem(6, hilt, false);

                handler.extractItem(7, 64, false);
                handler.insertItem(7, pommel, false);

                handler.extractItem(3, 64, false);
                handler.insertItem(3, kyber, false);

                handler.extractItem(9, 64, false);
                handler.insertItem(9, f1, false);

                handler.extractItem(8, 64, false);
                handler.insertItem(8, f2, false);

                handler.extractItem(10, 64, false);
                return;
            }

            if (isCompleteSaber(out) && hasAllParts()) {

                String hilt = BuiltInRegistries.ITEM.getKey(bodySlot.getItem().getItem()).toString();
                String pommel = BuiltInRegistries.ITEM.getKey(pommelSlot.getItem().getItem()).toString();
                String emitter = BuiltInRegistries.ITEM.getKey(emitterSlot.getItem().getItem()).toString();
                String switch_ = BuiltInRegistries.ITEM.getKey(switchSlot.getItem().getItem()).toString();

                String focus1 = s1.isEmpty() ? "" :
                        BuiltInRegistries.ITEM.getKey(s1.getItem()).toString();
                String focus2 = s2.isEmpty() ? "" :
                        BuiltInRegistries.ITEM.getKey(s2.getItem()).toString();

                String kyber = BuiltInRegistries.BLOCK
                        .getKey(Block.byItem(crystalSlot.getItem().getItem()))
                        .toString();

                LightsaberDataComponents.LightsaberData data =
                        new LightsaberDataComponents.LightsaberData(
                                pommel, hilt, switch_, emitter, focus1, focus2, kyber
                        );

                out.set(LightsaberDataComponents.LIGHTSABER, data);
                return;
            }

            if (isCompleteSaber(s1) && isCompleteSaber(s2)) {
                ItemStack dbl = new ItemStack(ModItems.doubleLightsaber.get());
                dbl.set(LightsaberDataComponents.DOUBLE_LIGHTSABER,
                        new LightsaberDataComponents.DoubleLightsaberData(
                                s1.get(LightsaberDataComponents.LIGHTSABER),
                                s2.get(LightsaberDataComponents.LIGHTSABER)
                        ));
                dbl.set(LightsaberDataComponents.LIGHTSABER_ACTIVE, false);
                dbl.set(LightsaberDataComponents.LIGHTSABER_LENGTH, 0f);

                handler.extractItem(10, 64, false);
                handler.insertItem(10, dbl, false);
                return;
            }

            if (hasAllParts()) {
                String hilt = BuiltInRegistries.ITEM.getKey(bodySlot.getItem().getItem()).toString();
                String pommel = BuiltInRegistries.ITEM.getKey(pommelSlot.getItem().getItem()).toString();
                String emitter = BuiltInRegistries.ITEM.getKey(emitterSlot.getItem().getItem()).toString();
                String switch_ = BuiltInRegistries.ITEM.getKey(switchSlot.getItem().getItem()).toString();

                String focus1 = s1.isEmpty() ? "" :
                        BuiltInRegistries.ITEM.getKey(s1.getItem()).toString();
                String focus2 = s2.isEmpty() ? "" :
                        BuiltInRegistries.ITEM.getKey(s2.getItem()).toString();

                String kyber = BuiltInRegistries.BLOCK
                        .getKey(Block.byItem(crystalSlot.getItem().getItem()))
                        .toString();

                LightsaberDataComponents.LightsaberData data =
                        new LightsaberDataComponents.LightsaberData(
                                pommel, hilt, switch_, emitter, focus1, focus2, kyber
                        );

                ItemStack single = new ItemStack(ModItems.lightsaber.get());
                single.set(LightsaberDataComponents.LIGHTSABER, data);
                single.set(LightsaberDataComponents.LIGHTSABER_ACTIVE, false);
                single.set(LightsaberDataComponents.LIGHTSABER_LENGTH, 0f);

                handler.extractItem(10, 64, false);
                handler.insertItem(10, single, false);
                return;
            }

            handler.extractItem(10, 64, false);
        } finally {
            internalUpdate = false;
        }
    }

    private class PartSlot extends SlotItemHandler {
        private final Class<?> type;

        public PartSlot(IItemHandler iih, int id, int x, int y, Class<?> type) {
            super(iih, id, x, y);
            this.type = type;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return type.isInstance(stack.getItem());
        }

        @Override
        public void setChanged() {
            if (!internalUpdate) {
                updateOutput();
            }
            super.setChanged();
        }
    }

    private class FocusSlot extends SlotItemHandler {
        public FocusSlot(IItemHandler iih, int id, int x, int y) {
            super(iih, id, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.getItem() instanceof ItemFocusingCrystal
                    || stack.getItem() instanceof LightsaberItem;
        }

        @Override
        public void setChanged() {
            if (!internalUpdate) {
                updateOutput();
            }
            super.setChanged();
        }
    }

    private class CrystalSlot extends SlotItemHandler {
        public CrystalSlot(IItemHandler iih, int id, int x, int y) {
            super(iih, id, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.getItem() instanceof BlockItem bi && bi.getBlock() instanceof BlockCrystal;
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public void setChanged() {
            if (!internalUpdate) {
                updateOutput();
            }
            super.setChanged();
        }
    }

    private class OutputSlot extends SlotItemHandler {
        public OutputSlot(IItemHandler iih, int id, int x, int y) {
            super(iih, id, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.getItem() instanceof LightsaberItem
                    || stack.getItem() instanceof LightsaberDoubleItem;
        }

        @Override
        public void setChanged() {
            if (!internalUpdate) {
                updateOutput();
            }
            super.setChanged();
        }

        @Override
        public void onTake(Player player, ItemStack itemstack) {
            EventHooks.firePlayerCraftingEvent(player, itemstack, this.container);
            itemstack.onCraftedBy(player.level(), player, 1);

            IItemHandler handler = TE.inventory.get();
            ItemStack s1 = handler.getStackInSlot(9);
            ItemStack s2 = handler.getStackInSlot(8);

            bodySlot.set(ItemStack.EMPTY);
            switchSlot.set(ItemStack.EMPTY);
            emitterSlot.set(ItemStack.EMPTY);
            pommelSlot.set(ItemStack.EMPTY);
            crystalSlot.set(ItemStack.EMPTY);
            focusCrystal1.set(ItemStack.EMPTY);
            focusCrystal2.set(ItemStack.EMPTY);
            itemstack.set(LightsaberDataComponents.LIGHTSABER_ACTIVE, false);
            itemstack.set(LightsaberDataComponents.LIGHTSABER_LENGTH, 0f);
        }
    }
}
