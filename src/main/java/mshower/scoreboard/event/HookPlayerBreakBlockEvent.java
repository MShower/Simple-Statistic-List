package mshower.scoreboard.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

//#if MC>=12003
//$$ import net.minecraft.nbt.NbtCompound;
//$$ import net.minecraft.scoreboard.ReadableScoreboardScore;
//$$ import net.minecraft.scoreboard.ScoreAccess;
//$$ import net.minecraft.scoreboard.ScoreboardObjective;
//$$ import net.minecraft.scoreboard.ScoreboardScore;
//#else
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.server.network.ServerPlayerEntity;
//#endif

import static mshower.scoreboard.SimpleStatisticList.*;
import static mshower.scoreboard.functions.AddScore.addScore;

public class HookPlayerBreakBlockEvent {
    public static void hook() {
        PlayerBlockBreakEvents.AFTER.register(((world, player, pos, state, blockEntity) -> {
            addScore(MiningScoreboardObj, (ServerPlayerEntity) player);
        }));
    }
}
