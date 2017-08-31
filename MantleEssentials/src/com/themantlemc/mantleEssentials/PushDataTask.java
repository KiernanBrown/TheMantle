package com.themantlemc.mantleEssentials;

import java.sql.PreparedStatement;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PushDataTask extends BukkitRunnable {

	private UUID uuid;
	private String pName;
	private MantleEssentials mantle;
	
	public PushDataTask(UUID id, String nm, MantleEssentials m) {
		uuid = id;
		pName = nm;
		mantle = m;
	}

	@Override
	public void run() {
		PlayerInfo info = mantle.getPlayerInfo(pName);
		
		if(info == null)
		{
			return;
		}
		
		// Update General
		try
		{
			PreparedStatement statement = MantleEssentials.getInstance().getConnection().prepareStatement("UPDATE `general` SET `name` = ?, `rank` = ?, `coins` = ? WHERE `uuid` = ?");
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
	}
}