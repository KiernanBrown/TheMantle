package com.themantlemc.theParkourProject;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

public class ParkourWorld 
{
	World world;
	String worldName;
	TheParkourProject parkour;
	
	List<Location> trackSpawns = new ArrayList<Location>();
	
	public ParkourWorld(TheParkourProject park, World wrld, String nm)
	{
		parkour= park;
		world= wrld;
		worldName = nm;
	}
}
