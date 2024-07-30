package com.tabletmc.travelsystemrevamp.mixin.server;

import com.tabletmc.travelsystemrevamp.net.impl.TSREntityExt;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class TSREntityMixin implements TSREntityExt {
    @Shadow @Nullable private Entity.RemovalReason removalReason;

    public void undoRemove() { removalReason = null; }
}
