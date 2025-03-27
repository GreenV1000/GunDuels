package me.green.gunduels.listeners;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HitByBullet implements Listener {

    @EventHandler
    public void onProjectileHit(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() == EntityType.SNOWBALL) {
            if (e.getDamager().hasMetadata("isBullet") && e.getDamager().getMetadata("isBullet").get(0).asBoolean()) {
                e.setDamage(8);
                e.getEntity().getWorld().playEffect(e.getEntity().getLocation(), Effect.EXPLOSION_HUGE, 1);
                Snowball sb = (Snowball) e.getDamager();
                Player shooter = (Player) sb.getShooter();

                shooter.playSound(shooter.getLocation(), Sound.ANVIL_LAND, 5, 1);
            }
        }
    }

}
