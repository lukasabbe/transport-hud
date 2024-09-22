package me.lukasabbe.simpleelytrahud.huds;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.lukasabbe.simpleelytrahud.Simpleelytrahud;
import me.lukasabbe.simpleelytrahud.config.Config;
import me.lukasabbe.simpleelytrahud.data.ElytraData;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

/**
 * Elytra rendering HUD
 */
public class ElytraHUD {
    /**
     * Elytra data that has all necessary data to render HUD
     */
    public ElytraData data;
    private final ResourceLocation elytraHudAssets = ResourceLocation.fromNamespaceAndPath(Simpleelytrahud.MODID, "textures/elytrahud.png");
    private double displaySpeed = 0.0d;
    private final Minecraft client;

    public ElytraHUD(Minecraft client){
        data = new ElytraData(client);
        this.client = client;
    }

    public void hudRender(GuiGraphics drawContext, DeltaTracker tickCounter) {
        final Config config = Config.HANDLER.instance();
        if(!config.isHudOn) return;
        if(!data.isFlying) return;
        if(client.options.hideGui) return;

        int screenWidth = client.getWindow().getGuiScaledWidth();
        int screenHeight = client.getWindow().getGuiScaledWidth();
        displaySpeed = Mth.lerp(tickCounter.getGameTimeDeltaPartialTick(true), displaySpeed, data.speed);

        //Creates pos for HUD
        int x = screenWidth / 2;
        int y = screenHeight - 25;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        drawContext.setColor(1.0f,1.0f,1.0f,1.0f);

        //Draw blackplate
        drawContext.blit(elytraHudAssets,x-50,y-60,2,44,100,36);

        //draws speed and pitch
        type(drawContext,String.format("%d°",(int)data.pitch),x-45, y-55,0xFFFFFF,client,0.6f);
        type(drawContext,Math.round(displaySpeed*10.0)/10.0 + "km/h",x-45, y-45,0xFFFFFF,client,0.6f);

        //draw cords
        final Vec3 playerPos = client.player.position();
        if(config.hudCords)
            drawCords(drawContext,playerPos,x-45, y-35,client);

        //draws arrows
        drawArrows(drawContext, data.pitch < 0, x+5, y-52);

        //compass
        drawContext.blit(elytraHudAssets,x+14,y-58,44,1,29,31);
        drawCompassArrow(drawContext,x+28,y-43);

        //DMG level
        if(config.isElytraDmgStatusOn)
            drawElytraStatus(drawContext, x-2, y-31,17);

        RenderSystem.disableBlend();
    }

    private void drawCords(GuiGraphics ctx, Vec3 pos, int x, int y, Minecraft client){
        String cordsText = String.format("%d:%d:%d", (int)pos.x, (int)pos.y, (int)pos.z);
        if(cordsText.length() > 10){
            type(ctx,cordsText,x,y,0xFFFFFF, client, ((float) (10 * 7 - ((cordsText.length()-4)*2)) / 100));
        }else{
            type(ctx,cordsText,x,y,0xFFFFFF, client, 0.6f);
        }
    }


    private void drawArrows(GuiGraphics context, boolean isGoingUp, int x, int upY){
        if(isGoingUp){
            context.blit(elytraHudAssets,x,upY,18,6,6,8);
            context.blit(elytraHudAssets,x,upY+10,28,16,6,8);
        }else{
            context.blit(elytraHudAssets,x,upY,18,16,6,8);
            context.blit(elytraHudAssets,x,upY+10,28,6,6,8);
        }
    }
    private void drawCompassArrow(GuiGraphics context, int posX, int posY){
        final int radius = 5;
        final float cos = Mth.cos((float) Math.toRadians(data.yaw));
        final float sin = Mth.sin((float) Math.toRadians(data.yaw));
        int x = Math.round(radius * cos + posX);
        int y = Math.round(radius * sin + posY);
        drawLine(context,posX, posY, x, y, radius);
    }

    private void drawLine(GuiGraphics context, int posX, int posY, int pos2x, int pos2y, int points){
        for(int i = 0 ; i<points; i++){
            int x = (int) Mth.lerp(
                    Mth.map(i,0,points,0,1),
                    posX,pos2x);
            int y = (int) Mth.lerp(
                    Mth.map(i,0,points,0,1),
                    posY,pos2y);
            context.blit(elytraHudAssets,x,y,77,12,1,1);
        }
    }

    private void drawElytraStatus(GuiGraphics context, int posX, int posY, int size){
        drawScaledItem(context,posX-3,posY-size-7, Items.ELYTRA,0.5f);
        float dmgPercentage = (1 - (data.elytraStatus / data.maxElytraStatus));
        final int statusBar = posY - (int)(dmgPercentage * size);
        context.fill(posX, posY, posX+2, statusBar, FastColor.ABGR32.color(0xFF,data.elytraDmgColor));
        float dmgPercentageLeft = 1 - dmgPercentage;
        if(dmgPercentageLeft == 0) return;
        context.fill(posX, statusBar, posX+2,statusBar - (int)(dmgPercentageLeft*size), 0xFF3D3D3D);
    }
    private void drawScaledItem(GuiGraphics context, int poxX, int posY, Item item, float scaled){
        PoseStack stack = context.pose();
        stack.pushPose();
        stack.translate(poxX,posY,0);
        stack.scale(scaled,scaled,scaled);
        stack.translate(-poxX,-posY,0);
        context.renderItem(item.getDefaultInstance(),poxX, posY);
        stack.popPose();
    }
    private void type(GuiGraphics graphics, String text, int centerX, int y, int color, Minecraft client, float scaled) {
        PoseStack stack = graphics.pose();
        stack.pushPose();
        stack.translate(centerX,y,0);
        stack.scale(scaled,scaled,scaled);
        stack.translate(-centerX,-y,0);
        graphics.drawString(client.font,text, centerX, y,color);
        stack.popPose();
    }
}
