package com.themantlemc.clayMix;

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
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor.EntityType;

import io.puharesource.mc.titlemanager.api.TitleObject;
import io.puharesource.mc.titlemanager.api.TitleObject.TitleType;

public class ClayMixListener implements Listener {
	
	ClayMix claymix;
	
	public ClayMixListener(ClayMix clay)
	{
		claymix = clay;
	}
	
	// Cancel Item Pickups
	@EventHandler
	public void cancelItemPickup(PlayerPickupItemEvent event)
	{
		event.setCancelled(true);
	}
	
	// Cancel Item Drops
	@EventHandler
	public void cancelItemDrop(PlayerDropItemEvent event)
	{
		event.setCancelled(true);
	}
	
	// Disable movement in startup
	@EventHandler
	public void disableMovement(PlayerMoveEvent event)
	{
		if(claymix.gameStartup)
		{
			event.setCancelled(true);
			//event.getPlayer().setVelocity(new Vector(0.0f, 0.0f, 0.0f));
		}
	}
	
	// Disable damage in startup
	@EventHandler
	public void disableStartupDamage(EntityDamageByEntityEvent event)
	{
		if(claymix.gameStartup)
		{
			event.setCancelled(true);
		}
	}
	
	// Block placing
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event)
	{
		if(!claymix.gameRunning)
		{
			event.setCancelled(true);
			return;
		}
		Block placed = event.getBlockPlaced();
		if(placed.getType() != Material.GOLD_PLATE && placed.getType() != Material.TNT)
		{
			event.setCancelled(true);
			return;
		}
		if(placed.getType() == Material.GOLD_PLATE)
		{
			placed.setMetadata("Placer", new FixedMetadataValue(claymix, event.getPlayer().getName()));
		}
		if(placed.getType() == Material.TNT)
		{
			placed.getWorld().spawn(placed.getLocation(), TNTPrimed.class);
			placed.setType(Material.AIR);
		}
	}
	
	// Powerhit
	@EventHandler
	public void powerHit(EntityDamageByEntityEvent event)
	{
		if(claymix.gameRunning)
		{
			if(!(event.getDamager() instanceof Player))
			{
				return;
			}
			if(!(event.getEntity() instanceof Player))
			{
				return;
			}
			Player hit = (Player)event.getEntity();
			Player hitter = (Player)event.getDamager();
			ItemStack weapon = hitter.getItemInHand();
			if(!weapon.hasItemMeta())
			{
				return;
			}
			if(ChatColor.stripColor(weapon.getItemMeta().getDisplayName()).equals("Powerhit") || ChatColor.stripColor(weapon.getItemMeta().getDisplayName()).equals("Hard Hit"))
			{
				hitter.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 160, 2));
				claymix.playerDie(hit, hitter);
			}
		}
	}
	
	// Using Powerups
	@EventHandler
	public void powerupUse(PlayerInteractEvent event)
	{
		if(!claymix.gameRunning)
		{
			return;
		}
		if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
		{
			// Player left clicks with bow
			if(event.getPlayer().getItemInHand().getType() == Material.BOW && (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))
			{
				Player p = event.getPlayer();
				if(p.getInventory().contains(Material.FIREWORK_CHARGE))
				{
					Projectile proj = p.launchProjectile(EnderPearl.class);
					Item gren = proj.getWorld().dropItem(proj.getLocation(), new ItemStack(Material.FIREWORK_CHARGE));
					proj.setVelocity(proj.getVelocity().multiply(.75));
					gren.setVelocity(proj.getVelocity());
					proj.remove();
					
					// Remove item
					for(int i = 0; i < p.getInventory().getSize(); i++)
					{
						ItemStack checkItem = p.getInventory().getItem(i);
						if(checkItem.getType() == Material.FIREWORK_CHARGE)
						{
							if(checkItem.getAmount() > 1)
							{
								checkItem.setAmount(checkItem.getAmount()-1);
							}
							else
							{
								p.getInventory().remove(checkItem);
							}
							break;
						}
					}
					
					// Explosion after 3 seconds
					claymix.scheduler.scheduleSyncDelayedTask(claymix, new Runnable()
					{
						@Override
						public void run() {
							for(int i = 0; i < 12; i++)
							{
								Location explosionLocation = new Location(gren.getWorld(), gren.getLocation().getX() + Math.floor(Math.random() * 7) - 3, gren.getLocation().getY() + Math.floor(Math.random() * 7) - 3, gren.getLocation().getZ() + Math.floor(Math.random() * 7) - 3);
								explosionLocation.getWorld().createExplosion(explosionLocation, 0.0f);
							}
										
							for(Player hitPlayer: Bukkit.getOnlinePlayers())
							{
								if(hitPlayer == p)
								{
									continue;
								}
								else
								{
									if(hitPlayer.getLocation().distance(gren.getLocation()) < 5)
									{
										if(hitPlayer.getHealth() - 8 <= 0)
										{
											claymix.playerDie(hitPlayer, p);
										}
										else
										{
											hitPlayer.damage(8);
										}
									}
								}
							}
							gren.remove();
						}
					}, 60L);
				}
			}
			return;	
		}
		ItemStack usedItem = event.getItem();
		if(usedItem == null)
		{
			return;
		}
		if(!usedItem.hasItemMeta())
		{
			return;
		}
		if(!usedItem.getItemMeta().getLore().contains(ChatColor.LIGHT_PURPLE + "Powerup"))
		{
			return;
		}
		
		// Player is using a powerup
		String powerupName = ChatColor.stripColor(usedItem.getItemMeta().getDisplayName());
		Player p = event.getPlayer();
		
		//ItemStack removeItem;
		
		switch(powerupName)
		{
			// Create leaves around the player for 5 seconds
			case "Hide":
				p.sendMessage(ChatColor.GOLD + "You have used the Hide powerup to create a barrier of leaves around you!");
				List<Location> leafLocations = new ArrayList<Location>();
				
				// Ceiling
				for(int x = -1; x <= 1; x++)
				{
					for(int z = -1; z <= 1; z++)
					{
						leafLocations.add(p.getLocation().add(new Vector(x, 2, z)));
					}
				}
				
				// Sides
				leafLocations.add(p.getLocation().add(new Vector(-1, 0, 2)));
				leafLocations.add(p.getLocation().add(new Vector(0, 0, 2)));
				leafLocations.add(p.getLocation().add(new Vector(1, 0, 2)));
				leafLocations.add(p.getLocation().add(new Vector(-1, 1, 2)));
				leafLocations.add(p.getLocation().add(new Vector(0, 1, 2)));
				leafLocations.add(p.getLocation().add(new Vector(1, 1, 2)));
				
				leafLocations.add(p.getLocation().add(new Vector(-1, 0, -2)));
				leafLocations.add(p.getLocation().add(new Vector(0, 0, -2)));
				leafLocations.add(p.getLocation().add(new Vector(1, 0, -2)));
				leafLocations.add(p.getLocation().add(new Vector(-1, 1, -2)));
				leafLocations.add(p.getLocation().add(new Vector(0, 1, -2)));
				leafLocations.add(p.getLocation().add(new Vector(1, 1, -2)));
				
				leafLocations.add(p.getLocation().add(new Vector(2, 0, -1)));
				leafLocations.add(p.getLocation().add(new Vector(2, 0, 0)));
				leafLocations.add(p.getLocation().add(new Vector(2, 0, 1)));
				leafLocations.add(p.getLocation().add(new Vector(2, 1, -1)));
				leafLocations.add(p.getLocation().add(new Vector(2, 1, 0)));
				leafLocations.add(p.getLocation().add(new Vector(2, 1, 1)));

				leafLocations.add(p.getLocation().add(new Vector(-2, 0, -1)));
				leafLocations.add(p.getLocation().add(new Vector(-2, 0, 0)));
				leafLocations.add(p.getLocation().add(new Vector(-2, 0, 1)));
				leafLocations.add(p.getLocation().add(new Vector(-2, 1, -1)));
				leafLocations.add(p.getLocation().add(new Vector(-2, 1, 0)));
				leafLocations.add(p.getLocation().add(new Vector(-2, 1, 1)));
				
				for(Location l : leafLocations)
				{
					Material original = l.getBlock().getType();
					byte originalData = l.getBlock().getData();
					l.getBlock().setType(Material.LEAVES);
					
					// Remove leaves after 5 seconds
					claymix.scheduler.scheduleSyncDelayedTask(claymix, new Runnable()
					{
						@Override
						public void run() {
							l.getBlock().setType(original);
							l.getBlock().setData(originalData);
						}	
					}, 100L);
				}

				if(p.getItemInHand().getAmount() > 1)
				{
					p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
				}
				else
				{
					p.getInventory().remove(p.getInventory().getItemInHand());
				}
				p.updateInventory();
				break;
				
			case "Thief":
				p.sendMessage(ChatColor.GOLD + "You have used the Thief powerup to remove 1 arrow from everyone else!");
				ItemStack arrowStack = new ItemStack(Material.ARROW);
				arrowStack.setAmount(1);
				for(Player otherPlayer : Bukkit.getOnlinePlayers())
				{
					if(p == otherPlayer)
					{
						continue;
					}
					if(otherPlayer.getInventory().contains(Material.ARROW))
					{
						otherPlayer.sendMessage(p.getDisplayName() + ChatColor.RED + " has used the Thief powerup and removed one of your arrows!");
						otherPlayer.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.ARROW, 1) });
					}
				}
				
				p.getInventory().remove(p.getInventory().getItemInHand());
				p.updateInventory();
				break;
				
			case "Jump":
				event.setCancelled(true);
				p.sendMessage(ChatColor.GOLD + "You have used the Jump powerup to boost yourself into the air!");
				claymix.scheduler.scheduleSyncDelayedTask(claymix, new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						p.setVelocity(p.getVelocity().add(new Vector(0, 1.8, 0)));
					}
				}, 1L);
				if(p.getItemInHand().getAmount() > 1)
				{
					p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
				}
				else
				{
					p.getInventory().remove(p.getInventory().getItemInHand());
				}
				break;
				
			case "Splash":
				p.sendMessage(ChatColor.GOLD + "You have used the Splash powerup, degrading blocks around you!");
				for(int x = -2; x <= 2; x++)
				{
					for(int z = -2; z <= 2; z++)
					{
						if(x == 0 && z == 0)
						{
							continue;
						}
						claymix.degradeBlock(p.getWorld().getBlockAt(p.getLocation().add(new Vector(x, -1, z))));
					}
				}
				
				p.getInventory().remove(p.getInventory().getItemInHand());
				p.updateInventory();
				break;
				
			case "Decay":
				Bukkit.broadcastMessage(p.getDisplayName() + ChatColor.GOLD + " has used the Decay powerup, instantly decaying 30% of the map!");
				int numBlocks = (int)((claymix.mapBlocksList.get(claymix.map - 1).size() / 10.0f) * 3);
				for(int i = 0; i < numBlocks; i++)
				{
					boolean changed;
					if(claymix.mapBlocksList.get(claymix.map - 1).size() > 0)
					{
						do
						{
							changed = true;
							Block changeBlock = claymix.mapBlocksList.get(claymix.map - 1).get((int)Math.floor((Math.random() * claymix.mapBlocksList.get(claymix.map - 1).size())));
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
									claymix.mapBlocksList.get(claymix.map - 1).remove(changeBlock);
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
				
				if(p.getItemInHand().getAmount() > 1)
				{
					p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
				}
				else
				{
					p.getInventory().remove(p.getInventory().getItemInHand());
				}
				p.updateInventory();
				break;
				
			case "Glue":
				p.sendMessage(ChatColor.GOLD + "You have used the Glue powerup, disabling everyone else's sprint for 5 seconds!");
				for(Player otherPlayer : Bukkit.getOnlinePlayers())
				{
					if(p == otherPlayer)
					{
						continue;
					}
					if(otherPlayer.getGameMode() == GameMode.SPECTATOR)
					{
						continue;
					}
					otherPlayer.sendMessage(p.getDisplayName() + ChatColor.RED + " has used the Glue powerup and disabled your sprint for 5 seconds!");
					otherPlayer.setVelocity(new Vector(0, 0, 0));
					otherPlayer.setFoodLevel(3);
					
					claymix.scheduler.scheduleSyncDelayedTask(claymix, new Runnable(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							otherPlayer.setFoodLevel(20);
						}
					}, 100L);
				}
				
				if(p.getItemInHand().getAmount() > 1)
				{
					p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
				}
				else
				{
					p.getInventory().remove(p.getInventory().getItemInHand());
				}
				p.updateInventory();
				break;
				
			case "Blackout":
				event.setCancelled(true);
				p.sendMessage(ChatColor.GOLD + "You have used the Blackout powerup, giving everyone else blindness for 5 seconds!");
				for(Player otherPlayer : Bukkit.getOnlinePlayers())
				{
					if(p == otherPlayer)
					{
						continue;
					}
					if(otherPlayer.getGameMode() == GameMode.SPECTATOR)
					{
						continue;
					}
					otherPlayer.sendMessage(p.getDisplayName() + ChatColor.RED + " has used the Blackout powerup, giving you blindness for 5 seconds!");
					otherPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));
				}
				
				if(p.getItemInHand().getAmount() > 1)
				{
					p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
				}
				else
				{
					p.getInventory().remove(p.getInventory().getItemInHand());
				}
				p.updateInventory();
				break;
				
			case "Smite":
				Player killedPlayer = null;
				do
				{
					int playerNum = (int)Math.floor((Math.random() * Bukkit.getOnlinePlayers().size()));
					killedPlayer = (Player)Bukkit.getOnlinePlayers().toArray()[playerNum];
				} while(killedPlayer == p && killedPlayer.getGameMode() != GameMode.SPECTATOR);
				
				p.sendMessage(ChatColor.GOLD + "You have smited " + killedPlayer.getName() + "!");
				killedPlayer.sendMessage(ChatColor.RED + "You have been smited by " + p.getName() + "!");
				killedPlayer.getWorld().strikeLightningEffect(killedPlayer.getLocation());
				claymix.playerDie(killedPlayer, p);
				
				if(p.getItemInHand().getAmount() > 1)
				{
					p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
				}
				else
				{
					p.getInventory().remove(p.getInventory().getItemInHand());
				}
				p.updateInventory();
				break;
		}
	}
	
	// Getting Powerups
	@EventHandler
	public void powerupHit(EntityDamageByEntityEvent event)
	{
		if(claymix.gameRunning)
		{
			if(event.getEntity() instanceof EnderCrystal)
			{
				event.setCancelled(true);
				if(event.getDamager() instanceof Player)
				{
					Player p = (Player)event.getDamager();
					EnderCrystal crystal = (EnderCrystal)event.getEntity();
					p.sendMessage(ChatColor.GOLD + "You have picked up a powerup!");
					crystal.remove();
					int powerupNum = (int)Math.floor(Math.random() * 5);
					switch(claymix.game)
					{
						// OITQ
						case 1:
							if(powerupNum == 0)
							{
								ItemStack hideStack = new ItemStack(Material.LEAVES);
								ItemMeta hideMeta = hideStack.getItemMeta();
								hideMeta.setDisplayName(ChatColor.GOLD + "Hide");
								hideMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Powerup", ChatColor.AQUA + "Right click to make a barrier", ChatColor.AQUA + "of leaves around you for 5", ChatColor.AQUA + "seconds!")));
								hideStack.setItemMeta(hideMeta);
								p.getInventory().addItem(hideStack);
							}
							if(powerupNum == 1)
							{
								ItemStack powerhitStack = new ItemStack(Material.DIAMOND_SWORD);
								powerhitStack.setDurability((short)1561);
								ItemMeta powerhitMeta = powerhitStack.getItemMeta();
								powerhitMeta.setDisplayName(ChatColor.GOLD + "Powerhit");
								powerhitMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
								powerhitMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Powerup", ChatColor.AQUA + "One use Diamond Sword that", ChatColor.AQUA + "instakills, but gives you nausea!")));
								powerhitStack.setItemMeta(powerhitMeta);
								p.getInventory().addItem(powerhitStack);
							}
							if(powerupNum == 2)
							{
								p.sendMessage(ChatColor.GOLD + "Reload: " + ChatColor.AQUA + "Gain 2 extra arrows!");
								p.getInventory().addItem(new ItemStack(Material.ARROW, 2));
							}
							if(powerupNum == 3)
							{
								ItemStack thiefStack = new ItemStack(Material.FISHING_ROD);
								ItemMeta thiefMeta = thiefStack.getItemMeta();
								thiefMeta.setDisplayName(ChatColor.GOLD + "Thief");
								thiefMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Powerup", ChatColor.AQUA + "Removes one arrow from", ChatColor.AQUA + "everyone else!")));
								thiefStack.setItemMeta(thiefMeta);
								p.getInventory().addItem(thiefStack);
							}
							if(powerupNum == 4)
							{
								ItemStack grenadeStack = new ItemStack(Material.FIREWORK_CHARGE);
								ItemMeta grenadeMeta = grenadeStack.getItemMeta();
								grenadeMeta.setDisplayName(ChatColor.GOLD + "Grenade");
								grenadeMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Powerup", ChatColor.AQUA + "A grenade that will explode", ChatColor.AQUA + "after one second!")));
								grenadeStack.setItemMeta(grenadeMeta);
								p.getInventory().addItem(grenadeStack);
							}
							break;
						// Melt
						case 2:
							if(powerupNum == 0)
							{
								ItemStack jumpStack = new ItemStack(Material.DIAMOND_BOOTS);
								ItemMeta jumpMeta = jumpStack.getItemMeta();
								jumpMeta.setDisplayName(ChatColor.GOLD + "Jump");
								jumpMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Powerup", ChatColor.AQUA + "Right click to fling yourself", ChatColor.AQUA + "into the air!")));
								jumpStack.setItemMeta(jumpMeta);
								p.getInventory().addItem(jumpStack);
							}
							if(powerupNum == 1)
							{
								ItemStack splashStack = new ItemStack(Material.WATER_BUCKET);
								ItemMeta splashMeta = splashStack.getItemMeta();
								splashMeta.setDisplayName(ChatColor.GOLD + "Splash");
								splashMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Powerup", ChatColor.AQUA + "Turn all blocks around you", ChatColor.AQUA + "into glass!")));
								splashStack.setItemMeta(splashMeta);
								p.getInventory().addItem(splashStack);
							}
							if(powerupNum == 2)
							{
								ItemStack decayStack = new ItemStack(Material.WEB);
								ItemMeta decayMeta = decayStack.getItemMeta();
								decayMeta.setDisplayName(ChatColor.GOLD + "Decay");
								decayMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Powerup", ChatColor.AQUA + "Instantly decay 30% of the blocks", ChatColor.AQUA + "on the map!")));
								decayStack.setItemMeta(decayMeta);
								p.getInventory().addItem(decayStack);
							}
							if(powerupNum == 3)
							{
								p.sendMessage(ChatColor.GOLD + "Heavy: " + ChatColor.AQUA + "Blocks beneath you will decay every second for 20 seconds!");
								PlayerInfo info = claymix.getPlayerInfo(p.getName());
								info.powerup = "Heavy";
								info.powerupTime = 20;
							}
							if(powerupNum == 4)
							{
								Bukkit.broadcastMessage(p.getDisplayName() + ChatColor.GOLD + " has picked up the Snowfight powerup, giving everyone 5 snowballs!");
								for(Player play : Bukkit.getOnlinePlayers())
								{
									if(play.getGameMode() != GameMode.SPECTATOR)
									{
										play.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 5));
									}
								}
							}
							break;
						// Timebomb
						case 3:
							if(powerupNum == 0)
							{
								Bukkit.broadcastMessage(p.getDisplayName() + ChatColor.GOLD + " has picked up the detonate powerup!");
								List<Player> killPlayers = new ArrayList<Player>();
								int killCheck = 0;
								do
								{
									for(Player checkPlayer : Bukkit.getOnlinePlayers())
									{
										if(checkPlayer.getGameMode() == GameMode.SPECTATOR)
										{
											continue;
										}
										if(claymix.getPlayerInfo(checkPlayer.getName()).kills == killCheck)
										{
											killPlayers.add(checkPlayer);
										}
									}
									killCheck++;
								} while(killPlayers.size() == 0);
								
								// Kill a player from the killPlayers list
								int killNum = (int)Math.floor((Math.random() * killPlayers.size()));
								claymix.timebombKill(killPlayers.get(killNum));
								claymix.timebombTimer = 10;
							}
							if(powerupNum == 1)
							{
								p.sendMessage(ChatColor.GOLD + "Cha-Ching: " + ChatColor.AQUA + "Adds 2 points to your score!");
								claymix.getPlayerInfo(p.getName()).kills += 2;
								claymix.updateScoreboard();
							}
							if(powerupNum == 2)
							{
								// Smite
								ItemStack smiteStack = new ItemStack(Material.BLAZE_ROD);
								ItemMeta smiteMeta = smiteStack.getItemMeta();
								smiteMeta.setDisplayName(ChatColor.GOLD + "Smite");
								smiteMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Powerup", ChatColor.AQUA + "Randomly kills 1 player, giving", ChatColor.AQUA + "a point to you!")));
								smiteStack.setItemMeta(smiteMeta);
								p.getInventory().addItem(smiteStack);
							}
							if(powerupNum == 3)
							{
								// Hardhit
								ItemStack powerhitStack = new ItemStack(Material.GOLD_SWORD);
								powerhitStack.setDurability((short)32);
								ItemMeta powerhitMeta = powerhitStack.getItemMeta();
								powerhitMeta.setDisplayName(ChatColor.GOLD + "Hard Hit");
								powerhitMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
								powerhitMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Powerup", ChatColor.AQUA + "One use Gold Sword that", ChatColor.AQUA + "instakills, but gives you nausea!")));
								powerhitStack.setItemMeta(powerhitMeta);
								p.getInventory().addItem(powerhitStack);
							}
							if(powerupNum == 4)
							{
								// Landmine
								ItemStack landmineStack = new ItemStack(Material.GOLD_PLATE);
								ItemMeta landmineMeta = landmineStack.getItemMeta();
								landmineMeta.setDisplayName(ChatColor.GOLD + "Landmine");
								landmineMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Powerup", ChatColor.AQUA + "Landmine that will kill any", ChatColor.AQUA + "player that walks over it!")));
								landmineStack.setItemMeta(landmineMeta);
								p.getInventory().addItem(landmineStack);
							}
							break;
						// Runner
						case 4:
							if(powerupNum == 0)
							{
								ItemStack jumpStack = new ItemStack(Material.DIAMOND_BOOTS);
								ItemMeta jumpMeta = jumpStack.getItemMeta();
								jumpMeta.setDisplayName(ChatColor.GOLD + "Jump");
								jumpMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Powerup", ChatColor.AQUA + "Right click to fling yourself", ChatColor.AQUA + "into the air!")));
								jumpStack.setItemMeta(jumpMeta);
								p.getInventory().addItem(jumpStack);
							}
							if(powerupNum == 1)
							{
								Bukkit.broadcastMessage(p.getDisplayName() + ChatColor.GOLD + " has picked up the flash powerup, giving everyone Speed III for 5 seconds!");
								for(Player player : Bukkit.getOnlinePlayers())
								{
									player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2));
								}
							}
							if(powerupNum == 2)
							{
								p.sendMessage(ChatColor.GOLD + "Grow: " + ChatColor.AQUA + "Extends the range of blocks you will break for 10 seconds!");
								PlayerInfo info = claymix.getPlayerInfo(p.getName());
								info.powerup = "Grow";
								info.powerupTime = 10;
							}
							if(powerupNum == 3)
							{
								p.sendMessage(ChatColor.GOLD + "Lightfooted: " + ChatColor.AQUA + "You will no longer break blocks for 10 seconds!");
								PlayerInfo info = claymix.getPlayerInfo(p.getName());
								info.powerup = "Lightfooted";
								info.powerupTime = 10;
							}
							if(powerupNum == 4)
							{
								ItemStack glueStack = new ItemStack(Material.INK_SACK, 1, (byte)12);
								ItemMeta glueMeta = glueStack.getItemMeta();
								glueMeta.setDisplayName(ChatColor.GOLD + "Glue");
								glueMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Powerup", ChatColor.AQUA + "Disables everyone elses sprint", ChatColor.AQUA + "for 5 seconds!")));
								glueStack.setItemMeta(glueMeta);
								p.getInventory().addItem(glueStack);
							}
							break;
						// Spleef
						case 5:
							if(powerupNum == 0)
							{
								ItemStack jumpStack = new ItemStack(Material.DIAMOND_BOOTS);
								ItemMeta jumpMeta = jumpStack.getItemMeta();
								jumpMeta.setDisplayName(ChatColor.GOLD + "Jump");
								jumpMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Powerup", ChatColor.AQUA + "Right click to fling yourself", ChatColor.AQUA + "into the air!")));
								jumpStack.setItemMeta(jumpMeta);
								p.getInventory().addItem(jumpStack);
							}
							if(powerupNum == 1)
							{
								// Bomb
								ItemStack blackoutStack = new ItemStack(Material.TNT);
								ItemMeta blackoutMeta = blackoutStack.getItemMeta();
								blackoutMeta.setDisplayName(ChatColor.GOLD + "Bomb");
								blackoutMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Powerup", ChatColor.AQUA + "Places an ignited TNT!")));
								blackoutStack.setItemMeta(blackoutMeta);
								p.getInventory().addItem(blackoutStack);
							}
							if(powerupNum == 2)
							{
								// Blackout
								ItemStack blackoutStack = new ItemStack(Material.EYE_OF_ENDER);
								ItemMeta blackoutMeta = blackoutStack.getItemMeta();
								blackoutMeta.setDisplayName(ChatColor.GOLD + "Blackout");
								blackoutMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Powerup", ChatColor.AQUA + "Gives everyone else blindness", ChatColor.AQUA + "for 5 seconds!")));
								blackoutStack.setItemMeta(blackoutMeta);
								p.getInventory().addItem(blackoutStack);
							}
							if(powerupNum == 3)
							{
								// Panic!
								Bukkit.broadcastMessage(p.getDisplayName() + ChatColor.GOLD + " has picked up the Panic powerup, setting everyone's level to 3!");
								for(Player play : Bukkit.getOnlinePlayers())
								{
									play.setLevel(3);
									play.setExp(0.0f);
								}
							}
							if(powerupNum == 4)
							{
								Bukkit.broadcastMessage(p.getDisplayName() + ChatColor.GOLD + " has picked up the Snowfight powerup, giving everyone 5 snowballs!");
								for(Player play : Bukkit.getOnlinePlayers())
								{
									if(play.getGameMode() != GameMode.SPECTATOR)
									{
										play.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 5));
									}
								}
							}
							break;
					}
				}
			}
		}
	}
	
	// One In The Quiver Damage
	@EventHandler
	public void oitqDamage(EntityDamageByEntityEvent event)
	{
		if(claymix.game == 1 && claymix.gameRunning)
		{
			if(!(event.getEntity() instanceof Player))
			{
				return;
			}
			
			boolean killed = false;
			Player player = (Player)event.getEntity();
			
			// Handle Deaths
			if(event.getCause().equals(DamageCause.PROJECTILE))
			{
				if(event.getDamager() instanceof Arrow)
				{
					killed = true;
				}
			}
			
			if(player.getHealth() - event.getFinalDamage() <= 0)
			{
				killed = true;
			}
			
			if(killed)
			{
				Player killer = player.getKiller();
				if(killer == null)
				{
					if(event.getDamager() instanceof Player)
					{
						killer = (Player)event.getDamager();
					}
					if(event.getDamager() instanceof Projectile)
					{
						Projectile p = (Projectile)event.getDamager();
						killer = (Player)p.getShooter();
					}
					if(player.getLastDamageCause() instanceof Player)
					{
						if(killer == null)
						{
							killer = (Player)player.getLastDamageCause();
						}
					}
				}
				
				// Player died somehow? Not sure, but just have them die
				if(killer == null)
				{
					claymix.playerDie(player);
				}
				else
				{
					claymix.playerDie(player, killer);
				}
				event.setCancelled(true);
			}
		}
	}
	
	// Timebomb damage
	@EventHandler
	public void timebombDamage(EntityDamageByEntityEvent event)
	{
		if(claymix.game == 3 && claymix.gameRunning)
		{
			if(!(event.getEntity() instanceof Player))
			{
				return;
			}
			
			boolean killed = false;
			Player player = (Player)event.getEntity();
			
			// Handle Deaths
			if(player.getHealth() - event.getFinalDamage() <= 0)
			{
				killed = true;
			}
			
			if(killed)
			{
				Player killer = player.getKiller();
				if(killer == null)
				{
					if(event.getDamager() instanceof Player)
					{
						killer = (Player)event.getDamager();
					}
					if(player.getLastDamageCause() instanceof Player)
					{
						if(killer == null)
						{
							killer = (Player)player.getLastDamageCause();
						}
					}
				}
				
				// Player died somehow? Not sure, but just have them die
				if(killer == null)
				{
					claymix.playerDie(player);
				}
				else
				{
					claymix.playerDie(player, killer);
				}
				event.setCancelled(true);
			}
		}
	}
	
	// Spleef block break
	@EventHandler
	public void playerBreakBlock(PlayerInteractEvent event)
	{
		if(claymix.game == 5 && claymix.gameRunning)
		{
			if(event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.STONE_SPADE))
			{
				Player p = event.getPlayer();
				if(event.getAction() == (Action.LEFT_CLICK_BLOCK))
				{
					event.getClickedBlock().setType(Material.AIR);
					if(p.getLevel() < 20)
					{
						if(p.getExp() >= .7f)
						{
							p.giveExpLevels(1);
							p.setExp(0);
						}
						else
						{
							p.setExp(p.getExp() + 0.35f);
						}
					}
				}
			}
		}
	}
	
	// Cancel Player Damage in Spleef
	@EventHandler
	public void cancelSpleefDamage(EntityDamageByEntityEvent event)
	{
		if(claymix.game == 5 && claymix.gameRunning)
		{
			if(event.getDamager() instanceof Player)
			{
				event.setCancelled(true);
			}
		}
	}
	
	// Player death from falling
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		if(claymix.gameRunning)
		{
			Player p = event.getPlayer();
			if(p.getGameMode().equals(GameMode.ADVENTURE) || p.getGameMode().equals(GameMode.SURVIVAL))
			{
				if(p.getLocation().getY() <= 70)
				{
					Player killer = p.getKiller();
					if(killer == null)
					{
						if(p.getLastDamageCause() instanceof Player)
						{
							if(killer == null)
							{
								killer = (Player)p.getLastDamageCause();
							}
						}
					}
					
					// Player died somehow? Not sure, but just have them die
					if(killer == null)
					{
						claymix.playerDie(p);
					}
					else
					{
						claymix.playerDie(p, killer);
					}
				}
			}
		}
	}
}
