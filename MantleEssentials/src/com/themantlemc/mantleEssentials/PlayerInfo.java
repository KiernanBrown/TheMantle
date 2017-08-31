package com.themantlemc.mantleEssentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerInfo {

	String name;
	String rank = "Player";
	int mantlecoins = 0;
	
	Player lastMessaged = null;
	
	Playlist playlist;
	String defaultPlaylist = "";
	boolean shuffle = false;
	
	Scoreboard board;
	Team playerTeam;
	Team spark;
	Team ember;
	Team flame;
	Team vip;
	Team builder;
	Team moderator;
	Team admin;
	Team owner;
	Objective objective;
	
	Score deco1;
	Score rank1;
	Score rank2;
	Score coins1;
	Score coins2;
	
	// Sets up the scoreboard for the game
	public void setScoreboard(MantleEssentials mantle)
	{
		board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		
		playerTeam = board.registerNewTeam(ChatColor.GRAY + "Player");
		playerTeam.setPrefix(ChatColor.GRAY.toString());
		spark = board.registerNewTeam(ChatColor.YELLOW + "Spark");
		spark.setPrefix(ChatColor.YELLOW.toString());
		ember = board.registerNewTeam(ChatColor.GOLD + "Ember");
		ember.setPrefix(ChatColor.GOLD.toString());
		flame = board.registerNewTeam(ChatColor.RED + "Flame");
		flame.setPrefix(ChatColor.RED.toString());
		vip = board.registerNewTeam(ChatColor.LIGHT_PURPLE + "VIP");
		vip.setPrefix(ChatColor.LIGHT_PURPLE.toString());
		builder = board.registerNewTeam(ChatColor.DARK_GREEN + "Builder");
		builder.setPrefix(ChatColor.DARK_GREEN.toString());
		moderator = board.registerNewTeam(ChatColor.BLUE + "Moderator");
		moderator.setPrefix(ChatColor.BLUE.toString());
		admin = board.registerNewTeam(ChatColor.DARK_AQUA + "Admin");
		admin.setPrefix(ChatColor.DARK_AQUA.toString());
		owner = board.registerNewTeam(ChatColor.DARK_PURPLE + "Owner");
		owner.setPrefix(ChatColor.DARK_PURPLE.toString());
	}
	
	public void setObjectives(MantleEssentials mantle)
	{
		objective = board.registerNewObjective("test", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "- The Mantle -");

		rank1 = objective.getScore(ChatColor.GOLD + "Rank");
		rank1.setScore(4);
		rank2 = objective.getScore(rank);
		rank2.setScore(3);
		deco1 = objective.getScore("");
		deco1.setScore(2);
		coins1 = objective.getScore(ChatColor.GOLD + "Mantle Coins");
		coins1.setScore(1);
		coins2 = objective.getScore("" + mantlecoins);
		coins2.setScore(0);
	}
	
	// Updates the scoreboard 
	public void updateScoreboard(MantleEssentials mantle)
	{
		for(OfflinePlayer p : mantle.playerTeam.getPlayers())
		{
			playerTeam.addPlayer(p);
		}
		for(OfflinePlayer p : mantle.spark.getPlayers())
		{
			spark.addPlayer(p);
		}
		for(OfflinePlayer p : mantle.ember.getPlayers())
		{
			ember.addPlayer(p);
		}
		for(OfflinePlayer p : mantle.flame.getPlayers())
		{
			flame.addPlayer(p);
		}
		for(OfflinePlayer p : mantle.vip.getPlayers())
		{
			vip.addPlayer(p);
		}
		for(OfflinePlayer p : mantle.builder.getPlayers())
		{
			builder.addPlayer(p);
		}
		for(OfflinePlayer p : mantle.moderator.getPlayers())
		{
			moderator.addPlayer(p);
		}
		for(OfflinePlayer p : mantle.admin.getPlayers())
		{
			admin.addPlayer(p);
		}
		for(OfflinePlayer p : mantle.owner.getPlayers())
		{
			owner.addPlayer(p);
		}
	}
	
	public void updateObjectives(MantleEssentials mantle)
	{
		objective.unregister();
		objective = board.registerNewObjective("test", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "- The Mantle -");

		rank1 = objective.getScore(ChatColor.GOLD + "Rank");
		rank1.setScore(4);
		rank2 = objective.getScore(rank);
		rank2.setScore(3);
		deco1 = objective.getScore("");
		deco1.setScore(2);
		coins1 = objective.getScore(ChatColor.GOLD + "Mantle Coins");
		coins1.setScore(1);
		coins2 = objective.getScore("" + mantlecoins);
		coins2.setScore(0);
	}
	
	public PlayerInfo(String pName)
	{
		name = pName;
	}
}