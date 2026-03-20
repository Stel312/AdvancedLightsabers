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
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import java.util.ArrayList;
import java.util.List;

public class LightsaberForgeContainer extends AbstractContainerMenu {

    public final LightsaberForgeBlockEntity TE;
    private final ContainerLevelAccess canInteractWith;
    public InventoryLightsaberForge craftMatrix = new InventoryLightsaberForge(this);
    public Container craftResult = new ResultContainer();

    public static final int[][] SLOTS = {{43, 71}, {89, 71}, {107, 71}};
    public static final List<Slot> inputSlots = new ArrayList<>();

    private Slot outputSlot, bodySlot, switchSlot, emitterSlot, pommelSlot, crystalSlot;
    public Slot focusCrystal1, focusCrystal2;

    public LightsaberForgeContainer(int id, Inventory inventoryPlayer) {
        this(id, inventoryPlayer, (LightsaberForgeBlockEntity) null);
    }

    public LightsaberForgeContainer(int windowId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(windowId, playerInventory, getTileEntity(playerInventory, buf));
    }

    public LightsaberForgeContainer(int id, Inventory inventoryPlayer, LightsaberForgeBlockEntity tile) {
        super(ModContainers.LIGHTSABER_FORGE.get(), id);

        TE = tile;
        canInteractWith = ContainerLevelAccess.create(TE.getLevel(), TE.getBlockPos());

        IItemHandler iih = TE.inventory.get();

        inputSlots.clear();

        emitterSlot = addSlot(new Input(iih, 4, 20, 17, LightsaberEmiter.class));
        switchSlot = addSlot(new Input(iih, 5, 20, 35, LightsaberSwitch.class));
        bodySlot = addSlot(new Input(iih, 6, 20, 53, LightsaberBody.class));
        pommelSlot = addSlot(new Input(iih, 7, 20, 71, LightsaberPommel.class));

        crystalSlot = addSlot(new Crystal(iih, 3, 66, 71));

        // These two slots serve dual purpose:
        // - Focus crystals for single sabers
        // - Saber slots for combining/splitting
        focusCrystal1 = addSlot(new Focus(iih, 9, 89, 71));
        focusCrystal2 = addSlot(new Focus(iih, 8, 107, 71));

        for (int slot = 0; slot < SLOTS.length; slot++) {
            int x = SLOTS[slot][0];
            int y = SLOTS[slot][1];
            addSlot(new SlotItemHandler(iih, slot, x, y));
        }

        outputSlot = addSlot(new Output(8, 136, 87));

        // Player inventory
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                addSlot(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 114 + i * 18));

        for (int i = 0; i < 9; i++)
            addSlot(new Slot(inventoryPlayer, i, 8 + i * 18, 172));

        inputSlots.add(bodySlot);
        inputSlots.add(switchSlot);
        inputSlots.add(emitterSlot);
        inputSlots.add(pommelSlot);
        inputSlots.add(crystalSlot);

