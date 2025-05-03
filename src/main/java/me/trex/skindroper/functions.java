package me.trex.skindroper;

import de.tr7zw.changeme.nbtapi.NBT;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class functions {
    private functions(){
        throw new UnsupportedOperationException();
    }

    /**
     * Formats the string with HEX code
     *
     * @param message The String you would like to format
     * @return Returns the formated string
     */
    public static String hex(String message) {
        Pattern pattern = Pattern.compile("(&#[a-fA-F0-9]{6})");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace("&#", "x");

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder("");
            for (char c : ch) {
                builder.append("&" + c);
            }

            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message).replace('&', '§');
    }

    /**
     * Sends a clickable message to a player that suggests a command when clicked.
     * @param message The clickable message!
     * @param command The command without the slash to suggest to the user.
     * @param player player to send to.
     */
    public static void sendClickableCommand(Player player, String message, String command) {
        // Make a new component (Bungee API).
        TextComponent component = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', message)));
        // Add a click event to the component.
        component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + command));

        // Send it!
        player.spigot().sendMessage(component);
    }

    public static ItemStack getskinitem(String ID) {

        ItemStack skin = new ItemStack(Material.KNOWLEDGE_BOOK);
        NBT.modify(skin, NBT -> {
            NBT.setString("skinid",ID);
            NBT.setBoolean("skin",true);
        });

        ItemMeta skinmeta = skin.getItemMeta();
        skinmeta.setDisplayName("§r§d"+ID);
        ArrayList<String> skinlore = new ArrayList<>();
        skinlore.add("§9Apply to:");

        if (CustomConfig.get().isSet("skins." + ID + ".tools")) {
            skinlore.addAll(Objects.requireNonNull(CustomConfig.get().getConfigurationSection("skins." + ID + ".tools")).getKeys(false));
        }
        if (CustomConfig.get().isSet("skins." + ID + ".armour")) {
            skinlore.addAll(Objects.requireNonNull(CustomConfig.get().getConfigurationSection("skins." + ID + ".armour")).getKeys(false));
        }
        skinmeta.setLore(skinlore);
        skin.setItemMeta(skinmeta);
        return skin;
    }

    public static ItemStack dyearmour(ItemStack item, String ID) {
        Material[] armour = {Material.LEATHER_BOOTS,Material.LEATHER_LEGGINGS,Material.LEATHER_CHESTPLATE,Material.LEATHER_HELMET};

        if (Arrays.stream(armour).anyMatch(tt -> item.getType().equals(tt))) {
            String type = item.getType().toString().split("_")[1];
            ItemMeta meta = item.getItemMeta();
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;

            leatherArmorMeta.setColor(CustomConfig.get().getColor("skins." + ID + ".armour."+type+".RGB"));

            item.setItemMeta(leatherArmorMeta);
            return item;
        }

        return item;
    }

}
