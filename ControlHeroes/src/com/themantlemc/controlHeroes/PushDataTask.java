// This class pushes player information to the MySQL Database

package com.themantlemc.controlHeroes;

import java.sql.PreparedStatement;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PushDataTask extends BukkitRunnable {

	private UUID uuid;
	private String pName;
	ControlPoint cp;
	
	public PushDataTask(UUID id, String nm, ControlPoint control) {
		uuid = id;
		pName = nm;
		cp = control;
	}

	@Override
	public void run() {
		
		PlayerInfo info = cp.getPlayerInfo(pName);
		
		// Update General
		try
		{
			PreparedStatement statement = cp.getInstance().getConnection().prepareStatement("UPDATE `general` SET `name` = ?, `rank` = ?, `coins` = ? WHERE `uuid` = ?");
			statement.setString(1, pName);
			statement.setString(2, info.rank);
			statement.setInt(3, info.mantlecoins);
			statement.setString(4, uuid.toString());
			statement.executeUpdate();
			statement.close();
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
		// Update player information
		try
		{
			PreparedStatement statement = ControlPoint.getInstance().getConnection().prepareStatement("UPDATE `controlheroes` SET `name` = ?, `prestige` = ?, `level` = ?, `xp` = ?, `games` = ?, `wins` = ?, `kills` = ?, `deaths` = ?, `class` = ?, `perk` = ?, `heropack` = ?, `coins` = ? WHERE `uuid` = ?");
			statement.setString(1, pName);
			statement.setInt(2, info.prestige);
			statement.setInt(3, info.level);
			statement.setInt(4, info.xp);
			statement.setInt(5, info.games);
			statement.setInt(6, info.wins);
			statement.setInt(7, info.totalKills);
			statement.setInt(8, info.totalDeaths);
			statement.setString(9, info.currentClass);
			statement.setString(10, info.currentPerk);
			statement.setString(11, info.currentPack);
			statement.setInt(12, info.coins);
			statement.setString(13, uuid.toString());
			statement.executeUpdate();
			statement.close();
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
		// Update player perks
		try
		{
			PreparedStatement statement = ControlPoint.getInstance().getConnection().prepareStatement("UPDATE `heroperks` SET `name` = ?, `powersurge` = ?, `ironskin` = ?, `immortal` = ?, `explosive` = ?, `resourceful` = ?, `heroic` = ?, `godslayer` = ? WHERE `uuid` = ?");
			statement.setString(1, pName);
			statement.setInt(2, info.powersurge);
			statement.setInt(3, info.ironskin);
			statement.setInt(4, info.immortal);
			statement.setInt(5, info.explosive);
			statement.setInt(6, info.resourceful);
			statement.setInt(7, info.heroic);
			statement.setInt(8, info.godslayer);
			statement.setString(9, uuid.toString());
			statement.executeUpdate();
			statement.close();
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
		// Update player hero packs
		try
		{
			PreparedStatement statement = ControlPoint.getInstance().getConnection().prepareStatement("UPDATE `heropacks` SET `name` = ?, `allrounder` = ?, `ranged` = ?, `tank` = ?, `stealthy` = ?, `utility` = ?, `agile` = ?, `magic` = ?, `support` = ?, `fiery` = ?, `poisonous` = ? WHERE `uuid` = ?");
			statement.setString(1, pName);
			statement.setBoolean(2, true);
			if(info.unlockedPacks.contains("Ranged"))
			{
				statement.setBoolean(3, true);
			}
			else
			{
				statement.setBoolean(3, false);
			}
			if(info.unlockedPacks.contains("Tank"))
			{
				statement.setBoolean(4, true);
			}
			else
			{
				statement.setBoolean(4, false);
			}
			if(info.unlockedPacks.contains("Stealthy"))
			{
				statement.setBoolean(5, true);
			}
			else
			{
				statement.setBoolean(5, false);
			}
			if(info.unlockedPacks.contains("Utility"))
			{
				statement.setBoolean(6, true);
			}
			else
			{
				statement.setBoolean(6, false);
			}
			if(info.unlockedPacks.contains("Agile"))
			{
				statement.setBoolean(7, true);
			}
			else
			{
				statement.setBoolean(7, false);
			}
			if(info.unlockedPacks.contains("Magic"))
			{
				statement.setBoolean(8, true);
			}
			else
			{
				statement.setBoolean(8, false);
			}
			if(info.unlockedPacks.contains("Support"))
			{
				statement.setBoolean(9, true);
			}
			else
			{
				statement.setBoolean(9, false);
			}
			if(info.unlockedPacks.contains("Fiery"))
			{
				statement.setBoolean(10, true);
			}
			else
			{
				statement.setBoolean(10, false);
			}
			if(info.unlockedPacks.contains("Poisonous"))
			{
				statement.setBoolean(11, true);
			}
			else
			{
				statement.setBoolean(11, false);
			}
			statement.setString(12, uuid.toString());
			statement.executeUpdate();
			statement.close();
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}