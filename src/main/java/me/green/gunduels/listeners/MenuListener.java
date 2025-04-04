package me.green.gunduels.listeners;

import me.green.gunduels.Gun;
import me.green.gunduels.inventories.GunInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MenuListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        if (!(event.getInventory().getName().equals(GunInventory.gunInventory().getName()))) {
            return;
        }

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (item == null || item.getType() == Material.AIR)
            return;

        for (Gun gun : GunInventory.getGunMap().values()) {
            if (!item.isSimilar(gun.getItem()))
                continue;

            gun.giveGun(player);
            player.closeInventory();
        }

        if (event.getRawSlot() == 44)
            player.closeInventory();
    }

}
