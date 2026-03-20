package com.stelmods.lightsabers.capabilities;

import com.stelmods.lightsabers.Lightsabers;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModCapabilities {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Lightsabers.MODID);
    public static final Supplier<AttachmentType<PlayerCapabilities>> PLAYER_DATA = ATTACHMENT_TYPES.register("player", () -> AttachmentType.serializable(PlayerCapabilities::new).copyOnDeath().build());

}