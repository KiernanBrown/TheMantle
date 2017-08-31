package com.themantlemc.mantleEssentials;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class CommandRespond implements CommandExecutor 
{
	
	MantleEssentials mantle;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(!(sender instanceof Player))
		{
			return true;
		}
		
		if(args.length == 0)
		{
			return true;
		}
		
		Player player = (Player)sender;
		PlayerInfo playerInfo = mantle.getPlayerInfo(player);
		if(playerInfo.lastMessaged == null)
		{
			player.sendMessage(ChatColor.RED + "You cannot use /r until you have messaged someone!");
			return true;
		}
		Player receiver = playerInfo.lastMessaged;
		if(!Bukkit.getOnlinePlayers().contains(receiver))
		{
			player.sendMessage(ChatColor.RED + "This player is not online!");
			return true;
		}
		String message = "";
		for(int i = 0; i < args.length; i++)
		{
			message += args[i] + " ";
		}
		player.sendMessage(player.getDisplayName() + ChatColor.YELLOW + " \u27A4 " + receiver.getDisplayName() + ChatColor.WHITE + ": " +  message);
		receiver.sendMessage(player.getDisplayName() + ChatColor.YELLOW + " \u27A4 " + receiver.getDisplayName() + ChatColor.WHITE + ": " + message);
		return true;
	}

	public CommandRespond(MantleEssentials m)
	{
		mantle = m;
	}
}