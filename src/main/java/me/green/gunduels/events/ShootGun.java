package me.green.gunduels.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class ShootGun implements Listener {

    private static JavaPlugin plugin;

    public static void init(JavaPlugin pluginInstance) {
        plugin = pluginInstance;
    }

    @EventHandler
    public void onShootGun(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemMeta heldItem = player.getItemInHand().getItemMeta();
        if (heldItem == null) { return; }
        if (heldItem.hasLore() && heldItem.getLore().contains(ChatColor.RED + "This is a gun")) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR) {
                Snowball sb = Bukkit.getWorld(player.getWorld().getName()).spawn(player.getEyeLocation(), Snowball.class);
                sb.setShooter(player);
                sb.setMetadata("isBullet", new FixedMetadataValue(plugin, true));
                sb.setVelocity(player.getLocation().getDirection().multiply(1.5));
            }
        }
    }

}
