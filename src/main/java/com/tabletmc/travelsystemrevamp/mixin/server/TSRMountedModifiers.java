package com.tabletmc.travelsystemrevamp.mixin.server;

import com.tabletmc.travelsystemrevamp.config.TSRModConfig;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

// adds DataPack attribute modifiers to player & horse while mounted to
// - increase horse's StepHeight by 10% (when enabled)
// - remove BreakSpeed debuff from not being grounded (when enabled)
@Mixin(value = PlayerEntity.class, priority = 960)
public abstract class TSRMountedModifiers extends LivingEntity {
    protected TSRMountedModifiers(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    final
    EntityAttributeModifier mountedStepHeight = new EntityAttributeModifier(new Identifier("horse-buff", "mounted-step-height"), 0.1, EntityAttributeModifier.Operation.ADD_VALUE);
    @Unique
    final
    EntityAttributeModifier mountedBreakSpeed = new EntityAttributeModifier(new Identifier("horse-buff", "mounted-break-speed"), 5, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);

    @Override
    public boolean startRiding(Entity entity, boolean force) {
        boolean result = super.startRiding(entity, force);
        if (!(super.getWorld() instanceof ServerWorld && entity instanceof AbstractHorseEntity horse))
            return result;

        if (TSRModConfig.getInstance().stepHeight) {
            EntityAttributeInstance stepHeight = horse.getAttributeInstance(EntityAttributes.GENERIC_STEP_HEIGHT);
            if (stepHeight != null) stepHeight.addTemporaryModifier(mountedStepHeight);
        }

        if (TSRModConfig.getInstance().breakSpeed) {
            EntityAttributeInstance breakSpeed = getAttributeInstance(EntityAttributes.PLAYER_BLOCK_BREAK_SPEED);
            if (breakSpeed != null) breakSpeed.addTemporaryModifier(mountedBreakSpeed);
        }
        return result;
    }

    @Override
    public boolean startRiding(Entity entity) {
        return this.startRiding(entity, false);
    }

    @Override
    public void stopRiding() {
        if (!(super.getWorld() instanceof ServerWorld && getVehicle() instanceof AbstractHorseEntity horse)) {
            super.stopRiding();
            return;
        }

        EntityAttributeInstance stepHeight = horse.getAttributeInstance(EntityAttributes.GENERIC_STEP_HEIGHT);
        if (stepHeight != null) stepHeight.removeModifier(mountedStepHeight);

        EntityAttributeInstance breakSpeed = getAttributeInstance(EntityAttributes.PLAYER_BLOCK_BREAK_SPEED);
        if (breakSpeed != null) breakSpeed.removeModifier(mountedBreakSpeed);

        super.stopRiding();
    }
}
