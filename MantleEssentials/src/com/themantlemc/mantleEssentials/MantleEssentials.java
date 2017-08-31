package com.themantlemc.mantleEssentials;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerPreLoginEvent.Result;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.xxmicloxx.NoteBlockAPI.NBSDecoder;

import io.puharesource.mc.titlemanager.api.v2.TitleManagerAPI;

public class MantleEssentials extends JavaPlugin implements Listener 
{
	static MantleEssentials instance;
	private Connection connection;
	
	BukkitScheduler scheduler;
	
	List<PlayerInfo> playerInfoList = new ArrayList<PlayerInfo>();
	
	Scoreboard board;
	Team playerTeam;
	Team spark;
	Team ember;
	Team flame;
	Team vip;
	Team builder;
	Team moderator;
	Team admin;
	Team owner;
	
	TitleManagerAPI title;
	
	// Custom Chat for Lobby only
	@EventHandler
	public void onChat(PlayerChatEvent event)
	{
		Player p = event.getPlayer();
		if(p.getWorld().getName().equals("MainLobby"))
		{
			PlayerInfo info = getPlayerInfo(p);
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
			return;
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player newPlayer = event.getPlayer();
		scheduler.scheduleSyncDelayedTask(this, new Runnable()
		{
				@Override
				public void run() {
				PlayerInfo newInfo = getPlayerInfo(newPlayer);
				
				switch(newInfo.rank)
				{
					case "Player": newPlayer.setDisplayName(ChatColor.GRAY + newPlayer.getName());
					playerTeam.addPlayer(newPlayer);
					break;
					case "Spark": newPlayer.setDisplayName(ChatColor.YELLOW + newPlayer.getName());
					spark.addPlayer(newPlayer);
					break;
					case "Ember": newPlayer.setDisplayName(ChatColor.GOLD + newPlayer.getName());
					ember.addPlayer(newPlayer);
					break;
					case "Flame": newPlayer.setDisplayName(ChatColor.RED + newPlayer.getName());
					flame.addPlayer(newPlayer);
					break;
					case "VIP": newPlayer.setDisplayName(ChatColor.LIGHT_PURPLE + newPlayer.getName());
					vip.addPlayer(newPlayer);
					break;
					case "Builder": newPlayer.setDisplayName(ChatColor.DARK_GREEN + newPlayer.getName());
					builder.addPlayer(newPlayer);
					break;
					case "Moderator": newPlayer.setDisplayName(ChatColor.BLUE + newPlayer.getName());
					moderator.addPlayer(newPlayer);
					break;
					case "Admin": newPlayer.setDisplayName(ChatColor.DARK_AQUA + newPlayer.getName());
					admin.addPlayer(newPlayer);
					break;
					case "Owner": newPlayer.setDisplayName(ChatColor.DARK_PURPLE + newPlayer.getName());
					owner.addPlayer(newPlayer);
					break;
				}
				
				newInfo.setScoreboard(instance);
				if(newPlayer.getWorld().getName().equals("MainLobby"))
				{
					newInfo.setObjectives(instance);
				}

				newPlayer.setScoreboard(newInfo.board);
				
				for(Player p : Bukkit.getOnlinePlayers())
				{
					PlayerInfo info = getPlayerInfo(p);
					info.updateScoreboard(instance);
					if(p.getWorld().getName().equals("MainLobby"))
					{
						info.updateObjectives(instance);
					}
				}
			}
		}, 6L);
	}
	
	// Push data when the player quits the game
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{		
		Player player = event.getPlayer();
		PlayerInfo info = getPlayerInfo(player);
		if(player.getWorld().getName().equals("MainLobby"))
		{
			PushDataTask pushDataTask = new PushDataTask(player.getUniqueId(), player.getName(), this);
			pushDataTask.runTaskAsynchronously(this);
			event.setQuitMessage(null);
		}
		if(info.playlist != null)
		{
			info.playlist.sPlayer.destroy();
		}
		playerInfoList.remove(info);
	}
	
	// Load data on preLogin
	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent event)
	{
		playerInfoList.add(new PlayerInfo(event.getName()));
		LoadDataTask loadDataTask = new LoadDataTask(event.getUniqueId(), event.getName(), this);
		loadDataTask.runTaskAsynchronously(this);
	}
	
