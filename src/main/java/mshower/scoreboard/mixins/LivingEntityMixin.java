package mshower.scoreboard.mixins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static mshower.scoreboard.SimpleStatisticList.KillEntityScoreboardObj;
import static mshower.scoreboard.functions.Utils.addScore;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "onDeath", at = @At("HEAD"))
    private void onDeathInject(DamageSource source, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        //#if MC<12108
        if (!self.getEntityWorld().isClient) {
        //#elseif MC=12108
        //$$ if (!self.getWorld().isClient) {
        //#else
        //$$ if (!self.getEntityWorld().isClient()) {
        //#endif
            if (source.getAttacker() instanceof ServerPlayerEntity) {
                ServerPlayerEntity player = (ServerPlayerEntity) source.getAttacker();
                addScore(KillEntityScoreboardObj, player);
            }
        }
    }
}