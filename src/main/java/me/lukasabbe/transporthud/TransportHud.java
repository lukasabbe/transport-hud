package me.lukasabbe.transporthud;

import me.lukasabbe.transporthud.huds.ElytraHUD;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransportHud implements ClientModInitializer {
    public static String MOD_ID = "transporthud";
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitializeClient() {
        Config.load();
        HudRenderCallback.EVENT.register(new ElytraHUD(MinecraftClient.getInstance()));
    }
}
