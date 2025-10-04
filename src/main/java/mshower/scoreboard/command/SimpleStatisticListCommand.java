package mshower.scoreboard.command;

import com.mojang.brigadier.CommandDispatcher;
//#if MC>=12002
//$$ import net.minecraft.scoreboard.ScoreboardDisplaySlot;
//#endif
import com.mojang.brigadier.arguments.StringArgumentType;
import mshower.scoreboard.functions.RemoveScoresWithSpecifiedPlayer;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import mshower.scoreboard.functions.RemoveScoresWithPlayerPrefix;
import net.minecraft.text.Text;

import java.io.IOException;
import java.util.List;

import static mshower.scoreboard.SimpleStatisticList.*;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class SimpleStatisticListCommand {
    public static String globalScoreboardDisplayMode = "Cycle";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("simplestatisticlist")
                .then(literal("display")
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
                        .then(literal("death").executes(ctx -> {
                            try {
                                return parseMulti("Death");
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
                .then(literal("method")
                        .then(literal("removeScoresWithPrefixInPlayerName")
                                .then(argument("prefix", StringArgumentType.word())
                                        .executes(ctx -> {
                                            String prefix = StringArgumentType.getString(ctx, "prefix");
                                            MinecraftServer server = ctx.getSource().getServer();
                                            return RemoveScoresWithPlayerPrefix.remove(server, prefix);
                                        })
                                )
                        )
                        .then(literal("removeScoresWithSpecifiedPlayer")
                                .then(argument("player", StringArgumentType.word())
                                        .executes(ctx -> {
                                            String player = StringArgumentType.getString(ctx, "player");
                                            MinecraftServer server = ctx.getSource().getServer();
                                            return RemoveScoresWithSpecifiedPlayer.remove(server, player);
                                        })
                                )
                        )
                )
        );
    }

    public static int parseMulti(String option) throws IOException {
        refreshDisplayModeFromConfig();
        setGlobalScoreboardDisplayMode(option);
        switch (globalScoreboardDisplayMode) {
            case "Mining":
                generalSwitching(MiningScoreboardObj);
                break;
            case "Placing":
                generalSwitching(PlacingScoreboardObj);
                break;
            case "Death":
                generalSwitching(DeathScoreboardObj);
                break;
            case "Off":
                //#if MC<12002
                StatisticListScoreboard.setObjectiveSlot(1, null);
                //#else
                //$$ StatisticListScoreboard.setObjectiveSlot(ScoreboardDisplaySlot.SIDEBAR, null);
                //#endif
                break;
            case "Cycle":
                break;
        }
        return 1;
    }
    public static void generalSwitching(ScoreboardObjective objective) {
        //#if MC<12002
        StatisticListScoreboard.setObjectiveSlot(1, objective);
        //#else
        //$$ StatisticListScoreboard.setObjectiveSlot(ScoreboardDisplaySlot.SIDEBAR, objective);
        //#endif
    }

    public static void setGlobalScoreboardDisplayMode(String globalScoreboardDisplayMode) throws IOException {
        SimpleStatisticListCommand.globalScoreboardDisplayMode = globalScoreboardDisplayMode;
        Config.UpdateValue("DisplayMode", globalScoreboardDisplayMode);
    }

    public static void refreshDisplayModeFromConfig() {
        globalScoreboardDisplayMode = Config.GetValue("DisplayMode");
    }

    public static void onServerStarted() {
        switch (globalScoreboardDisplayMode) {
            case "Mining":
                generalSwitching(MiningScoreboardObj);
                break;
            case "Placing":
                generalSwitching(PlacingScoreboardObj);
                break;
            case "Death":
                generalSwitching(DeathScoreboardObj);
                break;
            case "Off":
                //#if MC<12002
                StatisticListScoreboard.setObjectiveSlot(1, null);
                //#else
                //$$ StatisticListScoreboard.setObjectiveSlot(ScoreboardDisplaySlot.SIDEBAR, null);
                //#endif
                break;
            case "Cycle":
                break;
        }
    }
}