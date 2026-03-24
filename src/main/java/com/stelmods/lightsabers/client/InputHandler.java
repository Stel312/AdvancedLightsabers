package com.stelmods.lightsabers.client;

import com.stelmods.lightsabers.lib.Utils;
import com.stelmods.lightsabers.network.PacketHandler;
import com.stelmods.lightsabers.network.cts.*;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;

import java.util.List;
import java.util.Optional;

public class InputHandler {
    Minecraft mc;

    public static boolean forceSense = false;

    public InputHandler(){
        mc = Minecraft.getInstance();
    }

    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.Key event) {
        if(mc.screen == null) {
            if (event.getAction() == 1) { //We only want to run it once the key has been pressed, not released
                 if (event.getKey() == ClientSetup.Keybinds.FORCE_PUSH.getKeybind().getKey().getValue()) {
                    PacketHandler.sendToServer(new CSForcePush());
                } else if (event.getKey() == ClientSetup.Keybinds.FORCE_PULL.getKeybind().getKey().getValue()) {
                    PacketHandler.sendToServer(new CSForcePull());
                } else if (event.getKey() == ClientSetup.Keybinds.TOGGLE_LIGHTSABER.getKeybind().getKey().getValue()) {
                    PacketHandler.sendToServer(new CSToggleLightsaber());
                } else if (event.getKey() == ClientSetup.Keybinds.FORCE_ACTIVATE.getKeybind().getKey().getValue()) {
                    forceSense = !forceSense;
                } else if (event.getKey() == ClientSetup.Keybinds.FORCE_LIGHTNING.getKeybind().getKey().getValue()) {
                    //Turn force lightning on
                    PacketHandler.sendToServer(new CSShootLightning(true));
                } else if (event.getKey() == ClientSetup.Keybinds.FORCE_GRAB.getKeybind().getKey().getValue()) {
                    //Scan for entity in front and call the packet to grab it
                     HitResult hit = getMouseOverExtendedStraight(20);
                     if (hit != null && hit instanceof EntityHitResult eHit) {
                         Entity e = eHit.getEntity();
                         if(e != null){
                             System.out.println("Grabbing "+e.getDisplayName().getString());
                             PacketHandler.sendToServer(new CSGrabEntity(e.getId()));
                         }
                     } else {
                         PacketHandler.sendToServer(new CSGrabEntity(-1));
                     }
                }
            }

            if (event.getAction() == 0) { // On release
                if (event.getKey() == ClientSetup.Keybinds.FORCE_LIGHTNING.getKeybind().getKey().getValue()) {
                    //Turn force lightning off
                    PacketHandler.sendToServer(new CSShootLightning(false));

                }
            }
        }

    }

    @SubscribeEvent
    public void PlayerClick(InputEvent.InteractionKeyMappingTriggered event) {
        if(forceSense && event.getHand() == InteractionHand.OFF_HAND && event.isUseItem()) { //Checking offhand cause for some reason it triggers once while mainhand twice
            HitResult rt = InputHandler.getFirstInteractableBlock(20);
            if (rt instanceof BlockHitResult blockResult) {
                BlockState blockState = Minecraft.getInstance().player.level().getBlockState(blockResult.getBlockPos());
                if(Utils.isBlockStateInteractable(blockState)) {
                    PacketHandler.sendToServer(new CSInteractWithBlock(blockResult.getBlockPos(), blockResult));
                }
            }
        }
    }

    public static HitResult getFirstInteractableBlock(float dist) {
        Minecraft mc = Minecraft.getInstance();
        Entity theRenderViewEntity = mc.getCameraEntity();

        // If detects an entity return null
        if (theRenderViewEntity == null) {
            return null;
        }

        Vec3 pos = theRenderViewEntity.getEyePosition(0);
        Vec3 lookVec = theRenderViewEntity.getViewVector(0);
        Vec3 endPos = pos.add(lookVec.x * dist, lookVec.y * dist, lookVec.z * dist);

        // Start a custom raycast from player pos
        BlockHitResult returnMOP = null;
        Vec3 currentPos = pos;

        while (currentPos.distanceTo(endPos) > 0.1) {
            // Checks for multiple blocks until an interactable one is reached
            Vec3 nextPos = currentPos.add(lookVec.x * 0.1, lookVec.y * 0.1, lookVec.z * 0.1);
            returnMOP = mc.level.clip(new ClipContext(currentPos, nextPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, theRenderViewEntity));
            // If we got a block check it
            if (returnMOP != null) {
                BlockState blockState = mc.level.getBlockState(returnMOP.getBlockPos());

                if (Utils.isBlockStateInteractable(blockState)) {
                    return returnMOP;
                }
            }
            // Next iteration
            currentPos = nextPos;
        }

        return null;
    }

    public static HitResult getMouseOverExtendedStraight(float dist) {
        Minecraft mc = Minecraft.getInstance();
        Entity theRenderViewEntity = mc.getCameraEntity();
        AABB theViewBoundingBox = new AABB(theRenderViewEntity.getX() - 0.5D, theRenderViewEntity.getY() - 0.0D, theRenderViewEntity.getZ() - 0.5D, theRenderViewEntity.getX() + 0.5D, theRenderViewEntity.getY() + 1.5D, theRenderViewEntity.getZ() + 0.5D);
        HitResult returnMOP = null;
        if (mc.level != null) {
            double var2 = dist;
            returnMOP = theRenderViewEntity.pick(var2, 0, false);
            double calcdist = var2;
            Vec3 pos = theRenderViewEntity.getEyePosition(0);
            var2 = calcdist;
            if (returnMOP != null) {
                calcdist = returnMOP.getLocation().distanceTo(pos);
            }

            Vec3 lookvec = theRenderViewEntity.getViewVector(0);
            Vec3 var8 = pos.add(lookvec.x * var2, lookvec.y * var2, lookvec.z * var2);
            Entity pointedEntity = null;
            float var9 = 1.0F;

            List<Entity> list = mc.level.getEntities(theRenderViewEntity, theViewBoundingBox.inflate(lookvec.x * var2, lookvec.y * var2, lookvec.z * var2).inflate(var9, var9, var9));
            double d = calcdist;

            for (Entity entity : list) {
                if (entity.isPickable()) {
                    float bordersize = entity.getPickRadius();
                    AABB aabb = new AABB(entity.getX() - entity.getBbWidth() / 2, entity.getY(), entity.getZ() - entity.getBbWidth() / 2, entity.getX() + entity.getBbWidth() / 2, entity.getY() + entity.getBbHeight(), entity.getZ() + entity.getBbWidth() / 2);
                    aabb.inflate(bordersize, bordersize, bordersize);
                    Optional<Vec3> mop0 = aabb.clip(pos, var8);

                    if (aabb.contains(pos)) {
                        if (0.0D < d || d == 0.0D) {
                            pointedEntity = entity;
                            d = 0.0D;
                        }
                    } else if (mop0 != null && mop0.isPresent()) {
                        double d1 = pos.distanceTo(mop0.get());

                        if (d1 < d || d == 0.0D) {
                            pointedEntity = entity;
                            d = d1;
                        }
                    }
                }
            }

            if (pointedEntity != null && (d < calcdist || returnMOP == null)) {
                returnMOP = new EntityHitResult(pointedEntity);
            }
        }
        return returnMOP;
    }

}
