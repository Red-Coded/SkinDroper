package me.trex.skindroper.Commands;

import de.tr7zw.changeme.nbtapi.NBT;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getLogger;

public class skindropperCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player){
            ItemStack skin = new ItemStack(Material.KNOWLEDGE_BOOK, 1);

            NBT.modify(skin, nbt -> {
                nbt.setBoolean("skin", true);
            });
            ItemMeta skinMeta = skin.getItemMeta();
            skinMeta.setDisplayName(ChatColor.DARK_PURPLE + "NAMEHERE Skin");

            List<String> skinLore = new ArrayList<>();
            skinLore.add(ChatColor.BLUE + "Applies to:");
            skinLore.add("Sword");
           // skinLore.add(5,"Yes.");
            skinMeta.setLore(skinLore);


            //add the meta
            skin.setItemMeta(skinMeta);
            player.updateInventory();
            player.getInventory().addItem(skin);

        } else {
            getLogger().info(ChatColor.RED +"Command was NOT executed by a player.");
        }


        return true;
    }
}