	public PlayerInfo getPlayerInfo(Player player)
	{
		for(PlayerInfo pInfo : playerInfoList)
		{
			if(pInfo.name.equals(player.getName()))
			{
				return pInfo;
			}
		}
		return null;
	}
	
	public PlayerInfo getPlayerInfo(String pName)
	{
		for(PlayerInfo pInfo : playerInfoList)
		{
			if(pInfo.name.equals(pName))
			{
				return pInfo;
			}
		}
		return null;
	}
	
	List<Playlist> playlists = new ArrayList<Playlist>();
	
	public void loadPlaylists(File startFolder)
	{
		for(File folder : startFolder.listFiles())
		{
			if(folder.isDirectory())
			{
				Playlist newPlay = new Playlist(folder.getName());
				for(File song : folder.listFiles())
				{
					newPlay.songs.add(NBSDecoder.parse(song));
				}
				playlists.add(newPlay);
			}
		}
	}
	
	void openMusicMenu(Player p)
	{
		Inventory musicInv = Bukkit.createInventory(null, 54, ChatColor.BOLD + "" + ChatColor.DARK_GRAY + "Music!");
		
		PlayerInfo info = getPlayerInfo(p);
		
		// Glass Pane Background
		for(int i = 0; i < musicInv.getSize(); i++)
		{
			ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7);
			ItemMeta paneMeta = pane.getItemMeta();
			paneMeta.setDisplayName(" ");
			pane.setItemMeta(paneMeta);
			musicInv.setItem(i, pane);
		}
		
