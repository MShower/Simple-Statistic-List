package mshower.scoreboard.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.server.network.ServerPlayerEntity;

import static mshower.scoreboard.SimpleStatisticList.*;
import static mshower.scoreboard.functions.AddScore.addScore;

public class HookPlayerDeadEvent {
    public static void hook() {
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            if (!alive) {
                addScore(DeathScoreboardObj, oldPlayer);
            }
        });
    }
}
