package me.lukasabbe.simpleelytrahud.config;

import dev.isxander.yacl3.api.NameableEnum;
import net.minecraft.text.Text;

public enum SpeedEnum implements NameableEnum {
    km,
    mph,
    m;

    @Override
    public Text getDisplayName() {
        return Text.translatable("simpleelytrahud.option.speed.options." + name().toLowerCase());
    }
}
