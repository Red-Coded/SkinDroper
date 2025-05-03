package me.trex.skindroper;

import de.tr7zw.changeme.nbtapi.NBT;
import me.trex.skindroper.Commands.CommandTest;
import me.trex.skindroper.Commands.commands;
import me.trex.skindroper.Commands.skindropperCommand;
import me.trex.skindroper.Listeners.InventoryListener;
import me.trex.skindroper.Listeners.MenuListener;
import me.trex.skindroper.skinloader.SkinLoaderMain;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

import static me.trex.skindroper.functions.hex;

public final class SkinDroper extends JavaPlugin implements Listener {

    public static String prefix = "sd";

    private static SimpleCommandMap scm;
    private static SkinDroper instance;

    @Override
    public void onEnable() {
        if (!NBT.preloadApi()) {
            getLogger().warning("NBT-API wasn't initialized properly, disabling the plugin");
            getPluginLoader().disablePlugin(this);
            return;
        }
        instance = this;
        setupSimpleCommandMap();
        // Plugin startup logic
        this.saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
        getServer().getPluginManager().registerEvents(new MenuListener(),this);
        Objects.requireNonNull(getCommand("skindropper")).setExecutor(new commands(this));
        Objects.requireNonNull(getCommand("sdd")).setExecutor(new skindropperCommand());

        registerCommands(new CommandTest());

        try {
            SkinLoaderMain.setup();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //set up config
        prefix = hex(this.getConfig().getString("prefix"));
        CustomConfig.setup();
        CustomConfig.save();

        getLogger().info(ChatColor.GREEN +"Skin Dropper Has been Enabled.");
    }

    private void registerCommands(Command... commands) {
        Arrays.stream(commands).forEach(command -> scm.register("SkinLoader", command));//Register the plugin
    }

    private void setupSimpleCommandMap() {
        SimplePluginManager spm = (SimplePluginManager) this.getServer().getPluginManager();
        Field f = null;
        try {
            f = SimplePluginManager.class.getDeclaredField("commandMap");
        } catch (Exception e) {
            e.printStackTrace();
        }
        f.setAccessible(true);
        try {
            scm = (SimpleCommandMap) f.get(spm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SimpleCommandMap getCommandMap() {
        return scm;
    }

    public static <SkinDroper> JavaPlugin getInstance() {
        return instance;
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info(ChatColor.DARK_RED +"Skin Dropper HAs been Disabled.");
    }

    @EventHandler
    public  void onPlayerJoin(PlayerJoinEvent event) throws InterruptedException {
    }

    @EventHandler
    public void  playerInteraction(PlayerInteractEvent event) {
        // getLogger().info(event.getPlayer().getDisplayName()+"Interacted with pass_task");
    }

}
