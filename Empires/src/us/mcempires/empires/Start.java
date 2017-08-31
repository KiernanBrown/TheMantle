package us.mcempires.empires;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class Start implements CommandExecutor 
{
	
	Empires empires;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(sender instanceof Player)
		{
			Player p = (Player)sender;
			if(p.isOp())
			{
				if(empires.gameStarting == false && empires.gameRunning == false && empires.gameEnding == false)
				{
					empires.startCountdown();
				}
				return true;
			}
			else
			{
				p.sendMessage(ChatColor.RED + "You cannot use this command!");
				return true;
			}
		}
		
		for(Player player : Bukkit.getOnlinePlayers())
		{	
			player.getInventory().clear();
			player.sendMessage("");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-");
			player.sendMessage(ChatColor.GOLD + "MC Empires");
			player.sendMessage(ChatColor.AQUA + "Objective: Be the team with the most Empire Points at the end of the game to win!");
			player.sendMessage(ChatColor.LIGHT_PURPLE + "Map: " + empires.mapName);
			player.sendMessage(ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-");
			player.sendMessage("");
			player.sendMessage("");
			
			// Player is not on a team, assign them to one
			if(!empires.redTeam.team.hasPlayer(player) && !empires.blueTeam.team.hasPlayer(player) && !empires.greenTeam.team.hasPlayer(player) && !empires.purpleTeam.team.hasPlayer(player))
			{
				TeamManager smallestTeam = empires.redTeam;
				if(smallestTeam.team.getPlayers().size() > empires.purpleTeam.team.getPlayers().size())
				{
					smallestTeam = empires.purpleTeam;
				}
				if(smallestTeam.team.getPlayers().size() > empires.greenTeam.team.getPlayers().size())
				{
					smallestTeam = empires.greenTeam;
				}
				if(smallestTeam.team.getPlayers().size() > empires.blueTeam.team.getPlayers().size())
				{
					smallestTeam = empires.blueTeam;
				}

				
				if(smallestTeam == empires.redTeam)
				{
					player.performCommand("team red");
				}
				else if(smallestTeam == empires.blueTeam)
				{
					player.performCommand("team blue");
				}
				else if(smallestTeam == empires.greenTeam)
				{
					player.performCommand("team green");
				}
				else if(smallestTeam == empires.purpleTeam)
				{
					player.performCommand("team purple");
				}
			}
			
			// Player is on red team
			if(empires.redTeam.team.hasPlayer(player))
			{
				player.teleport(empires.redSpawn);
				player.getInventory().setHelmet(empires.redTeam.getIdentifier());
			}
			
			// Player is on blue team
			else if(empires.blueTeam.team.hasPlayer(player))
			{
				player.teleport(empires.blueSpawn);
				player.getInventory().setHelmet(empires.blueTeam.getIdentifier());
			}
			
			// Player is on green team
			else if(empires.greenTeam.team.hasPlayer(player))
			{
				player.teleport(empires.greenSpawn);
				player.getInventory().setHelmet(empires.greenTeam.getIdentifier());
			}
			
			// Player is on purple team
			else if(empires.purpleTeam.team.hasPlayer(player))
			{
				player.teleport(empires.purpleSpawn);
				player.getInventory().setHelmet(empires.purpleTeam.getIdentifier());
			}
			//player.sendMessage(ChatColor.GOLD + "You have switched to team chat! Use /chat to toggle chats!");
			player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 5));
		}
		empires.redTeam.team.setPrefix(empires.redTeam.prefix);
		empires.redTeam.team.setAllowFriendlyFire(false);
		empires.blueTeam.team.setPrefix(empires.blueTeam.prefix);
		empires.blueTeam.team.setAllowFriendlyFire(false);
		empires.greenTeam.team.setPrefix(empires.greenTeam.prefix);
		empires.greenTeam.team.setAllowFriendlyFire(false);
		empires.purpleTeam.team.setPrefix(empires.purpleTeam.prefix);
		empires.purpleTeam.team.setAllowFriendlyFire(false);
		empires.redTeam.setOtherTeams();
		empires.blueTeam.setOtherTeams();
		empires.greenTeam.setOtherTeams();
		empires.purpleTeam.setOtherTeams();
		
		// Set resource costs
		empires.redTeam.resourceCost = empires.redTeam.team.getPlayers().size();
		if(empires.redTeam.resourceCost < 8)
		{
			empires.redTeam.resourceCost = 8;
		}
		if(empires.redTeam.resourceCost > 20)
		{
			empires.redTeam.resourceCost = 20;
		}
		for(OfflinePlayer p : empires.redTeam.team.getPlayers())
		{
			p.getPlayer().sendMessage(ChatColor.GOLD + "You have switched to team chat! Use /chat to toggle chats!");
			p.getPlayer().sendMessage(ChatColor.GOLD + "Your team's resource cost to level up: " + empires.redTeam.resourceCost + " Wood and Bricks!");
			p.getPlayer().sendMessage(ChatColor.GREEN + "The game will start in 10 seconds!");
		}
		
		empires.blueTeam.resourceCost = empires.blueTeam.team.getPlayers().size();
		if(empires.blueTeam.resourceCost < 8)
		{
			empires.blueTeam.resourceCost = 8;
		}
		if(empires.blueTeam.resourceCost > 20)
		{
			empires.blueTeam.resourceCost = 20;
		}
		for(OfflinePlayer p : empires.blueTeam.team.getPlayers())
		{
			p.getPlayer().sendMessage(ChatColor.GOLD + "You have switched to team chat! Use /chat to toggle chats!");
			p.getPlayer().sendMessage(ChatColor.GOLD + "Your team's resource cost to level up: " + empires.blueTeam.resourceCost + " Wood and Bricks!");
			p.getPlayer().sendMessage(ChatColor.GREEN + "The game will start in 10 seconds!");
		}
		
		empires.greenTeam.resourceCost = empires.greenTeam.team.getPlayers().size();
		if(empires.greenTeam.resourceCost < 8)
		{
			empires.greenTeam.resourceCost = 8;
		}
		if(empires.greenTeam.resourceCost > 20)
		{
			empires.greenTeam.resourceCost = 20;
		}
		for(OfflinePlayer p : empires.greenTeam.team.getPlayers())
		{
			p.getPlayer().sendMessage(ChatColor.GOLD + "You have switched to team chat! Use /chat to toggle chats!");
			p.getPlayer().sendMessage(ChatColor.GOLD + "Your team's resource cost to level up: " + empires.greenTeam.resourceCost + " Wood and Bricks!");
			p.getPlayer().sendMessage(ChatColor.GREEN + "The game will start in 10 seconds!");
		}
		
		empires.purpleTeam.resourceCost = empires.purpleTeam.team.getPlayers().size();
		if(empires.purpleTeam.resourceCost < 8)
		{
			empires.purpleTeam.resourceCost = 8;
		}
		if(empires.purpleTeam.resourceCost > 20)
		{
			empires.purpleTeam.resourceCost = 20;
		}
		for(OfflinePlayer p : empires.purpleTeam.team.getPlayers())
		{
			p.getPlayer().sendMessage(ChatColor.GOLD + "You have switched to team chat! Use /chat to toggle chats!");
			p.getPlayer().sendMessage(ChatColor.GOLD + "Your team's resource cost to level up: " + empires.purpleTeam.resourceCost + " Wood and Bricks!");
			p.getPlayer().sendMessage(ChatColor.GREEN + "The game will start in 10 seconds!");
		}
		
		// Spawn starting animals
		for(int i = 0; i < 2; i++)
		{
			if(i != 1)
			{
				empires.spawnCow();
				empires.spawnPig();
			}
			empires.spawnSheep();
		}
		
		// Block Collector placement
		for(Collector c : empires.collectors)
		{
			Location cLoc = c.location.add(new Vector(0, 1, 0));
			if(!cLoc.getChunk().isLoaded())
			{
				cLoc.getChunk().load();
			}
			cLoc.getBlock().setType(Material.BEDROCK);
		}
		
		// Stop player from moving for 10 seconds
		empires.gameTimer += 10.0f;
		
		empires.gameRunning = true;
		
	    // Update team levels every 5 seconds
	    empires.scheduler.scheduleSyncRepeatingTask(empires, new Runnable(){
	    	@Override
	    	public void run(){
	    		if(empires.gameRunning)
	    		{
		    		empires.redTeam.updateLevel();
		    		empires.blueTeam.updateLevel();
		    		empires.greenTeam.updateLevel();
		    		empires.purpleTeam.updateLevel();
	    		}
	    	}
	    }, 0L, 100L);
	    
	    // Update collectors every 8 seconds
	    empires.scheduler.scheduleSyncRepeatingTask(empires, new Runnable(){
	    	@Override
	    	public void run(){
	    		if(empires.gameRunning)
	    		{
	    			for(Collector c : empires.collectors)
	    			{
	    				if(c.team != null)
	    				{
	    					c.team.collectorPoints++;
	    				}
	    			}
	    		}
	    	}
	    }, 0L, 160L);

	    // Spawn sheep every 2 minutes and 15 seconds
	    empires.scheduler.scheduleSyncRepeatingTask(empires, new Runnable(){
	    	@Override
	    	public void run(){
	    		if(empires.gameRunning)
	    		{
	    			empires.spawnSheep();
	    		}
	    	}
	    	
	    }, 0L, 2700L);
	    
	    // Spawn pigs and cows every 4 minutes
	    empires.scheduler.scheduleSyncRepeatingTask(empires, new Runnable(){
	    	@Override
	    	public void run(){
	    		if(empires.gameRunning)
	    		{
	    			empires.spawnCow();
	    			empires.spawnPig();
	    		}
	    	}
	    	
	    }, 0L, 4800L);
		return true;
	}
	
	public Start(Empires emp)
	{
		empires = emp;
	}

}
