package com.tabletmc.travelsystemrevamp.mixin.Server;

import com.tabletmc.travelsystemrevamp.interfaces.EldenExt;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.tabletmc.travelsystemrevamp.item.ModItems.NETHERITE_HORSE_ARMOR;

@Mixin(value = AbstractHorseEntity.class)
public abstract class ServHorseBaseEntityMixin implements EldenExt{

    @Shadow protected float jumpStrength;
    @Unique
    private boolean doubleJumped = false;
    @Unique
    private boolean tsvArmor = false;

    @Override
    public boolean hasTsvArmor() {
        return tsvArmor;
    }

    @Override
    public void updateTsvArmor() {
        tsvArmor = ((HorseEntity)(Object)this).getArmorItems().equals(NETHERITE_HORSE_ARMOR.asItem());
    }



    @Inject(method="writeCustomDataToNbt", at = @At("HEAD"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("tsvHorseArmor", tsvArmor);
    }

    @Inject(method="readCustomDataFromNbt", at = @At("HEAD"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        tsvArmor = nbt.getBoolean("tsvHorseArmor");
    }

    @Inject(method = "handleFallDamage", at = @At("RETURN"), cancellable = true)
    private void computeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Integer> cir) {
        if (hasTsvArmor()) {
            cir.setReturnValue(cir.getReturnValue() - 4);
        }
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo ci) {
        AbstractHorseEntity ahe = (AbstractHorseEntity)(Object)this;

        if (ahe.hasPlayerRider() && hasTsvArmor()) {
            if (ahe.isOnGround() && !ahe.isInAir()) doubleJumped = false;

            if (!doubleJumped && jumpStrength > 0.0F && ahe.isInAir() && !ahe.isOnGround())  {
                ahe.setVelocity(ahe.getVelocity().x, ahe.getJumpBoostVelocityModifier(), ahe.getVelocity().z);
                float h = MathHelper.sin(ahe.getYaw() * 0.017453292F);
                float i = MathHelper.cos(ahe.getYaw() * 0.017453292F);
                ahe.setVelocity(ahe.getVelocity().add(-0.4F * h * jumpStrength, 0.0, 0.4F * i * jumpStrength));

                doubleJumped = true;
            }
        }
    }


        @Inject(method = "jump", at = @At("HEAD"))
        public void getJumpStrength(float strength, Vec3d movementInput, CallbackInfo ci) {
            AbstractHorseEntity ahe = (AbstractHorseEntity)(Object)this;
            if (strength < 90) {
                ahe.setJumpStrength(91);

            }
            }
    }

