package com.stelmods.lightsabers.client;

import com.stelmods.lightsabers.lib.Utils;
import com.stelmods.lightsabers.network.PacketHandler;
import com.stelmods.lightsabers.network.cts.CSInteractWithBlock;
import com.stelmods.lightsabers.network.cts.CSToggleLightsaber;
import com.stelmods.lightsabers.network.cts.ForcePull;
import com.stelmods.lightsabers.network.cts.ForcePush;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class InputHandler {
    Minecraft mc;

    public static boolean forceSense = false;

    public InputHandler(){
        mc = Minecraft.getInstance();
    }

    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.Key event) {
        if(event.getAction() == 1) { //We only want to run it once the key has been pressed, not released
            if (event.getKey() == KeyMappings.FORCE_PUSH.getKey().getValue()) {
                PacketHandler.sendToServer(new ForcePush());
            } else if (event.getKey() == KeyMappings.FORCE_PULL.getKey().getValue()) {
                PacketHandler.sendToServer(new ForcePull());
            } else if (event.getKey() == KeyMappings.TOGGLE_LIGHTSABER.getKey().getValue()) {
                PacketHandler.sendToServer(new CSToggleLightsaber());
            } else if (event.getKey() == KeyMappings.FORCE_ACTIVATE.getKey().getValue()) {
                //Toggle force scan on or hold to use?
                forceSense = !forceSense;
                //forceSense = true;

            }
        }

       /* if(event.getAction() == 0){ // On release
            if(event.getKey() == KeyMappings.FORCE_ACTIVATE.getKey().getValue()) {
                //Toggle force scan off
                forceSense = false;
            }
        }*/
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

}
