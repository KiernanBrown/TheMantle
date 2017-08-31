package com.themantlemc.controlHeroes;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.SplashPotion;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerPreLoginEvent.Result;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import io.puharesource.mc.titlemanager.TitleManagerPlugin;
import io.puharesource.mc.titlemanager.api.ActionbarTitleObject;
import io.puharesource.mc.titlemanager.api.v2.TitleManagerAPI;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

public class ControlPoint extends JavaPlugin implements Listener{
	
	private static ControlPoint instance;
	
	List<PlayerInfo> playerInfoList = new ArrayList<PlayerInfo>();
	TeamManager teamManager = new TeamManager(this);
	Team winningTeam = null;
	List<ControlPointClass> classes = new ArrayList<ControlPointClass>();
	List<ControlPointHero> heroes = new ArrayList<ControlPointHero>();
	List<HeroPack> heroPacks = new ArrayList<HeroPack>();
	List<String> mapNames = new ArrayList<String>();
	List<Integer> mapVotes = new ArrayList<Integer>();
    
	int requiredPlayers = 2;
    int startTimer = 90;
    int pointTimer = 30;
    
	boolean gameStarting = false;
    boolean gameRunning = false;
    boolean gameEnding = false;
    
    BossBar pointBar;
    BossBar startBar;
    BossBar endBar;
    BossBar lobbyBar;
    
    int redHero;
    int blueHero;
    int greenHero;
    int purpleHero;
    
    BossBar redHeroBar;
    BossBar blueHeroBar;
    BossBar greenHeroBar;
    BossBar purpleHeroBar;
    
    BossBar redHeroActive;
    BossBar blueHeroActive;
    BossBar greenHeroActive;
    BossBar purpleHeroActive;
    
    // Method to update Hero Bars
    public void updateHeroBar(Player player, int points)
    {
    	PlayerInfo info = getPlayerInfo(player.getName());
    	Team team = getPlayerTeam(player);
    	
    	// Give the player heroPoints and add points to the team's hero bar
    	switch(ChatColor.stripColor(team.getName()))
    	{
    		case "Red Team":
    		if(redHero != 100)
    		{
        		if(redHero + points < 100)
        		{
        			info.heroPoints += points;
        			redHero += points;
        		}
        		else
        		{
        			info.heroPoints += (100 - redHero);
        			redHero = 100;
        		}
        		if(redHero == 100)
        		{
        			// Select Hero
        			selectHero(team);
        		}
        		redHeroBar.setProgress(redHero / 100.0);
        		if(redHero < 20)
        		{
            		redHeroActive.setProgress(redHero / 20.0);
        		}
        		else
        		{
        			redHeroActive.setProgress(1);
        		}
    		}
    		break;
    		case "Blue Team":
    			if(blueHero != 100)
    			{
            		if(blueHero + points < 100)
            		{
            			info.heroPoints += points;
            			blueHero += points;
            		}
            		else
            		{
            			info.heroPoints += (100 - blueHero);
            			blueHero = 100;
            		}
            		if(blueHero == 100)
            		{
            			// Select Hero
            			selectHero(team);
            		}
            		
            		blueHeroBar.setProgress(blueHero / 100.0);
            		if(blueHero < 20)
            		{
                		blueHeroActive.setProgress(blueHero / 20.0);
            		}
            		else
            		{
            			blueHeroActive.setProgress(1);
            		}
    			}
    		break;
    		case "Green Team":
    			if(greenHero != 100)
    			{
            		if(greenHero + points < 100)
            		{
            			info.heroPoints += points;
            			greenHero += points;
            		}
            		else
            		{
            			info.heroPoints += (100 - greenHero);
            			greenHero = 100;
            		}
            		if(greenHero == 100)
            		{
            			// Select Hero
            			selectHero(team);
            		}
            		
            		greenHeroBar.setProgress(greenHero / 100.0);
            		if(greenHero < 20)
            		{
                		greenHeroActive.setProgress(greenHero / 20.0);
            		}
            		else
            		{
            			greenHeroActive.setProgress(1);
            		}
    			}
    		break;
    		case "Purple Team":
    			if(purpleHero != 100)
    			{
            		if(purpleHero + points < 100)
            		{
            			info.heroPoints += points;
            			purpleHero += points;
            		}
            		else
            		{
            			info.heroPoints += (100 - purpleHero);
            			purpleHero = 100;
            		}
            		if(purpleHero == 100)
            		{
            			// Select Hero
            			selectHero(team);
            		}
            		
            		purpleHeroBar.setProgress(purpleHero / 100.0);
            		if(purpleHero < 20)
            		{
                		purpleHeroActive.setProgress(purpleHero / 20.0);
            		}
            		else
            		{
            			purpleHeroActive.setProgress(1);
            		}
    			}
    		break;
    	}
    }
    
    // Method to select a player to become a hero
    public void selectHero(Team team)
    {
		// Random generator
		Random rgen = new Random();
		List<Player> players = new ArrayList<Player>();
		
		// Create a list of players that is based on the amount of hero points each player has
		// More points = more appearances in the list = higher chance of being a hero
    	for(OfflinePlayer tp : team.getPlayers())
    	{
    		Player teamPlayer = tp.getPlayer();
    		teamPlayer.getPlayer().sendMessage(ChatColor.GOLD + "[Hero] Your team's hero bar has been filled!");
    		PlayerInfo info = getPlayerInfo(teamPlayer.getName());
    		
    		// Heroic Perk
    		if(info.currentPerk.equals("Heroic"))
    		{
    			info.heroPoints += (int)(info.heroic * 2.5);
    		}
    		
    		for(int i = 0; i < info.heroPoints; i++)
    		{
    			players.add(teamPlayer);
    		}
    		info.heroPoints = 0;
    	}
    	
    	// Select the hero
    	Player heroPlayer = players.get(rgen.nextInt(players.size()));
    	heroPlayer.sendMessage(ChatColor.GOLD + "[Hero] You have been selected as your team's hero! You will become a hero on your next respawn!");
    	getPlayerInfo(heroPlayer.getName()).heroRespawn = true;
    	
    	for(OfflinePlayer tp : team.getPlayers())
    	{
    		Player teamPlayer = tp.getPlayer();
    		if(!teamPlayer.equals(heroPlayer))
    		{
    			teamPlayer.getPlayer().sendMessage(ChatColor.GOLD + "[Hero] " + heroPlayer.getName() + " has been selected as your team's hero, and will become a hero on their next respawn!");
    		}
    	}
    }
	
	BukkitScheduler scheduler;
	
	String mapName = "Nebula";
	World lobby;
	World gameWorld;
	
    Location center;
    float centerDistance;
    // Center 540, 5, 731
    Location redSpawn; //-540, 6, 697
    Location blueSpawn; //-540, 6, 765
    Location greenSpawn; //-506, 6, 731
    Location purpleSpawn; //-574, 6, 731
    
    List<Arrow> archerArrows = new ArrayList<Arrow>(); // Arrows fired by the Archer Hero
    List<Arrow> tracerArrows = new ArrayList<Arrow>(); // Arrows fired by the Tracer Hero
    List<Arrow> artoriaArrows = new ArrayList<Arrow>(); // Arrows fired by Artoria's Divine Light
    List<Arrow> apolloArrows = new ArrayList<Arrow>(); // Arrows fired by Apollo
    
    // Method for setting Map
    public void setMap()
    {
		// Set Locations
		switch(mapName)
		{
			case "Nebula":
				gameWorld = Bukkit.getServer().createWorld(new WorldCreator("Nebula"));
				gameWorld.setAutoSave(false);
				gameWorld.setGameRuleValue("doMobSpawning", "false");
				gameWorld.setGameRuleValue("doDaylightCycle", "false");
				gameWorld.setGameRuleValue("doFireTick", "false");
				center = new Location(gameWorld, -540.5, 10, 731.5);
				centerDistance = 5.0f;
				redSpawn = new Location(gameWorld, -540, 11, 697);
				blueSpawn = new Location(gameWorld, -540, 11, 765);
				greenSpawn = new Location(gameWorld, -506, 11, 731);
				purpleSpawn = new Location(gameWorld, -574, 11, 731);
				gameWorld.setTime(6000);
				break;
				
			case "Block City":
				gameWorld = Bukkit.getServer().createWorld(new WorldCreator("Block City"));
				gameWorld.setAutoSave(false);
				gameWorld.setGameRuleValue("doMobSpawning", "false");
				gameWorld.setGameRuleValue("doDaylightCycle", "false");
				gameWorld.setGameRuleValue("doFireTick", "false");
				center = new Location(gameWorld, 43.5, 79, -43.5);
				centerDistance = 4.5f;
				redSpawn = new Location(gameWorld, 80, 72, -6);
				blueSpawn = new Location(gameWorld, 80, 72, -80);
				greenSpawn = new Location(gameWorld, 6, 72, -80);
				purpleSpawn = new Location(gameWorld, 6, 72, -6);
				gameWorld.setTime(6000);
				break;
				
			case "Tetra":
				gameWorld = Bukkit.getServer().createWorld(new WorldCreator("Tetra"));
				gameWorld.setAutoSave(false);
				gameWorld.setGameRuleValue("doMobSpawning", "false");
				gameWorld.setGameRuleValue("doDaylightCycle", "false");
				gameWorld.setGameRuleValue("doFireTick", "false");
				center = new Location(gameWorld, 42.5, 72, 0.5);
				centerDistance = 6.5f;
				redSpawn = new Location(gameWorld, 18, 168, 24);
				blueSpawn = new Location(gameWorld, 18, 168, -24);
				greenSpawn = new Location(gameWorld, 66, 168, -24);
				purpleSpawn = new Location(gameWorld, 66, 168, 24);
				gameWorld.setTime(6000);
				break;
				
			case "Nativus":
				gameWorld = Bukkit.getServer().createWorld(new WorldCreator("Nativus"));
				gameWorld.setAutoSave(false);
				gameWorld.setGameRuleValue("doMobSpawning", "false");
				gameWorld.setGameRuleValue("doDaylightCycle", "false");
				gameWorld.setGameRuleValue("doFireTick", "false");
				center = new Location(gameWorld, 0.5, 22, 0.5);
				centerDistance = 5.7f;
				redSpawn = new Location(gameWorld, 48, 27, -48);
				blueSpawn = new Location(gameWorld, -49, 22, -49);
				greenSpawn = new Location(gameWorld, -51, 25, 51);
				purpleSpawn = new Location(gameWorld, 52, 21, 52);
				gameWorld.setTime(6000);
				break;
		}
    }
	
	// Method for setting classes
	public void setClasses()
	{
		Warrior w = new Warrior();
		classes.add(w.returnClass());
		Archer a = new Archer();
		classes.add(a.returnClass());
		Mage m = new Mage();
		classes.add(m.returnClass());
		Spider s = new Spider();
		classes.add(s.returnClass());
		Annoyance annoy = new Annoyance();
		classes.add(annoy.returnClass());
		Ninja n = new Ninja();
		classes.add(n.returnClass());
		Demo d = new Demo();
		classes.add(d.returnClass());
		Soldier sol = new Soldier();
		classes.add(sol.returnClass());
		Scout scout = new Scout();
		classes.add(scout.returnClass());
		Shaman sham = new Shaman();
		classes.add(sham.returnClass());
		Marine mar = new Marine();
		classes.add(mar.returnClass());
		Witch witch = new Witch();
		classes.add(witch.returnClass());
		Blitzer blitz = new Blitzer();
		classes.add(blitz.returnClass());
		Marksman mark = new Marksman();
		classes.add(mark.returnClass());
		Assassin nite = new Assassin();
		classes.add(nite.returnClass());
	}
	
	// Method for setting heroes and hero packs
	public void setHeroes()
	{
		// Lena
		HeroLena lena = new HeroLena();
		ControlPointHero lenaHero = lena.returnHero();
		heroes.add(lenaHero);
		
		// Gilgamesh
		HeroGilgamesh gilgamesh = new HeroGilgamesh();
		ControlPointHero gilgameshHero = gilgamesh.returnHero();
		heroes.add(gilgameshHero);
		
		// Zeus
		HeroZeus zeus = new HeroZeus();
		ControlPointHero zeusHero = zeus.returnHero();
		heroes.add(zeusHero);
		
		// Fenrir
		HeroFenrir fenrir = new HeroFenrir();
		ControlPointHero fenrirHero = fenrir.returnHero();
		heroes.add(fenrirHero);
		
		// Cecilia
		HeroCecilia cecilia = new HeroCecilia();
		ControlPointHero ceciliaHero = cecilia.returnHero();
		heroes.add(ceciliaHero);
		
		// Azazel
		HeroAzazel azazel = new HeroAzazel();
		ControlPointHero azazelHero = azazel.returnHero();
		heroes.add(azazelHero);
		
		// Artoria
		HeroArtoria artoria = new HeroArtoria();
		ControlPointHero artoriaHero = artoria.returnHero();
		heroes.add(artoriaHero);
		
		// Noiresse
		HeroNoiresse noiresse = new HeroNoiresse();
		ControlPointHero noiresseHero = noiresse.returnHero();
		heroes.add(noiresseHero);
		
		// Kronos
		HeroKronos kronos = new HeroKronos();
		ControlPointHero kronosHero = kronos.returnHero();
		heroes.add(kronosHero);
		
		// Adrestia
		HeroAdrestia adrestia = new HeroAdrestia();
		ControlPointHero adrestiaHero = adrestia.returnHero();
		heroes.add(adrestiaHero);
		
		// Achyls
		HeroAchlys achlys = new HeroAchlys();
		ControlPointHero achlysHero = achlys.returnHero();
		heroes.add(achlysHero);
		
		// Terminus
		HeroTerminus terminus = new HeroTerminus();
		ControlPointHero terminusHero = terminus.returnHero();
		heroes.add(terminusHero);
		
		// Apollo
		HeroApollo apollo = new HeroApollo();
		ControlPointHero apolloHero = apollo.returnHero();
		heroes.add(apolloHero);
		
		// Khaos
		HeroKhaos khaos = new HeroKhaos();
		ControlPointHero khaosHero = khaos.returnHero();
		heroes.add(khaosHero);
		
		// Hephaestus
		HeroHephaestus hephaestus = new HeroHephaestus();
		ControlPointHero hephaestusHero = hephaestus.returnHero();
		heroes.add(hephaestusHero);
		
		// Set hero packs
		HeroPack allrounder = new HeroPack("All Rounder", gilgameshHero, adrestiaHero, zeusHero);
		heroPacks.add(allrounder);
		HeroPack ranged = new HeroPack("Ranged", lenaHero, achlysHero, gilgameshHero);
		heroPacks.add(ranged);
		HeroPack tank = new HeroPack("Tank", artoriaHero, terminusHero, adrestiaHero);
		heroPacks.add(tank);
		HeroPack stealthy = new HeroPack("Stealthy", azazelHero, noiresseHero, apolloHero);
		heroPacks.add(stealthy);
		HeroPack utility = new HeroPack("Utility", khaosHero, lenaHero, noiresseHero);
		heroPacks.add(utility);
		HeroPack agile = new HeroPack("Agile", kronosHero, fenrirHero, khaosHero);
		heroPacks.add(agile);
		HeroPack magic = new HeroPack("Magic", zeusHero, hephaestusHero, kronosHero);
		heroPacks.add(magic);
		HeroPack support = new HeroPack("Support", ceciliaHero, apolloHero, terminusHero);
		heroPacks.add(support);
		HeroPack fiery = new HeroPack("Fiery", hephaestusHero, azazelHero, artoriaHero);
		heroPacks.add(fiery);
		HeroPack poisonous = new HeroPack("Poisonous", achlysHero, ceciliaHero, fenrirHero);
		heroPacks.add(poisonous);
	}
	
	// Code to handle Player chagning class
	public void openClassMenu(Player p)
	{
		Inventory classInv = Bukkit.createInventory(null, 27, ChatColor.BOLD + "" + ChatColor.DARK_GRAY + "Select a Class!");
		
		for(int i = 0; i < classes.size(); i++)
		{
			classInv.setItem(i, classes.get(i).returnInventoryStack(getPlayerInfo(p.getName())));
		}
		p.openInventory(classInv);
	}
	
	// Code to handle Perk Menu
	public void openPerkMenu(Player p)
	{
		Inventory perkInv = Bukkit.createInventory(null, 54, ChatColor.BOLD + "" + ChatColor.DARK_GRAY + "Perks!");
		
		// Glass Pane Background
		for(int i = 0; i < perkInv.getSize(); i++)
		{
			ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7);
			ItemMeta paneMeta = pane.getItemMeta();
			paneMeta.setDisplayName(" ");
			pane.setItemMeta(paneMeta);
			perkInv.setItem(i, pane);
		}
		
		// Perk Menu Information
		ItemStack perks = new ItemStack(Material.EMERALD);
		ItemMeta perksMeta = perks.getItemMeta();
		perksMeta.setDisplayName(ChatColor.GOLD + "Perks!");
		perksMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Click on a perk to equip it!", ChatColor.AQUA + "Use the emeralds to upgrade", ChatColor.AQUA + "your perks!")));
		perks.setItemMeta(perksMeta);
		perkInv.setItem(13, perks);
		
		// Powersurge
		ItemStack powersurge = new ItemStack(Material.REDSTONE);
		ItemMeta powersurgeMeta = powersurge.getItemMeta();
		powersurgeMeta.setDisplayName(ChatColor.GOLD + "Powersurge");
		powersurgeMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Chance to spawn with increased", ChatColor.AQUA + "strength!")));
		powersurge.setItemMeta(powersurgeMeta);
		perkInv.setItem(28, powersurge);
		
