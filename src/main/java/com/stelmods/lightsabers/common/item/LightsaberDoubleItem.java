package com.stelmods.lightsabers.common.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LightsaberDoubleItem extends LightsaberItem {

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        stack.getTag().getCompound("upper").putBoolean("active", !stack.getTag().getCompound("upper").getBoolean("active"));
        stack.getTag().getCompound("lower").putBoolean("active", !stack.getTag().getCompound("lower").getBoolean("active"));
        return InteractionResultHolder.success(stack);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!stack.getTag().getCompound("upper").contains("active")) {
            stack.getTag().getCompound("upper").putBoolean("active", false);
        }
        if (!stack.getTag().getCompound("lower").contains("active")) {
            stack.getTag().getCompound("lower").putBoolean("active", false);
        }
        return super.onEntityItemUpdate(stack, entity);

    }
}
