package com.themantlemc.controlHeroes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HeroKronos {
	String name = "Kronos";
	int health = 30;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "A manipulator of time who", ChatColor.LIGHT_PURPLE + "warps reality in order to", ChatColor.LIGHT_PURPLE + "gain the upper hand."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	String passive = "Killing a player causes nearby enemies to be slowed for 5 seconds, and gives nearby teammates speed for 5 seconds.";
	String active = "Time Dilation: Every nearby enemy is given a slowness and anti-jump effect for 7 seconds, and every nearby teammate receives a speed boost.";
	String quote = "\"Oh my... It appears you're running out of time!\"";
	
	public ControlPointHero returnHero()
	{	
		ItemStack timekeeper = new ItemStack(Material.DIAMOND_HOE);
		ItemMeta timekeeperMeta = timekeeper.getItemMeta();
		timekeeperMeta.setDisplayName(ChatColor.GOLD + "Timekeeper");
		timekeeper.setItemMeta(timekeeperMeta);
		
		ItemStack dilation = new ItemStack(Material.WATCH);
		ItemMeta dilationMeta = dilation.getItemMeta();
		dilationMeta.setDisplayName(ChatColor.GOLD + "Time Dilation");
		dilationMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Active Ability", ChatColor.LIGHT_PURPLE + "Cause a Time Dilation, slowing", ChatColor.LIGHT_PURPLE + "all enemies around you, when", ChatColor.LIGHT_PURPLE + "starting this, for 7 seconds!")));
		dilation.setItemMeta(dilationMeta);
		
		ItemStack helmet = new ItemStack(Material.GOLD_HELMET);
		ItemMeta helmetMeta = helmet.getItemMeta();
		helmetMeta.setDisplayName(ChatColor.GOLD + "Kronos's Helmet");
		helmetMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Gold helmet worn", ChatColor.AQUA + "by Kronos.")));
		helmet.setItemMeta(helmetMeta);
		
		ItemStack chestplate = new ItemStack(Material.GOLD_CHESTPLATE);
		ItemMeta chestplateMeta = chestplate.getItemMeta();
		chestplateMeta.setDisplayName(ChatColor.GOLD + "Kronos's Chestplate");
		chestplateMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A gold chestplate worn", ChatColor.AQUA + "by Kronos.")));
		chestplateMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		chestplate.setItemMeta(chestplateMeta);
		
		items.add(timekeeper);
		items.add(dilation);
		armor.add(helmet);
		armor.add(chestplate);

		return new ControlPointHero(name, lore, items, armor, potions, health, passive, active, quote);
	}
}