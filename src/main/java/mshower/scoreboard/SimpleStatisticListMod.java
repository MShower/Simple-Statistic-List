package mshower.scoreboard;

import mshower.scoreboard.commands.SimpleStatisticListCommand;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

import java.io.IOException;

import static mshower.scoreboard.SimpleStatisticList.MiningScoreboardObj;
import static mshower.scoreboard.SimpleStatisticList.PlacingScoreboardObj;
import static mshower.scoreboard.commands.SimpleStatisticListCommand.Config;

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
