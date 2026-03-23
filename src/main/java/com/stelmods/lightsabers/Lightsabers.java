package com.stelmods.lightsabers;

import com.google.common.base.Suppliers;
import com.stelmods.lightsabers.capabilities.ModCapabilities;
import com.stelmods.lightsabers.client.ClientEvents;
import com.stelmods.lightsabers.common.CommonEvents;
import com.stelmods.lightsabers.common.block.BlockCrystal;
import com.stelmods.lightsabers.common.block.ModBlocks;
import com.stelmods.lightsabers.common.component.LightsaberDataComponents;
import com.stelmods.lightsabers.common.container.ModContainers;
import com.stelmods.lightsabers.common.entity.ModEntities;
import com.stelmods.lightsabers.common.item.ItemFocusingCrystal;
import com.stelmods.lightsabers.common.item.LightsaberItem;
import com.stelmods.lightsabers.common.item.LightsaberPart;
import com.stelmods.lightsabers.common.item.ModItems;
import com.stelmods.lightsabers.lib.Strings;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.Supplier;

@Mod(Lightsabers.MODID)
public class Lightsabers {

    public static final String NAME = "Advanced Lightsabers";
    public static final String MODID = "lightsabers";

    public static final Logger LOGGER = LogManager.getLogger();

    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    private static Lightsabers instance;

    private static final Supplier<List<ItemStack>> items = Suppliers.memoize(() ->
            ModItems.ITEM.getEntries().stream()
                    .filter(item -> !(item.get() instanceof LightsaberItem))
                    .map(Supplier::get)
                    .map(ItemStack::new)
                    .toList()
    );

