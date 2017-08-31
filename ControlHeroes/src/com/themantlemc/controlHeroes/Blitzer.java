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

public class Blitzer {
	String name = "Blitzer";
	int level = 52;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Blitzer", ChatColor.AQUA + "A demolitionist equipped with", ChatColor.AQUA+ "a sword, a grenade, and IEDs."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	
	public ControlPointClass returnClass()
	{
		ItemStack sword = new ItemStack(Material.WOOD_SWORD);
		ItemMeta swordMeta = sword.getItemMeta();
		swordMeta.setDisplayName(ChatColor.GOLD + "Wooden Longsword");
		swordMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A wooden sword used by", ChatColor.AQUA + "a blitzer.")));
		swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
		swordMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		sword.setItemMeta(swordMeta);
		
		ItemStack grenades = new ItemStack(Material.EGG, 1);
		ItemMeta grenadesMeta = grenades.getItemMeta();
		grenadesMeta.setDisplayName(ChatColor.GOLD + "Hand Grenade");
		grenades.setItemMeta(grenadesMeta);
		
		ItemStack ied = new ItemStack(Material.STRING, 2);
		ItemMeta iedMeta = ied.getItemMeta();
		iedMeta.setDisplayName(ChatColor.GOLD + "IED");
		ied.setItemMeta(iedMeta);
		
		ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
		ItemMeta leggingsMeta = leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Blitzer's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Chainmail leggings worn by", ChatColor.AQUA + "by a blitzer.")));
		leggings.setItemMeta(leggingsMeta);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		ItemMeta bootsMeta = boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Blitzer's Boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Leather boots worn by", ChatColor.AQUA + "by a blitzer.")));
		bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		boots.setItemMeta(bootsMeta);
		
		items.add(sword);
		items.add(ied);
		items.add(grenades);
		armor.add(leggings);
		armor.add(boots);
		
		return new ControlPointClass(name, level, lore, items, armor, potions);
	}
}
