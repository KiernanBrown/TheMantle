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

public class Marksman {
	String name = "Marksman";
	int level = 60;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Marksman", ChatColor.AQUA + "A powerful archer equipped", ChatColor.AQUA+ "with a flaming bow."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	
	public ControlPointClass returnClass()
	{
		ItemStack bow = new ItemStack(Material.BOW);
		ItemMeta bowMeta = bow.getItemMeta();
		bowMeta.setDisplayName(ChatColor.GOLD + "Marksman's Bow");
		bowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		bowMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
		bowMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		bowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		bow.setItemMeta(bowMeta);
		
		ItemStack arrow = new ItemStack(Material.ARROW);
		
		ItemStack blazeOrb = new ItemStack(Material.MAGMA_CREAM);
		ItemMeta blazeOrbMeta = blazeOrb.getItemMeta();
		blazeOrbMeta.setDisplayName(ChatColor.GOLD + "Blaze Orb");
		blazeOrb.setItemMeta(blazeOrbMeta);
		
		ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
		ItemMeta leggingsMeta = leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Marksman's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Chainmail leggings worn by", ChatColor.AQUA + "by a marksman.")));
		leggings.setItemMeta(leggingsMeta);
		
		ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
		ItemMeta bootsMeta = boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Marksman's boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Chainmail boots worn by", ChatColor.AQUA + "by a marksman.")));
		boots.setItemMeta(bootsMeta);
		
		items.add(bow);
		items.add(arrow);
		items.add(blazeOrb);
		armor.add(leggings);
		armor.add(boots);
		
		return new ControlPointClass(name, level, lore, items, armor, potions);
	}
}
