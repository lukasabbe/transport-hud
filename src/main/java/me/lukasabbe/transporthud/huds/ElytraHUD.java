package me.lukasabbe.transporthud.huds;

import com.mojang.blaze3d.systems.RenderSystem;
import me.lukasabbe.transporthud.config.Config;
import me.lukasabbe.transporthud.TransportHud;
import me.lukasabbe.transporthud.data.ElytraData;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * Elytra rendering HUD
 */
public class ElytraHUD implements HudRenderCallback {
    /**
     * Elytra data that has all necessary data to render HUD
     */
    public ElytraData data;
    private final Identifier elytraHudAssets = Identifier.of(TransportHud.MOD_ID, "textures/elytrahud.png");
    private double displaySpeed = 0.0d;
    private final MinecraftClient client;

    public ElytraHUD(MinecraftClient client){
        data = new ElytraData(client);
        this.client = client;
    }

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        if(!Config.isHudOn) return;
        if(!data.isFlying) return;
        if(client.options.hudHidden) return;

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();
        displaySpeed = MathHelper.lerp(tickCounter.getTickDelta(true), displaySpeed, data.speed);

        //Creates pos for HUD
        int x = screenWidth / 2;
        int y = screenHeight - 25;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        drawContext.setShaderColor(1.0f,1.0f,1.0f,1.0f);

        //Draw blackplate
        drawContext.drawTexture(elytraHudAssets,x-50,y-60,2,44,100,36);

        //draws speed and pitch
        type(drawContext,String.format("%d°",(int)data.pitch),x-45, y-55,0xFFFFFF,client,0.6f);
        type(drawContext,Math.round(displaySpeed*10.0)/10.0 + "km/h",x-45, y-45,0xFFFFFF,client,0.6f);

        //draw cords
        final Vec3d playerPos = client.player.getPos();
        if(Config.hudCords)
            drawCords(drawContext,playerPos,x-45, y-35,client);

        //draws arrows
        drawArrows(drawContext, data.pitch < 0, x+5, y-52);

        //compass
        drawContext.drawTexture(elytraHudAssets,x+14,y-58,44,1,29,31);
        drawCompassArrow(drawContext,x+28,y-43);

        //DMG level
        if(Config.isElytraDmgStatusOn)
            drawElytraStatus(drawContext, x-2, y-31,17);

        RenderSystem.disableBlend();
    }

    private void drawCords(DrawContext ctx, Vec3d pos, int x, int y, MinecraftClient client){
        String cordsText = String.format("%d:%d:%d", (int)pos.x, (int)pos.y, (int)pos.z);
        if(cordsText.length() > 10){
            type(ctx,cordsText,x,y,0xFFFFFF, client, ((float) (10 * 7 - ((cordsText.length()-4)*2)) / 100));
        }else{
            type(ctx,cordsText,x,y,0xFFFFFF, client, 0.6f);
        }
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
        final int radius = 5;
        final float cos = MathHelper.cos((float) Math.toRadians(data.yaw));
        final float sin = MathHelper.sin((float) Math.toRadians(data.yaw));
        int x = Math.round(radius * cos + posX);
        int y = Math.round(radius * sin + posY);
        drawLine(context,posX, posY, x, y, radius);
    }

    private void drawLine(DrawContext context, int posX, int posY, int pos2x, int pos2y, int points){
        for(int i = 0 ; i<points; i++){
            int x = MathHelper.lerp(
                    MathHelper.map(i,0,points,0,1),
                    posX,pos2x);
            int y = MathHelper.lerp(
                    MathHelper.map(i,0,points,0,1),
                    posY,pos2y);
            context.drawTexture(elytraHudAssets,x,y,77,12,1,1);
        }
    }

    private void drawElytraStatus(DrawContext context, int posX, int posY, int size){
        drawScaledItem(context,posX-3,posY-size-7,Items.ELYTRA,0.5f);
        float dmgPercentage = (1 - (data.elytraStatus / data.maxElytraStatus));
        final int statusBar = posY - (int)(dmgPercentage * size);
        context.fill(posX, posY, posX+2, statusBar, ColorHelper.Abgr.withAlpha(0xFF,data.elytraDmgColor));
        float dmgPercentageLeft = 1 - dmgPercentage;
        if(dmgPercentageLeft == 0) return;
        context.fill(posX, statusBar, posX+2,statusBar - (int)(dmgPercentageLeft*size), 0xFF3D3D3D);
    }
    private void drawScaledItem(DrawContext context, int poxX, int posY, Item item, float scaled){
        MatrixStack stack = context.getMatrices();
        stack.push();
        stack.translate(poxX,posY,0);
        stack.scale(scaled,scaled,scaled);
        stack.translate(-poxX,-posY,0);
        context.drawItem(item.getDefaultStack(),poxX, posY);
        stack.pop();
    }
    private void type(DrawContext graphics, String text, int centerX, int y, int color, MinecraftClient client, float scaled) {
        MatrixStack stack = graphics.getMatrices();
        stack.push();
        stack.translate(centerX,y,0);
        stack.scale(scaled,scaled,scaled);
        stack.translate(-centerX,-y,0);
        graphics.drawTextWithShadow(client.textRenderer,text, centerX, y,color);
        stack.pop();
    }
}
