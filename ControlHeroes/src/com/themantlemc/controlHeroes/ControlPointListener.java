package com.themantlemc.controlHeroes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

public class ControlPointListener implements Listener {

	ControlPoint cp;
	List<ControlPointClass> classes;
	
	// Custom Chat
	@EventHandler
	public void onChat(PlayerChatEvent event)
	{
		Player p = event.getPlayer();
		PlayerInfo info = cp.getPlayerInfo(p.getName());
		Team playerTeam = null;
		String prestigeStr = "";
		
		if(event.getMessage().equalsIgnoreCase("unlock ranged"))
		{
			p.sendMessage(ChatColor.GOLD + "Ranged Unlocked!");
			info.unlockedPacks.add("Ranged");
		}
		if(event.getMessage().equalsIgnoreCase("unlock tank"))
		{
			p.sendMessage(ChatColor.GOLD + "Tank Unlocked!");
			info.unlockedPacks.add("Tank");
		}
		if(event.getMessage().equalsIgnoreCase("unlock stealthy"))
		{
			p.sendMessage(ChatColor.GOLD + "Stealthy Unlocked!");
			info.unlockedPacks.add("Stealthy");
		}
		if(event.getMessage().equalsIgnoreCase("unlock utility"))
		{
			p.sendMessage(ChatColor.GOLD + "Utility Unlocked!");
			info.unlockedPacks.add("Utility");
		}
		if(event.getMessage().equalsIgnoreCase("unlock agile"))
		{
			p.sendMessage(ChatColor.GOLD + "Agile Unlocked!");
			info.unlockedPacks.add("Agile");
		}
		if(event.getMessage().equalsIgnoreCase("unlock magic"))
		{
			p.sendMessage(ChatColor.GOLD + "Magic Unlocked!");
			info.unlockedPacks.add("Magic");
		}
		if(event.getMessage().equalsIgnoreCase("unlock support"))
		{
			p.sendMessage(ChatColor.GOLD + "Support Unlocked!");
			info.unlockedPacks.add("Support");
		}
		if(event.getMessage().equalsIgnoreCase("unlock fiery"))
		{
			p.sendMessage(ChatColor.GOLD + "Fiery Unlocked!");
			info.unlockedPacks.add("Fiery");
		}
		if(event.getMessage().equalsIgnoreCase("unlock poisonous"))
		{
			p.sendMessage(ChatColor.GOLD + "Poisonous Unlocked!");
			info.unlockedPacks.add("Poisonous");
		}
		
		ChatColor pColor = ChatColor.GRAY;
		if(info.rank.equals("Moderator") || info.rank.equals("Admin") || info.rank.equals("Owner"))
		{
			pColor = ChatColor.WHITE;
		}
		
		if(cp.teamManager.redTeam.hasPlayer(p))
		{
			playerTeam = cp.teamManager.redTeam;
		}
		else if(cp.teamManager.blueTeam.hasPlayer(p))
		{
			playerTeam = cp.teamManager.blueTeam;
		}
		else if(cp.teamManager.greenTeam.hasPlayer(p))
		{
			playerTeam = cp.teamManager.greenTeam;
		}
		else if(cp.teamManager.purpleTeam.hasPlayer(p))
		{
			playerTeam = cp.teamManager.purpleTeam;
		}
		
		ChatColor playerColor = ChatColor.GRAY;
		if(playerTeam != null)
		{
			switch(ChatColor.stripColor(cp.getPlayerTeam(p).getName()))
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
		}

		switch(cp.getPlayerInfo(p.getName()).prestige)
		{
			case 1: prestigeStr = playerColor + "[I] ";
				break;
			case 2: prestigeStr = playerColor + "[II] ";
				break;
			case 3: prestigeStr = playerColor + "[III] ";
				break;
			case 4: prestigeStr = playerColor + "[IV] ";
				break;
			case 5: prestigeStr = playerColor + "[V] ";
				break;
			case 6: prestigeStr = playerColor + "[VI] ";
				break;
			case 7: prestigeStr = playerColor + "[VII] ";
				break;
			case 8: prestigeStr = playerColor + "[VIII] ";
				break;
			case 9: prestigeStr = playerColor + "[IX] ";
				break;
			case 10: prestigeStr = playerColor + "[X] ";
				break;
		}
		
		for(Player globalPlayer : Bukkit.getOnlinePlayers())
		{
			globalPlayer.sendMessage(prestigeStr + p.getDisplayName() + pColor + ": " + event.getMessage());
		}
		
		if(event.getMessage().equalsIgnoreCase("hero"))
		{
			cp.updateHeroBar(p, 99);
		}
		event.setCancelled(true);
	}
	
	// Disable Lobby Damage
	@EventHandler
	public void lobbyDamage(EntityDamageEvent event)
	{
		if(event.getEntity().getWorld() == cp.lobby)
		{
			event.setCancelled(true);
		}
	}
	
