package com.themantlemc.mantleEssentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMusic implements CommandExecutor {

	MantleEssentials music;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(!(sender instanceof Player))
		{
			return true;
		}
		
		music.openMusicMenu((Player)sender);
		return true;
	}
	
	public CommandMusic(MantleEssentials m)
	{
		music = m;
	}

}