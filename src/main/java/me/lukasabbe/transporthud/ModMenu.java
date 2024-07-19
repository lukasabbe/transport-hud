package me.lukasabbe.transporthud;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

public class ModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.translatable("transporthud.config.title"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            builder.getOrCreateCategory(Text.translatable("transporthud.config.cat"))
                    .addEntry(entryBuilder.startBooleanToggle(Text.translatable("transporthud.option.hud.enabled"),Config.isHudOn)
                            .setDefaultValue(true).setSaveConsumer(newVal->Config.isHudOn = newVal).build())
                    .addEntry(entryBuilder.startIntField(Text.translatable("transporthud.option.delay.text"),Config.hudDelay)
                            .setDefaultValue(2).setSaveConsumer(newVal -> Config.hudDelay = newVal).build())
                    .addEntry(entryBuilder.startBooleanToggle(Text.translatable("transporthud.option.elytraDmgLvl.info"),Config.isElytraDmgStatusOn)
                            .setDefaultValue(true).setSaveConsumer(newVal -> Config.isElytraDmgStatusOn = newVal).build())
                    .addEntry(entryBuilder.startBooleanToggle(Text.translatable("transporthud.option.coords.text"),Config.hudCords)
                            .setDefaultValue(true).setSaveConsumer(newVal -> Config.hudCords = newVal).build());
            builder.setSavingRunnable(Config::save);
            return builder.build();
        };
    }
}
