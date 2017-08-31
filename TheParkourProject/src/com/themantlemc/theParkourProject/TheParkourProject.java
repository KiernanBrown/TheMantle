package com.themantlemc.theParkourProject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class TheParkourProject extends JavaPlugin implements Listener 
{
	
	BukkitScheduler scheduler;
	static TheParkourProject instance;
	World hub;
	World world1;
	World world2;
	World world3;
	ParkourWorld parkourWorld1;
	ParkourWorld parkourWorld2;
	ParkourWorld parkourWorld3;
	List<Location> breakBlocks = new ArrayList<Location>();
	
	private Connection connection;
	
	List<PlayerInfo> playerInfoList = new ArrayList<PlayerInfo>();
	
	public void setWorld1()
	{
		parkourWorld1 = new ParkourWorld(this, world1, "World 1");
		parkourWorld1.trackSpawns.add(world1.getSpawnLocation());
		parkourWorld1.trackSpawns.add(new Location(world1, 0, 5, 130));
		parkourWorld1.trackSpawns.add(new Location(world1, 0, 5, 276));
		parkourWorld1.trackSpawns.add(new Location(world1, 0, 5, 422));
		parkourWorld1.trackSpawns.add(new Location(world1, 0, 5, 568));
		parkourWorld1.trackSpawns.add(new Location(world1, 0, 5, 714));
	}
	
	public void setWorld2()
	{
		parkourWorld2 = new ParkourWorld(this, world2, "World 2");
		parkourWorld2.trackSpawns.add(world2.getSpawnLocation());
		parkourWorld2.trackSpawns.add(new Location(world2, 143, 24, 5));
		parkourWorld2.trackSpawns.add(new Location(world2, 271, 24, 5));
		parkourWorld2.trackSpawns.add(new Location(world2, 399, 24, 5));
		parkourWorld2.trackSpawns.add(new Location(world2, 527, 24, 5));
		parkourWorld2.trackSpawns.add(new Location(world2, 655, 24, 5));
	}
	
	public void setWorld3()
	{
		parkourWorld3 = new ParkourWorld(this, world3, "World 3");
		parkourWorld3.trackSpawns.add(world3.getSpawnLocation());
		parkourWorld3.trackSpawns.add(new Location(world3, -1230, 73, 877));
		parkourWorld3.trackSpawns.add(new Location(world3, -1230, 73, 822));
		parkourWorld3.trackSpawns.add(new Location(world3, -1230, 73, 767));
		parkourWorld3.trackSpawns.add(new Location(world3, -1230, 73, 712));
		parkourWorld3.trackSpawns.add(new Location(world3, -1230, 73, 657));
		for(int i = -1186; i <= -1089; i++)
		{
			for(int j = 860; j <= 894; j++)
			{
				for(int k = 15; k <= 55; k++)
				{
					Location checkLoc = new Location(world3, i, k, j);
					Block block = world3.getBlockAt(checkLoc);
					if(block.getTypeId() == 97)
					{
						if(block.getData() == (byte)4)
						{
							block.setType(Material.AIR);
							breakBlocks.add(block.getLocation());
						}	
					}
				}
			}
		}
	}
	
	@Override
	public void onEnable()
	{
	    instance = this;
	    establishConnection();
		
		getServer().getPluginManager().registerEvents(this, this);
	    getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	    
	    hub = Bukkit.createWorld(new WorldCreator("ParkourHub"));
	    hub.setGameRuleValue("doMobSpawning", "false");
	    hub.setGameRuleValue("doFireTick", "false");
	    hub.setGameRuleValue("doDaylightCycle", "false");
	    world1 = Bukkit.createWorld(new WorldCreator("Puzzle1"));
	    world1.setGameRuleValue("doMobSpawning", "false");
	    world1.setGameRuleValue("doFireTick", "false");
	    world1.setGameRuleValue("doDaylightCycle", "false");
	    world2 = Bukkit.createWorld(new WorldCreator("Puzzle2"));
	    world2.setGameRuleValue("doMobSpawning", "false");
	    world2.setGameRuleValue("doFireTick", "false");
	    world2.setGameRuleValue("doDaylightCycle", "false");
	    world3 = Bukkit.createWorld(new WorldCreator("Puzzle3"));
	    world3.setGameRuleValue("doMobSpawning", "false");
	    world3.setGameRuleValue("doFireTick", "false");
	    world3.setGameRuleValue("doDaylightCycle", "false");
	    
	    setWorld1();
	    setWorld2();
	    setWorld3();
	    
	    scheduler = getServer().getScheduler();
	    
	    scheduler.scheduleSyncRepeatingTask(this, new Runnable()
	    {
			@Override
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers())
				{
					if(player.getWorld().equals(world3))
					{
						if(breakBlocks.size() != 0)
						{
							for(Location loc : breakBlocks)
							{
								player.sendBlockChange(loc, 97, (byte)4);
							}
						}
					}
				}
			}
	    }, 0L, 200L);
	}
	 
	@Override
	public void onDisable()
	{
		for(Location loc : breakBlocks)
		{
			loc.getWorld().getBlockAt(loc).setTypeIdAndData(97, (byte)4, true);
		}
	}
	
	// Custom Chat
	@EventHandler
	public void onChat(PlayerChatEvent event)
	{
		Player p = event.getPlayer();
		PlayerInfo info = getPlayerInfo(p.getName());
		ChatColor pColor = ChatColor.GRAY;
		if(info.rank.equals("Moderator") || info.rank.equals("Admin") || info.rank.equals("Owner"))
		{
			pColor = ChatColor.WHITE;
		}
		for(Player globalPlayer : Bukkit.getOnlinePlayers())
		{
			globalPlayer.sendMessage(p.getDisplayName() + pColor + ": " + event.getMessage());
		}
		event.setCancelled(true);
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
		player.sendMessage(ChatColor.GOLD + "Welcome to The Parkour Project!");
		player.setFoodLevel(20);
		player.setHealth(20);
		player.teleport(hub.getSpawnLocation());
		player.setGameMode(GameMode.ADVENTURE);
		player.setLevel(getPlayerInfo(player.getName()).parkourPoints);
		player.setExp(0);
		ItemStack toggle = new ItemStack(Material.REDSTONE_TORCH_ON);
		ItemMeta toggleMeta = toggle.getItemMeta();
		toggleMeta.setDisplayName(ChatColor.GOLD + "Hide All Players");
		toggle.setItemMeta(toggleMeta);
		player.getInventory().setItem(4, toggle);
		ItemStack hubReturn = new ItemStack(Material.WATCH);
		ItemMeta hubReturnMeta = hubReturn.getItemMeta();
		hubReturnMeta.setDisplayName(ChatColor.GOLD + "Return to Parkour Hub");
		hubReturn.setItemMeta(hubReturnMeta);
		player.getInventory().setItem(8, hubReturn);
		PlayerInfo info = getPlayerInfo(player.getName());
		info.checkpoint = null;
		info.currentTrack = 0;
		info.currentWorld = 0;
		for(Player otherPlayer : Bukkit.getOnlinePlayers())
		{
			if(getPlayerInfo(otherPlayer.getName()).hiding)
			{
				otherPlayer.hidePlayer(player);
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		event.setQuitMessage(null);
		PushDataTask pushDataTask = new PushDataTask(event.getPlayer().getUniqueId(), event.getPlayer().getName(), this);
		pushDataTask.runTaskAsynchronously(this);
	}
	
	// Hub Return
	@EventHandler
	public void returnToHub(PlayerInteractEvent event)
	{
		if(!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
		{
			return;
		}
		if(event.getItem() == null)
		{
			return;
		}
		if(event.getItem().getType() != Material.WATCH)
		{
			return;
		}
		Player player = event.getPlayer();
		PlayerInfo info = getPlayerInfo(player.getName());
		info.currentTrack = 0;
		info.currentWorld = 0;
		info.checkpoint = null;
		player.sendMessage(ChatColor.GOLD + "Returning to Hub!");
		player.teleport(hub.getSpawnLocation());
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
		if(mat == Material.GOLD_PLATE)
		{
			// Lobby Jump Pad
			if(block.getWorld().getBlockAt(checkLocation).getType() == Material.WOOD)
			{
				Vector direction = player.getLocation().getDirection();
				Vector jump = new Vector(direction.getX(), .02, direction.getZ());

				
				scheduler.scheduleSyncDelayedTask(this, new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						player.setVelocity(jump.multiply(60));
					}
					
				}, 1L);
			}
			
			// Parkour Jump Pad
			if(block.getWorld().getBlockAt(checkLocation).getDrops().contains(new ItemStack(Material.WOOL, 1, (byte)1)))
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 30, 5));
			}
			
			// Jump and Speed Pad
			if(block.getWorld().getBlockAt(checkLocation).getDrops().contains(new ItemStack(Material.WOOL, 1, (byte)6)))
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 2));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2));
			}
			
			PlayerInfo info = getPlayerInfo(player.getName());
			
			// Remove Blindness
			if(block.getWorld().getBlockAt(checkLocation).getDrops().contains(new ItemStack(Material.WOOL, 1, (byte)9)))
			{
				if(player.hasPotionEffect(PotionEffectType.BLINDNESS))
				{
					player.removePotionEffect(PotionEffectType.BLINDNESS);
				}
				if(!info.checkpoint.equals(event.getClickedBlock().getLocation()))
				{
					player.sendMessage(ChatColor.GOLD + "You have reached a new checkpoint!");
					info.checkpoint = event.getClickedBlock().getLocation();
				}
			}
			
			if(info.currentTrack == 1 && info.currentWorld == 3)
			{
				if(!info.checkpoint.equals(event.getClickedBlock().getLocation()))
				{
					player.sendMessage(ChatColor.GOLD + "You have reached a new checkpoint!");
					info.checkpoint = event.getClickedBlock().getLocation();
				}
			}
		}
		
		if(mat == Material.IRON_PLATE)
		{
			// Parkour Speed Pad
			if(block.getWorld().getBlockAt(checkLocation).getDrops().contains(new ItemStack(Material.WOOL, 1, (byte)5)))
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 45, 8));
			}
			
			// Blindness
			if(block.getWorld().getBlockAt(checkLocation).getDrops().contains(new ItemStack(Material.WOOL, 1, (byte)10)))
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40000, 1));
			}
		}
		
		if(mat == Material.STONE_PLATE)
		{
			// Teleport back to checkpoint
			if(block.getWorld().getBlockAt(checkLocation).getDrops().contains(new ItemStack(Material.WOOL, 1, (byte)14)))
			{
				player.teleport(getPlayerInfo(player.getName()).checkpoint);
				if(player.hasPotionEffect(PotionEffectType.BLINDNESS))
				{
					player.removePotionEffect(PotionEffectType.BLINDNESS);
				}
			}
		}
	}
	
	@EventHandler
	public void clickSign(PlayerInteractEvent event)
	{
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK)
		{
			return;
		}
		if(event.getClickedBlock().getType() != Material.SIGN_POST && event.getClickedBlock().getType() != Material.WALL_SIGN)
		{
			return;
		}
		
		Sign s = (Sign)event.getClickedBlock().getState();
		Player player = event.getPlayer();
		PlayerInfo info = getPlayerInfo(player.getName());
		if((ChatColor.stripColor(s.getLine(1)).equals("NEXT") && ChatColor.stripColor(s.getLine(2)).equals("TRACK")) || (ChatColor.stripColor(s.getLine(1)).equals("BACK TO") && ChatColor.stripColor(s.getLine(2)).equals("HUB")))
		{
			Boolean next = false;
			if(ChatColor.stripColor(s.getLine(1)).equals("NEXT") && ChatColor.stripColor(s.getLine(2)).equals("TRACK"))
			{
				next = true;
			}
			player.sendMessage(ChatColor.GOLD + "You have completed Track " + (info.currentTrack + 1) + "!");
			if(info.currentWorld == 1)
			{
				if(info.world1Tracks.get(info.currentTrack) == false)
				{
					info.world1Tracks.set(info.currentTrack, true);
					player.sendMessage(ChatColor.GOLD + "You have gained 1 Parkour Point for completing this track for the first time!");
				}
				if(next)
				{
					player.teleport(new Location(s.getWorld(), s.getLocation().getX(), s.getLocation().getY(), s.getLocation().getZ() + 5));
				}
			}
			if(info.currentWorld == 2)
			{
				if(info.world2Tracks.get(info.currentTrack) == false)
				{
					info.world2Tracks.set(info.currentTrack, true);
					player.sendMessage(ChatColor.GOLD + "You have gained 1 Parkour Point for completing this track for the first time!");
				}
				if(next)
				{
					player.teleport(new Location(s.getWorld(), s.getLocation().getX() + 5, s.getLocation().getY(), s.getLocation().getZ()));
				}
			}
			info.currentTrack++;
			info.calculatePoints();
			PushDataTask pushDataTask = new PushDataTask(player.getUniqueId(), player.getName(), this);
			pushDataTask.runTaskAsynchronously(this);
			if(!next)
			{
				player.sendMessage(ChatColor.GOLD + "Returning to hub!");
				player.teleport(hub.getSpawnLocation());
			}
		}
		if(ChatColor.stripColor(s.getLine(2)).equals("Join"))
		{
			if(ChatColor.stripColor(s.getLine(1)).equals("Level 1"))
			{
				openTrackMenu(player, "World 1", info.world1Tracks, 0);
			}
			if(ChatColor.stripColor(s.getLine(1)).equals("Level 2"))
			{
				openTrackMenu(player, "World 2", info.world2Tracks, 5);
			}
			if(ChatColor.stripColor(s.getLine(1)).equals("Level 3"))
			{
				openTrackMenu(player, "World 3", info.world3Tracks, 10);
			}
		}
	}
	
	@EventHandler
	public void world2Handler(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		if(!player.getWorld().equals(world2))
		{
			return;
		}
		PlayerInfo info = getPlayerInfo(player.getName());
		if(info.currentTrack == 3)
		{
			if(player.getLocation().getY() <= 8)
			{
				player.teleport(new Location(world2, 427, 28, 5));
			}
		}
	}
	
	@EventHandler
	public void world3Handler(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		if(!player.getWorld().equals(world3))
		{
			return;
		}
		PlayerInfo info = getPlayerInfo(player.getName());
		
		// Break Cracked Stone Bricks
		if(info.currentTrack == 1)
		{
			Location playerLoc = new Location(world3, player.getLocation().getX(), player.getLocation().getY() - 1, player.getLocation().getZ());
			for(Location loc : breakBlocks)
			{
				if(world3.getBlockAt(playerLoc).getLocation().equals(loc))
				{
					scheduler.scheduleSyncDelayedTask(this, new Runnable(){
						@Override
						public void run() {
							player.sendBlockChange(loc, 0, (byte)0);
						}	
					},6L);
					
					scheduler.scheduleSyncDelayedTask(this, new Runnable(){
						@Override
						public void run() {
							player.sendBlockChange(loc, 97, (byte)4);
						}	
					},60L);
				}
			}
		}
		
		// Track 4 teleport when falling
		if(info.currentTrack == 3)
		{
			if(player.getLocation().getY() <= 40)
			{
				if(player.getLocation().getX() >= -1185 && player.getLocation().getX() <= -1133)
				{
					if(player.getLocation().getZ() >= 751 && player.getLocation().getZ() <= 783)
					{
						player.teleport(new Location(world3, -1178, 53, 767));
					}
				}
			}
		}
		
		// Track 1
		if(player.getLocation().distance(new Location(world3, -1085, 4, 942)) <= 3)
		{
			player.sendMessage(ChatColor.GOLD + "You have completed Track " + (info.currentTrack + 1) + "!");
			if(info.world3Tracks.get(info.currentTrack) == false)
			{
				info.world3Tracks.set(info.currentTrack, true);
				player.sendMessage(ChatColor.GOLD + "You have gained 1 Parkour Point for completing this track for the first time!");
			}
			info.currentTrack++;
			info.calculatePoints();
			info.checkpoint = new Location(world3, -1178, 55, 877);
			PushDataTask pushDataTask = new PushDataTask(player.getUniqueId(), player.getName(), this);
			pushDataTask.runTaskAsynchronously(this);
			player.teleport(new Location(world3, -1230, 73, 877));
			for(Location loc : breakBlocks)
			{
				player.sendBlockChange(loc, 97, (byte)4);
			}
		}
		
		// Track 2
		if(player.getLocation().distance(new Location(world3, -1086, 4, 877)) <= 2)
		{
			player.sendMessage(ChatColor.GOLD + "You have completed Track " + (info.currentTrack + 1) + "!");
			if(info.world3Tracks.get(info.currentTrack) == false)
			{
				info.world3Tracks.set(info.currentTrack, true);
				player.sendMessage(ChatColor.GOLD + "You have gained 1 Parkour Point for completing this track for the first time!");
			}
			info.currentTrack++;
			info.calculatePoints();
			info.checkpoint = null;
			PushDataTask pushDataTask = new PushDataTask(player.getUniqueId(), player.getName(), this);
			pushDataTask.runTaskAsynchronously(this);
			player.teleport(new Location(world3, -1230, 73, 822));
		}
		
		// Track 3
		if(player.getLocation().distance(new Location(world3, -1085, 4, 822)) <= 3)
		{
			player.sendMessage(ChatColor.GOLD + "You have completed Track " + (info.currentTrack + 1) + "!");
			if(info.world3Tracks.get(info.currentTrack) == false)
			{
				info.world3Tracks.set(info.currentTrack, true);
				player.sendMessage(ChatColor.GOLD + "You have gained 1 Parkour Point for completing this track for the first time!");
			}
			info.currentTrack++;
			info.calculatePoints();
			PushDataTask pushDataTask = new PushDataTask(player.getUniqueId(), player.getName(), this);
			pushDataTask.runTaskAsynchronously(this);
			player.teleport(new Location(world3, -1230, 73, 767));
		}
		
		// Track 4
		if(player.getLocation().distance(new Location(world3, -1085, 4, 767)) <= 3)
		{
			player.sendMessage(ChatColor.GOLD + "You have completed Track " + (info.currentTrack + 1) + "!");
			if(info.world3Tracks.get(info.currentTrack) == false)
			{
				info.world3Tracks.set(info.currentTrack, true);
				player.sendMessage(ChatColor.GOLD + "You have gained 1 Parkour Point for completing this track for the first time!");
			}
			info.currentTrack++;
			info.calculatePoints();
			PushDataTask pushDataTask = new PushDataTask(player.getUniqueId(), player.getName(), this);
			pushDataTask.runTaskAsynchronously(this);
			player.teleport(new Location(world3, -1230, 73, 712));
		}
		
		// Track 5
		if(player.getLocation().distance(new Location(world3, -1085, 4, 712)) <= 3)
		{
			player.sendMessage(ChatColor.GOLD + "You have completed Track " + (info.currentTrack + 1) + "!");
			if(info.world3Tracks.get(info.currentTrack) == false)
			{
				info.world3Tracks.set(info.currentTrack, true);
				player.sendMessage(ChatColor.GOLD + "You have gained 1 Parkour Point for completing this track for the first time!");
			}
			info.currentTrack++;
			info.calculatePoints();
			info.checkpoint = new Location(world3, -1178, 33, 657);
			PushDataTask pushDataTask = new PushDataTask(player.getUniqueId(), player.getName(), this);
			pushDataTask.runTaskAsynchronously(this);
			player.teleport(new Location(world3, -1230, 73, 657));
		}
		
		// Track 6
		if(player.getLocation().distance(new Location(world3, -1085, 4, 657)) <= 3)
		{
			player.sendMessage(ChatColor.GOLD + "You have completed Track " + (info.currentTrack + 1) + "!");
			if(info.world3Tracks.get(info.currentTrack) == false)
			{
				info.world3Tracks.set(info.currentTrack, true);
				player.sendMessage(ChatColor.GOLD + "You have gained 1 Parkour Point for completing this track for the first time!");
			}
			info.currentTrack++;
			info.calculatePoints();
			info.checkpoint = null;
			PushDataTask pushDataTask = new PushDataTask(player.getUniqueId(), player.getName(), this);
			pushDataTask.runTaskAsynchronously(this);
			player.sendMessage(ChatColor.GOLD + "Returning to hub!");
			player.teleport(hub.getSpawnLocation());
		}
	}
	
	// Cancel Damage
	@EventHandler
	public void cancelDamage(EntityDamageEvent event)
	{		
		// World 3 Track 2 Teleport
		if(event.getEntity() instanceof Player)
		{
			Player player = (Player)event.getEntity();
			PlayerInfo info = getPlayerInfo(player.getName());
			if(info.currentTrack == 1 && info.currentWorld == 3)
			{
				if(event.getCause().equals(DamageCause.LAVA))
				{
					if(player.getWorld().getBlockAt(player.getLocation()).getType() == Material.STATIONARY_LAVA || player.getWorld().getBlockAt(player.getLocation()).getType() == Material.LAVA)
					{
						player.teleport(info.checkpoint);
					}
					event.setCancelled(true);
				}
				if(event.getCause().equals(DamageCause.FIRE) || event.getCause().equals(DamageCause.FIRE_TICK))
				{
					player.setFireTicks(-20);
					event.setCancelled(true);
				}
			}
		}
		
		event.setCancelled(true);
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
	
	// Method that gets information of a player
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
	
	public void openTrackMenu(Player player, String worldName, List<Boolean> tracks, int requiredPoints)
	{
		Inventory trackInv = Bukkit.createInventory(null, 9, ChatColor.BOLD + "" + ChatColor.DARK_GRAY + worldName);
		
		PlayerInfo info = getPlayerInfo(player.getName());
		
		for(int i = 0; i < tracks.size(); i++)
		{
			// Track completed
			if(tracks.get(i))
			{
				ItemStack clay = new ItemStack(Material.STAINED_CLAY, 1, (byte)13);
				ItemMeta clayMeta = clay.getItemMeta();
				clayMeta.setDisplayName(ChatColor.GOLD + "Track " + (i + 1));
				clayMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.GREEN + "Completed!", ChatColor.AQUA + "Click to play this track!")));
				clay.setItemMeta(clayMeta);
				trackInv.setItem(i, clay);
				continue;
			}
			
			// Track unlocked
			else if(info.parkourPoints >= requiredPoints)
			{
				ItemStack clay = new ItemStack(Material.STAINED_CLAY, 1, (byte)3);
				ItemMeta clayMeta = clay.getItemMeta();
				clayMeta.setDisplayName(ChatColor.GOLD + "Track " + (i + 1));
				clayMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Click to play this track!")));
				clay.setItemMeta(clayMeta);
				trackInv.setItem(i, clay);
				continue;
			}
			
			// Track locked
			else
			{
				ItemStack clay = new ItemStack(Material.STAINED_CLAY, 1, (byte)14);
				ItemMeta clayMeta = clay.getItemMeta();
				clayMeta.setDisplayName(ChatColor.GOLD + "Track " + (i + 1));
				clayMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.RED + "You need " + requiredPoints + " parkour points ", ChatColor.RED + "to play this track!")));
				clay.setItemMeta(clayMeta);
				trackInv.setItem(i, clay);
				continue;
			}
		}
		player.openInventory(trackInv);
	}
	
	@EventHandler
	public void inventoryClick(InventoryClickEvent event)
	{
		if(!ChatColor.stripColor(event.getInventory().getName()).contains("World"))
		{
			return;
		}
		
		event.setCancelled(true);
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta())
		{
			return;
		}		
		if(event.getCurrentItem().getData().getData() == (byte)14)
		{
			return;
		}
		Player player = (Player)event.getWhoClicked();
		PlayerInfo info = getPlayerInfo(player.getName());
		for(int i = 0; i < 6; i++)
		{
			if(event.getInventory().getItem(i).equals(event.getCurrentItem()))
			{
				info.currentTrack = i;
				switch(ChatColor.stripColor(event.getInventory().getName()))
				{
				case "World 1": info.currentWorld = 1;
					player.teleport(parkourWorld1.trackSpawns.get(i));
					break;
				case "World 2": info.currentWorld = 2;
					player.teleport(parkourWorld2.trackSpawns.get(i));
					break;
				case "World 3": info.currentWorld = 3;
					player.teleport(parkourWorld3.trackSpawns.get(i));
					if(i == 1)
					{
						for(Location loc : breakBlocks)
						{
							player.sendBlockChange(loc, 97, (byte)4);
						}
						info.checkpoint = new Location(world3, -1178, 55, 877);
					}
					if(i == 5)
					{
						info.checkpoint = new Location(world3, -1178, 33, 657);
					}
					break;
				}
				player.closeInventory();
				return;
			}
		}
	}
	
	public static TheParkourProject getInstance() {
		return instance;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	// Establish a connection to the MySQL Database
	private void establishConnection() {
		try{
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
