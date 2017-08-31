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

public class Soldier {
	
	String name = "Soldier";
	int level = 15;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Soldier", ChatColor.AQUA + "A melee class weilding", ChatColor.AQUA+ "a strong stone sword."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	
	public ControlPointClass returnClass()
	{
		ItemStack sword = new ItemStack(Material.STONE_SWORD);
		ItemMeta swordMeta = sword.getItemMeta();
		swordMeta.setDisplayName(ChatColor.GOLD + "Ancient Stone Sword");
		swordMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A stone sword used by", ChatColor.AQUA + "a soldier.")));
		swordMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		sword.setItemMeta(swordMeta);
		
		ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		ItemMeta chestplateMeta = chestplate.getItemMeta();
		chestplateMeta.setDisplayName(ChatColor.GOLD + "Soldier's Chestplate");
		chestplateMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A chainmail chestplate worn", ChatColor.AQUA + "by a soldier.")));
		chestplate.setItemMeta(chestplateMeta);
		
		ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
		ItemMeta leggingsMeta = leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Soldier's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Chainmail leggings worn by", ChatColor.AQUA + "by a soldier.")));
		leggings.setItemMeta(leggingsMeta);
		
		ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
		ItemMeta bootsMeta = boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Soldier's boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Chainmail boots worn by", ChatColor.AQUA + "by a soldier.")));
		boots.setItemMeta(bootsMeta);
		
		items.add(sword);
		armor.add(chestplate);
		armor.add(leggings);
		armor.add(boots);
		
		return new ControlPointClass(name, level, lore, items, armor, potions);
	}
}
