package me.green.gunduels.listeners;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import me.green.gunduels.DuelManager;
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

import java.sql.Time;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class HitByHook implements Listener {

    Plugin plugin = GunDuels.getInstance();
    private final Cache<UUID, Long> cooldown = CacheBuilder.newBuilder().expireAfterWrite(15 , TimeUnit.SECONDS).build();

    @EventHandler
    public void hitByHook(EntityDamageByEntityEvent event) {
        if (!(event.getDamager().getType() == EntityType.FISHING_HOOK)) {
            return;
        }

        FishHook hook = (FishHook) event.getDamager();
        Player player = (Player) hook.getShooter();
        Entity entity = event.getEntity();
        ItemStack helditem = player.getItemInHand();

        if (cooldown.asMap().containsKey(player.getUniqueId())) {
            long cooldownTime = cooldown.asMap().get(player.getUniqueId()) - System.currentTimeMillis();
            player.sendMessage(ChatColor.RED + "You are on cooldown for " + TimeUnit.MILLISECONDS.toSeconds(cooldownTime) + " seconds.");
            event.setCancelled(true);
            return;
        }

        if (!(helditem.getItemMeta().hasLore() && helditem.getItemMeta().getLore().contains(ChatColor.WHITE + "This is a hook"))) {
            return;
        }

        if (DuelManager.getInstance().getTeam1Players().contains(player.getUniqueId()) && DuelManager.getInstance().getTeam1Players().contains(entity.getUniqueId())) {
            event.setCancelled(true);
            return;
        }
        if (DuelManager.getInstance().getTeam2Players().contains(player.getUniqueId()) && DuelManager.getInstance().getTeam2Players().contains(entity.getUniqueId())) {
            event.setCancelled(true);
            return;
        }

        event.setDamage(8);
        hook.remove();
        cooldown.put(player.getUniqueId(), System.currentTimeMillis() + 15000);
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
