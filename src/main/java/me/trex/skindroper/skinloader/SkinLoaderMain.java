package me.trex.skindroper.skinloader;

import com.google.gson.Gson;
import me.trex.skindroper.skinloader.Gui.SkinLoaderMenu;
import me.trex.skindroper.skinloader.jsondefs.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.FileUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SkinLoaderMain {

    private static File file;
    private static File resources;
    private static File assets;
    private static File constructors;
    private static FileConfiguration config;

    //Finds or generates the custom config file
    public static void setup() throws IOException {
        resources = new File(Bukkit.getServer().getPluginManager().getPlugin("SkinDroper").getDataFolder()+ File.separator + "resources");
        resources.mkdir();
        assets = new File(resources+ File.separator + "assets");
        assets.mkdir();
        constructors = new File(resources+ File.separator + "constructors");
        constructors.mkdir();
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("SkinDroper").getDataFolder()+"\\resources", "pack.mcmeta");
        //System.out.println("ATEMPTING TO CREATE FILE");
        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                //owww
            }
        }
        try {
            FileWriter myWriter = new FileWriter(file);
            String Vale = "{$pack$:{$pack_format$:9,$description$:$§5§lSkinDropper Pack$}}";
            Vale = Vale.replace('$','"');
            myWriter.write(Vale);
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        SkinloaderConfig.setup();
        config = SkinloaderConfig.get();
        pack();
    }

    public static void delete(File file) throws IOException {
        if (file.isDirectory()) { // If its a folder, delete its contents
            for (File f : file.listFiles())
                delete(f);
        }
        file.delete();
    }

    public static void copyDirectory(File file, Path path) throws IOException {
        if (file.isDirectory()) { // If its a folder, create a new one and copy contents
            File nf = new File(path+"\\"+file.getName());
            nf.mkdirs();
            for (File f : file.listFiles())
                copyDirectory(f,nf.toPath());
        }else{
            File nf = new File(path.toString()+"\\"+ file.getName());
            Files.copy(file.toPath(), nf.toPath(),StandardCopyOption.REPLACE_EXISTING);
        }

    }

    public static void pack() throws IOException {
        File old = new File(resources + File.separator + "skin_dropper_resources.zip");
        if (old.exists()) {
            delete(old);
        }


        File Temp = new File(resources+"\\temp");
        Temp.deleteOnExit();
        Temp.mkdir();
        File tempassets = new  File(Temp+"\\assets");
        tempassets.mkdir();
        File minecraft = new File(tempassets+"\\minecraft");
        minecraft.mkdir();
        File item = new File(minecraft+"\\models\\item");
        item.mkdirs();

        //collects files to zip
        List<File> filesToZip = new ArrayList<>();
        filesToZip.add(file);
        File Packpng = new File(resources + "\\pack.png");
        if (Packpng.exists()) {filesToZip.add(Packpng);}


        ArrayList<predicateJson> Predicate_pickaxe = new ArrayList<>(),Predicate_sword = new ArrayList<>(),Predicate_axe = new ArrayList<>(),Predicate_shovel = new ArrayList<>(),Predicate_hoe = new ArrayList<>();
        ArrayList<predicateJson> Predicate_HELMET = new ArrayList<>(),Predicate_CHESTPLATE = new ArrayList<>(),Predicate_LEGGINGS = new ArrayList<>(),Predicate_BOOTS = new ArrayList<>();
        String[] type_armour = {"HELMET", "CHESTPLATE", "LEGGINGS", "BOOTS"};
        String[] type_tool = {"sword", "axe", "pickaxe", "shovel", "hoe"};
        String[] level_armour = {"leather","stone","iron","golden","diamond","netherite"};
        String[] level_tool = {"wooden","stone","iron","golden","diamond","netherite"};
        int cmd_tool =  config.getInt("skinloader.custom_model_data_tool");
        int cmd_armour = config.getInt("skinloader.custom_model_data_armour");

        Path dir = constructors.toPath();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                File file = new File(resources +"\\constructors", String.valueOf(path.getFileName()));

                FileConfiguration customFile = YamlConfiguration.loadConfiguration(new FileReader(file));

                for (String i : Objects.requireNonNull(customFile.getConfigurationSection("")).getKeys(false)) {
                    if (config.isSet("preloaded." + path.getFileName() + item + i)) {

                        Gson gson = new Gson();
                        File json = new File(item+"\\"+i+ ".json");
                        json.createNewFile();
                        predicateJson overide = new predicateJson(config.getInt("preloaded." + path.getFileName() + item + i),"minecraft:item/"+i);

                        switch (Objects.requireNonNull(customFile.getString(i + ".type")).toLowerCase()) {
                            case "sword":
                                Predicate_sword.add(overide);
                                break;
                            case "axe":
                                Predicate_axe.add(overide);
                                break;
                            case "pickaxe":
                                Predicate_pickaxe.add(overide);
                                break;
                            case "shovel":
                                Predicate_shovel.add(overide);
                                break;
                            case "hoe":
                                Predicate_hoe.add(overide);
                                break;
                            case "helmet":
                                Predicate_HELMET.add(overide);
                                break;
                            case "chestplate":
                                Predicate_CHESTPLATE.add(overide);
                                break;
                            case "leggings":
                                Predicate_LEGGINGS.add(overide);
                                break;
                            case "boots":
                                Predicate_BOOTS.add(overide);
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + customFile.getString(i+".type"));
                        }

                        TextureJson tj = new TextureJson(customFile.getString(i+".resource.model"), new TextureStructure(customFile.getStringList(i+".resource.textures").getFirst()));
                        Writer write = new FileWriter(json, false);

                        write.write(gson.toJson(tj));
                        write.flush();
                        write.close();
                    }else {
                        Gson gson = new Gson();
                        File json = new File(item+"\\"+i+ ".json");
                        json.createNewFile();
                        predicateJson overide = new predicateJson(cmd_tool,"minecraft:item/"+i);
                        config.set("preloaded." + path.getFileName() + item + i, cmd_tool);
                        cmd_tool += 1;

                        switch (Objects.requireNonNull(customFile.getString(i + ".type")).toLowerCase()) {
                            case "sword":
                                Predicate_sword.add(overide);
                                break;
                            case "axe":
                                Predicate_axe.add(overide);
                                break;
                            case "pickaxe":
                                Predicate_pickaxe.add(overide);
                                break;
                            case "shovel":
                                Predicate_shovel.add(overide);
                                break;
                            case "hoe":
                                Predicate_hoe.add(overide);
                                break;
                            case "helmet":
                                Predicate_HELMET.add(overide);
                                break;
                            case "chestplate":
                                Predicate_CHESTPLATE.add(overide);
                                break;
                            case "leggings":
                                Predicate_LEGGINGS.add(overide);
                                break;
                            case "boots":
                                Predicate_BOOTS.add(overide);
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + customFile.getString(i + ".type"));
                        }

                        TextureJson tj = new TextureJson(customFile.getString(i+".resource.model"), new TextureStructure(customFile.getStringList(i+".resource.textures").getFirst()));
                        Writer write = new FileWriter(json, false);

                        write.write(gson.toJson(tj));
                        write.flush();
                        write.close();
                    }
                    ItemStack Skin = new ItemStack(Material.STRUCTURE_VOID);

                    switch (Objects.requireNonNull(customFile.getString(i + ".type")).toLowerCase()) {
                        case "sword":
                            Skin = new ItemStack(Material.NETHERITE_SWORD);
                            break;
                        case "axe":
                            Skin = new ItemStack(Material.NETHERITE_AXE);
                            break;
                        case "pickaxe":
                            Skin = new ItemStack(Material.NETHERITE_PICKAXE);
                            break;
                        case "shovel":
                            Skin = new ItemStack(Material.NETHERITE_SHOVEL);
                            break;
                        case "hoe":
                            Skin = new ItemStack(Material.NETHERITE_HOE);
                            break;
                        case "helmet":
                            Skin = new ItemStack(Material.NETHERITE_HELMET);
                            break;
                        case "chestplate":
                            Skin = new ItemStack(Material.NETHERITE_CHESTPLATE);
                            break;
                        case "leggings":
                            Skin = new ItemStack(Material.NETHERITE_LEGGINGS);
                            break;
                        case "boots":
                            Skin = new ItemStack(Material.NETHERITE_BOOTS);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + customFile.getString(i + ".type"));
                    }

                    ArrayList<ItemStack> skins = new SkinLoaderMenu().getSkins();
                    skins.add(Skin);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        config.set("skinloader.custom_model_data_tool",cmd_tool);
        SkinloaderConfig.save();

        //write master files
        for (String t : type_tool) {
            for (String l : level_tool) {
                File MasterFile = new File(item+ "\\" + l + "_" + t + ".json");
                Gson gson = new Gson();
                Writer write = new FileWriter(MasterFile, false);
                ArrayList<predicateJson> pj = switch (t) {
                    case "sword" -> Predicate_sword;
                    case "axe" -> Predicate_axe;
                    case "pickaxe" -> Predicate_pickaxe;
                    case "shovel" -> Predicate_shovel;
                    case "hoe" -> Predicate_hoe;
                    default -> null;
                };

                write.write(gson.toJson(new MasterJson("item/handheld","item/"+l+"_"+t,pj)));

                write.flush();
                write.close();
            }
        }


        //copy texture img to temp
        File atlases = new File(minecraft+"\\atlases");
        atlases.mkdirs();
        File blocks = new File(atlases+"\\blocks.json");
        ArrayList<AtlasesSource> atlas = new ArrayList<>();
        if (!blocks.exists()){
            try{ blocks.createNewFile();
            }catch (IOException e){//owww
            }
        }

        dir = assets.toPath();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                Path textures = new File(path.toString()+"\\textures").toPath();
                try (DirectoryStream<Path> streams = Files.newDirectoryStream(textures)) {
                    for (Path paths : streams) {
                        System.out.println(paths.getFileName());
                        atlas.add(new AtlasesSource("directory",paths.getFileName().toString(), paths.getFileName().toString()+"/"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                copyDirectory(path.toFile(), tempassets.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Writer write = new FileWriter(blocks, false);

        write.write(gson.toJson(new Atlases(atlas)));
        write.flush();
        write.close();

        filesToZip.add(tempassets);

        try {
            // Zip files
            String zipFileName = resources + File.separator + "skin_dropper_resources.zip";
            new ZipUtility().zip(filesToZip,zipFileName);
            //System.out.println("Files zipped successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Temp.delete();
        delete(Temp);
    }

    public static void reload(){
        file = new  File(Bukkit.getServer().getPluginManager().getPlugin("SkinDroper").getDataFolder()+"\\resources", "pack.mcmeta");
    }

}
