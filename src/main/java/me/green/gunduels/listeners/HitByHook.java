package me.green.gunduels.listeners;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import me.green.gunduels.GunDuels;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class HitByHook implements Listener {

    private final GunDuels plugin;

    public HitByHook(GunDuels plugin) {
        this.plugin = plugin;
    }

    private final Cache<UUID, Long> cooldown = CacheBuilder.newBuilder().expireAfterWrite(15 , TimeUnit.SECONDS).build();

    @EventHandler
    public void hitByHook(EntityDamageByEntityEvent event) {

        FishHook hook = (FishHook) event.getDamager();
        Player player = (Player) hook.getShooter();
        Entity entity = event.getEntity();
        ItemStack helditem = player.getItemInHand();

        if (GunDuels.getDuelManager().getTeam1Players().contains(player.getUniqueId()) && GunDuels.getDuelManager().getTeam1Players().contains(entity.getUniqueId())) {
            event.setCancelled(true);
            return;
        }
        if (GunDuels.getDuelManager().getTeam2Players().contains(player.getUniqueId()) && GunDuels.getDuelManager().getTeam2Players().contains(entity.getUniqueId())) {
            event.setCancelled(true);
            return;
        }

        if (!(event.getDamager().getType() == EntityType.FISHING_HOOK)) {
            return;
        }

        if (cooldown.asMap().containsKey(player.getUniqueId())) {
            long cooldownTime = cooldown.asMap().get(player.getUniqueId()) - System.currentTimeMillis();
            player.sendMessage(ChatColor.RED + "You are on cooldown for " + TimeUnit.MILLISECONDS.toSeconds(cooldownTime) + " seconds.");
            event.setCancelled(true);
            return;
        }

        if (!(helditem.getItemMeta().hasLore() && helditem.getItemMeta().getLore().contains(ChatColor.WHITE + "This is a hook"))) {
            return;
        }

        event.setDamage(8);
        hook.remove();
        cooldown.put(player.getUniqueId(), System.currentTimeMillis() + 15000);

        BukkitTask runnable = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            int countdown = 3;

            @Override
            public void run() {
                if (countdown <= 0) {
                    player.sendMessage(ChatColor.GREEN + "Teleporting to " + entity.getName());
                    player.teleport(new Location(player.getWorld(), entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
                    return;
                }
                    player.sendMessage(ChatColor.GREEN + "Teleporting to " + entity.getName() + " in " + countdown + " seconds...");
                    countdown--;
                }
        },0, 20);
        Bukkit.getScheduler().runTaskLater(plugin, runnable::cancel, 60);
    }
}
