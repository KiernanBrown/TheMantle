package us.mcempires.empires;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EmpiresListener implements Listener 
{
	
	Empires empires;
	List<EmpiresClass> classes = new ArrayList<EmpiresClass>();
	
	// Method to set the classes used in the game
	public void setClasses()
	{
		Civilian c = new Civilian();
		classes.add(c.returnClass());
		Soldier s = new Soldier();
		classes.add(s.returnClass());
		Barbarian b = new Barbarian();
		classes.add(b.returnClass());
		Archer a = new Archer();
		classes.add(a.returnClass());
		Airbender air = new Airbender();
		classes.add(air.returnClass());
		Gatherer g = new Gatherer();
		classes.add(g.returnClass());
	}
	
	// Code to handle Player chagning class
	public void openClassMenu(TeamManager playerTeam, Player p)
	{
		Inventory classInv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Select a Class!");
		
		for(int i = 0; i < classes.size(); i++)
		{
			classInv.setItem(i, classes.get(i).returnInventoryStack(playerTeam));
		}
		p.openInventory(classInv);
	}
	
	// Code to handle Player selecting teams
	public void openTeamMenu(Player p)
	{
		Inventory teamInv = Bukkit.createInventory(null, 9, ChatColor.DARK_GRAY + "Select a Team!");
		
		// Red Team Wool
		ItemStack red = new ItemStack(Material.WOOL, 1, (byte)14);
		ItemMeta redMeta = red.getItemMeta();
		redMeta.setDisplayName(ChatColor.RED + "Join Red Team");
		List<String> redLore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Players: " + empires.redTeam.team.getPlayers().size() + "/6"));
		for(OfflinePlayer offP : empires.redTeam.team.getPlayers())
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
		List<String> blueLore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Players: " + empires.blueTeam.team.getPlayers().size() + "/6"));
		for(OfflinePlayer offP : empires.blueTeam.team.getPlayers())
		{
			blueLore.add(ChatColor.AQUA + offP.getPlayer().getDisplayName());
		}
		blueMeta.setLore(blueLore);
		blue.setItemMeta(blueMeta);
		teamInv.setItem(3, blue);
		
		// Green Team Wool
		ItemStack green = new ItemStack(Material.WOOL, 1, (byte)13);
		ItemMeta greenMeta = green.getItemMeta();
		greenMeta.setDisplayName(ChatColor.DARK_GREEN + "Join Green Team");
		List<String> greenLore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Players: " + empires.greenTeam.team.getPlayers().size() + "/6"));
		for(OfflinePlayer offP : empires.greenTeam.team.getPlayers())
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
		List<String> purpleLore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Players: " + empires.purpleTeam.team.getPlayers().size() + "/6"));
		for(OfflinePlayer offP : empires.purpleTeam.team.getPlayers())
		{
			purpleLore.add(ChatColor.AQUA + offP.getPlayer().getDisplayName());
		}
		purpleMeta.setLore(purpleLore);
		purple.setItemMeta(purpleMeta);
		teamInv.setItem(7, purple);
		
		p.openInventory(teamInv);
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
			if(org.bukkit.ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()).equals("Team Selector"))
			{
				openTeamMenu(event.getPlayer());
			}
		}
		
		if(event.getItem().getType() == Material.BOOK && event.getItem().hasItemMeta())
		{
			if(ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()).equals("Map Voting"))
			{
				empires.openMapMenu(event.getPlayer());
			}
		}
	}
	
	public void equipClass(Player p, EmpiresClass eClass)
	{
		// Equip class items and armor
		for(int j = 0; j < eClass.classItems.size(); j++)
		{
			p.getInventory().addItem(eClass.classItems.get(j));
		}
		for(int j = 0; j < eClass.classArmor.size(); j++)
		{
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
	
	@EventHandler
	public void onChat(PlayerChatEvent event)
	{
		Player p = event.getPlayer();
		TeamManager playerTeam = empires.getPlayerTeam(p);
		if(playerTeam == null)
		{
			for(Player globalPlayer : Bukkit.getOnlinePlayers())
			{
				globalPlayer.sendMessage(ChatColor.GRAY + p.getDisplayName() + ": " + event.getMessage());
			}
			event.setCancelled(true);
			return;
		}
		String chatType = empires.getPlayerInfo(p.getName()).chatType;
		if(empires.gameRunning)
		{
			if(chatType == "Team")
			{
				for(OfflinePlayer teammate : playerTeam.team.getPlayers())
				{
					Player teamPlayer = (Player)teammate;
					teamPlayer.sendMessage(ChatColor.GOLD + "[Team] " + playerTeam.prefix + p.getDisplayName() + ChatColor.GRAY + ": " + event.getMessage());
				}
			}
			else if(chatType == "Global")
			{
				for(Player globalPlayer : Bukkit.getOnlinePlayers())
				{
					globalPlayer.sendMessage(ChatColor.GOLD + "[Global] " + playerTeam.prefix  + p.getDisplayName() + ChatColor.GRAY + ": " + event.getMessage());
				}
			}
		}
		else
		{
			for(Player globalPlayer : Bukkit.getOnlinePlayers())
			{
				globalPlayer.sendMessage(playerTeam.prefix + p.getDisplayName() + ChatColor.GRAY + ": " + event.getMessage());
			}
		}
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		// Stop player from moving before the game starts
		if(empires.gameTimer > 1800.0f)
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		// Stop player from removing team identifier
		if(!ChatColor.stripColor(event.getInventory().getName()).equals("Select a Class!") && !ChatColor.stripColor(event.getInventory().getName()).equals("Select a Team!"))
		{
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
		
		// Player is choosing a team
		if(ChatColor.stripColor(event.getInventory().getName()).equals("Select a Team!"))
		{
			if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta())
			{
				p.closeInventory();
				return;
			}
			
			ItemStack teamItem = event.getCurrentItem();
			ItemMeta teamItemMeta = teamItem.getItemMeta();
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
		}
		
		// Player is selecting a class
		TeamManager playerTeam = empires.getPlayerTeam(p);
		event.setCancelled(true);
		
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta())
		{
			p.closeInventory();
			return;
		}
		
		for(int i = 0; i < classes.size(); i++)
		{
			if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equals("Equip " + classes.get(i).className + "!"))
			{
				EmpiresClass eClass = classes.get(i);
				if(empires.getPlayerInfo(p.getName()).currentClass == eClass.className)
				{
					p.sendMessage(ChatColor.RED + "You already have this class equiped!");
					p.closeInventory();
					return;
				}
				if(playerTeam.food < eClass.foodCost)
				{
					p.sendMessage(ChatColor.RED + "Your team does not have enough food for you to equip this class!");
					p.closeInventory();
					return;
				}
				
				p.sendMessage(ChatColor.GREEN + "You have equipped " + eClass.className + "!");
				p.sendMessage(ChatColor.RED + "All class items from previous classes and all armor have been removed.");
				playerTeam.food -= eClass.foodCost;
				empires.getPlayerInfo(p.getName()).currentClass = eClass.className;
				playerTeam.updateScoreboard();
				
				// Remove all previous class items, armor, and potion effects
				p.getInventory().setChestplate(null);
				p.getInventory().setLeggings(null);
				p.getInventory().setBoots(null);
				ItemStack[] allItems = p.getInventory().getContents();
				for(int j = allItems.length - 1; j >= 0; j--)
				{
					if(allItems[j] != null)
					{
						if(allItems[j].hasItemMeta())
						{
							if(allItems[j].getItemMeta().getLore() != null)
							{
								for(int k = 0; k < allItems[j].getItemMeta().getLore().size(); k++)
								{
									if(ChatColor.stripColor(allItems[j].getItemMeta().getLore().get(k)).equals("Class Item"))
									{
										p.getInventory().removeItem(allItems[j]);
									}
								}
							}
						}
					}
				}
				
				for(PotionEffect pE : p.getActivePotionEffects())
				{
					p.removePotionEffect(pE.getType());
				}
				
				// Equip class items and armor
				equipClass(p, eClass);
				
				p.closeInventory();
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		event.setJoinMessage(ChatColor.GOLD + player.getDisplayName() + " has joined! (" + Bukkit.getOnlinePlayers().size() + "/24)");
		player.getInventory().clear();
		ItemStack teamSelector = new ItemStack(Material.WOOL);
		ItemMeta teamMeta = teamSelector.getItemMeta();
		teamMeta.setDisplayName(ChatColor.GOLD + "Team Selector");
		teamSelector.setItemMeta(teamMeta);
		
		ItemStack mapVoting = new ItemStack(Material.BOOK);
		ItemMeta votingMeta = mapVoting.getItemMeta();
		votingMeta.setDisplayName(ChatColor.GOLD + "Map Voting");
		mapVoting.setItemMeta(votingMeta);
		player.getInventory().setItem(8, mapVoting);
		
		player.getInventory().addItem(teamSelector);
		player.setHealth(20);
		player.setFoodLevel(20);
		player.setLevel(1);
		player.setExp(0);
		player.teleport(empires.lobby.getSpawnLocation());
		empires.playerInfoList.add(new PlayerInfo(player.getName()));
		if(Bukkit.getOnlinePlayers().size() >= empires.requiredPlayers && empires.gameStarting == false)
		{
			empires.startCountdown();
		}
	}
	
	@EventHandler
	public void stopHunger(FoodLevelChangeEvent event)
	{
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerDamaged(EntityDamageEvent event)
	{
		// Cancel fall damage
		if(event.getCause() == DamageCause.FALL)
		{
			event.setCancelled(true);
			return;
		}
		
		if(event.getEntity() instanceof Player)
		{
			Player p = (Player)event.getEntity();
			// Cancel damage in lobby
			if(p.getLocation().getWorld() == empires.lobby)
			{
				event.setCancelled(true);
				return;
			}
			
			// Cancel damage in base protection
			if(empires.getPlayerInfo(p.getName()).baseProtection)
			{
				event.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler
	public void collectorDamage(EntityDamageByEntityEvent event)
	{
		if(event.getEntity() instanceof IronGolem)
		{
			IronGolem g = (IronGolem)event.getEntity();
			Collector damagedCollector = null;
			for(Collector c : empires.collectors)
			{
				if(g.equals(c.golem))
				{
					damagedCollector = c;
				}
			}
			if(damagedCollector == null)
			{
				return;
			}
			
			if(event.getDamager() instanceof Player)
			{
				Player p = (Player)event.getDamager();
				if(empires.getPlayerTeam(p).equals(damagedCollector.team))
				{
					event.setCancelled(true);
					return;
				}
			}
			
			if(event.getDamager() instanceof Arrow)
			{
				Arrow a = (Arrow)event.getDamager();
				Player p = (Player)a.getShooter();
				if(empires.getPlayerTeam(p).equals(damagedCollector.team))
				{
					event.setCancelled(true);
					return;
				}
			}
			
			if(damagedCollector.notificationTimer == 0)
			{
				for(OfflinePlayer offP : damagedCollector.team.team.getPlayers())
				{
					offP.getPlayer().sendMessage(ChatColor.RED + "[Collector] Your team's collector is being attacked!");
				}
				damagedCollector.notificationTimer = 5;
			}
			
			damagedCollector.health -= event.getDamage();
			g.setHealth(20);
			
			if(damagedCollector.health <= 0)
			{
				damagedCollector.team = null;
				damagedCollector.coolDownTimer = 20;
				damagedCollector.location.getBlock().setTypeIdAndData(95, (byte)0, true);
				new Location(damagedCollector.location.getWorld(), damagedCollector.location.getX(), damagedCollector.location.getY() - 1, damagedCollector.location.getZ()).getBlock().setType(Material.AIR);
				g.setHealth(0);
				for(OfflinePlayer offP : damagedCollector.team.team.getPlayers())
				{
					offP.getPlayer().sendMessage(ChatColor.RED + "[Collector] Your team's collector has been destroyed!");
					offP.getPlayer().playSound(offP.getPlayer().getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1, 1);
				}
			}
			//g.setHealth(damagedCollector.health);
		}
	}
	
	@EventHandler
	public void collectorPlace(PlayerInteractEvent event)
	{
		Player p = event.getPlayer();
		ItemStack item = p.getInventory().getItemInMainHand();
		if(item.getType() == Material.TRIPWIRE_HOOK && item.hasItemMeta())
		{
			if(ChatColor.stripColor(item.getItemMeta().getDisplayName()).equals("Collector") && event.getClickedBlock() != null)
			{
				Collector placeCollector = null;
				for(Collector c : empires.collectors)
				{
					if(event.getClickedBlock().getLocation().equals(c.location) && c.team == null)
					{
						placeCollector = c;
					}
				}
				if(placeCollector == null)
				{
					return;
				}
				if(placeCollector.coolDownTimer != -1)
				{
					p.sendMessage(ChatColor.RED + "[Collector] You cannot place a collector here until " + placeCollector.coolDownTimer + " more seconds have passed.");
					return;
				}
				
				TeamManager playerTeam = empires.getPlayerTeam(p);
				placeCollector.team = playerTeam;
				placeCollector.maxHealth = 100;
				placeCollector.health = placeCollector.maxHealth;
				p.getInventory().removeItem(item);
				switch(ChatColor.stripColor(playerTeam.teamName))
				{
				case "Red Team":
					placeCollector.location.getBlock().setTypeIdAndData(95, (byte)14, true);
					break;
				case "Blue Team":
					placeCollector.location.getBlock().setTypeIdAndData(95, (byte)11, true);
					break;
				case "Green Team":
					placeCollector.location.getBlock().setTypeIdAndData(95, (byte)13, true);
					break;
				case "Purple Team":
					placeCollector.location.getBlock().setTypeIdAndData(95, (byte)10, true);
					break;
				}
				
				p.sendMessage(ChatColor.GOLD + "[Collector] You have built a collector for your team!");
				for(OfflinePlayer teamMate : playerTeam.team.getPlayers())
				{
					Player tp = (Player) teamMate;
					if(tp != p)
					{
						tp.sendMessage(ChatColor.GOLD + "[Collector] " + p.getDisplayName() + " has built a collector for your team!");
					}
				}
				
				// Spawn the iron golem
				placeCollector.golem = (IronGolem)placeCollector.location.getWorld().spawnEntity(placeCollector.location, EntityType.IRON_GOLEM);
			}
		}
	}
	
	public void playerDeath(Player p, Player killer)
	{
		p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
		p.setHealth(20);
		empires.getPlayerInfo(p.getName()).killstreak = 0;
		TeamManager playerTeam = empires.getPlayerTeam(p);
		TeamManager killerTeam = empires.getPlayerTeam(killer);
		if(playerTeam.food >= 10)
		{
			switch(ChatColor.stripColor(playerTeam.teamName))
			{
				case "Red Team": p.teleport(empires.redSpawn);
				break;
				case "Blue Team": p.teleport(empires.blueSpawn);
				break;
				case "Green Team": p.teleport(empires.greenSpawn);
				break;
				case "Purple Team": p.teleport(empires.purpleSpawn);
				break;
			}
			p.sendMessage(ChatColor.RED + "You have been killed by " + killer.getDisplayName() + "! Your team has spent 10 food to revive you!");
			playerTeam.food -= 10;
			playerTeam.updateScoreboard();
			killer.sendMessage(ChatColor.RED + "You killed " + p.getDisplayName() + "! Your team has earned 3 Empire Points for killing an enemy that had enough food to be revived!");
			killerTeam.killPoints += 3;
			killerTeam.updateScoreboard();
			
			// Killstreaks
			PlayerInfo kInfo = empires.getPlayerInfo(killer.getName());
			kInfo.killstreak++;
			switch(kInfo.killstreak)
			{
				case 2: killer.sendMessage(ChatColor.GOLD + "[Killstreak] You have reached a two killstreak!");
					break;
				case 4: killer.sendMessage(ChatColor.GOLD + "[Killstreak] Awesome! That's a four killstreak!");
					break;
			}
			
			// Clear all items
			p.getInventory().clear();
			
			// Equip class
			p.getInventory().setHelmet(playerTeam.getIdentifier());
			for(int i = 0; i < classes.size(); i++)
			{
				if(ChatColor.stripColor(empires.getPlayerInfo(p.getName()).currentClass).equals(ChatColor.stripColor(classes.get(i).className)))
				{
					EmpiresClass eClass = classes.get(i);
					equipClass(p, eClass);
					break;
				}
			}
			
			// Apply potion effect
			p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 5));
		}
		else
		{
			p.teleport(empires.lobby.getSpawnLocation());
			p.sendMessage(ChatColor.RED + "You have been killed by " + killer.getDisplayName() + "! Your team did not have enough food to revive you!");
			
			// Set respawn timer
			empires.getPlayerInfo(p.getName()).respawnTimer = 120.0f - ((120.0f/10) * playerTeam.food);
			
			playerTeam.food = 0;
			playerTeam.updateScoreboard();
			killer.sendMessage(ChatColor.RED + "You killed " + p.getDisplayName() + "! Your team has earned 5 Empire Points for killing an enemy that did not have enough food to be revived!");
			killerTeam.killPoints += 5;
			killerTeam.updateScoreboard();
			
			// Clear all items
			p.getInventory().clear();
			
			// Equip class
			p.getInventory().setHelmet(playerTeam.getIdentifier());
			for(int i = 0; i < classes.size(); i++)
			{
				if(ChatColor.stripColor(empires.getPlayerInfo(p.getName()).currentClass).equals(ChatColor.stripColor(classes.get(i).className)))
				{
					EmpiresClass eClass = classes.get(i);
					equipClass(p, eClass);
					break;
				}
			}
			
			// Apply potion effect
			p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 5));	
		}
	}
	
	@EventHandler
	public void onPlayerDeath(EntityDamageByEntityEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			Player p = (Player)event.getEntity();
			Player killer = null;
			if(event.getDamager() instanceof Player)
			{
				 killer = (Player)event.getDamager();
			}
			if(event.getDamager() instanceof Arrow)
			{
				Arrow a = (Arrow)event.getDamager();
				killer = (Player)a.getShooter();
			}
			
			// Player Death
			if(p.getHealth() - event.getDamage() <= 0)
			{
				event.setCancelled(true);
				playerDeath(p, killer);
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player quit = event.getPlayer();
		if(empires.redTeam.team.hasPlayer(quit))
		{
			empires.redTeam.team.removePlayer(quit);
		}
		else if(empires.blueTeam.team.hasPlayer(quit))
		{
			empires.blueTeam.team.removePlayer(quit);
		}
		else if(empires.greenTeam.team.hasPlayer(quit))
		{
			empires.greenTeam.team.removePlayer(quit);
		}
		else if(empires.purpleTeam.team.hasPlayer(quit))
		{
			empires.purpleTeam.team.removePlayer(quit);
		}
		event.setQuitMessage(null);
	}
	
	// Stop players from dropping Class Items
	@EventHandler
	public void onDropItem(PlayerDropItemEvent event)
	{
		ItemStack dropItem = event.getItemDrop().getItemStack();
		if(dropItem != null)
		{
			if(dropItem.hasItemMeta())
			{
				ItemMeta dropMeta = dropItem.getItemMeta();
				for(int i = 0; i < dropMeta.getLore().size(); i++)
				{
					if(ChatColor.stripColor(dropMeta.getLore().get(i)).equals("Class Item"))
					{
						event.getPlayer().sendMessage(ChatColor.RED + "Class Items cannot be dropped!");
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	// Player only catches fish, and gives a messsage when caught
	@EventHandler
	public void onPlayerFish(PlayerFishEvent event)
	{
		if(event.getCaught() != null)
		{
			Item caught = (Item) event.getCaught();
			ItemStack fish = caught.getItemStack();
			//fish.setTypeId(349);
			fish.setType(Material.RAW_FISH);
			fish.setDurability((short)0);
			event.getPlayer().sendMessage(ChatColor.GREEN + "You caught a fish!");
			//event.getPlayer().getInventory().addItem(new ItemStack(Material.RAW_FISH));
			//event.setExpToDrop(0);
			//event.getHook().
			//event.setCancelled(true);
		}
	}
	
	// Change mob drops
	@EventHandler
	public void onAnimalKill(EntityDeathEvent event)
	{
		if(event.getEntity() instanceof Cow)
		{
			event.getDrops().clear();
			event.setDroppedExp(0);
			ItemStack newDrop = new ItemStack(Material.RAW_BEEF);
			int dropNum = (int)Math.floor((Math.random() * 3));
			newDrop.setAmount(dropNum);
			event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), newDrop);
		}
		if(event.getEntity() instanceof Sheep)
		{
			event.getDrops().clear();
			event.setDroppedExp(0);
			ItemStack newDrop = new ItemStack(Material.BONE);
			int dropNum = (int)Math.floor((Math.random() * 3));
			newDrop.setAmount(dropNum);
			event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), newDrop);
		}
		if(event.getEntity() instanceof Pig)
		{
			event.getDrops().clear();
			event.setDroppedExp(0);
			ItemStack newDrop = new ItemStack(Material.PORK);
			int dropNum = (int)Math.floor((Math.random() * 3));
			newDrop.setAmount(dropNum);
			event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), newDrop);
		}
		if(event.getEntity() instanceof IronGolem)
		{
			event.getDrops().clear();
			event.setDroppedExp(0);
		}
	}
	
	// Stop exp from furnaces
	@EventHandler
	public void onRemoveFurnaceItem(FurnaceExtractEvent event)
	{
		event.setExpToDrop(0);
	}
	
	@EventHandler
	public void depositSign(PlayerInteractEvent event)
	{
		// Check if the player is clicking a sign
		if(event.getClickedBlock() != null)
		{
			if(event.getClickedBlock().getType() == Material.SIGN_POST || event.getClickedBlock().getType() == Material.WALL_SIGN)
			{
				Sign s = (Sign)event.getClickedBlock().getState();
				Player p = event.getPlayer();
				
				// Get team of this player
				TeamManager playerTeam = empires.getPlayerTeam(p);
				
				// Get player info
				PlayerInfo info = empires.getPlayerInfo(p.getName());
				
				// Gather Tools
				if(ChatColor.stripColor(s.getLine(1)).equals("Gather") && ChatColor.stripColor(s.getLine(2)).equals("Tools"))
				{
					if(info.toolsTimer != 0)
					{
						p.sendMessage(ChatColor.RED + "[Tools] You cannot gather tools until another " + info.toolsTimer + " seconds have passed!");
						return;
					}
					
					info.toolsTimer = 30;
					
					// Wood Tools
					if(playerTeam.level < 10)
					{
						p.getInventory().addItem(new ItemStack(Material.WOOD_AXE));
						p.getInventory().addItem(new ItemStack(Material.WOOD_PICKAXE));
						p.getInventory().addItem(new ItemStack(Material.WOOD_SPADE));
						p.getInventory().addItem(new ItemStack(Material.WOOD_HOE));
						p.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
					}
					
					// Stone Tools
					else if(playerTeam.level >= 10 && playerTeam.level < 20)
					{
						p.getInventory().addItem(new ItemStack(Material.STONE_AXE));
						p.getInventory().addItem(new ItemStack(Material.STONE_PICKAXE));
						p.getInventory().addItem(new ItemStack(Material.STONE_SPADE));
						p.getInventory().addItem(new ItemStack(Material.STONE_HOE));
						p.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
					}
					
					// Iron Tools
					else if(playerTeam.level >= 20 && playerTeam.level < 30)
					{
						p.getInventory().addItem(new ItemStack(Material.IRON_AXE));
						p.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE));
						p.getInventory().addItem(new ItemStack(Material.IRON_SPADE));
						p.getInventory().addItem(new ItemStack(Material.IRON_HOE));
						p.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
					}
					
					// Diamond Tools
					else if(playerTeam.level >= 30)
					{
						p.getInventory().addItem(new ItemStack(Material.DIAMOND_AXE));
						p.getInventory().addItem(new ItemStack(Material.DIAMOND_PICKAXE));
						p.getInventory().addItem(new ItemStack(Material.DIAMOND_SPADE));
						p.getInventory().addItem(new ItemStack(Material.DIAMOND_HOE));
						p.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
					}
				}
				
				// Change Class
				if(ChatColor.stripColor(s.getLine(1)).equals("Change") && ChatColor.stripColor(s.getLine(2)).equals("Class"))
				{
					/*s.setLine(0, ChatColor.GOLD + "-[]-[]-[]-[]-");
					s.setLine(1, ChatColor.LIGHT_PURPLE + "§lChange");
					s.setLine(2, ChatColor.LIGHT_PURPLE + "§lClass");
					s.setLine(3, ChatColor.GOLD + "-[]-[]-[]-[]-");
					s.update();*/
					openClassMenu(playerTeam, p);
				}
				
				// Craft Collector
				if(ChatColor.stripColor(s.getLine(1)).equals("Craft") && ChatColor.stripColor(s.getLine(2)).equals("Collector"))
				{
					if(info.collectorTimer != 0)
					{
						p.sendMessage(ChatColor.RED + "[Collector] You cannot craft a collector until another " + info.collectorTimer + " seconds have passed.");
						return;
					}
					
					if(playerTeam.wood >= empires.collectorCost && playerTeam.bricks >= empires.collectorCost)
					{
						playerTeam.wood -= empires.collectorCost;
						playerTeam.bricks -= empires.collectorCost;
						p.sendMessage(ChatColor.GOLD + "[Collector] You have crafted a Collector!");
						ItemStack harvestStack = new ItemStack(Material.TRIPWIRE_HOOK);
						ItemMeta harvestMeta = harvestStack.getItemMeta();
						harvestMeta.setDisplayName(ChatColor.GOLD + "Collector");
						harvestMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Right click with this on a", ChatColor.AQUA + "Collector Beacon to build", ChatColor.AQUA + "a Collector for your team!")));
						harvestStack.setItemMeta(harvestMeta);
						p.getInventory().addItem(harvestStack);
						info.collectorTimer = 30;
					}
				}
				
				// Deposit Wood
				if(ChatColor.stripColor(s.getLine(1)).equals("Deposit") && ChatColor.stripColor(s.getLine(2)).equals("Wood"))
				{
					/*s.setLine(0, ChatColor.GOLD + "-[]-[]-[]-[]-");
					s.setLine(1, ChatColor.LIGHT_PURPLE + "§lDeposit");
					s.setLine(2, ChatColor.LIGHT_PURPLE + "§lWood");
					s.setLine(3, ChatColor.GOLD + "-[]-[]-[]-[]-");
					s.update();*/
					
					// Wood Logs
					if(p.getEquipment().getItemInMainHand().getType() == Material.LOG)
					{
						ItemStack depositStack = p.getInventory().getItemInMainHand();
						int depositAmount = depositStack.getAmount();
						int pointAmount = depositAmount * 2;
						p.sendMessage(ChatColor.YELLOW + "[Deposit] Deposited " + depositAmount + " wood logs, giving " + pointAmount + " wood!");
						for(OfflinePlayer teamMate : playerTeam.team.getPlayers())
						{
							Player tp = (Player) teamMate;
							if(tp != p)
							{
								tp.sendMessage(ChatColor.YELLOW + "[Team Deposit] " + p.getDisplayName() + " deposited " + depositAmount + " wood logs, giving " + pointAmount + " wood!");
							}
						}
						p.getInventory().removeItem(depositStack);
						playerTeam.wood += pointAmount;
						playerTeam.updateLevel();
					}
					// Wood Planks
					if(p.getEquipment().getItemInMainHand().getType() == Material.WOOD)
					{
						ItemStack depositStack = p.getInventory().getItemInMainHand();
						int depositAmount = depositStack.getAmount();
						int pointAmount = depositAmount;
						p.sendMessage(ChatColor.YELLOW + "[Deposit] Deposited " + depositAmount + " wood planks, giving " + pointAmount + " wood!");
						for(OfflinePlayer teamMate : playerTeam.team.getPlayers())
						{
							Player tp = (Player) teamMate;
							if(tp != p)
							{
								tp.sendMessage(ChatColor.YELLOW + "[Team Deposit] " + p.getDisplayName() + " deposited " + depositAmount + " wood planks, giving " + pointAmount + " wood!");
							}
						}
						p.getInventory().removeItem(depositStack);
						playerTeam.wood += pointAmount;
						playerTeam.updateLevel();
					}
					// Wood Slabs
					if(p.getEquipment().getItemInMainHand().getTypeId() == 126)
					{
						ItemStack depositStack = p.getInventory().getItemInMainHand();
						int depositAmount = depositStack.getAmount();
						int pointAmount = depositAmount;
						p.sendMessage(ChatColor.YELLOW + "[Deposit] Deposited " + depositAmount + " wood slabs, giving " + pointAmount + " wood!");
						for(OfflinePlayer teamMate : playerTeam.team.getPlayers())
						{
							Player tp = (Player) teamMate;
							if(tp != p)
							{
								tp.sendMessage(ChatColor.YELLOW + "[Team Deposit] " + p.getDisplayName() + " deposited " + depositAmount + " wood slabs, giving " + pointAmount + " wood!");
							}
						}
						p.getInventory().removeItem(depositStack);
						playerTeam.wood += pointAmount;
						playerTeam.updateLevel();
					}
				}
				
				// Deposit Bricks
				if(ChatColor.stripColor(s.getLine(1)).equals("Deposit") && ChatColor.stripColor(s.getLine(2)).equals("Bricks"))
				{
					/*s.setLine(0, ChatColor.GOLD + "-[]-[]-[]-[]-");
					s.setLine(1, ChatColor.LIGHT_PURPLE + "§lDeposit");
					s.setLine(2, ChatColor.LIGHT_PURPLE + "§lBricks");
					s.setLine(3, ChatColor.GOLD + "-[]-[]-[]-[]-");
					s.update();*/
					
					// Bricks
					if(p.getEquipment().getItemInMainHand().getType() == Material.CLAY_BRICK)
					{
						ItemStack depositStack = p.getInventory().getItemInMainHand();
						int depositAmount = depositStack.getAmount();
						int pointAmount = depositAmount;
						p.sendMessage(ChatColor.YELLOW + "[Deposit] Deposited " + depositAmount + " bricks, giving " + pointAmount + " bricks!");
						for(OfflinePlayer teamMate : playerTeam.team.getPlayers())
						{
							Player tp = (Player) teamMate;
							if(tp != p)
							{
								tp.sendMessage(ChatColor.YELLOW + "[Team Deposit] " + p.getDisplayName() + " deposited " + depositAmount + " bricks, giving " + pointAmount + " bricks!");
							}
						}
						p.getInventory().removeItem(depositStack);
						playerTeam.bricks += pointAmount;
						playerTeam.updateLevel();
					}
					
					// Brick Blocks
					if(p.getEquipment().getItemInMainHand().getType() == Material.BRICK)
					{
						ItemStack depositStack = p.getInventory().getItemInMainHand();
						int depositAmount = depositStack.getAmount();
						int pointAmount = depositAmount * 7;
						p.sendMessage(ChatColor.YELLOW + "[Deposit] Deposited " + depositAmount + " brick blocks, giving " + pointAmount + " bricks!");
						for(OfflinePlayer teamMate : playerTeam.team.getPlayers())
						{
							Player tp = (Player) teamMate;
							if(tp != p)
							{
								tp.sendMessage(ChatColor.YELLOW + "[Team Deposit] " + p.getDisplayName() + " deposited " + depositAmount + " brick blocks, giving " + pointAmount + " bricks!");
							}
						}
						p.getInventory().removeItem(depositStack);
						playerTeam.bricks += pointAmount;
						playerTeam.updateLevel();
					}
					
					// Brick Slabs
					if(p.getEquipment().getItemInMainHand().getTypeId() == 44)
					{
						ItemStack depositStack = p.getInventory().getItemInMainHand();
						int depositAmount = depositStack.getAmount();
						int pointAmount = depositAmount * 5;
						p.sendMessage(ChatColor.YELLOW + "[Deposit] Deposited " + depositAmount + " bricks slabs, giving " + pointAmount + " bricks!");
						for(OfflinePlayer teamMate : playerTeam.team.getPlayers())
						{
							Player tp = (Player) teamMate;
							if(tp != p)
							{
								tp.sendMessage(ChatColor.YELLOW + "[Team Deposit] " + p.getDisplayName() + " deposited " + depositAmount + " bricks slabs, giving " + pointAmount + " bricks!");
							}
						}
						p.getInventory().removeItem(depositStack);
						playerTeam.bricks += pointAmount;
						playerTeam.updateLevel();
					}
				}
				
				// Deposit Gold
				if(ChatColor.stripColor(s.getLine(1)).equals("Deposit") && ChatColor.stripColor(s.getLine(2)).equals("Gold"))
				{
					/*s.setLine(0, ChatColor.GOLD + "-[]-[]-[]-[]-");
					s.setLine(1, ChatColor.LIGHT_PURPLE + "§lDeposit");
					s.setLine(2, ChatColor.LIGHT_PURPLE + "§lGold");
					s.setLine(3, ChatColor.GOLD + "-[]-[]-[]-[]-");
					s.update();*/
					
					// Gold Ingots
					if(p.getEquipment().getItemInMainHand().getType() == Material.GOLD_INGOT)
					{
						ItemStack depositStack = p.getInventory().getItemInMainHand();
						int depositAmount = depositStack.getAmount();
						int pointAmount = depositAmount;
						p.sendMessage(ChatColor.YELLOW + "[Deposit] Deposited " + depositAmount + " gold ingots, giving " + pointAmount + " gold!");
						for(OfflinePlayer teamMate : playerTeam.team.getPlayers())
						{
							Player tp = (Player) teamMate;
							if(tp != p)
							{
								tp.sendMessage(ChatColor.YELLOW + "[Team Deposit] " + p.getDisplayName() + " deposited " + depositAmount + " gold ingots, giving " + pointAmount + " gold!");
							}
						}
						p.getInventory().removeItem(depositStack);
						playerTeam.gold += pointAmount;
						playerTeam.updateScoreboard();
					}
					
					// Gold Blocks
					if(p.getEquipment().getItemInMainHand().getType() == Material.GOLD_BLOCK)
					{
						ItemStack depositStack = p.getInventory().getItemInMainHand();
						int depositAmount = depositStack.getAmount();
						int pointAmount = depositAmount * 12;
						p.sendMessage(ChatColor.YELLOW + "[Deposit] Deposited " + depositAmount + " gold blocks, giving " + pointAmount + " gold!");
						for(OfflinePlayer teamMate : playerTeam.team.getPlayers())
						{
							Player tp = (Player) teamMate;
							if(tp != p)
							{
								tp.sendMessage(ChatColor.YELLOW + "[Team Deposit] " + p.getDisplayName() + " deposited " + depositAmount + " gold blocks, giving " + pointAmount + " gold!");
							}
						}
						p.getInventory().removeItem(depositStack);
						playerTeam.gold += pointAmount;
						playerTeam.updateScoreboard();
					}
					
					// Emeralds
					if(p.getEquipment().getItemInMainHand().getType() == Material.EMERALD)
					{
						ItemStack depositStack = p.getInventory().getItemInMainHand();
						int depositAmount = depositStack.getAmount();
						int pointAmount = depositAmount * 2;
						p.sendMessage(ChatColor.YELLOW + "[Deposit] Deposited " + depositAmount + " emeralds, giving " + pointAmount + " gold!");
						for(OfflinePlayer teamMate : playerTeam.team.getPlayers())
						{
							Player tp = (Player) teamMate;
							if(tp != p)
							{
								tp.sendMessage(ChatColor.YELLOW + "[Team Deposit] " + p.getDisplayName() + " deposited " + depositAmount + " emeralds, giving " + pointAmount + " gold!");
							}
						}
						p.getInventory().removeItem(depositStack);
						playerTeam.gold += pointAmount;
						playerTeam.updateScoreboard();
					}

					// Emerald Blocks
					if(p.getEquipment().getItemInMainHand().getType() == Material.EMERALD_BLOCK)
					{
						ItemStack depositStack = p.getInventory().getItemInMainHand();
						int depositAmount = depositStack.getAmount();
						int pointAmount = depositAmount * 25;
						p.sendMessage(ChatColor.YELLOW + "[Deposit] Deposited " + depositAmount + " emerald blocks, giving " + pointAmount + " gold!");
						for(OfflinePlayer teamMate : playerTeam.team.getPlayers())
						{
							Player tp = (Player) teamMate;
							if(tp != p)
							{
								tp.sendMessage(ChatColor.YELLOW + "[Team Deposit] " + p.getDisplayName() + " deposited " + depositAmount + " emerald blocks, giving " + pointAmount + " gold!");
							}
						}
						p.getInventory().removeItem(depositStack);
						playerTeam.gold += pointAmount;
						playerTeam.updateScoreboard();
					}
				}
				
				// Deposit Food
				if(ChatColor.stripColor(s.getLine(1)).equals("Deposit") && ChatColor.stripColor(s.getLine(2)).equals("Food"))
				{
					/*s.setLine(0, ChatColor.GOLD + "-[]-[]-[]-[]-");
					s.setLine(1, ChatColor.LIGHT_PURPLE + "§lDeposit");
					s.setLine(2, ChatColor.LIGHT_PURPLE + "§lFood");
					s.setLine(3, ChatColor.GOLD + "-[]-[]-[]-[]-");
					s.update();*/
					
					// Wheat
					if(p.getEquipment().getItemInMainHand().getType() == Material.WHEAT)
					{
						ItemStack depositStack = p.getInventory().getItemInMainHand();
						int depositAmount = depositStack.getAmount();
						int pointAmount = depositAmount;
						p.sendMessage(ChatColor.YELLOW + "[Deposit] Deposited " + depositAmount + " wheat, giving " + pointAmount + " food!");
						for(OfflinePlayer teamMate : playerTeam.team.getPlayers())
						{
							Player tp = (Player) teamMate;
							if(tp != p)
							{
								tp.sendMessage(ChatColor.YELLOW + "[Team Deposit] " + p.getDisplayName() + " deposited " + depositAmount + " wheat, giving " + pointAmount + " food!");
							}
						}
						p.getInventory().removeItem(depositStack);
						playerTeam.food += pointAmount;
						playerTeam.updateScoreboard();
					}
					
					// Bread
					if(p.getEquipment().getItemInMainHand().getType() == Material.BREAD)
					{
						ItemStack depositStack = p.getInventory().getItemInMainHand();
						int depositAmount = depositStack.getAmount();
						int pointAmount = depositAmount * 5;
						p.sendMessage(ChatColor.YELLOW + "[Deposit] Deposited " + depositAmount + " bread, giving " + pointAmount + " food!");
						for(OfflinePlayer teamMate : playerTeam.team.getPlayers())
						{
							Player tp = (Player) teamMate;
							if(tp != p)
							{
								tp.sendMessage(ChatColor.YELLOW + "[Team Deposit] " + p.getDisplayName() + " deposited " + depositAmount + " bread, giving " + pointAmount + " food!");
							}
						}
						p.getInventory().removeItem(depositStack);
						playerTeam.food += pointAmount;
						playerTeam.updateScoreboard();
					}
					
					// Raw Beef
					if(p.getEquipment().getItemInMainHand().getType() == Material.RAW_BEEF)
					{
						ItemStack depositStack = p.getInventory().getItemInMainHand();
						int depositAmount = depositStack.getAmount();
						int pointAmount = depositAmount * 3;
						p.sendMessage(ChatColor.YELLOW + "[Deposit] Deposited " + depositAmount + " raw beef, giving " + pointAmount + " food!");
						for(OfflinePlayer teamMate : playerTeam.team.getPlayers())
						{
							Player tp = (Player) teamMate;
							if(tp != p)
							{
								tp.sendMessage(ChatColor.YELLOW + "[Team Deposit] " + p.getDisplayName() + " deposited " + depositAmount + " raw beef, giving " + pointAmount + " food!");
							}
						}
						p.getInventory().removeItem(depositStack);
						playerTeam.food += pointAmount;
						playerTeam.updateScoreboard();
					}

					// Steak
					if(p.getEquipment().getItemInMainHand().getType() == Material.COOKED_BEEF)
					{
						ItemStack depositStack = p.getInventory().getItemInMainHand();
						int depositAmount = depositStack.getAmount();
						int pointAmount = depositAmount * 10;
						p.sendMessage(ChatColor.YELLOW + "[Deposit] Deposited " + depositAmount + " steaks, giving " + pointAmount + " food!");
						for(OfflinePlayer teamMate : playerTeam.team.getPlayers())
						{
							Player tp = (Player) teamMate;
							if(tp != p)
							{
								tp.sendMessage(ChatColor.YELLOW + "[Team Deposit] " + p.getDisplayName() + " deposited " + depositAmount + " steaks, giving " + pointAmount + " food!");
							}
						}
						p.getInventory().removeItem(depositStack);
						playerTeam.food += pointAmount;
						playerTeam.updateScoreboard();
					}
					
					// Raw Pork
					if(p.getEquipment().getItemInMainHand().getType() == Material.PORK)
					{
						ItemStack depositStack = p.getInventory().getItemInMainHand();
						int depositAmount = depositStack.getAmount();
						int pointAmount = depositAmount * 3;
						p.sendMessage(ChatColor.YELLOW + "[Deposit] Deposited " + depositAmount + " raw pork, giving " + pointAmount + " food!");
						for(OfflinePlayer teamMate : playerTeam.team.getPlayers())
						{
							Player tp = (Player) teamMate;
							if(tp != p)
							{
								tp.sendMessage(ChatColor.YELLOW + "[Team Deposit] " + p.getDisplayName() + " deposited " + depositAmount + " raw pork, giving " + pointAmount + " food!");
							}
						}
						p.getInventory().removeItem(depositStack);
						playerTeam.food += pointAmount;
						playerTeam.updateScoreboard();
					}

					// Porkchops
					if(p.getEquipment().getItemInMainHand().getType() == Material.GRILLED_PORK)
					{
						ItemStack depositStack = p.getInventory().getItemInMainHand();
						int depositAmount = depositStack.getAmount();
						int pointAmount = depositAmount * 10;
						p.sendMessage(ChatColor.YELLOW + "[Deposit] Deposited " + depositAmount + " porkchops, giving " + pointAmount + " food!");
						for(OfflinePlayer teamMate : playerTeam.team.getPlayers())
						{
							Player tp = (Player) teamMate;
							if(tp != p)
							{
								tp.sendMessage(ChatColor.YELLOW + "[Team Deposit] " + p.getDisplayName() + " deposited " + depositAmount + " porkchops, giving " + pointAmount + " food!");
							}	
						}
						p.getInventory().removeItem(depositStack);
						playerTeam.food += pointAmount;
						playerTeam.updateScoreboard();
					}
					
					// Raw Fish
					if(p.getEquipment().getItemInMainHand().getType() == Material.RAW_FISH)
					{
						ItemStack depositStack = p.getInventory().getItemInMainHand();
						int depositAmount = depositStack.getAmount();
						int pointAmount = depositAmount * 5;
						p.sendMessage(ChatColor.YELLOW + "[Deposit] Deposited " + depositAmount + " raw fish, giving " + pointAmount + " food!");
						for(OfflinePlayer teamMate : playerTeam.team.getPlayers())
						{
							Player tp = (Player) teamMate;
							if(tp != p)
							{
								tp.sendMessage(ChatColor.YELLOW + "[Team Deposit] " + p.getDisplayName() + " deposited " + depositAmount + " raw fish, giving " + pointAmount + " food!");
							}
						}
						p.getInventory().removeItem(depositStack);
						playerTeam.food += pointAmount;
						playerTeam.updateScoreboard();
					}

					// Cooked fish
					if(p.getEquipment().getItemInMainHand().getType() == Material.COOKED_FISH)
					{
						ItemStack depositStack = p.getInventory().getItemInMainHand();
						int depositAmount = depositStack.getAmount();
						int pointAmount = depositAmount * 15;
						p.sendMessage(ChatColor.YELLOW + "[Deposit] Deposited " + depositAmount + " cooked fish, giving " + pointAmount + " food!");
						for(OfflinePlayer teamMate : playerTeam.team.getPlayers())
						{
							Player tp = (Player) teamMate;
							if(tp != p)
							{
								tp.sendMessage(ChatColor.YELLOW + "[Team Deposit] " + p.getDisplayName() + " deposited " + depositAmount + " cooked fish, giving " + pointAmount + " food!");
							}
						}
						p.getInventory().removeItem(depositStack);
						playerTeam.food += pointAmount;
						playerTeam.updateScoreboard();
					}
				}
				
				// Deposit Iron
				if(ChatColor.stripColor(s.getLine(1)).equals("Deposit") && ChatColor.stripColor(s.getLine(2)).equals("Iron"))
				{
					/*s.setLine(0, ChatColor.GOLD + "-[]-[]-[]-[]-");
					s.setLine(1, ChatColor.LIGHT_PURPLE + "§lDeposit");
					s.setLine(2, ChatColor.LIGHT_PURPLE + "§lIron");
					s.setLine(3, ChatColor.GOLD + "-[]-[]-[]-[]-");
					s.update();*/
					
					// Iron Ingots
					if(p.getEquipment().getItemInMainHand().getType() == Material.IRON_INGOT)
					{
						ItemStack depositStack = p.getInventory().getItemInMainHand();
						int depositAmount = depositStack.getAmount();
						int pointAmount = depositAmount;
						p.sendMessage(ChatColor.YELLOW + "[Deposit] Deposited " + depositAmount + " iron ingots, giving " + pointAmount + " iron!");
						for(OfflinePlayer teamMate : playerTeam.team.getPlayers())
						{
							Player tp = (Player) teamMate;
							if(tp != p)
							{
								tp.sendMessage(ChatColor.YELLOW + "[Team Deposit] " + p.getDisplayName() + " deposited " + depositAmount + " iron ingots, giving " + pointAmount + " iron!");
							}
						}
						p.getInventory().removeItem(depositStack);
						playerTeam.iron += pointAmount;
						playerTeam.updateScoreboard();
					}
					
					// Iron Blocks
					if(p.getEquipment().getItemInMainHand().getType() == Material.IRON_BLOCK)
					{
						ItemStack depositStack = p.getInventory().getItemInMainHand();
						int depositAmount = depositStack.getAmount();
						int pointAmount = depositAmount * 12;
						p.sendMessage(ChatColor.YELLOW + "[Deposit] Deposited " + depositAmount + " iron blocks, giving " + pointAmount + " iron!");
						for(OfflinePlayer teamMate : playerTeam.team.getPlayers())
						{
							Player tp = (Player) teamMate;
							if(tp != p)
							{
								tp.sendMessage(ChatColor.YELLOW + "[Team Deposit] " + p.getDisplayName() + " deposited " + depositAmount + " iron blocks, giving " + pointAmount + " iron!");
							}
						}
						p.getInventory().removeItem(depositStack);
						playerTeam.iron += pointAmount;
						playerTeam.updateScoreboard();
					}
				}
			}
		}
	}
	
	// Stop players from crafting iron tools/armor
	@EventHandler public void onIronCraft(CraftItemEvent event)
	{
		ItemStack result = event.getRecipe().getResult();
		if(result.getType() == Material.IRON_SWORD || result.getType() == Material.IRON_AXE || result.getType() == Material.IRON_SPADE || result.getType() == Material.BUCKET || result.getType() == Material.IRON_HELMET || result.getType() == Material.IRON_CHESTPLATE || result.getType() == Material.IRON_LEGGINGS || result.getType() == Material.IRON_BOOTS)
		{
			if(event.getWhoClicked() instanceof Player)
			{
				Player p = (Player)event.getWhoClicked();
				p.sendMessage(ChatColor.RED + "You cannot craft this item!");
			}
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event)
	{
		if(event.getBlock().getTypeId() == 59 || event.getBlock().getTypeId() == 60)
		{
			return;
		}
		event.setCancelled(true);
	}
	

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		// Cancel event and get the player that is breaking the block
		if(event.getBlock().getType() == Material.LONG_GRASS || event.getBlock().getTypeId() == 59)
		{
			return;
		}
		event.setCancelled(true);
		Player p = event.getPlayer();
		
		// Handles player cutting wood
		// 50% probability of gathering wood
		if(event.getBlock().getType() == Material.LOG && event.getBlock().getData() == 0)
		{
			double breakNum = Math.random();
			if(breakNum <= 0.5)
			{
				ItemStack clay = new ItemStack(Material.LOG);
				clay.setAmount(1);
				p.getInventory().addItem(clay);
				p.sendMessage(ChatColor.GREEN + "[Success] You attempted cut wood and succeeded!");
			}
			else
			{
				p.sendMessage(ChatColor.RED + "[Failure] You attempted to cut wood and failed!");
			}
		}
		
		// Handles player mining materials
		// Based on this probability table
		/*
		 * Clay: 60%
		 * Coal: 45%
		 * Iron: 40%
		 * Gold: 35%
		 * Emerald: 25%
		 */
		if(event.getBlock().getType() == Material.CLAY)
		{
			double breakNum = Math.random();
			if(breakNum <= 0.6)
			{
				ItemStack clay = new ItemStack(Material.CLAY_BALL);
				clay.setAmount(1);
				p.getInventory().addItem(clay);
				p.sendMessage(ChatColor.GREEN + "[Success] You attempted to mine clay and succeeded!");
			}
			else
			{
				p.sendMessage(ChatColor.RED + "[Failure] You attempted to mine clay and failed!");
			}
		}
		if(event.getBlock().getType() == Material.COAL_ORE && (p.getEquipment().getItemInMainHand().getType() == Material.WOOD_PICKAXE || p.getEquipment().getItemInMainHand().getType() == Material.STONE_PICKAXE || p.getEquipment().getItemInMainHand().getType() == Material.IRON_PICKAXE || p.getEquipment().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE))
		{
			double breakNum = Math.random();
			if(breakNum <= 0.45)
			{
				ItemStack coal = new ItemStack(Material.COAL);
				coal.setAmount(1);
				event.getPlayer().getInventory().addItem(coal);
				p.sendMessage(ChatColor.GREEN + "[Success] You attempted to mine coal and succeeded!");
			}
			else
			{
				p.sendMessage(ChatColor.RED + "[Failure] You attempted to mine coal and failed!");
			}
		}
		if(event.getBlock().getType() == Material.IRON_ORE && (p.getEquipment().getItemInMainHand().getType() == Material.WOOD_PICKAXE || p.getEquipment().getItemInMainHand().getType() == Material.STONE_PICKAXE || p.getEquipment().getItemInMainHand().getType() == Material.IRON_PICKAXE || p.getEquipment().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE))
		{
			double breakNum = Math.random();
			if(breakNum <= 0.40)
			{
				ItemStack iron = new ItemStack(Material.IRON_ORE);
				iron.setAmount(1);
				event.getPlayer().getInventory().addItem(iron);
				p.sendMessage(ChatColor.GREEN + "[Success] You attempted to mine iron and succeeded!");
			}
			else
			{
				p.sendMessage(ChatColor.RED + "[Failure] You attempted to mine iron and failed!");
			}
		}
		if(event.getBlock().getType() == Material.GOLD_ORE && (p.getEquipment().getItemInMainHand().getType() == Material.STONE_PICKAXE || p.getEquipment().getItemInMainHand().getType() == Material.IRON_PICKAXE || p.getEquipment().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE))
		{
			double breakNum = Math.random();
			if(breakNum <= 0.35)
			{
				ItemStack coal = new ItemStack(Material.GOLD_ORE);
				coal.setAmount(1);
				event.getPlayer().getInventory().addItem(coal);
				p.sendMessage(ChatColor.GREEN + "[Success] You attempted to mine gold and succeeded!");
			}
			else
			{
				p.sendMessage(ChatColor.RED + "[Failure] You attempted to mine gold and failed!");
			}
		}
		if(event.getBlock().getType() == Material.EMERALD_ORE && (p.getEquipment().getItemInMainHand().getType() == Material.IRON_PICKAXE || p.getEquipment().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE))
		{
			double breakNum = Math.random();
			if(breakNum <= 0.25)
			{
				ItemStack coal = new ItemStack(Material.EMERALD_ORE);
				coal.setAmount(1);
				event.getPlayer().getInventory().addItem(coal);
				p.sendMessage(ChatColor.GREEN + "[Success] You attempted to mine an emerald and succeeded!");
			}
			else
			{
				p.sendMessage(ChatColor.RED + "[Failure] You attempted to mine an emerald and failed!");
			}
		}
	}
	
	public EmpiresListener(Empires emp)
	{
		empires = emp;
		setClasses();
	}
}