		// Create the record items for the playlists
		for(int i = 0; i < playlists.size(); i++)
		{
			ItemStack record = new ItemStack(Material.RECORD_5);
			ItemMeta recordMeta = record.getItemMeta();
			recordMeta.setDisplayName(ChatColor.GOLD + playlists.get(i).title);
			
			if(info.defaultPlaylist.equals(playlists.get(i).title))
			{
				recordMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1, true);
				recordMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "This is your default playlist!")));
			}
			else
			{
				recordMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Right Click to set this", ChatColor.AQUA + "as your default playlist!")));
			}
			record.setItemMeta(recordMeta);
			musicInv.setItem(20 + i, record);
		}
		
		// Custom Playlists
		for(int i = 0; i < 5; i++)
		{
			ItemStack record = new ItemStack(Material.RECORD_7);
			ItemMeta recordMeta = record.getItemMeta();
			recordMeta.setDisplayName(ChatColor.RED + "Custom Playlist");
			record.setItemMeta(recordMeta);
			musicInv.setItem(29 + i, record);
		}
		
		// Shuffle
		/*ItemStack shuffle = new ItemStack(Material.REDSTONE_COMPARATOR);
		ItemMeta shuffleMeta = shuffle.getItemMeta();
		shuffleMeta.setDisplayName(ChatColor.GOLD + "Shuffle");
		List<String> sLore = new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Click this to toggle Shuffle!"));
		if(info.shuffle)
		{
			shuffleMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1, true);
			sLore.add(ChatColor.GREEN + "Shuffle is currently ON!");
		}
		else
		{
			sLore.add(ChatColor.RED + "Shuffle is currently OFF!");
		}
		shuffleMeta.setLore(sLore);
		shuffle.setItemMeta(shuffleMeta);
		musicInv.setItem(50, shuffle);
		*/
		p.openInventory(musicInv);
	}
	
	@Override
	public void onEnable()
	{
		instance = this;
		establishConnection();
		title = (TitleManagerAPI) Bukkit.getServer().getPluginManager().getPlugin("TitleManager");
		
		getServer().getPluginManager().registerEvents(this, this);
		
		getCommand("msg").setExecutor(new CommandMessage(this));
		getCommand("r").setExecutor(new CommandRespond(this));
		getCommand("music").setExecutor(new CommandMusic(this));
		getCommand("rank").setExecutor(new CommandRank(this));
		
		loadPlaylists(new File("plugins/MantleMusic"));
		
		board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		
		playerTeam = board.registerNewTeam(ChatColor.GRAY + "Player");
		playerTeam.setPrefix(ChatColor.GRAY.toString());
		spark = board.registerNewTeam(ChatColor.YELLOW + "Spark");
		spark.setPrefix(ChatColor.YELLOW.toString());
		ember = board.registerNewTeam(ChatColor.GOLD + "Ember");
		ember.setPrefix(ChatColor.GOLD.toString());
		flame = board.registerNewTeam(ChatColor.RED + "Flame");
		flame.setPrefix(ChatColor.RED.toString());
		vip = board.registerNewTeam(ChatColor.LIGHT_PURPLE + "VIP");
		vip.setPrefix(ChatColor.LIGHT_PURPLE.toString());
		builder = board.registerNewTeam(ChatColor.DARK_GREEN + "Builder");
		builder.setPrefix(ChatColor.DARK_GREEN.toString());
		moderator = board.registerNewTeam(ChatColor.BLUE + "Moderator");
		moderator.setPrefix(ChatColor.BLUE.toString());
		admin = board.registerNewTeam(ChatColor.DARK_AQUA + "Admin");
		admin.setPrefix(ChatColor.DARK_AQUA.toString());
		owner = board.registerNewTeam(ChatColor.DARK_PURPLE + "Owner");
		owner.setPrefix(ChatColor.DARK_PURPLE.toString());
		
	    scheduler = getServer().getScheduler();
	    
	    scheduler.scheduleSyncRepeatingTask(this, new Runnable()
	    {
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers())
				{
					PlayerInfo info = getPlayerInfo(p);
					if(info.playlist != null)
					{
						info.playlist.updateSong();
					}
				}
			}
	    }, 0L, 10L);
	    
	    // Reconnect to SQL database every hour
	    scheduler.scheduleSyncRepeatingTask(this, new Runnable()
	    {
			@Override
			public void run() {
				establishConnection();
			}
	    }, 0L, 72000L);
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	public static MantleEssentials getInstance() {
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
	
	// Open Music Menu when player uses Noteblock
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		// Player isn't right clicking with an item
		if(event.getAction() == Action.PHYSICAL || event.getItem() == null || event.getItem().getType() == Material.AIR)
		{
			return;
		}
		
		if(event.getItem().getType() == Material.NOTE_BLOCK && event.getItem().hasItemMeta())
		{
			if(ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()).equals("Music"))
			{
				openMusicMenu(event.getPlayer());
			}
		}
	}
	
	// Handles the player doing things in their inventory
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		// Music Menu
		if(!ChatColor.stripColor(event.getInventory().getName()).equals("Music!"))
		{
			return;
		}
			
		Player p = (Player)event.getWhoClicked();
		
		event.setCancelled(true);
			
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta())
		{
			return;
		}
				
		ItemStack recordItem = event.getCurrentItem();
		ItemMeta recordMeta = recordItem.getItemMeta();
		PlayerInfo info = getPlayerInfo(p);
		
		if(event.getCurrentItem().getType() == Material.REDSTONE_COMPARATOR)
		{
			info.shuffle = !info.shuffle;
			if(info.shuffle)
			{
				p.sendMessage(ChatColor.GOLD + "You have enabled shuffle!");
			}
			else
			{
				p.sendMessage(ChatColor.GOLD + "You have disabled shuffle!");
			}
			p.closeInventory();
			openMusicMenu(p);
			return;
		}
		
		for(Playlist playlist : playlists)
		{
			if(ChatColor.stripColor(recordMeta.getDisplayName()).equals(playlist.title))
			{
				if(event.isLeftClick())
				{
					if(info.playlist != null)
					{
						info.playlist.sPlayer.removePlayer(p);
					}
					info.playlist = playlist.copy();
					info.playlist.addPlayer(p, info);
					info.playlist.Play(0, (short) 0);
					p.closeInventory();
					return;
				}
				if(event.isRightClick())
				{
					info.defaultPlaylist = playlist.title;
					p.sendMessage(ChatColor.GOLD + "You have set the " + info.defaultPlaylist + " playlist as your default playlist!");
					p.closeInventory();
					openMusicMenu(p);
					return;
				}
			}
		}
	}
}