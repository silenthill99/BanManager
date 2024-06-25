package fr.silenthill99.banmanager.listener;

import fr.silenthill99.banmanager.Main;
import fr.silenthill99.banmanager.infos.PlayerInfos;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

@SuppressWarnings("deprecation")
public class Events implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerInfos playerInfos = new PlayerInfos();
        playerInfos.update(player);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (main.getBanManager().isBanned(player.getUniqueId())) {
            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
            event.setKickMessage(ChatColor.RED + "Vous êtes banni !\n " +
                "\n" +
                ChatColor.GOLD + "Raison : " + ChatColor.WHITE + main.getBanManager().getReason(player.getUniqueId())
                + "\n " +
                "\n" +
                ChatColor.GREEN + "Temps restant : " + ChatColor.WHITE + main.getBanManager().getTimeLeft(
                player.getUniqueId()));
        }
    }
}
