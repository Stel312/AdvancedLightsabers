package com.stelmods.lightsabers.common.item;


import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.common.item.parts.LightsaberBody;
import com.stelmods.lightsabers.common.item.parts.LightsaberEmiter;
import com.stelmods.lightsabers.common.item.parts.LightsaberPommel;
import com.stelmods.lightsabers.common.item.parts.LightsaberSwitch;
import com.stelmods.lightsabers.common.lightsaber.FocusingCrystal;
import com.stelmods.lightsabers.lib.Strings;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, Lightsabers.MODID);


    public static final Supplier<Item>
            taronEmitter = registerEmitter("taron_emitter", .209f),
            taronGrip = registerBody("taron_grip", 0.271181f),
            taronSwitch = registerSwitch("taron_switch", 0.102172f),
            taronPommel = registerPommel("taron_pommel", 0.14922f);

    public static final Supplier<Item>
            revanEmitter = registerEmitter("revan_emitter", .1f),
            revanSwitch = registerSwitch("revan_switch", 0.114288f),
            revanGrip = registerBody("revan_grip", 0.30083f),
            revanPommel = registerPommel("revan_pommel", 0.041906f);

    public static final Supplier<Item>
        stelEmitter = registerEmitter("stel_emitter", .192952f);

    public static final Supplier<Item> lightsaber = ITEMS.register("lightsaber", LightsaberItem::new);
    public static final Supplier<Item> doubleLightsaber = ITEMS.register("lightsaber_double", LightsaberDoubleItem::new);

    public static final Supplier<Item> crackedCrystal = ITEMS.register(Strings.crackedFocusingCrystal, () -> new ItemFocusingCrystal(FocusingCrystal.CRACKED));
    public static final Supplier<Item> compressedCrystal = ITEMS.register(Strings.compressedFocusingCrystal, () -> new ItemFocusingCrystal(FocusingCrystal.COMPRESSED));
    public static final Supplier<Item> finecutCrystal = ITEMS.register(Strings.fineCutFocusingCrystal, () -> new ItemFocusingCrystal(FocusingCrystal.FINE_CUT));
    public static final Supplier<Item> invertedCrystal = ITEMS.register(Strings.invertedFocusingCrystal, () -> new ItemFocusingCrystal(FocusingCrystal.INVERTING));
    public static final Supplier<Item> prismaticCrystal = ITEMS.register(Strings.prismaticFocusingCrystal, () -> new ItemFocusingCrystal(FocusingCrystal.PRISMATIC));

    private static Supplier<Item> registerPommel(String name, float height) {
        return ITEMS.register(name, () -> new LightsaberPommel(height));
    }

    private static Supplier<Item> registerBody(String name, float height) {
        return ITEMS.register(name, () -> new LightsaberBody(height));
    }

    private static Supplier<Item> registerSwitch(String name, float height) {
        return ITEMS.register(name, () -> new LightsaberSwitch(height));
    }

    private static Supplier<Item> registerEmitter(String name, float height) {
        return ITEMS.register(name, () -> new LightsaberEmiter(height));
    }
}