    public static final Supplier<CreativeModeTab> lightsaberBlockTab =
            TABS.register(Strings.lightsaberBlocks, () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.lightsaberForge.get()))
                    .title(Component.translatable("gui.lightsabers.lightsaber_crystals"))
                    .displayItems((params, output) -> {
                        for (ItemStack itemStack : items.get()) {
                            Item i = itemStack.getItem();
                            if (i instanceof BlockItem blockItem && !(blockItem.getBlock() instanceof BlockCrystal)) {
                                output.accept(itemStack);
                            }
                        }
                    })
                    .build()
            );

    public static final Supplier<CreativeModeTab> lightsaberLightsaber =
            TABS.register(Strings.lightsaber_tab, () -> CreativeModeTab.builder()
                    .icon(() -> {
                        ItemStack revan = new ItemStack(ModItems.lightsaber.get());
                        revan.set(LightsaberDataComponents.LIGHTSABER,
                                new LightsaberDataComponents.LightsaberData(
                                        ModItems.revanPommel.get().toString(),
                                        ModItems.revanGrip.get().toString(),
                                        ModItems.revanSwitch.get().toString(),
                                        ModItems.revanEmitter.get().toString(),
                                        "",
                                        "",
                                        BuiltInRegistries.BLOCK.getKey(ModBlocks.purpleCrystal.get()).toString()
                                ));
                        revan.set(LightsaberDataComponents.LIGHTSABER_ACTIVE, false);
                        revan.set(LightsaberDataComponents.LIGHTSABER_LENGTH, 0f);
                        return revan;
                    })
                    .title(Component.translatable("gui.lightsabers.lightsaber_creative"))
                    .displayItems((params, output) -> {

                        // Taron single
                        LightsaberDataComponents.LightsaberData taronSingle =
                                new LightsaberDataComponents.LightsaberData(
                                        ModItems.taronPommel.get().toString(),
                                        ModItems.taronGrip.get().toString(),
                                        ModItems.taronSwitch.get().toString(),
                                        ModItems.taronEmitter.get().toString(),
                                        "",
                                        "",
                                        BuiltInRegistries.BLOCK.getKey(ModBlocks.redCrystal.get()).toString()
                                );

                        ItemStack taron = new ItemStack(ModItems.lightsaber.get());
                        taron.set(LightsaberDataComponents.LIGHTSABER, taronSingle);
                        taron.set(LightsaberDataComponents.LIGHTSABER_ACTIVE, false);
                        taron.set(LightsaberDataComponents.LIGHTSABER_LENGTH, 0f);
                        output.accept(taron);

                        // Revan single
                        LightsaberDataComponents.LightsaberData revanSingle =
                                new LightsaberDataComponents.LightsaberData(
                                        ModItems.revanPommel.get().toString(),
                                        ModItems.revanGrip.get().toString(),
                                        ModItems.revanSwitch.get().toString(),
                                        ModItems.revanEmitter.get().toString(),
                                        "",
                                        "",
                                        BuiltInRegistries.BLOCK.getKey(ModBlocks.purpleCrystal.get()).toString()
                                );

                        ItemStack revan = new ItemStack(ModItems.lightsaber.get());
                        revan.set(LightsaberDataComponents.LIGHTSABER, revanSingle);
                        revan.set(LightsaberDataComponents.LIGHTSABER_ACTIVE, false);
                        revan.set(LightsaberDataComponents.LIGHTSABER_LENGTH, 0f);
                        output.accept(revan);

                        // Revan double
                        ItemStack revanDouble = new ItemStack(ModItems.doubleLightsaber.get());
                        revanDouble.set(LightsaberDataComponents.DOUBLE_LIGHTSABER,
                                new LightsaberDataComponents.DoubleLightsaberData(revanSingle, revanSingle));
                        revanDouble.set(LightsaberDataComponents.LIGHTSABER_ACTIVE, false);
                        revanDouble.set(LightsaberDataComponents.LIGHTSABER_LENGTH, 0f);
                        output.accept(revanDouble);

                        // Taron double
                        ItemStack taronDouble = new ItemStack(ModItems.doubleLightsaber.get());
                        taronDouble.set(LightsaberDataComponents.DOUBLE_LIGHTSABER,
                                new LightsaberDataComponents.DoubleLightsaberData(taronSingle, taronSingle));
                        taronDouble.set(LightsaberDataComponents.LIGHTSABER_ACTIVE, false);
                        taronDouble.set(LightsaberDataComponents.LIGHTSABER_LENGTH, 0f);
                        output.accept(taronDouble);
                    })
                    .build()
            );

    public static final Supplier<CreativeModeTab> crystal_tab =
            TABS.register(Strings.crystal_tab, () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.goldCrystal.get()))
                    .title(Component.translatable("gui.lightsabers.lightsaber_crystals"))
                    .displayItems((params, output) -> {
                        for (ItemStack itemStack : items.get()) {
                            Item i = itemStack.getItem();
                            if ((i instanceof BlockItem blockItem && blockItem.getBlock() instanceof BlockCrystal)
                                    || i instanceof ItemFocusingCrystal) {
                                output.accept(itemStack);
                            }
                        }
                    })
                    .build()
            );

    public static final Supplier<CreativeModeTab> part_tab =
            TABS.register(Strings.partsTab, () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.taronEmitter.get()))
                    .title(Component.translatable("gui.lightsabers.lightsaber_creative"))
                    .displayItems((params, output) -> {
                        for (ItemStack itemStack : items.get()) {
                            if (itemStack.getItem() instanceof LightsaberPart) {
                                output.accept(itemStack);
                            }
                        }
                    })
                    .build()
            );

    public Lightsabers(IEventBus bus, ModContainer modContainer) {
        instance = this;
        ModItems.ITEM.register(bus);
        ModBlocks.BLOCKS.register(bus);
        LightsaberDataComponents.COMPONENTS.register(bus);
        ModEntities.TILE_ENTITIES.register(bus);
        ModContainers.CONTAINERS.register(bus);
        ModCapabilities.ATTACHMENT_TYPES.register(bus);
        NeoForge.EVENT_BUS.register(new CommonEvents());

        TABS.register(bus);
        if (FMLEnvironment.dist.isClient()) {
            bus.addListener(ModContainers::registerGUIFactories);
        }
    }
}
