package net.ultimatemc.xenforointegration;

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



    public Database getDatabase() {
        return database;
    }

    public Website getWebsite() {
        return website;
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