		// Ironskin
		ItemStack ironskin = new ItemStack(Material.SHIELD);
		ItemMeta ironskinMeta = ironskin.getItemMeta();
		ironskinMeta.setDisplayName(ChatColor.GOLD + "Ironskin");
		ironskinMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Chance to spawn with increased", ChatColor.AQUA + "resistance and absorption!")));
		ironskin.setItemMeta(ironskinMeta);
		perkInv.setItem(29, ironskin);
		
		// Immortal
		ItemStack immortal = new ItemStack(Material.GOLDEN_APPLE);
		ItemMeta immortalMeta = immortal.getItemMeta();
		immortalMeta.setDisplayName(ChatColor.GOLD + "Immortal");
		immortalMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Chance to heal back to full", ChatColor.AQUA + "health instead of dying!")));
		immortal.setItemMeta(immortalMeta);
		perkInv.setItem(30, immortal);
		
		// Explosive
		ItemStack explosive = new ItemStack(Material.TNT);
		ItemMeta explosiveMeta = explosive.getItemMeta();
		explosiveMeta.setDisplayName(ChatColor.GOLD + "Explosive");
		explosiveMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Chance to explode shortly", ChatColor.AQUA + "after death!")));
		explosive.setItemMeta(explosiveMeta);
		perkInv.setItem(31, explosive);
		
		// Resourceful
		ItemStack resourceful = new ItemStack(Material.NETHER_STAR);
		ItemMeta resourcefulMeta = resourceful.getItemMeta();
		resourcefulMeta.setDisplayName(ChatColor.GOLD + "Resourceful");
		resourcefulMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Chance to find killstreak items", ChatColor.AQUA + "when killing players or", ChatColor.AQUA + "getting points!")));
		resourceful.setItemMeta(resourcefulMeta);
		perkInv.setItem(32, resourceful);
		
		// Heroic
		ItemStack heroic = new ItemStack(Material.GOLD_CHESTPLATE);
		ItemMeta heroicMeta = heroic.getItemMeta();
		heroicMeta.setDisplayName(ChatColor.GOLD + "Heroic");
		heroicMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Increases chance of becoming the", ChatColor.AQUA + "hero when your team's hero", ChatColor.AQUA + "bar is full!")));
		heroic.setItemMeta(heroicMeta);
		perkInv.setItem(33, heroic);
		
		// Godslayer
		ItemStack godslayer = new ItemStack(Material.GOLD_SWORD);
		ItemMeta godslayerMeta = godslayer.getItemMeta();
		godslayerMeta.setDisplayName(ChatColor.GOLD + "Godslayer");
		godslayerMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Chance to deal double damage", ChatColor.AQUA + "when damaging a hero!")));
		godslayer.setItemMeta(godslayerMeta);
		perkInv.setItem(34, godslayer);
		
