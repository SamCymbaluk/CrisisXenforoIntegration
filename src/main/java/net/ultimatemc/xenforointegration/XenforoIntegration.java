package net.ultimatemc.xenforointegration;

import commands.RegisterCommand;
import commands.WebCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import storage.SqlDatabase;
import storage.UserLoader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class XenforoIntegration extends JavaPlugin {

    SqlDatabase db;
    private Map<String, Integer> users = new ConcurrentHashMap<>();
    public XenApiWrapper xenApi;
    private UserLoader userLoader;

    @Override
    public void onEnable() {
        //db = new SqlDatabase(this, "jdbc:mysql://ultimatemc.net:3306/web", "xenforo", "QJMTEz1ulJRwtqyuQYkb"); //Remote
        db = new SqlDatabase(this, "jdbc:mysql://localhost:3306/xenforo", "root", "e262Y%39f0d7e956642#0((47d19fc9e94c"); //Local
        xenApi = new XenApiWrapper("https://ultimatemc.net/api.php", "6f78c25a93b4c327846ce0f50c5e675ba35d271518471d8228c0850ecd3f5aab");
        userLoader = new UserLoader(this, db);

        this.getCommand("register").setExecutor(new RegisterCommand(this, userLoader));
        this.getCommand("web").setExecutor(new WebCommand(this, userLoader));

        for (Player p : Bukkit.getOnlinePlayers()) {
            userLoader.loadUser(p.getUniqueId().toString());
        }
    }

    @Override
    public void onDisable() {

    }

    public void addUser(String uuid, int user_id) {
        users.put(uuid, user_id);
    }

    public void removeUser(String uuid) {
        users.remove(uuid);
    }

    public boolean containsUser(String uuid) {
        return users.containsKey(uuid);
    }

    public int getUserId(String uuid) {
        return users.get(uuid);
    }
}