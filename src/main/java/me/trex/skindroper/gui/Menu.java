package me.trex.skindroper.gui;

import com.google.errorprone.annotations.Var;
import de.tr7zw.changeme.nbtapi.NBT;
import me.trex.skindroper.CustomConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class Menu {
    public void OpenMenu(Player player) {
        //Create the menu
        Inventory menu = Bukkit.createInventory(player, 9, net.md_5.bungee.api.ChatColor.DARK_PURPLE + "SkinDropper Menu");

        //The options
        ItemStack config = new ItemStack(Material.WRITABLE_BOOK);
        ItemStack skins = new ItemStack(Material.KNOWLEDGE_BOOK);
        ItemStack close = new ItemStack(Material.BARRIER);

        //Edit the items
        ItemMeta configMeta = config.getItemMeta();
        configMeta.setDisplayName(ChatColor.GREEN + "⚙ Config ⚙");
        configMeta.setLore(List.of(ChatColor.GOLD + "Edit Config"));
        config.setItemMeta(configMeta);

        ItemMeta skinsMeta = skins.getItemMeta();
        skinsMeta.setDisplayName(ChatColor.DARK_PURPLE + "🖌 Skins 🖌");
        skinsMeta.setLore(List.of(ChatColor.GOLD + "Add and edit skins"));
        skins.setItemMeta(skinsMeta);

        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName(ChatColor.DARK_RED + "× Close ×");
        closeMeta.setLore(List.of(ChatColor.GOLD + "Exits the menu"));
        close.setItemMeta(closeMeta);

        //Put the items in the menu
        menu.setItem(0, config);
        menu.setItem(4, skins);
        menu.setItem(8, close);

        //Open the menu for the player
        player.openInventory(menu);
    }

    public void OpenConfigMenu(Player player) {
        //Create the menu
        Inventory menu = Bukkit.createInventory(player, 54, net.md_5.bungee.api.ChatColor.DARK_PURPLE + "SkinDropper Config");

        //The options
        ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack feed = new ItemStack(Material.BREAD);
        ItemStack death = new ItemStack(Material.TNT);
        ItemStack exit = new ItemStack(Material.BARRIER);

        //Edit the items
        ItemMeta placeholdermeta = feed.getItemMeta();
        placeholdermeta.setDisplayName(ChatColor.BLACK + "");
        placeholder.setItemMeta(placeholdermeta);

        ItemMeta feedMeta = feed.getItemMeta();
        feedMeta.setDisplayName(ChatColor.GREEN + "Feed");
        feedMeta.setLore(List.of(ChatColor.GOLD + "Hunger no more."));
        feed.setItemMeta(feedMeta);

        ItemMeta deathMeta = death.getItemMeta();
        deathMeta.setDisplayName(ChatColor.RED + "Kill");
        deathMeta.setLore(List.of(ChatColor.GOLD + "Death is inevitable."));
        death.setItemMeta(deathMeta);

        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.setDisplayName(ChatColor.DARK_RED + "× Close ×");
        exit.setItemMeta(exitMeta);

        //Put the items in the menu
        for (int i = 0; i < 9; i++) {
            menu.setItem(i, placeholder);
        }
        for (int i = 1; i < 5; i ++) {
            menu.setItem(9*i, placeholder);
            menu.setItem(9*i+8, placeholder);
        }
        for (int i = 45; i <= 53; i++) {
            menu.setItem(i, placeholder);
        }

        menu.setItem(46, feed);
        menu.setItem(49, death);
        menu.setItem(52, exit);

        //Open the menu for the player
        player.openInventory(menu);
    }

    public void OpenSkinsMenu(Player player) {
        OpenSkinsMenu(player,1);
    }
    public void OpenSkinsMenu(Player player, Integer pagenumber) {
       FileConfiguration pl = Objects.requireNonNull(getServer().getPluginManager().getPlugin("skindroper")).getConfig();

        //Create the menu
        Inventory menu = Bukkit.createInventory(player, 54, net.md_5.bungee.api.ChatColor.DARK_PURPLE + "SkinDropper Skins");

        if (pagenumber > Math.ceil((Objects.requireNonNull(CustomConfig.get().getConfigurationSection("skins")).getKeys(false).toArray().length/28.00F)) || pagenumber <= 0) {
            return;
        }

        //The options
        ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack add = new ItemStack(Material.WRITABLE_BOOK);
        ItemStack page = new ItemStack(Material.BOOK);
        ItemStack exit = new ItemStack(Material.BARRIER);

        //Edit the items
        ItemMeta placeholdermeta = placeholder.getItemMeta();
        placeholdermeta.setDisplayName(ChatColor.BLACK + "");
        placeholder.setItemMeta(placeholdermeta);

        ItemMeta addMeta = add.getItemMeta();
        addMeta.setDisplayName(ChatColor.GREEN + "+ Add +");
        addMeta.setLore(List.of(ChatColor.GOLD + "Opens NEW skin menu"));
        add.setItemMeta(addMeta);

        ItemMeta pageMeta = page.getItemMeta();
        pageMeta.setDisplayName(ChatColor.RED + "Page " + pagenumber.toString());

        pageMeta.setLore(List.of(ChatColor.GOLD + "Left Click to go next", ChatColor.GOLD + "Right CLick to go back"));
        page.setItemMeta(pageMeta);
        NBT.modify(page, NBT -> {
            NBT.setInteger("page", pagenumber);
        });


        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.setDisplayName(ChatColor.DARK_RED + "× Close ×");
        exit.setItemMeta(exitMeta);

        //Put the items in the menu
        for (int i = 0; i < 9; i++) {
            menu.setItem(i, placeholder);
        }
        for (int i = 1; i < 5; i ++) {
            menu.setItem(9*i, placeholder);
            menu.setItem(9*i+8, placeholder);
        }
        for (int i = 45; i <= 53; i++) {
            menu.setItem(i, placeholder);
        }

        List<String> b = Objects.requireNonNull(CustomConfig.get().getConfigurationSection("skins")).getKeys(false).stream().toList();

        for (int i = 28*(pagenumber-1); i < b.size(); i++) {
           ItemStack skin = new ItemStack(Material.KNOWLEDGE_BOOK);
           ItemMeta skinmeta = skin.getItemMeta();
            assert skinmeta != null;
            skinmeta.setDisplayName(ChatColor.DARK_PURPLE + b.get(i));
           skin.setItemMeta(skinmeta) ;
            int finalI = i;
            NBT.modify(skin, NBT -> {
                NBT.setString("skinid",b.get(finalI));
                NBT.setBoolean("skin",false);
            });
           menu.addItem(skin);
        }

        menu.setItem(46, add);
        menu.setItem(49, page);
        menu.setItem(52, exit);

        //Open the menu for the player
        player.openInventory(menu);
    }

    public void OpenSkinMenu(Player player, String skinid) {
        FileConfiguration pl = Objects.requireNonNull(getServer().getPluginManager().getPlugin("skindroper")).getConfig();

        //Create the menu
        Inventory menu = Bukkit.createInventory(player, 54, net.md_5.bungee.api.ChatColor.DARK_PURPLE + "SkinDropper Skin");

        //The options
        ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack remove = new ItemStack(Material.LAVA_BUCKET);
        ItemStack grab = new ItemStack(Material.KNOWLEDGE_BOOK);
        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemStack save = new ItemStack(Material.LIME_DYE);

        //Edit the items
        ItemMeta placeholdermeta = placeholder.getItemMeta();
        placeholdermeta.setHideTooltip(true);
        placeholdermeta.setDisplayName(ChatColor.BLACK + "");
        placeholder.setItemMeta(placeholdermeta);

        ItemMeta removeMeta = remove.getItemMeta();
        removeMeta.setDisplayName(ChatColor.DARK_RED + "§l§n\uD83D\uDDD1 Delete \uD83D\uDDD1");
        removeMeta.setLore(List.of(ChatColor.DARK_RED + "§l§nDELETES THE SKIN THIS CANT BE UNDONE!! NO CONFORMATION!!"));
        remove.setItemMeta(removeMeta);

        NBT.modify(remove, NBT -> {
            NBT.setString("skinid",skinid);
            NBT.setBoolean("skin",false);
        });

        ItemMeta grabMeta = grab.getItemMeta();
        grabMeta.setDisplayName(ChatColor.RED + "Grab the skin");
        grabMeta.setLore(List.of(ChatColor.GOLD + "Gives you the skin"));
        grab.setItemMeta(grabMeta);

        NBT.modify(grab, NBT -> {
            NBT.setString("skinid",skinid);
            NBT.setBoolean("skin",false);
        });

        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.setDisplayName(ChatColor.DARK_RED + "× Close ×");
        exit.setItemMeta(exitMeta);

        ItemMeta saveMeta = save.getItemMeta();
        saveMeta.setDisplayName(ChatColor.GREEN + "\uD83D\uDCBE Save \uD83D\uDCBE");
        save.setItemMeta(saveMeta);

        NBT.modify(save, NBT -> {
            NBT.setString("skinid",skinid);
            NBT.setBoolean("skin",false);
        });

        //Put the items in the menu
        for (int i = 0; i < 9; i++) {
            menu.setItem(i, placeholder);
        }
        for (int i = 1; i < 5; i ++) {
            menu.setItem(9*i, placeholder);
            menu.setItem(9*i+8, placeholder);
        }
        for (int i = 45; i <= 53; i++) {
            menu.setItem(i, placeholder);
        }
        menu.setItem(28,placeholder);
        menu.setItem(37,placeholder);
        menu.setItem(34,placeholder);
        menu.setItem(43,placeholder);

        //if (CustomConfig.get().isSet("skins."+skinid+".tools")) {
        //            for (String b : CustomConfig.get().getConfigurationSection("skins."+skinid+".tools").getKeys(false)) {
        //                ItemStack skin = switch (b) {
        //                    case "sword" -> new ItemStack(Material.DIAMOND_SWORD);
        //                    case "axe" -> new ItemStack(Material.DIAMOND_AXE);
        //                    case "pickaxe" -> new ItemStack(Material.DIAMOND_PICKAXE);
        //                    case "shovel" -> new ItemStack(Material.DIAMOND_SHOVEL);
        //                    case "hoe" -> new ItemStack(Material.DIAMOND_HOE);
        //                    case "mace" -> new ItemStack(Material.MACE);
        //                    case "fishingrod" -> new ItemStack(Material.FISHING_ROD);
        //                    case "shield" -> new ItemStack(Material.SHIELD);
        //                    default -> new ItemStack(Material.STRUCTURE_VOID);
        //                };
        //
        //                ItemMeta skinmeta = skin.getItemMeta();
        //                skinmeta.setDisplayName(ChatColor.DARK_PURPLE + b);
        //                ArrayList<String> skinlore = new ArrayList<String>();
        //                skinmeta.setHideTooltip(false);
        //                skinlore.add("ID:"+CustomConfig.get().getInt("skins."+skinid+".tools."+b));
        //                skinmeta.setLore(skinlore);
        //                skinmeta.setCustomModelData(CustomConfig.get().getInt("skins."+skinid+".tools."+b));
        //                skin.setItemMeta(skinmeta) ;
        //
        //                NBT.modify(skin, NBT -> {
        //                    NBT.setString("skinid",skinid);
        //                    NBT.setBoolean("skin",false);
        //                });
        //                menu.addItem(skin);
        //            };
        //        }

        //Set placeholders
        menu.setItem(10,new ItemStack(Material.DIAMOND_SWORD));
        menu.setItem(11,new ItemStack(Material.DIAMOND_AXE));
        menu.setItem(12,new ItemStack(Material.DIAMOND_PICKAXE));
        menu.setItem(13,new ItemStack(Material.DIAMOND_SHOVEL));
        menu.setItem(14,new ItemStack(Material.DIAMOND_HOE));
        menu.setItem(15,new ItemStack(Material.FISHING_ROD));
        menu.setItem(16,new ItemStack(Material.SHIELD));
        menu.setItem(29,new ItemStack(Material.MACE));
        menu.setItem(30,new ItemStack(Material.DIAMOND_HELMET));
        menu.setItem(31,new ItemStack(Material.DIAMOND_CHESTPLATE));
        menu.setItem(32,new ItemStack(Material.DIAMOND_LEGGINGS));
        menu.setItem(33,new ItemStack(Material.DIAMOND_BOOTS));
        //seting real skins
        menu.setItem(19,getskin(skinid,"sword"));
        menu.setItem(20,getskin(skinid,"axe"));
        menu.setItem(21,getskin(skinid,"pickaxe"));
        menu.setItem(22,getskin(skinid,"shovel"));
        menu.setItem(23,getskin(skinid,"hoe"));
        menu.setItem(24,getskin(skinid,"fishingrod"));
        menu.setItem(25,getskin(skinid,"shield"));
        menu.setItem(38,getskin(skinid,"mace"));
        menu.setItem(39,getskin(skinid,"helmet"));
        menu.setItem(40,getskin(skinid,"chestplate"));
        menu.setItem(41,getskin(skinid,"leggings"));
        menu.setItem(42,getskin(skinid,"boots"));
        //Buttons
        menu.setItem(46, remove);
        menu.setItem(49, grab);
        menu.setItem(52, save);
        menu.setItem(53, exit);

        //Open the menu for the player
        player.openInventory(menu);
    }

    private ItemStack getskin(String skinID,String type) {
        if (Objects.equals(type, "helmet") || Objects.equals(type, "chestplate") || Objects.equals(type, "leggings") || Objects.equals(type, "boots")) {
            ItemStack skin = switch (type) {
                case "helmet" -> new ItemStack(Material.LEATHER_HELMET);
                case "chestplate" -> new ItemStack(Material.LEATHER_CHESTPLATE);
                case "leggings" -> new ItemStack(Material.LEATHER_LEGGINGS);
                case "boots" -> new ItemStack(Material.LEATHER_BOOTS);
                default -> new ItemStack(Material.STRUCTURE_VOID);
            };
            if (CustomConfig.get().getInt("skins."+skinID+".armour."+type+".id") == 0 || !CustomConfig.get().isSet("skins." + skinID + ".armour." + type + ".id")) {
                return new ItemStack(Material.AIR);
            }else {
                ItemMeta skinmeta = skin.getItemMeta();
                LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) skinmeta;
                leatherArmorMeta.setDisplayName(ChatColor.DARK_PURPLE + type);
                ArrayList<String> skinlore = new ArrayList<String>();
                leatherArmorMeta.setHideTooltip(false);
                skinlore.add("ID:"+CustomConfig.get().getInt("skins."+skinID+".armour."+type+".id"));
                leatherArmorMeta.setLore(skinlore);
                leatherArmorMeta.setCustomModelData(CustomConfig.get().getInt("skins."+skinID+".armour."+type+".id"));
                leatherArmorMeta.setColor(CustomConfig.get().getColor("skins."+skinID+".armour."+type+".RGB"));
                skin.setItemMeta(leatherArmorMeta);

                NBT.modify(skin, NBT -> {
                    NBT.setString("skinid",skinID);
                    NBT.setBoolean("skin",false);
                });

                return skin;
            }
        } else {
            ItemStack skin = switch (type) {
                case "sword" -> new ItemStack(Material.DIAMOND_SWORD);
                case "axe" -> new ItemStack(Material.DIAMOND_AXE);
                case "pickaxe" -> new ItemStack(Material.DIAMOND_PICKAXE);
                case "shovel" -> new ItemStack(Material.DIAMOND_SHOVEL);
                case "hoe" -> new ItemStack(Material.DIAMOND_HOE);
                case "mace" -> new ItemStack(Material.MACE);
                case "fishingrod" -> new ItemStack(Material.FISHING_ROD);
                case "shield" -> new ItemStack(Material.SHIELD);
                default -> new ItemStack(Material.STRUCTURE_VOID);
            };
            if (CustomConfig.get().getInt("skins."+skinID+".tools."+type) == 0 || !CustomConfig.get().isSet("skins." + skinID + ".tools." + type)) {
                return new ItemStack(Material.AIR);
            }else {
                ItemMeta skinmeta = skin.getItemMeta();
                skinmeta.setDisplayName(ChatColor.DARK_PURPLE + type);
                ArrayList<String> skinlore = new ArrayList<String>();
                skinmeta.setHideTooltip(false);
                skinlore.add("ID:"+CustomConfig.get().getInt("skins."+skinID+".tools."+type));
                skinmeta.setLore(skinlore);
                skinmeta.setCustomModelData(CustomConfig.get().getInt("skins."+skinID+".tools."+type));
                skin.setItemMeta(skinmeta) ;

                NBT.modify(skin, NBT -> {
                    NBT.setString("skinid",skinID);
                    NBT.setBoolean("skin",false);
                });

                return skin;
            }
        }
    }

    public void saveskin(Inventory menu,String skinID) {
        FileConfiguration conf = CustomConfig.get();
        conf.set("skins."+skinID+".tools."+"sword", getModelData(19,menu));
        conf.set("skins."+skinID+".tools."+"axe", getModelData(20,menu));
        conf.set("skins."+skinID+".tools."+"pickaxe", getModelData(21,menu));
        conf.set("skins."+skinID+".tools."+"shovel", getModelData(22,menu));
        conf.set("skins."+skinID+".tools."+"hoe", getModelData(23,menu));
        conf.set("skins."+skinID+".tools."+"fishingrod", getModelData(24,menu));
        conf.set("skins."+skinID+".tools."+"shield", getModelData(25,menu));

        conf.set("skins."+skinID+".tools."+"mace", getModelData(38,menu));
        conf.set("skins."+skinID+".armour."+"helmet.id", getModelData(39,menu));
        conf.set("skins."+skinID+".armour."+"helmet.RGB", getRGB(39,menu));
        conf.set("skins."+skinID+".armour."+"chestplate.id", getModelData(40,menu));
        conf.set("skins."+skinID+".armour."+"chestplate.RGB", getRGB(40,menu));
        conf.set("skins."+skinID+".armour."+"leggings.id", getModelData(41,menu));
        conf.set("skins."+skinID+".armour."+"leggings.RGB", getRGB(41,menu));
        conf.set("skins."+skinID+".armour."+"boots.id", getModelData(42,menu));
        conf.set("skins."+skinID+".armour."+"boots.RGB", getRGB(42,menu));
        CustomConfig.save();
        CustomConfig.reload();
    }

    private Object getModelData(int slot, Inventory Menu) {
        ItemStack IS = Menu.getItem(slot);
        ItemMeta IM = IS.getItemMeta();
        if (IS != null && IS.getType() != Material.AIR && IM.hasCustomModelData() && IM.getCustomModelData() >= 1) {
            return IM.getCustomModelData();
        } else {
            return null;
        }
    }

    private Color getRGB(int slot, Inventory Menu) {
        Material[] armour = {Material.LEATHER_BOOTS,Material.LEATHER_LEGGINGS,Material.LEATHER_CHESTPLATE,Material.LEATHER_HELMET};

        if (Menu.getItem(slot) != null && Menu.getItem(slot).getType() != Material.AIR && Arrays.stream(armour).anyMatch(tt -> Menu.getItem(slot).getType().equals(tt))) {
            //getLogger().info(String.valueOf(((LeatherArmorMeta)Menu.getItem(slot).getItemMeta()).getColor()));
            return ((LeatherArmorMeta)Menu.getItem(slot).getItemMeta()).getColor();
        } else {
            //getLogger().info("got null from color gen");
            return null;
        }
    }

    private void cleanitemstack(String type) {

    }
}
