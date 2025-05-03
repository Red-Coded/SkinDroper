package me.trex.skindroper;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomConfig {
    private  final SkinDroper plugin;

    public CustomConfig(SkinDroper plugin) {
        this.plugin = plugin;
    }

    private static File file;
    private static FileConfiguration customFile;

    //Finds or generates the custom config file
    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("SkinDroper").getDataFolder(), "skins.yml");

        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                //owww
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
        List<String> Header = new ArrayList<>();
        Header.add("File is auto generated. Use /skindropper to set up");
        customFile.options().setHeader(Header);
        CustomConfig.get().options().copyDefaults(true);
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