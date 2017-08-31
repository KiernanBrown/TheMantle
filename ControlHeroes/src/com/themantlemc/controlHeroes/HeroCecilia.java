package com.themantlemc.controlHeroes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HeroCecilia 
{
	String name = "Cecilia";
	int health = 30;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "A powerful medic who can", ChatColor.LIGHT_PURPLE + "both heal teammates and", ChatColor.LIGHT_PURPLE + "infect the enemy team."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	String passive = "The player who kills you is poisoned for 7 seconds.";
	String active = "Regeneration Boost: Nearby allies are given Regeneration 3 for 6 seconds.";
	String quote = "\"Those who do not stand by me will yield to our power!\"";
	
	public ControlPointHero returnHero()
	{			
		ItemStack devotionStaff = new ItemStack(Material.GOLD_HOE);
		ItemMeta devotionStaffMeta = devotionStaff.getItemMeta();
		devotionStaffMeta.setDisplayName(ChatColor.GOLD + "Staff of Devotion");
		devotionStaffMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Right click to shoot health", ChatColor.AQUA + "potions at teammates!")));
		devotionStaffMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		devotionStaff.setItemMeta(devotionStaffMeta);
		
		ItemStack destructionStaff = new ItemStack(Material.WOOD_HOE);
		ItemMeta destructionStaffMeta = destructionStaff.getItemMeta();
		destructionStaffMeta.setDisplayName(ChatColor.GOLD + "Staff of Destruction");
		destructionStaffMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Right click to shoot potions", ChatColor.AQUA + "of harming at enemies!")));
		destructionStaffMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		destructionStaff.setItemMeta(destructionStaffMeta);
		
		ItemStack regen = new ItemStack(Material.CAKE);
		ItemMeta regenMeta = regen.getItemMeta();
		regenMeta.setDisplayName(ChatColor.GOLD + "Regeneration Boost");
		regenMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Active Ability", ChatColor.LIGHT_PURPLE + "Greatly boost the health regen of", ChatColor.LIGHT_PURPLE + "all nearby teammates upon use!")));
		regen.setItemMeta(regenMeta);
		
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		LeatherArmorMeta helmetMeta = (LeatherArmorMeta)helmet.getItemMeta();
		helmetMeta.setDisplayName(ChatColor.GOLD + "Cecilia's Helmet");
		helmetMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Cecilia's leather helmet.")));
		helmetMeta.setColor(Color.fromRGB(255, 109, 221));
		helmet.setItemMeta(helmetMeta);
		
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		LeatherArmorMeta leggingsMeta = (LeatherArmorMeta)leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Cecilia's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Cecilia's leather leggings.")));
		leggingsMeta.setColor(Color.fromRGB(255, 109, 221));
		leggings.setItemMeta(leggingsMeta);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta bootsMeta = (LeatherArmorMeta)boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Cecilia's boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Cecilia's leather boots.")));
		bootsMeta.setColor(Color.fromRGB(255, 109, 221));
		boots.setItemMeta(bootsMeta);
		
		items.add(devotionStaff);
		items.add(destructionStaff);
		items.add(regen);
		armor.add(helmet);
		armor.add(leggings);
		armor.add(boots);
		potions.add(new PotionEffect(PotionEffectType.REGENERATION, 400000, 0));
		
		return new ControlPointHero(name, lore, items, armor, potions, health, passive, active, quote);
	}
}
