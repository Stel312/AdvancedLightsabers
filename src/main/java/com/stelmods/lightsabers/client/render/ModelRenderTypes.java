package com.stelmods.lightsabers.client.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.realmsclient.dto.RealmsNews;
import com.stelmods.lightsabers.Lightsabers;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import static net.minecraft.client.renderer.RenderStateShard.*;

public class ModelRenderTypes {
    public static final ResourceLocation BLADE_TEX =
            ResourceLocation.fromNamespaceAndPath(Lightsabers.MODID, "textures/item/lightsaber.blade.png");
    public static final RenderType SABER_OUTER = RenderType.create(
            "saber_outer",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.QUADS,
            1536,
            false,
            true,
            RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_LIGHTNING_SHADER)
                    .setWriteMaskState(COLOR_DEPTH_WRITE)
                    //.setDepthTestState(LEQUAL_DEPTH_TEST)
                    .setTransparencyState(LIGHTNING_TRANSPARENCY)
                    .setCullState(NO_CULL)
                    .setOutputState(PARTICLES_TARGET)
                    .createCompositeState(false)
    );

    public static final RenderType SABER_INNER = RenderType.create(
            "saber_outer",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.QUADS,
            1536,
            false,
            true,
            RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_LIGHTNING_SHADER)
                    .setWriteMaskState(COLOR_DEPTH_WRITE)
                    .setTransparencyState(LIGHTNING_TRANSPARENCY)
                    .setOutputState(PARTICLES_TARGET)
                    .createCompositeState(false)
    );
}
