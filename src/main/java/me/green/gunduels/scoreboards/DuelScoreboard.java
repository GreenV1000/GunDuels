package me.green.gunduels.scoreboards;

import me.green.gunduels.GunDuels;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class DuelScoreboard {

    public static Scoreboard duelScoreboard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = board.registerNewObjective("duelBoard", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Gun Duel");

        Score space = objective.getScore("   ");
        space.setScore(7);

        Team team1 = board.registerNewTeam("team1");
        team1.addEntry(ChatColor.RED.toString());
        team1.setPrefix(ChatColor.RED + "Team 1: ");
        team1.setSuffix(ChatColor.RED + String.valueOf(GunDuels.getDuelManager().getTeam1Players().size()));
        objective.getScore(ChatColor.RED.toString()).setScore(6);

        Team team2 = board.registerNewTeam("team2");
        team2.addEntry(ChatColor.BLUE.toString());
        team2.setPrefix(ChatColor.BLUE + "Team 2: ");
        team2.setSuffix(ChatColor.BLUE + String.valueOf(GunDuels.getDuelManager().getTeam2Players().size()));
        objective.getScore(ChatColor.BLUE.toString()).setScore(5);

        Score empty = objective.getScore(" ");
        empty.setScore(4);

        Team killsTeam = board.registerNewTeam("kills");
        killsTeam.addEntry(ChatColor.GOLD.toString());
        killsTeam.setPrefix(ChatColor.GOLD + "Kills: ");
        killsTeam.setSuffix(ChatColor.GOLD + String.valueOf(GunDuels.getDuelManager().getPlayerKills(player.getUniqueId())));
        objective.getScore(ChatColor.GOLD.toString()).setScore(3);

        Score space1 = objective.getScore("  ");
        space1.setScore(2);

        Score serverIP = objective.getScore(ChatColor.YELLOW + "Server IP: " + GunDuels.getInstance().getConfig().getString("server-ip"));
        serverIP.setScore(1);



        return board;
    }

}
