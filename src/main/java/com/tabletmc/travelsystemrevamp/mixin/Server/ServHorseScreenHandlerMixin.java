package com.tabletmc.travelsystemrevamp.mixin.Server;

import com.tabletmc.travelsystemrevamp.interfaces.EldenExt;
import com.tabletmc.travelsystemrevamp.interfaces.ServerPlayerExt;
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
public abstract class ServHorseScreenHandlerMixin {
    @Shadow @Final private AbstractHorseEntity entity;

    @Inject(method = "onClosed", at = @At("HEAD"))
    public void transferSlot(PlayerEntity player, CallbackInfo ci) {
        if (entity instanceof HorseEntity horse) {
            ((EldenExt) horse).updateTsvArmor();
            if (player instanceof ServerPlayerEntity serverPlayer) {
                if (horse.equals(serverPlayer.getVehicle()) && ((EldenExt) horse).hasTsvArmor()) {
                    ((ServerPlayerExt) serverPlayer).storeHorse(horse);
                }
            }
        }
    }

}
