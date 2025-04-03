package me.green.gunduels.listeners;

import me.green.gunduels.DuelManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathInDuel implements Listener {

    @EventHandler
    public void onDeathInDuel(PlayerDeathEvent event) {
        Player player = (Player) event.getEntity();
        if (!(DuelManager.getInstance().isInDuel(player.getUniqueId()))) {
            return;
        }
        event.setDeathMessage(null);
        event.setKeepInventory(true);
        event.setKeepLevel(true);
        event.setDroppedExp(0);
        event.getDrops().clear();

        player.setHealth(20);
        player.setFoodLevel(20);
        DuelManager.getInstance().teleportPlayer(player.getUniqueId());

    }

}
