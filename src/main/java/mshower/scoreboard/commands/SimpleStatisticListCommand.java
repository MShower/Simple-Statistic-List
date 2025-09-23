package mshower.scoreboard.commands;

import com.mojang.brigadier.CommandDispatcher;
import mshower.scoreboard.config.ScoreboardConfig;
import net.minecraft.network.packet.s2c.play.ScoreboardDisplayS2CPacket;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import java.io.IOException;
import java.util.Objects;

import static mshower.scoreboard.SimpleStatisticList.*;
import static net.minecraft.server.command.CommandManager.literal;

public class SimpleStatisticListCommand {
    public static ScoreboardConfig Config;
    public static String globalScoreboardDisplayMode = "Cycle";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("simplestatisticlist")
                .then(literal("multiplayer")
                        .requires(src -> src.hasPermissionLevel(2))
                        .then(literal("mining").executes(ctx -> {
                            try {
                                return parseMulti("Mining");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }))
                        .then(literal("placing").executes(ctx -> {
                            try {
                                return parseMulti("Placing");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }))
                        .then(literal("off").executes(ctx -> {
                            try {
                                return parseMulti("Off");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }))
                        .then(literal("cycle").executes(ctx -> {
                            try {
                                return parseMulti("Cycle");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }))
                )
                .then(literal("singleplayer")
                        .then(literal("mining").executes(ctx -> parseSingle(ctx.getSource().getPlayer(), "Mining")))
                        .then(literal("placing").executes(ctx -> parseSingle(ctx.getSource().getPlayer(), "Placing")))
                        .then(literal("off").executes(ctx -> parseSingle(ctx.getSource().getPlayer(), "Off")))
                        .then(literal("cycle").executes(ctx -> parseSingle(ctx.getSource().getPlayer(), "Cycle")))
                        .then(literal("default").executes(ctx -> parseSingle(ctx.getSource().getPlayer(), "Default")))
                )
        );
    }

    public static int parseMulti(String option) throws IOException {
        refreshDisplayModeFromConfig();
        setGlobalScoreboardDisplayMode(option);
        switch (globalScoreboardDisplayMode) {
            case "Mining":
                multiMiningOrPlacing(MiningScoreboardObj);
                break;
            case "Placing":
                multiMiningOrPlacing(PlacingScoreboardObj);
                break;
            case "Off":
                //#if MC<12002
                StatisticListScoreboard.setObjectiveSlot(1, null);
                //#else
                //$$ StatisticListScoreboard.setObjectiveSlot(ScoreboardDisplaySlot.SIDEBAR, null);
                //#endif
                break;
            case "Cycle":
                //TODO
                break;
        }
        return 1;
    }

    public static int parseSingle(ServerPlayerEntity player, String option) {
        refreshDisplayModeFromConfig();
        if (Objects.equals(option, "Default")) {
            parseSingleDefault(player, option);
        } else {
            parseSingleDefault(player, globalScoreboardDisplayMode);
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
        //#if MC<12002
        StatisticListScoreboard.setObjectiveSlot(1, objective);
        //#else
        //$$ StatisticListScoreboard.setObjectiveSlot(ScoreboardDisplaySlot.SIDEBAR, objective);
        //#endif
    }
    public static void parseSingleDefault(ServerPlayerEntity player, String option) {
        switch (option) {
            case "Mining":
                singleMiningOrPlacing(player, MiningScoreboardObj);
                break;
            case "Placing":
                singleMiningOrPlacing(player, PlacingScoreboardObj);
                break;
            case "Off":
                //#if MC<12002
                player.networkHandler.sendPacket(new ScoreboardDisplayS2CPacket(1, null));
                //#else
                //$$ player.networkHandler.sendPacket(new ScoreboardDisplayS2CPacket(ScoreboardDisplaySlot.SIDEBAR, null));
                //#endif
                break;
            case "Cycle":
                //TODO
                break;
            case "Default":
                break;
        }
    }
    public static void setGlobalScoreboardDisplayMode(String globalScoreboardDisplayMode) throws IOException {
        SimpleStatisticListCommand.globalScoreboardDisplayMode = globalScoreboardDisplayMode;
        Config.UpdateValue("DisplayMode", globalScoreboardDisplayMode);
    }

    public static void refreshDisplayModeFromConfig() {
        globalScoreboardDisplayMode = Config.GetValue("DisplayMode");
    }
}