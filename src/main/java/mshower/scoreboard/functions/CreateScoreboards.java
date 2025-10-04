package mshower.scoreboard.functions;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

//#if MC>=12002
//$$ import net.minecraft.scoreboard.ScoreboardDisplaySlot;
//#endif

import net.minecraft.scoreboard.ScoreboardCriterion;
//#if MC<11900
//$$import net.minecraft.text.LiteralText;
//#else
import net.minecraft.text.Text;
//#endif

//#if MC<11900
import net.minecraft.world.dimension.DimensionType;
//#endif
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import java.util.Objects;

import static mshower.scoreboard.SimpleStatisticList.StatisticListScoreboard;
import static mshower.scoreboard.SimpleStatisticList.MiningScoreboardObj;
import static mshower.scoreboard.SimpleStatisticList.PlacingScoreboardObj;

public class CreateScoreboards {
    public static void create(final String mining_name, final String placing_name, final String mining_display_name, final String placing_display_name) {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            ServerWorld overworld;
            //#if MC < 11600
            //$$ overworld = server.getWorld(DimensionType.OVERWORLD);
            //#else
            overworld = server.getWorld(World.OVERWORLD);
            //#endif
            StatisticListScoreboard = Objects.requireNonNull(overworld).getScoreboard();
            MiningScoreboardObj = StatisticListScoreboard.getNullableObjective(mining_name);
            if (MiningScoreboardObj == null) {
                //#if MC<11900
                //$$ MiningScoreboardObj = StatisticListScoreboard.addObjective(mining_name, ScoreboardCriterion.DUMMY, new LiteralText(mining_display_name), ScoreboardCriterion.RenderType.INTEGER);
                //#elseif MC>=11900 && MC<12003
                MiningScoreboardObj = StatisticListScoreboard.addObjective(mining_name, ScoreboardCriterion.DUMMY, Text.literal(mining_display_name), ScoreboardCriterion.RenderType.INTEGER);
                //#elseif MC>=12003
                //$$MiningScoreboardObj = StatisticListScoreboard.addObjective(mining_name, ScoreboardCriterion.DUMMY, Text.literal(mining_display_name), ScoreboardCriterion.RenderType.INTEGER, true, null);
                //#endif

                //#if MC<12002
                StatisticListScoreboard.setObjectiveSlot(1, null);
                //#else
                //$$ StatisticListScoreboard.setObjectiveSlot(ScoreboardDisplaySlot.SIDEBAR, null);
                //#endif
            }else{
                //#if MC<11900
                //$$ MiningScoreboardObj.setDisplayName(new LiteralText(mining_display_name));
                //#elseif MC>=12001
                MiningScoreboardObj.setDisplayName(Text.literal(mining_display_name));
                //#endif
            }

            PlacingScoreboardObj = StatisticListScoreboard.getNullableObjective(placing_name);
            if (PlacingScoreboardObj == null) {
                //#if MC<11900
                //$$ PlacingScoreboardObj = StatisticListScoreboard.addObjective(placing_name, ScoreboardCriterion.DUMMY, new LiteralText(placing_display_name), ScoreboardCriterion.RenderType.INTEGER);
                //#elseif MC>=11900 && MC<12003
                PlacingScoreboardObj = StatisticListScoreboard.addObjective(placing_name, ScoreboardCriterion.DUMMY, Text.literal(placing_display_name), ScoreboardCriterion.RenderType.INTEGER);
                //#elseif MC>=12003
                //$$PlacingScoreboardObj = StatisticListScoreboard.addObjective(placing_name, ScoreboardCriterion.DUMMY, Text.literal(placing_display_name), ScoreboardCriterion.RenderType.INTEGER, true, null);
                //#endif

                //#if MC<12002
                StatisticListScoreboard.setObjectiveSlot(1, null);
                //#else
                //$$ StatisticListScoreboard.setObjectiveSlot(ScoreboardDisplaySlot.SIDEBAR, null);
                //#endif
            }else{
                //#if MC<11900
                //$$ PlacingScoreboardObj.setDisplayName(new LiteralText(placing_display_name));
                //#elseif MC>=12003
                PlacingScoreboardObj.setDisplayName(Text.literal(placing_display_name));
                //#endif
            }
        });
    }
}
