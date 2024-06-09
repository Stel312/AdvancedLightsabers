package com.stelmods.lightsabers.common.item;


import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.common.item.parts.LightsaberBody;
import com.stelmods.lightsabers.common.item.parts.LightsaberEmiter;
import com.stelmods.lightsabers.common.item.parts.LightsaberPommel;
import com.stelmods.lightsabers.common.item.parts.LightsaberSwitch;
import com.stelmods.lightsabers.common.lightsaber.FocusingCrystal;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Lightsabers.MODID);


    public static final RegistryObject<Item>
            taronEmitter = registerEmitter("taron_emitter", .209f),
            taronGrip = registerBody("taron_grip", 0.271181f),
            taronSwitch = registerSwitch("taron_switch", 0.102172f),
            taronPommel = registerPommel("taron_pommel", 0.14922f);

    public static final RegistryObject<Item>
            revanEmitter = registerEmitter("revan_emitter", .1f),
            revanSwitch = registerSwitch("revan_switch", 0.114288f),
            revanGrip = registerBody("revan_grip", 0.30083f),
            revanPommel = registerPommel("revan_pommel", 0.041906f);

    public static final RegistryObject<Item> lightsaber = ITEMS.register("lightsaber", LightsaberItem::new);
    public static final RegistryObject<Item> doubleLightsaber = ITEMS.register("lightsaber_double", LightsaberDoubleItem::new);

    public static final RegistryObject<Item> crackedCrystal = ITEMS.register("crack_crystal", () -> new ItemFocusingCrystal(FocusingCrystal.CRACKED));
    public static final RegistryObject<Item> compressedCrystal = ITEMS.register("compressed_crystal", () -> new ItemFocusingCrystal(FocusingCrystal.COMPRESSED));
    public static final RegistryObject<Item> finecutCrystal = ITEMS.register("fine_cut_crystal", () -> new ItemFocusingCrystal(FocusingCrystal.FINE_CUT));
    public static final RegistryObject<Item> invertedCrystal = ITEMS.register("inverting_crystal", () -> new ItemFocusingCrystal(FocusingCrystal.INVERTING));
    public static final RegistryObject<Item> prismaticCrystal = ITEMS.register("prismatic_crystal", () -> new ItemFocusingCrystal(FocusingCrystal.PRISMATIC));

    private static RegistryObject<Item> registerPommel(String name, float height) {
        return ITEMS.register(name, () -> new LightsaberPommel(height));
    }

    private static RegistryObject<Item> registerBody(String name, float height) {
        return ITEMS.register(name, () -> new LightsaberBody(height));
    }

    private static RegistryObject<Item> registerSwitch(String name, float height) {
        return ITEMS.register(name, () -> new LightsaberSwitch(height));
    }

    private static RegistryObject<Item> registerEmitter(String name, float height) {
        return ITEMS.register(name, () -> new LightsaberEmiter(height));
    }
}
