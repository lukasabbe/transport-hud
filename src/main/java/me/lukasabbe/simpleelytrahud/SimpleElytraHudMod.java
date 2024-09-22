package me.lukasabbe.simpleelytrahud;

import me.lukasabbe.simpleelytrahud.config.Config;
import me.lukasabbe.simpleelytrahud.huds.ElytraHUD;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleElytraHudMod implements ClientModInitializer {
    public static String MOD_ID = "simpleelytrahud";
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitializeClient() {
        Config.load();
        final ElytraHUD listener = new ElytraHUD(MinecraftClient.getInstance());
        HudRenderCallback.EVENT.register(listener);
        ClientTickEvents.END_CLIENT_TICK.register((client) -> listener.data.updateData());
    }
}
