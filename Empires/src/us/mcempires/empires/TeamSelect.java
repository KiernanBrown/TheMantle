package us.mcempires.empires;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class TeamSelect implements CommandExecutor 
{
	TeamManager red;
	TeamManager blue;
	TeamManager green;
	TeamManager purple;
	
	Empires empires;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(sender instanceof Player)
		{
			Player p = (Player)sender;
			
			if(empires.gameRunning || empires.gameEnding)
			{
				p.sendMessage(ChatColor.RED + "You cannot change teams at this time!");
				return true;
			}
			if(args == null)
			{
				p.sendMessage("You must include a team to join!");
			}
			else if(args[0].toUpperCase().equals("RED"))
			{
				if(red.team.hasPlayer(p))
				{
					p.sendMessage(ChatColor.RED + "You are already on this team!");
					return true;
				}
				if(red.team.getPlayers().size() <= Bukkit.getOnlinePlayers().size() / 4)
				{
					red.team.addPlayer(p);
					p.getInventory().setHelmet(red.getIdentifier());
					p.sendMessage(ChatColor.RED + "You have joined the red team!");
					red.updateScoreboard();
					if(blue.team.hasPlayer(p))
					{
						blue.team.removePlayer(p);
					}
					else if(green.team.hasPlayer(p))
					{
						green.team.removePlayer(p);
					}
					else if(purple.team.hasPlayer(p))
					{
						purple.team.removePlayer(p);
					}
				}
				else
				{
					p.sendMessage(ChatColor.RED + "More people must join the game before you can join this team!");
				}
			}
			else if(args[0].toUpperCase().equals("BLUE"))
			{
				if(blue.team.hasPlayer(p))
				{
					p.sendMessage(ChatColor.RED + "You are already on this team!");
					return true;
				}
				if(blue.team.getPlayers().size() <= Bukkit.getOnlinePlayers().size() / 4)
				{
					blue.team.addPlayer(p);
					p.getInventory().setHelmet(blue.getIdentifier());
					p.sendMessage(ChatColor.BLUE + "You have joined the blue team!");
					blue.updateScoreboard();
					if(red.team.hasPlayer(p))
					{
						red.team.removePlayer(p);
					}
					else if(green.team.hasPlayer(p))
					{
						green.team.removePlayer(p);
					}
					else if(purple.team.hasPlayer(p))
					{
						purple.team.removePlayer(p);
					}
				}
				else
				{
					p.sendMessage(ChatColor.RED + "More people must join the game before you can join this team!");
				}
			}
			else if(args[0].toUpperCase().equals("GREEN"))
			{
				if(green.team.hasPlayer(p))
				{
					p.sendMessage(ChatColor.RED + "You are already on this team!");
					return true;
				}
				if(green.team.getPlayers().size() <= Bukkit.getOnlinePlayers().size() / 4)
				{
					green.team.addPlayer(p);
					p.getInventory().setHelmet(green.getIdentifier());
					p.sendMessage(ChatColor.DARK_GREEN + "You have joined the green team!");
					green.updateScoreboard();
					if(red.team.hasPlayer(p))
					{
						red.team.removePlayer(p);
					}
					else if(blue.team.hasPlayer(p))
					{
						blue.team.removePlayer(p);
					}
					else if(purple.team.hasPlayer(p))
					{
						purple.team.removePlayer(p);
					}
				}
				else
				{
					p.sendMessage(ChatColor.RED + "More people must join the game before you can join this team!");
				}
				
			}
			else if(args[0].toUpperCase().equals("PURPLE"))
			{
				if(purple.team.hasPlayer(p))
				{
					p.sendMessage(ChatColor.RED + "You are already on this team!");
					return true;
				}
				if(purple.team.getPlayers().size() <= Bukkit.getOnlinePlayers().size() / 4)
				{
					purple.team.addPlayer(p);
					p.getInventory().setHelmet(purple.getIdentifier());
					p.sendMessage(ChatColor.DARK_PURPLE + "You have joined the purple team!");
					purple.updateScoreboard();
					if(red.team.hasPlayer(p))
					{
						red.team.removePlayer(p);
					}
					else if(blue.team.hasPlayer(p))
					{
						blue.team.removePlayer(p);
					}
					else if(green.team.hasPlayer(p))
					{
						green.team.removePlayer(p);
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
	
	public TeamSelect(Empires emp, TeamManager r, TeamManager b, TeamManager g, TeamManager p)
	{
		empires = emp;
		red = r;
		blue = b;
		green = g;
		purple = p;
	}

}
