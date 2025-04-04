package me.green.gunduels.inventories;

import me.green.gunduels.Gun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class GunInventory {

    private static final HashMap<Integer, Gun> gunMap = new HashMap<>();

    public static Inventory gunInventory() {
        Inventory inv = Bukkit.createInventory(null, 45, ChatColor.RED.toString() + ChatColor.BOLD + "Gun Menu");

        for (Gun gun : Gun.getGuns()) {
            ItemStack gunItem = gun.getItem();
            ItemStack gunDisplayItem = new ItemStack(gunItem.getType());
            ItemMeta gunMeta = gunItem.getItemMeta();
            List<String> gunLore = gunMeta.getLore();
            gunMeta.setLore(gunLore);
            gunDisplayItem.setItemMeta(gunMeta);
            inv.addItem(gunDisplayItem);
            gunMap.put(inv.firstEmpty(), gun);
        }

        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName(ChatColor.RED + "Close");
        close.setItemMeta(closeMeta);
        inv.setItem(44, close);

        return inv;
    }
    public static HashMap<Integer, Gun> getGunMap() {
        return gunMap;
    }
}
