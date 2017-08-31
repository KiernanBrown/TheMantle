package com.themantlemc.controlHeroes;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Team;

public class End implements CommandExecutor {
	
	ControlPoint cp;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(sender instanceof Player)
		{
			Player p = (Player)sender;
			if(!p.isOp())
			{
				return true;
			}
		}
		for(Player player : Bukkit.getOnlinePlayers())
		{	
			player.getInventory().clear();
			player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			player.teleport(cp.lobby.getSpawnLocation());
			if(cp.winningTeam.hasPlayer(player))
			{
				cp.giveXP(player, "Win");
				cp.getPlayerInfo(player.getName()).wins++;
			}
			
			for(PotionEffect pE : player.getActivePotionEffects())
			{
				player.removePotionEffect(pE.getType());
			}
		}
		
		// Announce the winning team
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-[]-");
		Bukkit.broadcastMessage(ChatColor.GOLD + "The winner is: " + cp.winningTeam.getName() + "!");
		Bukkit.broadcastMessage(ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-[]-");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("");
		
		cp.gameRunning = false;
		cp.gameEnding = true;
		
		// Display leaderboard
		cp.scheduler.scheduleSyncDelayedTask(cp, new Runnable()
		{
			@Override
			public void run() {
				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage(ChatColor.AQUA + "-[]-[]-[]-[]-[]-[] " + ChatColor.GOLD + " Scoreboard " + ChatColor.AQUA + " []-[]-[]-[]-[]-[]-");
				
				// Most Kills
				int mostKills = -1;
				Player killsPlayer = null;
				for(Player player : Bukkit.getOnlinePlayers())
				{
					PlayerInfo info = cp.getPlayerInfo(player.getName());
					if(info.kills > mostKills)
					{
						mostKills = info.kills;
						killsPlayer = player;
					}
				}
				Bukkit.broadcastMessage(ChatColor.GOLD + "Most Kills: " + ChatColor.AQUA + killsPlayer.getName() + ChatColor.GOLD + " with " + mostKills + " kills!");
				cp.giveCoins(killsPlayer, "Leader");
				
				// Most Captures
				int mostCaptures = -1;
				Player capturesPlayer = null;
				for(Player player : Bukkit.getOnlinePlayers())
				{
					PlayerInfo info = cp.getPlayerInfo(player.getName());
					if(info.captures > mostCaptures)
					{
						mostCaptures = info.captures;
						capturesPlayer = player;
					}
				}
				Bukkit.broadcastMessage(ChatColor.GOLD + "Most Captures: " + ChatColor.AQUA + capturesPlayer.getName() + ChatColor.GOLD + " with " + mostCaptures + " captures!");
				cp.giveCoins(capturesPlayer, "Leader");
				
				// Highest Killstreak
				int highKillstreak = -1;
				Player killstreakPlayer = null;
				for(Player player : Bukkit.getOnlinePlayers())
				{
					PlayerInfo info = cp.getPlayerInfo(player.getName());
					if(info.highestStreak > highKillstreak)
					{
						highKillstreak = info.highestStreak;
						killstreakPlayer = player;
					}
				}
				Bukkit.broadcastMessage(ChatColor.GOLD + "Highest Killstreak: " +  ChatColor.AQUA + killstreakPlayer.getName() + ChatColor.GOLD + " with a " + highKillstreak + " killstreak!");
				cp.giveCoins(killstreakPlayer, "Leader");
				
				// Highest KD Ratio
				float highKD = -1;
				String kdStr = "";
				Player kdPlayer = null;
				for(Player player : Bukkit.getOnlinePlayers())
				{
					PlayerInfo info = cp.getPlayerInfo(player.getName());
					if(info.deaths == 0)
					{
						kdStr = "Infinite";
						kdPlayer = player;
						break;
					}

					float kd = (float)info.kills/ (float)info.deaths;
					if(kd > highKD)
					{
						highKD = kd;
						kdPlayer = player;
					}
				}
				if(!kdStr.equals("Infinite"))
				{
					NumberFormat df = DecimalFormat.getInstance();
					df.setMinimumFractionDigits(2);
					df.setRoundingMode(RoundingMode.DOWN);

					kdStr = df.format(highKD);
				}
				Bukkit.broadcastMessage(ChatColor.GOLD + "Highest K/D Ratio: " + ChatColor.AQUA + kdPlayer.getName() + ChatColor.GOLD + " with a " + kdStr + " K/D ratio!");
				cp.giveCoins(kdPlayer, "Leader");
				
				Bukkit.broadcastMessage(ChatColor.AQUA + "-[]-[]-[]-[]-[]-[]-[]-[]-[]-[]-[]-[]-[]-[]-[]-[]-[]-");
				Bukkit.broadcastMessage("");
			}
		}, 100L);
		
		// Add Coins
		cp.scheduler.scheduleSyncDelayedTask(cp, new Runnable()
		{
			@Override
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers())
				{
					cp.endCoins(player);
				}
			}
		}, 200L);
		
		// Move players to lobby after 15 seconds
		cp.scheduler.scheduleSyncDelayedTask(cp, new Runnable()
		{
			@Override
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers())
				{
					player.sendMessage(ChatColor.GOLD + "Sending you to lobby.");
					cp.sendToServer(player, "lobby");
				}
			}
		}, 300L);

		// Kick any remaining players, if for some reason they were not moved, after 18 seconds
		cp.scheduler.scheduleSyncDelayedTask(cp, new Runnable()
		{
			@Override
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers())
				{
					player.kickPlayer(ChatColor.RED + "Server Restarting!");
				}
			}
		}, 360L);
		
		// Shutdown the server, and it will automatically restart
		cp.scheduler.scheduleSyncDelayedTask(cp, new Runnable()
		{
			@Override
			public void run() {
				Bukkit.shutdown();
			}
		}, 400L);
		
		return true;
	}
	
	public End(ControlPoint control)
	{
		cp = control;
	}
	
}
