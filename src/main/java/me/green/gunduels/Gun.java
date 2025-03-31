package me.green.gunduels;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Gun {

    private final ItemStack item;
    private final String name;
    private final GunRarity rarity;
    private final String description;

    public Gun(ItemStack item, String name, GunRarity rarity, String description) {
        this.item = item;
        this.name = name;
        this.rarity = rarity;
        this.description = description;

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.WHITE + description);
        lore.add("");
        lore.add(rarity.getColor() + "§l" + rarity);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(rarity.getColor() + "§l" + name);
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public void giveGun(Player player, Gun gun) {
        player.getInventory().addItem(gun.item);
        player.sendMessage(ChatColor.GREEN + "You have been given a " + rarity.getColor() + ChatColor.BOLD + gun.name);
    }

    public ItemStack getItem() {
        return item;
    }

}
