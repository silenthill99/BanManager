package fr.silenthill99.banmanager.mysql;

public class DbCredentials {

    private final String host;
    private final String user;
    private final String pass;
    private final int port;
    private final String dbName;

    public DbCredentials(String host, String user, String pass, int port, String dbName) {
        this.host = host;
        this.user = user;
        this.pass = pass;
        this.port = port;
        this.dbName = dbName;
    }

    public String toURI() {
        StringBuilder url = new StringBuilder();
        url.append("jdbc:mysql://")
                .append(host)
                .append(":")
                .append(port)
                .append("/")
                .append(dbName);
        return url.toString();
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }
}
