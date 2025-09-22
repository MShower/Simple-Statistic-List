package mshower.scoreboard.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

public class SimpleStatisticListCommand {
    public static String scoreboardDisplayName = "Cycle";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("simplestatisticlist")
                .then(literal("multiplayer")
                        .requires(src -> src.hasPermissionLevel(2))
                        .then(literal("mining").executes(ctx -> parse(ctx, "mining")))
                        .then(literal("placing").executes(ctx -> parse(ctx, "placing")))
                        .then(literal("off").executes(ctx -> parse(ctx, "off")))
                        .then(literal("cycle").executes(ctx -> parse(ctx, "cycle")))
                )
                .then(literal("singleplayer")
                        .then(literal("mining").executes(ctx -> parse(ctx, "mining")))
                        .then(literal("placing").executes(ctx -> parse(ctx, "placing")))
                        .then(literal("off").executes(ctx -> parse(ctx, "off")))
                        .then(literal("cycle").executes(ctx -> parse(ctx, "cycle")))
                )
        );
    }

    public static int parse(CommandContext<ServerCommandSource> ctx, String option) {
        scoreboardDisplayName = option;
        return 1;
    }

    //TODO
}
