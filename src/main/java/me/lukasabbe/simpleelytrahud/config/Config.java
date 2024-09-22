package me.lukasabbe.simpleelytrahud.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import me.lukasabbe.simpleelytrahud.Simpleelytrahud;
import net.minecraft.resources.ResourceLocation;

public class Config{
    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(ResourceLocation.fromNamespaceAndPath(Simpleelytrahud.MODID, "my_config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("simple_elytra_hud_config.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public boolean isHudOn = true;

    @SerialEntry
    public boolean isElytraDmgStatusOn = true;

    @SerialEntry
    public int hudDelay = 2;

    @SerialEntry
    public boolean hudCords = true;
}
