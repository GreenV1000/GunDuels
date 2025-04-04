package me.green.gunduels.listeners;

import me.green.gunduels.GunDuels;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HitByBullet implements Listener {

    @EventHandler
    public void onProjectileHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager().getType() == EntityType.SNOWBALL)) {
            return;
        }
        if (!(event.getDamager().hasMetadata("isBullet") && event.getDamager().getMetadata("isBullet").get(0).asBoolean())) {
            return;
        }

        Snowball snowball = (Snowball) event.getDamager();
        LivingEntity hitEntity = (LivingEntity) event.getEntity();
        Player player = (Player) snowball.getShooter();

        if (GunDuels.getDuelManager().getTeam1Players().contains(player.getUniqueId()) && GunDuels.getDuelManager().getTeam1Players().contains(hitEntity.getUniqueId())) {
            event.setCancelled(true);
            return;
        }
        if (GunDuels.getDuelManager().getTeam2Players().contains(player.getUniqueId()) && GunDuels.getDuelManager().getTeam2Players().contains(hitEntity.getUniqueId())) {
            event.setCancelled(true);
            return;
        }



        hitEntity.setNoDamageTicks(0);
        event.setDamage(8);
        hitEntity.getWorld().playEffect(event.getEntity().getLocation(), Effect.EXPLOSION_HUGE, 1);
        player.playSound(player.getLocation(), Sound.ANVIL_LAND, 5, 1);
    }

}
