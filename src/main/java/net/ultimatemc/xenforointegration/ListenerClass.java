package net.ultimatemc.xenforointegration;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import net.ultimatemc.xenforointegration.storage.UserLoader;

public class ListenerClass implements Listener {

    private XenforoIntegration plugin;
    private UserLoader userLoader;

    public ListenerClass(XenforoIntegration plugin, UserLoader userLoader) {
        this.plugin = plugin;
        this.userLoader = userLoader;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        userLoader.loadUser(event.getPlayer().getUniqueId().toString());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.removeUser(event.getPlayer().getUniqueId().toString());
    }


}
