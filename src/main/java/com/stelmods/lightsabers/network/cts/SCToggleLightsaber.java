package com.stelmods.lightsabers.network.cts;

import com.stelmods.lightsabers.common.item.LightsaberDoubleItem;
import com.stelmods.lightsabers.common.item.LightsaberItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SCToggleLightsaber {
    public SCToggleLightsaber(){}

    public void encode(FriendlyByteBuf buffer) {
    }

    public static SCToggleLightsaber decode(FriendlyByteBuf buffer) {
        return new SCToggleLightsaber();
    }
    public static void handle(SCToggleLightsaber message, final Supplier<NetworkEvent.Context> ctx) {
        Player senderPlayer = ctx.get().getSender();
        ItemStack mainStack = senderPlayer.getMainHandItem();
        ItemStack offHandStack = senderPlayer.getOffhandItem();

        if(!mainStack.isEmpty()) {
            if (mainStack.getItem() instanceof LightsaberDoubleItem) {
                mainStack.getTag().getCompound("upper").putBoolean("active", !mainStack.getTag().getCompound("upper").getBoolean("active"));
                mainStack.getTag().getCompound("lower").putBoolean("active", !mainStack.getTag().getCompound("lower").getBoolean("active"));
            } else if (mainStack.getItem() instanceof LightsaberItem) {
                mainStack.getTag().putBoolean("active", !mainStack.getTag().getBoolean("active"));
            }
        }
        if(!offHandStack.isEmpty()) {
            if (offHandStack.getItem() instanceof LightsaberDoubleItem) {
                offHandStack.getTag().getCompound("upper").putBoolean("active", !offHandStack.getTag().getCompound("upper").getBoolean("active"));
                offHandStack.getTag().getCompound("lower").putBoolean("active", !offHandStack.getTag().getCompound("lower").getBoolean("active"));
            } else if (offHandStack.getItem() instanceof LightsaberItem) {
                offHandStack.getTag().putBoolean("active", !offHandStack.getTag().getBoolean("active"));
            }
        }
    }
}

