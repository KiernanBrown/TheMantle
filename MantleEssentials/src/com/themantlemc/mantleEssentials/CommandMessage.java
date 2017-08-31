package com.themantlemc.mantleEssentials;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMessage implements CommandExecutor {
	
	MantleEssentials mantle;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(!(sender instanceof Player))
		{
			return true;
		}
		
		if(args.length == 0 || args.length == 1)
		{
			return true;
		}
		
		Player player = (Player)sender;
		if(Bukkit.getPlayer(args[0]) == null)
		{
			player.sendMessage(ChatColor.RED + "This player is invalid!");
			return true;
		}
		Player receiver = Bukkit.getPlayer(args[0]);
		if(player.equals(receiver))
		{
			player.sendMessage(ChatColor.RED + "You can't message yourself!");
			return true;
		}
		String message = "";
		for(int i = 1; i < args.length; i++)
		{
			message += args[i] + " ";
		}
		player.sendMessage(player.getDisplayName() + ChatColor.YELLOW + " \u27A4 " + receiver.getDisplayName() + ChatColor.WHITE + ": " + message);
		receiver.sendMessage(player.getDisplayName() + ChatColor.YELLOW + " \u27A4 " + receiver.getDisplayName() + ChatColor.WHITE + ": " + message);
		mantle.getPlayerInfo(player).lastMessaged = receiver;
		return true;
	}
	
	public CommandMessage(MantleEssentials m)
	{
		mantle = m;
	}

}