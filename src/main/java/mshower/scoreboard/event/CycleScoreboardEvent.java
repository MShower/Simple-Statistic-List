package mshower.scoreboard.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import java.util.Objects;

import static mshower.scoreboard.SimpleStatisticList.*;
import static mshower.scoreboard.command.SimpleStatisticListCommand.generalSwitching;
import static mshower.scoreboard.command.SimpleStatisticListCommand.globalScoreboardDisplayMode;

public class CycleScoreboardEvent {
    private static int tickCounter = 0;
    private static int switchScoreboard = 0;
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
                    switch (switchScoreboard) {
                        case 0:
                            generalSwitching(MiningScoreboardObj);
                            break;
                        case 1:
                            generalSwitching(PlacingScoreboardObj);
                            break;
                        case 2:
                            generalSwitching(DeathScoreboardObj);
                            break;
                    }
                    setSwitchScoreboard();
                }
            }
        });
    }

    public static void refreshCycleDelayFromConfig() {
        cycleDelay = Integer.parseInt(Config.GetValue("CycleDelay"));
    }

    private static void setSwitchScoreboard() {
        if (switchScoreboard < 3) {
            switchScoreboard++;
        }
        else {
            switchScoreboard = 0;
        }
    }
}