	// Increased Hero Arrow Damage
	@EventHandler
	public void entityDamage(EntityDamageByEntityEvent event)
	{
		if(!(event.getEntity() instanceof Player))
		{
			return;
		}
		Player player = (Player)event.getEntity();
		
		if(event.getDamager() instanceof Player)
		{
			PlayerInfo playerInfo = cp.getPlayerInfo(player.getName());
			Player damager = (Player)event.getDamager();
			PlayerInfo damagerInfo = cp.getPlayerInfo(damager.getName());
			
			// Contagion
			if(damagerInfo.contagion)
			{
				if(!damagerInfo.contagionTeam.getPlayers().contains(player))
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 140, 0));
					playerInfo.contagion = true;
					playerInfo.contagionTeam = damagerInfo.contagionTeam;
				}
			}
			
			if(!damagerInfo.isHero && playerInfo.isHero)
			{
				// Check Godslayer perk
				if(damagerInfo.currentPerk.equals("Godslayer"))
				{
					if(cp.triggerGodslayer(damager))
					{
						event.setDamage(event.getDamage() * 2);
					}
				}
			}
			
			// Terminus Active
			if(playerInfo.currentHero.equals("Terminus") && playerInfo.heroActive)
			{
				Vector velocity = damager.getLocation().toVector().subtract(player.getLocation().toVector());
				damager.setVelocity(velocity.multiply(.75));
				ChatColor playerColor = null;
				switch(ChatColor.stripColor(cp.getPlayerTeam(player).getName()))
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
				damager.sendMessage(ChatColor.RED + "You are knocked away by " + playerColor + player.getName() + "'s" + ChatColor.GOLD + " Stone Morph!");
				
				if(damagerInfo.isHero == false)
				{
					if(damager.getHealth() - 4 <= 0)
					{
						cp.playerDie(damager, player);
					}
					else
					{
						damager.damage(4);
					}
				}
				else
				{
					if(damagerInfo.heroHealth - 4 <= 0)
					{
						if(damagerInfo.heroHealth > 0)
						{
							damager.damage(4 - damagerInfo.heroHealth);
							damagerInfo.heroHealth = 0;
						}
						if(damager.getHealth() - 4 <= 0)
						{
							cp.playerDie(damager, player);
						}
						else
						{
							damager.damage(4);
						}
					}
					else
					{
						damagerInfo.heroHealth -= 4;
					}
				}
				return;
			}
			
			// Fenrir Passive
			if(damagerInfo.currentHero.equals("Fenrir"))
			{
				if(Math.random() <= .15)
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 40, 1));
				}
			}
			
			// Khaos Passive
			if(damagerInfo.currentHero.equals("Khaos"))
			{
				if(Math.random() <= .08)
				{
					player.sendMessage(ChatColor.RED + "Your hotbar has been scrambled!");
					for(int i = 0; i < 9; i++)
					{
						int num1 = (int)(Math.random() * 9);
						int num2 = (int)(Math.random() * 9);
						ItemStack item1 = player.getInventory().getItem(num1);
						ItemStack item2 = player.getInventory().getItem(num2);
						player.getInventory().setItem(num1, item2);
						player.getInventory().setItem(num2, item1);
					}
				}
			}
			
			// Kronos Passive
			if (damagerInfo.currentHero.equals("Kronos"))
			{
				if(Math.random() <= .2)
				{
					if(Math.random() < .5)
					{
						player.sendMessage(ChatColor.GOLD + "You have been slowed due to Kronos's Time Magic!");
						if(player.hasPotionEffect(PotionEffectType.SLOW))
						{
							player.removePotionEffect(PotionEffectType.SLOW);
						}
						player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 1));
					}
					else
					{
						player.sendMessage(ChatColor.GOLD + "Your speed has been temporarily boosted due to Kronos's Time Magic!");
						if(player.hasPotionEffect(PotionEffectType.SPEED))
						{
							player.removePotionEffect(PotionEffectType.SPEED);
						}
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 4));
					}
				}
			}
			
			// Noiresse Passive
			if(damagerInfo.currentHero.equals("Noiresse"))
			{
				if(Math.random() <= .1)
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1));
				}
			}
			
			// Adrestia Passive
			/*if(damagerInfo.currentHero.equals("Adrestia"))
			{
				if(player.getActivePotionEffects().size() > 0)
				{
					// Remove current effects
					for(PotionEffect pE : damager.getActivePotionEffects())
					{
						if(!pE.getType().equals(PotionEffectType.ABSORPTION))
						{
							damager.removePotionEffect(pE.getType());
						}
					}
					
					// Add damaged players effects
					for(PotionEffect pE : player.getActivePotionEffects())
					{
						damager.addPotionEffect(new PotionEffect(pE.getType(), 60, pE.getAmplifier()));
					}
				}
			}*/
			
			// Azazel Passive
			if(damagerInfo.currentHero.equals("Azazel"))
			{
				// Check if this is a backstab
				if(player.getLocation().getDirection().dot(damager.getLocation().getDirection()) > 0)
				{
					ChatColor playerColor = null;
					switch(ChatColor.stripColor(cp.getPlayerTeam(player).getName()))
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
					// Backstab
					damager.sendMessage(ChatColor.GOLD + "You have backstabbed " + playerColor + player.getName() + ChatColor.GOLD + " and dealt extra damage!");
					event.setDamage(event.getDamage() * 1.25);
					if(Math.random() <= .35)
					{
						player.setFireTicks(80);
						damager.sendMessage(ChatColor.GOLD + "Your backstab has set " + playerColor + player.getName() + ChatColor.GOLD + " on fire!");
					}
					if(player.getHealth() - event.getFinalDamage() <= 0)
					{
						cp.playerDie(player, damager);
					}
				}
			}
		}
		
		if(event.getDamager() instanceof Arrow)
		{
			Arrow a = (Arrow)event.getDamager();
			
			if(cp.archerArrows.contains(a))
			{
				event.setDamage(event.getDamage() * 1.2);
				cp.archerArrows.remove(a);
			}
			else if(cp.tracerArrows.contains(a))
			{
				cp.tracerArrows.remove(a);
				event.setDamage(event.getDamage() * 0.50);
				if(player.getHealth() - event.getFinalDamage() <= 0)
				{
					cp.playerDie(player, (Player)a.getShooter());
				}
			}
			else
			{
				Player shooter = (Player)a.getShooter();
				PlayerInfo shooterInfo = cp.getPlayerInfo(shooter.getName());
				if(shooterInfo.isHero == false && cp.getPlayerInfo(player.getName()).isHero)
				{
					if(shooterInfo.currentPerk.equals("Godslayer"))
					{
						if(cp.triggerGodslayer(shooter))
						{
							event.setDamage(event.getDamage() * 2);
						}
					}
				}
				
				// Achlys Passive
				if(cp.getPlayerInfo(shooter.getName()).currentHero.equals("Achlys") && !cp.getPlayerTeam(shooter).getName().equals(cp.getPlayerTeam(player).getName()))
				{
					if(Math.random() <= .25)
					{
						player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 1));
						shooter.sendMessage(ChatColor.GOLD + "Your arrow has poisoned your target!");
					}
				}
			}
		}
		
		if(event.getDamager() instanceof Fireball)
		{
			Fireball f = (Fireball)event.getDamager();
			Player shooter = (Player)f.getShooter();
			PlayerInfo shooterInfo = cp.getPlayerInfo(shooter.getName());
			if(shooterInfo.isHero == false && cp.getPlayerInfo(player.getName()).isHero)
			{
				if(shooterInfo.currentPerk.equals("Godslayer"))
				{
					if(cp.triggerGodslayer(shooter))
					{
						event.setDamage(event.getDamage() * 2);
					}
				}
			}
		}
	}
	
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event)
	{
		if(!(event.getEntity() instanceof Player))
		{
			return;
		}
		
		// Cancel Fall Damage
		if(event.getCause().equals(DamageCause.FALL))
		{
			event.setCancelled(true);
			return;
		}
		
		// Cancel Protect
		if(event.getEntity() instanceof Player)
		{
			Player p = (Player)event.getEntity();
			if(cp.getPlayerInfo(p.getName()).protectTimer != 0)
			{
				event.setCancelled(true);
				return;
			}
		}
		
		Player player = (Player)event.getEntity();
		PlayerInfo info = cp.getPlayerInfo(player.getName());
		
		// Terminus Active
		if(info.currentHero.equals("Terminus") && info.heroActive)
		{
			event.setCancelled(true);
			return;
		}
		
		// Handle Deaths
		if(info.isHero && info.heroHealth - event.getFinalDamage() > 0)
		{
			info.heroHealth -= event.getFinalDamage();
			player.setHealth(20);
			if(info.heroHealth == 0 && info.currentHero.equals("Artoria"))
			{
				// Artoria Passive
				player.sendMessage(ChatColor.GOLD + "Your Hero Armor has run out, and you have burst into flames!");
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40000, 0));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40000, 0));
				player.setFireTicks(40000);
			}
			return;
		}

		if(info.isHero && info.heroHealth > 0)
		{
			event.setDamage(event.getDamage() - info.heroHealth);
			info.heroHealth = 0;
			
			// Artoria Passive
			if(info.currentHero.equals("Artoria"))
			{
				player.sendMessage(ChatColor.GOLD + "Your Hero Armor has run out, and you have burst into flames!");
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40000, 0));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40000, 0));
				player.setFireTicks(40000);
			}
		}
		
		if(player.getHealth() - event.getFinalDamage() <= 0)
		{
			Player killer = player.getKiller();
			if(killer == null)
			{
				if(player.getLastDamageCause().getEntity() instanceof Player)
				{
					killer = (Player)player.getLastDamageCause().getEntity();
				}
				if(player.getLastDamageCause().getEntity() instanceof Projectile)
				{
					Projectile p = (Projectile)player.getLastDamageCause().getEntity();
					killer = (Player)p.getShooter();
				}
			}
			
			// Player died somehow? Not sure, but just have them die
			if(killer == null)
			{
				//cp.playerDie(player);
			}
			else
			{
				cp.playerDie(player, killer);
			}
			event.setCancelled(true);
		}
	}
	
	// Cancel Explosion Block Damage
	@EventHandler
	public void exposion(EntityExplodeEvent event)
	{
		if(event.getEntity() instanceof Fireball)
		{
			event.setCancelled(true);
		}
	}
	
	// Cancel Team Explosions
	@EventHandler
	public void teamExplode(EntityDamageByEntityEvent event)
	{
		if(!(event.getDamager() instanceof Fireball || event.getDamager() instanceof WitherSkull))
		{
			return;
		}
		if(event.getDamager() instanceof Fireball)
		{
			Fireball fireball = (Fireball)event.getDamager();
			Player shooter = (Player)fireball.getShooter();
			Player hit = (Player)event.getEntity();
			if(cp.getPlayerTeam(hit).getName().equals(cp.getPlayerTeam(shooter).getName()))
			{
				event.setCancelled(true);
			}
		}
		if(event.getDamager() instanceof WitherSkull)
		{
			WitherSkull skull = (WitherSkull)event.getDamager();
			Player shooter = (Player)skull.getShooter();
			Player hit = (Player)event.getEntity();
			if(cp.getPlayerTeam(hit).getName().equals(cp.getPlayerTeam(shooter).getName()))
			{
				event.setCancelled(true);
			}
			else
			{
				hit.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, 2));
			}
		}
	}
	
	// Splash Potions
	@EventHandler
	public void splashPotion(PotionSplashEvent event)
	{
		if(!(event.getPotion().getShooter() instanceof Player))
		{
			return;
		}
		
		Player thrower = (Player)event.getPotion().getShooter();
		Team team = cp.getPlayerTeam(thrower);
		
		for(LivingEntity hitEntity : event.getAffectedEntities())
		{
			for(PotionEffect pE : event.getPotion().getEffects())
			{
				if(!(hitEntity instanceof Player))
				{
					continue;
				}
				Player hitPlayer = (Player)hitEntity;
				if(cp.getPlayerTeam(hitPlayer).getName().equals(team.getName()))
				{
					if(pE.getType().equals(PotionEffectType.HARM))
					{
						event.setIntensity(hitPlayer, 0);
					}
				}
				else
				{
					if(pE.getType().equals(PotionEffectType.HEAL))
					{
						event.setIntensity(hitPlayer, 0);
					}
				}
			}
		}
	}
	
	// Artoria Arrow
	@EventHandler
	public void arrowRemove(ProjectileHitEvent event)
	{
		if(!(event.getEntity() instanceof Arrow))
		{
			return;
		}
		Arrow a = (Arrow)event.getEntity();
		if(cp.archerArrows.contains(a))
		{
			cp.archerArrows.remove(a);
		}
		if(cp.tracerArrows.contains(a))
		{
			cp.tracerArrows.remove(a);
		}
		if(cp.artoriaArrows.contains(a))
		{
			cp.artoriaArrows.remove(a);
		}
		if(cp.apolloArrows.contains(a))
		{
			Player player = (Player)a.getShooter();
			for(Player hitPlayer : Bukkit.getOnlinePlayers())
			{
				// Give speed to teammates
				if(cp.getPlayerTeam(player).getName().equals(cp.getPlayerTeam(hitPlayer).getName()) || (player.equals(hitPlayer)))
				{
					if(a.getLocation().distance(hitPlayer.getLocation()) <= 3.5)
					{
						if(hitPlayer.hasPotionEffect(PotionEffectType.SPEED))
						{
							hitPlayer.removePotionEffect(PotionEffectType.SPEED);
						}
						hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0));
					}
					continue;
				}
				
				// Otherwise, give blindness and deal damage
				if(a.getLocation().distance(hitPlayer.getLocation()) <= 3.5)
				{
					if(hitPlayer.hasPotionEffect(PotionEffectType.BLINDNESS))
					{
						hitPlayer.removePotionEffect(PotionEffectType.BLINDNESS);
					}
					hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));
					if(cp.getPlayerInfo(hitPlayer.getName()).isHero == false)
					{
						if(hitPlayer.getHealth() - 3 <= 0)
						{
							cp.playerDie(hitPlayer, player);
						}
						else
						{
							hitPlayer.damage(3);
						}
					}
					else
					{
						if(cp.getPlayerInfo(hitPlayer.getName()).heroHealth - 3 <= 0)
						{
							if(cp.getPlayerInfo(hitPlayer.getName()).heroHealth > 0)
							{
								hitPlayer.damage(3 - cp.getPlayerInfo(hitPlayer.getName()).heroHealth);
								cp.getPlayerInfo(hitPlayer.getName()).heroHealth = 0;
							}
							if(hitPlayer.getHealth() - 3 <= 0)
							{
								cp.playerDie(hitPlayer, player);
							}
							else
							{
								hitPlayer.damage(3);
							}
						}
						else
						{
							cp.getPlayerInfo(hitPlayer.getName()).heroHealth -= 3;
						}
					}
				}
			}
		}
	}
	
	// Lightning Orb
	@EventHandler
	public void lightningOrb(ProjectileHitEvent event)
	{
		if(!(event.getEntity() instanceof Snowball))
		{
			return;
		}
		Snowball orb = (Snowball)event.getEntity();
		orb.getWorld().strikeLightningEffect(orb.getLocation());
		Player shooter = (Player)orb.getShooter();
		for(Player player : Bukkit.getOnlinePlayers())
		{
			// Cancel Team Damage
			Team shooterTeam = cp.getPlayerTeam(shooter);
			if(shooterTeam.getName().equals(cp.getPlayerTeam(player).getName()))
			{
				continue;
			}
			
			// Zeus Passive
			if(cp.getPlayerInfo(player.getName()).isHero && cp.getPlayerInfo(player.getName()).currentHero.equals("Zeus"))
			{
				continue;
			}
			
			if(orb.getLocation().distance(player.getLocation()) <= 4)
			{
				if(cp.getPlayerInfo(player.getName()).isHero == false)
				{
					if(player.getHealth() - 8 <= 0)
					{
						cp.playerDie(player, shooter);
					}
					else
					{
						player.damage(8);
					}
				}
				else
				{
					if(cp.getPlayerInfo(player.getName()).heroHealth - 8 <= 0)
					{
						if(cp.getPlayerInfo(player.getName()).heroHealth > 0)
						{
							player.damage(8 - cp.getPlayerInfo(player.getName()).heroHealth);
							cp.getPlayerInfo(player.getName()).heroHealth = 0;
						}
						if(player.getHealth() - 8 <= 0)
						{
							cp.playerDie(player, shooter);
						}
						else
						{
							player.damage(8);
						}
					}
					else
					{
						cp.getPlayerInfo(player.getName()).heroHealth -= 8;
					}
				}
			}
		}
	}
	
	// Wither Scroll
	@EventHandler
	public void witherScroll(PlayerInteractEvent event)
	{
		if(event.getItem() == null)
		{
			return;
		}
		if(event.getItem().getType() != Material.PAPER)
		{
			return;
		}
		if(!event.getItem().hasItemMeta())
		{
			return;
		}
		if(!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
		{
			return;
		}
		ItemStack removeItem = event.getItem();
		removeItem.setAmount(1);
		event.getPlayer().getInventory().removeItem(new ItemStack(removeItem));
		WitherSkull skull = event.getPlayer().launchProjectile(WitherSkull.class);
		skull.teleport(new Location(skull.getLocation().getWorld(), skull.getLocation().getX(), skull.getLocation().getY() - .6, skull.getLocation().getZ()));
		skull.setVelocity(skull.getVelocity().multiply(4));
	}
	
	// Nether Fragment
	@EventHandler
	public void netherFragment(PlayerInteractEvent event)
	{
		if(event.getItem() == null)
		{
			return;
		}
		if(event.getItem().getType() != Material.NETHER_STAR)
		{
			return;
		}
		if(!event.getItem().hasItemMeta())
		{
			return;
		}
		if(!ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()).equals("Nether Fragment"))
		{
			return;
		}
		if(!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
		{
			return;
		}
		ItemStack removeItem = event.getItem();
		removeItem.setAmount(1);
		Player player = event.getPlayer();
		player.getInventory().removeItem(new ItemStack(removeItem));
		if(player.hasPotionEffect(PotionEffectType.NIGHT_VISION))
		{
			player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		}
		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 120, 1));
		if(player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))
		{
			player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
		}
		player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 120, 1));
		for(Player other : Bukkit.getOnlinePlayers())
		{
			other.hidePlayer(event.getPlayer());
		}
		cp.scheduler.scheduleSyncDelayedTask(cp, new Runnable(){
			@Override
			public void run() {
				for(Player all : Bukkit.getOnlinePlayers())
				{
					all.showPlayer(event.getPlayer());
				}
			}
		}, 120L);
	}
	
	// Active Ability
	@EventHandler
	public void activeAbility(PlayerInteractEvent event)
	{
		if(event.getItem() == null)
		{
			return;
		}
		if(!event.getItem().hasItemMeta())
		{
			return;
		}
		if(!event.getItem().getItemMeta().hasLore())
		{
			return;
		}
		if(!(event.getItem().getItemMeta().getLore().contains(ChatColor.LIGHT_PURPLE + "Active Ability")))
		{
			return;
		}
		if(!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
		{
			return;
		}
		
		Player player = event.getPlayer();
		PlayerInfo info = cp.getPlayerInfo(player.getName());
		ChatColor teamColor = null;
		switch(ChatColor.stripColor(cp.getPlayerTeam(player).getName()))
		{
			case "Red Team": 
				if(cp.redHero < 2)
				{
					return;
				}
				cp.redHero = 0;
				cp.redHeroActive.setProgress(0);
				teamColor = ChatColor.RED;	
				break;
			case "Blue Team": 
				if(cp.blueHero < 2)
				{
					return;
				}
				cp.blueHero = 0;
				cp.blueHeroActive.setProgress(0);
				teamColor = ChatColor.BLUE;	
				break;
			case "Green Team":
				if(cp.greenHero < 2)
				{
					return;
				}
				cp.greenHero = 0;
				cp.greenHeroActive.setProgress(0);
				teamColor = ChatColor.GREEN;	
				break;
			case "Purple Team": 
				if(cp.purpleHero < 2)
				{
					return;
				}
				cp.purpleHero = 0;
				cp.purpleHeroActive.setProgress(0);
				teamColor = ChatColor.DARK_PURPLE;	
				break;
		}
		
		// Hero Abilities
		switch(info.currentHero)
		{
		
			case "Achlys": Bukkit.broadcastMessage(teamColor + player.getName() + ChatColor.GOLD + " has caused a Contagion!");
				// Launch Wither Skulls
				for(int i = 0; i < 10; i++)
				{
					double value = i * (Math.PI * 2.0 / 10.0);
					cp.scheduler.scheduleSyncDelayedTask(cp, new Runnable()
					{
						@Override
						public void run() {
							WitherSkull skull = player.launchProjectile(WitherSkull.class);
							skull.setShooter(player);
							skull.setDirection(cp.rotateVectorCC(skull.getDirection(), new Vector(0, 1, 0), value));
							skull.setMetadata("ProjectileType", new FixedMetadataValue(cp, "Contagion"));
						}
					}, i * 7L);
				}
				
				cp.scheduler.scheduleSyncDelayedTask(cp, new Runnable()
				{
					@Override
					public void run() {
						for(Player contagionPlayer : Bukkit.getOnlinePlayers())
						{
							PlayerInfo info = cp.getPlayerInfo(contagionPlayer.getName());
							if(info.contagion)
							{
								info.contagion = false;
								info.contagionTeam = null;
							}
						}
					}
				}, 200L);
				break;
			case "Adrestia": Bukkit.broadcastMessage(teamColor + player.getName() + ChatColor.GOLD + " has used Balancing Act!");
				HeroAdrestia adrestia = new HeroAdrestia();
				Bukkit.broadcastMessage(ChatColor.GOLD + adrestia.quote);
				cp.getPlayerTeam(player);
				if(cp.getPlayerTeam(player) != cp.teamManager.redTeam)
				{
					for(OfflinePlayer p : cp.teamManager.redTeam.getPlayers())
					{
						int lowestLevelClass;
						
					}
				}

				/*player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 2));
				for(Player hitPlayer : Bukkit.getOnlinePlayers())
				{
					if((player.equals(hitPlayer)))
					{
						continue;
					}
					if(player.getLocation().distance(hitPlayer.getLocation()) <= 10)
					{
						hitPlayer.sendMessage(ChatColor.GOLD + "Your potion effects have been nullified by " + teamColor + player.getName() + "'s" + ChatColor.GOLD + " Balancing Act!");
						cp.getPlayerInfo(hitPlayer.getName()).nullified = true;
						for(PotionEffect pE : hitPlayer.getActivePotionEffects())
						{
							hitPlayer.removePotionEffect(pE.getType());
						}
						cp.scheduler.scheduleSyncDelayedTask(cp, new Runnable()
						{
							@Override
							public void run() {
								if(cp.getPlayerInfo(hitPlayer.getName()).nullified)
								{
									cp.getPlayerInfo(hitPlayer.getName()).nullified = false;
								}
							}
						}, 200L);
					}
				}*/
				break;
				
			case "Apollo": Bukkit.broadcastMessage(teamColor + player.getName() + ChatColor.GOLD + " has blessed their team with Enlightenment!");
				HeroApollo apollo = new HeroApollo();
				Bukkit.broadcastMessage(ChatColor.GOLD + apollo.quote);
				for(OfflinePlayer teamPlay : cp.getPlayerTeam(player).getPlayers())
				{
					Player teamPlayer = teamPlay.getPlayer();
					if(teamPlayer.equals(player))
					{
						teamPlayer.sendMessage(ChatColor.GOLD + "You have been Enlightened!");
					}
					else
					{
						teamPlayer.sendMessage(ChatColor.GOLD + "You have been Enlightened by" + teamColor + player.getName() + ChatColor.GOLD + "!");
					}
					teamPlayer.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 400, 0));
					teamPlayer.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 400, 0));
					if(Math.random() <= .5)
					{
						ItemStack lightOrb = new ItemStack(Material.ENDER_PEARL);
						ItemMeta lightOrbMeta = lightOrb.getItemMeta();
						lightOrbMeta.setDisplayName(ChatColor.GOLD + "Light Orb");
						lightOrb.setItemMeta(lightOrbMeta);
						teamPlayer.getInventory().addItem(lightOrb);
						teamPlayer.sendMessage(ChatColor.GOLD + "You have received a Light Orb from your Enlightenment!");
					}
				}
				break;
			
			case "Artoria": Bukkit.broadcastMessage(teamColor + player.getName() + ChatColor.GOLD + " has used Divine Light!");
				HeroArtoria art = new HeroArtoria();
				Bukkit.broadcastMessage(ChatColor.GOLD + art.quote);
				Arrow a = player.launchProjectile(Arrow.class);
				a.setVelocity(a.getVelocity().multiply(1.22));
				cp.artoriaArrows.add(a);
				break;
				
			case "Azazel": Bukkit.broadcastMessage(teamColor + player.getName() + ChatColor.GOLD + " has used Demonic Slash!");
				for(Player hitPlayer : Bukkit.getOnlinePlayers())
				{
					if(cp.getPlayerTeam(player).getName().equals(cp.getPlayerTeam(hitPlayer).getName()) || (player.equals(hitPlayer)))
					{
						continue;
					}
					if(player.getLocation().distance(hitPlayer.getLocation()) <= 8)
					{
						hitPlayer.sendMessage(ChatColor.GOLD + "You have been hit by " + teamColor + player.getName() + ChatColor.GOLD +"'s Demonic Slash!");
						hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 1));
						hitPlayer.setFireTicks(80);
						if(cp.getPlayerInfo(hitPlayer.getName()).isHero == false)
						{
							if(hitPlayer.getHealth() - 4.5 <= 0)
							{
								cp.playerDie(hitPlayer, player);
							}
							else
							{
								hitPlayer.damage(4.5);
							}
						}
						else
						{
							if(cp.getPlayerInfo(hitPlayer.getName()).heroHealth - 4.5 <= 0)
							{
								if(cp.getPlayerInfo(hitPlayer.getName()).heroHealth > 0)
								{
									hitPlayer.damage(4.5 - cp.getPlayerInfo(hitPlayer.getName()).heroHealth);
									cp.getPlayerInfo(hitPlayer.getName()).heroHealth = 0;
								}
								if(hitPlayer.getHealth() - 4.5 <= 0)
								{
									cp.playerDie(hitPlayer, player);
								}
								else
								{
									hitPlayer.damage(4.5);
								}
							}
							else
							{
								cp.getPlayerInfo(hitPlayer.getName()).heroHealth -= 4.5;
							}
						}
					}
				}
				break;
				
			case "Cecilia": Bukkit.broadcastMessage(teamColor + player.getName() + ChatColor.GOLD + " has used Regeneration Boost!");
				for(OfflinePlayer offP : cp.getPlayerTeam(player).getPlayers())
				{
					Player teamPlayer = offP.getPlayer();
					if(teamPlayer.equals(player))
					{
						continue;
					}
					if(player.getLocation().distance(teamPlayer.getLocation()) <= 8)
					{
						teamPlayer.sendMessage(ChatColor.GOLD + "Your regeneration has been boosted by " + teamColor + player.getName() + ChatColor.GOLD + "!");
						teamPlayer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 120, 2));
					}
				}
				break;	
			
			case "Fenrir": Bukkit.broadcastMessage(teamColor + player.getName() + ChatColor.GOLD + " has used Wolf Howl!");
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0));
				
				for(Player hitPlayer : Bukkit.getOnlinePlayers())
				{
					hitPlayer.playSound(hitPlayer.getLocation(), Sound.ENTITY_WOLF_HOWL, 1, 1);
					if(cp.getPlayerTeam(player).getName().equals(cp.getPlayerTeam(hitPlayer).getName()) || (player.equals(hitPlayer)))
					{
						continue;
					}
					if(player.getLocation().distance(hitPlayer.getLocation()) <= 10)
					{
						hitPlayer.sendMessage(ChatColor.GOLD + "You have been stunned by " + teamColor + player.getName() + "'s" + ChatColor.GOLD + " Wolf Howl!");
						hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 10));
						hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 60, -10));
					}	
				}
				
				cp.scheduler.scheduleSyncDelayedTask(cp, new Runnable()
				{
					@Override
					public void run() {
						player.sendMessage(ChatColor.GOLD + "The effects of your Wolf Howl have worn off!");
					}
				}, 200L);
				break;
				
			case "Gilgamesh": Bukkit.broadcastMessage(teamColor + player.getName() + ChatColor.GOLD + " has opened the Gate of Babylon!");
				for(int i = 0; i < 6; i++)
				{
					cp.scheduler.scheduleSyncDelayedTask(cp, new Runnable()
					{
						@Override
						public void run() {
							for(int i = -4; i < 5; i++)
							{
								for(int j = -1; j < 2; j++)
								{
									Arrow arrow = player.launchProjectile(Arrow.class);
									arrow.setShooter(player);
									arrow.setVelocity(cp.rotateVectorCC(arrow.getVelocity(), new Vector(0, 1, 0), i * .2));
									arrow.setVelocity(cp.rotateVectorCC(arrow.getVelocity(), new Vector(0, 0, 1), j * .12));
									arrow.setVelocity(arrow.getVelocity().multiply(1.3));
								}
							}
	
							player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_AMBIENT, 1, 1);
						}
					}, i * 8);
				}
				break;
				
			case "Hephaestus": Bukkit.broadcastMessage(teamColor + player.getName() + ChatColor.GOLD + " has Erupted!");
				player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 300, 1));
				
				// Launch Fireballs
				for(int i = 0; i < 16; i++)
				{
					int count = i;
					cp.scheduler.scheduleSyncDelayedTask(cp, new Runnable()
					{
						@Override
						public void run() {
							Fireball fireball = player.launchProjectile(Fireball.class);
							fireball.setShooter(player);
							fireball.setDirection(cp.rotateVectorCC(fireball.getDirection(), new Vector(0, 1, 0), count * (2.0 * Math.PI / 8.0)).normalize());
						}	
					}, i * 6L);
				}
	
				// Set fire
				for(int i = -5; i <= 5; i++)
				{
					for(int j = -5; j <= 5; j++)
					{
						if(Math.random() <= .25)
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
							
							cp.scheduler.scheduleSyncDelayedTask(cp, new Runnable()
							{
								@Override
								public void run() {
									player.getWorld().getBlockAt(fireLoc).setType(Material.NETHERRACK);
									player.getWorld().getBlockAt(fireLoc).getRelative(BlockFace.UP).setType(Material.FIRE);
								}
							}, 2L);
							
							cp.scheduler.scheduleSyncDelayedTask(cp, new Runnable()
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
				break;
			
			case "Khaos": Bukkit.broadcastMessage(teamColor + player.getName() + ChatColor.GOLD + " has used Entropy!");
				player.sendMessage(ChatColor.RED + "Your hotbar has been scrambled!");
				List<Player> hitPlayers = new ArrayList<Player>();
				for(Player hitPlayer : Bukkit.getOnlinePlayers())
				{
					if((player.equals(hitPlayer)))
					{
						continue;
					}
					if(player.getLocation().distance(hitPlayer.getLocation()) <= 12)
					{
						hitPlayers.add(hitPlayer);
					}
				}
				if(hitPlayers.size() > 1)
				{
					for(int i = 0; i < hitPlayers.size(); i ++)
					{
						hitPlayers.get(i).sendMessage(ChatColor.RED + "You have been teleported to another player's location!");
						Location prev = hitPlayers.get(i).getLocation();
						if(i + 1 < hitPlayers.size())
						{
							hitPlayers.get(i).teleport(hitPlayers.get(i + 1).getLocation());
							hitPlayers.get(i + 1).teleport(prev);
						}
						else
						{
							hitPlayers.get(i).teleport(hitPlayers.get(0).getLocation());
							hitPlayers.get(0).teleport(prev);
						}
					}
				}
				for(Player hitPlayer : hitPlayers)
				{
					hitPlayer.sendMessage(ChatColor.RED + "Your hotbar has been scrambled!");
					for(int i = 0; i < 9; i++)
					{
						int num1 = (int)(Math.random() * 9);
						int num2 = (int)(Math.random() * 9);
						ItemStack item1 = hitPlayer.getInventory().getItem(num1);
						ItemStack item2 = hitPlayer.getInventory().getItem(num2);
						hitPlayer.getInventory().setItem(num1, item2);
						hitPlayer.getInventory().setItem(num2, item1);
					}
				}
				break;
				
			case "Kronos":
				break;
				
			case "Lena": Bukkit.broadcastMessage(teamColor + player.getName() + ChatColor.GOLD + " has thrown a Pulse Bomb!");
				HeroLena lena = new HeroLena();
				Bukkit.broadcastMessage(ChatColor.GOLD + lena.quote);
				Projectile p = player.launchProjectile(EnderPearl.class);
				p.setMetadata("ProjectileType", new FixedMetadataValue(cp, "Pulse"));
				p.setVelocity(p.getVelocity().multiply(.45));
				break;			

			case "Noiresse": String playerStr = teamColor + player.getName();
				Bukkit.broadcastMessage(playerStr + ChatColor.GOLD + " has created a Black Hole!");
				player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 160, -10));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 160, 10));
				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 160, 1));
				player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 160, 10));
				player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 160, 10));
				
				int taskId1 = cp.scheduler.scheduleSyncRepeatingTask(cp, new Runnable(){
					@Override
					public void run() {
						for(Player hitPlayer : Bukkit.getOnlinePlayers())
						{
							if(cp.getPlayerTeam(player).getName().equals(cp.getPlayerTeam(hitPlayer).getName()) || (player.equals(hitPlayer)))
							{
								continue;
							}
							if(player.getLocation().distance(hitPlayer.getLocation()) <= 14)
							{
								Vector velocity = player.getLocation().toVector().subtract(hitPlayer.getLocation().toVector());
								hitPlayer.setVelocity(velocity.normalize().multiply(.075));
							}	
							if(player.getLocation().distance(hitPlayer.getLocation()) <= 8)
							{
								if(hitPlayer.hasPotionEffect(PotionEffectType.BLINDNESS))
								{
									hitPlayer.removePotionEffect(PotionEffectType.BLINDNESS);
								}
								hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
							}
						}
					}
				}, 0L, 2L);
				int taskId2 = cp.scheduler.scheduleSyncRepeatingTask(cp, new Runnable(){
					@Override
					public void run() {
						Location fwLoc = new Location(player.getWorld(), player.getLocation().getX() + (int)(Math.random() * 5) - 3, player.getLocation().getY() + 3, player.getLocation().getZ()+ (int)(Math.random() * 5) - 3);
						Firework fw = (Firework)player.getWorld().spawnEntity(fwLoc, EntityType.FIREWORK);
						FireworkMeta fwMeta = fw.getFireworkMeta();
						fwMeta.addEffect(FireworkEffect.builder().trail(false).flicker(false).withColor(Color.BLACK).withFade(Color.BLACK).with(FireworkEffect.Type.BURST).build());
						fw.setFireworkMeta(fwMeta);
						
						// Explode the firework 2 ticks after
						cp.scheduler.scheduleSyncDelayedTask(cp, new Runnable()
						{
							@Override
							public void run() {
								fw.playEffect(EntityEffect.FIREWORK_EXPLODE);
								fw.remove();
							}
						}, 2L);
						
						for(Player hitPlayer : Bukkit.getOnlinePlayers())
						{
							if(cp.getPlayerTeam(player).getName().equals(cp.getPlayerTeam(hitPlayer).getName()) || (player.equals(hitPlayer)))
							{
								continue;
							}
							if(player.getLocation().distance(hitPlayer.getLocation()) <= 5)
							{
								if(hitPlayer.hasPotionEffect(PotionEffectType.CONFUSION))
								{
									hitPlayer.removePotionEffect(PotionEffectType.CONFUSION);
								}
								hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 60, 1));
								if(cp.getPlayerInfo(hitPlayer.getName()).isHero == false)
								{
									if(hitPlayer.getHealth() - 2.5 <= 0)
									{
										cp.playerDie(hitPlayer, player);
									}
									else
									{
										hitPlayer.damage(2.5);
									}
								}
								else
								{
									if(cp.getPlayerInfo(hitPlayer.getName()).heroHealth - 2.5 <= 0)
									{
										if(cp.getPlayerInfo(hitPlayer.getName()).heroHealth > 0)
										{
											hitPlayer.damage(2.5 - cp.getPlayerInfo(hitPlayer.getName()).heroHealth);
											cp.getPlayerInfo(hitPlayer.getName()).heroHealth = 0;
										}
										if(hitPlayer.getHealth() - 2.5 <= 0)
										{
											cp.playerDie(hitPlayer, player);
										}
										else
										{
											hitPlayer.damage(2.5);
										}
									}
									else
									{
										cp.getPlayerInfo(hitPlayer.getName()).heroHealth -= 2.5;
									}
								}
							}
						}
					}
				}, 0L, 20L);
				cp.scheduler.scheduleSyncDelayedTask(cp, new Runnable()
				{
					@Override
					public void run() {
						cp.scheduler.cancelTask(taskId1);
						cp.scheduler.cancelTask(taskId2);
						Bukkit.broadcastMessage(playerStr + "'s" + ChatColor.GOLD + " Black Hole has stopped!");
					}
				}, 160L);
				break;
				
			case "Terminus": Bukkit.broadcastMessage(teamColor + player.getName() + ChatColor.GOLD + " has used Stone Morph!");
				HeroTerminus term = new HeroTerminus();
				Bukkit.broadcastMessage(ChatColor.GOLD + term.quote);
				player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, -10));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 10));
				PlayerInfo playerInfo = cp.getPlayerInfo(player.getName());
				playerInfo.heroActive = true;
				
				cp.scheduler.scheduleSyncDelayedTask(cp, new Runnable(){
					@Override
					public void run() {
						playerInfo.heroActive = false;
					}
				}, 100L);
				
				BukkitRunnable runnable = new BukkitRunnable()
				{
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(playerInfo.heroActive)
						{
							for(int i = 0; i < 20; i++)
							{
								double xChange = Math.random() -.5;
								double yChange = Math.random() -.5;
								double zChange = Math.random() -.5;
								player.getWorld().playEffect(new Location(player.getWorld(), player.getLocation().getX() + xChange, player.getLocation().getY() + .8 + yChange, player.getLocation().getZ() + zChange), Effect.TILE_BREAK, 1);
							}
							for(OfflinePlayer teamPlay : cp.getPlayerTeam(player).getPlayers())
							{
								if(teamPlay.getPlayer().getLocation().distance(player.getLocation()) <= 7)
								{
									teamPlay.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 0));
								}
							}
						}
						else
						{
							this.cancel();
						}
					}
				};
				runnable.runTaskTimer(cp, 0L, 2L);
				break;
				
			case "Zeus": Bukkit.broadcastMessage(teamColor + player.getName() + ChatColor.GOLD + " has Smited the Control Point!");
				// Lightning effects
				for(double i = cp.center.getX() - cp.centerDistance; i <= cp.center.getX() + cp.centerDistance; i++)
				{
					for(double j = cp.center.getZ() - cp.centerDistance; j <= cp.center.getZ() + cp.centerDistance; j++)
					{
						Location lightningLoc = new Location(cp.center.getWorld(), i, cp.center.getY(), j);
						lightningLoc.getWorld().strikeLightningEffect(lightningLoc);
					}
				}
			
				// Check if each player is on the point
				for(Player hitPlayer : Bukkit.getOnlinePlayers())
				{
					if(cp.getPlayerTeam(player).getName().equals(cp.getPlayerTeam(hitPlayer).getName()) || (player.equals(hitPlayer)))
					{
						continue;
					}
					if(cp.getPlayerInfo(hitPlayer.getName()).isHero && cp.getPlayerInfo(hitPlayer.getName()).currentHero.equals("Zeus"))
					{
						continue;
					}		
					Location pLocation = hitPlayer.getLocation();
					if(pLocation.distance(cp.center) <= cp.centerDistance)
					{
						// Player is on the point
						hitPlayer.sendMessage(ChatColor.GOLD + "You have been hit by " + teamColor + player.getName() + "'s" + ChatColor.GOLD + " Smite and have been stunned!");
						hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 10));
						hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 60, -10));
						
						// Deal Damage
						if(cp.getPlayerInfo(hitPlayer.getName()).isHero == false)
						{
							if(hitPlayer.getHealth() - 7 <= 0)
							{
								cp.playerDie(hitPlayer, player);
							}
							else
							{
								hitPlayer.damage(7);
							}
						}
						else
						{
							if(cp.getPlayerInfo(hitPlayer.getName()).heroHealth - 7 <= 0)
							{
								if(cp.getPlayerInfo(hitPlayer.getName()).heroHealth > 0)
								{
									hitPlayer.damage(7 - cp.getPlayerInfo(hitPlayer.getName()).heroHealth);
									cp.getPlayerInfo(hitPlayer.getName()).heroHealth = 0;
								}
								if(hitPlayer.getHealth() - 7 <= 0)
								{
									cp.playerDie(hitPlayer, player);
								}
								else
								{
									hitPlayer.damage(7);
								}
							}
							else
							{
								cp.getPlayerInfo(hitPlayer.getName()).heroHealth -= 7;
							}
						}
					}
				}
			break;
		}
	}
	
	// Blaze Orb
	@EventHandler
	public void blazeOrb(PlayerInteractEvent event)
	{
		if(event.getItem() == null)
		{
			return;
		}
		if(event.getItem().getType() != Material.MAGMA_CREAM)
		{
			return;
		}
		if(!event.getItem().hasItemMeta())
		{
			return;
		}
		if(!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
		{
			return;
		}
		ItemStack removeItem = event.getItem();
		removeItem.setAmount(1);
		Player player = event.getPlayer();
		player.getInventory().removeItem(new ItemStack(removeItem));
		if(player.hasPotionEffect(PotionEffectType.SPEED))
		{
			player.removePotionEffect(PotionEffectType.SPEED);
		}
		event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120, 2));
	}
	
	// Collector
	@EventHandler
	public void collectorPull(PlayerFishEvent event)
	{
		if(!(event.getCaught() instanceof Player))
		{
			return;
		}
		ItemStack rod = event.getPlayer().getInventory().getItemInMainHand();
		if(!rod.hasItemMeta())
		{
			return;
		}
		Player caught = (Player)event.getCaught();
		caught.teleport(event.getPlayer().getLocation());
		caught.sendMessage(ChatColor.GOLD + "You have been pulled to " + event.getPlayer().getName() + "!");
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
	
	// Cancel Block Break
	@EventHandler
	public void cancelBlockBreak(BlockBreakEvent event)
	{
		event.setCancelled(true);
	}
	
	// Cancel Hunger
	@EventHandler
	public void cancelHunger(FoodLevelChangeEvent event)
	{
		event.setCancelled(true);
	}
	
	// Cancel Block Place
	@EventHandler
	public void cancelPlace(BlockPlaceEvent event)
	{
		if(event.getBlock().getType() != Material.TRIPWIRE)
		{
			event.setCancelled(true);
			return;
		}
		event.getBlock().setMetadata("Player", new FixedMetadataValue(cp, event.getPlayer().getName()));
	}
	
	// Blockopolis Jump Pad
	@EventHandler
	public void jumpPad(PlayerMoveEvent event)
	{
		if(!cp.mapName.equals("Block City"))
		{
			return;
		}
		Player player = event.getPlayer();
		Location blockLoc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() - 1, player.getLocation().getZ());
		if(player.getWorld().getBlockAt(blockLoc).getType() != Material.CLAY)
		{
			return;
		}
		Vector velocity = cp.center.toVector().subtract(player.getLocation().toVector()).normalize();
		velocity.multiply(2);
		velocity.setY(.5);
		player.setVelocity(velocity);
		
	}
	
	// IED Explosion
	@EventHandler
	public void ied(PlayerMoveEvent event)
	{
		if(event.getPlayer().getWorld().getBlockAt(event.getPlayer().getLocation()).getType() != Material.TRIPWIRE)
		{
			return;
		}
		Block block = event.getPlayer().getWorld().getBlockAt(event.getPlayer().getLocation());
		if(block.hasMetadata("Player"))
		{
			Player placer = Bukkit.getPlayer(block.getMetadata("Player").get(0).asString());
			Player player = event.getPlayer();
			if(cp.getPlayerTeam(placer).getName().equals(cp.getPlayerTeam(player).getName()))
			{
				return;
			}
			block.getWorld().createExplosion(block.getLocation(), 0);
			block.setType(Material.AIR);
			player.sendMessage(ChatColor.RED + "You have triggered an IED!");
			if(cp.getPlayerInfo(player.getName()).isHero == false)
			{
				if(player.getHealth() - 5 <= 0)
				{
					cp.playerDie(player, placer);
				}
				else
				{
					player.damage(5);
				}
			}
			else
			{
				if(cp.getPlayerInfo(player.getName()).heroHealth - 5 <= 0)
				{
					if(cp.getPlayerInfo(player.getName()).heroHealth > 0)
					{
						player.damage(5 - cp.getPlayerInfo(player.getName()).heroHealth);
						cp.getPlayerInfo(player.getName()).heroHealth = 0;
					}
					if(player.getHealth() - 5 <= 0)
					{
						cp.playerDie(player, placer);
					}
					else
					{
						player.damage(5);
					}
				}
				else
				{
					cp.getPlayerInfo(player.getName()).heroHealth -= 5;
				}
			}
		}
	}
	
	// Light orb throw
	@EventHandler
	public void lightOrb(PlayerInteractEvent event)
	{
		if(event.getItem() == null)
		{
			return;
		}
		if(event.getItem().getType() != Material.ENDER_PEARL)
		{
			return;
		}
		if(!event.getAction().equals(Action.RIGHT_CLICK_AIR) && !event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			return;
		}
		if(!event.getItem().hasItemMeta())
		{
			return;
		}
		if(!ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()).equals("Light Orb"))
		{
			return;
		}
		ItemStack removeItem = event.getItem();
		removeItem.setAmount(1);
		event.getPlayer().getInventory().remove(removeItem);
		EnderPearl light = event.getPlayer().launchProjectile(EnderPearl.class);
		light.setMetadata("ProjectileType", new FixedMetadataValue(cp, "Light"));
		event.setCancelled(true);
	}
	
	// Khaos Potions
	@EventHandler
	public void khaosPotions(PlayerInteractEvent event)
	{
		if(event.getItem() == null)
		{
			return;
		}
		if(event.getItem().getType() != Material.SPLASH_POTION)
		{
			return;
		}
		if(!event.getAction().equals(Action.RIGHT_CLICK_AIR) && !event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			return;
		}
		if(!event.getItem().hasItemMeta())
		{
			return;
		}
		if(ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()).equals("Khaotic Poison"))
		{
			ItemStack removeItem = event.getItem();
			removeItem.setAmount(1);
			event.getPlayer().getInventory().remove(removeItem);
			ThrownPotion light = event.getPlayer().launchProjectile(ThrownPotion.class);
			light.setMetadata("ProjectileType", new FixedMetadataValue(cp, "Poison"));
			event.setCancelled(true);
			return;
		}
		if(ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()).equals("Khaotic Confusion"))
		{
			ItemStack removeItem = event.getItem();
			removeItem.setAmount(1);
			event.getPlayer().getInventory().remove(removeItem);
			ThrownPotion light = event.getPlayer().launchProjectile(ThrownPotion.class);
			light.setMetadata("ProjectileType", new FixedMetadataValue(cp, "Confusion"));
			event.setCancelled(true);
			return;
		}
		if(ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()).equals("Khaotic Jump"))
		{
			ItemStack removeItem = event.getItem();
			removeItem.setAmount(1);
			event.getPlayer().getInventory().remove(removeItem);
			ThrownPotion light = event.getPlayer().launchProjectile(ThrownPotion.class);
			light.setMetadata("ProjectileType", new FixedMetadataValue(cp, "Jump"));
			event.setCancelled(true);
			return;
		}

	}
	
	// Cancel Enderpearl Teleport
	@EventHandler
	public void cancelPearl(PlayerTeleportEvent event)
	{
		if(event.getCause().equals(TeleportCause.ENDER_PEARL))
		{
			event.setCancelled(true);
		}
	}
	
	public ControlPointListener(ControlPoint control)
	{
		cp = control;
		classes = cp.classes;
	}
}
