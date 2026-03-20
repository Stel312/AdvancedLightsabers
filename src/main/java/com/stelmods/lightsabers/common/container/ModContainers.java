package com.stelmods.lightsabers.common.container;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.client.gui.LightsaberForgeScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModContainers {
	public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(BuiltInRegistries.MENU, Lightsabers.MODID);


	public static final Supplier<MenuType<LightsaberForgeContainer>> LIGHTSABER_FORGE = createContainer("lightsaber_forge", LightsaberForgeContainer::new);
	public static final Supplier<MenuType<LightsaberForgeTier2Container>> LIGHTSABER_FORGE_TIER2 = createContainer("lightsaber_forge_tier2", LightsaberForgeTier2Container::new);


	public static <M extends AbstractContainerMenu> Supplier<MenuType<M>> createContainer(String name, IContainerFactory<M> container) {
		return CONTAINERS.register(name, () -> new MenuType<>(container, FeatureFlags.VANILLA_SET));
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerGUIFactories(RegisterMenuScreensEvent event) {
        event.register(ModContainers.LIGHTSABER_FORGE.get(), LightsaberForgeScreen::new);
        event.register(ModContainers.LIGHTSABER_FORGE_TIER2.get(), LightsaberForgeTier2Screen::new);
	}
}
