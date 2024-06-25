package fr.silenthill99.banmanager.commands;

import fr.silenthill99.banmanager.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

@SuppressWarnings("deprecation")
public class Ban implements CommandExecutor {

    private final Main main;
    private final Connection connection;

    public Ban() {
        this.main = Main.getInstance();
        try {
            this.connection = main.getManager().connection().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s,
                             @NotNull String[] args) {

        if (args.length < 3) {
            helpMessage(sender);
            return false;
        }

        String targetName = args[0];

        Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
            if (!main.playerInfos.exist(targetName)) {
                sender.sendMessage(ChatColor.RED + "Ce joueur ne s'est jamais connecté au serveur");
            }
        });

        UUID targetUUID = main.playerInfos.getUUID(targetName);
        if (main.getBanManager().isBanned(targetUUID)) {
            sender.sendMessage(ChatColor.RED + "Ce joueur est déjà banni !");
            return false;
        }

        StringBuilder reason = new StringBuilder();

        for (int i = 2; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }

        if (args[1].equalsIgnoreCase("perm")) {
            main.getBanManager().ban(targetUUID, -1, reason.toString());
            sender.sendMessage(ChatColor.GREEN + "Vous avez banni " + ChatColor.GOLD + targetName);
        }

        return true;
    }

    public void helpMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "/ban <joueur> perm <raison>");
        sender.sendMessage(ChatColor.RED + "/ban <joueur> <durée>:<unité> <raison>");
    }
}
