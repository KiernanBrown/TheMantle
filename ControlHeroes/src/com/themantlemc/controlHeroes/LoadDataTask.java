package com.themantlemc.controlHeroes;

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
	ControlPoint cp;
	
	public LoadDataTask(UUID id, String nm, ControlPoint control)
	{
		uuid = id;
		pName = nm;
		cp = control;
	}
	
	@Override
	public void run() 
	{
		PlayerInfo info = cp.getPlayerInfo(pName);
		
		// General loading
		try {
			PreparedStatement statement = cp.getInstance().getConnection().prepareStatement("SELECT * FROM `general`  WHERE `uuid` = ?");
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
				PreparedStatement preparedStatement = cp.getInstance().getConnection().prepareStatement("INSERT INTO `general` (`name`, `rank`, `coins`, `uuid`) VALUES (?, ?, 0, ?)");
				preparedStatement.setString(1, pName);
				preparedStatement.setString(2, "Player");
				preparedStatement.setString(3, uuid.toString());
				preparedStatement.executeUpdate();
				preparedStatement.close();
			}
			resultSet.close();
			statement.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// Stats Loading
		try {
			PreparedStatement statement = ControlPoint.getInstance().getConnection().prepareStatement("SELECT * FROM `controlheroes`  WHERE `uuid` = ?");
			statement.setString(1, uuid.toString());
			ResultSet resultSet = statement.executeQuery();
			Boolean doesPlayerHaveData = resultSet.next();
			if(doesPlayerHaveData)
			{
				info.prestige = resultSet.getInt(2);
				info.level = resultSet.getInt(3);
				info.xp = resultSet.getInt(4);
				info.games = resultSet.getInt(5);
				info.wins = resultSet.getInt(6);
				info.totalKills = resultSet.getInt(7);
				info.totalDeaths = resultSet.getInt(8);
				info.currentClass = resultSet.getString(9);
				info.currentPerk = resultSet.getString(10);
				info.currentPack = resultSet.getString(11);
				info.coins = resultSet.getInt(12);
			}
			else
			{
				PreparedStatement preparedStatement = ControlPoint.getInstance().getConnection().prepareStatement("INSERT INTO `controlheroes` (`name`, `prestige`, `level`, `xp`, `games`, `wins`, `kills`, `deaths`, `class`, `perk`, `heropack`, `coins`, `uuid`) VALUES (?, 1, 1, 0, 0, 0, 0, 0, 'Warrior', 'Powersurge', 'All Rounder', 0, ?)");
				preparedStatement.setString(1, pName);
				preparedStatement.setString(2, uuid.toString());
				preparedStatement.executeUpdate();
				preparedStatement.close();
			}
			resultSet.close();
			statement.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// Perk Loading
		try {
			PreparedStatement statement = ControlPoint.getInstance().getConnection().prepareStatement("SELECT * FROM `heroperks`  WHERE `uuid` = ?");
			statement.setString(1, uuid.toString());
			ResultSet resultSet = statement.executeQuery();
			Boolean doesPlayerHaveData = resultSet.next();
			if(doesPlayerHaveData)
			{
				info.powersurge = resultSet.getInt(2);
				info.ironskin = resultSet.getInt(3);
				info.immortal = resultSet.getInt(4);
				info.explosive = resultSet.getInt(5);
				info.resourceful = resultSet.getInt(6);
				info.heroic = resultSet.getInt(7);
				info.godslayer = resultSet.getInt(8);
			}
			else
			{
				PreparedStatement preparedStatement = ControlPoint.getInstance().getConnection().prepareStatement("INSERT INTO `heroperks` (`name`, `powersurge`, `ironskin`, `immortal`, `explosive`, `resourceful`, `heroic`, `godslayer`, `uuid`) VALUES (?, 1, 1, 1, 1, 1, 1, 1, ?)");
				preparedStatement.setString(1, pName);
				preparedStatement.setString(2, uuid.toString());
				preparedStatement.executeUpdate();
				preparedStatement.close();
			}
			resultSet.close();
			statement.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// Hero Pack Loading
		try {
			PreparedStatement statement = ControlPoint.getInstance().getConnection().prepareStatement("SELECT * FROM `heropacks`  WHERE `uuid` = ?");
			statement.setString(1, uuid.toString());
			ResultSet resultSet = statement.executeQuery();
			Boolean doesPlayerHaveData = resultSet.next();
			if(doesPlayerHaveData)
			{
				info.unlockedPacks.add("All Rounder");
				if(resultSet.getBoolean(3))
				{
					info.unlockedPacks.add("Ranged");
				}
				if(resultSet.getBoolean(4))
				{
					info.unlockedPacks.add("Tank");
				}
				if(resultSet.getBoolean(5))
				{
					info.unlockedPacks.add("Stealthy");
				}
				if(resultSet.getBoolean(6))
				{
					info.unlockedPacks.add("Utility");
				}
				if(resultSet.getBoolean(7))
				{
					info.unlockedPacks.add("Agile");
				}
				if(resultSet.getBoolean(8))
				{
					info.unlockedPacks.add("Magic");
				}
				if(resultSet.getBoolean(9))
				{
					info.unlockedPacks.add("Support");
				}
				if(resultSet.getBoolean(10))
				{
					info.unlockedPacks.add("Fiery");
				}
				if(resultSet.getBoolean(11))
				{
					info.unlockedPacks.add("Poisonous");
				}
			}
			else
			{
				PreparedStatement preparedStatement = ControlPoint.getInstance().getConnection().prepareStatement("INSERT INTO `heropacks` (`name`, `allrounder`, `ranged`, `tank`, `stealthy`, `utility`, `agile`, `magic`, `support`, `fiery`, `poisonous`, `uuid`) VALUES (?, true, false, false, false, false, false, false, false, false, false, ?)");
				preparedStatement.setString(1, pName);
				preparedStatement.setString(2, uuid.toString());
				preparedStatement.executeUpdate();
				preparedStatement.close();
				info.unlockedPacks.add("All Rounder");
			}
			resultSet.close();
			statement.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
