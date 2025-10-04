package mshower.scoreboard.functions;

import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
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