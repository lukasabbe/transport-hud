package me.lukasabbe.transporthud;

import me.lukasabbe.transporthud.data.ElytraData;
import me.lukasabbe.transporthud.huds.ElytraHUD;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransportHud implements ClientModInitializer {
    public static String MOD_ID = "transporthud";
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static ElytraHUD hud;
    @Override
    public void onInitializeClient() {
        hud = new ElytraHUD(MinecraftClient.getInstance());
    }
}
