package managers;

import net.ultimatemc.xenforointegration.XenforoIntegration;
import org.bukkit.entity.Player;

import java.util.Map;

public class NameManager implements Manager {

    private XenforoIntegration plugin;

    public NameManager(XenforoIntegration plugin) {
        this.plugin = plugin;
    }

    @Override
    public Map<String, String> onPlayerLoad(Player player, Map<String, String> editUserParams) {
        editUserParams.put("username", player.getName());
        return editUserParams;
    }
}
