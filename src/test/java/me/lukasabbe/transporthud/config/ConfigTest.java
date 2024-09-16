package me.lukasabbe.transporthud.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConfigTest {
    @Test
    void testDefaultValues(){
        Assertions.assertTrue(Config.isHudOn);
        Assertions.assertTrue(Config.isElytraDmgStatusOn);
        Assertions.assertTrue(Config.hudCords);
        Assertions.assertEquals(Config.hudDelay, 2);
    }

    @Test
    void testAndLoadSaveMethod(){
        Config.save();
        Config.isHudOn = false;
        Config.load();
        Assertions.assertTrue(Config.isHudOn);
    }
}
