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

public class Shaman {
	
	String name = "Shaman";
	int level = 24;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Shaman", ChatColor.AQUA + "A basic magic class with a", ChatColor.AQUA+ "wand and armor."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	
	public ControlPointClass returnClass()
	{
		ItemStack wand = new ItemStack(Material.BLAZE_ROD);
		ItemMeta wandMeta = wand.getItemMeta();
		wandMeta.setDisplayName(ChatColor.GOLD + "Inferno Wand");
		wandMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A magic wand used by", ChatColor.AQUA + "a shaman.")));
		wandMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
		wand.setItemMeta(wandMeta);
		
		ItemStack gravity = new ItemStack(Material.STICK);
		ItemMeta gravityMeta = gravity.getItemMeta();
		gravityMeta.setDisplayName(ChatColor.GOLD + "Gravity Staff");
		gravityMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A powerful staff used by", ChatColor.AQUA + "a shaman.")));
		gravityMeta.addEnchant(Enchantment.KNOCKBACK, 2, true);
		gravity.setItemMeta(gravityMeta);
		
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemMeta leggingsMeta = leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Shaman's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Leather leggings worn", ChatColor.AQUA + "by a shaman.")));
		leggings.setItemMeta(leggingsMeta);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		ItemMeta bootsMeta = boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Shaman's boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Leather boots worn", ChatColor.AQUA + "by a shaman.")));
		boots.setItemMeta(bootsMeta);
		
		items.add(wand);
		items.add(gravity);
		armor.add(leggings);
		armor.add(boots);
		
		return new ControlPointClass(name, level, lore, items, armor, potions);
	}
}
