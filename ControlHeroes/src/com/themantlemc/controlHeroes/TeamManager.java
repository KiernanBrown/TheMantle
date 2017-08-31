package com.themantlemc.controlHeroes;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TeamManager
{
	
	ControlPoint cp;
	
	String teamName;
	String displayName;
	String prefix;
	Scoreboard board;
	Team redTeam;
	Team blueTeam;
	Team greenTeam;
	Team purpleTeam;
	Objective objective;
	
	Score toWin;
	Score redDisplayScore;
	Score blueDisplayScore;
	Score greenDisplayScore;
	Score purpleDisplayScore;
	Score deco1;
	Score deco2;
	
	int winScore = 50;
	int redScore;
	int blueScore;
	int greenScore;
	int purpleScore;
	
	// Returns the wool to put on a player's head
	public ItemStack getIdentifier(Player player)
	{
		ItemStack wool = null;
		if(redTeam.hasPlayer(player))
		{
			wool = new ItemStack(Material.WOOL, 1, (byte)14);
		}
		if(blueTeam.hasPlayer(player))
		{
			wool = new ItemStack(Material.WOOL, 1, (byte)11);
		}
		if(greenTeam.hasPlayer(player))
		{
			wool = new ItemStack(Material.WOOL, 1, (byte)5);
		}
		if(purpleTeam.hasPlayer(player))
		{
			wool = new ItemStack(Material.WOOL, 1, (byte)10);
		}
		ItemMeta woolMeta = wool.getItemMeta();
		woolMeta.setDisplayName(ChatColor.GOLD + "Team Identifier");
		woolMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A block of wool that", ChatColor.AQUA + "indicates your team.", ChatColor.DARK_PURPLE + "Class Item")));
		wool.setItemMeta(woolMeta);
		return wool;
	}
	
	// Sets up the scoreboard for the game
	public void setScoreboard()
	{
		board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		
		redTeam = board.registerNewTeam(ChatColor.RED + "Red Team");
		redTeam.setPrefix(ChatColor.RED + "[R] ");
		redTeam.setAllowFriendlyFire(false);
		blueTeam = board.registerNewTeam(ChatColor.BLUE + "Blue Team");
		blueTeam.setPrefix(ChatColor.BLUE + "[B] ");
		blueTeam.setAllowFriendlyFire(false);
		greenTeam = board.registerNewTeam(ChatColor.GREEN + "Green Team");
		greenTeam.setPrefix(prefix = ChatColor.GREEN + "[G] ");
		greenTeam.setAllowFriendlyFire(false);
		purpleTeam = board.registerNewTeam(ChatColor.DARK_PURPLE + "Purple Team");
		purpleTeam.setPrefix(prefix = ChatColor.DARK_PURPLE + "[P] ");
		purpleTeam.setAllowFriendlyFire(false);
		
		objective = board.registerNewObjective("test", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "- Team Scores -");
		deco1 = objective.getScore(ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-[]-");
		deco1.setScore(999);
		toWin = objective.getScore(ChatColor.AQUA + "Points To Win:");
		toWin.setScore(winScore);
		redDisplayScore = objective.getScore(ChatColor.RED + "Red Team:");
		redDisplayScore.setScore(redScore);
		blueDisplayScore = objective.getScore(ChatColor.BLUE + "Blue Team:");
		blueDisplayScore.setScore(blueScore);
		greenDisplayScore = objective.getScore(ChatColor.GREEN + "Green Team:");
		greenDisplayScore.setScore(greenScore);
		purpleDisplayScore = objective.getScore(ChatColor.DARK_PURPLE + "Purple Team:");
		purpleDisplayScore.setScore(purpleScore);
		deco2 = objective.getScore(ChatColor.GOLD + "" + ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-[]-");
		deco2.setScore(-1);
	}
	
	// Updates the scoreboard 
	public void updateScoreboard()
	{
		objective.unregister();
		objective = board.registerNewObjective("test", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "- Team Scores -");
		
		deco1 = objective.getScore(ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-[]-");
		deco1.setScore(999);
		toWin = objective.getScore(ChatColor.AQUA + "Points To Win:");
		toWin.setScore(winScore);
		redDisplayScore = objective.getScore(ChatColor.RED + "Red Team:");
		redDisplayScore.setScore(redScore);
		blueDisplayScore = objective.getScore(ChatColor.BLUE + "Blue Team:");
		blueDisplayScore.setScore(blueScore);
		greenDisplayScore = objective.getScore(ChatColor.GREEN + "Green Team:");
		greenDisplayScore.setScore(greenScore);
		purpleDisplayScore = objective.getScore(ChatColor.DARK_PURPLE + "Purple Team:");
		purpleDisplayScore.setScore(purpleScore);
		deco2 = objective.getScore(ChatColor.GOLD + "" + ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-[]-");
		deco2.setScore(-1);
	}
	
	public TeamManager(ControlPoint control)
	{
		cp = control;
	}
}