package me.green.gunduels;

import me.green.gunduels.commands.*;
import me.green.gunduels.listeners.*;
import me.green.gunduels.recipes.GunRecipe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import static me.green.gunduels.Gun.registerGun;

public final class GunDuels extends JavaPlugin {

    private static GunDuels instance;
    private static DuelManager duelManager;

    @Override
    public void onEnable() {
        instance = this;
        duelManager = new DuelManager();

        saveDefaultConfig();

        getCommand("heal").setExecutor(new Heal());
        getCommand("givegun").setExecutor(new GiveGun());
        getCommand("joinduel").setExecutor(new JoinDuel());
        getCommand("leaveduel").setExecutor(new LeaveDuel());

        getServer().getPluginManager().registerEvents(new HitByBullet(), this);
        getServer().getPluginManager().registerEvents(new ShootGun(this), this);
        getServer().getPluginManager().registerEvents(new HitByHook(this), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new DeathInDuel(), this);

        Bukkit.addRecipe(GunRecipe.gunRecipe());

        registerGun("gun", new Gun(new ItemStack(Material.IRON_BARDING), "Gun", GunRarity.COMMON, "This is a gun"));
        registerGun("hook", new Gun(new ItemStack(Material.FISHING_ROD), "Hook", GunRarity.ADMIN, "This is a hook"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static GunDuels getInstance() {
        return instance;
    }

    public static DuelManager getDuelManager() {
        return duelManager;
    }

    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', getInstance().getConfig().getString("prefix"));
    }
}
