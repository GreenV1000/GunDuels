package me.green.gunduels.listeners;

import me.green.gunduels.GunDuels;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HitInDuel implements Listener {

    @EventHandler
    public void hitInDuel(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getDamager();
        Entity entity = event.getEntity();

        if (GunDuels.getDuelManager().getTeam1Players().contains(player.getUniqueId()) && GunDuels.getDuelManager().getTeam1Players().contains(entity.getUniqueId())) {
            event.setCancelled(true);
            return;
        }
        if (GunDuels.getDuelManager().getTeam2Players().contains(player.getUniqueId()) && GunDuels.getDuelManager().getTeam2Players().contains(entity.getUniqueId())) {
            event.setCancelled(true);
        }
    }

}
