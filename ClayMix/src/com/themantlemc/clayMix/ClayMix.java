package com.themantlemc.clayMix;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent.  DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import io.puharesource.mc.titlemanager.api.ActionbarTitleObject;
import io.puharesource.mc.titlemanager.api.TitleObject;
import io.puharesource.mc.titlemanager.api.TitleObject.TitleType;

public class ClayMix extends JavaPlugin implements Listener
{
	
	BukkitScheduler scheduler;
	static ClayMix instance;
	World lobby;
	World gameMap;
	List<List<Block>> mapImages;
	List<List<Block>> gameImages;
	List<Location> mapImage;
	List<Location> gameImage;
	String[] mapNames = {"FourPlex", "12bit", "Spectrum", "Network", "Circulus", "Crystallize", "Flora", "Mockingjay", "Framed", "ForceField"};
	String[] gameNames = {"One In The Quiver", "Melt", "Timebomb", "Runner", "Spleef"};
	
	int game = 0;
	int map = 0;
	boolean gameStartup = false;
	boolean gameRunning = false;
	List<List<Location>> mapSpawns = new ArrayList<List<Location>>();
    List<List<Block>> mapBlocksList = new ArrayList<List<Block>>();
    List<List<Location>> jumpPadLocations = new ArrayList<List<Location>>();
	List<Location> mapBounds1 = new ArrayList<Location>();
	List<Location> mapBounds2 = new ArrayList<Location>();
	List<Block> breakBlocks = new ArrayList<Block>();
	
	int timebombTimer = 0;
	int powerupTimer = 15;
	List<Location> powerupLocations = new ArrayList<Location>();
	Scoreboard scoreboard;
	Objective objective;
	BossBar timebombBar;
	
	BossBar claymixBar;
	
	private Connection connection;
	
	List<PlayerInfo> playerInfoList = new ArrayList<PlayerInfo>();
	
	int gamePlayers = 0;
	
	@Override
	public void onEnable()
	{
	    instance = this;
	    establishConnection();
		
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new ClayMixListener(this), this);
	    getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	    
	    scheduler = getServer().getScheduler();
	    
	    // Get the lobby world
	    lobby = Bukkit.getServer().createWorld(new WorldCreator("ClayMixLobby"));
	    gameMap = Bukkit.getServer().createWorld(new WorldCreator("ClayMixGame"));
	    gameMap.setAutoSave(false);
	    
	    setMaps();
	    setImages();
	    
