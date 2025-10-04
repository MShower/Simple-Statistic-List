package mshower.scoreboard.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
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
import static mshower.scoreboard.functions.AddScore.addScore;


public class HookPlayerPlaceBlockEvent {

    public static void hook() {
        //#if MC<12109
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {

            //#if MC<11600
            if (world.isClient) return ActionResult.PASS;
            //#else
            //$$ if (world.isClient()) return ActionResult.PASS;
            //#endif
            ItemStack stack = player.getStackInHand(hand);
            if (stack.getItem() instanceof BlockItem) {
                BlockItem blockItem = (BlockItem) stack.getItem();
                BlockPos placePos = hitResult.getBlockPos().offset(hitResult.getSide());
                Objects.requireNonNull(player.getServer()).execute(() -> {
                    if (world.getBlockState(placePos).getBlock() == blockItem.getBlock()) {
                        addScore(PlacingScoreboardObj,(ServerPlayerEntity) player);
                    }
                });
            }
            return ActionResult.PASS;
        });
        //#else
        //$$  UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
        //$$     if (world.isClient()) return ActionResult.PASS;
        //$$     if (!(player instanceof ServerPlayerEntity serverPlayer)) return ActionResult.PASS;
        //$$     ItemStack stack = player.getStackInHand(hand);
        //$$     if (stack.getItem() instanceof BlockItem) {
        //$$          ServerWorld serverWorld = (ServerWorld) serverPlayer.getEntityWorld();
        //$$         serverWorld.getServer().execute(() -> {
        //$$             addScore(PlacingScoreboardObj,(ServerPlayerEntity) player);
        //$$         });
        //$$     }
        //$$     return ActionResult.PASS;
        //$$ });
        //#endif
    }
}
