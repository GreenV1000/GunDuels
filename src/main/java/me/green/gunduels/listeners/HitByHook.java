package me.green.gunduels.listeners;

import me.green.gunduels.GunDuels;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class HitByHook implements Listener {

    Plugin plugin = GunDuels.getInstance();

    @EventHandler
    public void hitByHook(EntityDamageByEntityEvent event) {
        if (!(event.getDamager().getType() == EntityType.FISHING_HOOK)) {
            return;
        }

        FishHook hook = (FishHook) event.getDamager();
        Player player = (Player) hook.getShooter();
        Entity entity = event.getEntity();
        ItemStack helditem = player.getItemInHand();

        if (!(helditem.getItemMeta().hasLore() && helditem.getItemMeta().getLore().contains(ChatColor.WHITE + "This is a hook"))) {
            return;
        }

        event.setDamage(8);
        hook.remove();


        player.sendMessage(ChatColor.GREEN + "Teleporting to " + entity.getName() + " in 3 seconds...");
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            player.sendMessage(ChatColor.GREEN + "Teleporting to " + entity.getName() + " in 2 seconds...");
        }, 20);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            player.sendMessage(ChatColor.GREEN + "Teleporting to " + entity.getName() + " in 1 second...");
        }, 40);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            player.sendMessage(ChatColor.GREEN + "Teleporting to " + entity.getName());
            player.teleport(new Location(player.getWorld(), entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
        }, 60);


    }
}
