package com.stelmods.lightsabers.client;

import com.stelmods.lightsabers.common.block.ModBlocks;
import com.stelmods.lightsabers.common.block.BlockCrystal;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.registries.RegistryObject;

public class ClientEvents {
    public static void colourTint(RegisterColorHandlersEvent.Block event) {
        for(RegistryObject<Block> block: ModBlocks.BLOCKS.getEntries())
        {
            if (block.get() instanceof BlockCrystal crystal){

                event.register((state, level, pos, tintIndex) -> crystal.getCrystalColor().color, crystal);
            }
        }
    }
}
