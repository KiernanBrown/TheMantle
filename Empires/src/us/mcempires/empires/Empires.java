package us.mcempires.empires;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class Empires extends JavaPlugin {
	
    TeamManager redTeam = new TeamManager("Red", this);
    TeamManager blueTeam = new TeamManager("Blue", this);
    TeamManager greenTeam = new TeamManager("Green", this);
    TeamManager purpleTeam = new TeamManager("Purple", this);
    
    float gameTimer = 1800.0f;
    float startTimer = 90.0f;
    Boolean gameStarting = false;
    Boolean gameRunning = false;
    Boolean gameEnding = false;
    BossBar timerBar;
    BossBar startBar;
    BossBar endBar;
    BossBar lobbyBar;
    
    int requiredPlayers = 2;
    int collectorCost = 40;
    
    BukkitScheduler scheduler;
    
    List<PlayerInfo> playerInfoList = new ArrayList<PlayerInfo>();
    
	Location redSpawn = null;
	Location redProt1 = null;
	Location redProt2 = null;
	Location blueSpawn = null;
	Location blueProt1 = null;
	Location blueProt2 = null;
	Location greenSpawn = null;
	Location greenProt1 = null;
	Location greenProt2 = null;
	Location purpleSpawn = null;
	Location purpleProt1 = null;
	Location purpleProt2 = null;
	
	//Collector collector1;
	//Collector collector2;
	
	List<Collector> collectors = null;
	List<Location> sheepSpawns = null;
	List<Location> pigSpawns = null;
	List<Location> cowSpawns = null;
	
	String mapName;
	World gameWorld;
	World lobby;
	World source;
	
	List<String> mapNames = new ArrayList<String>();
	List<Integer> mapVotes = new ArrayList<Integer>();
	
	// Fired when plugin is first enabled
	@Override
	public void onEnable() 
	{
	    getServer().getPluginManager().registerEvents(new EmpiresListener(this), this);
	    getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	    
	    this.getCommand("team").setExecutor(new TeamSelect(this, redTeam, blueTeam, greenTeam, purpleTeam));
	    this.getCommand("empiresstart").setExecutor(new Start(this));
	    this.getCommand("chat").setExecutor(new Chat(this));
	    this.getCommand("empiresend").setExecutor(new End(this));
	    
	    lobby = Bukkit.getServer().createWorld(new WorldCreator("MinigameLobby"));
		
	    mapNames.add("Vitinn");
	    mapNames.add("University");
	    mapVotes.add(0);
	    mapVotes.add(0);
	    
	    redTeam.setScoreboard();
	    redTeam.updateScoreboard();
	    redTeam.updateLevel();
	    blueTeam.setScoreboard();
	    blueTeam.updateScoreboard();
	    blueTeam.updateLevel();
	    greenTeam.setScoreboard();
	    greenTeam.updateScoreboard();
	    greenTeam.updateLevel();
	    purpleTeam.setScoreboard();
	    purpleTeam.updateScoreboard();
	    purpleTeam.updateLevel();
	    
	    scheduler = getServer().getScheduler();
	    
	    // Update base protection every tick
	    scheduler.scheduleSyncRepeatingTask(this, new Runnable(){
	    	@Override
	    	public void run(){
	    		if(gameRunning)
	    		{
	    			for(Player p : Bukkit.getOnlinePlayers())
	    			{
	    				PlayerInfo info = getPlayerInfo(p.getName());
	    				Boolean inBase = false;
	    				Location pLocation = p.getLocation();
	    				switch(ChatColor.stripColor(getPlayerTeam(p).teamName))
	    				{
	    					case "Red Team":
	    						if(pLocation.getY() >= redProt1.getY() && pLocation.getY() <= redProt2.getY())
	    						{
	    							if(pLocation.getX() >= redProt1.getX() && pLocation.getZ() >= redProt1.getZ())
			    					{
			    						if(pLocation.getX() <= redProt2.getX() && pLocation.getZ() <= redProt2.getZ())
				    					{
				    						inBase = true;
				    					}
			    					}
	    						}
	    						break;
	    					case "Blue Team": 
	    						if(pLocation.getY() >= blueProt1.getY() && pLocation.getY() <= blueProt2.getY())
	    						{
	    							if(pLocation.getX() >= blueProt1.getX() && pLocation.getZ() >= blueProt1.getZ())
			    					{
			    						if(pLocation.getX() <= blueProt2.getX() && pLocation.getZ() <= blueProt2.getZ())
				    					{
				    						inBase = true;
				    					}
			    					}
	    						}
	    						break;
	    					case "Green Team": 
	    						if(pLocation.getY() >= greenProt1.getY() && pLocation.getY() <= greenProt2.getY())
	    						{
	    							if(pLocation.getX() >= greenProt1.getX() && pLocation.getZ() >= greenProt1.getZ())
			    					{
			    						if(pLocation.getX() <= greenProt2.getX() && pLocation.getZ() <= greenProt2.getZ())
				    					{
				    						inBase = true;
				    					}
			    					}
	    						}
	    						break;
	    					case "Purple Team": 
	    						if(pLocation.getY() >= purpleProt1.getY() && pLocation.getY() <= purpleProt2.getY())
	    						{
	    							if(pLocation.getX() >= purpleProt1.getX() && pLocation.getZ() >= purpleProt1.getZ())
			    					{
			    						if(pLocation.getX() <= purpleProt2.getX() && pLocation.getZ() <= purpleProt2.getZ())
				    					{
				    						inBase = true;
				    					}
			    					}
	    						}
	    						break;
	    				}
	    				if(info.baseProtection)
	    				{
	    					if(!inBase)
	    					{
	    						info.baseProtection = false;
	    						p.sendMessage(ChatColor.GOLD + "[Base Protection] You have left your base protection!");
	    					}
	    				}
	    				else
	    				{
	    					if(inBase)
	    					{
	    						info.baseProtection = true;
	    						p.sendMessage(ChatColor.GOLD + "[Base Protection] You have entered your base protection!");
	    					}
	    				}
	    			}
	    		}
	    	}
	    }, 0L, 1L);

	    scheduler.scheduleSyncRepeatingTask(this, new Runnable(){
	    	@Override
	    	public void run()
	    	{
	    		// Forward if the server is joinable to the lobby
	    		
	    		
	    	    // Update lobby bar every second
	    		if(!gameStarting && !gameRunning && !gameEnding)
	    		{
	    			String lobbyStr = (requiredPlayers - Bukkit.getOnlinePlayers().size()) + " more players needed to start the game!";
	    			for(Player player : Bukkit.getOnlinePlayers())
	    			{
	    				if(lobbyBar == null)
	    				{
		    				lobbyBar = Bukkit.createBossBar(lobbyStr, BarColor.WHITE, BarStyle.SOLID);
	    				}
	    				if(!lobbyBar.getPlayers().contains(player))
	    				{
		    				lobbyBar.addPlayer(player);
	    				}
	    				lobbyBar.setTitle(ChatColor.GOLD + lobbyStr);
	    				lobbyBar.setProgress(0);
	    			}
	    		}
	    		
	    	    // Update game timer, boss bar, collectors, and respawnTimer every second
	    		if(gameRunning)
	    		{
	    			gameTimer--;
	    			String timerStr = "Game Time: " + (int)((int)gameTimer / 60) + ":" + String.format("%02d",(int)((int)gameTimer % 60));
	    			for(Player player : Bukkit.getOnlinePlayers())
	    			{
	    				if(gameTimer == 1800.0f)
	    				{
	    					player.sendMessage(ChatColor.GREEN + "The game has started! Good luck!");
	    				}
	    				
	    				if(timerBar == null)
	    				{
		    				timerBar = Bukkit.createBossBar(timerStr, BarColor.GREEN, BarStyle.SEGMENTED_6);
	    				}
	    				if(!timerBar.getPlayers().contains(player))
	    				{
		    				timerBar.addPlayer(player);
	    				}
	    				timerBar.setTitle(ChatColor.GREEN + timerStr);
	    				if(gameTimer <= 1800.0f) 
	    				{
	    					timerBar.setProgress(gameTimer / 1800.0);
	    				}
	    				
    					PlayerInfo info = getPlayerInfo(player.getName());
    					
	    				// Player gather tools timer
    					if(info.toolsTimer != 0)
    					{
    						info.toolsTimer--;
    					}
    					
    					if(info.collectorTimer != 0)
    					{
    						info.collectorTimer--;
    					}
	    				
	    				if(info.respawnTimer != -1)
	    				{
	    					// Respawn Player
	    					if(info.respawnTimer == 0)
	    					{
	    						TeamManager playerTeam = getPlayerTeam(player);
	    						switch(ChatColor.stripColor(playerTeam.teamName))
	    						{
	    							case "Red Team": player.teleport(redSpawn);
	    							break;
	    							case "Blue Team": player.teleport(blueSpawn);
	    							break;
	    							case "Green Team": player.teleport(greenSpawn);
	    							break;
	    							case "Purple Team": player.teleport(purpleSpawn);
	    							break;
	    						}
	    						player.sendMessage(ChatColor.GOLD + "You have respawned!");
	    						info.respawnBar.removeAll();
	    						info.respawnBar = null;
	    						info.respawnTimer = -1;
	    					}
	    					
	    					// Update Boss Bar
	    					if(info.respawnTimer != -1)
	    					{
		    					String respawnStr = "Respawning in: " + (int)((int)info.respawnTimer / 60) + ":" + String.format("%02d",(int)((int)info.respawnTimer % 60));
		    					if(info.respawnBar == null)
		    					{
		    						info.respawnBar = Bukkit.createBossBar(ChatColor.GOLD + respawnStr, BarColor.WHITE, BarStyle.SOLID);
		    					}
		    					if(!info.respawnBar.getPlayers().contains(player))
		    					{
		    						info.respawnBar.addPlayer(player);
		    					}
		    					info.respawnBar.setTitle(ChatColor.GOLD + respawnStr);
		    					info.respawnBar.setProgress(info.respawnTimer / 120.0);
		    					
		    					// Decrease respawn timer
		    					info.respawnTimer--;
	    					}
	    				}
	    			}
	    			
	    			// Make collectors placeable
	    			if(gameTimer == 1200)
	    			{
	    				for(Player p : Bukkit.getOnlinePlayers())
	    				{
	    					p.sendMessage(ChatColor.GOLD + "[Collector] Collectors can now be placed!");
	    				}
	    				for(Collector c : collectors)
	    				{
	    					
	    					Location cLoc = c.location.add(new Vector(0, 1, 0));
	    					if(!cLoc.getChunk().isLoaded())
	    					{
	    						cLoc.getChunk().load();
	    					}
	    					cLoc.getBlock().setType(Material.AIR);
	    				}
	    			}
	    			
	    			if(gameTimer == 0)
	    			{
	    				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "empiresend");
	    			}
	    			
    				// Collector cooldowns
	    			for(Collector c : collectors)
	    			{
	    				if(c.coolDownTimer == 0)
	    				{
	    					c.location.add(new Vector(0, -1, 0)).getBlock().setType(Material.BEACON);
	    					c.coolDownTimer = -1;
	    				}
	    				else if(c.coolDownTimer > -1)
	    				{
	    					c.coolDownTimer--;
	    				}
	    				if(c.notificationTimer != 0)
	    				{
	    					c.notificationTimer--;
	    				}
	    			}
	    		}
	    	}
	    }, 0L, 20L);
	    
	    // Update start timer
	    scheduler.scheduleSyncRepeatingTask(this, new Runnable(){
	    	@Override
	    	public void run(){
	    		if(gameStarting)
	    		{
	    			startTimer--;
	    			String timerStr = "Game Starting In: " + (int)((int)startTimer / 60) + ":" + String.format("%02d",(int)((int)startTimer % 60));
	    			for(Player player : Bukkit.getOnlinePlayers())
	    			{
	    				if(startBar == null)
	    				{
		    				startBar = Bukkit.createBossBar(timerStr, BarColor.WHITE, BarStyle.SOLID);
	    				}
	    				if(!startBar.getPlayers().contains(player))
	    				{
		    				startBar.addPlayer(player);
	    				}
	    				startBar.setTitle(ChatColor.GOLD + timerStr);
	    				startBar.setProgress(startTimer / 90.0);
	    			}
	    			
	    			if(startTimer == 5)
	    			{
	    				tallyVotes();
	    				setMap();
	    			}
	    			
	    			if(startTimer == 0)
	    			{
	    				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "empiresstart");
	    				startBar.removeAll();
	    				gameStarting = false;
	    			}
	    		}
	    	}
	    }, 0L, 20L);
	}
	
	
	public void startCountdown()
	{
		if(lobbyBar != null)
		{
			lobbyBar.removeAll();
		}
		gameStarting = true;
	}
	
	public void setMap()
	{	
		// Load in the maps we are playing and set our locations
		switch(mapName)
		{
			/*case "Tribute": 
				redSpawn = new Location(gameWorld, -496, 26, -642);
				redProt1 = new Location(gameWorld, -510, 23, -728);
				redProt2 = new Location(gameWorld, -426, 23, -628);
				blueSpawn = new Location(gameWorld, -187, 26, -658);
				blueProt1 = new Location(gameWorld, -273, 23, -728);
				blueProt2 = new Location(gameWorld, -173, 23, -644);
				greenSpawn = new Location(gameWorld, -512, 26, -951);
				greenProt1 = new Location(gameWorld, -526, 23, -965);
				greenProt2 = new Location(gameWorld, -426, 23, -881);
				purpleSpawn = new Location(gameWorld, -203, 26, -967);
				purpleProt1 = new Location(gameWorld, -273, 23, -981);
				purpleProt2 = new Location(gameWorld, -189, 23, -881);
				//collector1 = new Collector(null, new Location(gameWorld, -340, 29, -747));
				//collector2 = new Collector(null, new Location(gameWorld, -359, 29, -862));
				
				// Set animal spawn locations
				sheepSpawns = new ArrayList<Location>();
				sheepSpawns.add(new Location(gameWorld, -439, 27, -649));
				sheepSpawns.add(new Location(gameWorld, -194, 27, -715));
				sheepSpawns.add(new Location(gameWorld, -260, 27, -960));
				sheepSpawns.add(new Location(gameWorld, -505, 27, -894));
				
				pigSpawns = new ArrayList<Location>();
				//pigSpawns.add(new Location(collector1.location.getWorld(), collector1.location.getX(), collector1.location.getY() + 2, collector1.location.getZ()));
				//pigSpawns.add(new Location(collector2.location.getWorld(), collector2.location.getX(), collector2.location.getY() + 2, collector2.location.getZ()));
				
				cowSpawns = new ArrayList<Location>();
				//cowSpawns.add(new Location(collector1.location.getWorld(), collector1.location.getX(), collector1.location.getY() + 2, collector1.location.getZ()));
				//cowSpawns.add(new Location(collector2.location.getWorld(), collector2.location.getX(), collector2.location.getY() + 2, collector2.location.getZ()));
				break;*/
				
			case "Vitinn": 
				gameWorld = Bukkit.getServer().createWorld(new WorldCreator("Vitinn"));
				gameWorld.setAutoSave(false);
				gameWorld.setGameRuleValue("doMobSpawning", "false");
				gameWorld.setGameRuleValue("doDaylightCycle", "false");
				gameWorld.setGameRuleValue("doFireTick", "false");
				
				// Set spawn points and base protection
				redSpawn = new Location(gameWorld, 189, 88, -189);
				redProt1 = new Location(gameWorld, 198, 85, -198);
				redProt2 = new Location(gameWorld, 158, 107, -158);
				blueSpawn = new Location(gameWorld, 189, 88, -37);
				blueProt1 = new Location(gameWorld, 198, 85, -28);
				blueProt2 = new Location(gameWorld, 158, 107, -68);
				greenSpawn = new Location(gameWorld, 37, 88, -37);
				greenProt1 = new Location(gameWorld, 28, 85, -28);
				greenProt2 = new Location(gameWorld, 68, 107, -68);
				purpleSpawn = new Location(gameWorld, 37, 88, -189);
				purpleProt1 = new Location(gameWorld, 28, 85, -198);
				purpleProt2 = new Location(gameWorld, 68, 23, -158);
				
				// Collector locations
				collectors = new ArrayList<Collector>();
				collectors.add(new Collector(null, new Location(gameWorld, 171, 86, -172)));
				collectors.add(new Collector(null, new Location(gameWorld, 172, 86, -55)));
				collectors.add(new Collector(null, new Location(gameWorld, 55, 86, -54)));
				collectors.add(new Collector(null, new Location(gameWorld, 54, 86, -171)));
				
				// Set animal spawn locations
				sheepSpawns = new ArrayList<Location>();
				sheepSpawns.add(new Location(gameWorld, 153, 83, -153));
				sheepSpawns.add(new Location(gameWorld, 153, 83, -73));
				sheepSpawns.add(new Location(gameWorld, 73, 83, -73));
				sheepSpawns.add(new Location(gameWorld, 73, 83, -153));
				
				pigSpawns = new ArrayList<Location>();
				pigSpawns.add(new Location(gameWorld, 113, 73, -96));
				pigSpawns.add(new Location(gameWorld, 113, 73, -130));
				
				cowSpawns = new ArrayList<Location>();
				cowSpawns.add(new Location(gameWorld, 96, 73, -113));
				cowSpawns.add(new Location(gameWorld, 130, 73, -113));
				break;
				
			case "University":
				break;
		}
	}
	
	public TeamManager getPlayerTeam(Player p)
	{
		TeamManager playerTeam = null;
		if(redTeam.team.hasPlayer(p))
		{
			playerTeam = redTeam;
		}
		else if(blueTeam.team.hasPlayer(p))
		{
			playerTeam = blueTeam;
		}
		else if(greenTeam.team.hasPlayer(p))
		{
			playerTeam = greenTeam;
		}
		else if(purpleTeam.team.hasPlayer(p))
		{
			playerTeam = purpleTeam;
		}
		return playerTeam;
	}
	
	public void endGame()
	{
		timerBar.removeAll();
		
		// Add points from level
		scheduler.scheduleSyncDelayedTask(this, new Runnable(){
	    	@Override
	    	public void run(){
	    		for(Player player : Bukkit.getOnlinePlayers())
	    		{	
	    			player.sendMessage("");
	    			player.sendMessage("");
	    			player.sendMessage(ChatColor.GOLD + "Adding Empire Points gained from Levelling Up!");
	    			player.sendMessage(ChatColor.RED + "Red Team: " + ChatColor.WHITE + redTeam.levelPoints +". " + ChatColor.GOLD + "Total Points: " + ChatColor.WHITE + redTeam.endPoints + " -> " + (redTeam.endPoints + redTeam.levelPoints));
	    			player.sendMessage(ChatColor.BLUE + "Blue Team: " + ChatColor.WHITE + blueTeam.levelPoints +". " + ChatColor.GOLD + "Total Points: " + ChatColor.WHITE + blueTeam.endPoints + " -> " + (blueTeam.endPoints + blueTeam.levelPoints));
	    			player.sendMessage(ChatColor.DARK_GREEN + "Green Team: " + ChatColor.WHITE + greenTeam.levelPoints +". " + ChatColor.GOLD + "Total Points: " + ChatColor.WHITE + greenTeam.endPoints + " -> " + (greenTeam.endPoints + greenTeam.levelPoints));
	    			player.sendMessage(ChatColor.DARK_PURPLE + "Purple Team: " + ChatColor.WHITE + purpleTeam.levelPoints +". " + ChatColor.GOLD + "Total Points: " + ChatColor.WHITE + purpleTeam.endPoints + " -> " + (purpleTeam.endPoints + purpleTeam.levelPoints));
	    			player.sendMessage("");
	    			player.sendMessage("");
	    		}
	    		redTeam.endPoints += redTeam.levelPoints;
	    		blueTeam.endPoints += blueTeam.levelPoints;
	    		greenTeam.endPoints += greenTeam.levelPoints;
	    		purpleTeam.endPoints += purpleTeam.levelPoints;
	    	}
		}, 120L);
		
		// Add points from collectors
		scheduler.scheduleSyncDelayedTask(this, new Runnable(){
	    	@Override
	    	public void run(){
	    		for(Player player : Bukkit.getOnlinePlayers())
	    		{	
	    			player.sendMessage("");
	    			player.sendMessage("");
	    			player.sendMessage(ChatColor.GOLD + "Adding Empire Points gained from Collectors!");
	    			player.sendMessage(ChatColor.RED + "Red Team: " + ChatColor.WHITE + redTeam.collectorPoints +". " + ChatColor.GOLD + "Total Points: " + ChatColor.WHITE + redTeam.endPoints + " -> " + (redTeam.endPoints + redTeam.collectorPoints));
	    			player.sendMessage(ChatColor.BLUE + "Blue Team: " + ChatColor.WHITE + blueTeam.collectorPoints +". " + ChatColor.GOLD + "Total Points: " + ChatColor.WHITE + blueTeam.endPoints + " -> " + (blueTeam.endPoints + blueTeam.collectorPoints));
	    			player.sendMessage(ChatColor.DARK_GREEN + "Green Team: " + ChatColor.WHITE + greenTeam.collectorPoints +". " + ChatColor.GOLD + "Total Points: " + ChatColor.WHITE + greenTeam.endPoints + " -> " + (greenTeam.endPoints + greenTeam.collectorPoints));
	    			player.sendMessage(ChatColor.DARK_PURPLE + "Purple Team: " + ChatColor.WHITE + purpleTeam.collectorPoints +". " + ChatColor.GOLD + "Total Points: " + ChatColor.WHITE + purpleTeam.endPoints + " -> " + (purpleTeam.endPoints + purpleTeam.collectorPoints));
	    			player.sendMessage("");
	    			player.sendMessage("");
	    		}
	    		redTeam.endPoints += redTeam.collectorPoints;
	    		blueTeam.endPoints += blueTeam.collectorPoints;
	    		greenTeam.endPoints += greenTeam.collectorPoints;
	    		purpleTeam.endPoints += purpleTeam.collectorPoints;
	    	}
		}, 240L);
		
		// Add points from kills
		scheduler.scheduleSyncDelayedTask(this, new Runnable(){
	    	@Override
	    	public void run(){
	    		for(Player player : Bukkit.getOnlinePlayers())
	    		{	
	    			player.sendMessage("");
	    			player.sendMessage("");
	    			player.sendMessage(ChatColor.GOLD + "Adding Empire Points gained from Kills!");
	    			player.sendMessage(ChatColor.RED + "Red Team: " + ChatColor.WHITE + redTeam.killPoints +". " + ChatColor.GOLD + "Total Points: " + ChatColor.WHITE + redTeam.endPoints + " -> " + (redTeam.endPoints + redTeam.killPoints));
	    			player.sendMessage(ChatColor.BLUE + "Blue Team: " + ChatColor.WHITE + blueTeam.killPoints +". " + ChatColor.GOLD + "Total Points: " + ChatColor.WHITE + blueTeam.endPoints + " -> " + (blueTeam.endPoints + blueTeam.killPoints));
	    			player.sendMessage(ChatColor.DARK_GREEN + "Green Team: " + ChatColor.WHITE + greenTeam.killPoints +". " + ChatColor.GOLD + "Total Points: " + ChatColor.WHITE + greenTeam.endPoints + " -> " + (greenTeam.endPoints + greenTeam.killPoints));
	    			player.sendMessage(ChatColor.DARK_PURPLE + "Purple Team: " + ChatColor.WHITE + purpleTeam.killPoints +". " + ChatColor.GOLD + "Total Points: " + ChatColor.WHITE + purpleTeam.endPoints + " -> " + (purpleTeam.endPoints + purpleTeam.killPoints));
	    			player.sendMessage("");
	    			player.sendMessage("");
	    		}
	    		redTeam.endPoints += redTeam.killPoints;
	    		blueTeam.endPoints += blueTeam.killPoints;
	    		greenTeam.endPoints += greenTeam.killPoints;
	    		purpleTeam.endPoints += purpleTeam.killPoints;
	    	}
		}, 360L);
		
		// Add points from gold
		scheduler.scheduleSyncDelayedTask(this, new Runnable(){
	    	@Override
	    	public void run(){
	    		for(Player player : Bukkit.getOnlinePlayers())
	    		{	
	    			player.sendMessage("");
	    			player.sendMessage("");
	    			player.sendMessage(ChatColor.GOLD + "Converting remaining gold to Empire Points!");
	    			player.sendMessage(ChatColor.RED + "Red Team: " + ChatColor.WHITE + redTeam.gold +". " + ChatColor.GOLD + "Total Points: " + ChatColor.WHITE + redTeam.endPoints + " -> " + (redTeam.endPoints + redTeam.gold));
	    			player.sendMessage(ChatColor.BLUE + "Blue Team: " + ChatColor.WHITE + blueTeam.gold +". " + ChatColor.GOLD + "Total Points: " + ChatColor.WHITE + blueTeam.endPoints + " -> " + (blueTeam.endPoints + blueTeam.gold));
	    			player.sendMessage(ChatColor.DARK_GREEN + "Green Team: " + ChatColor.WHITE + greenTeam.gold +". " + ChatColor.GOLD + "Total Points: " + ChatColor.WHITE + greenTeam.endPoints + " -> " + (greenTeam.endPoints + greenTeam.gold));
	    			player.sendMessage(ChatColor.DARK_PURPLE + "Purple Team: " + ChatColor.WHITE + purpleTeam.gold +". " + ChatColor.GOLD + "Total Points: " + ChatColor.WHITE + purpleTeam.endPoints + " -> " + (purpleTeam.endPoints + purpleTeam.gold));
	    			player.sendMessage("");
	    			player.sendMessage("");
	    		}
	    		redTeam.endPoints += redTeam.gold;
	    		blueTeam.endPoints += blueTeam.gold;
	    		greenTeam.endPoints += greenTeam.gold;
	    		purpleTeam.endPoints += purpleTeam.gold;
	    	}
		}, 480L);
		
		// Check Winner
		scheduler.scheduleSyncDelayedTask(this, new Runnable(){
	    	@Override
	    	public void run(){
	    		TeamManager winningTeam = purpleTeam;
	    		if(redTeam.endPoints > blueTeam.endPoints && redTeam.endPoints > greenTeam.endPoints && redTeam.endPoints > purpleTeam.endPoints)
	    		{
	    			winningTeam = redTeam;
	    		}
	    		else if(blueTeam.endPoints > redTeam.endPoints && blueTeam.endPoints > greenTeam.endPoints && blueTeam.endPoints > purpleTeam.endPoints)
	    		{
	    			winningTeam = blueTeam;
	    		}
	    		else if(greenTeam.endPoints > redTeam.endPoints && greenTeam.endPoints > blueTeam.endPoints && greenTeam.endPoints > purpleTeam.endPoints)
	    		{
	    			winningTeam = greenTeam;
	    		}
	    		else if(purpleTeam.endPoints > redTeam.endPoints && purpleTeam.endPoints > blueTeam.endPoints && purpleTeam.endPoints > greenTeam.endPoints)
	    		{
	    			winningTeam = purpleTeam;
	    		}
	    		for(Player player : Bukkit.getOnlinePlayers())
	    		{	
	    			player.sendMessage("");
	    			player.sendMessage("");
	    			player.sendMessage(ChatColor.GOLD + "The winner is " + winningTeam.displayName + "!");
	    			player.sendMessage(ChatColor.GOLD + "Final Points:");
	    			player.sendMessage(ChatColor.RED + "Red Team: " + ChatColor.WHITE + redTeam.endPoints);
	    			player.sendMessage(ChatColor.BLUE + "Blue Team: " + ChatColor.WHITE + blueTeam.endPoints);
	    			player.sendMessage(ChatColor.DARK_GREEN + "Green Team: " + ChatColor.WHITE + greenTeam.endPoints);
	    			player.sendMessage(ChatColor.DARK_PURPLE + "Purple Team: " + ChatColor.WHITE + purpleTeam.endPoints);
	    			player.sendMessage("");
	    			player.sendMessage("");
	    		}
	    	}
		}, 600L);
		
		// Move players to lobby
		scheduler.scheduleSyncDelayedTask(this, new Runnable(){
	    	@Override
	    	public void run()
	    	{
	    		for(Player player : Bukkit.getOnlinePlayers())
	    		{
	    			player.sendMessage(ChatColor.GOLD + "Sending you to lobby.");
	    			sendToServer(player, "lobby");
	    		}
	    	}
		}, 800L);
		
		// Stop the server
		scheduler.scheduleSyncDelayedTask(this, new Runnable(){
	    	@Override
	    	public void run()
	    	{
    			Bukkit.shutdown();
	    	}
		}, 900L);
	}
	
	// Fired when plugin is disabled
	@Override
	public void onDisable() 
	{
		
	}
	
	public void spawnSheep()
	{
		for(Location l : sheepSpawns)
		{
			double x = l.getX() + (int)Math.floor((Math.random() * 10) - 5);
			double z = l.getZ() + (int)Math.floor((Math.random() * 10) - 5);
			Location spawn = new Location(l.getWorld(), x, l.getY(), z);
			if(!spawn.getChunk().isLoaded())
			{
				spawn.getChunk().load();
			}
			spawn.getWorld().spawnEntity(spawn, EntityType.SHEEP);
		}
	}
	
	public void spawnPig()
	{
		for(Location l : pigSpawns)
		{
			double x = l.getX() + (int)Math.floor((Math.random() * 40) - 20);
			double z = l.getZ() + (int)Math.floor((Math.random() * 40) - 20);
			Location spawn = new Location(l.getWorld(), x, l.getY(), z);
			if(!spawn.getChunk().isLoaded())
			{
				spawn.getChunk().load();
			}
			spawn.getWorld().spawnEntity(spawn, EntityType.PIG);
		}
	}
	
	public void spawnCow()
	{
		for(Location l : cowSpawns)
		{
			double x = l.getX() + (int)Math.floor((Math.random() * 40) - 20);
			double z = l.getZ() + (int)Math.floor((Math.random() * 40) - 20);
			Location spawn = new Location(l.getWorld(), x, l.getY(), z);
			if(!spawn.getChunk().isLoaded())
			{
				spawn.getChunk().load();
			}
			spawn.getWorld().spawnEntity(spawn, EntityType.COW);
		}
	}
	
	// Code to handle Player selecting teams
	public void openMapMenu(Player p)
	{
		Inventory mapInv = Bukkit.createInventory(null, 9, ChatColor.BOLD + "" + ChatColor.DARK_GRAY + "Map Voting!");
		PlayerInfo info = getPlayerInfo(p.getName());
			
		for(int i = 0; i < mapNames.size(); i++)
		{
			ItemStack mapItem = new ItemStack(Material.EMPTY_MAP);
			ItemMeta mapMeta = mapItem.getItemMeta();
			mapMeta.setDisplayName(ChatColor.GOLD + mapNames.get(i));
			if(info.votedMap.equals(mapNames.get(i)))
			{
				mapMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1, true);
				mapMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Votes: " + mapVotes.get(i), ChatColor.AQUA + "You have voted for this!")));
			}
			else
			{
				mapMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Votes: " + mapVotes.get(i))));
			}
			mapItem.setItemMeta(mapMeta);
			mapInv.setItem(2 * i + 1, mapItem);
		}
			
		p.openInventory(mapInv);
	}
		
		public void countVotes()
		{
			for(int i = 0; i < mapNames.size(); i++)
			{
				mapVotes.set(i, 0);
			}
			for(Player p : Bukkit.getOnlinePlayers())
			{
				PlayerInfo info = getPlayerInfo(p.getName());
				for(int i = 0; i < mapNames.size(); i++)
				{
					if(info.votedMap.equals(mapNames.get(i)))
					{
						int votes = mapVotes.get(i);
						votes++;
						mapVotes.set(i, votes);
					}
				}
			}
		}
		
		public void tallyVotes()
		{
			countVotes();
			int highestVotes = -1;
			String votedMap = "";
			for(int i = 0; i < mapNames.size(); i++)
			{
				if(mapVotes.get(i) > highestVotes)
				{
					highestVotes = mapVotes.get(i);
					votedMap = mapNames.get(i);
				}
			}
			
			mapName = votedMap;
			
			for(Player p : Bukkit.getOnlinePlayers())
			{
				p.getInventory().remove(Material.BOOK);
				p.sendMessage(ChatColor.GOLD + "Map Voting has closed! " + ChatColor.AQUA + mapName + ChatColor.GOLD + " has been selected!");
			}
		}
	
	public void sendToServer(Player player, String targetServer)
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try
		{
			out.writeUTF("Connect");
			out.writeUTF(targetServer);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		player.sendPluginMessage(this,  "BungeeCord", b.toByteArray());
	}
	
	public Boolean isJoinable()
	{
		if(gameRunning || gameEnding)
		{
			return false;
		}
		else return true;
	}
	
	/*
	// Broken map code
	public boolean deleteWorld(File path) {
	      if(path.exists()) {
	          File files[] = path.listFiles();
	          for(int i=0; i<files.length; i++) {
	              if(files[i].isDirectory()) {
	                  deleteWorld(files[i]);
	              } else {
	                  files[i].delete();
	              }
	          }
	      }
	      return(path.delete());
	}*/
	
	
	/*public void copyWorld(File source, File target){
	    try {
	        ArrayList<String> ignore = new ArrayList<String>(Arrays.asList("uid.dat", "session.dat"));
	        if(!ignore.contains(source.getName())) {
	            if(source.isDirectory()) {
	                if(!target.exists())
	                target.mkdirs();
	                String files[] = source.list();
	                for (String file : files) {
	                    File srcFile = new File(source, file);
	                    File destFile = new File(target, file);
	                    copyWorld(srcFile, destFile);
	                }
	            } else {
	                InputStream in = new FileInputStream(source);
	                OutputStream out = new FileOutputStream(target);
	                byte[] buffer = new byte[1024];
	                int length;
	                while ((length = in.read(buffer)) > 0)
	                    out.write(buffer, 0, length);
	                in.close();
	                out.close();
	            }
	        }
	    } catch (IOException e) {
	 
	    }
	}*/
	 
	
	public PlayerInfo getPlayerInfo(String name)
	{
		for(PlayerInfo pInfo : playerInfoList)
		{
			if(pInfo.displayName.equals(name))
			{
				return pInfo;
			}
		}
		return null;
	}
}
