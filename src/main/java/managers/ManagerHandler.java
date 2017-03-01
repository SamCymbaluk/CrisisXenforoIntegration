package managers;

import net.ultimatemc.xenforointegration.XenApiWrapper;
import net.ultimatemc.xenforointegration.XenforoIntegration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerHandler {

    private XenforoIntegration plugin;
    private XenApiWrapper xenApi;
    private List<Manager> managers = new ArrayList<>();

    public ManagerHandler(XenforoIntegration plugin, XenApiWrapper xenApi) {
        this.plugin = plugin;
        this.xenApi = xenApi;
    }

    public void addManager(Manager manager) {
        managers.add(manager);
    }

    public List<Manager> getManagers() {
        return managers;
    }

    /**
     * https://github.com/Contex/XenAPI/wiki/REST-API-Actions#editUser-required-parameters-user
     *
     * @param params
     * @param player
     */
    private void editUser(Map<String, String> params, Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            params.put("user", Integer.toString(plugin.getUserId(player.getUniqueId().toString()))); //The player we are editing
            xenApi.executeQuery("editUser", params);
        });
    }

    /**
     * Called after player is loaded from DB
     *
     * @param player
     */
    public void onPlayerLoad(Player player) {
        Map<String, String> editUserParams = new HashMap<>();
        for (Manager manager : managers) {
            editUserParams = manager.onPlayerLoad(player, editUserParams);
        }
        editUser(editUserParams, player);
    }
}
