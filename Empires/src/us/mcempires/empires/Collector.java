package us.mcempires.empires;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;

public class Collector {
	
	TeamManager team = null;
	Location location;
	int maxHealth;
	int health;
	int coolDownTimer = -1;
	IronGolem golem = null;
	int notificationTimer = 0;
	
	public Collector(TeamManager t, Location loc)
	{
		team = t;
		location = loc;
	}
}
