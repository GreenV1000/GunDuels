package me.green.gunduels.listeners;

import me.green.gunduels.DuelManager;
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
        LivingEntity entity = (LivingEntity) event.getEntity();
        Player player = (Player) snowball.getShooter();

        if (DuelManager.getInstance().getTeam1Players().contains(player.getUniqueId()) && DuelManager.getInstance().getTeam1Players().contains(entity.getUniqueId())) {
            event.setCancelled(true);
            return;
        }
        if (DuelManager.getInstance().getTeam2Players().contains(player.getUniqueId()) && DuelManager.getInstance().getTeam2Players().contains(entity.getUniqueId())) {
            event.setCancelled(true);
            return;
        }



        entity.setNoDamageTicks(0);
        event.setDamage(8);
        entity.getWorld().playEffect(event.getEntity().getLocation(), Effect.EXPLOSION_HUGE, 1);
        Snowball sb = (Snowball) event.getDamager();
        Player shooter = (Player) sb.getShooter();
        shooter.playSound(shooter.getLocation(), Sound.ANVIL_LAND, 5, 1);
    }

}
