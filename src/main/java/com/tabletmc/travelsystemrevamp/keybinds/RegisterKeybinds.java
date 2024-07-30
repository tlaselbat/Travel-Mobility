package com.tabletmc.travelsystemrevamp.keybinds;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class RegisterKeybinds {

    public static final KeyBinding HORSE_INVENTORY_KEY = new KeyBinding(
            "text.HorseBuff.keybinding.horsePlayerInventory",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_ALT,
            "text.HorseBuff.keybinding.category"
    );
    public static final KeyBinding SUMMON_HORSE = new KeyBinding(
            "key.travelsystemrevamp.summerhouse",
            InputUtil.Type.KEYSYM,
            InputUtil.UNKNOWN_KEY.getCode(),
            "travelsystemrevamp.category"
    );

    public static final List<KeyBinding> ALL = List.of(
            HORSE_INVENTORY_KEY,
            SUMMON_HORSE
    );
    }

