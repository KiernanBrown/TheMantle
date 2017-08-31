package us.mcempires.empires;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class PlayerInfo {

	String displayName;
	String currentClass;
	String chatType;
	Player shotBy = null;
	Boolean baseProtection;
	float respawnTimer = -1.0f;
	int collectorTimer = 0;
	int toolsTimer = 0;
	int killstreak = 0;
	BossBar respawnBar = null;
	
	String votedMap = "";
	
	public PlayerInfo(String name)
	{
		displayName = name;
		currentClass = "Civilian";
		chatType = "Team";
		baseProtection = false;
	}
}
