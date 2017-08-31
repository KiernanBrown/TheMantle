package com.themantlemc.mantleEssentials;

import java.sql.ResultSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoadDataTask extends BukkitRunnable {
	
	private UUID uuid;
	private String pName;
	private MantleEssentials mantle;
	
	public LoadDataTask(UUID id, String nm, MantleEssentials m)
	{
		uuid = id;
		pName = nm;
		mantle = m;
	}
	
	@Override
	public void run() 
	{
		PlayerInfo info = mantle.getPlayerInfo(pName);
		
		// General loading
		try {
			PreparedStatement statement = mantle.getInstance().getConnection().prepareStatement("SELECT * FROM `general`  WHERE `uuid` = ?");
			statement.setString(1, uuid.toString());
			ResultSet resultSet = statement.executeQuery();
			Boolean doesPlayerHaveData = resultSet.next();

			if(doesPlayerHaveData)
			{
				info.rank = resultSet.getString(2);
				info.mantlecoins = resultSet.getInt(3);
			}
			else
			{
				PreparedStatement preparedStatement = mantle.getInstance().getConnection().prepareStatement("INSERT INTO `general` (`name`, `rank`, `coins`, `uuid`) VALUES (?, ?, 0, ?)");
				preparedStatement.setString(1, pName);
				preparedStatement.setString(2, "Player");
				preparedStatement.setString(3, uuid.toString());
				preparedStatement.executeUpdate();
				preparedStatement.close();
			}
			resultSet.close();
			statement.close();
			
			/*Player p = Bukkit.getPlayer(uuid);
			
			switch(info.rank)
			{
				case "Player": p.setDisplayName(ChatColor.GRAY + pName);
				mantle.playerTeam.addPlayer(p);
				break;
				case "Spark": p.setDisplayName(ChatColor.YELLOW + pName);
				mantle.spark.addPlayer(p);
				break;
				case "Ember": p.setDisplayName(ChatColor.GOLD + pName);
				mantle.ember.addPlayer(p);
				break;
				case "Flame": p.setDisplayName(ChatColor.RED + pName);
				mantle.flame.addPlayer(p);
				break;
				case "VIP": p.setDisplayName(ChatColor.LIGHT_PURPLE + pName);
				mantle.vip.addPlayer(p);
				break;
				case "Builder": p.setDisplayName(ChatColor.DARK_GREEN + pName);
				mantle.builder.addPlayer(p);
				break;
				case "Moderator": p.setDisplayName(ChatColor.BLUE + pName);
				mantle.moderator.addPlayer(p);
				break;
				case "Admin": p.setDisplayName(ChatColor.DARK_AQUA + pName);
				mantle.admin.addPlayer(p);
				break;
				case "Owner": p.setDisplayName(ChatColor.DARK_PURPLE + pName);
				mantle.owner.addPlayer(p);
				break;
			}*/
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// Music loading
		/*try {
			PreparedStatement statement = mantle.getInstance().getConnection().prepareStatement("SELECT * FROM `music`  WHERE `uuid` = ?");
			statement.setString(1, uuid.toString());
			ResultSet resultSet = statement.executeQuery();
			Boolean doesPlayerHaveData = resultSet.next();

			if(doesPlayerHaveData)
			{
				info.defaultPlaylist = resultSet.getString(2);
				info.shuffle = resultSet.getBoolean(3);
			}
			else
			{
				PreparedStatement preparedStatement = mantle.getInstance().getConnection().prepareStatement("INSERT INTO `music` (`name`, `defaultplaylist`, `shuffle`, `song`, `ticks`, `uuid`) VALUES (?, ?, false, 0, 0, ?)");
				preparedStatement.setString(1, pName);
				preparedStatement.setString(2, "");
				preparedStatement.setString(3, uuid.toString());
				preparedStatement.executeUpdate();
				preparedStatement.close();
			}
			resultSet.close();
			statement.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}*/
	}
}