package me.lukasabbe.transporthud.huds;

import com.mojang.blaze3d.systems.RenderSystem;
import me.lukasabbe.transporthud.TransportHud;
import me.lukasabbe.transporthud.data.ElytraData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ElytraHUD {
    public ElytraData data;
    private MinecraftClient client;
    private Identifier elytraHudAssets = Identifier.of("transporthud", "texture/elytrahud.png");
    private double display_speed = 0.0d;
    public ElytraHUD(MinecraftClient client){
        data = new ElytraData(client);
        this.client = client;
    }

    public void elytraHudRenderer(DrawContext context, RenderTickCounter tickCounter){
        TransportHud.LOGGER.info("yes");
        this.display_speed = MathHelper.lerp(tickCounter.getTickDelta(true),display_speed,data.speed);
        context.fill(0,0,10000,10000,0xFFFFF);

        int screen_width = client.getWindow().getWidth();
        int screen_height = client.getWindow().getHeight();
        int hud_length = 100;
        renderText(context,"TEST",screen_width,screen_height, 0xFFFFF);
        this.renderText(context, "Test", (screen_width/2) - 58, screen_height - 76, 0xFFFFFF);
        context.drawTexture(
                elytraHudAssets,
                (screen_width/2) - (hud_length/2),
                (screen_height + 90),
                2, 44, 100, 36);

    }
    private void renderText(DrawContext graphics, String text, int centerX, int y, int color) {
        graphics.drawTextWithShadow(this.client.textRenderer, text, centerX - this.client.textRenderer.getWidth(text) / 2, y, color);
    }

}
