package com.tabletmc.travelsystemrevamp.mixin.client;

import com.tabletmc.travelsystemrevamp.net.impl.TSRExt;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.HorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerMixin {
    @Inject(method = "startRiding", at = @At("HEAD"))
    public void startRiding(Entity entity, boolean force, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof HorseEntity horse) ((TSRExt) horse).updateTsvArmor();
    }
}
