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
    public static List<Slot> inputSlots = new ArrayList<>();
    public static List<Slot> inputSlots2 = new ArrayList<>();
    private Slot outputSlot, bodySlot, switchSlot, emitterSlot, pommelSlot, crystalSlot;
    private Slot outputSlot2, bodySlot2, switchSlot2, emitterSlot2, pommelSlot2, crystalSlot2;
    private Slot dualSlot;
    private Slot focusCrystal1_1, focusCrystal1_2;
    private Slot focusCrystal2_1, focusCrystal2_2;
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
        inputSlots.clear();
        inputSlots2.clear();
        TE = tile;
        canInteractWith = ContainerLevelAccess.create(TE.getLevel(), TE.getBlockPos());
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                emitterSlot=  addSlot(new Input(iih, 4,17, 17, LightsaberEmiter.class)));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                switchSlot = addSlot(new Input(iih, 5,17, 35, LightsaberSwitch.class) ));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                bodySlot = addSlot(new Input(iih, 6,17, 53, LightsaberBody.class)));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                pommelSlot = addSlot(new Input(iih, 7,16, 71, LightsaberPommel.class)));

        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                crystalSlot= addSlot(new Crystal(iih, 8,61, 71)));

        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                emitterSlot2=  addSlot(new Input2(iih, 9,38, 17, LightsaberEmiter.class)));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                switchSlot2 = addSlot(new Input2(iih, 10,38, 35, LightsaberSwitch.class) ));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                bodySlot2 = addSlot(new Input2(iih, 11,38, 53, LightsaberBody.class)));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                pommelSlot2 = addSlot(new Input2(iih, 12,38, 71, LightsaberPommel.class)));

        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                crystalSlot2 = addSlot(new Crystal2(iih, 13,61, 92)));

        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih -> outputSlot = addSlot(new SingleOutput(iih,14, 133, 72)));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih -> outputSlot2 = addSlot(new SingleOutput2(iih, 15, 133, 90)));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih -> dualSlot = addSlot(new DoubleOutput(iih, 17, 164,87 )));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                focusCrystal1_1= addSlot(new Focus(iih, 3,82, 71)));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                focusCrystal1_2= addSlot(new Focus(iih, 19,100, 71)));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                focusCrystal2_1= addSlot(new Focus2(iih, 20,82, 92)));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                focusCrystal2_2= addSlot(new Focus2(iih, 21,100, 92)));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventoryPlayer, j + i * 9 + 9, 26 + j * 18, 114 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) { addSlot(new Slot(inventoryPlayer, i, 26 + i * 18, 172));}
        inputSlots.add(bodySlot);
        inputSlots.add(switchSlot);
        inputSlots.add(emitterSlot);
        inputSlots.add(pommelSlot);
        inputSlots.add(crystalSlot);

        inputSlots2.add(bodySlot2);
        inputSlots2.add(switchSlot2);
        inputSlots2.add(emitterSlot2);
        inputSlots2.add(pommelSlot2);
        inputSlots2.add(crystalSlot2);
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

        player.drop(crystalSlot.remove(1),false);
        player.drop(emitterSlot.remove(1),false);
        player.drop(switchSlot.remove(1),false);
        player.drop(pommelSlot.remove(1),false);
        player.drop(bodySlot.remove(1),false);
        player.drop(focusCrystal1_2.remove(1),false);
        player.drop(focusCrystal1_2.remove(1),false);
        player.drop(focusCrystal2_1.remove(1),false);
        player.drop(focusCrystal2_2.remove(1),false);
        player.drop(crystalSlot2.remove(1),false);
        player.drop(emitterSlot2.remove(1),false);
        player.drop(switchSlot2.remove(1),false);
        player.drop(pommelSlot2.remove(1),false);
        player.drop(bodySlot2.remove(1),false);
        outputSlot.remove(1);
        outputSlot2.remove(1);
        dualSlot.remove(1);
        this.broadcastChanges();

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
            dualSlot.remove(1);
        }

        @Override
        public void setByPlayer(ItemStack p_270152_) {
            super.setByPlayer(p_270152_);
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
            if(isCrafted && !(outputSlot.getItem().getItem() instanceof LightsaberItem))
            {
                ItemStack stack = new ItemStack(ModItems.lightsaber.get());
                stack.setTag(new CompoundTag());

                stack.getTag().putString("focus1", ForgeRegistries.ITEMS.getKey(emitterSlot.getItem().getItem()).toString());
                stack.getTag().putString("focus2", ForgeRegistries.ITEMS.getKey(emitterSlot.getItem().getItem()).toString());
                stack.getTag().putString("emitter", ForgeRegistries.ITEMS.getKey(emitterSlot.getItem().getItem()).toString());
                stack.getTag().putString("grip", ForgeRegistries.ITEMS.getKey(bodySlot.getItem().getItem()).toString());
                stack.getTag().putString("pommel", ForgeRegistries.ITEMS.getKey(pommelSlot.getItem().getItem()).toString());
                stack.getTag().putString("switch", ForgeRegistries.ITEMS.getKey(switchSlot.getItem().getItem()).toString());
                stack.getTag().putString("type", LightsaberType.SINGLE.toString());
                stack.getTag().putString("color", ForgeRegistries.BLOCKS.getKey(Block.byItem(crystalSlot.getItem().getItem())).toString());
                stack.getTag().putBoolean("active", false);
                if(focusCrystal1_1.getItem().getItem() != Items.AIR)
                    stack.getTag().putString("focus1", ForgeRegistries.ITEMS.getKey(focusCrystal1_1.getItem().getItem()).toString());
                if(focusCrystal1_2.getItem().getItem() != Items.AIR)
                    stack.getTag().putString("focus2", ForgeRegistries.ITEMS.getKey(focusCrystal1_2.getItem().getItem()).toString());

                outputSlot.set(stack);
                if(outputSlot2.getItem() != null && outputSlot2.getItem().getItem() instanceof LightsaberItem)
                {
                    ItemStack itemStack = new ItemStack(ModItems.doubleLightsaber.get());
                    itemStack.setTag(new CompoundTag());
                    itemStack.getTag().putString("type", LightsaberType.DOUBLE.toString());
                    itemStack.getTag().put("upper", stack.getTag());
                    itemStack.getTag().put("lower", outputSlot2.getItem().getTag());
                    dualSlot.set(itemStack);
                }
                outputSlot.setChanged();
            }
        }

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
    private class Focus2 extends Crystal2
    {
        public Focus2(IItemHandler iih, int id, int x, int y) {
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
    private class Crystal2 extends SlotItemHandler
    {

        public Crystal2(IItemHandler iih, int id, int x, int y) {
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
            dualSlot.remove(1);
            outputSlot2.remove(1);
        }

        @Override
        public void setByPlayer(ItemStack p_270152_) {
            super.setByPlayer(p_270152_);
            outputSlot2.remove(1);
        }

        @Override
        public void setChanged() {
            super.setChanged();
            boolean isCrafted = true;
            for (Slot inputSlot : inputSlots2) {
                if (inputSlot.getItem().getItem() == Items.AIR)
                {
                    isCrafted = false;
                    break;
                }
            }
            if(isCrafted &&  !(outputSlot2.getItem().getItem() instanceof LightsaberItem) )
            {
                ItemStack stack = new ItemStack(ModItems.lightsaber.get());
                stack.setTag(new CompoundTag());

                stack.getTag().putString("emitter", ForgeRegistries.ITEMS.getKey(emitterSlot2.getItem().getItem()).toString());
                stack.getTag().putString("grip", ForgeRegistries.ITEMS.getKey(bodySlot2.getItem().getItem()).toString());
                stack.getTag().putString("pommel", ForgeRegistries.ITEMS.getKey(pommelSlot2.getItem().getItem()).toString());
                stack.getTag().putString("switch", ForgeRegistries.ITEMS.getKey(switchSlot2.getItem().getItem()).toString());
                stack.getTag().putString("type", LightsaberType.SINGLE.toString());
                stack.getTag().putString("color", ForgeRegistries.BLOCKS.getKey(Block.byItem(crystalSlot2.getItem().getItem())).toString());
                stack.getTag().putBoolean("active", false);
                if(focusCrystal2_1.getItem().getItem() != Items.AIR)
                    stack.getTag().putString("focus1", ForgeRegistries.ITEMS.getKey(focusCrystal2_1.getItem().getItem()).toString());
                if(focusCrystal2_2.getItem().getItem() != Items.AIR)
                    stack.getTag().putString("focus2", ForgeRegistries.ITEMS.getKey(focusCrystal2_2.getItem().getItem()).toString());

                outputSlot2.set(stack);
                if(outputSlot.getItem() != null && outputSlot.getItem().getItem() instanceof LightsaberItem)
                {
                    ItemStack itemStack = new ItemStack(ModItems.doubleLightsaber.get());
                    itemStack.setTag(new CompoundTag());
                    itemStack.getTag().putString("type", LightsaberType.DOUBLE.toString());
                    itemStack.getTag().put("upper", outputSlot.getItem().getTag());
                    itemStack.getTag().put("lower", stack.getTag());
                    dualSlot.set(itemStack);
                }
                outputSlot2.setChanged();
            }
        }

    }
    private class Input2 extends Crystal2
    {
        Class c;
        public Input2(IItemHandler iih, int id, int x, int y, Class c)
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

    private class SingleOutput extends SlotItemHandler
    {
        public SingleOutput(IItemHandler iih, int id, int x, int y)
        {
            super(iih, id, x, y);
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
                    focusCrystal1_1.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("focus1"))).asItem().getDefaultInstance());
                if(stack.getTag().contains("focus2"))
                    focusCrystal1_2.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("focus2"))).asItem().getDefaultInstance());

                if(outputSlot2.getItem() != null && outputSlot2.getItem().getItem() instanceof LightsaberItem)
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
            crystalSlot.remove(1);
            emitterSlot.remove(1);
            switchSlot.remove(1);
            pommelSlot.remove(1);
            bodySlot.remove(1);
            outputSlot.remove(1);
            dualSlot.remove(1);
            focusCrystal1_1.remove(1);
            focusCrystal1_2.remove(1);

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
                bodySlot2.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("grip"))).getDefaultInstance());
                emitterSlot2.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("emitter"))).getDefaultInstance());
                pommelSlot2.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("pommel"))).getDefaultInstance());
                switchSlot2.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("switch"))).getDefaultInstance());
                crystalSlot2.set(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(stack.getTag().getString("color"))).asItem().getDefaultInstance());
                if(stack.getTag().contains("focus1"))
                    focusCrystal2_1.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("focus1"))).asItem().getDefaultInstance());
                if(stack.getTag().contains("focus2"))
                    focusCrystal2_2.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("focus2"))).asItem().getDefaultInstance());

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
            crystalSlot2.remove(1);
            emitterSlot2.remove(1);
            switchSlot2.remove(1);
            pommelSlot2.remove(1);
            bodySlot2.remove(1);
            outputSlot2.remove(1);
            dualSlot.remove(1);

            focusCrystal2_1.remove(1);
            focusCrystal2_2.remove(1);
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
                bodySlot.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(upperTag.getString("grip"))).getDefaultInstance());
                emitterSlot.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(upperTag.getString("emitter"))).getDefaultInstance());
                pommelSlot.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(upperTag.getString("pommel"))).getDefaultInstance());
                switchSlot.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(upperTag.getString("switch"))).getDefaultInstance());
                crystalSlot.set(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(upperTag.getString("color"))).asItem().getDefaultInstance());
                if(stack.getTag().contains("focus1"))
                    focusCrystal1_1.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("focus1"))).asItem().getDefaultInstance());
                if(stack.getTag().contains("focus2"))
                    focusCrystal2_1.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("focus2"))).asItem().getDefaultInstance());

                itemStack = new ItemStack(ModItems.lightsaber.get());

                CompoundTag lower = stack.getTag().getCompound("lower");
                itemStack.setTag(lower);
                outputSlot2.set(itemStack);
                bodySlot2.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(lower.getString("grip"))).getDefaultInstance());
                emitterSlot2.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(lower.getString("emitter"))).getDefaultInstance());
                pommelSlot2.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(lower.getString("pommel"))).getDefaultInstance());
                switchSlot2.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(lower.getString("switch"))).getDefaultInstance());
                crystalSlot2.set(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(lower.getString("color"))).asItem().getDefaultInstance());
                if(stack.getTag().contains("focus1"))
                    focusCrystal2_1.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("focus1"))).asItem().getDefaultInstance());
                if(stack.getTag().contains("focus2"))
                    focusCrystal2_2.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("focus2"))).asItem().getDefaultInstance());


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
            crystalSlot.remove(1);
            emitterSlot.remove(1);
            switchSlot.remove(1);
            pommelSlot.remove(1);
            bodySlot.remove(1);
            outputSlot.remove(1);
            crystalSlot2.remove(1);
            emitterSlot2.remove(1);
            switchSlot2.remove(1);
            pommelSlot2.remove(1);
            bodySlot2.remove(1);
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
