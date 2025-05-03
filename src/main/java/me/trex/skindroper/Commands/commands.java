package me.trex.skindroper.Commands;

import me.trex.skindroper.CustomConfig;
import me.trex.skindroper.SkinDroper;
import me.trex.skindroper.gui.Menu;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static me.trex.skindroper.functions.hex;

public class commands implements TabCompleter, CommandExecutor {
    private  final SkinDroper plugin;

    public commands(SkinDroper plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        var prefix = hex((String) this.plugin.getConfig().get("prefix"));

        if (args.length > 0 && Objects.equals(args[0], "reload")) {
            sender.sendMessage(prefix+ hex("&2 Reloading config..."));
            plugin.reloadConfig();
            CustomConfig.reload();
            sender.sendMessage(prefix+ hex("&2 Config has been reloaded."));
        } else if (sender instanceof Player player && args.length > 0 && Objects.equals(args[0], "skins")) {
            new Menu().OpenSkinsMenu(player);
        } else if (sender instanceof Player player && args.length > 0 && Objects.equals(args[0], "new")){
            if (args.length >=2 && !Arrays.toString(args).contains(".")) {
                ArrayList<String> tt = new ArrayList<>(Arrays.stream(args).toList());
                tt.removeFirst();
                StringBuilder sb = new StringBuilder();
                for (String s : tt)
                {
                    sb.append(s.replace('&','§'));
                    sb.append(" ");
                }

                if (!CustomConfig.get().isSet("skins." + sb)) {
                    CustomConfig.get().set("skins."+sb+".level", "all");
                    CustomConfig.save();
                    player.sendMessage(prefix + " §6Created Skin: §r" + sb);
                    new Menu().OpenSkinMenu(player,sb.toString());
                } else {
                    player.sendMessage(prefix + " §cSkin §r<" + sb + "§r> §c already exists.");
                }
            } else if (Arrays.toString(args).contains(".")) {
                player.sendMessage(prefix+" §cName is invalid. Name cannot Contain '§2.§c'");
            } else {
                player.sendMessage(prefix+" §cYou need to provide a name.");
            }
        } else if (sender instanceof Player player){
            new Menu().OpenMenu(player);
        }else{
            sender.sendMessage(prefix + ChatColor.RED + " You must be a player to use this command.");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1){
            List<String> arguments = new ArrayList<>();
            arguments.add("config");
            arguments.add("reload");
            arguments.add("skins");
            arguments.add("new");
            return arguments;
        } else if (strings.length >= 2 && Objects.equals(strings[1], "skins")) {
            return new ArrayList<>(Objects.requireNonNull(CustomConfig.get().getConfigurationSection("skins")).getKeys(false));
        }
        List<String> arguments = new ArrayList<>();
        return arguments;
    }
}
