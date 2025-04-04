package me.green.gunduels.commands;

import me.green.gunduels.GunDuels;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveDuel implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;
        if (!(sender.hasPermission("gunduels.leave"))) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }
        GunDuels.getDuelManager().leaveDuel(player.getUniqueId());
        player.sendMessage(GunDuels.getPrefix() + ChatColor.GREEN + "You have left the duel!");
        return true;
    }
}
