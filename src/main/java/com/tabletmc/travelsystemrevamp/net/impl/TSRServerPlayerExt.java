package com.tabletmc.travelsystemrevamp.net.impl;

import net.minecraft.entity.passive.HorseEntity;

public interface TSRServerPlayerExt {

    void storeHorse(HorseEntity horse);

    void summonHorse(boolean mountPlayer);
    HorseEntity getHorse();
}
