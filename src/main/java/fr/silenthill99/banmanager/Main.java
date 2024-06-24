package fr.silenthill99.banmanager;

import fr.silenthill99.banmanager.listener.Events;
import fr.silenthill99.banmanager.mysql.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    private DatabaseManager manager;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        manager = new DatabaseManager();
        getLogger().info("Le plugin est opérationnel !");
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new Events(), this);
    }

    @Override
    public void onDisable() {
        manager.close();
    }

    public DatabaseManager getManager() {
        return manager;
    }
}
