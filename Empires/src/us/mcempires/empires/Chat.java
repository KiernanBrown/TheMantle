package us.mcempires.empires;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Chat implements CommandExecutor {
	
	Empires empires;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		Player p = (Player)sender;
		PlayerInfo pInfo = empires.getPlayerInfo(p.getName());
		if(empires.gameRunning)
		{
			if(pInfo.chatType == "Team")
			{
				pInfo.chatType = "Global";
				p.sendMessage(ChatColor.GOLD + "You have switched to global chat!");
			}
			else if(pInfo.chatType == "Global")
			{
				pInfo.chatType = "Team";
				p.sendMessage(ChatColor.GOLD + "You have switched to team chat!");
			}
		}
		else
		{
			p.sendMessage(ChatColor.RED + "You cannot change chats while the game is not running!");
		}
		return true;
	}
	
	public Chat(Empires emp)
	{
		empires = emp;
	}
}
