package com.tabletmc.travelsystemrevamp.mixin.Server;

import com.tabletmc.travelsystemrevamp.interfaces.ServerPlayerExt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerManager.class)
public class ServPlayerManagerMixin {
    @Inject(method="respawnPlayer", at=@At("RETURN"))
    public void respawnPlayer(ServerPlayerEntity player, boolean alive, Entity.RemovalReason removalReason, CallbackInfoReturnable<ServerPlayerEntity> cir) {
            HorseEntity horse = ((ServerPlayerExt) player).getHorse();
            if (horse != null) {
                ((ServerPlayerExt) cir.getReturnValue()).storeHorse(horse); // Transfer horse data to respawned player
            }

    }
}
