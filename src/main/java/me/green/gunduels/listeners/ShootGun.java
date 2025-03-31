package me.green.gunduels.listeners;

import me.green.gunduels.GunDuels;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ShootGun implements Listener {

    @EventHandler
    public void onShootGun(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.RIGHT_CLICK_AIR)) {
            return;
        }

        Player player = event.getPlayer();
        ItemMeta heldItem = player.getItemInHand().getItemMeta();

        if (heldItem == null) {
            return;
        }

        if (!(heldItem.hasLore() || !heldItem.getLore().contains(ChatColor.WHITE + "This is a gun"))) {
            return;
        }

        Snowball sb = Bukkit.getWorld(player.getWorld().getName()).spawn(player.getEyeLocation(), Snowball.class);
        sb.setShooter(player);
        sb.setMetadata("isBullet", new FixedMetadataValue(GunDuels.getInstance(), true));
        sb.setVelocity(player.getLocation().getDirection().multiply(1.5));
    }

}
