package fr.silenthill99.banmanager.ban;

import fr.silenthill99.banmanager.Main;
import fr.silenthill99.banmanager.utils.TimeUnit;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@SuppressWarnings("deprecation")
public class BanManager {

    Main main;
    Connection connection;

    public BanManager() {
        this.main = Main.getInstance();
        try {
            this.connection = main.getManager().connection().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void ban(UUID uuid, long endInSeconds, String reason) {
        if (isBanned(uuid)) return;

        long endToMillis = endInSeconds*1000;
        long end = endToMillis + System.currentTimeMillis();

        if (endInSeconds == -1) end = -1;

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO bans (player_uuid, end, reason) VALUES (?, ?, ?)");
            statement.setString(1, uuid.toString());
            statement.setLong(2, end);
            statement.setString(3, reason);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Player target = Bukkit.getPlayer(uuid);
        if (target != null) {
            target.kick(Component.text(ChatColor.RED + "Vous avez été banni !\n " +
                "\n" +
                ChatColor.GOLD + "Raison : " + ChatColor.WHITE + reason + "\n " +
                "\n" +
                ChatColor.GREEN + "Temps restant : " + ChatColor.WHITE + getTimeLeft(uuid)));
        }
    }

    public void unban(UUID uuid) {
        if (isBanned(uuid)) return;

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM bans WHERE player_uuid = ?");
            statement.setString(1, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isBanned(UUID uuid) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM bans WHERE player_uuid = ?");
            statement.setString(1, uuid.toString());
            ResultSet rs = statement.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkDuration(UUID uuid) {
        if (!isBanned(uuid)) return;

        if (getEnd(uuid) == -1) return;

        if (getEnd(uuid) < System.currentTimeMillis()) {
            unban(uuid);
        }
    }

    public long getEnd(UUID uuid) {
        if(!isBanned(uuid)) return 0;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM bans WHERE player_uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getLong("end");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public String getTimeLeft(UUID uuid) {
        if (!isBanned(uuid)) return ChatColor.RED + "Non banni";

        if (getEnd(uuid) == -1) return ChatColor.RED + "Permanent";

        long tempsRestant = (getEnd(uuid) - System.currentTimeMillis()) / 1000;
        int mois = 0;
        int jours = 0;
        int heures = 0;
        int minutes = 0;
        int secondes = 0;

        while (tempsRestant >= TimeUnit.MOIS.getToSecond()){
            mois++;
            tempsRestant -= TimeUnit.MOIS.getToSecond();
        }
        while (tempsRestant >= TimeUnit.JOUR.getToSecond()){
            jours++;
            tempsRestant -= TimeUnit.JOUR.getToSecond();
        }
        while (tempsRestant >= TimeUnit.HEURE.getToSecond()){
            heures++;
            tempsRestant -= TimeUnit.HEURE.getToSecond();
        }
        while (tempsRestant >= TimeUnit.MINUTE.getToSecond()){
            minutes++;
            tempsRestant -= TimeUnit.MINUTE.getToSecond();
        }
        while (tempsRestant >= TimeUnit.SECONDE.getToSecond()){
            secondes++;
            tempsRestant -= TimeUnit.SECONDE.getToSecond();
        }
        return mois + " " + TimeUnit.MOIS.getName() + ", " + jours + " " + TimeUnit.JOUR.getName() + ", " + heures + " " +
                TimeUnit.HEURE.getName() + ", " + minutes + " " + TimeUnit.MINUTE.getName() + ", " + secondes + " " +
                TimeUnit.SECONDE.getName();
    }

    public String getReason(UUID uuid) {
        if (!isBanned(uuid)) return ChatColor.RED + "Non banni";
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM bans WHERE player_uuid = ?");
            statement.setString(1, uuid.toString());
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return rs.getString("reason");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ChatColor.RED + "Non banni";
    }

}
