package me.lukasabbe.simpleelytrahud.data;

import me.lukasabbe.simpleelytrahud.Simpleelytrahud;
import me.lukasabbe.simpleelytrahud.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

/**
 * All data the HUD is using
 */
public class ElytraData {
    private final Minecraft client;
    public boolean isFlying;
    private int counter;
    public float speed;
    public Vec3 postion;
    public float pitch;
    public double yaw;
    public float elytraStatus;
    public float maxElytraStatus;
    public int elytraDmgColor;

    public ElytraData(Minecraft client){
        if(client == null)
            Simpleelytrahud.LOGGER.error("There were no client");
        this.client = client;
    }

    /**
     * Update elytra data
     */
    public void updateData(){
        LocalPlayer player = client.player;
        if(player == null) return;

        final ItemStack armor = player.getInventory().getArmor(2);
        if(!player.isFallFlying() || !armor.is(Items.ELYTRA)){
            counter = 0;
            isFlying=false;
            return;
        }
        //Hud delay is in sec times ticks. 20 ticks in 1 sec
        if(counter > Config.HANDLER.instance().hudDelay * 20){
            isFlying = true;
            speed = (float) (player.getDeltaMovement().length() * 20d);
            postion = player.position();
            pitch = player.getViewXRot(0);
            elytraStatus = armor.getDamageValue();
            maxElytraStatus = armor.getMaxDamage();
            elytraDmgColor = armor.getBarColor();
            yaw = Mth.wrapDegrees(player.getViewYRot(0)) + 90;
        }else counter++;
    }
}
