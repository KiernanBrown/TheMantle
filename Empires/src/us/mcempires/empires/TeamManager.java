package us.mcempires.empires;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.event.Listener;

import net.md_5.bungee.api.ChatColor;

public class TeamManager
{
	
	Empires empires;
	
	String teamName;
	String displayName;
	String prefix;
	Scoreboard board;
	Team team;
	Team other1;
	Team other2;
	Team other3;
	Objective objective;
	
	Score empireScore;
	Score woodScore;
	Score bricksScore;
	Score foodScore;
	Score goldScore;
	Score ironScore;
	Score deco1;
	Score deco2;
	
	int empirePoints = 0;
	int wood = 0;
	int bricks = 0;
	int food = 0;
	int gold = 0;
	int iron = 0;
	int level = 1;
	int resourceCost = 10;
	
	// End game points
	int endPoints = 0;
	int killPoints = 0;
	int levelPoints = 0;
	int collectorPoints = 0;
	
	public ItemStack getIdentifier()
	{
		ItemStack wool = null;
		switch(ChatColor.stripColor(teamName))
		{
		case "Red Team": wool = new ItemStack(Material.WOOL, 1, (byte)14);
			break;
		case "Blue Team": wool = new ItemStack(Material.WOOL, 1, (byte)11);
			break;
		case "Green Team": wool = new ItemStack(Material.WOOL, 1, (byte)13);
			break;
		case "Purple Team": wool = new ItemStack(Material.WOOL, 1, (byte)10);
			break;
		}
		ItemMeta woolMeta = wool.getItemMeta();
		woolMeta.setDisplayName(ChatColor.GOLD + "Team Identifier");
		woolMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A block of wool that", ChatColor.AQUA + "indicates your team.", ChatColor.LIGHT_PURPLE + "Class Item")));
		wool.setItemMeta(woolMeta);
		return wool;
	}
	
	public void setScoreboard()
	{
		board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		team = board.registerNewTeam(teamName);
		objective = board.registerNewObjective("test", "dummy");
			
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(displayName);
		
		empireScore = objective.getScore(ChatColor.GOLD + "§lEmpire Points: " + ChatColor.WHITE + "" + empirePoints);
		empireScore.setScore(5);
		woodScore = objective.getScore(ChatColor.DARK_GREEN + "Wood: " + ChatColor.WHITE + "" + wood);
		woodScore.setScore(4);
		bricksScore = objective.getScore(ChatColor.GRAY + "Bricks: " + ChatColor.WHITE + "" + bricks);
		bricksScore.setScore(3);
		foodScore = objective.getScore(ChatColor.RED + "Food: " + ChatColor.WHITE + "" + food);
		foodScore.setScore(2);
		goldScore = objective.getScore(ChatColor.YELLOW + "Gold: " + ChatColor.WHITE + "" + gold);
		goldScore.setScore(1);
		ironScore = objective.getScore(ChatColor.DARK_GRAY + "Iron: " + ChatColor.WHITE + "" + iron);
		ironScore.setScore(0);
	}
	
	// Sets up prefixes for other teams, as each team has a different scoreboard
	public void setOtherTeams()
	{
		switch(ChatColor.stripColor(teamName))
		{
			case "Red Team": 
				other1 = board.registerNewTeam(empires.blueTeam.teamName);
				for(OfflinePlayer p : empires.blueTeam.team.getPlayers())
				{
					other1.addPlayer((Player)p);
				}
				other1.setPrefix(empires.blueTeam.prefix);
				other2 = board.registerNewTeam(empires.greenTeam.teamName);
				for(OfflinePlayer p : empires.greenTeam.team.getPlayers())
				{
					other2.addPlayer((Player)p);
				}
				other2.setPrefix(empires.greenTeam.prefix);
				other3 = board.registerNewTeam(empires.purpleTeam.teamName);
				for(OfflinePlayer p : empires.purpleTeam.team.getPlayers())
				{
					other3.addPlayer((Player)p);
				}
				other3.setPrefix(empires.purpleTeam.prefix);
			break;
			case "Blue Team": 
				other1 = board.registerNewTeam(empires.redTeam.teamName);
				for(OfflinePlayer p : empires.redTeam.team.getPlayers())
				{
					other1.addPlayer((Player)p);
				}
				other1.setPrefix(empires.redTeam.prefix);
				other2 = board.registerNewTeam(empires.greenTeam.teamName);
				for(OfflinePlayer p : empires.greenTeam.team.getPlayers())
				{
					other2.addPlayer((Player)p);
				}
				other2.setPrefix(empires.greenTeam.prefix);
				other3 = board.registerNewTeam(empires.purpleTeam.teamName);
				for(OfflinePlayer p : empires.purpleTeam.team.getPlayers())
				{
					other3.addPlayer((Player)p);
				}
				other3.setPrefix(empires.purpleTeam.prefix);
			break;
			case "Green Team": 
				other1 = board.registerNewTeam(empires.redTeam.teamName);
				for(OfflinePlayer p : empires.redTeam.team.getPlayers())
				{
					other1.addPlayer((Player)p);
				}
				other1.setPrefix(empires.redTeam.prefix);
				other2 = board.registerNewTeam(empires.blueTeam.teamName);
				for(OfflinePlayer p : empires.blueTeam.team.getPlayers())
				{
					other2.addPlayer((Player)p);
				}
				other2.setPrefix(empires.blueTeam.prefix);
				other3 = board.registerNewTeam(empires.purpleTeam.teamName);
				for(OfflinePlayer p : empires.purpleTeam.team.getPlayers())
				{
					other3.addPlayer((Player)p);
				}
				other3.setPrefix(empires.purpleTeam.prefix);
			break;
			case "Purple Team": 
				other1 = board.registerNewTeam(empires.redTeam.teamName);
				for(OfflinePlayer p : empires.redTeam.team.getPlayers())
				{
					other1.addPlayer((Player)p);
				}
				other1.setPrefix(empires.redTeam.prefix);
				other2 = board.registerNewTeam(empires.blueTeam.teamName);
				for(OfflinePlayer p : empires.blueTeam.team.getPlayers())
				{
					other2.addPlayer((Player)p);
				}
				other2.setPrefix(empires.blueTeam.prefix);
				other3 = board.registerNewTeam(empires.greenTeam.teamName);
				for(OfflinePlayer p : empires.greenTeam.team.getPlayers())
				{
					other3.addPlayer((Player)p);
				}
				other3.setPrefix(empires.greenTeam.prefix);
			break;
		}
	}
	
