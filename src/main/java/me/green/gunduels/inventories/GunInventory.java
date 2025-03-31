package me.green.gunduels.inventories;

import me.green.gunduels.Gun;
import me.green.gunduels.GunRarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GunInventory {

    public static Inventory gunInventory() {
        Gun gun = new Gun(new ItemStack(Material.IRON_BARDING), "Gun", GunRarity.COMMON, "This is a gun");
        Gun hook = new Gun(new ItemStack(Material.FISHING_ROD), "Hook", GunRarity.ADMIN, "This is a hook");

        Inventory inv = Bukkit.createInventory(null, 45, ChatColor.RED.toString() + ChatColor.BOLD + "Gun Menu");

        ItemStack gunItem = gun.getItem();
        ItemMeta gunMeta = gunItem.getItemMeta();
        List<String> gunLore = gunMeta.getLore();
        gunLore.add("");
        gunLore.add(ChatColor.GOLD + "Click to receive this gun");
        gunMeta.setLore(gunLore);
        gunItem.setItemMeta(gunMeta);
        inv.setItem(0, gunItem);

        ItemStack hookItem = hook.getItem();
        ItemMeta hookMeta = hookItem.getItemMeta();
        List<String> hookLore = hookMeta.getLore();
        hookLore.add("");
        hookLore.add(ChatColor.GOLD + "Click to receive this gun");
        hookMeta.setLore(hookLore);
        hookItem.setItemMeta(hookMeta);
        inv.setItem(1, hookItem);

        // I know I should probably use a loop for this Alfie thanks for letting me know

        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName(ChatColor.RED + "Close");
        close.setItemMeta(closeMeta);
        inv.setItem(44, close);

        return inv;
    }

}
