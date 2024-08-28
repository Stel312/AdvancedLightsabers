package com.stelmods.lightsabers.common.container;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.client.gui.LightsaberForgeScreen;

import com.stelmods.lightsabers.client.gui.LightsaberForgeTier2Screen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainers {
	public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Lightsabers.MODID);

	// Haven't figured out the meaning of parameter FeatureFlags yet
	public static final RegistryObject<MenuType<?>> LIGHTSABER_FORGE = createContainer("lightsaber_forge", LightsaberForgeContainer::new);
	public static final RegistryObject<MenuType<?>> LIGHTSABER_FORGE_TIER2 = createContainer("lightsaber_forge_tier2", LightsaberForgeTier2Container::new);




	public static <M extends AbstractContainerMenu> RegistryObject<MenuType<?>> createContainer(String name, IContainerFactory<M> container) {
		return CONTAINERS.register(name, () -> new MenuType<>(container, FeatureFlags.VANILLA_SET));
	}

	@OnlyIn(Dist.CLIENT)
	public static <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void registerGUIFactory(MenuType<M> container, MenuScreens.ScreenConstructor<M, U> guiFactory) {
		MenuScreens.register(container, guiFactory);
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerGUIFactories() {
		registerGUIFactory((MenuType<LightsaberForgeContainer>) (ModContainers.LIGHTSABER_FORGE.get()), LightsaberForgeScreen::new);
		registerGUIFactory((MenuType<LightsaberForgeTier2Container>) (ModContainers.LIGHTSABER_FORGE_TIER2.get()), LightsaberForgeTier2Screen::new);
	}
}
