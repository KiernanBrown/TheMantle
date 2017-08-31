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

public class HeroHephaestus {
	String name = "Hephaestus";
	int health = 30;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "A deranged fire mage who", ChatColor.LIGHT_PURPLE + "aims to incinerate the other", ChatColor.LIGHT_PURPLE + "teams in any way possible."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	String passive = "Killing a player will leave fire in their place.";
	String active = "Eruption: Fireballs shoot out of you in all directions. Fire is started on the ground randomly near you. You receive Fire Resistance for 15 seconds.";
	String quote = "\"My desire is to see you turn to ash!\"";
	
	public ControlPointHero returnHero()
	{			
		ItemStack volcanicwand = new ItemStack(Material.BLAZE_ROD);
		ItemMeta volcanicwandMeta = volcanicwand.getItemMeta();
		volcanicwandMeta.setDisplayName(ChatColor.GOLD + "Volcanic Wand");
		volcanicwandMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
		volcanicwand.setItemMeta(volcanicwandMeta);
		
		ItemStack eruption = new ItemStack(Material.FLINT_AND_STEEL);
		ItemMeta eruptionMeta = eruption.getItemMeta();
		eruptionMeta.setDisplayName(ChatColor.GOLD + "Eruption");
		eruptionMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Active Ability", ChatColor.LIGHT_PURPLE + "Cause an Eruption, shooting", ChatColor.LIGHT_PURPLE + "fireballs and setting fire", net.md_5.bungee.api.ChatColor.LIGHT_PURPLE + "around you!")));
		eruption.setItemMeta(eruptionMeta);
		
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		LeatherArmorMeta helmetMeta = (LeatherArmorMeta)helmet.getItemMeta();
		helmetMeta.setDisplayName(ChatColor.GOLD + "Hephaestus's Helmet");
		helmetMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Hephaestus's leather helmet.")));
		helmetMeta.setColor(Color.YELLOW);
		helmetMeta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
		helmet.setItemMeta(helmetMeta);
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta chestplateMeta = (LeatherArmorMeta)chestplate.getItemMeta();
		chestplateMeta.setDisplayName(ChatColor.GOLD + "Hephaestus's Chestplate");
		chestplateMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Hephaestus's leather chestplate.")));
		chestplateMeta.setColor(Color.ORANGE);
		chestplateMeta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
		chestplate.setItemMeta(chestplateMeta);
		
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		LeatherArmorMeta leggingsMeta = (LeatherArmorMeta)leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Hephaestus's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Hephaestus's leather leggings.")));
		leggingsMeta.setColor(Color.RED);
		leggingsMeta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
		leggings.setItemMeta(leggingsMeta);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta bootsMeta = (LeatherArmorMeta)boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Hephaestus's Boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Hephaestus's leather boots.")));
		bootsMeta.setColor(Color.BLACK);
		bootsMeta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
		boots.setItemMeta(bootsMeta);
		
		items.add(volcanicwand);
		items.add(eruption);
		armor.add(helmet);
		armor.add(chestplate);
		armor.add(leggings);
		armor.add(boots);
		
		return new ControlPointHero(name, lore, items, armor, potions, health, passive, active, quote);
	}
}
