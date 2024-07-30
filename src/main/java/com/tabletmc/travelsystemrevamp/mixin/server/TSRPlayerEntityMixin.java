package com.tabletmc.travelsystemrevamp.mixin.server;

import com.tabletmc.travelsystemrevamp.net.impl.TSRExt;
import net.minecraft.block.BlockState;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class TSRPlayerEntityMixin {

    // Disable block breaking slowdown on elden horse
    @Inject(method="getBlockBreakingSpeed", at=@At("RETURN"), cancellable = true)
    public void getBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cir) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        if (player.hasVehicle()) {
            if (player.getVehicle() instanceof HorseEntity horse)
                if (((TSRExt) horse).hasTsvArmor())
                    cir.setReturnValue(cir.getReturnValue() * 5.0F);
        }
    }

}
