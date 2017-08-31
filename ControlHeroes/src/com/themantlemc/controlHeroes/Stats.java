package com.themantlemc.controlHeroes;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Stats implements CommandExecutor {
	
	ControlPoint cp;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(!(sender instanceof Player))
		{
			return true;
		}
		
		Player player = (Player)sender;
		Player statsPlayer = null;
		if(args.length == 0)
		{
			statsPlayer = player;
		}
		else
		{
			for(Player sPlayer : Bukkit.getOnlinePlayers())
			{
				if(args[0].equals(sPlayer.getName()))
				{
					statsPlayer = sPlayer;
					break;
				}
			}
		}
		if(statsPlayer == null)
		{
			player.sendMessage(ChatColor.RED + "You cannot check the stats of this player!");
			return true;
		}
		PlayerInfo statsInfo = cp.getPlayerInfo(statsPlayer.getName());
		player.sendMessage("");
		player.sendMessage(ChatColor.GOLD + "-[]-[]-[]-[] " + ChatColor.AQUA +  statsPlayer.getName() + "'s Stats" + ChatColor.GOLD + " []-[]-[]-[]-");
		player.sendMessage(ChatColor.GOLD + "Prestige: " + ChatColor.AQUA + statsInfo.prestige);
		player.sendMessage(ChatColor.GOLD + "Level: " + ChatColor.AQUA + statsInfo.level);
		player.sendMessage(ChatColor.GOLD + "Class: " + ChatColor.AQUA + statsInfo.currentClass + ChatColor.GOLD + "      Perk: " + ChatColor.AQUA + statsInfo.currentPerk);
		player.sendMessage(ChatColor.GOLD + "Games Played: " + ChatColor.AQUA + statsInfo.games + ChatColor.GOLD + "      Wins: " + ChatColor.AQUA + statsInfo.wins);
		player.sendMessage("");
		
		float kd = 0;
		if(statsInfo.totalDeaths > 0)
		{
			kd = (float)statsInfo.totalKills/statsInfo.totalDeaths;
		}
		player.sendMessage(ChatColor.GOLD + "Kills: " + ChatColor.AQUA + statsInfo.totalKills + ChatColor.GOLD + "    Deaths: " + ChatColor.AQUA + statsInfo.totalDeaths + ChatColor.GOLD + "    K/D Ratio: " + ChatColor.AQUA + kd);
		player.sendMessage(ChatColor.GOLD + "XP: " + ChatColor.AQUA + statsInfo.xp + ChatColor.GOLD + "      XP Needed For Level Up: " + ChatColor.AQUA + statsInfo.xpToLevel);
		return true;
	}
	
	public Stats(ControlPoint control)
	{
		cp = control;
	}
}