	public void updateScoreboard()
	{
		empirePoints = levelPoints + collectorPoints + killPoints;

		objective.unregister();
		objective = board.registerNewObjective("test", "dummy");
		
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(displayName);
		empireScore = objective.getScore(ChatColor.GOLD + "§lEmpire Points: " + ChatColor.WHITE + "" + empirePoints);
		empireScore.setScore(5);
		woodScore = objective.getScore(ChatColor.DARK_GREEN + "Wood: " + ChatColor.WHITE + "" + wood);
		woodScore.setScore(4);
		bricksScore = objective.getScore(ChatColor.GRAY + "Bricks: " + ChatColor.WHITE + "" + bricks);
		bricksScore.setScore(3);
		foodScore = objective.getScore(ChatColor.RED + "Food: " + ChatColor.WHITE + "" + food);
		foodScore.setScore(2);
		goldScore = objective.getScore(ChatColor.YELLOW + "Gold: " + ChatColor.WHITE + "" + gold);
		goldScore.setScore(1);
		ironScore = objective.getScore(ChatColor.DARK_GRAY + "Iron: " + ChatColor.WHITE + "" + iron);
		ironScore.setScore(0);

		
		Object[] tPlayers = team.getPlayers().toArray();
		for(int i = 0; i < tPlayers.length; i++)
		{
			Player sP = (Player)tPlayers[i];
			sP.setScoreboard(board);
		}
	}
	
	public void updateLevel()
	{
		Object[] tPlayers = team.getPlayers().toArray();
		if(wood >= resourceCost && bricks >= resourceCost && level < 50)
		{
			wood -= resourceCost;
			bricks -= resourceCost;
			//if(level < 30)
			//{
			//}
			/*if(level >= 30)
			{
				levelPoints += 3;
			}*/
			levelPoints += 2;
			level++;
			for(int i = 0; i < tPlayers.length; i++)
			{
				Player sP = (Player)tPlayers[i];
				sP.setLevel(level);
				sP.playSound(sP.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
				if(level == 10)
				{
					sP.sendMessage(ChatColor.GOLD + "[Tools] Your empire has reached level 10! You may now gather stone tools from the Gather Tools sign!");
				}
				if(level == 20)
				{
					sP.sendMessage(ChatColor.GOLD + "[Tools] Your empire has reached level 20! You may now gather iron tools from the Gather Tools sign!");
				}
				if(level == 30)
				{
					sP.sendMessage(ChatColor.GOLD + "[Tools] Your empire has reached level 30! You may now gather diamond tools from the Gather Tools sign!");
				}
			}
		}
		else 
		{
			for(int i = 0; i < tPlayers.length; i++)
			{
				Player sP = (Player)tPlayers[i];
				sP.setLevel(level);
			}
		}
		updateScoreboard();
	}
	
	public TeamManager(String color, Empires emp)
	{
		switch(color)
		{
			case "Red": teamName = ChatColor.RED + "Red Team";
				displayName = ChatColor.RED + "§nRed Team";
				prefix = ChatColor.RED + "[R] ";
				break;
			case "Blue": teamName = ChatColor.BLUE + "Blue Team";
				displayName = ChatColor.BLUE + "§nBlue Team";
				prefix = ChatColor.BLUE + "[B] ";
				break;
			case "Green": teamName = ChatColor.DARK_GREEN + "Green Team";
				displayName = ChatColor.DARK_GREEN + "§nGreen Team";
				prefix = ChatColor.DARK_GREEN + "[G] ";
				break;
			case "Purple": teamName = ChatColor.DARK_PURPLE + "Purple Team";
				displayName = ChatColor.DARK_PURPLE + "§nPurple Team";
				prefix = ChatColor.DARK_PURPLE + "[P] ";
				break;
		}
		empires = emp;
	}
}