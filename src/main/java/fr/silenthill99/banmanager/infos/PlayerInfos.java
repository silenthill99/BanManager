package fr.silenthill99.banmanager.infos;

import fr.silenthill99.banmanager.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerInfos {

    private final Main main = Main.getInstance();
    Connection connection;

    {
        try {
            connection = main.getManager().connection().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Actualiser/créer les informations du joueur
     */
    public void update(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(main, ()-> {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT player_name FROM player_infos" +
                        " WHERE player_uuid = ?");
                statement.setString(1, player.getUniqueId().toString());
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    PreparedStatement update = connection.prepareStatement("UPDATE player_infos SET player_name = ? " +
                            "WHERE player_uuid = ?");
                    update.setString(1, player.getName());
                    update.setString(2, player.getUniqueId().toString());
                    update.executeUpdate();
                    update.close();
                    main.getLogger().info("Update : " + player.getName() + ", " + player.getUniqueId()); // Facultatif
                } else {
                    PreparedStatement insert = connection.prepareStatement("INSERT INTO player_infos (player_uuid," +
                            " player_name) VALUES (?, ?)");
                    insert.setString(1, player.getUniqueId().toString());
                    insert.setString(2, player.getName());
                    insert.executeUpdate();
                    main.getLogger().info("Update : " + player.getName() + ", " + player.getUniqueId()); // Facultatif
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Vérifie si le joueur a des informations dans la base de donnnées
     * @return true/false
     */
    public boolean exist(String playerName) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM player_infos WHERE player_name = ?");
            statement.setString(1, playerName);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Récupérer l'UUID d'un joueur avec son pseudo
     */
    public UUID getUUID(String playerName) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT player_uuid FROM player_infos WHERE player_name = ?");
            statement.setString(1, playerName);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return UUID.fromString(rs.getString("player_uuid"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new NullPointerException("Le joueur n'a pas d'informatiions dans la table !");
    }

}
