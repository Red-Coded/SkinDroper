package me.trex.skindroper.skinloader.Gui;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableItemNBT;
import me.trex.skindroper.CustomConfig;
import me.trex.skindroper.functions;
import me.trex.skindroper.gui.Menu;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;
import java.util.function.Function;

import static me.trex.skindroper.SkinDroper.prefix;

public class SkinLoaderMenuListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){

        //See if the inventory that was clicked was the custom menu
        // by checking the name of the inventory
        if (event.getView().getTitle().equals(ChatColor.DARK_PURPLE + "SkinLoader Menu")){

            //IMPORTANT! Cancel the event so that players cannot move items
            event.setCancelled(true);

            //We only care about left clicks for this
            //if (!event.getClick().isLeftClick()) return;

            Player player = (Player) event.getWhoClicked();

            //Determine which "buttons" were clicked and do something
            switch (event.getCurrentItem().getType()){
                case WRITABLE_BOOK:
                    player.sendMessage(prefix + " Opening Settings...");
                    break;
                case BLACK_STAINED_GLASS_PANE:
                    break;
                case BARRIER:
                    player.closeInventory();
                    player.sendMessage(prefix + " Closed menu.");
                    break;
                default:
                    if (NBT.get(event.getCurrentItem(), nbt -> (Boolean) nbt.getBoolean("skinloader"))) {
                        ItemStack clicked = event.getCurrentItem().clone();
                        ItemStack Item = new ItemStack(clicked.getType());
                        ItemMeta Itemmet = Item.getItemMeta();
                        Itemmet.setItemName(clicked.getItemMeta().getItemName());
                        Item.setItemMeta(Itemmet);

                        player.getInventory().addItem();
                    }
                    break;
            }
        }
    }
}
