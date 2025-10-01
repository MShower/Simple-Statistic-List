package mshower.scoreboard;

import mshower.scoreboard.command.SimpleStatisticListCommand;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

import static mshower.scoreboard.SimpleStatisticList.*;

public class SimpleStatisticListMod {
    public static void init() {
        ServerLifecycleEvents.SERVER_STARTED.register(SimpleStatisticListMod::onServerStarted);
    }

    private static void onServerStarted(MinecraftServer server) {
        SimpleStatisticListCommand.onServerStarted();
        SimpleStatisticList.LOGGER.info("Simple Statistic List Loaded!");
        System.out.println("Config = " + Config);
        System.out.println("MiningScoreboardObj = " + MiningScoreboardObj);
        System.out.println("PlacingScoreboardObj = " + PlacingScoreboardObj);
    }
}
