package fr.silenthill99.banmanager.commands;

import fr.silenthill99.banmanager.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@SuppressWarnings("deprecation")
public class Check implements CommandExecutor {

    private final Main main = Main.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s,
                             @NotNull String[] args) {

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "/check <joueur>");
            return false;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        UUID targetUUID = main.playerInfos.getUUID(target.getName());

        main.getBanManager().checkDuration(targetUUID);

        if (!main.playerInfos.exist(target.getName())) {
            sender.sendMessage(ChatColor.GRAY + "-----------------------------------------------------");
            sender.sendMessage(ChatColor.YELLOW + "Pseudo : " + ChatColor.GRAY + target.getName());
            sender.sendMessage(ChatColor.YELLOW + "UUID : " + ChatColor.GRAY + targetUUID);
            sender.sendMessage(ChatColor.YELLOW + "Banni : " + (main.getBanManager().isBanned(targetUUID) ? "✅":"❌"));
            if (main.getBanManager().isBanned(targetUUID)) {
                sender.sendMessage("\n" + ChatColor.GOLD + "Raison : " + ChatColor.RED + main.getBanManager()
                        .getReason(targetUUID));
                sender.sendMessage(ChatColor.GOLD + "Temps restant : " + ChatColor.WHITE + main.getBanManager()
                        .getTimeLeft(targetUUID));
            }
            sender.sendMessage(ChatColor.GRAY + "-----------------------------------------------------");
        }

        return true;
    }
}
