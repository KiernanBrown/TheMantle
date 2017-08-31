package com.themantlemc.controlHeroes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Team;


public class Start implements CommandExecutor {

	ControlPoint cp;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(sender instanceof Player)
		{
			Player p = (Player)sender;
			if(p.isOp())
			{
				if(cp.gameStarting == false && cp.gameRunning == false && cp.gameEnding == false)
				{
					cp.startCountdown();
				}
				return true;
			}
			else
			{
				p.sendMessage(ChatColor.RED + "You cannot use this command!");
				return true;
			}
		}
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-");
		Bukkit.broadcastMessage(ChatColor.GOLD + "Control Point");
		Bukkit.broadcastMessage(ChatColor.AQUA + "Objective: Be the first team to get to 150 points!");
		Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Map: " + cp.mapName);
		Bukkit.broadcastMessage(ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("");
		
		// Create Hero Bars
		cp.redHeroBar = Bukkit.createBossBar(ChatColor.RED + "Hero Bar", BarColor.RED, BarStyle.SEGMENTED_10);
		cp.redHeroBar.setProgress(0);
		cp.blueHeroBar = Bukkit.createBossBar(ChatColor.BLUE + "Hero Bar", BarColor.BLUE, BarStyle.SEGMENTED_10);
		cp.blueHeroBar.setProgress(0);
		cp.greenHeroBar = Bukkit.createBossBar(ChatColor.DARK_GREEN + "Hero Bar", BarColor.GREEN, BarStyle.SEGMENTED_10);
		cp.greenHeroBar.setProgress(0);
		cp.purpleHeroBar = Bukkit.createBossBar(ChatColor.DARK_PURPLE + "Hero Bar", BarColor.PURPLE, BarStyle.SEGMENTED_10);
		cp.purpleHeroBar.setProgress(0);
		
		// Create Hero Health Bars
		cp.redHeroActive = Bukkit.createBossBar(ChatColor.RED + "Hero Armor", BarColor.RED, BarStyle.SOLID);
		cp.redHeroActive.setProgress(1);
		cp.blueHeroActive = Bukkit.createBossBar(ChatColor.BLUE + "Hero Armor", BarColor.BLUE, BarStyle.SOLID);
		cp.blueHeroActive.setProgress(1);
		cp.greenHeroActive = Bukkit.createBossBar(ChatColor.DARK_GREEN + "Hero Armor", BarColor.GREEN, BarStyle.SOLID);
		cp.greenHeroActive.setProgress(1);
		cp.purpleHeroActive = Bukkit.createBossBar(ChatColor.DARK_PURPLE + "Hero Armor", BarColor.PURPLE, BarStyle.SOLID);
		cp.purpleHeroActive.setProgress(1);
		
		for(Player player : Bukkit.getOnlinePlayers())
		{
			// Player has no team, place them in one
			if(cp.getPlayerTeam(player) == null)
			{
				// Get the smallest team
				Team smallestTeam = cp.teamManager.redTeam;
				if(smallestTeam.getPlayers().size() > cp.teamManager.blueTeam.getPlayers().size())
				{
					smallestTeam = cp.teamManager.blueTeam;
				}
				if(smallestTeam.getPlayers().size() > cp.teamManager.greenTeam.getPlayers().size())
				{
					smallestTeam = cp.teamManager.greenTeam;
				}
				if(smallestTeam.getPlayers().size() > cp.teamManager.purpleTeam.getPlayers().size())
				{
					smallestTeam = cp.teamManager.purpleTeam;
				}
				
				// Put the player on the smallest team
				if(smallestTeam.equals(cp.teamManager.redTeam))
				{
					player.performCommand("team red");
				}
				if(smallestTeam.equals(cp.teamManager.blueTeam))
				{
					player.performCommand("team blue");
				}
				if(smallestTeam.equals(cp.teamManager.greenTeam))
				{
					player.performCommand("team green");
				}
				if(smallestTeam.equals(cp.teamManager.purpleTeam))
				{
					player.performCommand("team purple");
				}
			}
			
			// Clear player's inventory
			player.getInventory().clear();
			
			// Give the player their team identifier and class selector
			player.getInventory().setHelmet(cp.teamManager.getIdentifier(player));
			ItemStack classSelector = new ItemStack(Material.NAME_TAG);
			ItemMeta classMeta = classSelector.getItemMeta();
			classMeta.setDisplayName(ChatColor.GOLD + "Class Selector");
			classSelector.setItemMeta(classMeta);
			player.getInventory().setItem(8, classSelector);
			
			/*ItemStack tracerBow = new ItemStack(Material.BOW);
			ItemMeta tBowMeta = tracerBow.getItemMeta();
			tBowMeta.setDisplayName(ChatColor.GOLD + "Tracer Bow");
			tracerBow.setItemMeta(tBowMeta);
			player.getInventory().addItem(tracerBow);*/
			
			// Teleport player to their spawn
			Team playerTeam = cp.getPlayerTeam(player);
			if(playerTeam.equals(cp.teamManager.redTeam))
			{
				player.teleport(cp.redSpawn);
				cp.redHeroBar.addPlayer(player);
			}
			if(playerTeam.equals(cp.teamManager.blueTeam))
			{
				player.teleport(cp.blueSpawn);
				cp.blueHeroBar.addPlayer(player);
			}
			if(playerTeam.equals(cp.teamManager.greenTeam))
			{
				player.teleport(cp.greenSpawn);
				cp.greenHeroBar.addPlayer(player);
			}
			if(playerTeam.equals(cp.teamManager.purpleTeam))
			{
				player.teleport(cp.purpleSpawn);
				cp.purpleHeroBar.addPlayer(player);
			}
			
			// Set the player's scoreboard
			player.setScoreboard(cp.teamManager.board);
			PlayerInfo info = cp.getPlayerInfo(player.getName());
			if(info.changeClass != "")
			{
				info.currentClass = info.changeClass;
				info.changeClass = "";
			}
			
			// Equip the player's current class
			for(int i = 0; i < cp.classes.size(); i++)
			{
				if(ChatColor.stripColor(info.currentClass).equals(ChatColor.stripColor(cp.classes.get(i).className)))
				{
					ControlPointClass eClass = cp.classes.get(i);
					cp.equipClass(player, eClass);
					break;
				}
			}
			
			// Update the player's games played
			cp.getPlayerInfo(player.getName()).games++;
		}
		
		cp.gameRunning = true;
		cp.pointTimer = 35 + (int)Math.floor((Math.random() * 10)) - 5;
		return true;
	}

	public Start(ControlPoint control)
	{
		cp = control;
	}
	
}
