package me.green.gunduels.commands;

import me.green.gunduels.DuelManager;
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

        DuelManager duelManager = DuelManager.getInstance();
        UUID playerId = player.getUniqueId();

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Usage: /joinduel <team>");
            return true;
        }
        switch (args[0]) {
            case "1":
                duelManager.joinDuel(playerId, args[0]);
                break;
            case "2":
                duelManager.joinDuel(playerId, args[0]);
                break;
            default:
                player.sendMessage(ChatColor.RED + "Usage: /joinduel <team>");
                return true;
        }

        return true;
    }
}