        slotsChanged(craftMatrix);
    }

    public Slot getOutputSlot() {
        return outputSlot;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);

        player.drop(focusCrystal1.remove(1), false);
        player.drop(focusCrystal2.remove(1), false);
        player.drop(crystalSlot.remove(1), false);
        player.drop(emitterSlot.remove(1), false);
        player.drop(switchSlot.remove(1), false);
        player.drop(pommelSlot.remove(1), false);
        player.drop(bodySlot.remove(1), false);
        outputSlot.remove(1);

        broadcastChanges();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
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

    @Override
    public boolean canTakeItemForPickAll(ItemStack item, Slot slot) {
        return slot.container != craftResult && super.canTakeItemForPickAll(item, slot);
    }

    // ------------------------------------------------------------
    // Helper logic
    // ------------------------------------------------------------

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

    // ------------------------------------------------------------
    // Slot Types
    // ------------------------------------------------------------

    private class Input extends Crystal {
        private final Class<?> type;

        public Input(IItemHandler iih, int id, int x, int y, Class<?> type) {
            super(iih, id, x, y);
            this.type = type;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return type.isInstance(stack.getItem());
        }
    }

    private class Focus extends Crystal {
        public Focus(IItemHandler iih, int id, int x, int y) {
            super(iih, id, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            // Accept focus crystals OR sabers
            return stack.getItem() instanceof ItemFocusingCrystal
                    || stack.getItem() instanceof LightsaberItem;
        }
    }

    private class Crystal extends SlotItemHandler {
        public Crystal(IItemHandler iih, int id, int x, int y) {
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
        public void onTake(Player player, ItemStack item) {
            item.onCraftedBy(player.level(), player, 1);
            outputSlot.remove(1);
        }

        @Override
        public void setChanged() {
            super.setChanged();

            ItemStack s1 = focusCrystal1.getItem();
            ItemStack s2 = focusCrystal2.getItem();

            // 1) Two singles → double
            if (isCompleteSaber(s1) && isCompleteSaber(s2)) {
                var upper = s1.get(LightsaberDataComponents.LIGHTSABER);
                var lower = s2.get(LightsaberDataComponents.LIGHTSABER);

                ItemStack out = new ItemStack(ModItems.doubleLightsaber.get());
                out.set(LightsaberDataComponents.DOUBLE_LIGHTSABER,
                        new LightsaberDataComponents.DoubleLightsaberData(upper, lower));
                out.set(LightsaberDataComponents.LIGHTSABER_ACTIVE, false);
                out.set(LightsaberDataComponents.LIGHTSABER_LENGTH, 0f);

                outputSlot.set(out);
                return;
            }

            // 2) Double → two singles
            if (isDoubleSaber(s1)) {
                var data = s1.get(LightsaberDataComponents.DOUBLE_LIGHTSABER);

                ItemStack upper = new ItemStack(ModItems.lightsaber.get());
                upper.set(LightsaberDataComponents.LIGHTSABER, data.upper());
                upper.set(LightsaberDataComponents.LIGHTSABER_ACTIVE, false);
                upper.set(LightsaberDataComponents.LIGHTSABER_LENGTH, 0f);

                ItemStack lower = new ItemStack(ModItems.lightsaber.get());
                lower.set(LightsaberDataComponents.LIGHTSABER, data.lower());
                lower.set(LightsaberDataComponents.LIGHTSABER_ACTIVE, false);
                lower.set(LightsaberDataComponents.LIGHTSABER_LENGTH, 0f);

                focusCrystal1.set(upper);
                focusCrystal2.set(lower);
                outputSlot.remove(1);
                return;
            }

            // 3) Parts → single saber
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
                                hilt, pommel, emitter, switch_, focus1, focus2, kyber
                        );

                ItemStack out = new ItemStack(ModItems.lightsaber.get());
                out.set(LightsaberDataComponents.LIGHTSABER, data);
                out.set(LightsaberDataComponents.LIGHTSABER_ACTIVE, false);
                out.set(LightsaberDataComponents.LIGHTSABER_LENGTH, 0f);

                outputSlot.set(out);
                return;
            }

            // 4) Nothing valid
            outputSlot.remove(1);
        }
    }

    private class Output extends Slot {
        public Output(int id, int x, int y) {
            super(craftResult, id, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.getItem() instanceof LightsaberItem;
        }

        @Override
        public void onTake(Player player, ItemStack itemstack) {
            EventHooks.firePlayerCraftingEvent(player, itemstack, this.container);
            itemstack.onCraftedBy(player.level(), player, 1);

            bodySlot.remove(1);
            switchSlot.remove(1);
            emitterSlot.remove(1);
            pommelSlot.remove(1);
            crystalSlot.remove(1);
            focusCrystal1.remove(1);
            focusCrystal2.remove(1);
            outputSlot.remove(1);

            itemstack.set(LightsaberDataComponents.LIGHTSABER_ACTIVE, false);
            itemstack.set(LightsaberDataComponents.LIGHTSABER_LENGTH, 0f);

            craftMatrix.clearContent();
        }
    }
}
