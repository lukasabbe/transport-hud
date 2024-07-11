package me.lukasabbe.transporthud;

import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.util.Properties;

public class Config {
    public static boolean isHudOn = true;
    public static void save(){
        File saveFile = new File(FabricLoader.getInstance().getConfigDir().toFile(),"elytrahud.properties");
        try{
            FileWriter writer = new FileWriter(saveFile);
            writer.write("enabled"+ isHudOn);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void load(){
        File saveFile = new File(FabricLoader.getInstance().getConfigDir().toFile(),"elytrahud.properties");
        if(!saveFile.exists()) return;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(saveFile));
            Properties properties = new Properties();
            properties.load(reader);
            reader.close();
            if(properties.get("enabled") instanceof String val){
                isHudOn = Boolean.parseBoolean(val);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
