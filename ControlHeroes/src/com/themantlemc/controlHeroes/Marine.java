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

public class Marine 
{
	
	String name = "Marine";
	int level = 35;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Marine", ChatColor.AQUA + "A melee class with protective", ChatColor.AQUA+ "armor and a shield."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	
	public ControlPointClass returnClass()
	{
		ItemStack sword = new ItemStack(Material.STONE_SWORD);
		ItemMeta swordMeta = sword.getItemMeta();
		swordMeta.setDisplayName(ChatColor.GOLD + "NCO Sword");
		swordMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A stone sword used by", ChatColor.AQUA + "a marine.")));
		swordMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		sword.setItemMeta(swordMeta);
		
		ItemStack shield = new ItemStack(Material.SHIELD);
		ItemMeta shieldMeta = shield.getItemMeta();
		shieldMeta.setDisplayName(ChatColor.GOLD + "NCO Shield");
		shieldMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A shield used by", ChatColor.AQUA + "a marine.")));
		shield.setItemMeta(shieldMeta);
		
		ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		ItemMeta chestplateMeta = chestplate.getItemMeta();
		chestplateMeta.setDisplayName(ChatColor.GOLD + "Marine's Chestplate");
		chestplateMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A chainmail chestplate worn", ChatColor.AQUA + "by a marine.")));
		chestplateMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		chestplate.setItemMeta(chestplateMeta);
		
		ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
		ItemMeta leggingsMeta = leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Marine's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Chainmail leggings worn by", ChatColor.AQUA + "by a marine.")));
		leggingsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		leggings.setItemMeta(leggingsMeta);
		
		ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
		ItemMeta bootsMeta = boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Marine's boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Chainmail boots worn by", ChatColor.AQUA + "by a marine.")));
		bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		boots.setItemMeta(bootsMeta);
		
		items.add(sword);
		armor.add(shield);
		armor.add(chestplate);
		armor.add(leggings);
		armor.add(boots);
		
		return new ControlPointClass(name, level, lore, items, armor, potions);
	}
}
