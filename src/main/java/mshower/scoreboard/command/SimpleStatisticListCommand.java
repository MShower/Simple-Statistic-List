package mshower.scoreboard.command;

import com.mojang.brigadier.CommandDispatcher;
import mshower.scoreboard.SimpleStatisticList;
import mshower.scoreboard.config.ScoreboardConfig;
//#if MC>=12002
//$$ import net.minecraft.scoreboard.ScoreboardDisplaySlot;
//#endif
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.server.command.ServerCommandSource;

import java.io.IOException;

import static mshower.scoreboard.SimpleStatisticList.*;
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
                        })))
        );
    }

    public static int parseMulti(String option) throws IOException {
        refreshDisplayModeFromConfig();
        setGlobalScoreboardDisplayMode(option);
        switch (globalScoreboardDisplayMode) {
            case "Mining":
                MiningOrPlacing(MiningScoreboardObj);
                break;
            case "Placing":
                MiningOrPlacing(PlacingScoreboardObj);
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
    public static void MiningOrPlacing(ScoreboardObjective objective) {
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
                MiningOrPlacing(MiningScoreboardObj);
                break;
            case "Placing":
                MiningOrPlacing(PlacingScoreboardObj);
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
    }
}