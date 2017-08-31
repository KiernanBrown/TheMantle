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

public class HeroGilgamesh {	
	String name = "Gilgamesh";
	int health = 30;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "An experienced archer with", ChatColor.LIGHT_PURPLE + "powers that can manipulate", ChatColor.LIGHT_PURPLE + "arrows in impossible ways."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	String passive = "Fully charged bow fires a stronger arrow that has a chance of being homing arrows.";
	String active = "Gate of Babylon: Open the Gate of Babylon to fire 6 waves of arrows in front of you.";
	String quote = "\"May your scattered remains entertain me, mongrel!\"";
	
	public ControlPointHero returnHero()
	{	
		ItemStack archerBow = new ItemStack(Material.BOW);
		ItemMeta aBowMeta = archerBow.getItemMeta();
		aBowMeta.setDisplayName(ChatColor.GOLD + "Gilgamesh's Bow");
		aBowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		aBowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		aBowMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		archerBow.setItemMeta(aBowMeta);
		
		ItemStack arrow = new ItemStack(Material.ARROW);
		
		ItemStack gate = new ItemStack(Material.ENDER_PORTAL_FRAME);
		ItemMeta gateMeta = gate.getItemMeta();
		gateMeta.setDisplayName(ChatColor.GOLD + "Gate of Babylon");
		gateMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Active Ability", ChatColor.LIGHT_PURPLE + "Open the Gate of Babylon", ChatColor.LIGHT_PURPLE + "and fill your enemies with arrows!")));
		gate.setItemMeta(gateMeta);
		
		ItemStack chestplate = new ItemStack(Material.GOLD_CHESTPLATE);
		ItemMeta chestplateMeta = chestplate.getItemMeta();
		chestplateMeta.setDisplayName(ChatColor.GOLD + "Gilgamesh's Chestplate");
		chestplateMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A gold chestplate worn", ChatColor.AQUA + "by Gilgamesh.")));
		chestplateMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		chestplate.setItemMeta(chestplateMeta);
		
		ItemStack leggings = new ItemStack(Material.GOLD_LEGGINGS);
		ItemMeta leggingsMeta = leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Gilgamesh's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Gold leggings worn", ChatColor.AQUA + "by Gilgamesh.")));
		leggingsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		leggings.setItemMeta(leggingsMeta);
		
		ItemStack boots = new ItemStack(Material.GOLD_BOOTS);
		ItemMeta bootsMeta = boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Gilgamesh's boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Gold boots worn", ChatColor.AQUA + "by Gilgamesh.")));
		bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		boots.setItemMeta(bootsMeta);
		
		items.add(archerBow);
		items.add(arrow);
		items.add(gate);
		armor.add(chestplate);
		armor.add(leggings);
		armor.add(boots);
		potions.add(new PotionEffect(PotionEffectType.SLOW, 400000, 0));
		
		return new ControlPointHero(name, lore, items, armor, potions, health, passive, active, quote);
	}
}