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

public class Scout {
	String name = "Scout";
	int level = 21;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Scout", ChatColor.AQUA + "A basic ranged class with a", ChatColor.AQUA+ "bow and armor."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	
	public ControlPointClass returnClass()
	{
		ItemStack bow = new ItemStack(Material.BOW);
		ItemMeta bowMeta = bow.getItemMeta();
		bowMeta.setDisplayName(ChatColor.GOLD + "Scout's Bow");
		bowMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A wooden bow used by", ChatColor.AQUA + "an scout.")));
		bowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		bowMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		bowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		bow.setItemMeta(bowMeta);
		
		ItemStack arrow = new ItemStack(Material.ARROW);
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemMeta chestplateMeta = chestplate.getItemMeta();
		chestplateMeta.setDisplayName(ChatColor.GOLD + "Scout's Chestplate");
		chestplateMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A leather chestplate worn", ChatColor.AQUA + "by an scout.")));
		chestplate.setItemMeta(chestplateMeta);
		
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemMeta leggingsMeta = leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Scout's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Leather leggings worn", ChatColor.AQUA + "by an scout.")));
		leggings.setItemMeta(leggingsMeta);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		ItemMeta bootsMeta = boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Scout's boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Leather boots worn", ChatColor.AQUA + "by an scout.")));
		boots.setItemMeta(bootsMeta);
		
		items.add(bow);
		items.add(arrow);
		armor.add(chestplate);
		armor.add(leggings);
		armor.add(boots);
		
		return new ControlPointClass(name, level, lore, items, armor, potions);
	}
}
