package net.ultimatemc.xenforointegration;

import java.util.LinkedHashMap;
import java.util.Map;

public class Config {

    private Database database = new Database(
            "jdbc:mysql://localhost:3306/database",
            "user",
            "password"
    );

    private Website website = new Website(
            "https://mysite.com/api.php",
            "my_api_key"
    );

    /**
     * Defines the unique permission that should be used to identify which users below to which xenforo groups
     * Xenforo groups are represented by their group ids (Integer).
     * Should be in descending order of rank importance/inheritance
     */
    private Map<String, Integer> groupPermissions = new LinkedHashMap<String, Integer>() {{
        put("rank_permission", -1);
    }};


    public Database getDatabase() {
        return database;
    }

    public Website getWebsite() {
        return website;
    }

    public Map<String, Integer> getGroupPermissions() {
        return groupPermissions;
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

    class Website {

        private String apiUrl;
        private String apiKey;

        public Website(String apiUrl, String apiKey) {
            this.apiUrl = apiUrl;
            this.apiKey = apiKey;
        }

        public String getApiUrl() {
            return apiUrl;
        }

        public String getApiKey() {
            return apiKey;
        }
    }
}


