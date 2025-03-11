package com.stelmods.lightsabers.common.item;

import com.stelmods.lightsabers.client.render.item.RenderItemLightsaber;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class LightsaberItem extends SwordItem implements IClientItemExtensions {

    public LightsaberItem() {
        super(Tiers.NETHERITE, 8, 1, new Item.Properties().stacksTo(1));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public RenderItemLightsaber getCustomRenderer() {
                return RenderItemLightsaber.bewlr;
            }
        });
    }

    /*@Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        stack.getTag().putBoolean("active", !stack.getTag().getBoolean("active"));
        return InteractionResultHolder.success(stack);
    }*/

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        //System.out.println(stack.getTag().getFloat("length"));
        //stack.getTag().putFloat("length", 1F);
        if(!stack.getTag().contains("length")) {
            stack.getTag().putFloat("length", 0);
            stack.getTag().putBoolean("active", false);
        }

        float len = stack.getTag().getFloat("length");
        if (!stack.getTag().getBoolean("active")) { //If is turned off
            if (len > 0) { //And still has length
                //Reduce it
                stack.getTag().putFloat("length", Math.max(len - 0.2F,0));
            }
        } else { //If is turned on
            if (len < 1) { //And still has no length
                //Increase it
                stack.getTag().putFloat("length", Math.min(len + 0.3F,1));
            }
        }

        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!stack.getTag().contains("active")) {
            stack.getTag().putBoolean("active", false);
            stack.getTag().putFloat("length", 0F);
        }
        return super.onEntityItemUpdate(stack, entity);
    }
}
