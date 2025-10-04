package mshower.scoreboard.event;

import mshower.scoreboard.config.ScoreboardConfig;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import java.io.IOException;
import java.util.Objects;

import static mshower.scoreboard.SimpleStatisticList.MiningScoreboardObj;
import static mshower.scoreboard.SimpleStatisticList.PlacingScoreboardObj;
import static mshower.scoreboard.command.SimpleStatisticListCommand.MiningOrPlacing;
import static mshower.scoreboard.command.SimpleStatisticListCommand.globalScoreboardDisplayMode;
import static mshower.scoreboard.SimpleStatisticList.Config;

public class CycleScoreboardEvent {
    private static int tickCounter = 0;
    private static boolean toggleScoreboard = true;
    private static boolean onStart = true;
    public static int cycleDelay;

    public static void register() {
        refreshCycleDelayFromConfig();
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tickCounter++;
            if (tickCounter >= cycleDelay && onStart) {
                tickCounter = 0;
                onStart = false;
                if (Objects.equals(globalScoreboardDisplayMode, "Cycle")) {
                    if (toggleScoreboard) {
                        toggleScoreboard = false;
                        MiningOrPlacing(MiningScoreboardObj);
                    }
                    else {
                        toggleScoreboard = true;
                        MiningOrPlacing(PlacingScoreboardObj);
                    }
                }
            }
        });
    }

    public static void refreshCycleDelayFromConfig() {
        cycleDelay = Integer.parseInt(Config.GetValue("CycleDelay"));
    }
}
