package mshower.scoreboard.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import mshower.scoreboard.config.ScoreboardConfig;
import net.minecraft.network.packet.s2c.play.ScoreboardDisplayS2CPacket;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.server.command.ServerCommandSource;
//#if MC>12001
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.server.network.ServerPlayerEntity;
//#endif
import static mshower.scoreboard.SimpleStatisticList.*;
import static net.minecraft.server.command.CommandManager.literal;

public class SimpleStatisticListCommand {
    public static String globalScoreboardDisplayName = "Cycle";
    ScoreboardConfig Config;

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("simplestatisticlist")
                .then(literal("multiplayer")
                        .requires(src -> src.hasPermissionLevel(2))
                        .then(literal("mining").executes(ctx -> parseMulti("mining")))
                        .then(literal("placing").executes(ctx -> parseMulti("placing")))
                        .then(literal("off").executes(ctx -> parseMulti("off")))
                        .then(literal("cycle").executes(ctx -> parseMulti("cycle")))
                )
                .then(literal("singleplayer")
                        .then(literal("mining").executes(ctx -> parseSingle(ctx.getSource().getPlayer(), "mining")))
                        .then(literal("placing").executes(ctx -> parseSingle(ctx.getSource().getPlayer(), "placing")))
                        .then(literal("off").executes(ctx -> parseSingle(ctx.getSource().getPlayer(), "off")))
                        .then(literal("cycle").executes(ctx -> parseSingle(ctx.getSource().getPlayer(), "cycle")))
                )
        );
    }

    public static int parseMulti(String option) {
        globalScoreboardDisplayName = option;
        switch (globalScoreboardDisplayName) {
            case "mining":
                break;
            case "placing":
                break;
            case "off":
                break;
            case "cycle":
                //TODO
                break;
        }
        return 1;
    }

    public static int parseSingle(ServerPlayerEntity player, String option) {
        switch (option) {
            case "mining":
                singleMiningOrPlacing(player, MiningScoreboardObj);
                break;
            case "placing":
                singleMiningOrPlacing(player, PlacingScoreboardObj);
                break;
            case "off":
                //#if MC<12002
                player.networkHandler.sendPacket(new ScoreboardDisplayS2CPacket(1, null));
                //#else
                //$$ player.networkHandler.sendPacket(new ScoreboardDisplayS2CPacket(ScoreboardDisplaySlot.SIDEBAR, null));
                //#endif
                break;
            case "cycle":
                //TODO
                break;
        }
        return 1;
    }

    public static void singleMiningOrPlacing(ServerPlayerEntity player, ScoreboardObjective objective) {
        //#if MC<12002
        player.networkHandler.sendPacket(new ScoreboardDisplayS2CPacket(1, null));
        //#else
        //$$ player.networkHandler.sendPacket(new ScoreboardDisplayS2CPacket(ScoreboardDisplaySlot.SIDEBAR, null));
        //#endif
        //#if MC<12002
        player.networkHandler.sendPacket(new ScoreboardDisplayS2CPacket(1, objective));
        //#else
        //$$ player.networkHandler.sendPacket(new ScoreboardDisplayS2CPacket(ScoreboardDisplaySlot.SIDEBAR, objective));
        //#endif
    }
    public static void multiMiningOrPlacing(ScoreboardObjective objective) {
        //#if MC<12002
        StatisticListScoreboard.setObjectiveSlot(1, null);
        //#else
        //$$ StatisticListScoreboard.setObjectiveSlot(ScoreboardDisplaySlot.SIDEBAR, null);
        //#endif
    }
}