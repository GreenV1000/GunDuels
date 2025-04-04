package me.green.gunduels.commands;

import me.green.gunduels.inventories.GunInventory;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class GiveGun implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;
        if (!sender.hasPermission("gunduels.admin")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }
        player.openInventory(GunInventory.gunInventory());
        return true;
    }
}
