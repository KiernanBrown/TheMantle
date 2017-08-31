// This class pushes player information to the MySQL Database

package com.themantlemc.theParkourProject;

import java.sql.PreparedStatement;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PushDataTask extends BukkitRunnable {

	private UUID uuid;
	private String pName;
	private TheParkourProject parkour;
	
	public PushDataTask(UUID id, String nm, TheParkourProject park) {
		uuid = id;
		pName = nm;
		parkour = park;
	}

	@Override
	public void run() {
		PlayerInfo info = parkour.getPlayerInfo(pName);
		
		if(info == null)
		{
			return;
		}
		
		// Update World 1
		try
		{
			PreparedStatement statement = TheParkourProject.getInstance().getConnection().prepareStatement("UPDATE `parkourpuzzle1` SET `name` = ?, `track1` = ?, `track2` = ?, `track3` = ?, `track4` = ?, `track5` = ?, `track6` = ? WHERE `uuid` = ?");
			statement.setString(1, pName);
			statement.setBoolean(2, info.world1Tracks.get(0));
			statement.setBoolean(3, info.world1Tracks.get(1));
			statement.setBoolean(4, info.world1Tracks.get(2));
			statement.setBoolean(5, info.world1Tracks.get(3));
			statement.setBoolean(6, info.world1Tracks.get(4));
			statement.setBoolean(7, info.world1Tracks.get(5));
			statement.setString(8, uuid.toString());
			statement.executeUpdate();
			statement.close();
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
		// Update World 2
		try
		{
			PreparedStatement statement = TheParkourProject.getInstance().getConnection().prepareStatement("UPDATE `parkourpuzzle2` SET `name` = ?, `track1` = ?, `track2` = ?, `track3` = ?, `track4` = ?, `track5` = ?, `track6` = ? WHERE `uuid` = ?");
			statement.setString(1, pName);
			statement.setBoolean(2, info.world2Tracks.get(0));
			statement.setBoolean(3, info.world2Tracks.get(1));
			statement.setBoolean(4, info.world2Tracks.get(2));
			statement.setBoolean(5, info.world2Tracks.get(3));
			statement.setBoolean(6, info.world2Tracks.get(4));
			statement.setBoolean(7, info.world2Tracks.get(5));
			statement.setString(8, uuid.toString());
			statement.executeUpdate();
			statement.close();
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
		// Update World 3
		try
		{
			PreparedStatement statement = TheParkourProject.getInstance().getConnection().prepareStatement("UPDATE `parkourpuzzle3` SET `name` = ?, `track1` = ?, `track2` = ?, `track3` = ?, `track4` = ?, `track5` = ?, `track6` = ? WHERE `uuid` = ?");
			statement.setString(1, pName);
			statement.setBoolean(2, info.world3Tracks.get(0));
			statement.setBoolean(3, info.world3Tracks.get(1));
			statement.setBoolean(4, info.world3Tracks.get(2));
			statement.setBoolean(5, info.world3Tracks.get(3));
			statement.setBoolean(6, info.world3Tracks.get(4));
			statement.setBoolean(7, info.world3Tracks.get(5));
			statement.setString(8, uuid.toString());
			statement.executeUpdate();
			statement.close();
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}