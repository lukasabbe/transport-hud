package me.lukasabbe.transporthud.data;

import me.lukasabbe.transporthud.TransportHud;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;

public class ElytraData {
    private MinecraftClient client;
    public boolean isFlying;
    private int counter;
    public float speed;
    public Vec3d postion;
    public float pitch;
    public float yaw;

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
        //over 2 seconds in the air
        if(counter > 40){
            isFlying = true;
            speed = (float) (player.getVelocity().length() * 20d);
            postion = player.getPos();
            yaw = player.getYaw();
            pitch = player.getPitch();
        }else counter++;
    }
}