		p.openInventory(perkInv);
	}
	
	// Code to handle Hero Pack Menu
	public void openPackMenu(Player p)
	{
		Inventory heroInv = Bukkit.createInventory(null, 54, ChatColor.BOLD + "" + ChatColor.DARK_GRAY + "Heroes!");
		PlayerInfo pInfo = getPlayerInfo(p.getName());
			
		// Glass Pane Background
		for(int i = 0; i < heroInv.getSize(); i++)
		{
			ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7);
			ItemMeta paneMeta = pane.getItemMeta();
			paneMeta.setDisplayName(" ");
			pane.setItemMeta(paneMeta);
			heroInv.setItem(i, pane);
		}
			
		// Book
		ItemStack book = new ItemStack(Material.BOOK);
		ItemMeta bookMeta = book.getItemMeta();
		bookMeta.setDisplayName(ChatColor.GOLD + "Information");
		bookMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Click on a hero pack", ChatColor.AQUA + "to equip that pack.", ChatColor.AQUA + "Click on a hero to", net.md_5.bungee.api.ChatColor.AQUA + "see more information about", ChatColor.AQUA + "that hero.")));
		book.setItemMeta(bookMeta);
		heroInv.setItem(50, book);
			
		// Pack Creator
		ItemStack packCreator = new ItemStack(Material.WORKBENCH);
		ItemMeta packCreatorMeta = packCreator.getItemMeta();
		packCreatorMeta.setDisplayName(ChatColor.GOLD + "Hero Pack Creator");
		packCreator.setItemMeta(packCreatorMeta);
		heroInv.setItem(51, packCreator);
		
		// Exit Arrow
		ItemStack exit = new ItemStack(Material.ARROW);
		ItemMeta exitMeta = exit.getItemMeta();
		exitMeta.setDisplayName(ChatColor.GOLD + "Exit");
		exit.setItemMeta(exitMeta);
		heroInv.setItem(52, exit);
		
		// All Rounder
		ItemStack allrounderStack = new ItemStack(Material.IRON_SWORD);
		ItemMeta allrounderMeta = allrounderStack.getItemMeta();
		allrounderMeta.setDisplayName(ChatColor.GOLD + "All Rounder");
		allrounderMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A balanced Hero Pack suitable", ChatColor.AQUA + "for all playstyles!")));
		allrounderStack.setItemMeta(allrounderMeta);
		heroPacks.get(0).addStacks(allrounderStack, heroInv, 0, pInfo);
		
		// Ranged
		ItemStack rangedStack = new ItemStack(Material.BOW);
		ItemMeta rangedMeta = rangedStack.getItemMeta();
		rangedMeta.setDisplayName(ChatColor.GOLD + "Ranged");
		rangedMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A Hero Pack of ranged", ChatColor.AQUA + "and bow wielding heroes!")));
		rangedStack.setItemMeta(rangedMeta);
		heroPacks.get(1).addStacks(rangedStack, heroInv, 9, pInfo);
		
		// Tank
		ItemStack p3Stack = new ItemStack(Material.IRON_CHESTPLATE);
		ItemMeta p3Meta = p3Stack.getItemMeta();
		p3Meta.setDisplayName(ChatColor.GOLD + "Tank");
		p3Meta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A Hero Pack of durable,", ChatColor.AQUA + "resistant heroes!")));
		p3Stack.setItemMeta(p3Meta);
		heroPacks.get(2).addStacks(p3Stack, heroInv, 18, pInfo);
		
		// Stealthy
		ItemStack stealthyStack = new ItemStack(Material.EYE_OF_ENDER);
		ItemMeta stealthyMeta = stealthyStack.getItemMeta();
		stealthyMeta.setDisplayName(ChatColor.GOLD + "Stealthy");
		stealthyMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A Hero Pack of sneaky,", ChatColor.AQUA + "stealthy heroes!")));
		stealthyStack.setItemMeta(stealthyMeta);
		heroPacks.get(3).addStacks(stealthyStack, heroInv, 27, pInfo);
		
		// Utility
		ItemStack utilityStack = new ItemStack(Material.COMPASS);
		ItemMeta utilityMeta = utilityStack.getItemMeta();
		utilityMeta.setDisplayName(ChatColor.GOLD + "Utility");
		utilityMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A Hero Pack of heroes", ChatColor.AQUA + "who use a variety of items!")));
		utilityStack.setItemMeta(utilityMeta);
		heroPacks.get(4).addStacks(utilityStack, heroInv, 36, pInfo);
		
		// Agile
		ItemStack agileStack = new ItemStack(Material.FEATHER);
		ItemMeta agileMeta = agileStack.getItemMeta();
		agileMeta.setDisplayName(ChatColor.GOLD + "Agile");
		agileMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A Hero Pack of agile", ChatColor.AQUA + "and mobile heroes!")));
		agileStack.setItemMeta(agileMeta);
		heroPacks.get(5).addStacks(agileStack, heroInv, 45, pInfo);
		
		// Magic
		ItemStack magicStack = new ItemStack(Material.BLAZE_ROD);
		ItemMeta magicMeta = magicStack.getItemMeta();
		magicMeta.setDisplayName(ChatColor.GOLD + "Magic");
		magicMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A Hero Pack of magical", ChatColor.AQUA + "heroes!")));
		magicStack.setItemMeta(magicMeta);
		heroPacks.get(6).addStacks(magicStack, heroInv, 5, pInfo);
		
		// Support
		ItemStack supportStack = new ItemStack(Material.GOLDEN_APPLE);
		ItemMeta supportMeta = supportStack.getItemMeta();
		supportMeta.setDisplayName(ChatColor.GOLD + "Support");
		supportMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A Hero Pack of support", ChatColor.AQUA + "heroes who help their allies!")));
		supportStack.setItemMeta(supportMeta);
		heroPacks.get(7).addStacks(supportStack, heroInv, 14, pInfo);
		
		// Fiery
		ItemStack fieryStack = new ItemStack(Material.BLAZE_POWDER);
		ItemMeta fieryMeta = fieryStack.getItemMeta();
		fieryMeta.setDisplayName(ChatColor.GOLD + "Fiery");
		fieryMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A Hero Pack of fire wielding", ChatColor.AQUA + "heroes who ignite enemies!")));
		fieryStack.setItemMeta(fieryMeta);
		heroPacks.get(8).addStacks(fieryStack, heroInv, 23, pInfo);
		
		// Poisonous
		ItemStack poisonousStack = new Potion(PotionType.POISON, 1, true).toItemStack(1);
		ItemMeta poisonousMeta = poisonousStack.getItemMeta();
		poisonousMeta.setDisplayName(ChatColor.GOLD + "Poisonous");
		poisonousMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A Hero Pack of heroes who", ChatColor.AQUA + "poison and wither enemies!")));
		poisonousStack.setItemMeta(poisonousMeta);
		heroPacks.get(9).addStacks(poisonousStack, heroInv, 32, pInfo);
		
		p.openInventory(heroInv);
	}
	
	// Open Hero Information  Menu
	public void openHeroMenu(Player player, ControlPointHero hero)
	{
		Inventory heroInv = Bukkit.createInventory(null, 54, ChatColor.BOLD + "" + ChatColor.DARK_GRAY + hero.heroName);
		
		// Glass Pane Background
		for(int i = 0; i < heroInv.getSize(); i++)
		{
			ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7);
			ItemMeta paneMeta = pane.getItemMeta();
			paneMeta.setDisplayName(" ");
			pane.setItemMeta(paneMeta);
			heroInv.setItem(i, pane);
		}
		
		// Brown glass
		ItemStack brown = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)12);
		ItemMeta brownMeta = brown.getItemMeta();
		brownMeta.setDisplayName(ChatColor.GOLD + "Hero Information");
		brown.setItemMeta(brownMeta);
		heroInv.setItem(10, brown);
		heroInv.setItem(28, brown);
		
		// Hero Information Book
		ItemStack book = new ItemStack(Material.BOOK);
		ItemMeta bookMeta = book.getItemMeta();
		bookMeta.setDisplayName(ChatColor.GOLD + hero.heroName);
		bookMeta.setLore(hero.heroLore);
		book.setItemMeta(bookMeta);
		heroInv.setItem(19, book);
		
		// Yellow glass
		ItemStack yellow = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)4);
		ItemMeta yellowMeta = yellow.getItemMeta();
		yellowMeta.setDisplayName(ChatColor.GOLD + "Active Ability");
		yellow.setItemMeta(yellowMeta);
		heroInv.setItem(12, yellow);
		heroInv.setItem(30, yellow);
		
		// Active Ability
		ItemStack active = new ItemStack(Material.GLOWSTONE_DUST);
		ItemMeta activeMeta = active.getItemMeta();
		activeMeta.setDisplayName(ChatColor.GOLD + "Active Ability Name");
		// Split the active string into a list
		/*int count = 0;
		int count2 = 0;
		int lastcount = 0;
		List<String> activeList = new ArrayList<String>();
		while(count < hero.activeAbility.length())
		{
			count++;
			count2++;
			if(count2 >= 20 && hero.activeAbility.substring(count, count + 1).equals(" "))
			{
				activeList.add(hero.activeAbility.substring(lastcount, count2));
				lastcount+=count2;
				count2 = 0;
			}
			else if(count == hero.activeAbility.length())
			{
				activeList.add(hero.activeAbility.substring(lastcount, count));
			}
		}*/
		//activeMeta.setLore(activeList);
		active.setItemMeta(activeMeta);
		heroInv.setItem(21, active);
		
		// Gray glass
		ItemStack gray = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)8);
		ItemMeta grayMeta = gray.getItemMeta();
		grayMeta.setDisplayName(ChatColor.GOLD + "Passive Ability");
		gray.setItemMeta(grayMeta);
		heroInv.setItem(14, gray);
		heroInv.setItem(32, gray);
		
		// Passive Ability
		ItemStack passive = new ItemStack(Material.SULPHUR);
		ItemMeta passiveMeta = passive.getItemMeta();
		passiveMeta.setDisplayName(ChatColor.GOLD + "Passive Ability Name");
		passive.setItemMeta(passiveMeta);
		heroInv.setItem(23, passive);

		// Armor
		//7, 16, 25, 34
		heroInv.setItem(7, new ItemStack(Material.AIR));
		heroInv.setItem(16, new ItemStack(Material.AIR));
		heroInv.setItem(25, new ItemStack(Material.AIR));
		heroInv.setItem(34, new ItemStack(Material.AIR));
		for(ItemStack armor : hero.heroArmor)
		{
			if(armor.getType() == Material.LEATHER_HELMET || armor.getType() == Material.GOLD_HELMET || armor.getType() == Material.CHAINMAIL_HELMET || armor.getType() == Material.IRON_HELMET|| armor.getType() == Material.DIAMOND_HELMET)
			{
				heroInv.setItem(7, armor);
			}
			if(armor.getType() == Material.LEATHER_CHESTPLATE || armor.getType() == Material.GOLD_CHESTPLATE || armor.getType() == Material.CHAINMAIL_CHESTPLATE || armor.getType() == Material.IRON_CHESTPLATE|| armor.getType() == Material.DIAMOND_CHESTPLATE || armor.getType() == Material.ELYTRA)
			{
				heroInv.setItem(16, armor);
			}
			if(armor.getType() == Material.LEATHER_LEGGINGS || armor.getType() == Material.GOLD_LEGGINGS || armor.getType() == Material.CHAINMAIL_LEGGINGS || armor.getType() == Material.IRON_LEGGINGS || armor.getType() == Material.DIAMOND_LEGGINGS)
			{
				heroInv.setItem(25, armor);
			}
			if(armor.getType() == Material.LEATHER_BOOTS || armor.getType() == Material.GOLD_BOOTS || armor.getType() == Material.CHAINMAIL_BOOTS || armor.getType() == Material.IRON_BOOTS || armor.getType() == Material.DIAMOND_BOOTS)
			{
				heroInv.setItem(34, armor);
			}
		}
		
		// Items
		heroInv.setItem(45, new ItemStack(Material.AIR));
		heroInv.setItem(46, new ItemStack(Material.AIR));
		heroInv.setItem(47, new ItemStack(Material.AIR));
		heroInv.setItem(48, new ItemStack(Material.AIR));
		heroInv.setItem(49, new ItemStack(Material.AIR));
		heroInv.setItem(50, new ItemStack(Material.AIR));
		for(int i = 0; i < hero.heroItems.size(); i++)
		{
			heroInv.setItem(45 + i, hero.heroItems.get(i));
		}
		
		// Back Arrow
		ItemStack exit = new ItemStack(Material.ARROW);
		ItemMeta exitMeta = exit.getItemMeta();
		exitMeta.setDisplayName(ChatColor.GOLD + "Back");
		exit.setItemMeta(exitMeta);
		heroInv.setItem(52, exit);
		
		player.openInventory(heroInv);
	}
	
	// Set the player's xp bar based on the xp they have / xp to the next level
	public void setXPBar(Player p)
	{
		PlayerInfo info = getPlayerInfo(p.getName());
		float bar = ((float)info.xp / info.xpToLevel);
		if(bar > 1)
		{
			bar = 1;
		}
		p.setLevel(info.level);
		p.setExp(bar);
	}
	
	
	// Method to give a player xp
	public void giveXP(Player p, String type)
	{
		PlayerInfo info = getPlayerInfo(p.getName());
			
		// Increase xp when the player has a killstreak
		float killstreakMod = 1 + ((info.killstreak) * 0.2f);
		if(killstreakMod > 5)
		{
			killstreakMod = 5;
		}
			
		// Give xp, based on whether the player has killed a player, gotten points, or won the game
		switch(type)
		{
			case "Kill": info.xp += 25 * killstreakMod;
				break;
			case "Point": info.xp += 50 * killstreakMod;
				break;
			case "Win": info.xp += 200;
				break;
		}
			
		// Increase level
		while(info.xp >= info.xpToLevel && info.level < 70)
		{
			info.xp -= info.xpToLevel;
			info.level++;
			info.calculateXP();
			p.sendMessage(ChatColor.GOLD + "You have gained a level! You are now level " + info.level + "!");
			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
		}
			
		setXPBar(p);
	}
		
	// Method to give a player coins, does nothing now
	public void giveCoins(Player p, String type)
	{
		PlayerInfo info = getPlayerInfo(p.getName());
		switch(type)
		{
		case "Kill": info.gameCoins += (3 + info.killstreak);
			break;
		case "Capture": info.gameCoins += 10;
			break;
		case "Leader": info.gameCoins += 20;
			break;
		case "Win": info.gameCoins += 50;
			break;
		}
	}
	
	public void endCoins(Player p)
	{
		PlayerInfo info = getPlayerInfo(p.getName());
		info.coins += info.gameCoins;
		info.mantlecoins += info.gameCoins;
		p.sendMessage(ChatColor.GOLD + "You have received " + info.gameCoins + " Control Heroes coins and Mantle Coins!");
	}
		
	// Code to handle Player selecting teams
	public void openTeamMenu(Player p)
	{
		Inventory teamInv = Bukkit.createInventory(null, 9, ChatColor.BOLD + "" + ChatColor.DARK_GRAY + "Select a Team!");
				
		// Red Team Wool
		ItemStack red = new ItemStack(Material.WOOL, 1, (byte)14);
		ItemMeta redMeta = red.getItemMeta();
		redMeta.setDisplayName(ChatColor.RED + "Join Red Team");
		List<String> redLore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Players: " + teamManager.redTeam.getPlayers().size() + "/6"));
		for(OfflinePlayer offP : teamManager.redTeam.getPlayers())
		{
			redLore.add(ChatColor.AQUA + offP.getPlayer().getDisplayName());
		}
		redMeta.setLore(redLore);
		red.setItemMeta(redMeta);
		teamInv.setItem(1, red);
				
		// Blue Team Wool
		ItemStack blue = new ItemStack(Material.WOOL, 1, (byte)11);
		ItemMeta blueMeta = blue.getItemMeta();
		blueMeta.setDisplayName(ChatColor.BLUE + "Join Blue Team");
		List<String> blueLore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Players: " + teamManager.blueTeam.getPlayers().size() + "/6"));
		for(OfflinePlayer offP : teamManager.blueTeam.getPlayers())
		{
			blueLore.add(ChatColor.AQUA + offP.getPlayer().getDisplayName());
		}
		blueMeta.setLore(blueLore);
		blue.setItemMeta(blueMeta);
		teamInv.setItem(3, blue);
				
		// Green Team Wool
		ItemStack green = new ItemStack(Material.WOOL, 1, (byte)5);
		ItemMeta greenMeta = green.getItemMeta();
		greenMeta.setDisplayName(ChatColor.GREEN + "Join Green Team");
		List<String> greenLore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Players: " + teamManager.greenTeam.getPlayers().size() + "/6"));
		for(OfflinePlayer offP : teamManager.greenTeam.getPlayers())
		{
			greenLore.add(ChatColor.AQUA + offP.getPlayer().getDisplayName());
		}
		greenMeta.setLore(greenLore);
		green.setItemMeta(greenMeta);
		teamInv.setItem(5, green);
				
		// Purple Team Wool
		ItemStack purple = new ItemStack(Material.WOOL, 1, (byte)10);
		ItemMeta purpleMeta = purple.getItemMeta();
		purpleMeta.setDisplayName(ChatColor.DARK_PURPLE + "Join Purple Team");
		List<String> purpleLore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Players: " + teamManager.purpleTeam.getPlayers().size() + "/6"));
		for(OfflinePlayer offP : teamManager.purpleTeam.getPlayers())
		{
			purpleLore.add(ChatColor.AQUA + offP.getPlayer().getDisplayName());
		}
		purpleMeta.setLore(purpleLore);
		purple.setItemMeta(purpleMeta);
		teamInv.setItem(7, purple);
				
		p.openInventory(teamInv);
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
	
	public void nullDeath(Player player)
	{
		PlayerInfo playerInfo = getPlayerInfo(player.getName());
		player.sendMessage(ChatColor.DARK_RED + "You have died!");
		
		// Player dies
		player.getInventory().clear();
		player.setHealth(20);
		player.setFireTicks(0);
		
		// Launch firework on death
		Location fwLoc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() + 1.2, player.getLocation().getZ());
		Firework fw = (Firework)player.getWorld().spawnEntity(fwLoc, EntityType.FIREWORK);
		FireworkMeta fwMeta = fw.getFireworkMeta();
		switch(ChatColor.stripColor(getPlayerTeam(player).getName()))
		{
			case "Red Team": fwMeta.addEffect(FireworkEffect.builder().trail(false).flicker(false).withColor(Color.RED).withFade(Color.RED).with(FireworkEffect.Type.BALL).build());
				break;
			case "Blue Team": fwMeta.addEffect(FireworkEffect.builder().trail(false).flicker(false).withColor(Color.BLUE).withFade(Color.BLUE).with(FireworkEffect.Type.BALL).build());
				break;
			case "Green Team": fwMeta.addEffect(FireworkEffect.builder().trail(false).flicker(false).withColor(Color.GREEN).withFade(Color.GREEN).with(FireworkEffect.Type.BALL).build());
				break;
			case "Purple Team": fwMeta.addEffect(FireworkEffect.builder().trail(false).flicker(false).withColor(Color.PURPLE).withFade(Color.PURPLE).with(FireworkEffect.Type.BALL).build());
				break;
		}
		
		// Increase deaths
		playerInfo.deaths++;
		playerInfo.totalDeaths++;
		playerInfo.killstreak = 0;
		playerInfo.protectTimer = 4;
		playerInfo.pointBonus = false;
		
		// Give player items
		if(playerInfo.heroRespawn == false)
		{
			player.getInventory().setHelmet(teamManager.getIdentifier(player));
		}
		ItemStack classSelector = new ItemStack(Material.NAME_TAG);
		ItemMeta classMeta = classSelector.getItemMeta();
		classMeta.setDisplayName(ChatColor.GOLD + "Class Selector");
		classSelector.setItemMeta(classMeta);
		player.getInventory().setItem(8, classSelector);
		
		// Equip class
		if(playerInfo.changeClass != "")
		{
			playerInfo.currentClass = playerInfo.changeClass;
			playerInfo.changeClass = "";
		}
		
		if(playerInfo.heroRespawn == false)
		{
			for(int i = 0; i < classes.size(); i++)
			{
				if(ChatColor.stripColor(getPlayerInfo(player.getName()).currentClass).equals(ChatColor.stripColor(classes.get(i).className)))
				{
					ControlPointClass eClass = classes.get(i);
					equipClass(player, eClass);
					break;
				}
			}
		}
		
		// Hero Death?
		if(playerInfo.isHero)
		{
			playerInfo.isHero = false;
			switch(ChatColor.stripColor(getPlayerTeam(player).getName()))
			{
				case "Red Team": redHeroActive.removeAll();
				redHero = 0;
				redHeroBar.setProgress(0);
				for(OfflinePlayer teamPlayer : getPlayerTeam(player).getPlayers())
				{
					redHeroBar.addPlayer(teamPlayer.getPlayer());
				}
				break;
				case "Blue Team": blueHeroActive.removeAll();
				blueHero = 0;
				blueHeroBar.setProgress(0);
				for(OfflinePlayer teamPlayer : getPlayerTeam(player).getPlayers())
				{
					blueHeroBar.addPlayer(teamPlayer.getPlayer());
				}
				break;
				case "Green Team": greenHeroActive.removeAll();
				greenHero = 0;
				greenHeroBar.setProgress(0);
				for(OfflinePlayer teamPlayer : getPlayerTeam(player).getPlayers())
				{
					greenHeroBar.addPlayer(teamPlayer.getPlayer());
				}
				break;
				case "Purple Team": purpleHeroActive.removeAll();
				purpleHero = 0;
				purpleHeroBar.setProgress(0);
				for(OfflinePlayer teamPlayer : getPlayerTeam(player).getPlayers())
				{
					purpleHeroBar.addPlayer(teamPlayer.getPlayer());
				}
				break;
			}
		}
		
		// Teleport dead player to their spawn
		switch(ChatColor.stripColor(getPlayerTeam(player).getName()))
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
		
		// Trigger Perk
		if(playerInfo.currentPerk.equals("Powersurge") && playerInfo.heroRespawn == false)
		{
			triggerPowersurge(player);
		}
		
		if(playerInfo.currentPerk.equals("Ironskin") && playerInfo.heroRespawn == false)
		{
			triggerIronskin(player);
		}
		
		// The player is respawning as a hero
		if(playerInfo.heroRespawn)
		{
			playerInfo.isHero = true;
			playerInfo.heroRespawn = false;
			
			// Choose the hero of the player
			int heroNum = (int)(Math.random() * 3);
			String heroStr = "";
			HeroPack pack = null;
			for(HeroPack hPack : heroPacks)
			{
				if(hPack.packName.equals(playerInfo.currentPack))
				{
					pack = hPack;
					break;
				}
			}
			switch(heroNum)
			{
				case 0: heroStr = pack.hero1.heroName;
				break;
				
				case 1: heroStr = pack.hero2.heroName;
				break;
				
				case 2: heroStr = pack.hero3.heroName;
				break;
			}
			
			playerInfo.currentHero = heroStr;
			for(int i = 0; i < heroes.size(); i++)
			{
				if(ChatColor.stripColor(playerInfo.currentHero).equals(ChatColor.stripColor(heroes.get(i).heroName)))
				{
					ControlPointHero eHero = heroes.get(i);
					equipHero(player, eHero);
					player.sendMessage("");
					player.sendMessage(ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-");
					player.sendMessage(ChatColor.GOLD + heroes.get(i).heroName);
					player.sendMessage(ChatColor.AQUA + "Passive Ability: " + heroes.get(i).passiveAbility);
					player.sendMessage(ChatColor.LIGHT_PURPLE + "Active Ability: " + heroes.get(i).activeAbility);
					player.sendMessage(ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-");
					player.sendMessage("");
					break;
				}
			}
			
			switch(ChatColor.stripColor(getPlayerTeam(player).getName()))
			{
				case "Red Team": redHeroBar.removeAll();
				redHero = 0;
				redHeroActive.setTitle(ChatColor.RED + player.getName() + "'s Active Ability Charge");
				redHeroActive.setProgress(0);
				for(OfflinePlayer teamPlayer : getPlayerTeam(player).getPlayers())
				{
					redHeroActive.addPlayer(teamPlayer.getPlayer());
				}
				coneParticles(player, 255, 0, 0); // Particle effect
				break;
				
				case "Blue Team": blueHeroBar.removeAll();
				blueHero = 0;
				blueHeroActive.setTitle(ChatColor.BLUE + player.getName() + "'s Active Ability Charge");
				blueHeroActive.setProgress(0);
				for(OfflinePlayer teamPlayer : getPlayerTeam(player).getPlayers())
				{
					blueHeroActive.addPlayer(teamPlayer.getPlayer());
				}
				coneParticles(player, 0, 0, 255); // Particle effect
				break;
				
				case "Green Team": greenHeroBar.removeAll();
				greenHero = 0;
				greenHeroActive.setTitle(ChatColor.GREEN + player.getName() + "'s Active Ability Charge");
				greenHeroActive.setProgress(0);
				for(OfflinePlayer teamPlayer : getPlayerTeam(player).getPlayers())
				{
					greenHeroActive.addPlayer(teamPlayer.getPlayer());
				}
				coneParticles(player, 2, 75, 7); // Particle effect
				break;
				
				case "Purple Team": purpleHeroBar.removeAll();
				purpleHero = 0;
				purpleHeroActive.setTitle(ChatColor.DARK_PURPLE + player.getName() + "'s Active Ability Charge");
				purpleHeroActive.setProgress(0);
				for(OfflinePlayer teamPlayer : getPlayerTeam(player).getPlayers())
				{
					purpleHeroActive.addPlayer(teamPlayer.getPlayer());
				}
				coneParticles(player, 89, 23, 117); // Particle effect
				break;
			}
			
			ChatColor playerColor = null;
			
			// Set Chat Colors
			switch(ChatColor.stripColor(getPlayerTeam(player).getName()))
			{
				case "Red Team": playerColor = ChatColor.RED;
				break;
				case "Blue Team": playerColor = ChatColor.BLUE;
				break;
				case "Green Team": playerColor = ChatColor.GREEN;
				break;
				case "Purple Team": playerColor = ChatColor.DARK_PURPLE;
				break;
			}
			
			Bukkit.broadcastMessage(playerColor + player.getName() + ChatColor.GOLD + " has respawned as " + ChatColor.AQUA + playerInfo.currentHero + ChatColor.GOLD + "!");
			
			// Timed Hero Items
			if(heroStr.equals("Lena"))
			{
				BukkitRunnable runnable = new BukkitRunnable()
				{
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(playerInfo.isHero && playerInfo.currentHero.equals("Lena"))
						{
							ItemStack blazeOrb = new ItemStack(Material.MAGMA_CREAM);
							ItemMeta blazeOrbMeta = blazeOrb.getItemMeta();
							blazeOrbMeta.setDisplayName(ChatColor.GOLD + "Blaze Orb");
							blazeOrb.setItemMeta(blazeOrbMeta);
							player.getInventory().addItem(blazeOrb);
						}
						else
						{
							this.cancel();
						}
					}
				};
				runnable.runTaskTimer(this, 300L, 300L);
			}
			
			if(heroStr.equals("Apollo"))
			{
				BukkitRunnable runnable = new BukkitRunnable()
				{
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(playerInfo.isHero && playerInfo.currentHero.equals("Apollo"))
						{
							ItemStack arrow = new ItemStack(Material.ARROW);
							player.getInventory().addItem(arrow);
						}
						else
						{
							this.cancel();
						}
					}
				};
				runnable.runTaskTimer(this, 280L, 280L);
			}
		}
	}
	
	// Method called when a player dies
	public void playerDie(Player player, Player killer)
	{
		if(killer == null)
		{
			nullDeath(player);
			return;
		}
		
		if(player == killer)
		{
			nullDeath(player);
			return;
		}
		PlayerInfo playerInfo = getPlayerInfo(player.getName());
		PlayerInfo killerInfo = getPlayerInfo(killer.getName());
		
		// Trigger Immortal
		if(playerInfo.currentPerk.equals("Immortal") && playerInfo.isHero == false)
		{
			if(triggerImmortal(player))
			{
				killer.sendMessage(ChatColor.DARK_RED + "[Immortal] " + player.getName() + " has defied death and regained all health!");
				player.setHealth(20);
				return;
			}
		}
		
		// Player dies
		player.getInventory().clear();
		player.setHealth(20);
		player.setFireTicks(0);
		
		// Launch firework on death
		Location fwLoc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() + 1.2, player.getLocation().getZ());
		Firework fw = (Firework)player.getWorld().spawnEntity(fwLoc, EntityType.FIREWORK);
		FireworkMeta fwMeta = fw.getFireworkMeta();
		switch(ChatColor.stripColor(getPlayerTeam(player).getName()))
		{
			case "Red Team": fwMeta.addEffect(FireworkEffect.builder().trail(false).flicker(false).withColor(Color.RED).withFade(Color.RED).with(FireworkEffect.Type.BALL).build());
				break;
			case "Blue Team": fwMeta.addEffect(FireworkEffect.builder().trail(false).flicker(false).withColor(Color.BLUE).withFade(Color.BLUE).with(FireworkEffect.Type.BALL).build());
				break;
			case "Green Team": fwMeta.addEffect(FireworkEffect.builder().trail(false).flicker(false).withColor(Color.GREEN).withFade(Color.GREEN).with(FireworkEffect.Type.BALL).build());
				break;
			case "Purple Team": fwMeta.addEffect(FireworkEffect.builder().trail(false).flicker(false).withColor(Color.PURPLE).withFade(Color.PURPLE).with(FireworkEffect.Type.BALL).build());
				break;
		}
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
		
		// Trigger Explosive
		if(playerInfo.currentPerk.equals("Explosive") && playerInfo.isHero == false)
		{
			if(triggerExplosive(player))
			{
				killer.sendMessage(ChatColor.DARK_RED + "[Explosive] The player you killed is exploding!");
				Location explodeLocation = player.getLocation();
				scheduler.scheduleSyncDelayedTask(this, new Runnable()
				{
					@Override
					public void run() {
						for(int i = 0; i < 30; i++)
						{
							Location explosionLocation = new Location(explodeLocation.getWorld(), explodeLocation.getX() + Math.floor(Math.random() * 5) - 2, explodeLocation.getY() + Math.floor(Math.random() * 8) - 2, explodeLocation.getZ() + Math.floor(Math.random() * 5) - 2);
							explosionLocation.getWorld().createExplosion(explosionLocation, 0.0f);
						}
						for(Player hitPlayer: Bukkit.getOnlinePlayers())
						{
							// Cancel Team Damage
							if(getPlayerTeam(player).getName().equals(getPlayerTeam(hitPlayer).getName()))
							{
								continue;
							}
							
							if(hitPlayer.getLocation().distance(explodeLocation) < 7)
							{
								if(hitPlayer.getHealth() - 7 <= 0)
								{
									// Player dies
									playerDie(hitPlayer, player);
								}
								else
								{
									hitPlayer.damage(7);
								}
							}
						}
					}
				}, 20L);
			}
		}
		
		ChatColor playerColor = null;
		ChatColor killerColor = null;
		
		switch(ChatColor.stripColor(getPlayerTeam(killer).getName()))
		{
			case "Red Team": killerColor = ChatColor.RED;
			break;
			case "Blue Team": killerColor = ChatColor.BLUE;
			break;
			case "Green Team": killerColor = ChatColor.GREEN;
			break;
			case "Purple Team": killerColor = ChatColor.DARK_PURPLE;
			break;
		}
		
		// Set Chat Colors
		switch(ChatColor.stripColor(getPlayerTeam(player).getName()))
		{
			case "Red Team": playerColor = ChatColor.RED;
			break;
			case "Blue Team": playerColor = ChatColor.BLUE;
			break;
			case "Green Team": playerColor = ChatColor.GREEN;
			break;
			case "Purple Team": playerColor = ChatColor.DARK_PURPLE;
			break;
		}
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 5));
		player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
		
		// Increase deaths
		playerInfo.deaths++;
		playerInfo.totalDeaths++;
		playerInfo.killstreak = 0;
		playerInfo.protectTimer = 4;
		playerInfo.pointBonus = false;
		
		// Increase kills
		killerInfo.kills++;
		killerInfo.totalKills++;
		killerInfo.killstreak++;
		if(killerInfo.killstreak > killerInfo.highestStreak)
		{
			killerInfo.highestStreak = killerInfo.killstreak;
		}
		giveCoins(killer, "Kill");
		
		// Give killstreak rewards
		if(killerInfo.isHero == false)
		{
			switch(killerInfo.killstreak)
			{
				// 2 Killstreak gives Hand Grenade
				case 2: killer.sendMessage(ChatColor.GOLD + "-[]-[] 2 Killstreak []-[]-");
				ItemStack grenade = new ItemStack(Material.EGG);
				ItemMeta grenadeMeta = grenade.getItemMeta();
				grenadeMeta.setDisplayName(ChatColor.GOLD + "Hand Grenade");
				grenade.setItemMeta(grenadeMeta);
				killer.getInventory().addItem(grenade);
				break;
				
				// 3 Killstreak gives Lightning Orb
				case 3: killer.sendMessage(ChatColor.GOLD + "-[]-[] 3 Killstreak []-[]-");
				ItemStack orb = new ItemStack(Material.SNOW_BALL);
				ItemMeta orbMeta = orb.getItemMeta();
				orbMeta.setDisplayName(ChatColor.GOLD + "Lightning Orb");
				orb.setItemMeta(orbMeta);
				killer.getInventory().addItem(orb);
				break;
				
				// 5 Killstreak gives Blaze Orb
				case 5: killer.sendMessage(ChatColor.GOLD + "-[]-[] 5 Killstreak []-[]-");
				ItemStack blazeOrb = new ItemStack(Material.MAGMA_CREAM);
				ItemMeta blazeOrbMeta = blazeOrb.getItemMeta();
				blazeOrbMeta.setDisplayName(ChatColor.GOLD + "Blaze Orb");
				blazeOrb.setItemMeta(blazeOrbMeta);
				killer.getInventory().addItem(blazeOrb);
				break;
				
				// 7 Killstreak gives Jump Boost and a lightning orb
				case 7: killer.sendMessage(ChatColor.GOLD + "-[]-[] 7 Killstreak []-[]-");
				if(killer.hasPotionEffect(PotionEffectType.JUMP))
				{
					killer.removePotionEffect(PotionEffectType.JUMP);
				}
				killer.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 600, 1));
				ItemStack orb2 = new ItemStack(Material.SNOW_BALL);
				ItemMeta orbMeta2 = orb2.getItemMeta();
				orbMeta2.setDisplayName(ChatColor.GOLD + "Lightning Orb");
				orb2.setItemMeta(orbMeta2);
				killer.getInventory().addItem(orb2);
				break;
				
				// 10 Killstreak gives Speed and a Nether Fragment
				case 10: killer.sendMessage(ChatColor.GOLD + "-[]-[] 10 Killstreak []-[]-");
				if(killer.hasPotionEffect(PotionEffectType.SPEED))
				{
					killer.removePotionEffect(PotionEffectType.SPEED);
				}
				killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 2));
				ItemStack nether = new ItemStack(Material.NETHER_STAR);
				ItemMeta netherMeta = nether.getItemMeta();
				netherMeta.setDisplayName(ChatColor.GOLD + "Nether Fragment");
				nether.setItemMeta(netherMeta);
				killer.getInventory().addItem(nether);
				break;
				
				// 12 Killsreak gives Regen and a Hand Grenade
				case 12: killer.sendMessage(ChatColor.GOLD + "-[]-[] 12 Killstreak []-[]-");
				if(killer.hasPotionEffect(PotionEffectType.REGENERATION))
				{
					killer.removePotionEffect(PotionEffectType.REGENERATION);
				}
				killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 1));
				ItemStack grenade2 = new ItemStack(Material.EGG);
				ItemMeta grenadeMeta2 = grenade2.getItemMeta();
				grenadeMeta2.setDisplayName(ChatColor.GOLD + "Hand Grenade");
				grenade2.setItemMeta(grenadeMeta2);
				killer.getInventory().addItem(grenade2);
				break;
				
				// 14 Killstreak gives Strength 2 and 3 Wither Scrolls
				case 14: killer.sendMessage(ChatColor.GOLD + "-[]-[] 14 Killstreak []-[]-");
				if(killer.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))
				{
					killer.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
				}
				killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 1));
				ItemStack scroll = new ItemStack(Material.PAPER, 3);
				ItemMeta scrollMeta = scroll.getItemMeta();
				scrollMeta.setDisplayName(ChatColor.GOLD + "Wither Scroll");
				scroll.setItemMeta(scrollMeta);
				killer.getInventory().addItem(scroll);
				break;
				
				// 16 Killstreak gives Nether Fragment and 2 Hand Grenades
				case 16: killer.sendMessage(ChatColor.GOLD + "-[]-[] 16 Killstreak []-[]-");
				ItemStack nether2 = new ItemStack(Material.NETHER_STAR);
				ItemMeta nether2Meta = nether2.getItemMeta();
				nether2Meta.setDisplayName(ChatColor.GOLD + "Nether Fragment");
				nether2.setItemMeta(nether2Meta);
				killer.getInventory().addItem(nether2);
				ItemStack grenade3 = new ItemStack(Material.EGG, 2);
				ItemMeta grenadeMeta3 = grenade3.getItemMeta();
				grenadeMeta3.setDisplayName(ChatColor.GOLD + "Hand Grenade");
				grenade3.setItemMeta(grenadeMeta3);
				killer.getInventory().addItem(grenade3);
				break;
				
				// 18 Killstreak gives Lightning Orb and 2 IEDs
				case 18: killer.sendMessage(ChatColor.GOLD + "-[]-[] 18 Killstreak []-[]-");
				ItemStack orb3 = new ItemStack(Material.SNOW_BALL);
				ItemMeta orbMeta3 = orb3.getItemMeta();
				orbMeta3.setDisplayName(ChatColor.GOLD + "Lightning Orb");
				orb3.setItemMeta(orbMeta3);
				killer.getInventory().addItem(orb3);
				ItemStack ied = new ItemStack(Material.STRING, 2);
				ItemMeta iedMeta = ied.getItemMeta();
				iedMeta.setDisplayName(ChatColor.GOLD + "IED");
				ied.setItemMeta(iedMeta);
				killer.getInventory().addItem(ied);
				break;
				
				// 20 Killstreak gives 2 Hand Grenades and a Blaze Orb
				case 20: killer.sendMessage(ChatColor.GOLD + "-[]-[] 20 Killstreak []-[]-");
				ItemStack grenade4 = new ItemStack(Material.EGG, 2);
				ItemMeta grenadeMeta4 = grenade4.getItemMeta();
				grenadeMeta4.setDisplayName(ChatColor.GOLD + "Hand Grenade");
				grenade4.setItemMeta(grenadeMeta4);
				killer.getInventory().addItem(grenade4);
				ItemStack blazeOrb2 = new ItemStack(Material.MAGMA_CREAM);
				ItemMeta blazeOrbMeta2 = blazeOrb2.getItemMeta();
				blazeOrbMeta2.setDisplayName(ChatColor.GOLD + "Blaze Orb");
				blazeOrb2.setItemMeta(blazeOrbMeta2);
				killer.getInventory().addItem(blazeOrb2);
				break;
			}
		}

		// Update Hero Bar
		updateHeroBar(killer, 1);
		
		// Give 2 points to random players on this person's team
		Random rgen = new Random();
		List<Player> deadTeam = new ArrayList<Player>();
		for(OfflinePlayer offP : getPlayerTeam(player).getPlayers())
		{
			deadTeam.add(offP.getPlayer());
		}
		for(int i = 0; i < 2; i++)
		{
			updateHeroBar(deadTeam.get(rgen.nextInt(deadTeam.size())), 1);
		}
		
		// Give kill and death messages
		if(!playerInfo.isHero)
		{
			killer.sendMessage(ChatColor.GOLD + "You killed " + playerColor + player.getName() + "!");
		}
		else
		{
			Bukkit.broadcastMessage(killerColor + killer.getName() + ChatColor.GOLD + " has killed the Hero " + playerColor + player.getName() + "!");
		}
		giveXP(killer, "Kill");
		player.sendMessage(ChatColor.DARK_RED + "You were killed by " + killerColor + killer.getName() + "!");
		
		// Trigger kill based passives
		if(killerInfo.isHero)
		{
			if(killerInfo.currentHero.equals("Adrestia"))
			{
				killer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 2));
				killer.sendMessage(ChatColor.AQUA + "Killing an enemy has given you absorption!");
			}
			
			if(killerInfo.currentHero.equals("Apollo"))
			{
				if(Math.random() < .5)
				{
					double distance = 9999999;
					Player closest = null;
					for(OfflinePlayer teamPlay : getPlayerTeam(killer).getPlayers())
					{
						Player teammate = teamPlay.getPlayer();
						if(teammate.equals(killer))
						{
							continue;
						}
						if(teammate.getLocation().distance(killer.getLocation()) < distance)
						{
							distance = teammate.getLocation().distance(killer.getLocation());
							closest = teammate;
						}
					}
					if(closest != null)
					{
						// Give random killstreak item
						int itemNum = (int)(Math.random() * 6);
						switch(itemNum)
						{
							// Grenade
							case 0: ItemStack grenade = new ItemStack(Material.EGG);
								ItemMeta grenadeMeta = grenade.getItemMeta();
								grenadeMeta.setDisplayName(ChatColor.GOLD + "Hand Grenade");
								grenade.setItemMeta(grenadeMeta);
								closest.getInventory().addItem(grenade);
								break;
							case 1: ItemStack orb = new ItemStack(Material.SNOW_BALL);
								ItemMeta orbMeta = orb.getItemMeta();
								orbMeta.setDisplayName(ChatColor.GOLD + "Lightning Orb");
								orb.setItemMeta(orbMeta);
								closest.getInventory().addItem(orb);
								break;
							case 2: ItemStack scroll = new ItemStack(Material.PAPER);
								ItemMeta scrollMeta = scroll.getItemMeta();
								scrollMeta.setDisplayName(ChatColor.GOLD + "Wither Scroll");
								scroll.setItemMeta(scrollMeta);
								closest.getInventory().addItem(scroll);
								break;
							case 3:	ItemStack ied = new ItemStack(Material.STRING);
								ItemMeta iedMeta = ied.getItemMeta();
								iedMeta.setDisplayName(ChatColor.GOLD + "IED");
								ied.setItemMeta(iedMeta);
								closest.getInventory().addItem(ied);
								break;
							case 4: ItemStack blazeOrb = new ItemStack(Material.MAGMA_CREAM);
								ItemMeta blazeOrbMeta = blazeOrb.getItemMeta();
								blazeOrbMeta.setDisplayName(ChatColor.GOLD + "Blaze Orb");
								blazeOrb.setItemMeta(blazeOrbMeta);
								closest.getInventory().addItem(blazeOrb);
								break;
							case 5: ItemStack nether = new ItemStack(Material.NETHER_STAR);
								ItemMeta netherMeta = nether.getItemMeta();
								netherMeta.setDisplayName(ChatColor.GOLD + "Nether Fragment");
								nether.setItemMeta(netherMeta);
								closest.getInventory().addItem(nether);
							break;
						}
						closest.sendMessage(ChatColor.GOLD + "You have been granted an item from " + killerColor + killer.getName() + ChatColor.GOLD + "!");
						killer.sendMessage(ChatColor.GOLD + "You have granted " + killerColor + closest.getName() + ChatColor.GOLD + " an item!");
					}
				}
			}
			
			if(killerInfo.currentHero.equals("Fenrir"))
			{
				killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 1));
				killer.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 1));
				killer.sendMessage(ChatColor.RED + "Killing an enemy has increased your mobility!");
			}
			
			
			
			if(killerInfo.currentHero.equals("Noiresse"))
			{
				if(Math.random() <= .5)
				{
					ItemStack blindnessOrb = new ItemStack(Material.ENDER_PEARL);
					ItemMeta blindnessOrbsMeta = blindnessOrb.getItemMeta();
					blindnessOrbsMeta.setDisplayName(ChatColor.GOLD + "Blindness Orb");
					blindnessOrb.setItemMeta(blindnessOrbsMeta);
					killer.getInventory().addItem(blindnessOrb);
					killer.sendMessage(ChatColor.GOLD + "You received a Blindness Orb for killing an enemy!");
				}
			}
			if(killerInfo.currentHero.equals("Zeus"))
			{
				Location deathLoc = player.getLocation();
				for(int i = (int)deathLoc.getX() - 1; i <= (int)deathLoc.getX() + 1; i++)
				{
					for(int j = (int)deathLoc.getZ() - 1; j <= (int)deathLoc.getZ() + 1; j++)
					{
						deathLoc.getWorld().strikeLightningEffect(new Location(deathLoc.getWorld(), i, deathLoc.getY(), j));
					}
				}
				for(Player hitPlayer : Bukkit.getOnlinePlayers())
				{
					if(getPlayerTeam(player).getName().equals(getPlayerTeam(hitPlayer).getName()) || (player.equals(hitPlayer)))
					{
						continue;
					}
					if(getPlayerInfo(hitPlayer.getName()).isHero && getPlayerInfo(hitPlayer.getName()).currentHero.equals("Zeus"))
					{
						continue;
					}
					if(player.getLocation().distance(hitPlayer.getLocation()) <= 3)
					{
						if(getPlayerInfo(hitPlayer.getName()).isHero == false)
						{
							if(hitPlayer.getHealth() - 4 <= 0)
							{
								playerDie(hitPlayer, player);
							}
							else
							{
								hitPlayer.damage(4);
							}
						}
						else
						{
							if(getPlayerInfo(hitPlayer.getName()).heroHealth - 4 <= 0)
							{
								if(getPlayerInfo(hitPlayer.getName()).heroHealth > 0)
								{
									hitPlayer.damage(4 - getPlayerInfo(hitPlayer.getName()).heroHealth);
									getPlayerInfo(hitPlayer.getName()).heroHealth = 0;
								}
								if(hitPlayer.getHealth() - 4 <= 0)
								{
									playerDie(hitPlayer, player);
								}
								else
								{
									hitPlayer.damage(4);
								}
							}
							else
							{
								getPlayerInfo(hitPlayer.getName()).heroHealth -= 4;
							}
						}
					}
				}
			}
			
			if(killerInfo.currentHero.equals("Terminus"))
			{
				killer.sendMessage(ChatColor.GOLD + "You and your nearby teammates have received Resistance because of your kill!");
				for(OfflinePlayer teamplay : getPlayerTeam(killer).getPlayers())
				{
					if(teamplay.getPlayer().getLocation().distance(killer.getLocation()) <= 6)
					{
						teamplay.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 0));
					}
				}
			}
			
			if(killerInfo.currentHero.equals("Kronos"))
			{
				killer.sendMessage(ChatColor.GOLD + "Your speed has been temporarily increased, and nearby enemies have been slowed, because of your kill!");
				killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0));
				for(Player hitPlayer : Bukkit.getOnlinePlayers())
				{
					if(getPlayerTeam(player).getName().equals(getPlayerTeam(hitPlayer).getName()) || (player.equals(hitPlayer)))
					{
						continue;
					}
					if(getPlayerInfo(hitPlayer.getName()).isHero && getPlayerInfo(hitPlayer.getName()).currentHero.equals("Zeus"))
					{
						continue;
					}
					if(player.getLocation().distance(hitPlayer.getLocation()) <= 5)
					{
						hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 0));
						hitPlayer.sendMessage(ChatColor.RED + "You have been slowed by time magic!");
					}
				}
			}
			
			if(killerInfo.currentHero.equals("Hephaestus"))
			{
				for(int i = -1; i <= 1; i++)
				{
					for(int j = -1; j <= 1; j++)
					{
						Location fireLoc = new Location(player.getWorld(), player.getLocation().getX() + i, player.getLocation().getY() - 1, player.getLocation().getZ() + j);
						Material original = player.getWorld().getBlockAt(fireLoc).getType();
						if(original == Material.AIR)
						{
							continue;
						}
						byte originalData = player.getWorld().getBlockAt(fireLoc).getData();
						Material originalTop = player.getWorld().getBlockAt(fireLoc).getRelative(BlockFace.UP).getType();
						byte originalTopData = player.getWorld().getBlockAt(fireLoc).getRelative(BlockFace.UP).getData();
						
						scheduler.scheduleSyncDelayedTask(this, new Runnable()
						{
							@Override
							public void run() {
								player.getWorld().getBlockAt(fireLoc).setType(Material.NETHERRACK);
								player.getWorld().getBlockAt(fireLoc).getRelative(BlockFace.UP).setType(Material.FIRE);
							}
						}, 2L);

						scheduler.scheduleSyncDelayedTask(this, new Runnable()
						{
							@Override
							public void run() {
								player.getWorld().getBlockAt(fireLoc).setType(original);
								player.getWorld().getBlockAt(fireLoc).setData(originalData);
								player.getWorld().getBlockAt(fireLoc).getRelative(BlockFace.UP).setType(originalTop);
								player.getWorld().getBlockAt(fireLoc).getRelative(BlockFace.UP).setData(originalTopData);
							}			
						}, 160L);
					}
				}
			}
		}
		
		// Trigger death based passives
		if(playerInfo.isHero)
		{
			if(playerInfo.currentHero.equals("Cecilia"))
			{
				killer.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 140, 1));
				killer.sendMessage(ChatColor.RED + "You have been poisoned for killing " + playerColor + player.getName() + ChatColor.RED + "!");
			}
		}
		
		// Give player items
		if(playerInfo.heroRespawn == false)
		{
			player.getInventory().setHelmet(teamManager.getIdentifier(player));
		}
		ItemStack classSelector = new ItemStack(Material.NAME_TAG);
		ItemMeta classMeta = classSelector.getItemMeta();
		classMeta.setDisplayName(ChatColor.GOLD + "Class Selector");
		classSelector.setItemMeta(classMeta);
		player.getInventory().setItem(8, classSelector);
		
		// Equip class
		if(playerInfo.changeClass != "")
		{
			playerInfo.currentClass = playerInfo.changeClass;
			playerInfo.changeClass = "";
		}
		
		if(playerInfo.heroRespawn == false)
		{
			for(int i = 0; i < classes.size(); i++)
			{
				if(ChatColor.stripColor(getPlayerInfo(player.getName()).currentClass).equals(ChatColor.stripColor(classes.get(i).className)))
				{
					ControlPointClass eClass = classes.get(i);
					equipClass(player, eClass);
					break;
				}
			}
		}
		
		// Hero Death?
		if(playerInfo.isHero)
		{
			playerInfo.isHero = false;
			switch(ChatColor.stripColor(getPlayerTeam(player).getName()))
			{
				case "Red Team": redHeroActive.removeAll();
				redHero = 0;
				redHeroBar.setProgress(0);
				for(OfflinePlayer teamPlayer : getPlayerTeam(player).getPlayers())
				{
					redHeroBar.addPlayer(teamPlayer.getPlayer());
				}
				break;
				case "Blue Team": blueHeroActive.removeAll();
				blueHero = 0;
				blueHeroBar.setProgress(0);
				for(OfflinePlayer teamPlayer : getPlayerTeam(player).getPlayers())
				{
					blueHeroBar.addPlayer(teamPlayer.getPlayer());
				}
				break;
				case "Green Team": greenHeroActive.removeAll();
				greenHero = 0;
				greenHeroBar.setProgress(0);
				for(OfflinePlayer teamPlayer : getPlayerTeam(player).getPlayers())
				{
					greenHeroBar.addPlayer(teamPlayer.getPlayer());
				}
				break;
				case "Purple Team": purpleHeroActive.removeAll();
				purpleHero = 0;
				purpleHeroBar.setProgress(0);
				for(OfflinePlayer teamPlayer : getPlayerTeam(player).getPlayers())
				{
					purpleHeroBar.addPlayer(teamPlayer.getPlayer());
				}
				break;
			}
		}
		
		// Teleport dead player to their spawn
		switch(ChatColor.stripColor(getPlayerTeam(player).getName()))
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
		
		// Trigger Perk
		if(playerInfo.currentPerk.equals("Powersurge") && playerInfo.heroRespawn == false)
		{
			triggerPowersurge(player);
		}
		
		if(playerInfo.currentPerk.equals("Ironskin") && playerInfo.heroRespawn == false)
		{
			triggerIronskin(player);
		}
		
		if(killerInfo.currentPerk.equals("Resourceful") && killerInfo.isHero == false)
		{
			triggerResourceful(killer);
		}
		
		// The player is respawning as a hero
		if(playerInfo.heroRespawn)
		{
			playerInfo.isHero = true;
			playerInfo.heroRespawn = false;
			
			// Choose the hero of the player
			int heroNum = (int)(Math.random() * 3);
			String heroStr = "";
			HeroPack pack = null;
			for(HeroPack hPack : heroPacks)
			{
				if(hPack.packName.equals(playerInfo.currentPack))
				{
					pack = hPack;
					break;
				}
			}
			switch(heroNum)
			{
				case 0: heroStr = pack.hero1.heroName;
				break;
				
				case 1: heroStr = pack.hero2.heroName;
				break;
				
				case 2: heroStr = pack.hero3.heroName;
				break;
			}
			
			playerInfo.currentHero = heroStr;
			for(int i = 0; i < heroes.size(); i++)
			{
				if(ChatColor.stripColor(playerInfo.currentHero).equals(ChatColor.stripColor(heroes.get(i).heroName)))
				{
					ControlPointHero eHero = heroes.get(i);
					equipHero(player, eHero);
					player.sendMessage("");
					player.sendMessage(ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-");
					player.sendMessage(ChatColor.GOLD + heroes.get(i).heroName);
					player.sendMessage(ChatColor.AQUA + "Passive Ability: " + heroes.get(i).passiveAbility);
					player.sendMessage(ChatColor.LIGHT_PURPLE + "Active Ability: " + heroes.get(i).activeAbility);
					player.sendMessage(ChatColor.GOLD + "-[]-[]-[]-[]-[]-[]-[]-");
					player.sendMessage("");
					break;
				}
			}
			
			switch(ChatColor.stripColor(getPlayerTeam(player).getName()))
			{
				case "Red Team": redHeroBar.removeAll();
				redHero = 0;
				redHeroActive.setTitle(ChatColor.RED + player.getName() + "'s Active Ability Charge");
				redHeroActive.setProgress(0);
				for(OfflinePlayer teamPlayer : getPlayerTeam(player).getPlayers())
				{
					redHeroActive.addPlayer(teamPlayer.getPlayer());
				}
				coneParticles(player, 255, 0, 0); // Particle effect
				break;
				
				case "Blue Team": blueHeroBar.removeAll();
				blueHero = 0;
				blueHeroActive.setTitle(ChatColor.BLUE + player.getName() + "'s Active Ability Charge");
				blueHeroActive.setProgress(0);
				for(OfflinePlayer teamPlayer : getPlayerTeam(player).getPlayers())
				{
					blueHeroActive.addPlayer(teamPlayer.getPlayer());
				}
				coneParticles(player, 0, 0, 255); // Particle effect
				break;
				
				case "Green Team": greenHeroBar.removeAll();
				greenHero = 0;
				greenHeroActive.setTitle(ChatColor.GREEN + player.getName() + "'s Active Ability Charge");
				greenHeroActive.setProgress(0);
				for(OfflinePlayer teamPlayer : getPlayerTeam(player).getPlayers())
				{
					greenHeroActive.addPlayer(teamPlayer.getPlayer());
				}
				coneParticles(player, 2, 75, 7); // Particle effect
				break;
				
				case "Purple Team": purpleHeroBar.removeAll();
				purpleHero = 0;
				purpleHeroActive.setTitle(ChatColor.DARK_PURPLE + player.getName() + "'s Active Ability Charge");
				purpleHeroActive.setProgress(0);
				for(OfflinePlayer teamPlayer : getPlayerTeam(player).getPlayers())
				{
					purpleHeroActive.addPlayer(teamPlayer.getPlayer());
				}
				coneParticles(player, 89, 23, 117); // Particle effect
				break;
			}
			
			Bukkit.broadcastMessage(playerColor + player.getName() + ChatColor.GOLD + " has respawned as " + ChatColor.AQUA + playerInfo.currentHero + ChatColor.GOLD + "!");
			
			// Timed Hero Items
			if(heroStr.equals("Lena"))
			{
				BukkitRunnable runnable = new BukkitRunnable()
				{
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(playerInfo.isHero && playerInfo.currentHero.equals("Lena") && gameRunning)
						{
							ItemStack blazeOrb = new ItemStack(Material.MAGMA_CREAM);
							ItemMeta blazeOrbMeta = blazeOrb.getItemMeta();
							blazeOrbMeta.setDisplayName(ChatColor.GOLD + "Blaze Orb");
							blazeOrb.setItemMeta(blazeOrbMeta);
							player.getInventory().addItem(blazeOrb);
						}
						else
						{
							this.cancel();
						}
					}
				};
				runnable.runTaskTimer(this, 300L, 300L);
			}
			
			if(heroStr.equals("Apollo"))
			{
				BukkitRunnable runnable = new BukkitRunnable()
				{
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(playerInfo.isHero && playerInfo.currentHero.equals("Apollo") && gameRunning)
						{
							ItemStack arrow = new ItemStack(Material.ARROW);
							player.getInventory().addItem(arrow);
						}
						else
						{
							this.cancel();
						}
					}
				};
				runnable.runTaskTimer(this, 240L, 240L);
			}
		}
	}
	
	// Trigger the Powersurge perk
	public void triggerPowersurge(Player p)
	{
		PlayerInfo info = getPlayerInfo(p.getName());
		double level = (7 + (info.powersurge - 1) * 2) / 100.0;
		if(Math.random() <= level)
		{
			p.sendMessage(ChatColor.GOLD + "[Powersurge] You feel power flowing through your veins!");
			p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1200, 1));
		}
	}
	
	// Trigger the Ironskin perk
	public void triggerIronskin(Player p)
	{
		PlayerInfo info = getPlayerInfo(p.getName());
		double level = (7 + (info.ironskin - 1) * 2) / 100.0;
		if(Math.random() <= level)
		{
			p.sendMessage(ChatColor.GOLD + "[Ironskin] Your skin feels as thick and durable as iron!");
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1200, 0));
			p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 1200, 3));
		}
	}
	
	// Trigger the Explosive perk
	public boolean triggerExplosive(Player p)
	{
		PlayerInfo info = getPlayerInfo(p.getName());
		double level = (15 + (info.explosive - 1) * 2) / 100.0;
		if(Math.random() <= level)
		{
			p.sendMessage(ChatColor.GOLD + "[Explosive] You exploded after dying!");
			return true;
		}
		return false;
	}
	
	// Trigger the Immortal perk
	public boolean triggerImmortal(Player p)
	{
		PlayerInfo info = getPlayerInfo(p.getName());
		double level = (7 + (info.immortal - 1) * 2) / 100.0;
		if(Math.random() <= level)
		{
			p.sendMessage(ChatColor.GOLD + "[Immortal] You defied death and regained all health!");
			return true;
		}
		return false;
	}
	
	// Trigger the Resourceful perk
	public void triggerResourceful(Player p)
	{
		PlayerInfo info = getPlayerInfo(p.getName());
		double level = (10 + (info.resourceful - 1) * 2.22) / 100.0;
		
		if(info.resourceful == 10)
		{
			level = 0.30;
		}
		
		if(Math.random() <= level)
		{
			// Give random killstreak item
			int itemNum = (int)(Math.random() * 6);
			switch(itemNum)
			{
				// Grenade
				case 0: ItemStack grenade = new ItemStack(Material.EGG);
					ItemMeta grenadeMeta = grenade.getItemMeta();
					grenadeMeta.setDisplayName(ChatColor.GOLD + "Hand Grenade");
					grenade.setItemMeta(grenadeMeta);
					p.getInventory().addItem(grenade);
					p.sendMessage(ChatColor.GOLD + "[Resourceful] You manage to find a Hand Grenade!");
					break;
				case 1: ItemStack orb = new ItemStack(Material.SNOW_BALL);
					ItemMeta orbMeta = orb.getItemMeta();
					orbMeta.setDisplayName(ChatColor.GOLD + "Lightning Orb");
					orb.setItemMeta(orbMeta);
					p.getInventory().addItem(orb);
					p.sendMessage(ChatColor.GOLD + "[Resourceful] You manage to find a Lightning Orb!");
					break;
				case 2: ItemStack scroll = new ItemStack(Material.PAPER);
					ItemMeta scrollMeta = scroll.getItemMeta();
					scrollMeta.setDisplayName(ChatColor.GOLD + "Wither Scroll");
					scroll.setItemMeta(scrollMeta);
					p.getInventory().addItem(scroll);
					p.sendMessage(ChatColor.GOLD + "[Resourceful] You manage to find a Wither Scroll!");
					break;
				case 3:	ItemStack ied = new ItemStack(Material.STRING);
					ItemMeta iedMeta = ied.getItemMeta();
					iedMeta.setDisplayName(ChatColor.GOLD + "IED");
					ied.setItemMeta(iedMeta);
					p.getInventory().addItem(ied);
					p.sendMessage(ChatColor.GOLD + "[Resourceful] You manage to find an IED!");
					break;
				case 4: ItemStack blazeOrb = new ItemStack(Material.MAGMA_CREAM);
					ItemMeta blazeOrbMeta = blazeOrb.getItemMeta();
					blazeOrbMeta.setDisplayName(ChatColor.GOLD + "Blaze Orb");
					blazeOrb.setItemMeta(blazeOrbMeta);
					p.getInventory().addItem(blazeOrb);
					p.sendMessage(ChatColor.GOLD + "[Resourceful] You manage to find a Blaze Orb!");
					break;
				case 5: ItemStack nether = new ItemStack(Material.NETHER_STAR);
					ItemMeta netherMeta = nether.getItemMeta();
					netherMeta.setDisplayName(ChatColor.GOLD + "Nether Fragment");
					nether.setItemMeta(netherMeta);
					p.getInventory().addItem(nether);
					p.sendMessage(ChatColor.GOLD + "[Resourceful] You manage to find a Nether Fragment!");
				break;
			}
		}
	}
	
	// Trigger the Godslayer Perk
	public boolean triggerGodslayer(Player p)
	{
		PlayerInfo info = getPlayerInfo(p.getName());
		double level = (8 + (info.godslayer - 1) * 3) / 100.0;
		if(Math.random() <= level)
		{
			p.sendMessage(ChatColor.GOLD + "[Godslayer] You dealt double damage to a Hero!");
			return true;
		}
		return false;
	}
	
	private Connection connection;
	
	@Override
	public void onEnable()
	{
		instance = this;
		establishConnection();
	    getServer().getPluginManager().registerEvents(new ControlPointListener(this), this);
	    getServer().getPluginManager().registerEvents(this, this);
	    getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	    
	    // Register commands
	    getCommand("class").setExecutor(new ClassSelect(this));
	    getCommand("perk").setExecutor(new Perk(this));
	    getCommand("team").setExecutor(new TeamSelect(this));
	    getCommand("stats").setExecutor(new Stats(this));
	    getCommand("cpstart").setExecutor(new Start(this));
	    getCommand("cpend").setExecutor(new End(this));
	    
	    // Set the classes, heroes, map, and scoreboard
	    setClasses();
	    setHeroes();
	    teamManager.setScoreboard();
	    
	    mapNames.add("Nebula");
	    mapNames.add("Block City");
	    mapNames.add("Tetra");
	    mapNames.add("Nativus");
	    
		for(int i = 0; i < mapNames.size(); i++)
		{
			mapVotes.add(0);
		}
	    
	    // Get the lobby world
	    lobby = Bukkit.getServer().createWorld(new WorldCreator("MinigameLobby"));
	    
	    scheduler = getServer().getScheduler();
	    
	    scheduler.scheduleSyncRepeatingTask(this, new Runnable(){
	    	@Override
	    	public void run()
	    	{
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
	    		
	    		// If the game is starting, we update the lobby bar and countdown timer
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
	    			
	    			if(startTimer > 5)
	    			{
		    			countVotes();
	    			}
	    			// Map voting finished
	    			if(startTimer == 5)
	    			{
	    				tallyVotes();
	    			    setMap();
	    			}
	    			
	    			// Countdown is done, start the game
	    			if(startTimer == 0)
	    			{
	    				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "cpstart");
	    				startBar.removeAll();
	    				gameStarting = false;
	    				gameRunning = true;
	    			}
	    		}
	    		
	    		// If the game is running, we update these every second
	    		if(gameRunning)
	    		{
	    			if(pointTimer != 0)
	    			{
	    				pointTimer--;
	    			}
	    			
	    			// Decrease Spawn Protection Timers and Show Hero Armor
	    			for(Player player: Bukkit.getOnlinePlayers())
	    			{
	    				PlayerInfo info = getPlayerInfo(player.getName());
	    				if(info.protectTimer != 0)
	    				{
	    					info.protectTimer --;
	    				}
	    				if(info.isHero)
	    				{
	    					ActionbarTitleObject healthTitle = new ActionbarTitleObject(ChatColor.GREEN + "" + ChatColor.BOLD + "Hero Armor: " + (int)info.heroHealth + "/" + info.heroMaxHealth);
	    					healthTitle.send(player);
	    				}
	    				if(info.nullified == false)
	    				{
		    				giveClassEffects(player);
	    				}
	    			}
	    			
	    			// Create and update the countdown bar for the point
	    			if(pointTimer <= 5 && pointTimer != 0)
	    			{
	    				for(Player player : Bukkit.getOnlinePlayers())
	    				{
	    					String pointStr = "";
	    					if(pointTimer > 3)
	    					{
	    						pointStr = ChatColor.GOLD + "" + pointTimer;
	    					}
	    					else if(pointTimer == 1)
	    					{
	    						pointStr = ChatColor.DARK_RED + "" + ChatColor.BOLD + "" + pointTimer;
	    					}
	    					else
	    					{
	    						pointStr = ChatColor.RED + "" + pointTimer;
	    					}
	    					if(pointBar == null)
	    					{
			    				pointBar = Bukkit.createBossBar(pointStr, BarColor.WHITE, BarStyle.SOLID);
	    					}
	    					else
	    					{
	    						pointBar.setTitle(pointStr);
	    					}
		    				pointBar.setProgress(pointTimer / 5.0);
	    					player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
	    					if(!pointBar.getPlayers().contains(player))
	    					{
	    						pointBar.addPlayer(player);
	    					}
	    				}
	    			}
	    			
	    			// The point is active, award points for players on it
	    			if(pointTimer == 0)
	    			{
	    				for(Player p : Bukkit.getOnlinePlayers())
	    				{
	    					p.playSound(p.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 1, 1);
	    				}
	    				// Award points
	    				int redPoints = 0;
	    				int	bluePoints = 0;
	    				int greenPoints = 0;
	    				int purplePoints = 0;
	    				
	    				//Bukkit.broadcastMessage("Center Point: " + center.toString());
	    				//Bukkit.broadcastMessage("Distance Needed For Points: " + centerDistance);
	    				
	    				// Check if each player is on the point
	    				//Bukkit.broadcastMessage("Center Y: " + center.getY());
	    				for(Player player : Bukkit.getOnlinePlayers())
	    				{
	    					Location pLocation = player.getLocation();
	    					//Bukkit.broadcastMessage(player.getName() + "'s Y: " + pLocation.getY());
	    					//Bukkit.broadcastMessage(player.getName() + "'s Y Minus Center Y: " + (pLocation.getY() - center.getY()));

	    					// Center checks
	    					if(pLocation.getY() - center.getY() < 3.0 && pLocation.getY() - center.getY() >= -0.5)
	    					{
	    						Location centLoc = new Location(gameWorld, center.getX(), 0.0, center.getZ());
	    						Location pLoc = pLocation;
	    						pLoc.setY(0.0);
	    						//Bukkit.broadcastMessage(player.getName() + "'s Distance From Center: " + pLoc.distance(centLoc));
		    					if(pLoc.distance(centLoc) <= centerDistance)
		    					{
		    						// Player is on the point, give points to their team
		    						Team playerTeam = getPlayerTeam(player);
		    						switch(ChatColor.stripColor(playerTeam.getName()))
		    						{
		    						case "Red Team": redPoints += 10;
		    						teamManager.redScore += 10;
		    						break;
		    						case "Blue Team": bluePoints += 10;
		    						teamManager.blueScore += 10;
		    						break;
		    						case "Green Team": greenPoints += 10;
		    						teamManager.greenScore += 10;
		    						break;
		    						case "Purple Team": purplePoints += 10;
		    						teamManager.purpleScore += 10;
		    						break;
		    						}
		    						
		    						// Give the player xp
		    						giveXP(player, "Point");
		    						giveCoins(player, "Capture");
		    						getPlayerInfo(player.getName()).captures++;
		    						getPlayerInfo(player.getName()).totalCaptures++;
		    						
		    						// Update Hero Bar
		    						updateHeroBar(player, 3);
		    						
		    						// Trigger Resourceful
		    						if(getPlayerInfo(player.getName()).currentPerk.equals("Resourceful") && getPlayerInfo(player.getName()).isHero == false)
		    						{
		    							triggerResourceful(player);
		    						}
		    					}
	    					}
	    				}
	    				
	    				// Send Message to all players
	    				Bukkit.broadcastMessage("");
	    				Bukkit.broadcastMessage("");
	    				Bukkit.broadcastMessage(ChatColor.GOLD + "-[]-[]-[] Awarding Points []-[]-[]-");
	    				if(redPoints > 0)
	    				{
	    					Bukkit.broadcastMessage(ChatColor.RED + "Red Team" + ChatColor.AQUA + " has earned " + redPoints + " points!");
	    				}
	    				if(bluePoints > 0)
	    				{
	    					Bukkit.broadcastMessage(ChatColor.BLUE + "Blue Team" + ChatColor.AQUA + " has earned " + bluePoints + " points!");
	    				}
	    				if(greenPoints > 0)
	    				{
	    					Bukkit.broadcastMessage(ChatColor.GREEN + "Green Team" + ChatColor.AQUA + " has earned " + greenPoints + " points!");
	    				}
	    				if(purplePoints > 0)
	    				{
	    					Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "Purple Team" + ChatColor.AQUA + " has earned " + purplePoints + " points!");
	    				}
	    				Bukkit.broadcastMessage("");
	    				Bukkit.broadcastMessage("");
	    				
	    				// Remove the countdown bar
	    				pointBar.removeAll();
	    				pointBar = null;
	    				
	    				// Reset point timer
	    				pointTimer = 35 + (int)Math.floor((Math.random() * 10)) - 5;
	    				
	    				// Check if a team has won
	    				int teamsWinning = 0;
	    				int winPoints = 0;
	    				winningTeam = null;
	    				if(teamManager.redScore >= teamManager.winScore)
	    				{
	    					teamsWinning++;
	    					winningTeam = teamManager.redTeam;
	    					winPoints = teamManager.redScore;
	    				}
	    				if(teamManager.blueScore >= teamManager.winScore)
	    				{
	    					teamsWinning++;
	    					winningTeam = teamManager.blueTeam;
	    					winPoints = teamManager.blueScore;
	    				}
	    				if(teamManager.greenScore >= teamManager.winScore)
	    				{
	    					teamsWinning++;
	    					winningTeam = teamManager.greenTeam;
	    					winPoints = teamManager.greenScore;
	    				}
	    				if(teamManager.purpleScore >= teamManager.winScore)
	    				{
	    					teamsWinning++;
	    					winningTeam = teamManager.purpleTeam;
	    					winPoints = teamManager.purpleScore;
	    				}
	    				
	    				// One or more teams have enough points to win
	    				if(teamsWinning != 0)
	    				{
	    					// End the game, one team has won
	    					if(teamsWinning == 1)
	    					{
	    	    				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "cpend");
	    					}
	    					
	    					// Multiple teams are tied, do a tiebreaker until a team wins
	    					if(teamsWinning > 1)
	    					{
	    						Bukkit.broadcastMessage(ChatColor.GOLD + "There is more than one team with enough points to win! The game will continue until one team is ahead!");
	    						teamManager.winScore = winPoints + 10;
	    					}
	    				}
	    				
	    				// Update the scoreboard with the new points
	    				teamManager.updateScoreboard();
	    			}
	    		}
	    	}
	    }, 0L, 20L);
	    
	    // Archer arrow homing
	    scheduler.scheduleSyncRepeatingTask(this, new Runnable(){

			@Override
			public void run() {
				for(Arrow arrow : archerArrows)
				{
					for(Player player : Bukkit.getOnlinePlayers())
					{
						if(!player.equals(arrow.getShooter()))
						{
							if(arrow.getLocation().distance(player.getLocation()) <= 3.5)
							{
								double magnitude = Math.sqrt(arrow.getVelocity().dot(arrow.getVelocity()));
								arrow.setVelocity((player.getLocation().toVector().subtract(arrow.getLocation().toVector())).normalize().multiply(magnitude));
							}
						}
					}
				}
			}	
	    }, 0L, 1L);
	    
	    // Artoria Divine Light Effect
		new BukkitRunnable()
        {
            public void run()
            {
            	for(Arrow a : artoriaArrows)
            	{            		
            		Player player = (Player)a.getShooter();
            		
            		for(Player hitPlayer : Bukkit.getOnlinePlayers())
					{
						if(getPlayerTeam(player).getName().equals(getPlayerTeam(hitPlayer).getName()) || (player.equals(hitPlayer)))
						{
							continue;
						}
						if(a.getLocation().distance(hitPlayer.getLocation()) <= 4.8)
						{
							if(getPlayerInfo(hitPlayer.getName()).isHero == false)
							{
								if(hitPlayer.getHealth() - 9 <= 0)
								{
									playerDie(hitPlayer, player);
								}
								else
								{
									hitPlayer.damage(9);
									hitPlayer.setFireTicks(100);
								}
							}
							else
							{
								if(getPlayerInfo(hitPlayer.getName()).heroHealth - 9 <= 0)
								{
									if(getPlayerInfo(hitPlayer.getName()).heroHealth > 0)
									{
										hitPlayer.damage(9 - getPlayerInfo(hitPlayer.getName()).heroHealth);
										getPlayerInfo(hitPlayer.getName()).heroHealth = 0;
									}
									if(hitPlayer.getHealth() - 9 <= 0)
									{
										playerDie(hitPlayer, player);
									}
									else
									{
										hitPlayer.damage(9);
										hitPlayer.setFireTicks(100);
									}
								}
								else
								{
									getPlayerInfo(hitPlayer.getName()).heroHealth -= 9;
									hitPlayer.setFireTicks(100);
								}
							}
						}
					}
            		
            		for(int i = -5; i <= 5; i++)
            		{
            			for(int j = 0; j < 10; j++)
            			{
            				double xChange = Math.random() -.5;
            				double zChange = Math.random() -.5;
                			Location effectLoc = new Location(a.getWorld(), a.getLocation().getX() + xChange, a.getLocation().getY() + i, a.getLocation().getZ() + zChange);
                			a.getWorld().spigot().playEffect(effectLoc, Effect.FIREWORKS_SPARK);
            			}
            		}
            		
            		for(int i = -1; i <= 1; i++)
            		{
            			Firework fw = (Firework)a.getWorld().spawnEntity(new Location(a.getWorld(), a.getLocation().getX(), a.getLocation().getY() + i, a.getLocation().getZ()), EntityType.FIREWORK);
            			FireworkMeta fwMeta = fw.getFireworkMeta();
            			fwMeta.addEffect(FireworkEffect.builder().trail(false).flicker(false).withColor(Color.fromRGB(255, 252, 130)).withFade(Color.fromRGB(119, 0, 1)).with(FireworkEffect.Type.BALL).build());
            			fw.setFireworkMeta(fwMeta);
            			scheduler.scheduleSyncDelayedTask(instance, new Runnable()
            			{
            				@Override
            				public void run() {
            					fw.playEffect(EntityEffect.FIREWORK_EXPLODE);
            					fw.remove();
            				}
            			}, 1L);
            		}
            	}
            }
            
            
        }.runTaskTimer(this, 0, 2);
	}
	
	// Method to start the game's countdown
	public void startCountdown()
	{
		if(lobbyBar != null)
		{
			lobbyBar.removeAll();
		}
		gameStarting = true;
	}
	
	// Method to get a player's team
	public Team getPlayerTeam(Player p)
	{
		if(teamManager.redTeam.hasPlayer(p))
		{
			return(teamManager.redTeam);
		}
		if(teamManager.blueTeam.hasPlayer(p))
		{
			return(teamManager.blueTeam);
		}
		if(teamManager.greenTeam.hasPlayer(p))
		{
			return(teamManager.greenTeam);
		}
		if(teamManager.purpleTeam.hasPlayer(p))
		{
			return(teamManager.purpleTeam);
		}
		return null;
	}
	
	// Method for equipping a class
	public void equipClass(Player p, ControlPointClass eClass)
	{
		// Remove all potion effects
		for(PotionEffect pE : p.getActivePotionEffects())
		{
			p.removePotionEffect(pE.getType());
		}
		
		// Equip class items and armor
		for(int j = 0; j < eClass.classItems.size(); j++)
		{
			p.getInventory().addItem(eClass.classItems.get(j));
		}
		for(int j = 0; j < eClass.classArmor.size(); j++)
		{
			if(eClass.classArmor.get(j).getType() == Material.SHIELD)
			{
				p.getInventory().setItemInOffHand(eClass.classArmor.get(j));
			}
			if(eClass.classArmor.get(j).getType() == Material.LEATHER_CHESTPLATE || eClass.classArmor.get(j).getType() == Material.GOLD_CHESTPLATE || eClass.classArmor.get(j).getType() == Material.CHAINMAIL_CHESTPLATE || eClass.classArmor.get(j).getType() == Material.IRON_CHESTPLATE|| eClass.classArmor.get(j).getType() == Material.DIAMOND_CHESTPLATE || eClass.classArmor.get(j).getType() == Material.ELYTRA)
			{
				p.getInventory().setChestplate(eClass.classArmor.get(j));
			}
			if(eClass.classArmor.get(j).getType() == Material.LEATHER_LEGGINGS || eClass.classArmor.get(j).getType() == Material.GOLD_LEGGINGS || eClass.classArmor.get(j).getType() == Material.CHAINMAIL_LEGGINGS || eClass.classArmor.get(j).getType() == Material.IRON_LEGGINGS || eClass.classArmor.get(j).getType() == Material.DIAMOND_LEGGINGS)
			{
				p.getInventory().setLeggings(eClass.classArmor.get(j));
			}
			if(eClass.classArmor.get(j).getType() == Material.LEATHER_BOOTS || eClass.classArmor.get(j).getType() == Material.GOLD_BOOTS || eClass.classArmor.get(j).getType() == Material.CHAINMAIL_BOOTS || eClass.classArmor.get(j).getType() == Material.IRON_BOOTS || eClass.classArmor.get(j).getType() == Material.DIAMOND_BOOTS)
			{
				p.getInventory().setBoots(eClass.classArmor.get(j));
			}
		}
		for(PotionEffect pE : eClass.classPotions)
		{
			p.addPotionEffect(pE);
		}
	}
	
	// Method for equipping a hero
	public void equipHero(Player p, ControlPointHero eHero)
	{
		// Remove all potion effects
		for(PotionEffect pE : p.getActivePotionEffects())
		{
			p.removePotionEffect(pE.getType());
		}
		
		// Equip hero items and armor
		for(int j = 0; j < eHero.heroItems.size(); j++)
		{
			p.getInventory().addItem(eHero.heroItems.get(j));
		}
		for(int j = 0; j < eHero.heroArmor.size(); j++)
		{
			if(eHero.heroArmor.get(j).getType() == Material.SHIELD)
			{
				p.getInventory().setItemInOffHand(eHero.heroArmor.get(j));
			}
			if(eHero.heroArmor.get(j).getType() == Material.LEATHER_HELMET || eHero.heroArmor.get(j).getType() == Material.GOLD_HELMET || eHero.heroArmor.get(j).getType() == Material.CHAINMAIL_HELMET || eHero.heroArmor.get(j).getType() == Material.IRON_HELMET|| eHero.heroArmor.get(j).getType() == Material.DIAMOND_HELMET)
			{
				p.getInventory().setHelmet(eHero.heroArmor.get(j));
			}
			if(eHero.heroArmor.get(j).getType() == Material.LEATHER_CHESTPLATE || eHero.heroArmor.get(j).getType() == Material.GOLD_CHESTPLATE || eHero.heroArmor.get(j).getType() == Material.CHAINMAIL_CHESTPLATE || eHero.heroArmor.get(j).getType() == Material.IRON_CHESTPLATE|| eHero.heroArmor.get(j).getType() == Material.DIAMOND_CHESTPLATE || eHero.heroArmor.get(j).getType() == Material.ELYTRA)
			{
				p.getInventory().setChestplate(eHero.heroArmor.get(j));
			}
			if(eHero.heroArmor.get(j).getType() == Material.LEATHER_LEGGINGS || eHero.heroArmor.get(j).getType() == Material.GOLD_LEGGINGS || eHero.heroArmor.get(j).getType() == Material.CHAINMAIL_LEGGINGS || eHero.heroArmor.get(j).getType() == Material.IRON_LEGGINGS || eHero.heroArmor.get(j).getType() == Material.DIAMOND_LEGGINGS)
			{
				p.getInventory().setLeggings(eHero.heroArmor.get(j));
			}
			if(eHero.heroArmor.get(j).getType() == Material.LEATHER_BOOTS || eHero.heroArmor.get(j).getType() == Material.GOLD_BOOTS || eHero.heroArmor.get(j).getType() == Material.CHAINMAIL_BOOTS || eHero.heroArmor.get(j).getType() == Material.IRON_BOOTS || eHero.heroArmor.get(j).getType() == Material.DIAMOND_BOOTS)
			{
				p.getInventory().setBoots(eHero.heroArmor.get(j));
			}
		}
		
		// Add hero potion effects
		for(PotionEffect pE : eHero.heroPotions)
		{
			p.addPotionEffect(pE);
		}
		
		PlayerInfo info = getPlayerInfo(p.getName());
		info.heroMaxHealth = eHero.heroHealth;
		info.heroHealth = eHero.heroHealth;
	}
	
	// Method to give player class specific potion effects
	public void giveClassEffects(Player player)
	{
		PlayerInfo info = getPlayerInfo(player.getName());
		if(info.isHero)
		{
			// Get Class
			ControlPointHero eHero = null;
			for(int i = 0; i < heroes.size(); i++)
			{
				if(info.currentHero.equals(heroes.get(i).heroName))
				{
					eHero = heroes.get(i);
					break;
				}
			}
			
			// Apply potions
			for(PotionEffect pE : eHero.heroPotions)
			{
				if(!player.hasPotionEffect(pE.getType()))
				{
					player.addPotionEffect(pE);
				}
			}
			
			// Apply Artoria Burning Effects
			if(info.currentHero.equals("Artoria") && info.heroHealth == 0)
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40000, 0));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40000, 0));
				player.setFireTicks(40000);
			}
			
			// Remove Achlys Poison
			if(info.currentHero.equals("Achlys"))
			{
				if(player.hasPotionEffect(PotionEffectType.POISON))
				{
					player.removePotionEffect(PotionEffectType.POISON);
				}
			}
		}
		else
		{
			// Get Class
			ControlPointClass eClass = null;
			for(int i = 0; i < classes.size(); i++)
			{
				if(info.currentClass.equals(classes.get(i).className))
				{
					eClass = classes.get(i);
					break;
				}
			}
			
			// Apply potions
			for(PotionEffect pE : eClass.classPotions)
			{
				if(!player.hasPotionEffect(pE.getType()))
				{
					player.addPotionEffect(pE);
				}
			}
		}
	}
		
	// Method to send player to another server using BungeeCord
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
	
	@Override
	public void onDisable()
	{
		
	}
	
	public static ControlPoint getInstance() {
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
		if(gameRunning || gameEnding)                                                                                                 
		{
			event.setResult(Result.KICK_OTHER);
			event.setKickMessage(ChatColor.RED + "You cannot join a game that is already in progress!");
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		event.setJoinMessage(ChatColor.GOLD + player.getName() + " has joined! (" + Bukkit.getOnlinePlayers().size() + "/24)");
		
		// Give the player items to change team, class, and perk
		player.getInventory().clear();
		ItemStack teamSelector = new ItemStack(Material.WOOL);
		ItemMeta teamMeta = teamSelector.getItemMeta();
		teamMeta.setDisplayName(ChatColor.GOLD + "Team Selector");
		teamSelector.setItemMeta(teamMeta);
		player.getInventory().setItem(0, teamSelector);
		
		ItemStack mapVoting = new ItemStack(Material.BOOK);
		ItemMeta votingMeta = mapVoting.getItemMeta();
		votingMeta.setDisplayName(ChatColor.GOLD + "Map Voting");
		mapVoting.setItemMeta(votingMeta);
		player.getInventory().setItem(8, mapVoting);
		
		ItemStack classSelector = new ItemStack(Material.NAME_TAG);
		ItemMeta classMeta = classSelector.getItemMeta();
		classMeta.setDisplayName(ChatColor.GOLD + "Class Selector");
		classSelector.setItemMeta(classMeta);
		player.getInventory().setItem(3, classSelector);
		
		ItemStack perkSelector = new ItemStack(Material.EMERALD);
		ItemMeta perkMeta = perkSelector.getItemMeta();
		perkMeta.setDisplayName(ChatColor.GOLD + "Perk Selector");
		perkSelector.setItemMeta(perkMeta);
		player.getInventory().setItem(4, perkSelector);
		
		ItemStack heroSelector = new ItemStack(Material.NETHER_STAR);
		ItemMeta heroMeta = heroSelector.getItemMeta();
		heroMeta.setDisplayName(ChatColor.GOLD + "Hero Selector");
		heroSelector.setItemMeta(heroMeta);
		player.getInventory().setItem(5, heroSelector);
		
		// Set health, food, gamemode, and teleport player to lobby
		player.setHealth(20);
		player.setFoodLevel(20);
		player.setGameMode(GameMode.ADVENTURE);
		player.teleport(lobby.getSpawnLocation());
		
		// Remove all potion effects
		for(PotionEffect pE : player.getActivePotionEffects())
		{
			player.removePotionEffect(pE.getType());
		}
		
		// If the game has reached the amount of players it needs to start, start the countdown for the game
		if(Bukkit.getOnlinePlayers().size() == requiredPlayers && !gameStarting && !gameRunning && !gameEnding)
		{
			startCountdown();
		}
		
		// Set level and xp bar
		scheduler.scheduleSyncDelayedTask(this, new Runnable()
		{
				@Override
				public void run() {
					PlayerInfo info = getPlayerInfo(player.getName());
					info.calculateXP();
					player.setLevel(info.level);
					setXPBar(player);
			}
		}, 6L);

		
		/*TitleManagerPlugin title = new TitleManagerPlugin();

		title.giveScoreboard(player);
		title.setScoreboardTitle(player, ChatColor.GOLD + player.getName());
		title.setScoreboardValue(player, 0, "Coins: " + getPlayerInfo(player.getName()).coins);*/
	}
	
	// Push data when the player quits the game
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		PushDataTask pushDataTask = new PushDataTask(player.getUniqueId(), player.getName(), this);
		pushDataTask.runTaskAsynchronously(this);
		event.setQuitMessage(null);
		if(Bukkit.getOnlinePlayers().size() - 1 < requiredPlayers && gameStarting)
		{
			gameStarting = false;
			startTimer = 90;
			startBar.removeAll();
			for(Player lobbyPlayer : Bukkit.getOnlinePlayers())
			{
				lobbyBar.addPlayer(lobbyPlayer);
			}
		}
	}
	
	// Push data when the player is kicked
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event)
	{
		Player player = event.getPlayer();
		PushDataTask pushDataTask = new PushDataTask(player.getUniqueId(), player.getName(), this);
		pushDataTask.runTaskAsynchronously(this);
	}
	
	// Open Server Menu when player right clicks compass
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		// Player isn't right clicking with an item
		if(event.getAction() == Action.PHYSICAL || event.getItem() == null || event.getItem().getType() == Material.AIR)
		{
			return;
		}
		
		if(event.getItem().getType() == Material.WOOL && event.getItem().hasItemMeta())
		{
			if(ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()).equals("Team Selector"))
			{
				openTeamMenu(event.getPlayer());
			}
		}
		
		if(event.getItem().getType() == Material.NAME_TAG && event.getItem().hasItemMeta())
		{
			if(ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()).equals("Class Selector"))
			{
				event.getPlayer().performCommand("class");
			}
		}
		
		if(event.getItem().getType() == Material.EMERALD && event.getItem().hasItemMeta())
		{
			if(ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()).equals("Perk Selector"))
			{
				event.getPlayer().performCommand("perk");
			}
		}
		
		if(event.getItem().getType() == Material.NETHER_STAR && event.getItem().hasItemMeta())
		{
			if(ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()).equals("Hero Selector"))
			{
				openPackMenu(event.getPlayer());
			}
		}
		
		if(event.getItem().getType() == Material.BOOK && event.getItem().hasItemMeta())
		{
			if(ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()).equals("Map Voting"))
			{
				openMapMenu(event.getPlayer());
			}
		}
	}
	
	// Handles the player doing things in their inventory
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		// Stop player from removing team identifier
		if(!ChatColor.stripColor(event.getInventory().getName()).equals("Select a Class!") && !ChatColor.stripColor(event.getInventory().getName()).equals("Map Voting!") && !ChatColor.stripColor(event.getInventory().getName()).equals("Select a Team!") && !ChatColor.stripColor(event.getInventory().getName()).equals("Perks!") && !ChatColor.stripColor(event.getInventory().getName()).equals("Heroes!"))
		{
			// Hero Information Menu
			for(ControlPointHero hero : heroes)
			{
				if(ChatColor.stripColor(event.getInventory().getName()).equals(hero.heroName))
				{
					event.setCancelled(true);
					if(event.getCurrentItem().hasItemMeta())
					{
						if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equals("Back"))
						{
							openPackMenu((Player)event.getWhoClicked());
							return;
						}
					}
				}
			}
			
			if(event.getCurrentItem() != null)
			{
				ItemStack checkItem = event.getCurrentItem();
				if(checkItem.hasItemMeta())
				{
					if(ChatColor.stripColor(checkItem.getItemMeta().getDisplayName()).equals("Team Identifier"))
					{
						event.setCancelled(true);
					}
				}
			}
			return;
		}
			
		Player p = (Player) event.getWhoClicked();
		
		event.setCancelled(true);
			
		// Player is choosing a team
		if(ChatColor.stripColor(event.getInventory().getName()).equals("Select a Team!"))
		{
			if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta())
			{
				//p.closeInventory();
				return;
			}
				
			ItemStack teamItem = event.getCurrentItem();
			ItemMeta teamItemMeta = teamItem.getItemMeta();
				
			// Have the player join teams when they click the wool items
			if(ChatColor.stripColor(teamItemMeta.getDisplayName()).equals("Join Red Team"))
			{
				p.performCommand("team red");
			}
			else if(ChatColor.stripColor(teamItemMeta.getDisplayName()).equals("Join Blue Team"))
			{
				p.performCommand("team blue");
			}
			else if(ChatColor.stripColor(teamItemMeta.getDisplayName()).equals("Join Green Team"))
			{
				p.performCommand("team green");
			}
			else if(ChatColor.stripColor(teamItemMeta.getDisplayName()).equals("Join Purple Team"))
			{
				p.performCommand("team purple");
			}		
			p.closeInventory();
			return;
		}
		
		// Player is voting for a map
		if(ChatColor.stripColor(event.getInventory().getName()).equals("Map Voting!"))
		{
			if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta())
			{
				//p.closeInventory();
				return;
			}
			
			ItemStack mapItem = event.getCurrentItem();
			ItemMeta mapItemMeta = mapItem.getItemMeta();
			
			for(int i = 0; i < mapNames.size(); i++)
			{
				if(ChatColor.stripColor(mapItemMeta.getDisplayName()).equals(mapNames.get(i)))
				{
					getPlayerInfo(p.getName()).votedMap = mapNames.get(i);
					p.sendMessage(ChatColor.GOLD + "You have voted for " + ChatColor.AQUA + mapNames.get(i));
				}
			}
				
			p.closeInventory();
			return;
		}
			
		// Player is selecting a class
		if(ChatColor.stripColor(event.getInventory().getName()).equals("Select a Class!"))
		{
			if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta())
			{
				p.closeInventory();
				return;
			}
				
			// If the item is a class, set that class as the player's current class
			for(int i = 0; i < classes.size(); i++)
			{
				if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equals("Equip " + classes.get(i).className + "!"))
				{
					ControlPointClass eClass = classes.get(i);
					p.sendMessage(ChatColor.GREEN + "You will equip " + eClass.className + " on your next respawn!");
					getPlayerInfo(p.getName()).changeClass = eClass.className;
					p.closeInventory();
					return;
				}
			}
		}
			
		// Player is in the perk menu
		if(ChatColor.stripColor(event.getInventory().getName()).equals("Perks!"))
		{
			if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta())
			{
				p.closeInventory();
				return;
			}
				
			ItemStack perkItem = event.getCurrentItem();
			ItemMeta perkMeta = perkItem.getItemMeta();
				
			// If the item the player clicks is a perk, equip the perk
			if(!perkMeta.getDisplayName().contains("Upgrade") && !perkMeta.getDisplayName().equals(" "))
			{
				PlayerInfo pInfo = getPlayerInfo(p.getName());
				String perkName = ChatColor.stripColor(perkMeta.getDisplayName());
				if(!pInfo.currentPerk.equals(perkName) && perkItem.getType() != Material.EMERALD)
				{
					p.sendMessage(ChatColor.GOLD + "You have removed the " + pInfo.currentPerk + " perk!");
					pInfo.currentPerk = ChatColor.stripColor(perkMeta.getDisplayName());
					p.closeInventory();
					p.sendMessage(ChatColor.GOLD + "You will equip the " + perkName + " perk on your next respawn!");
					return;
				}	
			}
				
			// Code for upgrading perks will go here
			if(perkMeta.getDisplayName().contains("Upgrade"))
			{
				
			}
		}
		
		// Player is in the perk menu
		if(ChatColor.stripColor(event.getInventory().getName()).equals("Heroes!"))
		{
			if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta())
			{
				p.closeInventory();
				return;
			}
			
			if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equals("Exit"))
			{
				p.closeInventory();
				return;
			}
			
			for(HeroPack hp : heroPacks)
			{
				if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equals(hp.packName))
				{
					PlayerInfo info = getPlayerInfo(p.getName());
					if(!info.currentPack.equals(hp.packName))
					{
						if(info.unlockedPacks.contains(hp.packName))
						{
							info.currentPack = hp.packName;
							p.sendMessage(ChatColor.GOLD + "You have chosen the " + hp.packName + " Hero Pack!");
							p.closeInventory();
							return;
						}
					}
				}
			}
			
			for(ControlPointHero hero : heroes)
			{
				if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equals(hero.heroName))
				{
					openHeroMenu(p, hero);
					return;
				}
			}

		}
	}
	
	// Wand fire
	@EventHandler
	public void wandFire(PlayerInteractEvent event)
	{
		if(event.getItem() == null)
		{
			return;
		}
		if(event.getItem().getType() != Material.BLAZE_ROD)
		{
			return;
		}
		if(!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
		{
			return;
		}
		if(getPlayerInfo(event.getPlayer().getName()).wandFire == true)
		{
			Fireball fireball = event.getPlayer().launchProjectile(Fireball.class);
			fireball.setVelocity(fireball.getVelocity().multiply(2));
			getPlayerInfo(event.getPlayer().getName()).wandFire = false;
			
			// Refresh wand
			scheduler.scheduleSyncDelayedTask(this, new Runnable()
			{
				@Override
				public void run() {
					getPlayerInfo(event.getPlayer().getName()).wandFire = true;
				}
			}, 25L);
		}
	}
	
	// Grenade
	@EventHandler
	public void grenadeHit(ProjectileHitEvent event)
	{
		if(!(event.getEntity() instanceof Egg))
		{
			return;
		}
		Egg grenade = (Egg)event.getEntity();

		// Explosion after second and a half
		scheduler.scheduleSyncDelayedTask(this, new Runnable()
		{
			@Override
			public void run() {
				for(int i = 0; i < 10; i++)
				{
					Location explosionLocation = new Location(grenade.getWorld(), grenade.getLocation().getX() + Math.floor(Math.random() * 5) - 2, grenade.getLocation().getY() + Math.floor(Math.random() * 5) - 2, grenade.getLocation().getZ() + Math.floor(Math.random() * 5) - 2);
					explosionLocation.getWorld().createExplosion(explosionLocation, 0.0f);
				}
				
				Player shooter = (Player)grenade.getShooter();
				
				for(Player hitPlayer: Bukkit.getOnlinePlayers())
				{
					// Cancel Team Damage
					Team shooterTeam = getPlayerTeam(shooter);
					if(shooterTeam.getName().equals(getPlayerTeam(hitPlayer).getName()))
					{
						continue;
					}
					
					if(hitPlayer.getLocation().distance(grenade.getLocation()) < 5)
					{
						if(getPlayerInfo(hitPlayer.getName()).isHero == false)
						{
							if(hitPlayer.getHealth() - 6 <= 0)
							{
								playerDie(hitPlayer, shooter);
							}
							else
							{
								hitPlayer.damage(6);
							}
						}
						else
						{
							if(getPlayerInfo(hitPlayer.getName()).heroHealth - 6 <= 0)
							{
								if(getPlayerInfo(hitPlayer.getName()).heroHealth > 0)
								{
									hitPlayer.damage(6 - getPlayerInfo(hitPlayer.getName()).heroHealth);
									getPlayerInfo(hitPlayer.getName()).heroHealth = 0;
								}
								if(hitPlayer.getHealth() - 6 <= 0)
								{
									playerDie(hitPlayer, shooter);
								}
								else
								{
									hitPlayer.damage(6);
								}
							}
							else
							{
								getPlayerInfo(hitPlayer.getName()).heroHealth -= 6;
							}
						}
					}
				}
			}
		}, 30L);
	}
	
	// Contagion
	@EventHandler
	public void contagion(ProjectileHitEvent event)
	{
		if(!(event.getEntity() instanceof WitherSkull))
		{
			return;
		}
		if(event.getHitEntity() != null)
		{
			return;
		}
		if(!(event.getHitEntity() instanceof Player))
		{
			return;
		}
		if(event.getEntity().getMetadata("ProjectileType").get(0).asString().equals("Contagion"))
		{
			Player hitPlayer = (Player)event.getHitEntity();
			PlayerInfo info = getPlayerInfo(hitPlayer.getName());
			info.contagion = true;
			info.contagionTeam = getPlayerTeam((Player)event.getEntity().getShooter());
			if(hitPlayer.hasPotionEffect(PotionEffectType.WITHER))
			{
				hitPlayer.removePotionEffect(PotionEffectType.WITHER);
			}
			hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 0));
		}
	}
	
	// Pulse Bomb
	@EventHandler
	public void pulseBomb(ProjectileHitEvent event)
	{
		if(!(event.getEntity() instanceof EnderPearl))
		{
			return;
		}
		EnderPearl bomb = (EnderPearl)event.getEntity();
		Player shooter = (Player)bomb.getShooter();
		if(!bomb.hasMetadata("ProjectileType"))
		{
			// Blindness Orb
			for(Player hit : Bukkit.getOnlinePlayers())
			{
				if(getPlayerTeam(hit).getName().equals(getPlayerTeam(shooter).getName()))
				{
					continue;
				}
				if(bomb.getLocation().distance(hit.getLocation()) <= 3.5)
				{
					hit.sendMessage(ChatColor.RED + "You have been blinded by a Blindness Orb!");
					hit.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));
				}
			}
			return;
		}
		
		// Light Orb
		if(bomb.getMetadata("ProjectileType").get(0).asString().equals("Light"))
		{
			for(Player hitPlayer : Bukkit.getOnlinePlayers())
			{
				if(getPlayerTeam(shooter).getName().equals(getPlayerTeam(hitPlayer).getName()) || (shooter.equals(hitPlayer)))
				{
					if(bomb.getLocation().distance(hitPlayer.getLocation()) <= 3.5)
					{
						if(hitPlayer.hasPotionEffect(PotionEffectType.SPEED))
						{
							hitPlayer.removePotionEffect(PotionEffectType.SPEED);
						}
						hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0));
					}
					continue;
				}
				if(bomb.getLocation().distance(hitPlayer.getLocation()) <= 3.5)
				{
					if(hitPlayer.hasPotionEffect(PotionEffectType.BLINDNESS))
					{
						hitPlayer.removePotionEffect(PotionEffectType.BLINDNESS);
					}
					hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));
					if(getPlayerInfo(hitPlayer.getName()).isHero == false)
					{
						if(hitPlayer.getHealth() - 3 <= 0)
						{
							playerDie(hitPlayer, shooter);
						}
						else
						{
							hitPlayer.damage(3);
						}
					}
					else
					{
						if(getPlayerInfo(hitPlayer.getName()).heroHealth - 3 <= 0)
						{
							if(getPlayerInfo(hitPlayer.getName()).heroHealth > 0)
							{
								hitPlayer.damage(3 - getPlayerInfo(hitPlayer.getName()).heroHealth);
								getPlayerInfo(hitPlayer.getName()).heroHealth = 0;
							}
							if(hitPlayer.getHealth() - 3 <= 0)
							{
								playerDie(hitPlayer, shooter);
							}
							else
							{
								hitPlayer.damage(3);
							}
						}
						else
						{
							getPlayerInfo(hitPlayer.getName()).heroHealth -= 3;
						}
					}
				}
			}
		}
		
		// Timekeeper
		if(bomb.getMetadata("ProjectileType").get(0).asString().equals("Time"))
		{
			for(int i = -1; i <= 1; i++)
			{
				for(int j = -1; j <= 1; j++)
				{
					for(int k = 0; k < 5; k++)
					{
						Location particleLoc = new Location(bomb.getWorld(), bomb.getLocation().getX() + i + (Math.random() / 2.0), bomb.getLocation().getY(), bomb.getLocation().getZ() + j + (Math.random() / 2.0));
						particleLoc.getWorld().spawnParticle(Particle.REDSTONE, particleLoc, 0, 39/255 - 1, 4/255, 56/255, 1);
					}
				}
			}
			
			for(Player hitPlayer : Bukkit.getOnlinePlayers())
			{
				if(getPlayerTeam(shooter).getName().equals(getPlayerTeam(hitPlayer).getName()) || (shooter.equals(hitPlayer)))
				{
					continue;
				}
				if(bomb.getLocation().distance(hitPlayer.getLocation()) <= 2.5)
				{
					if(Math.random() <= .2)
					{
						if(Math.random() < .5)
						{
							hitPlayer.sendMessage(ChatColor.GOLD + "You have been slowed due to Kronos's Time Magic!");
							if(hitPlayer.hasPotionEffect(PotionEffectType.SLOW))
							{
								hitPlayer.removePotionEffect(PotionEffectType.SLOW);
							}
							hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 1));
						}
						else
						{
							hitPlayer.sendMessage(ChatColor.GOLD + "Your speed has been temporarily boosted due to Kronos's Time Magic!");
							if(hitPlayer.hasPotionEffect(PotionEffectType.SPEED))
							{
								hitPlayer.removePotionEffect(PotionEffectType.SPEED);
							}
							hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 4));
						}
					}
					if(getPlayerInfo(hitPlayer.getName()).isHero == false)
					{
						if(hitPlayer.getHealth() - 5 <= 0)
						{
							playerDie(hitPlayer, shooter);
						}
						else
						{
							hitPlayer.damage(5);
						}
					}
					else
					{
						if(getPlayerInfo(hitPlayer.getName()).heroHealth - 5 <= 0)
						{
							if(getPlayerInfo(hitPlayer.getName()).heroHealth > 0)
							{
								hitPlayer.damage(5 - getPlayerInfo(hitPlayer.getName()).heroHealth);
								getPlayerInfo(hitPlayer.getName()).heroHealth = 0;
							}
							if(hitPlayer.getHealth() - 5 <= 0)
							{
								playerDie(hitPlayer, shooter);
							}
							else
							{
								hitPlayer.damage(5);
							}
						}
						else
						{
							getPlayerInfo(hitPlayer.getName()).heroHealth -= 5;
						}
					}
				}
			}
		}
		
		if(bomb.getMetadata("ProjectileType").get(0).asString().equals("Pulse"))
		{
			// Firework Indicator
			Location fwLoc = new Location(bomb.getWorld(), bomb.getLocation().getX(), bomb.getLocation().getY() + 1.8, bomb.getLocation().getZ());
			
			Firework fw = (Firework)bomb.getWorld().spawnEntity(fwLoc, EntityType.FIREWORK);
			FireworkMeta fwMeta = fw.getFireworkMeta();
			fwMeta.addEffect(FireworkEffect.builder().trail(false).flicker(false).withColor(Color.YELLOW).withFade(Color.WHITE).with(FireworkEffect.Type.CREEPER).build());
			fw.setFireworkMeta(fwMeta);
			scheduler.scheduleSyncDelayedTask(instance, new Runnable()
			{
				@Override
				public void run() {
					fw.playEffect(EntityEffect.FIREWORK_EXPLODE);
					fw.remove();
				}
			}, 2L);
			
			// Explosion after second and a half
			scheduler.scheduleSyncDelayedTask(this, new Runnable()
			{
				@Override
				public void run() {
					for(int i = 0; i < 20; i++)
					{
						Location explosionLocation = new Location(bomb.getWorld(), bomb.getLocation().getX() + Math.floor(Math.random() * 7) - 3, bomb.getLocation().getY() + Math.floor(Math.random() * 7) - 3, bomb.getLocation().getZ() + Math.floor(Math.random() * 7) - 3);
						explosionLocation.getWorld().createExplosion(explosionLocation, 0.0f);
					}
					
					for(Player hitPlayer: Bukkit.getOnlinePlayers())
					{
						// Cancel Team Damage
						Team shooterTeam = getPlayerTeam(shooter);
						if(shooterTeam.getName().equals(getPlayerTeam(hitPlayer).getName()))
						{
							continue;
						}
						
						if(hitPlayer.getLocation().distance(bomb.getLocation()) < 5)
						{
							if(getPlayerInfo(hitPlayer.getName()).isHero == false)
							{
								if(hitPlayer.getHealth() - 11 <= 0)
								{
									playerDie(hitPlayer, shooter);
								}
								else
								{
									hitPlayer.damage(11);
								}
							}
							else
							{
								if(getPlayerInfo(hitPlayer.getName()).heroHealth - 11 <= 0)
								{
									playerDie(hitPlayer, shooter);
								}
								else
								{
									hitPlayer.damage(11);
								}
							}
						}
					}
				}
			}, 30L);
		}
	}
	
	// Timekeeper
	@EventHandler
	public void timekeeper(PlayerInteractEvent event)
	{
		if(event.getItem() == null)
		{
			return;
		}
		if(!(event.getItem().getType() == Material.DIAMOND_HOE && event.getItem().hasItemMeta()))
		{
			return;
		}
		if(!ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()).equals("Timekeeper"))
		{
			return;
		}
		if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
		{
			return;	
		}
		event.setCancelled(true);
		Player kronos = event.getPlayer();
		PlayerInfo kronosInfo = getPlayerInfo(kronos.getName());
		if(kronosInfo.heroAbility1 == false)
		{
			return;
		}

		kronosInfo.heroAbility1 = false;

		EnderPearl p = kronos.launchProjectile(EnderPearl.class);
		p.setVelocity(p.getVelocity().multiply(1.6));
		p.setShooter(kronos);
		p.setMetadata("ProjectileType", new FixedMetadataValue(this, "Time"));
		kronos.playSound(kronos.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 1, 1);
			
		scheduler.scheduleSyncDelayedTask(this, new Runnable()
		{
			@Override
			public void run() {
				kronosInfo.heroAbility1 = true;
			}
		}, 30L);
	}
	
	// Tracer bow
	@EventHandler
	public void tracerBow(PlayerInteractEvent event)
	{
		if(event.getItem() == null)
		{
			return;
		}
		if(!(event.getItem().getType() == Material.IRON_HOE && event.getItem().hasItemMeta()))
		{
			return;
		}
		if(!ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()).equals("Pulse Pistol"))
		{
			return;
		}
		if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
		{
			return;	
		}
		event.setCancelled(true);
		Player tracer = event.getPlayer();
		PlayerInfo tracerInfo = getPlayerInfo(tracer.getName());
		if(tracerInfo.heroAbility1 == false)
		{
			return;
		}
		for(int i = 0; i < 4; i++)
		{
			tracerInfo.heroAbility1 = false;
			scheduler.scheduleSyncDelayedTask(this, new Runnable()
			{
				@Override
				public void run() {
					
					Arrow a = tracer.launchProjectile(Arrow.class);
					a.setVelocity(a.getVelocity().multiply(1.2));
					a.setShooter(tracer);
					tracerArrows.add(a);
					tracer.playSound(tracer.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 1, 1);
				}
			}, i * 4);
			
			scheduler.scheduleSyncDelayedTask(this, new Runnable()
			{
				@Override
				public void run() {
					tracerInfo.heroAbility1 = true;
				}
			}, 28L);
		}
	}
	
	// Zeus thunderbolt
	@EventHandler
	public void zeusThunderbolt(PlayerInteractEvent event)
	{
		if(event.getItem() == null)
		{
			return;
		}
		if(!(event.getItem().getType() == Material.GOLD_SPADE && event.getItem().hasItemMeta()))
		{
			return;
		}
		if(!ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()).equals("Thunderbolt"))
		{
			return;
		}
		if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
		{
			return;	
		}
		event.setCancelled(true);
		Player zeus = event.getPlayer();
		PlayerInfo zeusInfo = getPlayerInfo(zeus.getName());
		if(zeusInfo.heroAbility1 == false)
		{
			return;
		}
		
		zeusInfo.heroAbility1 = false;
		Snowball snow = zeus.launchProjectile(Snowball.class);
		snow.setVelocity(snow.getVelocity().multiply(1.6));
		
		zeus.playSound(zeus.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 1, 1);
		
		scheduler.scheduleSyncDelayedTask(this, new Runnable()
		{
			@Override
			public void run() {
				zeusInfo.heroAbility1 = true;
			}
		}, 65L);
	}
	
	// Cecilia Staff
	@EventHandler
	public void ceciliaStaff(PlayerInteractEvent event)
	{
		if(event.getItem() == null)
		{
			return;
		}
		if(!((event.getItem().getType() == Material.GOLD_HOE || event.getItem().getType() == Material.WOOD_HOE) && event.getItem().hasItemMeta()))
		{
			return;
		}
		if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
		{
			return;	
		}
		
		Player cecilia = event.getPlayer();
		PlayerInfo ceciliaInfo = getPlayerInfo(cecilia.getName());
		
		if(ceciliaInfo.heroAbility1 == false)
		{
			return;
		}
		Potion splashPotion = null;
		if(ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()).equals("Staff of Devotion"))
		{
			splashPotion = new Potion(PotionType.INSTANT_HEAL, 1);
		}
		if(ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()).equals("Staff of Destruction"))
		{
			splashPotion = new Potion(PotionType.INSTANT_DAMAGE, 1);
		}
		if(splashPotion == null)
		{
			return;
		}
		splashPotion.setSplash(true);
		ceciliaInfo.heroAbility1 = false;
		ItemStack potionStack = splashPotion.toItemStack(1);
		//splashPotion.apply(potionStack);
		ThrownPotion tPotion = cecilia.launchProjectile(ThrownPotion.class);
		tPotion.setItem(potionStack);
		tPotion.setVelocity(tPotion.getVelocity().multiply(1.65));
		
		scheduler.scheduleSyncDelayedTask(this, new Runnable()
		{
			@Override
			public void run() {
				ceciliaInfo.heroAbility1 = true;
			}
		}, 60L);
	}
	
	// Method to rotate a vector around an axis
	public Vector rotateVectorCC(Vector vec, Vector axis, double theta){
	    double x, y, z;
	    double u, v, w;
	    x=vec.getX();y=vec.getY();z=vec.getZ();
	    u=axis.getX();v=axis.getY();w=axis.getZ();
	    double xPrime = u*(u*x + v*y + w*z)*(1d - Math.cos(theta)) 
	            + x*Math.cos(theta)
	            + (-w*y + v*z)*Math.sin(theta);
	    double yPrime = v*(u*x + v*y + w*z)*(1d - Math.cos(theta))
	            + y*Math.cos(theta)
	            + (w*x - u*z)*Math.sin(theta);
	    double zPrime = w*(u*x + v*y + w*z)*(1d - Math.cos(theta))
	            + z*Math.cos(theta)
	            + (-v*x + u*y)*Math.sin(theta);
	    return new Vector(xPrime, yPrime, zPrime);
	}
	
	// Apollo Bow
	@EventHandler
	public void apolloBow(EntityShootBowEvent event)
	{
		if(!event.getBow().hasItemMeta())
		{
			return;
		}
		if(!(ChatColor.stripColor(event.getBow().getItemMeta().getDisplayName()).equals("Bow of Light")))
		{
			return;
		}
		Arrow arrow = (Arrow)event.getProjectile();
		apolloArrows.add(arrow);
	}
	
	// Archer Bow
	@EventHandler
	public void archerBow(EntityShootBowEvent event)
	{
		if(!event.getBow().hasItemMeta())
		{
			return;
		}
		if(!(ChatColor.stripColor(event.getBow().getItemMeta().getDisplayName()).equals("Gilgamesh's Bow")))
		{
			return;
		}
		
		Player archer = (Player)event.getEntity();
		
		if(event.getForce() == 1)
		{
			Arrow archerArrow = (Arrow)event.getProjectile();
			archerArrow.setShooter(archer);
			/*Arrow archerArrow2 = archer.launchProjectile(Arrow.class);
			archerArrow2.setShooter(archer);
			archerArrow2.setVelocity(rotateVectorCC(archerArrow2.getVelocity(), new Vector(0,1,0), .15));
			Arrow archerArrow3 = archer.launchProjectile(Arrow.class);
			archerArrow3.setShooter(archer);
			archerArrow3.setVelocity(rotateVectorCC(archerArrow3.getVelocity(), new Vector(0,1,0), -.15));*/
			
			// 50% chance for each arrow to become a homing arrow
			if(Math.random() <= .5)
			{
				archerArrows.add(archerArrow);
			}
			/*if(Math.random() <= .4)
			{
				archerArrows.add(archerArrow2);
			}
			if(Math.random() <= .4)
			{
				archerArrows.add(archerArrow3);
			}*/
			
			Location fwLoc = new Location(archer.getWorld(), archer.getLocation().getX(), archer.getLocation().getY() + 1.2, archer.getLocation().getZ());
			
			Firework fw = (Firework)archer.getWorld().spawnEntity(fwLoc, EntityType.FIREWORK);
			FireworkMeta fwMeta = fw.getFireworkMeta();
			fwMeta.addEffect(FireworkEffect.builder().trail(false).flicker(false).withColor(Color.YELLOW).withFade(Color.YELLOW).with(FireworkEffect.Type.STAR).build());
			fw.setFireworkMeta(fwMeta);
			scheduler.scheduleSyncDelayedTask(this, new Runnable()
			{
				@Override
				public void run() {
					fw.playEffect(EntityEffect.FIREWORK_EXPLODE);
					fw.remove();
				}
			}, 2L);

			scheduler.scheduleSyncDelayedTask(this, new Runnable()
			{
				@Override
				public void run() {
					if(archerArrows.contains(archerArrow))
					{
						archerArrows.remove(archerArrow);
					}
					/*if(archerArrows.contains(archerArrow2))
					{
						archerArrows.remove(archerArrow2);
					}
					if(archerArrows.contains(archerArrow3))
					{
						archerArrows.remove(archerArrow3);
					}*/
				}
			}, 80L);
		}
	}
	
	public void coneParticles(Player player, float r, float g, float b)
    {
		new BukkitRunnable()
        {
        	double phi = 0;
            public void run()
            {
            	phi = phi + Math.PI/8;                                 
                double x, y, z;                
                       
                Location location1 = player.getLocation();
                for(double t = 0; t <= 2 * Math.PI; t = t + Math.PI / 8)
                {
                	for (double i = 0; i <= 1; i++)
                    {
                		x = 0.35 * (2 * Math.PI - t) * 0.5 * Math.cos(t + phi + i * Math.PI);
                        y = 0.4 * t;
                        z = 0.35 * (2 * Math.PI - t) * 0.5 * Math.sin(t + phi + i * Math.PI);
                        location1.add(x, y, z);
                        if(y > 1.15)
                        {
                        	/*for(Player partPlayer : Bukkit.getOnlinePlayers())
                        	{
                        		if(!partPlayer.equals(player))
                        		{
                        			partPlayer.spawnParticle(Particle.REDSTONE, location1, 0, (r-1)/255, g/255, b/255, 1);
                        		}
                        	}*/
                        }
                        else
                        {
                        	location1.getWorld().spawnParticle(Particle.REDSTONE, location1, 0, (r-1)/255, g/255, b/255, 1);
                        }
                        //location1.getWorld().spigot().playEffect(location1, Effect.COLOURED_DUST, 0, 1, (r-1)/255, g/255, b/255, 1, 0, 64);
                        location1.subtract(x,y,z);
                    }
                }              
                       
                if(getPlayerInfo(player.getName()).isHero == false)
                {                                          
                	this.cancel();
                }
            }      
        }.runTaskTimer(this, 0, 2);                       
    }
}