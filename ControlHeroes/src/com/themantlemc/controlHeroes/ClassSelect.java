package com.themantlemc.controlHeroes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClassSelect implements CommandExecutor
{
	ControlPoint cp;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		// Open the class menu when the player uses /class
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			cp.openClassMenu(player);
		}
		return true;
	}
	
	public ClassSelect(ControlPoint control)
	{
		cp = control;
	}
}