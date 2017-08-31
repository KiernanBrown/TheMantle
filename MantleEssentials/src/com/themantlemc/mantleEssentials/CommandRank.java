package com.themantlemc.mantleEssentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class CommandRank implements CommandExecutor 
{
	
	MantleEssentials mantle;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(!(sender instanceof Player))
		{
			return true;
		}
		
		if(args.length == 0 || args.length > 2)
		{
			return true;
		}
		
		Player player = (Player)sender;
		PlayerInfo playerInfo = mantle.getPlayerInfo(player);
		if(!playerInfo.rank.equals("Owner"))
		{
			return true;
		}
		if(Bukkit.getPlayer(args[0]) == null)
		{
			player.sendMessage(ChatColor.RED + "This player is invalid!");
			return true;
		}
		Player receiver = Bukkit.getPlayer(args[0]);
		if(player.equals(receiver))
		{
			player.sendMessage(ChatColor.RED + "You can't change your own rank!");
			return true;
		}
		PlayerInfo receiverInfo = mantle.getPlayerInfo(receiver);
		
		String rankStr = args[1].toUpperCase();
		switch(rankStr)
		{
			case "PLAYER": receiverInfo.rank = "Player";
			receiver.setDisplayName(ChatColor.GRAY + receiver.getName());
			player.sendMessage(ChatColor.GOLD + "You have changed " + receiver.getName() + "'s rank to Player!");
			break;
			case "SPARK": receiverInfo.rank = "Spark";
			receiver.setDisplayName(ChatColor.YELLOW + receiver.getName());
			player.sendMessage(ChatColor.GOLD + "You have changed " + receiver.getName() + "'s rank to Spark!");
			break;
			case "EMBER": receiverInfo.rank = "Ember";
			receiver.setDisplayName(ChatColor.GOLD + receiver.getName());
			player.sendMessage(ChatColor.GOLD + "You have changed " + receiver.getName() + "'s rank to Ember!");
			break;
			case "FLAME": receiverInfo.rank = "Flame";
			receiver.setDisplayName(ChatColor.YELLOW + receiver.getName());
			player.sendMessage(ChatColor.RED + "You have changed " + receiver.getName() + "'s rank to Flame!");
			break;
			case "VIP": receiverInfo.rank = "VIP";
			receiver.setDisplayName(ChatColor.LIGHT_PURPLE + receiver.getName());
			player.sendMessage(ChatColor.GOLD + "You have changed " + receiver.getName() + "'s rank to VIP!");
			break;
			case "BUILDER": receiverInfo.rank = "Builder";
			receiver.setDisplayName(ChatColor.DARK_GREEN + receiver.getName());
			player.sendMessage(ChatColor.GOLD + "You have changed " + receiver.getName() + "'s rank to Builder!");
			break;
			case "MODERATOR": receiverInfo.rank = "Moderator";
			receiver.setDisplayName(ChatColor.BLUE + receiver.getName());
			player.sendMessage(ChatColor.GOLD + "You have changed " + receiver.getName() + "'s rank to Moderator!");
			break;
			case "ADMIN": receiverInfo.rank = "Admin";
			receiver.setDisplayName(ChatColor.DARK_AQUA + receiver.getName());
			player.sendMessage(ChatColor.GOLD + "You have changed " + receiver.getName() + "'s rank to Admin!");
			break;
		}
		return true;
	}

	public CommandRank(MantleEssentials m)
	{
		mantle = m;
	}
}