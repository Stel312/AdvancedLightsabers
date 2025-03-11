package com.stelmods.lightsabers.common.container;

import com.stelmods.lightsabers.common.block.BlockCrystal;
import com.stelmods.lightsabers.common.block.ModBlocks;
import com.stelmods.lightsabers.common.item.ItemFocusingCrystal;
import com.stelmods.lightsabers.common.item.LightsaberDoubleItem;
import com.stelmods.lightsabers.common.item.LightsaberItem;
import com.stelmods.lightsabers.common.item.ModItems;
import com.stelmods.lightsabers.common.item.parts.LightsaberBody;
import com.stelmods.lightsabers.common.item.parts.LightsaberEmiter;
import com.stelmods.lightsabers.common.item.parts.LightsaberPommel;
import com.stelmods.lightsabers.common.item.parts.LightsaberSwitch;
import com.stelmods.lightsabers.common.lightsaber.LightsaberType;
import com.stelmods.lightsabers.common.tileentity.LightsaberForgeBlockEntity;
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

public class LightsaberForgeContainer extends AbstractContainerMenu
{
    public final LightsaberForgeBlockEntity TE;
    private final ContainerLevelAccess canInteractWith;
    public InventoryLightsaberForge craftMatrix = new InventoryLightsaberForge(this);
    public Container craftResult = new ResultContainer();

    public static final int[][] SLOTS = { {43, 71}, {89, 71}, {107, 71}};
    public static List<Slot> inputSlots = new ArrayList<>();
    private Slot outputSlot, bodySlot, switchSlot, emitterSlot, pommelSlot, crystalSlot;
    private Slot focusCrystal1, focusCrystal2;

    public LightsaberForgeContainer(int id, Inventory inventoryPlayer)
    {
    	this(id, inventoryPlayer, (LightsaberForgeBlockEntity) null);
    }

    public LightsaberForgeContainer(final int windowId, final Inventory playerInventory, final FriendlyByteBuf buf) {
        this(windowId, playerInventory, getTileEntity(playerInventory, buf));
    }

