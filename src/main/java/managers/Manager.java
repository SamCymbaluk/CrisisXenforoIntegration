package managers;

import org.bukkit.entity.Player;

import java.util.Map;

public interface Manager {

    public Map<String, String> onPlayerLoad(Player player, Map<String, String> editUserParams);
}
