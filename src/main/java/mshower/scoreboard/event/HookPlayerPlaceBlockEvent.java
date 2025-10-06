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
//#endif

import java.util.Objects;

import static mshower.scoreboard.SimpleStatisticList.*;
import static mshower.scoreboard.functions.Utils.addScore;
import static mshower.scoreboard.functions.Utils.isClient;


public class HookPlayerPlaceBlockEvent {

    public static void hook() {
        //#if MC<12109
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {

            if (isClient(world)) return ActionResult.PASS;
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
        //$$     if (isClient(world)) return ActionResult.PASS;
        //$$     if (!(player instanceof ServerPlayerEntity serverPlayer)) return ActionResult.PASS;
        //$$     ItemStack stack = player.getStackInHand(hand);
        //$$     if (stack.getItem() instanceof BlockItem) {
        //$$         ServerWorld serverWorld = serverPlayer.getEntityWorld();
        //$$         serverWorld.getServer().execute(() -> {
        //$$             addScore(PlacingScoreboardObj, serverPlayer);
        //$$         });
        //$$     }
        //$$     return ActionResult.PASS;
        //$$ });
        //#endif
    }
}
