package storage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.ultimatemc.xenforointegration.XenforoIntegration;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlDatabase {

    private XenforoIntegration plugin;
    private HikariDataSource ds;

    public SqlDatabase(XenforoIntegration plugin, String url, String username, String password) {
        this.plugin = plugin;

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setLeakDetectionThreshold(10000);
        ds = new HikariDataSource(config);

        setupTables();

    }

    /**
     * Runs on main thread. Use only on startup.
     */
    private void setupTables() {
        System.out.println("Setting up MySQL tables");
        try (Connection con = ds.getConnection()) {
            Statement players = con.createStatement();
            players.executeUpdate("CREATE TABLE IF NOT EXISTS xf_minecraft("
                    + "uuid VARCHAR(36), "
                    + "user_id INT, "
                    + "PRIMARY KEY (uuid))");

        } catch (SQLException e) {
            Bukkit.getPluginManager().disablePlugin(plugin);
            e.printStackTrace();
        }
    }

    public HikariDataSource getDataSource() {
        return ds;

    }


}