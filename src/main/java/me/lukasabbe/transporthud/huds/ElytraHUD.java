package me.lukasabbe.transporthud.huds;

import com.mojang.blaze3d.systems.RenderSystem;
import me.lukasabbe.transporthud.Config;
import me.lukasabbe.transporthud.TransportHud;
import me.lukasabbe.transporthud.data.ElytraData;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;


public class ElytraHUD implements HudRenderCallback {
    public ElytraData data;
    private final Identifier elytraHudAssets = Identifier.of(TransportHud.MOD_ID, "textures/elytrahud.png");
    private double displaySpeed = 0.0d;


    public ElytraHUD(MinecraftClient client){
        data = new ElytraData(client);
    }

    @Override
    public void onHudRender(DrawContext drawContext, float tickCounter) {
        if(!Config.isHudOn) return;
        if (!data.isFlying) return;
        MinecraftClient client = MinecraftClient.getInstance();
        if(client == null) return;
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();
        displaySpeed = MathHelper.lerp(tickCounter, displaySpeed, data.speed);

        int x = screenWidth / 2;
        int y = screenHeight - 25;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        drawContext.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        //Draw blackplate
        drawContext.drawTexture(elytraHudAssets,x-50,y-60,2,44,100,36);

        type(drawContext,String.format("%d°",(int)data.pitch),x-45, y-55,0xFFFFFF,client);
        type(drawContext,Math.round(displaySpeed*10.0)/10.0 + "km/h",x-45, y-45,0xFFFFFF,client);
        final Vec3d playerPos = client.player.getPos();
        type(drawContext,String.format("%d:%d:%d", (int)playerPos.x, (int)playerPos.y, (int)playerPos.z),x-45, y-35,0xFFFFFF,client);

        drawArrows(drawContext, data.pitch < 0, x+5, y-52);
        //compass
        drawContext.drawTexture(elytraHudAssets,x+14,y-58,44,1,29,31);
        drawCompassArrow(drawContext,x+28,y-43);
        RenderSystem.disableBlend();
    }

    private void drawArrows(DrawContext context, boolean isGoingUp, int x, int upY){
        if(isGoingUp){
            context.drawTexture(elytraHudAssets,x,upY,18,6,6,8);
            context.drawTexture(elytraHudAssets,x,upY+10,28,16,6,8);
        }else{
            context.drawTexture(elytraHudAssets,x,upY,18,16,6,8);
            context.drawTexture(elytraHudAssets,x,upY+10,28,6,6,8);
        }
    }
    private void drawCompassArrow(DrawContext context, int posX, int posY){
        int radius = 5;
        for(int i = 0; i<radius;i++){
            int x = (int) (i*Math.cos(Math.toRadians(data.yaw)) + posX);
            int y = (int) (i*Math.sin(Math.toRadians(data.yaw)) + posY);
            context.drawTexture(elytraHudAssets,x,y,77,12,1,1);
        }
    }
    private void type(DrawContext graphics, String text, int centerX, int y, int color, MinecraftClient client) {
        MatrixStack stack = graphics.getMatrices();
        stack.push();
        stack.translate(centerX,y,0);
        stack.scale(0.6f,0.6f,0.6f);
        stack.translate(-centerX,-y,0);
        graphics.drawTextWithShadow(client.textRenderer,text, centerX, y,color);
        stack.pop();
    }
}
