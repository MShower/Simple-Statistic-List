package mshower.scoreboard.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

import net.minecraft.server.network.ServerPlayerEntity;

//#if MC>=12003
//$$ import net.minecraft.scoreboard.ScoreAccess;
//$$ import net.minecraft.scoreboard.ScoreboardObjective;
//$$ import net.minecraft.scoreboard.ReadableScoreboardScore;
//#else
import net.minecraft.scoreboard.ScoreboardPlayerScore;
//#endif

import java.util.Objects;

import static mshower.scoreboard.SimpleStatisticList.*;


public class HookPlayerPlaceBlockEvent {

    public static void hook() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {

            //#if MC<11600
            if (world.isClient) return ActionResult.PASS;
            //#else
            //$$ if (world.isClient()) return ActionResult.PASS;
            //#endif
            //CANT COUNT REPLACING BLOCK
            ItemStack stack = player.getStackInHand(hand);
            if (stack.getItem() instanceof BlockItem) {
                BlockItem blockItem = (BlockItem) stack.getItem();
                BlockPos placePos = hitResult.getBlockPos().offset(hitResult.getSide());

                Objects.requireNonNull(player.getServer()).execute(() -> {
                    if (world.getBlockState(placePos).getBlock() == blockItem.getBlock()) {
                        addPlacingScore((ServerPlayerEntity) player);
                    }
                });
            }
            return ActionResult.PASS;
        });
    }

    private static void addPlacingScore(ServerPlayerEntity player) {
        //#if MC < 12003
        ScoreboardPlayerScore score = StatisticListScoreboard.getPlayerScore(player.getName().getString(), PlacingScoreboardObj);
        int player_score = score.getScore();
        player_score++;
        score.setScore(player_score);
        //#else
        //$$ ScoreAccess access = StatisticListScoreboard.getOrCreateScore(player, PlacingScoreboardObj);
        //$$ int score = access.getScore();
        //$$ score++;
        //$$ access.setScore(score);
        //#endif
    }
}
