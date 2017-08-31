package com.themantlemc.controlHeroes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.scoreboard.Team;

public class PlayerInfo {

	// Information about the player
	// This is all changed when the LoadDataTask is run, and the correct information is gotten from the MySQL Database
	String playerName = "";
	
	String rank = "Player";
	int mantlecoins = 0;
	
	int kills = 0;
	int deaths = 0;
	int totalKills = 0;
	int totalDeaths = 0;
	int killstreak = 0;
	int highestStreak = 0;
	int prestige = 1;
	int level = 1;
	int xp = 0;
	int xpToLevel = 9999999;
	int wins = 0;
	int games = 0;
	int captures = 0;
	int totalCaptures = 0;
	String currentClass = "Warrior";
	String changeClass = "";
	String currentPerk = "Powersurge";
	int coins = 0;
	int gameCoins = 0;
	int protectTimer = 0;
	boolean wandFire = true;
	
	// Perk levels
	int powersurge = 1;
	int ironskin = 1;
	int immortal = 1;
	int explosive = 1;
	int resourceful = 1;
	int heroic = 1;
	int godslayer = 1;
	
	// Heroes code
	int heroPoints = 0;
	
	String hero1 = "";
	String hero2 = "";
	String hero3 = "";
	String currentHero = "";
	String currentPack = "All Rounder";
	List<String> unlockedPacks = new ArrayList<String>();
	String votedMap = "";
	
	int heroMaxHealth = 0;
	double heroHealth = 0;
	
	boolean isHero = false;
	boolean heroRespawn = false;
	boolean heroAbility1 = true;
	boolean heroAbility2 = true;
	boolean heroActive = false;
	
	boolean nullified = false;
	
	boolean pointBonus = false;
	
	boolean contagion = false;
	Team contagionTeam = null;
	
	public PlayerInfo(String nm)
	{
		playerName = nm;
	}
	
	// Calculate the xp to the next level
	public void calculateXP()
	{
		int base = 100;
		for(int i = 1; i < level; i++)
		{
			if(i < 10)
			{
				base += 35;
			}
			else
			{
				base = (int)Math.pow(base, 1.008);
			}
		}
		xpToLevel = base;
	}
	
	// Get the cost of a perk upgrade
	public int perkCost(String perk)
	{
		int perkLevel = 0;
		switch(perk)
		{
			case "Powersurge": perkLevel = powersurge;
				break;
		}
		switch(perkLevel)
		{
			case 1: return 100;
			case 2: return 300;
			case 3: return 500;
		}
		
		return 0;
	}
}
