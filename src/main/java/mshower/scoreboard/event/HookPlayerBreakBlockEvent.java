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
//#endif

import static mshower.scoreboard.SimpleStatisticList.*;

public class HookPlayerBreakBlockEvent {
    public static void hook() {
        PlayerBlockBreakEvents.AFTER.register(((world, player, pos, state, blockEntity) -> {

            //#if MC < 12003
            ScoreboardPlayerScore score = StatisticListScoreboard.getPlayerScore(player.getName().getString(), MiningScoreboardObj);//获取玩家计分对象
            int player_score = score.getScore();//获取玩家当前的计分数
            player_score++;//分数递增
            score.setScore(player_score);//更新玩家的分数
            //#else
            //$$ ScoreAccess access = StatisticListScoreboard.getOrCreateScore(player, MiningScoreboardObj);
            //$$ int score = access.getScore();
            //$$ score++;
            //$$ access.setScore(score);
            //#endif
        }));
    }
}
