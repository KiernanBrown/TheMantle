package com.themantlemc.theParkourProject;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerInfo 
{
	String displayName = "";
	String rank = "Player";
	
	int mantlecoins = 0;
	
	int currentWorld = 0;
	int currentTrack = 0;
	
	int parkourPoints = 0;
	
	Location checkpoint;
	
	List<Boolean> world1Tracks = new ArrayList<Boolean>();
	List<Boolean> world2Tracks = new ArrayList<Boolean>();
	List<Boolean> world3Tracks = new ArrayList<Boolean>();
	
	Boolean hiding = false;
	
	public PlayerInfo(String name)
	{
		displayName = name;
		for(int i = 0; i < 6; i++)
		{
			world1Tracks.add(false);
			world2Tracks.add(false);
			world3Tracks.add(false);
		}
	}
	
	public void calculatePoints()
	{
		parkourPoints = 0;
		for(Boolean b : world1Tracks)
		{
			if(b)
			{
				parkourPoints++;
			}
		}
		for(Boolean b : world2Tracks)
		{
			if(b)
			{
				parkourPoints++;
			}
		}
		for(Boolean b : world3Tracks)
		{
			if(b)
			{
				parkourPoints++;
			}
		}
		Player player = Bukkit.getPlayer(displayName);
		player.setLevel(parkourPoints);
		player.setExp(0);
	}
}