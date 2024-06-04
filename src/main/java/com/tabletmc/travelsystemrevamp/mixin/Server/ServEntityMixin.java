package com.tabletmc.travelsystemrevamp.mixin.Server;

import com.tabletmc.travelsystemrevamp.interfaces.EntityExt;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class ServEntityMixin implements EntityExt {
    @Shadow @Nullable private Entity.RemovalReason removalReason;

    public void undoRemove() { removalReason = null; }
}
