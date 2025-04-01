package me.green.gunduels.commands;

import me.green.gunduels.DuelManager;
import me.green.gunduels.GunDuels;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveDuel implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }
        if (!(sender.hasPermission("gunduels.leave"))) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }
        String prefix = GunDuels.getPrefix();
        DuelManager.getInstance().leaveDuel(player.getUniqueId());
        return true;
    }
}
