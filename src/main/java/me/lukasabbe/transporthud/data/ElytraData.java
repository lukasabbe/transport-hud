package me.lukasabbe.transporthud.data;

import me.lukasabbe.transporthud.Config;
import me.lukasabbe.transporthud.TransportHud;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Items;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * All data the HUD is using
 */
public class ElytraData {
    private MinecraftClient client;
    public boolean isFlying;
    private int counter;
    public float speed;
    public Vec3d postion;
    public float pitch;
    public double yaw;
    public float elytraStatus;
    public float maxElytraStatus;
    public int elytraDmgColor;

    public ElytraData(MinecraftClient client){
        if(client == null)
            TransportHud.LOGGER.error("There were no client");
        this.client = client;
        init();
    }
    private void init(){
        ClientTickEvents.END_WORLD_TICK.register(this::updateData);
    }

    private void updateData(ClientWorld world){
        ClientPlayerEntity player = client.player;
        if(!player.isFallFlying() || !player.getInventory().getArmorStack(2).isOf(Items.ELYTRA)){
            counter = 0;
            isFlying=false;
            return;
        }
        //Hud delay is in sec times ticks. 20 ticks in 1 sec
        if(counter > Config.hudDelay * 20){
            isFlying = true;
            speed = (float) (player.getVelocity().length() * 20d);
            postion = player.getPos();
            pitch = player.getPitch();
            float playersYaw = player.getYaw()+90;
            elytraStatus = player.getInventory().getArmorStack(2).getDamage();
            maxElytraStatus = player.getInventory().getArmorStack(2).getMaxDamage();
            elytraDmgColor = player.getInventory().getArmorStack(2).getItemBarColor();
            if(playersYaw > 360){
                int yawRotations = (int) Math.floor(playersYaw /360);
                yaw = playersYaw - (360*yawRotations);
            } else if (playersYaw < 0) {
                int yawRotations = (int) Math.floor(Math.abs( playersYaw) /360) + 1;
                yaw = yawRotations*360+playersYaw;
            }else{
                yaw = playersYaw;
            }

        }else counter++;
    }
}
