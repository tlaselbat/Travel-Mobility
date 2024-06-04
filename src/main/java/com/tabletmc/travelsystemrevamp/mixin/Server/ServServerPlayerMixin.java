package com.tabletmc.travelsystemrevamp.mixin.Server;

import com.tabletmc.travelsystemrevamp.interfaces.EldenExt;
import com.tabletmc.travelsystemrevamp.interfaces.EntityExt;
import com.tabletmc.travelsystemrevamp.interfaces.ServerPlayerExt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServServerPlayerMixin implements ServerPlayerExt {

    @Shadow public abstract void sendMessage(Text message, boolean actionBar);


    @Unique
    public abstract void setServerWorld(ServerWorld world);

    @Unique
    public abstract ServerWorld getWorld(); // returns ServerWorld instance from ServerPlayerEntity instance
    @Unique
    private HorseEntity tsvHorse = null;

    @Override
    public HorseEntity getHorse() { return tsvHorse; }

    public void summonHorse(boolean mountPlayer) {
        HorseEntity tsvHorse2 = tsvHorse; // Prevent concurrent modification crash
        if (tsvHorse2 == null) {
            this.sendMessage(Text.of("No Horse Found!"), true);
            return;
        }

        ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;


        tsvHorse2.refreshPositionAndAngles(player.getX(), player.getY(), player.getZ(), player.getYaw(), player.getPitch());
        if (mountPlayer) player.startRiding(tsvHorse2, true);
        tsvHorse2.setVelocity(player.getVelocity());
        player.getWorld().spawnEntity(tsvHorse2);
        // Add glowing effect to old horse if replaced
        if (!mountPlayer) tsvHorse2.addStatusEffect(
                new StatusEffectInstance(StatusEffects.GLOWING,60,0,false,false));
    }

    public void storeHorse(HorseEntity horse) {
        if (tsvHorse != null) {
            if (tsvHorse.getUuid().equals(horse.getUuid())) return;

            this.sendMessage(Text.of("[Elden Horses]: Replaced Old Horse."), false);
            summonHorse(false); // Summon old horse if new one found
        }
        if (horse.getRemovalReason() != null) {
            tsvHorse = null;
            return;
        }
        tsvHorse = horse;
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        NbtCompound horseNbt = nbt.getCompound("tsvHorse");
        if (!horseNbt.isEmpty()) {
            tsvHorse = (HorseEntity) EntityType.getEntityFromNbt(horseNbt, this.getWorld()).get();
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        if (tsvHorse != null && !((ServerPlayerEntity)(Object)this).hasVehicle()) {
            NbtCompound tag = new NbtCompound();
            tsvHorse.saveSelfNbt(tag);
            nbt.put("tsvHorse", tag);
        }
    }

    @Inject(method = "startRiding", at = @At("TAIL"))
    public void startRiding(Entity entity, boolean force, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof HorseEntity horse) {
            if (((EldenExt) horse).hasTsvArmor()) {
                storeHorse(horse);
            }
        }
    }

    @Inject(method = "stopRiding", at = @At("TAIL"))
    public void stopRiding(CallbackInfo ci) {
        if (tsvHorse == null) return;

        if (!((EldenExt) tsvHorse).hasTsvArmor() || tsvHorse.getRemovalReason() != null) {
            tsvHorse = null;
            return;
        }
        tsvHorse.remove(Entity.RemovalReason.DISCARDED);
        ((EntityExt) tsvHorse).undoRemove();  // Set removalReason to null so that horse can be spawned
        ((ServerPlayerEntity)(Object)this).fallDistance = tsvHorse.fallDistance;
    }

}
