package me.lukasabbe.transporthud.data;

import net.minecraft.client.MinecraftClient;
import org.junit.jupiter.api.Test;

public class TestElytraData {

    @Test
    void startUpNoCrash(){
        ElytraData data = new ElytraData(MinecraftClient.getInstance());
        data.updateData();
    }

}
