package me.lukasabbe.transporthud.huds;

import me.lukasabbe.transporthud.data.ElytraData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class ElytraHUD {
    public ElytraData data;
    private MinecraftClient client;
    public ElytraHUD(MinecraftClient client){
        data = new ElytraData(client);
        this.client = client;
    }
    public void render(DrawContext context, RenderTickCounter tickCounter){

    }

}
