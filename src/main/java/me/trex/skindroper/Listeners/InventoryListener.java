package me.trex.skindroper.Listeners;

import de.tr7zw.changeme.nbtapi.NBT;
import me.trex.skindroper.CustomConfig;
import me.trex.skindroper.SkinDroper;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static me.trex.skindroper.functions.dyearmour;
import static me.trex.skindroper.functions.hex;


public class InventoryListener implements Listener {
    private  final SkinDroper plugin;

    public InventoryListener(SkinDroper plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) throws InterruptedException {
        if (e.getWhoClicked() instanceof Player player) {
            if (e.getCurrentItem() == null || e.getCursor() == null || e.getCurrentItem().getType() == Material.AIR || e.getCursor().getType() == Material.AIR) {
                return;
            }

            var prefix = hex((String) this.plugin.getConfig().get("prefix"));
            var clickedItem = e.getCurrentItem();
            var heldItem = e.getCursor();
            ItemStack updated = clickedItem.clone();
            LocalTime now = LocalTime.now();
            int currentTimeAsInt = now.getHour() * 10000 + now.getMinute() * 100 + now.getSecond();

            try {
                Boolean bool = NBT.get(heldItem, nbt -> (boolean) nbt.getBoolean("skin"));
                if (Boolean.FALSE.equals(bool)) {
                    return;
                }
            } catch (RuntimeException ex) {
                throw new RuntimeException(ex);
            }
            String skinID = NBT.get(heldItem, nbt -> (String) nbt.getString("skinid"));

            Boolean alloweditem = false; //clickedItem.getType() == Material.DIAMOND_SWORD
            Boolean allowedmat = false;
            String itemmat = null;
            //check if item is allowed
            String[] split = clickedItem.getType().toString().split("_");

            if (Objects.equals(CustomConfig.get().getString("skins." + skinID + ".level"), "all")) {
                allowedmat = true;
            }else if (Objects.equals(Objects.requireNonNull(CustomConfig.get().getString("skins." + skinID + ".level")).toLowerCase(), split[0].toLowerCase())) {
                allowedmat = true;
            }

            if (split.length >= 2 && CustomConfig.get().isSet("skins."+skinID+".tools."+split[1].toLowerCase())) {
                alloweditem = true;
                itemmat = split[1].toLowerCase();
            } else if (clickedItem.getType().equals(Material.MACE) && CustomConfig.get().isSet("skins."+skinID+".tools.mace")) {
                alloweditem = true;
                allowedmat = true;
                itemmat = "mace";
            } else if (clickedItem.getType().equals(Material.FISHING_ROD) && CustomConfig.get().isSet("skins."+skinID+".tools.fishingrod")) {
                alloweditem = true;
                allowedmat = true;
                itemmat = "fishingrod";
            } else if (clickedItem.getType().equals(Material.SHIELD) && CustomConfig.get().isSet("skins."+skinID+".tools.shield")) {
                alloweditem = true;
                allowedmat = true;
                itemmat = "shield";
            }

            // if items allowed attempt to apply skin
            if (heldItem.getType() == Material.KNOWLEDGE_BOOK && alloweditem && allowedmat) {
                GameMode gameMode = e.getWhoClicked().getGameMode();

                if (!gameMode.equals(GameMode.SURVIVAL) && !gameMode.equals(GameMode.ADVENTURE)) {
                    player.sendMessage(prefix + hex(this.plugin.getConfig().getString("messages.wrong_game_mode")));
                    return;
                }

                NBT.modify(updated, nbt -> {
                    nbt.setInteger("lastclicked", currentTimeAsInt);
                });

                int lastclicked = NBT.get(clickedItem, nbt -> (int) nbt.getInteger("lastclicked"));
                int confirmtime = plugin.getConfig().getInt("confirm_time");

                if (lastclicked < currentTimeAsInt && (currentTimeAsInt - lastclicked < confirmtime)) {
                    ItemStack air = new ItemStack(Material.AIR);
                    ItemMeta toolmeta = Objects.requireNonNull(clickedItem).getItemMeta();

                    toolmeta.setCustomModelData(CustomConfig.get().getInt("skins."+skinID+".tools."+itemmat));
                    List<String> Lore = new ArrayList<String>();
                    Lore.add(ChatColor.GRAY + "§lskin: "+skinID);
                    toolmeta.setLore(Lore);
                    updated.setItemMeta(toolmeta);

                    NBT.modify(updated, nbt -> {
                        nbt.removeKey("lastclicked");
                        nbt.setBoolean("has_skin", true);
                        nbt.setString("skinid",skinID);
                    });

                    e.setCancelled(true);
                    updated = dyearmour(updated, skinID);
                    e.setCurrentItem(updated);
                    e.getWhoClicked().setItemOnCursor(air);

                    player.sendMessage(prefix + hex(this.plugin.getConfig().getString("messages.skin_applied")));
                } else {
                    e.setCancelled(true);
                    e.setCurrentItem(updated);
                    player.sendMessage(prefix + hex(this.plugin.getConfig().getString("messages.skin_attempt")));
                    player.updateInventory();
                }
            }
        }
    }
}

