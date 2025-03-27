package me.green.gunduels.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class HitByHook implements Listener {

    @EventHandler
    public void hitByHook(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() == EntityType.FISHING_HOOK) {
            FishHook hook = (FishHook) e.getDamager();
            Player player = (Player) hook.getShooter();
            Entity entity = e.getEntity();
            ItemStack helditem = player.getItemInHand();
            if (helditem.getItemMeta().hasLore() && helditem.getItemMeta().getLore().contains(ChatColor.RED + "This is a hook")) {
                e.setDamage(8);
                player.teleport(new Location(player.getWorld(), entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
                hook.remove();
            }
        }
    }
}
