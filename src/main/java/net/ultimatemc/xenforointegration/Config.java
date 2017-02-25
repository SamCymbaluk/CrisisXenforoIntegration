package net.ultimatemc.xenforointegration;

public class Config {

    private Database database;

    public Database getDatabase() {
        return database;
    }
}


class Database {

    private String host;
    private String user;
    private String password;

    public Database(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
