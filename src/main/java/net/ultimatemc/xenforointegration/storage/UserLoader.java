package net.ultimatemc.xenforointegration.storage;

import com.zaxxer.hikari.HikariDataSource;
import net.ultimatemc.xenforointegration.managers.ManagerHandler;
import net.ultimatemc.xenforointegration.XenforoIntegration;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserLoader {

    private XenforoIntegration plugin;
    private SqlDatabase db;
    private HikariDataSource ds;
    private ManagerHandler managerHandler;

    private final String INSERT = "INSERT INTO xf_minecraft VALUES(?,?)";
    private final String SELECT_UUID = "SELECT * FROM xf_minecraft WHERE uuid=?";

    public UserLoader(XenforoIntegration plugin, SqlDatabase db, ManagerHandler managerHandler) {
        this.plugin = plugin;
        this.db = db;
        this.managerHandler = managerHandler;
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
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        managerHandler.onPlayerLoad(Bukkit.getPlayer(UUID.fromString(uuid)));
                    });
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}