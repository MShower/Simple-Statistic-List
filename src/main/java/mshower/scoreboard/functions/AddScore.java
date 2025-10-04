package mshower.scoreboard.functions;

//#if MC>=12003
//$$ import net.minecraft.nbt.NbtCompound;
//$$ import net.minecraft.scoreboard.ReadableScoreboardScore;
//$$ import net.minecraft.scoreboard.ScoreAccess;
//$$ import net.minecraft.scoreboard.ScoreboardScore;
//#else
import net.minecraft.scoreboard.ScoreboardPlayerScore;
//#endif

import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.server.network.ServerPlayerEntity;

import static mshower.scoreboard.SimpleStatisticList.StatisticListScoreboard;

public class AddScore {
    public static void addScore(ScoreboardObjective scoreboardObj, ServerPlayerEntity player) {
        //#if MC < 12003
        ScoreboardPlayerScore score = StatisticListScoreboard.getPlayerScore(player.getName().getString(), scoreboardObj);
        int player_score = score.getScore();
        player_score++;
        score.setScore(player_score);
        //#else
        //$$ ScoreAccess access = StatisticListScoreboard.getOrCreateScore(player, scoreboardObj);
        //$$ int score = access.getScore();
        //$$ score++;
        //$$ access.setScore(score);
        //#endif
    }
}