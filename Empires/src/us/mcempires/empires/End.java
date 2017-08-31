package us.mcempires.empires;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import net.md_5.bungee.api.ChatColor;

public class End implements CommandExecutor {
	
	Empires empires;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(sender instanceof Player)
		{
			Player p = (Player)sender;
			if(!p.isOp())
			{
				return true;
			}
		}
		for(Player player : Bukkit.getOnlinePlayers())
		{	
			player.getInventory().clear();
			player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			player.teleport(empires.lobby.getSpawnLocation());
			player.sendMessage(ChatColor.GOLD + "The game has ended! Tallying final Empire Points!");
			empires.endBar = Bukkit.createBossBar(ChatColor.GOLD + "Game Over!", BarColor.WHITE, BarStyle.SOLID);
			empires.endBar.addPlayer(player);
			
			if(empires.getPlayerInfo(player.getName()).respawnTimer != -1)
			{
				empires.getPlayerInfo(player.getName()).respawnBar.removeAll();
			}
			
			for(PotionEffect pE : player.getActivePotionEffects())
			{
				player.removePotionEffect(pE.getType());
			}
		}
		empires.gameRunning = false;
		empires.gameEnding = true;
		Bukkit.getServer().unloadWorld(empires.gameWorld, false);
		empires.endGame();
		return true;
	}
	
	public End(Empires emp)
	{
		empires = emp;
	}
}
