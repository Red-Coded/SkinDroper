package me.trex.skindroper.Commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandTest extends CCommand {
    //https://www.spigotmc.org/threads/how-to-register-commands-without-plugin-yml.441499/
    public CommandTest() {
        super("test");
    }
    @Override
    public void run(CommandSender s, String cl, String[] args) {

        if (!(s instanceof Player)) {
            s.sendMessage("The Console can't run this command!");
            return;
        }
        Player player = (Player) s;
        if (!player.hasPermission("skindropper.admin")) {
            s.sendMessage(ChatColor.RED + "You don't have permission to use this!");
            return;
        }
        sendMessage("TEST " + (args.length > 0 ? args[0] : "No args"));
        //random code
    }


    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] arguments) {
        List<String> arguments1 = Arrays.asList("testing", "testing2");

        List<String> result = new ArrayList<String>();
        if (arguments.length == 1) {
            for (String argument : arguments1)
                if (argument.toLowerCase().startsWith(arguments[0].toLowerCase()))
                    result.add("uhhh");
            return result;
        }

        return result;
    }
}
