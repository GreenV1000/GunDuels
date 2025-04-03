package me.green.gunduels.commands;

import me.green.gunduels.Gun;
import me.green.gunduels.GunRarity;
import me.green.gunduels.inventories.GunInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;


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

        Gun gun = Gun.getGun("gun");
        Gun hook = Gun.getGun("hook");

        if (args.length < 1) {
            player.openInventory(GunInventory.gunInventory());
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "gun":
                gun.giveGun(player);
                return true;
            case "hook":
                hook.giveGun(player);
                return true;
            default:
                player.sendMessage(ChatColor.RED + "Usage: /givegun <Gun>");
                return true;
        }
    }
}
