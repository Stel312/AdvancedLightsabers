package com.stelmods.lightsabers;

import com.google.common.base.Suppliers;
import com.stelmods.lightsabers.client.ClientEvents;
import com.stelmods.lightsabers.common.block.BlockCrystal;
import com.stelmods.lightsabers.common.item.ItemFocusingCrystal;
import com.stelmods.lightsabers.common.block.ModBlocks;
import com.stelmods.lightsabers.common.container.ModContainers;
import com.stelmods.lightsabers.common.entity.ModEntities;
import com.stelmods.lightsabers.common.item.*;
import com.stelmods.lightsabers.common.lightsaber.LightsaberType;
import com.stelmods.lightsabers.datagen.DataGeneration;
import com.stelmods.lightsabers.lib.Strings;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

import static com.stelmods.lightsabers.Lightsabers.MODID;

@Mod(MODID)
public class Lightsabers
{
    public static final String NAME = "Advanced Lightsabers";
    public static final String MODID = "lightsabers";
    public static final String VERSION = "${version}";
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    private static Lightsabers instance;
    private static final Supplier<List<ItemStack>> items = Suppliers.memoize(() ->
            ModItems.ITEMS.getEntries().stream().filter(item -> !(item.get() instanceof LightsaberItem)).map(RegistryObject::get).map(ItemStack::new).toList());

    public static final RegistryObject<CreativeModeTab> lightsaberBlockTab = TABS.register(Strings.lightsaberBlocks, () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModBlocks.lightsaberForge.get()))
            .title(Component.translatable("gui.lightsabers.lightsaber_crystals"))
            .displayItems((params, output) -> {
                for(ItemStack itemStack: items.get()) {
                    Item i = itemStack.getItem();
                    if ((i instanceof BlockItem blockItem && !(blockItem.getBlock() instanceof BlockCrystal)))
                    {
                        output.accept(itemStack);
                    }
                }
            }).build());

    public static final RegistryObject<CreativeModeTab> lightsaberLightsaber = TABS.register(Strings.lightsaber_tab, () -> CreativeModeTab.builder()
            .icon(() -> { ItemStack revan = new ItemStack(ModItems.lightsaber.get());
                revan.setTag(new CompoundTag());
                revan.getTag().putString("emitter", ModItems.revanEmitter.getId().toString());
                revan.getTag().putString("grip", ModItems.revanGrip.getId().toString());
                revan.getTag().putString("pommel", ModItems.revanPommel.getId().toString());
                revan.getTag().putString("switch", ModItems.revanSwitch.getId().toString());
                revan.getTag().putString("type", LightsaberType.SINGLE.toString());
                revan.getTag().putString("color", ModBlocks.purpleCrystal.getId().toString());
                revan.getTag().putBoolean("active", false);
                return revan;})
            .title(Component.translatable("gui.lightsabers.lightsaber_creative"))
            .displayItems((params, output) -> {
                ItemStack taron = new ItemStack(ModItems.lightsaber.get());
                taron.setTag(new CompoundTag());
                taron.getTag().putString("emitter", ModItems.taronEmitter.getId().toString());
                taron.getTag().putString("grip", ModItems.taronGrip.getId().toString());
                taron.getTag().putString("pommel", ModItems.taronPommel.getId().toString());
                taron.getTag().putString("switch", ModItems.taronSwitch.getId().toString());
                taron.getTag().putString("type", LightsaberType.SINGLE.toString());
                taron.getTag().putString("color", ModBlocks.redCrystal.getId().toString());
                taron.getTag().putBoolean("active", false);
                output.accept(taron);

                ItemStack revan = new ItemStack(ModItems.lightsaber.get());
                revan.setTag(new CompoundTag());
                revan.getTag().putString("emitter", ModItems.revanEmitter.getId().toString());
                revan.getTag().putString("grip", ModItems.revanGrip.getId().toString());
                revan.getTag().putString("pommel", ModItems.revanPommel.getId().toString());
                revan.getTag().putString("switch", ModItems.revanSwitch.getId().toString());
                revan.getTag().putString("type", LightsaberType.SINGLE.toString());
                revan.getTag().putString("color", ModBlocks.purpleCrystal.getId().toString());
                revan.getTag().putBoolean("active", false);
                output.accept(revan);

                output.accept(registerDoubleSaber(taron, revan));
                output.accept(registerDoubleSaber(taron, taron));
            }).build());

    public static final RegistryObject<CreativeModeTab> crystal_tab = TABS.register(Strings.crystal_tab, () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModBlocks.goldCrystal.get()))
            .title(Component.translatable("gui.lightsabers.lightsaber_crystals"))

            .displayItems((params, output) -> {
                for(ItemStack itemStack: items.get()) {
                    Item i = itemStack.getItem();
                    if ((i instanceof BlockItem blockItem && blockItem.getBlock() instanceof BlockCrystal) || i instanceof ItemFocusingCrystal)
                    {
                        output.accept(itemStack);
                    }
                }
            }).build());

    public static final RegistryObject<CreativeModeTab> part_tab = TABS.register(Strings.partsTab, () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.taronEmitter.get()))
            .title(Component.translatable("gui.lightsabers.lightsaber_creative"))
            .displayItems((params, output) -> {
                for(ItemStack itemStack: items.get())
                {
                    if(itemStack.getItem() instanceof LightsaberPart){
                        output.accept(itemStack);
                    }
                }

            })
            .build());

    private static ItemStack registerDoubleSaber(ItemStack upper, ItemStack lower)
    {
        ItemStack itemStack = new ItemStack(ModItems.doubleLightsaber.get());
        itemStack.setTag(new CompoundTag());
        itemStack.getTag().putString("type", LightsaberType.DOUBLE.toString());
        itemStack.getTag().put("upper", upper.getTag());
        itemStack.getTag().put("lower", lower.getTag());
        return itemStack;
    }

	public static Lightsabers getInstance() {
		return instance;
	}

    
    public Lightsabers() {
    	instance = this;

       	IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    	bus.addListener(this::constructMod);
    	bus.addListener(this::doClientStuff);
    	bus.addListener(this::doServerStuff);
    	bus.addListener(this::doCommonStuff);
        bus.addListener(this::modelRegistry);
        ModItems.ITEMS.register(bus);
        ModBlocks.BLOCKS.register(bus);
        ModEntities.TILE_ENTITIES.register(bus);
        ModContainers.CONTAINERS.register(bus);
        TABS.register(bus);
        MinecraftForge.EVENT_BUS.register(new DataGeneration());
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> new DistExecutor.SafeRunnable() {
            @Override
            public void run() {
                bus.addListener(ClientEvents::colourTint);
            }
        });
    }
    
    private void constructMod(final FMLConstructModEvent event) {
    	
    }
    
    private void doClientStuff(final FMLClientSetupEvent event) {
        ModContainers.registerGUIFactories();
    }
	
	private void doServerStuff(final FMLDedicatedServerSetupEvent event) {

	}
	
	private void doCommonStuff(final FMLCommonSetupEvent event) {

    }
    @OnlyIn(Dist.CLIENT)
    private void modelRegistry(ModelEvent.RegisterAdditional event) {
        event.register(new ResourceLocation(Lightsabers.MODID, "item/blade"));
        event.register(new ResourceLocation(Lightsabers.MODID, "item/cube"));
    }

}
