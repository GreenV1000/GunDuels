package me.green.gunduels.listeners;

import me.green.gunduels.Gun;
import me.green.gunduels.GunRarity;
import me.green.gunduels.inventories.GunInventory;
import org.bukkit.ChatColor;
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
        for (int slot : GunInventory.getGunMap().keySet()) {
                event.setCancelled(true);
                Player player = (Player) event.getWhoClicked();
                Gun selectedGun = GunInventory.getGunFromSlot(event.getRawSlot() + 1);
                if (selectedGun != null) {
                    selectedGun.giveGun(player, selectedGun);
                    player.closeInventory();
                }
                return;
        }
        if (event.getRawSlot() == 44) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            player.closeInventory();
        }
    }

}
