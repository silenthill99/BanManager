package fr.silenthill99.banmanager.listener;

import fr.silenthill99.banmanager.Main;
import fr.silenthill99.banmanager.infos.PlayerInfos;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Events implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerInfos playerInfos = new PlayerInfos();
        playerInfos.update(player);

    }
}
