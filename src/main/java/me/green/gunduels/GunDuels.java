package me.green.gunduels;

import me.green.gunduels.commands.GiveGun;
import me.green.gunduels.commands.Heal;
import me.green.gunduels.commands.TestCommand;
import me.green.gunduels.listeners.HitByBullet;
import me.green.gunduels.listeners.HitByHook;
import me.green.gunduels.listeners.ShootGun;
import org.bukkit.plugin.java.JavaPlugin;

public final class GunDuels extends JavaPlugin {

    @Override
    public void onEnable() {
        TestCommand.init(this);
        ShootGun.init(this);

        getCommand("testprojectile").setExecutor(new TestCommand());
        getCommand("heal").setExecutor(new Heal());
        getCommand("givegun").setExecutor(new GiveGun());

        getServer().getPluginManager().registerEvents(new HitByBullet(), this);
        getServer().getPluginManager().registerEvents(new ShootGun(), this);
        getServer().getPluginManager().registerEvents(new HitByHook(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
