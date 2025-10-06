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
import net.minecraft.world.World;

import static mshower.scoreboard.SimpleStatisticList.StatisticListScoreboard;

public class Utils {
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

    public static void setScore(ScoreboardObjective scoreboardObj, ServerPlayerEntity player, Integer number) {
        //#if MC < 12003
        ScoreboardPlayerScore score = StatisticListScoreboard.getPlayerScore(player.getName().getString(), scoreboardObj);
        score.setScore(number);
        //#else
        //$$ ScoreAccess access = StatisticListScoreboard.getOrCreateScore(player, scoreboardObj);
        //$$ access.setScore(number);
        //#endif
    }

    public static String stripColor(String s) {
        return s.replaceAll("§.", "");
    }

    public static boolean isClient(World world) {
        //#if MC<12009
        return world.isClient;
        //#else
        //$$ return world.isClient();
        //#endif
    }
}