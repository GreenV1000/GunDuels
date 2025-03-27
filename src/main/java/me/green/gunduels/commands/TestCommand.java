package me.green.gunduels.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class TestCommand implements CommandExecutor {

    private static JavaPlugin plugin;

    public static void init(JavaPlugin pluginInstance) {
        plugin = pluginInstance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("testprojectile")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Only players can use this command!");
                return true;
            }
            if (!sender.hasPermission("gunduels.admin")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                return true;
            }

            Player player = (Player) sender;
            Snowball sb = Bukkit.getWorld(player.getWorld().getName()).spawn(player.getEyeLocation(), Snowball.class);
            sb.setShooter(player);
            sb.setMetadata("isBullet", new FixedMetadataValue(plugin, true));
            sb.setVelocity(player.getLocation().getDirection().multiply(1.5));
            return true;
        }

        return false;
    }
}
