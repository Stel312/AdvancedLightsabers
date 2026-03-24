package com.stelmods.lightsabers.common;

import com.stelmods.lightsabers.capabilities.PlayerCapabilities;
import com.stelmods.lightsabers.network.PacketHandler;
import com.stelmods.lightsabers.network.stc.SCSendLightningData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.ArrayList;
import java.util.List;

public class CommonEvents {
    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        PlayerCapabilities playerData = PlayerCapabilities.get(player);
        if(playerData.isLightningMode()) {
            handleLightning(player);

        }
        if(playerData.getGrabbedID() > -1) {
            handleGrab(player, playerData.getGrabbedID());
        }
    }

    private void handleGrab(Player player, int id) {
        Entity entity = player.level().getEntity(id);
        if(entity != null) {
            System.out.println("Moving "+entity.getDisplayName().getString());
            double distance = 4.0;

            //Player camera angle
            Vec3 eyePos = player.getEyePosition();
            Vec3 look = player.getLookAngle();

            //Actual motion of the entity
            Vec3 targetPos = eyePos.add(look.scale(distance));
            Vec3 currentPos = entity.position();
            Vec3 motion = targetPos.subtract(currentPos).scale(0.25);

            //Apply the motion
            entity.setDeltaMovement(motion);
            entity.hurtMarked = true;
        }
    }

    private void handleLightning(Player player) {
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
