package com.themantlemc.clayMix;

import java.sql.ResultSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoadDataTask extends BukkitRunnable {
	
	private UUID uuid;
	private String pName;
	private ClayMix claymix;
	
	public LoadDataTask(UUID id, String nm, ClayMix clay)
	{
		uuid = id;
		pName = nm;
		claymix = clay;
	}
	
	@Override
	public void run() {
		PlayerInfo info = claymix.getPlayerInfo(pName);
		
		// General loading
		try {
			PreparedStatement statement = claymix.getInstance().getConnection().prepareStatement("SELECT * FROM `general`  WHERE `uuid` = ?");
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
				PreparedStatement preparedStatement = claymix.getInstance().getConnection().prepareStatement("INSERT INTO `general` (`name`, `rank`, `coins`, `uuid`) VALUES (?, ?, 0, ?)");
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
		
		Player p = Bukkit.getPlayer(uuid);
		
		/*// World 1 Loading
		try {
			PreparedStatement statement = TheParkourProject.getInstance().getConnection().prepareStatement("SELECT * FROM `parkourpuzzle1`  WHERE `uuid` = ?");
			statement.setString(1, uuid.toString());
			ResultSet resultSet = statement.executeQuery();
			Boolean doesPlayerHaveData = resultSet.next();

			if(doesPlayerHaveData)
			{
				info.world1Tracks.set(0, resultSet.getBoolean(2));
				info.world1Tracks.set(1, resultSet.getBoolean(3));
				info.world1Tracks.set(2, resultSet.getBoolean(4));
				info.world1Tracks.set(3, resultSet.getBoolean(5));
				info.world1Tracks.set(4, resultSet.getBoolean(6));
				info.world1Tracks.set(5, resultSet.getBoolean(7));
			}
			else
			{
				PreparedStatement preparedStatement = TheParkourProject.getInstance().getConnection().prepareStatement("INSERT INTO `parkourpuzzle1` (`name`, `track1`, `track2`, `track3`, `track4`, `track5`, `track6`, `uuid`) VALUES (?, false, false, false, false, false, false, ?)");
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
		
		// World 2 Loading
		try {
			PreparedStatement statement = TheParkourProject.getInstance().getConnection().prepareStatement("SELECT * FROM `parkourpuzzle2`  WHERE `uuid` = ?");
			statement.setString(1, uuid.toString());
			ResultSet resultSet = statement.executeQuery();
			Boolean doesPlayerHaveData = resultSet.next();
			if(doesPlayerHaveData)
			{
				info.world2Tracks.set(0, resultSet.getBoolean(2));
				info.world2Tracks.set(1, resultSet.getBoolean(3));
				info.world2Tracks.set(2, resultSet.getBoolean(4));
				info.world2Tracks.set(3, resultSet.getBoolean(5));
				info.world2Tracks.set(4, resultSet.getBoolean(6));
				info.world2Tracks.set(5, resultSet.getBoolean(7));
			}
			else
			{
				PreparedStatement preparedStatement = TheParkourProject.getInstance().getConnection().prepareStatement("INSERT INTO `parkourpuzzle2` (`name`, `track1`, `track2`, `track3`, `track4`, `track5`, `track6`, `uuid`) VALUES (?, false, false, false, false, false, false, ?)");
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
		}*/
	}
}
