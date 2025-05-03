package me.trex.skindroper.Listeners;

import de.tr7zw.changeme.nbtapi.NBT;
import me.trex.skindroper.CustomConfig;
import me.trex.skindroper.functions;
import me.trex.skindroper.gui.Menu;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

import static me.trex.skindroper.SkinDroper.prefix;

public class MenuListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if (event.getCurrentItem() == null) {
            return;
        }

        //See if the inventory that was clicked was the custom menu
        // by checking the name of the inventory
        if (event.getView().getTitle().equals(ChatColor.DARK_PURPLE + "SkinDropper Menu")){

            //IMPORTANT! Cancel the event so that players cannot move items
            event.setCancelled(true);

            //We only care about left clicks for this
            //if (!event.getClick().isLeftClick()) return;

            Player player = (Player) event.getWhoClicked();

            //Determine which "buttons" were clicked and do something
            switch (event.getCurrentItem().getType()){
                case WRITABLE_BOOK:
                    player.sendMessage(prefix + " Opening Settings...");
                    new Menu().OpenConfigMenu(player);
                    break;
                case KNOWLEDGE_BOOK:
                    player.sendMessage(prefix + " Opening Skins...");
                    new Menu().OpenSkinsMenu(player);
                    break;
                case BARRIER:
                    player.closeInventory();
                    player.sendMessage(prefix + " Closed menu.");
                    break;
            }

        }
        else if (event.getView().getTitle().equals(ChatColor.DARK_PURPLE + "SkinDropper Skins")) {

                //IMPORTANT! Cancel the event so that players cannot move items
                event.setCancelled(true);

                //We only care about left clicks for this
                //if (!event.getClick().isLeftClick()) return;

                Player player = (Player) event.getWhoClicked();
                ItemStack Clicked = event.getCurrentItem();

                //Determine which "buttons" were clicked and do something
                switch (Clicked.getType()) {
                    case WRITABLE_BOOK:
                        event.setCancelled(true);
                        functions.sendClickableCommand(player,prefix+" §5Click here to make new skin","skindroper:skindropper new ");
                        player.closeInventory();
                        break;
                    case BOOK:
                        event.setCancelled(true);
                        int page = NBT.get(Clicked, nbt -> (int) nbt.getInteger("page"));
                        if (event.getClick().isLeftClick()) new Menu().OpenSkinsMenu(player, page + 1);
                        else new Menu().OpenSkinsMenu(player, page - 1);
                        break;
                    case KNOWLEDGE_BOOK:
                        player.sendMessage(prefix + " Opening Skin...");
                        String string = NBT.get(Clicked, nbt -> (String) nbt.getString("skinid"));
                        new Menu().OpenSkinMenu(player, string.toString());
                        break;
                    case BARRIER:
                        event.setCancelled(true);
                        new Menu().OpenMenu(player);
                        //player.closeInventory();
                        player.sendMessage(prefix + " Closed menu.");
                        break;
                    case BLACK_STAINED_GLASS_PANE:
                        event.setCancelled(true);
                        break;
                }

        }
        else if (event.getView().getTitle().equals(ChatColor.DARK_PURPLE + "SkinDropper Skin")) {

            //IMPORTANT! Cancel the event so that players cannot move items
            //event.setCancelled(true);

            //We only care about left clicks for this
            //if (!event.getClick().isLeftClick()) return;

            Player player = (Player) event.getWhoClicked();
            ItemStack clicked = event.getCurrentItem();

            if (clicked == null || clicked.getType() == Material.AIR) {return;}

            //Determine which "buttons" were clicked and do something
            String ID = NBT.get(clicked, nbt -> (String) nbt.getString("skinid"));
            switch (Objects.requireNonNull(clicked).getType()) {
                case LAVA_BUCKET:
                    event.setCancelled(true);
                    CustomConfig.get().set("skins."+ID,null);
                    CustomConfig.save();
                    CustomConfig.reload();
                    player.playSound(player.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, SoundCategory.AMBIENT,2.0F,3.5F);
                    player.sendMessage(prefix + " Deleted.");
                    new Menu().OpenSkinsMenu(player);
                    break;
                case KNOWLEDGE_BOOK:
                    event.setCancelled(true);
                    player.closeInventory();
                    player.updateInventory();
                    player.getInventory().addItem(functions.getskinitem(ID));
                    player.sendMessage(prefix + " Gave Skin: "+ID);
                    break;
                case BARRIER:
                    event.setCancelled(true);
                    new Menu().OpenSkinsMenu(player);
                    //player.closeInventory();
                    player.sendMessage(prefix + " Closed menu.");
                    break;
                case BLACK_STAINED_GLASS_PANE:
                    event.setCancelled(true);
                    break;
                case LIME_DYE:
                    event.setCancelled(true);
                    new Menu().saveskin(event.getInventory(),ID);
                    new Menu().OpenSkinsMenu(player);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT,2.0F,3.5F);
                    player.sendMessage(prefix + " §aSkins Saved!");
                   // player.closeInventory();
                    break;
            }
            if (!clicked.getItemMeta().hasCustomModelData()) {
                event.setCancelled(true);
            }
        }
        else if (event.getView().getTitle().equals(ChatColor.DARK_PURPLE + "SkinDropper Config")) {

            //IMPORTANT! Cancel the event so that players cannot move items
            //event.setCancelled(true);

            //We only care about left clicks for this
            if (!event.getClick().isLeftClick()) return;

            Player player = (Player) event.getWhoClicked();

            //Determine which "buttons" were clicked and do something
            switch (event.getCurrentItem().getType()) {
                case WRITABLE_BOOK:
                    event.setCancelled(true);
                    player.sendMessage(prefix + " Opening Settings...");
                    new Menu().OpenConfigMenu(player);
                    break;
                case KNOWLEDGE_BOOK:
                    event.setCancelled(true);
                    player.sendMessage(prefix + " Opening Skins...");
                    new Menu().OpenSkinsMenu(player);
                    break;
                case BARRIER:
                    event.setCancelled(true);
                    player.closeInventory();
                    player.sendMessage(prefix + " Closed menu.");
                    break;
                case BLACK_STAINED_GLASS_PANE:
                    event.setCancelled(true);
                    break;
            }
        }
    }
}
