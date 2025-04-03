package me.green.gunduels;

import me.green.gunduels.commands.*;
import me.green.gunduels.listeners.HitByBullet;
import me.green.gunduels.listeners.HitByHook;
import me.green.gunduels.listeners.MenuListener;
import me.green.gunduels.listeners.ShootGun;
import me.green.gunduels.recipies.GunRecipie;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

import static me.green.gunduels.Gun.registerGun;

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

    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', getInstance().getConfig().getString("prefix"));
    }
}
