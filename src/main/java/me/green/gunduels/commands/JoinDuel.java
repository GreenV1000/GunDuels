package me.green.gunduels.commands;

import me.green.gunduels.GunDuels;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class JoinDuel implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("gunduels.join")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        UUID playerId = player.getUniqueId();

        if (args.length < 1) {
            player.sendMessage(GunDuels.getPrefix() + ChatColor.RED + "Usage: /joinduel <team>");
            return true;
        }
        switch (args[0]) {
            case "1":
                GunDuels.getDuelManager().joinDuel(playerId, args[0]);
                break;
            case "2":
                GunDuels.getDuelManager().joinDuel(playerId, args[0]);
                break;
            default:
                player.sendMessage(GunDuels.getPrefix() + ChatColor.RED + "Usage: /joinduel <team>");
                return true;
        }

        return true;
    }
}
