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

public class HeroZeus {

	String name = "Zeus";
	int health = 28;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "A lightning god who uses", ChatColor.LIGHT_PURPLE + "his powers to easily clear", ChatColor.LIGHT_PURPLE + "out the control point."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	String passive = "You take no damage from Lightning Orbs.";
	String active = "Smite: Smite the Control Point, damaging and stunning any enemies that are on it.";
	String quote = "\"From the heavens, I have come to smite the unworthy!\"";
	
	public ControlPointHero returnHero()
	{			
		ItemStack thunderbolt = new ItemStack(Material.GOLD_SPADE);
		ItemMeta thunderboltMeta = thunderbolt.getItemMeta();
		thunderboltMeta.setDisplayName(ChatColor.GOLD + "Thunderbolt");
		thunderboltMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Right Click to shoot", ChatColor.AQUA + "lightning orbs!")));
		thunderboltMeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
		thunderboltMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		thunderbolt.setItemMeta(thunderboltMeta);
		
		ItemStack orbs = new ItemStack(Material.SNOW_BALL, 2);
		ItemMeta orbsMeta = orbs.getItemMeta();
		orbsMeta.setDisplayName(ChatColor.GOLD + "Lightning Orb");
		orbs.setItemMeta(orbsMeta);
		
		ItemStack smite = new ItemStack(Material.GOLD_BLOCK);
		ItemMeta smiteMeta = smite.getItemMeta();
		smiteMeta.setDisplayName(ChatColor.GOLD + "Smite");
		smiteMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Active Ability", ChatColor.LIGHT_PURPLE + "Smite the Control Point, damaging", ChatColor.LIGHT_PURPLE + "and stunning all enemies standing", ChatColor.LIGHT_PURPLE + "on it!")));
		smite.setItemMeta(smiteMeta);
		
		ItemStack helmet = new ItemStack(Material.IRON_HELMET);
		ItemMeta helmetMeta = helmet.getItemMeta();
		helmetMeta.setDisplayName(ChatColor.GOLD + "Zeus's Helmet");
		helmetMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Zeus's leather leggings.")));
		helmet.setItemMeta(helmetMeta);
		
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		LeatherArmorMeta leggingsMeta = (LeatherArmorMeta)leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Zeus's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Zeus's leather leggings.")));
		leggingsMeta.setColor(Color.fromRGB(249, 252, 98));
		leggings.setItemMeta(leggingsMeta);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta bootsMeta = (LeatherArmorMeta)boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Zeus's boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Zeus's leather boots.")));
		bootsMeta.setColor(Color.fromRGB(249, 252, 98));
		bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		boots.setItemMeta(bootsMeta);
		
		items.add(thunderbolt);
		items.add(orbs);
		items.add(smite);
		armor.add(helmet);
		armor.add(leggings);
		armor.add(boots);
		
		return new ControlPointHero(name, lore, items, armor, potions, health, passive, active, quote);
	}
	
}
