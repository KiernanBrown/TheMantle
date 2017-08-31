package com.themantlemc.controlHeroes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Perk implements CommandExecutor {

	ControlPoint cp;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		// Don't let players change perks during the game
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			if(cp.gameRunning || cp.gameEnding)
			{
				player.sendMessage(ChatColor.RED + "[Perk] You cannot change perks at this time!");
				return true;
			}
			cp.openPerkMenu(player);
		}
		return true;
	}
	
	public Perk(ControlPoint control)
	{
		cp = control;
	}
}