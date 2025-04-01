package me.green.gunduels;

import me.green.gunduels.commands.GiveGun;
import me.green.gunduels.commands.Heal;
import me.green.gunduels.commands.JoinDuel;
import me.green.gunduels.commands.LeaveDuel;
import me.green.gunduels.listeners.HitByBullet;
import me.green.gunduels.listeners.HitByHook;
import me.green.gunduels.listeners.MenuListener;
import me.green.gunduels.listeners.ShootGun;
import me.green.gunduels.recipies.GunRecipie;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class GunDuels extends JavaPlugin {

    private static GunDuels instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();


        getCommand("heal").setExecutor(new Heal());
        getCommand("givegun").setExecutor(new GiveGun());
        getCommand("joinduel").setExecutor(new JoinDuel());
        getCommand("leaveduel").setExecutor(new LeaveDuel());

        getServer().getPluginManager().registerEvents(new HitByBullet(), this);
        getServer().getPluginManager().registerEvents(new ShootGun(), this);
        getServer().getPluginManager().registerEvents(new HitByHook(), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);

        Bukkit.addRecipe(GunRecipie.gunRecipie());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static GunDuels getInstance() {
        return instance;
    }

    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', getInstance().getConfig().getString("prefix"));
    }
}
