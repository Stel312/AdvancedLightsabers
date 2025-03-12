package com.stelmods.lightsabers.common;

import com.stelmods.lightsabers.capabilities.IPlayerCapabilities;
import com.stelmods.lightsabers.capabilities.ModCapabilities;
import com.stelmods.lightsabers.client.ClientEvents;
import com.stelmods.lightsabers.client.ClientUtils;
import com.stelmods.lightsabers.network.PacketHandler;
import com.stelmods.lightsabers.network.stc.SCSendLightningData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommonEvents {
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
        if(playerData.isLightningMode()){


            Level level = player.level();
            Vec3 start = player.getEyePosition(); // Posición de los ojos del jugador
            Vec3 look = player.getViewVector(1.0F); // Dirección en la que mira
            Vec3 end = start.add(look.scale(10)); // 10 bloques de distancia máxima

            HitResult hitResult = level.clip(new ClipContext(start, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                end = hitResult.getLocation();
            }

            AABB area = new AABB(start, end).inflate(1.0);
            List<Entity> entities = level.getEntities(player, area, e -> e instanceof LivingEntity && e != player);

            for (Entity entity : entities) {

                entity.hurt(entity.damageSources().lightningBolt(), 10.0F/entities.size());
            }

            ArrayList<Integer> list = new ArrayList<>();
            for(Entity e : entities){
                list.add(e.getId());
            }

            //Sync only when changes?
            if(!level.isClientSide) {

                //Send to all
                for(ServerPlayer p : player.getServer().getPlayerList().getPlayers()) {
                    PacketHandler.sendTo(new SCSendLightningData(player.getId(), list), p);
                }
            }

            //ClientUtils.renderLightningBeam(event.getPoseStack(), event.getMultiBufferSource(), player.getEyePosition().add(0, -0.3, 0), end, 20);

        }
    }
}
