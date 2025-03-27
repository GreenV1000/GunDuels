package me.green.gunduels.commands;

import me.green.gunduels.Gun;
import me.green.gunduels.GunRarity;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class GiveGun implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }
        if (!sender.hasPermission("gunduels.admin")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /givegun <Gun>");
            return true;
        }

        Player player = (Player) sender;
        switch (args[0].toLowerCase()) {
            case "gun":
                Gun gun = new Gun(new ItemStack(Material.IRON_BARDING), "Gun", GunRarity.COMMON, "This is a gun");
                gun.giveGun(player, gun);
                return true;
            case "hook":
                Gun hook = new Gun(new ItemStack(Material.FISHING_ROD), "Hook", GunRarity.ADMIN, "This is a hook");
                hook.giveGun(player, hook);
                return true;
            default:
                player.sendMessage(ChatColor.RED + "Usage: /givegun <Gun>");
                return true;
        }
    }
}
