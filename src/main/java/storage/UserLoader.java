package storage;

import com.zaxxer.hikari.HikariDataSource;
import net.ultimatemc.xenforointegration.XenforoIntegration;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLoader {

    XenforoIntegration plugin;
    storage.SqlDatabase db;
    HikariDataSource ds;

    private final String INSERT = "INSERT INTO xf_minecraft VALUES(?,?)";
    private final String SELECT_UUID = "SELECT * FROM xf_minecraft WHERE uuid=?";

    public UserLoader(XenforoIntegration plugin, SqlDatabase db) {
        this.plugin = plugin;
        this.db = db;
        ds = db.getDataSource();

    }

    public void insert(String uuid, int user_id) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (Connection connection = ds.getConnection();
                 PreparedStatement insert = connection.prepareStatement(INSERT)) {

                insert.setString(1, uuid);
                insert.setInt(2, user_id);

                insert.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void loadUser(String uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (Connection connection = ds.getConnection();
                 PreparedStatement select = connection.prepareStatement(SELECT_UUID)) {

                select.setString(1, uuid);

                ResultSet result = select.executeQuery();
                while (result.next()) {
                    plugin.addUser(uuid, result.getInt("user_id"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}