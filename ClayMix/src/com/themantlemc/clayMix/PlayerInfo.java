package com.themantlemc.clayMix;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerInfo {
	
	public PlayerInfo(String name) {
		playerName = name;
	}
	
	// Information about the player
	// This is all changed when the LoadDataTask is run, and the correct information is gotten from the MySQL Database
	String playerName = "";
	
	String rank = "Player";
	int mantlecoins = 0;
	
	int gamesPlayed = 0;
	int kills = 0;
	int score = 0;
	
	String powerup = "";
	int powerupTime = 0;
	
	int place = 0;
	int pointChange = 0;
}