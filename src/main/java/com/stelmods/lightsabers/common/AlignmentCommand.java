package com.stelmods.lightsabers.common;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.stelmods.lightsabers.capabilities.PlayerCapabilities;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class AlignmentCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("alignment")
                .requires(cs -> cs.hasPermission(2)) // OP level 2 required
                .then(Commands.literal("get")
                    .executes(ctx -> getAlignment(ctx.getSource()))
                    .then(Commands.argument("player", EntityArgument.player())
                        .executes(ctx -> getAlignmentOther(ctx.getSource(), EntityArgument.getPlayer(ctx, "player")))
                    )
                )
                .then(Commands.literal("set")
                    .then(Commands.argument("value", IntegerArgumentType.integer(-100, 100))
                        .executes(ctx -> setAlignment(ctx.getSource(), IntegerArgumentType.getInteger(ctx, "value")))
                        .then(Commands.argument("player", EntityArgument.player())
                            .executes(ctx -> setAlignmentOther(ctx.getSource(), EntityArgument.getPlayer(ctx, "player"), IntegerArgumentType.getInteger(ctx, "value")))
                        )
                    )
                )
                .then(Commands.literal("add")
                    .then(Commands.argument("value", IntegerArgumentType.integer(-200, 200))
                        .executes(ctx -> addAlignment(ctx.getSource(), IntegerArgumentType.getInteger(ctx, "value")))
                        .then(Commands.argument("player", EntityArgument.player())
                            .executes(ctx -> addAlignmentOther(ctx.getSource(), EntityArgument.getPlayer(ctx, "player"), IntegerArgumentType.getInteger(ctx, "value")))
                        )
                    )
                )
        );
    }

    private static int getAlignment(CommandSourceStack source) {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            source.sendFailure(Component.literal("This command can only be run by a player."));
            return 0;
        }
        return getAlignmentOther(source, player);
    }

    private static int getAlignmentOther(CommandSourceStack source, ServerPlayer player) {
        PlayerCapabilities caps = PlayerCapabilities.get(player);
        if (caps == null) {
            source.sendFailure(Component.literal("Could not get player capabilities."));
            return 0;
        }
        
        int alignment = caps.getAlignment();
        String alignmentType = getAlignmentType(alignment);
        
        source.sendSuccess(
            () -> Component.literal(player.getName().getString() + "'s alignment: " + alignment + " (" + alignmentType + ")"),
            false
        );
        return 1;
    }

    private static int setAlignment(CommandSourceStack source, int value) {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            source.sendFailure(Component.literal("This command can only be run by a player."));
            return 0;
        }
        return setAlignmentOther(source, player, value);
    }

    private static int setAlignmentOther(CommandSourceStack source, ServerPlayer player, int value) {
        PlayerCapabilities caps = PlayerCapabilities.get(player);
        if (caps == null) {
            source.sendFailure(Component.literal("Could not get player capabilities."));
            return 0;
        }
        
        caps.setAlignment(value);
        String alignmentType = getAlignmentType(value);
        
        source.sendSuccess(
            () -> Component.literal(player.getName().getString() + "'s alignment set to " + value + " (" + alignmentType + ")"),
            true // Send to all admins
        );
        return 1;
    }

    private static int addAlignment(CommandSourceStack source, int delta) {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            source.sendFailure(Component.literal("This command can only be run by a player."));
            return 0;
        }
        return addAlignmentOther(source, player, delta);
    }

    private static int addAlignmentOther(CommandSourceStack source, ServerPlayer player, int delta) {
        PlayerCapabilities caps = PlayerCapabilities.get(player);
        if (caps == null) {
            source.sendFailure(Component.literal("Could not get player capabilities."));
            return 0;
        }
        
        int oldAlignment = caps.getAlignment();
        caps.addAlignment(delta);
        int newAlignment = caps.getAlignment();
        String alignmentType = getAlignmentType(newAlignment);
        
        source.sendSuccess(
            () -> Component.literal(player.getName().getString() + "'s alignment changed from " + oldAlignment + " to " + newAlignment + " (" + alignmentType + ")"),
            true // Send to all admins
        );
        return 1;
    }

    private static String getAlignmentType(int alignment) {
        if (alignment >= 75) return "§6Light Side";
        if (alignment >= 25) return "§aLight";
        if (alignment >= -24) return "§bNeutral";
        if (alignment >= -74) return "§cDark";
        return "§4Dark Side";
    }
}

