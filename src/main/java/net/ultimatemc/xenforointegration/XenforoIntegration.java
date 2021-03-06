package net.ultimatemc.xenforointegration;

import net.ultimatemc.xenforointegration.commands.RegisterCommand;
import net.ultimatemc.xenforointegration.commands.WebCommand;
import net.ultimatemc.xenforointegration.managers.ManagerHandler;
import net.ultimatemc.xenforointegration.managers.NameManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import net.ultimatemc.xenforointegration.storage.SqlDatabase;
import net.ultimatemc.xenforointegration.storage.UserLoader;
import net.ultimatemc.xenforointegration.util.ConfigLoader;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class XenforoIntegration extends JavaPlugin {

    SqlDatabase db;
    private Map<String, Integer> users = new ConcurrentHashMap<>();
    public XenApiWrapper xenApi;
    private UserLoader userLoader;
    private ManagerHandler managerHandler;
    private Config config;

    @Override
    public void onEnable() {

        try {
            File dataFolder = this.getDataFolder();
            dataFolder.mkdir();
            config = ConfigLoader.loadConfig(Config.class, new File(this.getDataFolder().getPath() + File.separator + "config.json"));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Error loading XenforoIntegration config");
            return;
        }
        db = new SqlDatabase(this, config.getDatabase().getHost(), config.getDatabase().getUser(), config.getDatabase().getPassword());
        xenApi = new XenApiWrapper(config.getWebsite().getApiUrl(), config.getWebsite().getApiKey());

        //Load Managers
        managerHandler = new ManagerHandler(this, xenApi);
        managerHandler.addManager(new NameManager(this));

        userLoader = new UserLoader(this, db, managerHandler);

        //Register commands
        this.getCommand("register").setExecutor(new RegisterCommand(this, userLoader));
        this.getCommand("web").setExecutor(new WebCommand(this, userLoader));

        //Listeners
        new ListenerClass(this, userLoader);

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
