package fr.silenthill99.banmanager.mysql;

import fr.silenthill99.banmanager.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    public static final Main main = Main.getInstance();
    private final DbCredentials credentials;
    private Connection connection;

    public DbConnection(DbCredentials credentials){
        this.credentials = credentials;
        connect();
    }

    private void connect() {
        try {
            this.connection = DriverManager.getConnection(credentials.toURI(), credentials.getUser(),
                    credentials.getPass());
            main.getLogger().info("Connection établie avec la BDD !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() throws SQLException {
        if (this.connection != null) {
            if (!this.connection.isClosed()) {
                this.connection.close();
                main.getLogger().info("Déconnection de la BDD !");
            }
        }
    }

    public Connection getConnection() throws SQLException {
        if (this.connection != null) {
            if (!this.connection.isClosed()) {
                return this.connection;
            }
        }
        connect();
        return connection;
    }
}
