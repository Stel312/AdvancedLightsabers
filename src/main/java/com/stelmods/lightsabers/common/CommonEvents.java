package com.stelmods.lightsabers.common;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.capabilities.PlayerCapabilities;
import com.stelmods.lightsabers.network.PacketHandler;
import com.stelmods.lightsabers.network.stc.SCSendLightningData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
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
        if (playerData != null) {
            if (playerData.isLightningMode()) {
                handleLightning(player);
            }
            if (playerData.getGrabbedID() > -1) {
                handleGrab(player, playerData.getGrabbedID());
            }
            // Handle grabbed block movement
            if (playerData.getGrabbedBlockEntityId() != -1) {
                handleGrabbedBlock(player, playerData.getGrabbedBlockEntityId());
            }
        }
        handleStickyForcePushedBlocks(player);
    }

    private void handleGrab(Player player, int id) {
        Entity entity = player.level().getEntity(id);
        if(entity != null) {
            Lightsabers.LOGGER.debug("Moving {}", entity.getDisplayName().getString());
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

    private void handleGrabbedBlock(Player player, int entityId) {
        // Only run server-side (authoritative movement)
        if (player.level().isClientSide) return;

        Entity entity = player.level().getEntity(entityId);
        if (entity instanceof Display.BlockDisplay blockDisplay) {
            double distance = 4.0;

            Vec3 eyePos = player.getEyePosition();
            Vec3 look = player.getLookAngle();

            Vec3 targetPos = eyePos.add(look.scale(distance));
            // Keep the held block from touching solid blocks to avoid vanilla falling-block consume/placement.
            HitResult holdHit = player.level().clip(new ClipContext(eyePos, targetPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
            if (holdHit.getType() == HitResult.Type.BLOCK) {
                targetPos = holdHit.getLocation().subtract(look.scale(1.1));
            }
            // Move display directly to the held position (display uses corner-space).
            Vec3 targetCorner = targetPos.add(-0.5, -0.5, -0.5);
            Vec3 current = blockDisplay.position();
            Vec3 next = current.add(targetCorner.subtract(current).scale(0.7));
            blockDisplay.hurtMarked = true;
            blockDisplay.setPos(next.x, next.y, next.z);
        } else if (entity == null) {
            // Entity vanished unexpectedly — clear capability state
            PlayerCapabilities playerData = PlayerCapabilities.get(player);
            if (playerData != null && !player.level().isClientSide) {
                playerData.setGrabbedBlockEntityId(-1);
                playerData.setGrabbedBlockPos(null);
                playerData.setGrabbedBlockId(null);
                playerData.setGrabbedBlockStateNBT(null);
                PacketHandler.syncToAllAround(player, playerData);
            }
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

    private void handleStickyForcePushedBlocks(Player player) {
        if (player.level().isClientSide) {
            return;
        }

        List<FallingBlockEntity> fallingBlocks = player.level().getEntitiesOfClass(
                FallingBlockEntity.class,
                player.getBoundingBox().inflate(64),
                e -> e.getPersistentData().getBoolean("lightsabers_force_pushed_block")
        );

        for (FallingBlockEntity fallingBlock : fallingBlocks) {
            BlockState state = fallingBlock.getBlockState();
            Vec3 endPos = fallingBlock.position();
            Vec3 motion = fallingBlock.getDeltaMovement();

            // Damage entities on impact; damage scales with block hardness and throw speed.
            List<LivingEntity> hitEntities = player.level().getEntitiesOfClass(
                    LivingEntity.class,
                    fallingBlock.getBoundingBox().inflate(0.35),
                    e -> e.isAlive() && e != player
            );
            if (!hitEntities.isEmpty()) {
                LivingEntity target = hitEntities.get(0);
                float hardness = Math.max(0.0F, state.getDestroySpeed(player.level(), BlockPos.containing(endPos)));
                float speed = (float) motion.length();
                float damage = Math.min(20.0F, 2.0F + hardness * 0.8F + speed * 2.0F);

                target.hurt(player.damageSources().generic(), damage);
                if (motion.lengthSqr() > 1.0E-4) {
                    target.addDeltaMovement(motion.normalize().scale(0.4 + hardness * 0.05));
                }

                BlockPos placePos = resolveStickyPlacePos(player.level(), fallingBlock, null);
                if (placePos != null) {
                    player.level().setBlock(placePos, state, 3);
                    fallingBlock.discard();
                } else {
                    fallingBlock.setDeltaMovement(Vec3.ZERO);
                    fallingBlock.setNoGravity(true);
                    fallingBlock.dropItem = false;
                    fallingBlock.time = 0;
                    fallingBlock.getPersistentData().remove("lightsabers_force_pushed_block");
                }
                continue;
            }

            if (!(fallingBlock.horizontalCollision || fallingBlock.verticalCollision || fallingBlock.onGround())) {
                continue;
            }

            Vec3 startPos = endPos.subtract(fallingBlock.getDeltaMovement());
            HitResult hitResult = player.level().clip(new ClipContext(startPos, endPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, fallingBlock));

            BlockPos placePos = resolveStickyPlacePos(player.level(), fallingBlock, hitResult);
            if (placePos != null) {
                player.level().setBlock(placePos, state, 3);
                fallingBlock.discard();
            } else {
                // No valid placement nearby: freeze in place instead of turning into an item.
                fallingBlock.setDeltaMovement(Vec3.ZERO);
                fallingBlock.setNoGravity(true);
                fallingBlock.dropItem = false;
                fallingBlock.time = 0;
                fallingBlock.getPersistentData().remove("lightsabers_force_pushed_block");
            }
        }
    }

    private BlockPos resolveStickyPlacePos(Level level, FallingBlockEntity fallingBlock, HitResult hitResult) {
        Vec3 endPos = fallingBlock.position();
        Vec3 motion = fallingBlock.getDeltaMovement();
        BlockPos impactPos = BlockPos.containing(endPos);

        // Best case: raycast tells us exact hit face; place on adjacent side.
        if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            BlockPos byFace = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());
            if (level.getBlockState(byFace).canBeReplaced()) {
                return byFace;
            }
        }

        // Fallback: use motion direction to infer which side to stick to.
        Direction oppositeTravel = Direction.getNearest(-motion.x, -motion.y, -motion.z);
        BlockPos byMotion = impactPos.relative(oppositeTravel);
        if (level.getBlockState(byMotion).canBeReplaced()) {
            return byMotion;
        }

        // Final fallback: scan immediate neighbors for any replaceable spot.
        for (Direction dir : Direction.values()) {
            BlockPos candidate = impactPos.relative(dir);
            if (level.getBlockState(candidate).canBeReplaced()) {
                return candidate;
            }
        }

        if (level.getBlockState(impactPos).canBeReplaced()) {
            return impactPos;
        }
        return null;
    }
}
