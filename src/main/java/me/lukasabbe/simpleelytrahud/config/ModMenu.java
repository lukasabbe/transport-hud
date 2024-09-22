package me.lukasabbe.simpleelytrahud.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.text.Text;

public class ModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            final Config config = Config.HANDLER.instance();
            return YetAnotherConfigLib.createBuilder()
                    .title(Text.translatable("transporthud.config.title"))
                    .category(ConfigCategory.createBuilder()
                            .name(Text.translatable("transporthud.config.cat"))
                            .option(Option.<Boolean>createBuilder()
                                    .name(Text.translatable("transporthud.option.hud.enabled"))
                                    .binding(true, ()-> config.isHudOn, newVal -> config.isHudOn = newVal)
                                    .controller(TickBoxControllerBuilder::create)
                                    .build())
                            .option(Option.<Integer>createBuilder()
                                    .name(Text.translatable("transporthud.option.delay.text"))
                                    .binding(2, ()-> config.hudDelay, newVal -> config.hudDelay = newVal)
                                    .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                            .range(0,30)
                                            .step(1))
                                    .build())
                            .option(Option.<Boolean>createBuilder()
                                    .name(Text.translatable("transporthud.option.elytraDmgLvl.info"))
                                    .binding(true, ()-> config.isElytraDmgStatusOn, newVal -> config.isElytraDmgStatusOn = newVal)
                                    .controller(TickBoxControllerBuilder::create)
                                    .build())
                            .option(Option.<Boolean>createBuilder()
                                    .name(Text.translatable("transporthud.option.coords.text"))
                                    .binding(true, ()-> config.hudCords, newVal -> config.hudCords = newVal)
                                    .controller(TickBoxControllerBuilder::create)
                                    .build())
                            .build())
                    .save(()-> Config.HANDLER.save())
                    .build().generateScreen(parent);
        };
    }
}
