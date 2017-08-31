package com.themantlemc.controlHeroes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamSelect implements CommandExecutor {
	
	ControlPoint cp;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(sender instanceof Player)
		{
			Player p = (Player)sender;
			
			// Stop players from changing teams during the game
			if(cp.gameRunning || cp.gameEnding)
			{
				p.sendMessage(ChatColor.RED + "You cannot change teams at this time!");
				return true;
			}
			
			// Give the player an error message if they only do /team
			if(args.length == 0)
			{
				p.sendMessage(ChatColor.RED + "You must include a team to join!");
				return true;
			}
			
			// Add player to red team
			else if(args[0].toUpperCase().equals("RED"))
			{
				if(cp.teamManager.redTeam.hasPlayer(p))
				{
					p.sendMessage(ChatColor.RED + "You are already on this team!");
					return true;
				}
				if(cp.teamManager.redTeam.getPlayers().size() <= Bukkit.getOnlinePlayers().size() / 4)
				{
					cp.teamManager.redTeam.addPlayer(p);
					p.sendMessage(ChatColor.RED + "You have joined the red team!");
					if(cp.teamManager.blueTeam.hasPlayer(p))
					{
						cp.teamManager.blueTeam.removePlayer(p);
					}
					else if(cp.teamManager.greenTeam.hasPlayer(p))
					{
						cp.teamManager.greenTeam.removePlayer(p);
					}
					else if(cp.teamManager.purpleTeam.hasPlayer(p))
					{
						cp.teamManager.purpleTeam.removePlayer(p);
					}
				}
				else
				{
					p.sendMessage(ChatColor.RED + "More people must join the game before you can join this team!");
				}
			}
			
			// Add player to blue team
			else if(args[0].toUpperCase().equals("BLUE"))
			{
				if(cp.teamManager.blueTeam.hasPlayer(p))
				{
					p.sendMessage(ChatColor.RED + "You are already on this team!");
					return true;
				}
				if(cp.teamManager.blueTeam.getPlayers().size() <= Bukkit.getOnlinePlayers().size() / 4)
				{
					cp.teamManager.blueTeam.addPlayer(p);
					p.sendMessage(ChatColor.BLUE + "You have joined the blue team!");
					if(cp.teamManager.redTeam.hasPlayer(p))
					{
						cp.teamManager.redTeam.removePlayer(p);
					}
					else if(cp.teamManager.greenTeam.hasPlayer(p))
					{
						cp.teamManager.greenTeam.removePlayer(p);
					}
					else if(cp.teamManager.purpleTeam.hasPlayer(p))
					{
						cp.teamManager.purpleTeam.removePlayer(p);
					}
				}
				else
				{
					p.sendMessage(ChatColor.RED + "More people must join the game before you can join this team!");
				}
			}
			
			// Add player to green team
			else if(args[0].toUpperCase().equals("GREEN"))
			{
				if(cp.teamManager.greenTeam.hasPlayer(p))
				{
					p.sendMessage(ChatColor.RED + "You are already on this team!");
					return true;
				}
				if(cp.teamManager.greenTeam.getPlayers().size() <= Bukkit.getOnlinePlayers().size() / 4)
				{
					cp.teamManager.greenTeam.addPlayer(p);
					p.sendMessage(ChatColor.GREEN + "You have joined the green team!");
					if(cp.teamManager.redTeam.hasPlayer(p))
					{
						cp.teamManager.redTeam.removePlayer(p);
					}
					else if(cp.teamManager.blueTeam.hasPlayer(p))
					{
						cp.teamManager.blueTeam.removePlayer(p);
					}
					else if(cp.teamManager.purpleTeam.hasPlayer(p))
					{
						cp.teamManager.purpleTeam.removePlayer(p);
					}
				}
				else
				{
					p.sendMessage(ChatColor.RED + "More people must join the game before you can join this team!");
				}
				
			}
			
			// Add player to purple team
			else if(args[0].toUpperCase().equals("PURPLE"))
			{
				if(cp.teamManager.purpleTeam.hasPlayer(p))
				{
					p.sendMessage(ChatColor.RED + "You are already on this team!");
					return true;
				}
				if(cp.teamManager.purpleTeam.getPlayers().size() <= Bukkit.getOnlinePlayers().size() / 4)
				{
					cp.teamManager.purpleTeam.addPlayer(p);
					p.sendMessage(ChatColor.DARK_PURPLE + "You have joined the purple team!");
					if(cp.teamManager.redTeam.hasPlayer(p))
					{
						cp.teamManager.redTeam.removePlayer(p);
					}
					else if(cp.teamManager.blueTeam.hasPlayer(p))
					{
						cp.teamManager.blueTeam.removePlayer(p);
					}
					else if(cp.teamManager.greenTeam.hasPlayer(p))
					{
						cp.teamManager.greenTeam.removePlayer(p);
					}
				}
				else
				{
					p.sendMessage(ChatColor.RED + "More people must join the game before you can join this team!");
				}
			}
		}
		return true;
	}
	
	public TeamSelect(ControlPoint control)
	{
		cp = control;
	}
}
