package me.green.gunduels.listeners;

import me.green.gunduels.DuelManager;
import me.green.gunduels.GunDuels;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathInDuel implements Listener {

    @EventHandler
    public void onDeathInDuel(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (!(GunDuels.getDuelManager().isInDuel(player.getUniqueId()))) {
            return;
        }
        event.setDeathMessage(null);
        event.setKeepInventory(true);
        event.setKeepLevel(true);
        event.setDroppedExp(0);
        event.getDrops().clear();

        player.setHealth(20);
        player.setFoodLevel(20);
        GunDuels.getDuelManager().teleportPlayer(player.getUniqueId());

    }

}
