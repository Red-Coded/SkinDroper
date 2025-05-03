package me.trex.skindroper.skinloader.Gui;

import de.tr7zw.changeme.nbtapi.NBT;
import me.trex.skindroper.CustomConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SkinLoaderMenu {

    private ArrayList<ItemStack> Skins = new ArrayList<>();

    public void OpenMenu(Player player) {
        //Create the menu
        Inventory menu = Bukkit.createInventory(player, 54, net.md_5.bungee.api.ChatColor.DARK_PURPLE + "SkinLoader Menu");

        //The options
        ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack page = new ItemStack(Material.BOOK);
        ItemStack exit = new ItemStack(Material.BARRIER);

        //Edit the items
        ItemMeta placeholdermeta = placeholder.getItemMeta();
        placeholdermeta.setDisplayName(ChatColor.BLACK + "");
        placeholder.setItemMeta(placeholdermeta);

        ItemMeta pageMeta = page.getItemMeta();
        pageMeta.setDisplayName(ChatColor.RED + "Page 1");

        pageMeta.setLore(List.of(ChatColor.GOLD + "Left Click to go next", ChatColor.GOLD + "Right CLick to go back"));
        page.setItemMeta(pageMeta);

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

        for (ItemStack b : Skins) {
            ItemStack skin = b.clone();
            NBT.modify(skin, NBT -> {
                NBT.setBoolean("skinloader",true);
            });
            menu.addItem(skin);
        };

        menu.setItem(49, page);
        menu.setItem(52, exit);

        //Open the menu for the player
        player.openInventory(menu);
    }

    public void setSkins(ArrayList<ItemStack> skins) {
        Skins = skins;
    }

    public ArrayList<ItemStack> getSkins() {
        return Skins;
    }
}
