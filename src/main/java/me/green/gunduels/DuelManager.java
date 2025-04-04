package me.green.gunduels;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class DuelManager {
    private final Plugin plugin = GunDuels.getInstance();
    private final FileConfiguration config = plugin.getConfig();

    private final HashMap<UUID, ItemStack[]> playerInv = new HashMap<>();
    private final HashMap<UUID, Location> playerLoc = new HashMap<>();
    private final List<UUID> team1Players = new ArrayList<>();
    private final List<UUID> Team2Players = new ArrayList<>();
    private final Location team1Spawn = new Location(plugin.getServer().getWorld(config.getString("duelWorld")), config.getDouble("team1Spawn.x"), config.getDouble("team1Spawn.y"), config.getDouble("team1Spawn.z"));
    private final Location team2Spawn = new Location(plugin.getServer().getWorld(config.getString("duelWorld")), config.getDouble("team2Spawn.x"), config.getDouble("team2Spawn.y"), config.getDouble("team2Spawn.z"));

    public void storePlayerInv(UUID playerUUID, Inventory inventory) {
        playerInv.put(playerUUID, inventory.getContents());
    }

    public void storePlayerLoc(UUID playerUUID, Location location) {
        playerLoc.put(playerUUID, location);
    }

    public void returnPlayerInv(UUID playerUUID) {
        if (!(playerInv.containsKey(playerUUID))) {
            return;
        }
        plugin.getServer().getPlayer(playerUUID).getInventory().setContents(playerInv.get(playerUUID));
        playerInv.remove(playerUUID);
    }

    public void returnPlayerLoc(UUID playerUUID) {
        Location location = playerLoc.get(playerUUID);
        if (!(playerLoc.containsKey(playerUUID))) {
            return;
        }
        if (location == null) {
            return;
        }
        plugin.getServer().getPlayer(playerUUID).teleport(location);
        playerLoc.remove(playerUUID);
    }

    public void addTeam1Player(UUID playerUUID) {
        team1Players.add(playerUUID);
    }

    public void addTeam2Player(UUID playerUUID) {
        Team2Players.add(playerUUID);
    }

    public List<UUID> getTeam1Players() {
        return team1Players;
    }

    public List<UUID> getTeam2Players() {
        return Team2Players;
    }

    public void removeDuelPlayer(UUID playerUUID) {
        team1Players.remove(playerUUID);
        Team2Players.remove(playerUUID);
    }

    public void teleportPlayer(UUID playerUUID) {
        if (team1Players.contains(playerUUID)) {
            plugin.getServer().getPlayer(playerUUID).teleport(team1Spawn);
        } else if (Team2Players.contains(playerUUID)) {
            plugin.getServer().getPlayer(playerUUID).teleport(team2Spawn);
        }
    }

    public Boolean isInDuel(UUID playerUUID) {
        return playerInv.containsKey(playerUUID);
    }

    public void joinDuel(UUID playerUUID, String team) {
        Player player = plugin.getServer().getPlayer(playerUUID);
        if (isInDuel(playerUUID)) {
            player.sendMessage(GunDuels.getPrefix() + ChatColor.RED + "You are already in a duel!");
            return;
        }
        if (team.equalsIgnoreCase("1")) {
            storePlayerLoc(playerUUID, player.getLocation());
            storePlayerInv(playerUUID, plugin.getServer().getPlayer(playerUUID).getInventory());
            player.getInventory().clear();
            addTeam1Player(playerUUID);
            teleportPlayer(playerUUID);
            player.sendMessage(GunDuels.getPrefix() + ChatColor.GREEN + "You have joined team 1!");
        }
        if (team.equalsIgnoreCase("2")) {
            storePlayerLoc(playerUUID, player.getLocation());
            storePlayerInv(playerUUID, plugin.getServer().getPlayer(playerUUID).getInventory());
            player.getInventory().clear();
            addTeam2Player(playerUUID);
            teleportPlayer(playerUUID);
            player.sendMessage(GunDuels.getPrefix() + ChatColor.GREEN + "You have joined team 2!");
        }
    }
    public void leaveDuel(UUID playerUUID) {
        Player player = plugin.getServer().getPlayer(playerUUID);
        if (playerInv.containsKey(playerUUID)) {
            returnPlayerLoc(playerUUID);
            returnPlayerInv(playerUUID);
            removeDuelPlayer(playerUUID);
            player.sendMessage(GunDuels.getPrefix() + ChatColor.GREEN + "You have left the duel!");
            return;
        }
        player.sendMessage(GunDuels.getPrefix() + ChatColor.RED + "You are not in a duel!");
    }
}


