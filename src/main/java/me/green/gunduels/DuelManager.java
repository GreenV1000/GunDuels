package me.green.gunduels;

import me.green.gunduels.scoreboards.DuelScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.*;

public class DuelManager {

    private final Plugin plugin = GunDuels.getInstance();
    private final FileConfiguration config = plugin.getConfig();

    private final HashMap<UUID, ItemStack[]> playerInv = new HashMap<>();
    private final HashMap<UUID, ItemStack[]> playerArmor = new HashMap<>();
    private final HashMap<UUID, Location> playerLoc = new HashMap<>();
    private final List<UUID> Team1Players = new ArrayList<>();
    private final List<UUID> Team2Players = new ArrayList<>();
    private final Location team1Spawn = new Location(plugin.getServer().getWorld(config.getString("duelWorld")), config.getDouble("team1Spawn.x"), config.getDouble("team1Spawn.y"), config.getDouble("team1Spawn.z"));
    private final Location team2Spawn = new Location(plugin.getServer().getWorld(config.getString("duelWorld")), config.getDouble("team2Spawn.x"), config.getDouble("team2Spawn.y"), config.getDouble("team2Spawn.z"));

    private final Map<UUID, Integer> playerKills = new HashMap<>();

    public void incrementPlayerKill(UUID playerUUID) {
        playerKills.put(playerUUID, playerKills.getOrDefault(playerUUID, 0) + 1);
    }

    public int getPlayerKills(UUID playerUUID) {
        return playerKills.getOrDefault(playerUUID, 0);
    }

    public void storePlayerInv(UUID playerUUID, Inventory inventory) {
        playerInv.put(playerUUID, inventory.getContents());
        ItemStack[] armorContents = Bukkit.getPlayer(playerUUID).getInventory().getArmorContents();
        playerArmor.put(playerUUID, armorContents);
    }

    public void storePlayerLoc(UUID playerUUID, Location location) {
        playerLoc.put(playerUUID, location);
    }

    public void returnPlayerInv(UUID playerUUID) {
        if (!(playerInv.containsKey(playerUUID))) {
            return;
        }
        if (!(playerArmor.containsKey(playerUUID))) {
            return;
        }
        Bukkit.getPlayer(playerUUID).getInventory().setArmorContents(playerArmor.get(playerUUID));
        Bukkit.getPlayer(playerUUID).getInventory().setContents(playerInv.get(playerUUID));
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
        Team1Players.add(playerUUID);
    }

    public void addTeam2Player(UUID playerUUID) {
        Team2Players.add(playerUUID);
    }

    public List<UUID> getAllDuelPlayers() {
        List<UUID> allPlayers = new ArrayList<>();
        allPlayers.addAll(Team1Players);
        allPlayers.addAll(Team2Players);
        return allPlayers;
    }

    public List<UUID> getTeam1Players() {
        return Team1Players;
    }

    public List<UUID> getTeam2Players() {
        return Team2Players;
    }

    public void removeDuelPlayer(UUID playerUUID) {
        Team1Players.remove(playerUUID);
        Team2Players.remove(playerUUID);
    }

    public void teleportPlayer(UUID playerUUID) {
        if (Team1Players.contains(playerUUID)) {
            plugin.getServer().getPlayer(playerUUID).teleport(team1Spawn);
        } else if (Team2Players.contains(playerUUID)) {
            plugin.getServer().getPlayer(playerUUID).teleport(team2Spawn);
        }
    }

    public Boolean isInDuel(UUID playerUUID) {
        return playerInv.containsKey(playerUUID);
    }

    public void joinDuel(UUID playerUUID, String team) {
        Player player = Bukkit.getPlayer(playerUUID);
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
            DuelScoreboard.duelScoreboard(player).getTeam("team1").setSuffix(ChatColor.RED + String.valueOf(Team1Players.size()));
        }
        if (team.equalsIgnoreCase("2")) {
            storePlayerLoc(playerUUID, player.getLocation());
            storePlayerInv(playerUUID, plugin.getServer().getPlayer(playerUUID).getInventory());
            player.getInventory().clear();
            addTeam2Player(playerUUID);
            teleportPlayer(playerUUID);
            player.sendMessage(GunDuels.getPrefix() + ChatColor.GREEN + "You have joined team 2!");
            DuelScoreboard.duelScoreboard(player).getTeam("team2").setSuffix(ChatColor.BLUE + String.valueOf(Team2Players.size()));
        }
        Gun gun = Gun.getGun("gun");
        Gun hook = Gun.getGun("hook");
        ItemStack swordItem = new ItemStack(Material.IRON_SWORD);
        ItemStack helmetItem = new ItemStack(Material.IRON_HELMET);
        ItemStack chestplateItem = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack leggingsItem = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        ItemStack bootsItem = new ItemStack(Material.LEATHER_BOOTS);
        player.getInventory().addItem(swordItem);
        player.getInventory().setHelmet(helmetItem);
        player.getInventory().setChestplate(chestplateItem);
        player.getInventory().setLeggings(leggingsItem);
        player.getInventory().setBoots(bootsItem);

        gun.giveGun(player);
        hook.giveGun(player);
        playerKills.put(playerUUID, 0);
        player.setScoreboard(DuelScoreboard.duelScoreboard(player));

    }
    public void leaveDuel(UUID playerUUID) {
        Player player = plugin.getServer().getPlayer(playerUUID);
        if (!playerInv.containsKey(playerUUID)) {
            player.sendMessage(GunDuels.getPrefix() + ChatColor.RED + "You are not in a duel!");
            return;
        }
        getAllDuelPlayers().forEach(uuid -> {
            Player player1 = Bukkit.getPlayer(uuid);
            if (player1 == null) {
                return;
            }
            if (Team1Players.contains(playerUUID)) {
                player1.getScoreboard().getTeam("team1").setSuffix(ChatColor.RED + String.valueOf(Team1Players.size() - 1));
            } else if (Team2Players.contains(playerUUID)) {
                player1.getScoreboard().getTeam("team2").setSuffix(ChatColor.BLUE + String.valueOf(Team2Players.size() - 1));
            }
            player1.sendMessage(GunDuels.getPrefix() + ChatColor.LIGHT_PURPLE + player.getName() + " has left the duel!");
        });
        returnPlayerLoc(playerUUID);
        returnPlayerInv(playerUUID);
        removeDuelPlayer(playerUUID);
        player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        player.sendMessage(GunDuels.getPrefix() + ChatColor.GREEN + "You have left the duel!");
    }
}


