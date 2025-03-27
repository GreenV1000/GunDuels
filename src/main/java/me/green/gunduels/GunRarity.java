package me.green.gunduels;

import org.bukkit.ChatColor;

public enum GunRarity {
    COMMON(ChatColor.WHITE),
    UNCOMMON(ChatColor.GREEN),
    RARE(ChatColor.BLUE),
    EPIC(ChatColor.DARK_PURPLE),
    LEGENDARY(ChatColor.GOLD),
    ADMIN(ChatColor.DARK_RED);

    private ChatColor color;

    GunRarity(ChatColor color) {
        this.color = color;
    }
    public ChatColor getColor() {
        return color;
    }
}
