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

    Gun gun = new Gun(new ItemStack(Material.IRON_BARDING), "Gun", GunRarity.COMMON, "This is a gun");
    Gun hook = new Gun(new ItemStack(Material.FISHING_ROD), "Hook", GunRarity.ADMIN, "This is a hook");

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        if (!(event.getInventory().getName().equals(GunInventory.gunInventory().getName()))) {
            return;
        }
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            switch (event.getRawSlot()) {
                case 0:
                    gun.giveGun(player, gun);
                    break;
                case 1:
                    hook.giveGun(player, hook);
                    break;
                case 44:
                    break;
                default:
                    return;
            }
            player.closeInventory();
    }

}
