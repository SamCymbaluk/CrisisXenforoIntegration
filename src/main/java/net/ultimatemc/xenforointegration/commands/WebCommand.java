package net.ultimatemc.xenforointegration.commands;

import net.ultimatemc.xenforointegration.XenforoIntegration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import net.ultimatemc.xenforointegration.storage.UserLoader;

public class WebCommand implements CommandExecutor {

    private XenforoIntegration plugin;
    private UserLoader userLoader;

    public WebCommand(XenforoIntegration plugin, UserLoader userLoader) {
        this.plugin = plugin;
        this.userLoader = userLoader;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        return false;
    }
}
