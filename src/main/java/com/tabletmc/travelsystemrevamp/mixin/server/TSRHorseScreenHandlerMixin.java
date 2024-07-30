package com.tabletmc.travelsystemrevamp.mixin.server;

import com.tabletmc.travelsystemrevamp.net.impl.TSRExt;
import com.tabletmc.travelsystemrevamp.net.impl.TSRServerPlayerExt;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.HorseScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseScreenHandler.class)
public abstract class TSRHorseScreenHandlerMixin {
    @Shadow @Final private AbstractHorseEntity entity;

    @Inject(method = "onClosed", at = @At("HEAD"))
    public void transferSlot(PlayerEntity player, CallbackInfo ci) {
        if (entity instanceof HorseEntity horse) {
            ((TSRExt) horse).updateTsvArmor();
            if (player instanceof ServerPlayerEntity serverPlayer) {
                if (horse.equals(serverPlayer.getVehicle()) && ((TSRExt) horse).hasTsvArmor()) {
                    ((TSRServerPlayerExt) serverPlayer).storeHorse(horse);
                }
            }
        }
    }

}
