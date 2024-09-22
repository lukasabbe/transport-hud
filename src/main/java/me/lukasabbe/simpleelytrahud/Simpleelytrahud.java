package me.lukasabbe.simpleelytrahud;

import com.mojang.logging.LogUtils;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import me.lukasabbe.simpleelytrahud.config.Config;
import me.lukasabbe.simpleelytrahud.config.ConfigBuilder;
import me.lukasabbe.simpleelytrahud.huds.ElytraHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.slf4j.Logger;

@Mod(value = Simpleelytrahud.MODID, dist = Dist.CLIENT)
public class Simpleelytrahud {

    public static final String MODID = "simpleelytrahud";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static ElytraHUD listener;

    public Simpleelytrahud(IEventBus modBus) {
        Config.HANDLER.load();
        listener = new ElytraHUD(Minecraft.getInstance());

        NeoForge.EVENT_BUS.addListener(Simpleelytrahud::onTick);

        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (client, parent) -> ConfigBuilder.configBuilder(parent)
        );
    }

    private static void onTick(PlayerTickEvent.Post event){
        listener.data.updateData();
    }

}