	    claymixBar = Bukkit.createBossBar(ChatColor.GOLD + "MAP:        " + "       " + ChatColor.GOLD + "GAME: ", BarColor.WHITE, BarStyle.SOLID);
		List<Block> newMapImage = mapImages.get(0);
		for(int i = 0; i < newMapImage.size(); i++)
		{
			lobby.getBlockAt(mapImage.get(i)).setTypeId(newMapImage.get(i).getTypeId());
			lobby.getBlockAt(mapImage.get(i)).setData(newMapImage.get(i).getData());
		}
		List<Block> newGameImage = gameImages.get(0);
		for(int i = 0; i < newGameImage.size(); i++)
		{
			lobby.getBlockAt(gameImage.get(i)).setTypeId(newGameImage.get(i).getTypeId());
			lobby.getBlockAt(gameImage.get(i)).setData(newGameImage.get(i).getData());
		}
	}
	 
	@Override
	public void onDisable()
	{
		
	}
	
	public void setMaps()
	{
		// Set map bounds
		// Map 1
		mapBounds1.add(new Location(gameMap, -32, 102, -32));
		mapBounds2.add(new Location(gameMap, 34, 126, 34));	
		// Map 2
		mapBounds1.add(new Location(gameMap, -33, 104, 81));
		mapBounds2.add(new Location(gameMap, 22, 134, 136));
		// Map 3
		mapBounds1.add(new Location(gameMap, -137, 110, -19));
		mapBounds2.add(new Location(gameMap, -87, 146, 31));
		// Map 4
		mapBounds1.add(new Location(gameMap, -137, 103, 73));
		mapBounds2.add(new Location(gameMap, -77, 121, 133));
		// Map 5
		mapBounds1.add(new Location(gameMap, -64, 106, 187));
		mapBounds2.add(new Location(gameMap, 55, 108, 306));
		// Map 6
		mapBounds1.add(new Location(gameMap, -39, 101, 363));
		mapBounds2.add(new Location(gameMap, 34, 119, 446));
		// Map 7
		mapBounds1.add(new Location(gameMap, -58, 112, 535));
		mapBounds2.add(new Location(gameMap, -9, 136, 585));
		// Map 8
		mapBounds1.add(new Location(gameMap, -146, 114, 441));
		mapBounds2.add(new Location(gameMap, -88, 128, 498));
		// Map 9
		mapBounds1.add(new Location(gameMap, 85, 103, -51));
		mapBounds2.add(new Location(gameMap, 125, 134, -11));
		// Map 10
		mapBounds1.add(new Location(gameMap, -174, 105, 219));
		mapBounds2.add(new Location(gameMap, -111, 112, 282));
		
		// Set map blocks and spawns
		for(int i = 0; i < 10; i++)
		{
			List<Location> spawns = new ArrayList<Location>();
			List<Block> blocks = new ArrayList<Block>();
			List<Location> jumpPads = new ArrayList<Location>();
			for(int x = mapBounds1.get(i).getBlockX() - 1; x <= mapBounds2.get(i).getBlockX(); x++)
			{
				for(int y = mapBounds1.get(i).getBlockY() - 1; y <= mapBounds2.get(i).getBlockY(); y++)
				{
					for(int z = mapBounds1.get(i).getBlockZ() - 1; z <= mapBounds2.get(i).getBlockZ(); z++)
					{
						Location checkLoc = new Location(lobby, x, y, z);
						if(lobby.getBlockAt(checkLoc) == null)
						{
							continue;
						}
						if(lobby.getBlockAt(checkLoc).getType() == Material.SIGN_POST || lobby.getBlockAt(checkLoc).getType() == Material.WALL_SIGN)
						{
							Sign s = (Sign)lobby.getBlockAt(checkLoc).getState();
							if(s.getLine(0).equals("SPAWN"))
							{
								spawns.add(new Location(gameMap, checkLoc.getX(), checkLoc.getY(), checkLoc.getZ()));
							}
						}
						if(lobby.getBlockAt(checkLoc).getType() == Material.REDSTONE_BLOCK)
						{
							jumpPads.add(new Location(gameMap, checkLoc.getX(), checkLoc.getY(), checkLoc.getZ()));
						}
						if(lobby.getBlockAt(checkLoc).getType() != Material.AIR)
						{
							blocks.add(gameMap.getBlockAt(new Location(gameMap, checkLoc.getX(), checkLoc.getY(), checkLoc.getZ())));
						}
					}
				}
			}
			mapBlocksList.add(blocks);
			mapSpawns.add(spawns);
			jumpPadLocations.add(jumpPads);
		}
	}
	
	public void setImages()
	{
		mapImage = new ArrayList<Location>();
		gameImage = new ArrayList<Location>();
		for(int z = 90; z <= 106; z++)
		{
			for(int y = 118; y <= 134; y++)
			{
				mapImage.add(new Location(lobby, 173, y, z));
			}
		}
		for(int z = 110; z <= 126; z++)
		{
			for(int y = 118; y <= 134; y++)
			{
				gameImage.add(new Location(lobby, 173, y, z));
			}
		}
		mapImages = new ArrayList<List<Block>>();
		gameImages = new ArrayList<List<Block>>();
		for(int i = 0; i < 11; i++)
		{
			List<Block> mapImageBlocks = new ArrayList<Block>();
			for(int z = 90; z <= 106; z++)
			{
				for(int y = 118; y <= 134; y++)
				{
					mapImageBlocks.add(lobby.getBlockAt(new Location(lobby, 191 + (11 * i), y, z)));
				}
			}
			mapImages.add(mapImageBlocks);
		}
		for(int i = 0; i < 6; i++)
		{
			List<Block> gameBlocks = new ArrayList<Block>();
			for(int z = 110; z <= 126; z++)
			{
				for(int y = 118; y <= 134; y++)
				{
					gameBlocks.add(lobby.getBlockAt(new Location(lobby, 191 + (11 * i), y, z)));
				}
			}
			gameImages.add(gameBlocks);
		}
	}
	
	public void runGame()
	{
		selectMap();
		
		scheduler.scheduleSyncDelayedTask(this, new Runnable(){
			@Override
			public void run() {
				selectGame();
			}
		}, 100L);
		scheduler.scheduleSyncDelayedTask(this, new Runnable(){
			@Override
			public void run() {
				startGame();
			}
		}, 200L);
	}
	
	public void selectMap()
	{
		Bukkit.broadcastMessage(ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(ChatColor.GOLD + "Selecting Map...");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-");
		claymixBar.setTitle(ChatColor.GOLD + "MAP: " + ChatColor.AQUA + "Selecting..." + "       " + ChatColor.GOLD + "GAME: ");
		
		int mapNum = 0;
		do
		{
			mapNum = (int)Math.floor((Math.random() * (mapImages.size() - 1))) + 1;
		} while(mapNum == map);
		map = mapNum;

		// Show selected map 3 seconds later
		scheduler.scheduleSyncDelayedTask(this, new Runnable()
		{
			@Override
			public void run() {
				List<Block> newImage = mapImages.get(map);
				for(int i = 0; i < newImage.size(); i++)
				{
					lobby.getBlockAt(mapImage.get(i)).setTypeId(newImage.get(i).getTypeId());
					lobby.getBlockAt(mapImage.get(i)).setData(newImage.get(i).getData());
				}
				
				Bukkit.broadcastMessage(ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-");
				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage(ChatColor.GOLD + "Selecting Map...");
				Bukkit.broadcastMessage(ChatColor.AQUA + mapNames[map - 1] + " Selected!");
				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage(ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-");
				claymixBar.setTitle(ChatColor.GOLD + "MAP: " + ChatColor.AQUA + mapNames[map - 1] + "       " + ChatColor.GOLD + "GAME: ");
			
				for(Player p : Bukkit.getOnlinePlayers())
				{
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
				}
				
				// Launch fireworks
				List<Location> fwLocs = new ArrayList<Location>();
				
				fwLocs.add(new Location(lobby, 170, 131, 98));
				fwLocs.add(new Location(lobby, 170, 121, 94));
				fwLocs.add(new Location(lobby, 170, 121, 102));
				for(Location fwLoc : fwLocs)
				{
					Firework fw = (Firework)lobby.spawnEntity(fwLoc, EntityType.FIREWORK);
					FireworkMeta fwMeta = fw.getFireworkMeta();
					fwMeta.addEffect(FireworkEffect.builder().trail(false).flicker(true).withColor(Color.RED).withFade(Color.BLUE).with(FireworkEffect.Type.BALL).build());
					fw.setFireworkMeta(fwMeta);
					
					// Explode the firework 2 ticks after
					scheduler.scheduleSyncDelayedTask(instance, new Runnable()
					{
						@Override
						public void run() {
							fw.playEffect(EntityEffect.FIREWORK_EXPLODE);
							fw.remove();
						}
					}, 2L);
				}

			}
			
		}, 60L);
	}
	
	public void selectGame()
	{
		Bukkit.broadcastMessage(ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(ChatColor.GOLD + "Selecting Game...");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-");
		claymixBar.setTitle(ChatColor.GOLD + "MAP: " + ChatColor.AQUA + mapNames[map - 1] + "       " + ChatColor.GOLD + "GAME: " + ChatColor.AQUA + "Selecting...");
		
		int gameNum = 0;
		do
		{
			gameNum = (int)Math.floor((Math.random() * (gameImages.size() - 1))) + 1;
		} while (gameNum == game);
		game = gameNum;
		
		// Show selected game 3 seconds later
		scheduler.scheduleSyncDelayedTask(this, new Runnable()
		{
			@Override
			public void run() {
				
				List<Block> newImage = gameImages.get(game);
				for(int i = 0; i < newImage.size(); i++)
				{
					lobby.getBlockAt(gameImage.get(i)).setTypeId(newImage.get(i).getTypeId());
					lobby.getBlockAt(gameImage.get(i)).setData(newImage.get(i).getData());
				}
				
				Bukkit.broadcastMessage(ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-");
				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage(ChatColor.GOLD + "Selecting Game...");
				Bukkit.broadcastMessage(ChatColor.AQUA + gameNames[game - 1] + " Selected!");
				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage(ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-");
				claymixBar.setTitle(ChatColor.GOLD + "MAP: " + ChatColor.AQUA + mapNames[map - 1] + "       " + ChatColor.GOLD + "GAME: " + ChatColor.AQUA + gameNames[game - 1]);
				
				for(Player p : Bukkit.getOnlinePlayers())
				{
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
				}
				
				// Launch fireworks
				List<Location> fwLocs = new ArrayList<Location>();
				
				fwLocs.add(new Location(lobby, 170, 131, 118));
				fwLocs.add(new Location(lobby, 170, 121, 114));
				fwLocs.add(new Location(lobby, 170, 121, 122));
				for(Location fwLoc : fwLocs)
				{
					Firework fw = (Firework)lobby.spawnEntity(fwLoc, EntityType.FIREWORK);
					FireworkMeta fwMeta = fw.getFireworkMeta();
					fwMeta.addEffect(FireworkEffect.builder().trail(false).flicker(true).withColor(Color.RED).withFade(Color.BLUE).with(FireworkEffect.Type.BALL).build());
					fw.setFireworkMeta(fwMeta);
					
					// Explode the firework 2 ticks after
					scheduler.scheduleSyncDelayedTask(instance, new Runnable()
					{
						@Override
						public void run() {
							fw.playEffect(EntityEffect.FIREWORK_EXPLODE);
							fw.remove();
						}
					}, 2L);
				}
			}
		}, 60L);
		

	}
	
	void startGame()
	{
		gameStartup = true;
		powerupTimer = 15;
		int spawnCount = 0;
		TitleObject title1 = new TitleObject(ChatColor.GOLD + "Get Ready", TitleType.TITLE);
		
		for(Player p : Bukkit.getOnlinePlayers())
		{
			// Teleport Code
			p.teleport(mapSpawns.get(map - 1).get(spawnCount));
			p.setHealth(20);
			if(game == 5 || game == 3)
			{
				p.setGameMode(GameMode.SURVIVAL);
			}
			else
			{
				p.setGameMode(GameMode.ADVENTURE);
			}
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 140, 10));
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 140, 128));
			spawnCount++;
			title1.send(p);
		}
		
		gamePlayers = spawnCount;
		
		// Remove Glass Corners
		mapBounds1.get(map - 1).getBlock().setType(Material.AIR);
		mapBounds2.get(map - 1).getBlock().setType(Material.AIR);
		
		// Remove Jump Pads if in a non pvp game
		if(game == 2 || game == 4 || game == 5)
		{
			for(int i = 0; i < jumpPadLocations.get(map - 1).size(); i++)
			{
				Location jumpLoc = jumpPadLocations.get(map - 1).get(i);
				jumpLoc.getBlock().setType(Material.STAINED_CLAY);
				Location jumpAbove = jumpLoc.add(new Location(gameMap, 0, 1, 0));
				jumpAbove.getBlock().setType(Material.AIR);
			}
		}
		
		// Remove Spawn Signs
		for(int i = 0; i < mapSpawns.get(map - 1).size(); i++)
		{
			mapSpawns.get(map - 1).get(i).getBlock().setType(Material.AIR);
		}
		
		Bukkit.broadcastMessage(ChatColor.GOLD + "Starting " + gameNames[game - 1] + "!");
		
		scheduler.scheduleSyncDelayedTask(this, new Runnable()
		{
			@Override
			public void run() {
				TitleObject title2 = new TitleObject(ChatColor.GREEN + "5", TitleType.TITLE);
				for(Player p : Bukkit.getOnlinePlayers())
				{
					title2.send(p);
				}
			}	
		}, 40L);
		
		scheduler.scheduleSyncDelayedTask(this, new Runnable()
		{
			@Override
			public void run() {
				TitleObject title2 = new TitleObject(ChatColor.YELLOW + "4", TitleType.TITLE);
				for(Player p : Bukkit.getOnlinePlayers())
				{
					title2.send(p);
				}
			}	
		}, 60L);
		
		scheduler.scheduleSyncDelayedTask(this, new Runnable()
		{
			@Override
			public void run() {
				TitleObject title2 = new TitleObject(ChatColor.GOLD + "3", TitleType.TITLE);
				for(Player p : Bukkit.getOnlinePlayers())
				{
					p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);
					title2.send(p);
				}
			}	
		}, 80L);
		
		scheduler.scheduleSyncDelayedTask(this, new Runnable()
		{
			@Override
			public void run() {
				TitleObject title2 = new TitleObject(ChatColor.RED + "2", TitleType.TITLE);
				for(Player p : Bukkit.getOnlinePlayers())
				{
					p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);
					title2.send(p);
				}
			}	
		}, 100L);
		
		scheduler.scheduleSyncDelayedTask(this, new Runnable()
		{
			@Override
			public void run() {
				TitleObject title2 = new TitleObject(ChatColor.DARK_RED + "1", TitleType.TITLE);
				for(Player p : Bukkit.getOnlinePlayers())
				{
					p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);
					title2.send(p);
				}
			}	
		}, 120L);
		
		scheduler.scheduleSyncDelayedTask(this, new Runnable()
		{
			@Override
			public void run() {
				TitleObject blank = new TitleObject("", TitleType.TITLE);
				for(Player p : Bukkit.getOnlinePlayers())
				{
					blank.send(p);
					p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 1, 1);
				}
				
				gameStartup = false;
				
				// Powerup Spawner
				BukkitRunnable runnable2 = new BukkitRunnable()
				{
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(gameRunning)
						{
							if(powerupTimer == 0)
							{
								// UNDO AFTER TEST
								// UNDO AFTER TEST
								// UNDO AFTER TEST
								spawnPowerup();
							}
							else
							{
								powerupTimer--;
							}
						}
						else
						{
							this.cancel();
						}
					}
				};
				runnable2.runTaskTimer(instance, 0L, 20L);
				
				// One In The Quiver
				if(game == 1)
				{
					for(Player p : Bukkit.getOnlinePlayers())
					{
						p.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
						p.getInventory().addItem(new ItemStack(Material.BOW));
						p.getInventory().addItem(new ItemStack(Material.ARROW));
					}
					
					gameRunning = true;
					
					// Give players a new arrow every 15 seconds
					BukkitRunnable runnable = new BukkitRunnable()
					{
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if(gameRunning && game == 1)
							{
								for(Player p : Bukkit.getOnlinePlayers())
								{
									p.getInventory().addItem(new ItemStack(Material.ARROW));
								}
							}
							else
							{
								this.cancel();
							}
						}
					};
					runnable.runTaskTimer(instance, 300L, 300L);
				}
				
				// Melt
				if(game == 2)
				{
					gameRunning = true;
					BukkitRunnable runnable = new BukkitRunnable()
					{
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if(gameRunning && game == 2)
							{
								for(int i = 0; i < 28; i++)
								{
									boolean changed;
									if(mapBlocksList.get(map - 1).size() > 0)
									{
										do
										{
											if(mapBlocksList.get(map - 1).size() == 0)
											{
												break;
											}
											changed = true;
											Block changeBlock = mapBlocksList.get(map - 1).get((int)Math.floor((Math.random() * mapBlocksList.get(map - 1).size())));
											if(changeBlock != null)
											{
												if(changeBlock.getType() == Material.STAINED_CLAY)
												{
													byte data = changeBlock.getData();
													changeBlock.setType(Material.WOOL);
													changeBlock.setData(data);
												}
												else if(changeBlock.getType() == Material.WOOL)
												{
													byte data = changeBlock.getData();
													changeBlock.setType(Material.STAINED_GLASS);
													changeBlock.setData(data);
												}
												else if(changeBlock.getType() == Material.STAINED_GLASS)
												{
													changeBlock.setType(Material.AIR);
													mapBlocksList.get(map - 1).remove(changeBlock);
												}
												else if (changeBlock.getType() == Material.AIR)
												{
													mapBlocksList.get(map - 1).remove(changeBlock);
													changed = false;
												}
												else
												{
													changed = false;
												}
											}
										}
										while (!changed);
									}
								}
							}
							else
							{
								this.cancel();
							}
						}
					};
					runnable.runTaskTimer(instance, 5L, 5L);
					
					BukkitRunnable meltPowerup = new BukkitRunnable()
					{
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if(gameRunning && game == 2)
							{
								for(Player powerupPlayer : Bukkit.getOnlinePlayers())
								{
									if(powerupPlayer.getGameMode() == GameMode.SPECTATOR)
									{
										continue;
									}
									PlayerInfo pInfo = getPlayerInfo(powerupPlayer.getName());
									if(pInfo.powerup.equals("Heavy"))
									{
										pInfo.powerupTime--;
										Location underLoc = powerupPlayer.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation();
										Location blockLoc = getPlayerStandOnBlockLocation(underLoc);
										Block changeBlock = gameMap.getBlockAt(blockLoc);
										if(changeBlock.getType().equals(Material.STAINED_CLAY) || changeBlock.getType().equals(Material.WOOL) || changeBlock.getType().equals(Material.STAINED_GLASS))
										{
											degradeBlock(changeBlock);
										}
										if(pInfo.powerupTime == 0)
										{
											powerupPlayer.sendMessage(ChatColor.GOLD + "Your Heavy powerup has run out!");
											pInfo.powerup = "";
										}
									}
								}
							}
						}
					};
					meltPowerup.runTaskTimer(instance, 20L, 20L);
				}
				
				// Timebomb
				if(game == 3)
				{
					gameRunning = true;
					timebombTimer = 50;
					
					// Create the scoreboard for the player kills
					scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
					
					objective = scoreboard.registerNewObjective("test", "dummy");
					objective.setDisplaySlot(DisplaySlot.SIDEBAR);
					objective.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "- Timebomb Scores -");
					
					for(Player p : Bukkit.getOnlinePlayers())
					{
						Score pScore = objective.getScore(p.getDisplayName());
						pScore.setScore(getPlayerInfo(p.getName()).kills);
						p.setScoreboard(scoreboard);
						p.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
					}

					BukkitRunnable runnable = new BukkitRunnable()
					{
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if(gameRunning && game == 3)
							{
								// Update the xp bar
								if(timebombTimer <= 10)
								{
			    					if(timebombBar == null)
			    					{
					    				timebombBar = Bukkit.createBossBar("" + timebombTimer, BarColor.RED, BarStyle.SOLID);
			    					}
			    					else
			    					{
			    						timebombBar.setTitle("" + timebombTimer);
			    					}
				    				timebombBar.setProgress(timebombTimer / 10.0);
				    				for(Player p : Bukkit.getOnlinePlayers())
				    				{
					    				if(timebombTimer <= 3 && timebombTimer != 0)
					    				{
					    					p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
					    				}
				    					
				    					if(!timebombBar.getPlayers().contains(p))
				    					{
				    						timebombBar.addPlayer(p);
				    					}
				    				}

								}
								
								// Kill the player with the lowest amount of kills
								if(timebombTimer == 0)
								{
									List<Player> killPlayers = new ArrayList<Player>();
									int killCheck = 0;
									do
									{
										for(Player p : Bukkit.getOnlinePlayers())
										{
											if(p.getGameMode() == GameMode.SPECTATOR)
											{
												continue;
											}
											if(getPlayerInfo(p.getName()).kills == killCheck)
											{
												killPlayers.add(p);
											}
										}
										killCheck++;
									} while(killPlayers.size() == 0);
									
									// Kill a player from the killPlayers list
									int killNum = (int)Math.floor((Math.random() * killPlayers.size()));
									timebombKill(killPlayers.get(killNum));
									timebombTimer = 10;
								}
								else
								{
									timebombTimer--;
								}
							}
							else
							{
								this.cancel();
							}
						}
					};
					runnable.runTaskTimer(instance, 20L, 20L);
				}
				
				// Runner
				if(game == 4)
				{
					gameRunning = true;
					
					for(Player p : Bukkit.getOnlinePlayers())
					{
						p.sendMessage(ChatColor.GOLD + "Blocks will start breaking in 5 seconds!");
					}
					
					BukkitRunnable runnable = new BukkitRunnable()
					{
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if(gameRunning && game == 4)
							{
								for(Block b : breakBlocks)
								{
									b.setType(Material.AIR);
								}
								breakBlocks.clear();
								for(Player p : Bukkit.getOnlinePlayers())
								{
									if(p.getGameMode() != GameMode.SPECTATOR)
									{
										if(getPlayerInfo(p.getName()).powerup.equals("Lightfooted"))
										{
											continue;
										}
										Location underLoc = p.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation();
										Location blockLoc = getPlayerStandOnBlockLocation(underLoc);
										Block changeBlock = gameMap.getBlockAt(blockLoc);
										if(changeBlock.getType().equals(Material.STAINED_CLAY))
										{
											breakBlocks.add(changeBlock);
										}
										if(getPlayerInfo(p.getName()).powerup.equals("Grow"))
										{
											Location ploc = p.getLocation();
											Location bloc = ploc.toVector().add(ploc.getDirection().normalize()).toLocation(ploc.getWorld());
											BlockFace face = ploc.getWorld().getBlockAt(ploc).getFace(ploc.getWorld().getBlockAt(bloc));
											if(face == BlockFace.EAST || face == BlockFace.WEST)
											{
												breakBlocks.add(changeBlock.getRelative(BlockFace.NORTH));
												breakBlocks.add(changeBlock.getRelative(BlockFace.SOUTH));
											}
											if(face == BlockFace.NORTH || face == BlockFace.SOUTH)
											{
												breakBlocks.add(changeBlock.getRelative(BlockFace.EAST));
												breakBlocks.add(changeBlock.getRelative(BlockFace.WEST));
											}
										}
									}
								}
							}
							else
							{
								this.cancel();
							}
						}
					};
					runnable.runTaskTimer(instance, 100L, 3L);
					
					BukkitRunnable runnerPowerup = new BukkitRunnable()
					{
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if(gameRunning && game == 4)
							{
								for(Player powerupPlayer : Bukkit.getOnlinePlayers())
								{
									if(powerupPlayer.getGameMode() == GameMode.SPECTATOR)
									{
										continue;
									}
									PlayerInfo pInfo = getPlayerInfo(powerupPlayer.getName());
									if(pInfo.powerup.equals("Lightfooted") || pInfo.powerup.equals("Grow"))
									{
										pInfo.powerupTime--;
										if(pInfo.powerupTime == 0)
										{
											powerupPlayer.sendMessage(ChatColor.GOLD + "Your " + pInfo.powerup + " powerup has run out!");
											pInfo.powerup = "";
										}
									}
								}
							}
						}
					};
					runnerPowerup.runTaskTimer(instance, 20L, 20L);
				}
				
				// Spleef
				if(game == 5)
				{
					for(Player p: Bukkit.getOnlinePlayers())
					{
						p.setGameMode(GameMode.SURVIVAL);
						p.setLevel(15);
						p.getInventory().addItem(new ItemStack(Material.STONE_SPADE));
					}
					gameRunning = true;
					BukkitRunnable runnable = new BukkitRunnable()
					{
						@Override
						public void run() {
							if(gameRunning && game == 5)
							{
								for(Player p : Bukkit.getOnlinePlayers())
								{
									if(p.getGameMode().equals(GameMode.SURVIVAL))
									{
										if(p.getLevel() == 0)
										{
											p.sendMessage(ChatColor.RED + "You have taken damage because you ran out of levels!");
											if(p.getHealth() - 7 <= 0)
											{
												playerDie(p);
											}
											else
											{
												p.setHealth(p.getHealth() - 7);
											}
										}
										else
										{
											p.setLevel(p.getLevel() - 1);;
										}
										if(p.getLevel() <= 3 && p.getLevel() > 0)
										{
											p.sendMessage(ChatColor.RED + "You must break more blocks to refill your level before you start taking damage!");
										}
									}
								}
							}
							else
							{
								this.cancel();
							}
						}
					};
					runnable.runTaskTimer(instance, 20L, 20L);
				}
			}
		}, 140L);
		
	}
	
	void endGame()
	{
		gameRunning = false;
		for(Player p : Bukkit.getOnlinePlayers())
		{
			p.teleport(lobby.getSpawnLocation());
			p.setGameMode(GameMode.ADVENTURE);
			p.getInventory().clear();
			p.setHealth(20);
			p.setExp(0);
			p.setLevel(0);
			PlayerInfo info = getPlayerInfo(p.getName());
			info.kills = 0;
			info.powerup = "";
			info.powerupTime = 0;
		}
		breakBlocks.clear();
		if(game == 3)
		{
			scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
			for(Player p : Bukkit.getOnlinePlayers())
			{
				p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			}
			timebombBar.removeAll();
		}
		
		Bukkit.getServer().unloadWorld(gameMap, false);
		gameMap = Bukkit.getServer().createWorld(new WorldCreator("ClayMixGame"));
		gameMap.setAutoSave(false);
		scheduler.scheduleSyncDelayedTask(this, new Runnable()
		{
			@Override
			public void run() {
				resetMapBlocks();
				
				// Send players messages about their points
				for(Player p : Bukkit.getOnlinePlayers())
				{
					PlayerInfo info = getPlayerInfo(p.getName());
					if(info.pointChange > 0)
					{
						p.sendMessage(ChatColor.GOLD + "You received " + info.pointChange + " points for winning!");
					}
					else if(info.pointChange == 0)
					{
						p.sendMessage(ChatColor.GOLD + "Your points did not change due to your placement this game!");
					}
					else if(info.pointChange < 0)
					{
						p.sendMessage(ChatColor.GOLD + "You lost " + Math.abs(info.pointChange) + " points due to your placement this game!");
					}
					
				}
			}
		}, 100L);
		
		
		// Reset boss bar and map and game images
		claymixBar.setTitle(ChatColor.GOLD + "MAP:        " + "       " + ChatColor.GOLD + "GAME: ");
		List<Block> newMapImage = mapImages.get(0);
		for(int i = 0; i < newMapImage.size(); i++)
		{
			lobby.getBlockAt(mapImage.get(i)).setTypeId(newMapImage.get(i).getTypeId());
			lobby.getBlockAt(mapImage.get(i)).setData(newMapImage.get(i).getData());
		}
		List<Block> newGameImage = gameImages.get(0);
		for(int i = 0; i < newGameImage.size(); i++)
		{
			lobby.getBlockAt(gameImage.get(i)).setTypeId(newGameImage.get(i).getTypeId());
			lobby.getBlockAt(gameImage.get(i)).setData(newGameImage.get(i).getData());
		}
	}
	
	public void resetMapBlocks()
	{
		for(int i = 0; i < 10; i++)
		{
			int mSize = mapBlocksList.get(i).size();
			for(int j = 0; j < mSize; j++)
			{
				Location loc = mapBlocksList.get(i).get(0).getLocation();
				mapBlocksList.get(i).add(gameMap.getBlockAt(new Location(gameMap, loc.getX(), loc.getY(), loc.getZ())));
				mapBlocksList.get(i).remove(0);
			}
			
			int sSize = mapSpawns.get(i).size();
			for(int j = 0; j < sSize; j++)
			{
				Location loc = mapSpawns.get(i).get(0);
				mapSpawns.get(i).add(new Location(gameMap, loc.getX(), loc.getY(), loc.getZ()));
				mapSpawns.get(i).remove(0);
			}
			
			int jSize = jumpPadLocations.get(i).size();
			for(int j = 0; j < jSize; j++)
			{
				Location loc = jumpPadLocations.get(i).get(0);
				jumpPadLocations.get(i).add(new Location(gameMap, loc.getX(), loc.getY(), loc.getZ()));
				jumpPadLocations.get(i).remove(0);
			}
			
			int bSize = mapBounds1.size();
			for(int j = 0; j < bSize; j++)
			{
				Location loc1 = mapBounds1.get(0);
				Location loc2 = mapBounds2.get(0);
				mapBounds1.add(new Location(gameMap, loc1.getX(), loc1.getY(), loc1.getZ()));
				mapBounds2.add(new Location(gameMap, loc2.getX(), loc2.getY(), loc2.getZ()));
				mapBounds1.remove(0);
				mapBounds2.remove(0);
			}
		}
	}
	
	void updateScoreboard()
	{
		objective.unregister();
		objective = scoreboard.registerNewObjective("test", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "- Timebomb Scores -");
		
		for(Player p : Bukkit.getOnlinePlayers())
		{
			Score pScore = objective.getScore(p.getDisplayName());
			pScore.setScore(getPlayerInfo(p.getName()).kills);
			p.setScoreboard(scoreboard);
		}
	}
	
	private Location getPlayerStandOnBlockLocation(Location locationUnderPlayer)
    {
		Location b10 = locationUnderPlayer.clone();
        if (b10.getBlock().getType() != Material.AIR)
        {
            return b10;
        } 
        Location b11 = locationUnderPlayer.clone().add(0.5, 0, -0.5);
        if (b11.getBlock().getType() != Material.AIR)
        {
            return b11;
        } 
        Location b12 = locationUnderPlayer.clone().add(-0.5, 0, -0.5);
        if (b12.getBlock().getType() != Material.AIR)
        {
            return b12;
        }
        Location b21 = locationUnderPlayer.clone().add(0.5, 0, 0.5);
        if (b21.getBlock().getType() != Material.AIR)
        {
            return b21;
        }
        Location b22 = locationUnderPlayer.clone().add(-0.5, 0, 0.5);
        if (b22.getBlock().getType() != Material.AIR)
        {
            return b22;
        }
        return locationUnderPlayer;
    }
	
	public void playerDie(Player player)
	{
		player.sendMessage(ChatColor.RED + "You have been killed!");
		player.setHealth(20);
		
		int spawnNum = (int)Math.floor((Math.random() * 24));
		player.teleport(mapSpawns.get(map-1).get(spawnNum));
		
		if(game != 3)
		{
			player.setGameMode(GameMode.SPECTATOR);
			player.getInventory().clear();
			
			List<Player> alivePlayers = new ArrayList<Player>();
			for(Player p : Bukkit.getOnlinePlayers())
			{
				if(p.getGameMode().equals(GameMode.ADVENTURE) || p.getGameMode().equals(GameMode.SURVIVAL))
				{
					alivePlayers.add(p);
				}
			}
			
			PlayerInfo deadInfo = getPlayerInfo(player.getName());
			deadInfo.place = alivePlayers.size() + 1;
			awardPoints(player);
			
			if(alivePlayers.size() == 1)
			{
				TitleObject winnerTitle = new TitleObject(alivePlayers.get(0).getDisplayName(), TitleType.TITLE);
				TitleObject winnerSubtitle = new TitleObject(ChatColor.GOLD + "is the winner!", TitleType.SUBTITLE);
				PlayerInfo winnerInfo = getPlayerInfo(alivePlayers.get(0).getName());
				winnerInfo.place = 1;
				awardPoints(alivePlayers.get(0));
				for(Player p : Bukkit.getOnlinePlayers())
				{
					winnerTitle.send(p);
					winnerSubtitle.send(p);
				}
				Bukkit.broadcastMessage(alivePlayers.get(0).getDisplayName() + ChatColor.GOLD + " is the winner!");
				endGame();
			}
		}
		if(game == 3)
		{
			if(getPlayerInfo(player.getName()).kills > 0)
			{			
				getPlayerInfo(player.getName()).kills--;
			}
			updateScoreboard();
		}

	}
	
	public void playerDie(Player player, Player killer)
	{
		player.sendMessage(ChatColor.RED + "You have been killed by " + killer.getDisplayName());
		player.setHealth(20);
		killer.sendMessage(ChatColor.GOLD + "You have killed " + player.getDisplayName());
		
		int spawnNum = (int)Math.floor((Math.random() * 24));
		player.teleport(mapSpawns.get(map-1).get(spawnNum));
		
		if(game == 1)
		{
			killer.getInventory().addItem(new ItemStack(Material.ARROW));
		}
		if(game != 3)
		{
			player.setGameMode(GameMode.SPECTATOR);
			player.getInventory().clear();
			
			List<Player> alivePlayers = new ArrayList<Player>();
			for(Player p : Bukkit.getOnlinePlayers())
			{
				if(p.getGameMode().equals(GameMode.ADVENTURE) || p.getGameMode().equals(GameMode.SURVIVAL))
				{
					alivePlayers.add(p);
				}
			}
			
			PlayerInfo deadInfo = getPlayerInfo(player.getName());
			deadInfo.place = alivePlayers.size() + 1;
			awardPoints(player);
			
			if(alivePlayers.size() == 1)
			{
				TitleObject winnerTitle = new TitleObject(alivePlayers.get(0).getDisplayName(), TitleType.TITLE);
				TitleObject winnerSubtitle = new TitleObject(ChatColor.GOLD + "is the winner!", TitleType.SUBTITLE);
				PlayerInfo winnerInfo = getPlayerInfo(alivePlayers.get(0).getName());
				winnerInfo.place = 1;
				awardPoints(alivePlayers.get(0));
				for(Player p : Bukkit.getOnlinePlayers())
				{
					winnerTitle.send(p);
					winnerSubtitle.send(p);
				}
				Bukkit.broadcastMessage(alivePlayers.get(0).getDisplayName() + ChatColor.GOLD + " is the winner!");
				endGame();
			}
		}
		
		// Timebomb: Increase kills
		if(game == 3)
		{
			if(getPlayerInfo(player.getName()).kills > 0)
			{			
				getPlayerInfo(player.getName()).kills--;
			}
			getPlayerInfo(killer.getName()).kills++;
			updateScoreboard();
		}
	}
	
	public void timebombKill(Player player)
	{
		Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.RED + " has exploded from the Timebomb!");
		player.setHealth(20);
		player.setGameMode(GameMode.SPECTATOR);
		updateScoreboard();
			
		int spawnNum = (int)Math.floor((Math.random() * 24));
		player.teleport(mapSpawns.get(map-1).get(spawnNum));
		
		// Explosion effect
		for(int x = -1; x <= 1; x++)
		{
			for(int y = -1; y <= 1; y++)
			{
				for(int z = -1; z <= 1; z++)
				{
					player.getWorld().createExplosion(new Location(player.getWorld(), player.getLocation().getX() + x, player.getLocation().getY() + y, player.getLocation().getZ() + z), 2);
				}	
			}
		}
		
		// Launch firework on death
		Location fwLoc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() + 1.2, player.getLocation().getZ());
		Firework fw = (Firework)player.getWorld().spawnEntity(fwLoc, EntityType.FIREWORK);
		FireworkMeta fwMeta = fw.getFireworkMeta();
		fwMeta.addEffect(FireworkEffect.builder().trail(false).flicker(false).withColor(Color.RED).withFade(Color.BLACK).with(FireworkEffect.Type.BALL).build());
		fw.setFireworkMeta(fwMeta);
		
		// Explode the firework 2 ticks after
		scheduler.scheduleSyncDelayedTask(this, new Runnable()
		{
			@Override
			public void run() {
				fw.playEffect(EntityEffect.FIREWORK_EXPLODE);
				fw.remove();
			}
		}, 2L);
		
		// Check what players are alive
		List<Player> alivePlayers = new ArrayList<Player>();
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(p.getGameMode().equals(GameMode.SURVIVAL))
			{
				alivePlayers.add(p);
			}
		}
		
		PlayerInfo deadInfo = getPlayerInfo(player.getName());
		deadInfo.place = alivePlayers.size() + 1;
		awardPoints(player);
		
		// End the game if only one player is alive
		if(alivePlayers.size() == 1)
		{
			TitleObject winnerTitle = new TitleObject(alivePlayers.get(0).getDisplayName(), TitleType.TITLE);
			TitleObject winnerSubtitle = new TitleObject(ChatColor.GOLD + "is the winner!", TitleType.SUBTITLE);
			PlayerInfo winnerInfo = getPlayerInfo(alivePlayers.get(0).getName());
			winnerInfo.place = 1;
			awardPoints(alivePlayers.get(0));
			for(Player p : Bukkit.getOnlinePlayers())
			{
				winnerTitle.send(p);
				winnerSubtitle.send(p);
			}
			Bukkit.broadcastMessage(alivePlayers.get(0).getDisplayName() + ChatColor.GOLD + " is the winner!");
			endGame();
		}
	}
	
	public void degradeBlock(Block changeBlock)
	{
		if(changeBlock != null)
		{
			if(changeBlock.getType() == Material.STAINED_CLAY)
			{
				byte data = changeBlock.getData();
				changeBlock.setType(Material.WOOL);
				changeBlock.setData(data);
			}
			else if(changeBlock.getType() == Material.WOOL)
			{
				byte data = changeBlock.getData();
				changeBlock.setType(Material.STAINED_GLASS);
				changeBlock.setData(data);
			}
			else if(changeBlock.getType() == Material.STAINED_GLASS)
			{
				changeBlock.setType(Material.AIR);
				mapBlocksList.get(map - 1).remove(changeBlock);
			}
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
			out.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		player.sendPluginMessage(this, "BungeeCord", b.toByteArray());
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		event.setJoinMessage(null);
		Player player = event.getPlayer();
		for(Player p : Bukkit.getOnlinePlayers())
		{
			p.sendMessage(ChatColor.GOLD + player.getName() + " has joined! (" + Bukkit.getOnlinePlayers().size() + "/24)");
			p.sendMessage(ChatColor.GOLD + "For testing purposes, type the words 'run game' in chat to start the game");
		}
		player.setFoodLevel(20);
		player.setHealth(20);
		player.teleport(lobby.getSpawnLocation());
		player.setGameMode(GameMode.ADVENTURE);
		player.setLevel(0);
		player.setExp(0);
		player.getInventory().clear();
		claymixBar.addPlayer(player);
		//PlayerInfo info = getPlayerInfo(player.getName());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		event.setQuitMessage(null);
		PushDataTask pushDataTask = new PushDataTask(event.getPlayer().getUniqueId(), event.getPlayer().getName(), this);
		pushDataTask.runTaskAsynchronously(this);
	}
	
	public void spawnPowerup()
	{
		boolean checked = false;
		
		if(mapBlocksList.get(map - 1).size() > 0)
		{
			do
			{
				Block randomBlock = mapBlocksList.get(map - 1).get((int)Math.floor((Math.random() * mapBlocksList.get(map - 1).size())));
				Block changeBlock = gameMap.getBlockAt(new Location(gameMap, randomBlock.getLocation().getX(), randomBlock.getLocation().getY() + 2, randomBlock.getLocation().getZ()));
				if(changeBlock.getType() != Material.AIR && changeBlock.getType() != Material.REDSTONE_BLOCK);
				{
					Location topLoc = new Location(changeBlock.getWorld(), changeBlock.getLocation().getX(), changeBlock.getLocation().getY() + 1, changeBlock.getLocation().getZ());
					if(gameMap.getBlockAt(topLoc).getType() == Material.AIR)
					{
						checked = true;
					}
				}
				if(map == 10 && changeBlock.getLocation().getY() > 107)
				{
					checked = false;
				}
				if(checked)
				{
					gameMap.spawnEntity(changeBlock.getLocation(), EntityType.ENDER_CRYSTAL);
					Bukkit.broadcastMessage(ChatColor.GOLD + "A powerup has spawned at (" + changeBlock.getLocation().getBlockX() + ", " + changeBlock.getLocation().getBlockY() + ", " + changeBlock.getLocation().getBlockZ() + ")");
					powerupTimer = (int)Math.floor((Math.random() * 10)) + 10;
				}
			} while(!checked);
		}
		
	}
	
	public void awardPoints(Player p)
	{
		PlayerInfo info = getPlayerInfo(p.getName());
		// First place points is the number of players * 2.5
		int firstPoints = (int)Math.ceil(gamePlayers * 2.5f);
		if(info.place == 1)
		{
			info.pointChange = firstPoints;
			info.score += info.pointChange;
		}
		else
		{
			//(Points first place got – 1)* -((Player Place - 1)/(((Number of players – 1) * (Number of players))/2))
			int playerPoints = (int)Math.ceil((firstPoints - 1)* -((info.place - 1)/(((gamePlayers - 1) * (gamePlayers))/2.0f)));
			info.pointChange = playerPoints;
			info.score += info.pointChange;
			if(info.score < 0)
			{
				info.score = 0;
			}
		}
	}
	
	// Pressure Plate Events
	@EventHandler
	public void pressurePlate(PlayerInteractEvent event)
	{
		if(!event.getAction().equals(Action.PHYSICAL))
		{
			return;
		}
		Block block = event.getClickedBlock();
		Material mat = block.getType();
		if(mat != Material.GOLD_PLATE && mat != Material.IRON_PLATE && mat != Material.STONE_PLATE)
		{
			return;
		}
		Player player = event.getPlayer();
		Location checkLocation = new Location(block.getLocation().getWorld(), block.getLocation().getX(), block.getLocation().getY() - 1, block.getLocation().getZ());

		if(mat == Material.STONE_PLATE)
		{
			// Jump Pad
			player.sendMessage(ChatColor.GOLD + "You have been boosted up a jump pad!");
			scheduler.scheduleSyncDelayedTask(this, new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					player.setVelocity(player.getVelocity().add(new Vector(0, 1.8, 0)));
				}
			}, 1L);
		}
		
		else if(mat == Material.GOLD_PLATE)
		{
			if(!block.hasMetadata("Placer"))
			{
				block.setType(Material.AIR);
			}
			else
			{
				block.setType(Material.AIR);
				block.getWorld().createExplosion(block.getLocation(), 2);
				Player killer = null;
				for(Player checkP : Bukkit.getOnlinePlayers())
				{
					if(checkP.getName().equals(block.getMetadata("Placer").get(0).toString()))
					{
						killer = checkP;
					}
				}
				if(killer == null)
				{
					playerDie(player);
				}
				else
				{
					if(killer.getGameMode() != GameMode.SPECTATOR)
					{
						playerDie(player, killer);
					}
					else
					{
						playerDie(player);
					}
				}
			}
		}
	}
	
	// Cancel Lobby Damage and Fall Damage
	@EventHandler
	public void cancelDamage(EntityDamageEvent event)
	{
		if(event.getEntity().getWorld() == lobby)
		{
			event.setCancelled(true);
		}
		if(event.getCause().equals(DamageCause.FALL))
		{
			event.setCancelled(true);
		}
		if(event.getCause().equals(DamageCause.BLOCK_EXPLOSION) || event.getCause().equals(DamageCause.ENTITY_EXPLOSION))
		{
			event.setCancelled(true);
		}
	}
	
	// Cancel Hunger
	@EventHandler
	public void onHunger(FoodLevelChangeEvent event)
	{
		event.setCancelled(true);
	}
	
	// Cancel Block Break
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		event.setCancelled(true);
	}
	
	// Chat event testing
	@EventHandler
	public void onChat(PlayerChatEvent event)
	{
		Player p = event.getPlayer();
		
		if(event.getMessage().equalsIgnoreCase("select map"))
		{
			selectMap();
		}
		
		if(event.getMessage().equalsIgnoreCase("select game"))
		{
			selectGame();
		}
		
		if(event.getMessage().equalsIgnoreCase("start game"))
		{
			startGame();
		}
		if(event.getMessage().equalsIgnoreCase("run game"))
		{
			runGame();
		}
		if(event.getMessage().equalsIgnoreCase("kills"))
		{
			p.sendMessage("" + getPlayerInfo(p.getName()).kills);
		}
		
		ChatColor pColor = ChatColor.GRAY;
		PlayerInfo info = getPlayerInfo(p.getName());
		if(info.rank.equals("Moderator") || info.rank.equals("Admin") || info.rank.equals("Owner"))
		{
			pColor = ChatColor.WHITE;
		}
		
		for(Player globalPlayer : Bukkit.getOnlinePlayers())
		{
			globalPlayer.sendMessage(ChatColor.GRAY + "[" + info.score + "] " + p.getDisplayName() + pColor + ": " + event.getMessage());
		}
		
		event.setCancelled(true);
	}
	
	// Method that gets information of a player
	public PlayerInfo getPlayerInfo(String name)
	{
		for(PlayerInfo pInfo : playerInfoList)
		{
			if(pInfo.playerName.equals(name))
			{
				return pInfo;
			}
		}
		return null;
	}
	
	public static ClayMix getInstance() {
		return instance;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	// Establish a connection to the MySQL Database
	private void establishConnection() {
		try{
			if(connection != null)
			{
				if(!connection.isClosed())
				{
					connection.close();
				}
			}
			connection = DriverManager.getConnection("jdbc:mysql://144.217.15.191:3306/testedhost_217", "testedhost_217", "9feb842fd5");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// Check if data exists for a player
	public boolean doesDataExist(Player player)
	{
		return false;
	}
	
	// Load data on preLogin
	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent event)
	{
		playerInfoList.add(new PlayerInfo(event.getName()));
		LoadDataTask loadDataTask = new LoadDataTask(event.getUniqueId(), event.getName(), this);
		loadDataTask.runTaskAsynchronously(this);
	}
}
