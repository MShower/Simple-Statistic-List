package mshower.scoreboard.mixins;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static mshower.scoreboard.SimpleStatisticList.DeathScoreboardObj;
import static mshower.scoreboard.functions.AddScore.addScore;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(method = "onDeath", at = @At("HEAD"))
    private void onPlayerDeath(DamageSource damageSource, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
        addScore(DeathScoreboardObj, player);
    }
}