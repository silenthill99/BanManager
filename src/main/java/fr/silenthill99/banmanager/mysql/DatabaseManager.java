package fr.silenthill99.banmanager.mysql;

import java.sql.SQLException;

public class DatabaseManager {

    private final DbConnection connection;

    public DatabaseManager() {
        connection = new DbConnection(new DbCredentials("sql3.minestrator.com", "minesr_wACHU9Fo",
                "fyuJevJQiCuwzEYx", 3306, "minesr_wACHU9Fo"));
    }

    public DbConnection connection() {
        return connection;
    }

    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
