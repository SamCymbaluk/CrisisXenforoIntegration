package commands;

import com.google.gson.JsonObject;
import net.ultimatemc.xenforointegration.ListenerClass;
import net.ultimatemc.xenforointegration.XenforoIntegration;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import storage.UserLoader;
import util.InputUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RegisterCommand implements CommandExecutor {


    private XenforoIntegration plugin;
    private UserLoader userLoader;

    public RegisterCommand(XenforoIntegration plugin, UserLoader userLoader) {
        this.plugin = plugin;
        this.userLoader = userLoader;
        new ListenerClass(plugin, userLoader);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        System.out.println(args.length);
        if (args.length == 1) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "This command must be executed by a player.");
                return false;
            }
            String email = args[0];
            System.out.println(email);
            if (InputUtil.isEmail(email)) {
                Player p = (Player) sender;
                if (plugin.containsUser(p.getUniqueId().toString())) {
                    p.sendMessage(ChatColor.RED + "You have already registered.");
                    return false;
                }
                register(p, email);
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + email + " is an invalid email");
            }
        }
        return false;
    }

    public void register(Player player, String email) {
        String password = generatePassword();
        Map<String, String> params = new HashMap<>();
        params.put("username", player.getName());
        params.put("password", password);
        params.put("email", InputUtil.sanitizeInput(email));

        JsonObject response = plugin.xenApi.executeQuery("register", params);

        if (response != null && !response.toString().equals("null") && response.get("error") == null) {
            userLoader.insert(player.getUniqueId().toString(), Integer.parseInt(response.get("user_id").toString()));
            player.sendMessage(net.md_5.bungee.api.ChatColor.GOLD + "Congratulations! You are now registered on the UltimateMC forums!");
            player.sendMessage(net.md_5.bungee.api.ChatColor.GOLD + "You can use the the following credentials to login:");
            player.sendMessage(net.md_5.bungee.api.ChatColor.GOLD + "Username: " + net.md_5.bungee.api.ChatColor.BLUE + player.getName() + net.md_5.bungee.api.ChatColor.GOLD + " Password: " + net.md_5.bungee.api.ChatColor.BLUE + password);
        } else {
            player.sendMessage(net.md_5.bungee.api.ChatColor.RED + "You were unable to register. Perhaps that email is already taken.");
        }
    }

    private String generatePassword() {
        Random r = new Random();
        String password = "";

        for (int i = 0; i < 8; i++) {
            char c = (char) (r.nextBoolean() ? r.nextInt(26) + 65 : r.nextInt(26) + 97);
            password = password + c;
        }

        return password;
    }

}