    public LightsaberForgeContainer(int id, Inventory inventoryPlayer, LightsaberForgeBlockEntity tile)
    {
        super( ModContainers.LIGHTSABER_FORGE.get(), id);
        inputSlots.clear();
        TE = tile;
        canInteractWith = ContainerLevelAccess.create(TE.getLevel(), TE.getBlockPos());
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                emitterSlot=  addSlot(new Input(iih, 4,20, 17, LightsaberEmiter.class)));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                switchSlot = addSlot(new Input(iih, 5,20, 35, LightsaberSwitch.class) ));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                bodySlot = addSlot(new Input(iih, 6,20, 53, LightsaberBody.class)));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                pommelSlot = addSlot(new Input(iih, 7,20, 71, LightsaberPommel.class)));

        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
               crystalSlot= addSlot(new Crystal(iih, 3,66, 71)));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
               focusCrystal1= addSlot(new Focus(iih, 9,89, 71)));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
               focusCrystal2= addSlot(new Focus(iih, 8,107, 71)));

        for (int slot = 0; slot < SLOTS.length; ++slot)
        {
            int finalSlot = slot;
            int x = SLOTS[finalSlot][0];
            int y = SLOTS[finalSlot][1];
            TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                    addSlot(new SlotItemHandler(iih, finalSlot, x, y) {
                        @Override
                        public boolean mayPlace(ItemStack stack) {
                            return true;
                        }
                    }));
        }

        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih -> outputSlot = addSlot(new Output( 8, 136, 87)));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 114 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) { addSlot(new Slot(inventoryPlayer, i, 8 + i * 18, 172));}
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

        player.drop(focusCrystal1.remove(1),false);
        player.drop(focusCrystal2.remove(1),false);
        player.drop(crystalSlot.remove(1),false);
        player.drop(emitterSlot.remove(1),false);
        player.drop(switchSlot.remove(1),false);
        player.drop(pommelSlot.remove(1),false);
        player.drop(bodySlot.remove(1),false);
        outputSlot.remove(1);
            this.broadcastChanges();

    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    private static LightsaberForgeBlockEntity getTileEntity(final Inventory playerInventory, final FriendlyByteBuf buf) {
        final BlockEntity te = playerInventory.player.level().getBlockEntity(buf.readBlockPos());
        if (te instanceof LightsaberForgeBlockEntity) {
            return (LightsaberForgeBlockEntity) te;
        }
        throw new IllegalStateException("Tile Entity mismatch with container");
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(canInteractWith, playerIn, ModBlocks.lightsaberForge.get());
    }


    /*
    @Override
    public ItemStack quickMoveStack(Player player, int slotId)
    {
        ItemStack itemstack = null;
        Slot slot = this.slots.get(slotId);
        int OUTPUT = 8;

        if (slot != null && slot.hasItem())
        {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (slotId == OUTPUT)
            {
                if (!moveItemStackTo(itemstack1, OUTPUT + 1, OUTPUT + 36 + 1, true))
                {
                    return null;
                }
                
                //slot.onSlotChange(itemstack1, itemstack);
                slot.onQuickCraft(itemstack1, itemstack);
            }
            else if (slotId > OUTPUT)
            {
                boolean flag = true;

                for (int i = 0; i < OUTPUT; ++i)
                {
                    Slot slot1 = this.slots.get(i);
                    Item item = itemstack1.getItem();

                    if (item instanceof ILightsaberComponent && ((ILightsaberComponent) item).isCompatibleSlot(itemstack, i))
                    {
                        if (!slot1.hasItem() && !moveItemStackTo(itemstack1, i, i + 1, false))
                        {
                            return null;
                        }
                        
                        flag = false;
                    }
                }
                
                if (flag)
                {
                    if (slotId >= OUTPUT + 1 && slotId < OUTPUT + 28)
                    {
                        if (!moveItemStackTo(itemstack1, OUTPUT + 28, OUTPUT + 37, false))
                        {
                            return null;
                        }
                    }
                    else if (slotId >= OUTPUT + 28 && slotId < OUTPUT + 37 && !moveItemStackTo(itemstack1, OUTPUT + 1, OUTPUT + 28, false))
                    {
                        return null;
                    }
                }
            }
            else if (!moveItemStackTo(itemstack1, OUTPUT + 1, OUTPUT + 37, false))
            {
                return null;
            }

            if (itemstack1.getCount() == 0)
            {
                slot.set(null);
            }
            else
            {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return null;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }
*/
    @Override
    public boolean canTakeItemForPickAll(ItemStack itemstack, Slot slot)
    {
        return slot.container != craftResult && super.canTakeItemForPickAll(itemstack, slot);
    }

    private class Input extends Crystal
    {
        Class c;
        public Input(IItemHandler iih, int id, int x, int y, Class c)
        {
            super(iih, id, x, y);
            this.c = c;
        }
        @Override
        public boolean mayPlace(ItemStack itemstack)
        {
            return c.isInstance(itemstack.getItem());
        }

    }
    private class Focus extends Crystal
    {
        public Focus(IItemHandler iih, int id, int x, int y) {
            super(iih, id, x, y);
        }


        @Override
        public void onTake(Player player, ItemStack itemstack) {
            super.setChanged();
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.getItem() instanceof ItemFocusingCrystal;
        }
    }

    private class Crystal extends SlotItemHandler
    {

        public Crystal(IItemHandler iih, int id, int x, int y) {
            super(iih, id, x, y);
        }
        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof BlockCrystal;
        }
        @Override
        public int getMaxStackSize()
        {
            return 1;
        }


        @Override
        public void onTake(Player player, ItemStack itemstack)
        {
            //net.minecraftforge.event.ForgeEventFactory.firePlayerCraftingEvent(player, itemstack, this.container);

            itemstack.onCraftedBy(player.level(), player, 1);

            outputSlot.remove(1);
        }

        @Override
        public void setChanged() {
            super.setChanged();
            boolean isCrafted = true;
            for (Slot inputSlot : inputSlots) {
                if (inputSlot.getItem().getItem() == Items.AIR)
                {
                    isCrafted = false;
                    break;
                }
            }
            if(isCrafted)
            {
                ItemStack stack = new ItemStack(ModItems.lightsaber.get());
                stack.setTag(new CompoundTag());

                stack.getTag().putString("emitter", ForgeRegistries.ITEMS.getKey(emitterSlot.getItem().getItem()).toString());
                stack.getTag().putString("grip", ForgeRegistries.ITEMS.getKey(bodySlot.getItem().getItem()).toString());
                stack.getTag().putString("pommel", ForgeRegistries.ITEMS.getKey(pommelSlot.getItem().getItem()).toString());
                stack.getTag().putString("switch", ForgeRegistries.ITEMS.getKey(switchSlot.getItem().getItem()).toString());
                stack.getTag().putString("type", LightsaberType.SINGLE.toString());
                stack.getTag().putString("color", ForgeRegistries.BLOCKS.getKey(Block.byItem(crystalSlot.getItem().getItem())).toString());
                if(focusCrystal1.getItem().getItem() != Items.AIR)
                    stack.getTag().putString("focus1", ForgeRegistries.ITEMS.getKey(focusCrystal1.getItem().getItem()).toString());
                if(focusCrystal2.getItem().getItem() != Items.AIR)
                    stack.getTag().putString("focus2", ForgeRegistries.ITEMS.getKey(focusCrystal2.getItem().getItem()).toString());

                stack.getTag().putBoolean("active", false);
                outputSlot.set(stack);
            }
            else{
                outputSlot.remove(1);
            }
        }
    }


    private class Output extends Slot
    {
        public Output(int id, int x, int y)
        {
            super(craftResult, id, x, y);
        }

        @Override
        public void setByPlayer(ItemStack stack) {
            super.setByPlayer(stack);
            if(stack != null && stack.getItem() instanceof LightsaberItem)
            {
                bodySlot.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("grip"))).getDefaultInstance());
                emitterSlot.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("emitter"))).getDefaultInstance());
                pommelSlot.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("pommel"))).getDefaultInstance());
                switchSlot.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("switch"))).getDefaultInstance());
                crystalSlot.set(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(stack.getTag().getString("color"))).asItem().getDefaultInstance());
                if(stack.getTag().contains("focus1"))
                    focusCrystal1.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("focus1"))).asItem().getDefaultInstance());
                if(stack.getTag().contains("focus2"))
                    focusCrystal2.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("focus2"))).asItem().getDefaultInstance());

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
            crystalSlot.remove(1);
            emitterSlot.remove(1);
            switchSlot.remove(1);
            pommelSlot.remove(1);
            bodySlot.remove(1);
            outputSlot.remove(1);
            itemstack.getTag().putBoolean("active", false);
            craftMatrix.clearContent();
            focusCrystal1.remove(1);
            focusCrystal2.remove(1);
        }
    }
}
