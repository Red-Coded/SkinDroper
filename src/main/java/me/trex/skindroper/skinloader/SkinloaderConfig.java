package me.trex.skindroper.skinloader;

import me.trex.skindroper.SkinDroper;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SkinloaderConfig {
    private static File file;
    private static FileConfiguration customFile;

    //Finds or generates the custom config file
    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("SkinDroper").getDataFolder(), "SkinloaderConfig.yml");

        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                //owww
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
        List<String> Header = new ArrayList<>();
        Header.add("File is auto generated. Only edit if you know what ur doing.");
        customFile.options().setHeader(Header);
        customFile.addDefault("skinloader.custom_model_data_tool", 100000);
        customFile.addDefault("skinloader.custom_model_data_armour", 100000);
        SkinloaderConfig.get().options().copyDefaults(true);
        save();
    }

    public static FileConfiguration get(){
        return customFile;
    }

    public static void save(){
       try{
           customFile.save(file);
        }catch (IOException e){
           System.out.println("Couldn't save file");
        }
    }
    public static void saveof(FileConfiguration cf){
        try{
            cf.save(file);
        }catch (IOException e){
            System.out.println("Couldn't save file");
        }
    }

    public static void reload(){
        customFile = YamlConfiguration.loadConfiguration(file);
    }

}