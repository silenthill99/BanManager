package fr.silenthill99.banmanager.commands;

import fr.silenthill99.banmanager.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@SuppressWarnings("deprecation")
public class Unban implements CommandExecutor {
    private final Main main = Main.getInstance();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s,
                             @NotNull String[] args) {

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "/unban <joueur>");
            return false;
        }

        String targetName = args[0];

        if (main.playerInfos.exist(targetName)) {
            sender.sendMessage(ChatColor.RED + "Ce joueur ne s'est jamais connecté au serveur !");
        }

        UUID uuid = main.playerInfos.getUUID(targetName);

        if (!main.getBanManager().isBanned(uuid)) {
            sender.sendMessage(ChatColor.RED + "Ce joueur n'est pas banni !");
            return false;
        }

        main.getBanManager().unban(uuid);
        sender.sendMessage(ChatColor.GREEN + "Vous avez débanni " + ChatColor.GOLD + targetName);

        return true;
    }
}
