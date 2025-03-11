package com.stelmods.lightsabers.common.item;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LightsaberDoubleItem extends LightsaberItem {

    /*@Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        stack.getTag().getCompound("upper").putBoolean("active", !stack.getTag().getCompound("upper").getBoolean("active"));
        stack.getTag().getCompound("lower").putBoolean("active", !stack.getTag().getCompound("lower").getBoolean("active"));
        return InteractionResultHolder.success(stack);
    }*/

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
       // System.out.println(stack.getTag().getFloat("length"));
        //stack.getTag().putFloat("length", 1F);
        if(!stack.getTag().contains("length")) {
            stack.getTag().getCompound("upper").putFloat("length",0);
            stack.getTag().getCompound("lower").putFloat("length",0);
            stack.getTag().getCompound("upper").putBoolean("active",false);
            stack.getTag().getCompound("lower").putBoolean("active",false);
        }
        if(stack.getTag().getCompound("upper").contains("length")) {
            float len = stack.getTag().getCompound("upper").getFloat("length");
            if (!stack.getTag().getCompound("upper").getBoolean("active")) { //If upper is turned off
                if (len > 0) { //And still has length
                    //Reduce it
                    stack.getTag().getCompound("upper").putFloat("length", Math.max(len - 0.2F,0));
                }
            } else { //If is turned on
                if (len < 1) { //And still has no length
                    //Increase it
                    stack.getTag().getCompound("upper").putFloat("length", Math.min(len + 0.3F,1));
                }
            }
        }

        if(stack.getTag().getCompound("lower").contains("length")) {
            float len = stack.getTag().getCompound("lower").getFloat("length");
            if (!stack.getTag().getCompound("lower").getBoolean("active")) { //If lower is turned off
                if (len > 0) { //And still has length
                    //Reduce it
                    stack.getTag().getCompound("lower").putFloat("length", Math.max(len - 0.2F,0));
                }
            } else { //If is turned on
                if (len < 1) { //And still has no length
                    //Increase it
                    stack.getTag().getCompound("lower").putFloat("length", Math.min(len + 0.3F,1));
                }
            }
        }
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!stack.getTag().getCompound("upper").contains("active")) {
            stack.getTag().getCompound("upper").putBoolean("active", false);
            stack.getTag().getCompound("upper").putFloat("length", 0F);
        }
        if (!stack.getTag().getCompound("lower").contains("active")) {
            stack.getTag().getCompound("lower").putBoolean("active", false);
            stack.getTag().getCompound("lower").putFloat("length", 0F);
        }

        return super.onEntityItemUpdate(stack, entity);

    }
}
