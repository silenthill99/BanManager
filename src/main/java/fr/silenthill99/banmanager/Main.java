package fr.silenthill99.banmanager;

import fr.silenthill99.banmanager.ban.BanManager;
import fr.silenthill99.banmanager.commands.Ban;
import fr.silenthill99.banmanager.commands.Check;
import fr.silenthill99.banmanager.commands.Unban;
import fr.silenthill99.banmanager.infos.PlayerInfos;
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

    BanManager banManager;
    public PlayerInfos playerInfos;

    @Override
    public void onEnable() {
        instance = this;
        manager = new DatabaseManager();
        banManager = new BanManager();
        playerInfos = new PlayerInfos();
        getLogger().info("Le plugin est opérationnel !");
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new Events(), this);
        commands();
    }

    @SuppressWarnings("DataFlowIssue")
    private void commands() {
        getCommand("ban").setExecutor(new Ban());
        getCommand("unban").setExecutor(new Unban());
        getCommand("check").setExecutor(new Check());
    }

    @Override
    public void onDisable() {
        manager.close();
    }

    public DatabaseManager getManager() {
        return manager;
    }

    public BanManager getBanManager() {
        return banManager;
    }
}
