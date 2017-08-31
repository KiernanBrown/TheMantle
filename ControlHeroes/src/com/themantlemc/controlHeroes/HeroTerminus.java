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

public class HeroTerminus {
	String name = "Terminus";
	int health = 40;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "A steadfast stone golem with", ChatColor.LIGHT_PURPLE + "powerful defensive abilities."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	String passive = "Killing a player has a chance of giving you and nearby teammates resistance for 6 seconds.";
	String active = "Stone Morph: Transform into stone, becoming immobile and invulnerable. Any enemy that hits you during this time is knocked back and takes damage. Nearby teammates are given resistance.";
	String quote = "\"To be one with the Earth is to be unwavering, relentless, resolute!\"";
	
	public ControlPointHero returnHero()
	{
		
		ItemStack terra = new ItemStack(Material.STONE_SWORD);
		ItemMeta terraMeta = terra.getItemMeta();
		terraMeta.setDisplayName(ChatColor.GOLD + "Terra");
		terraMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
		terraMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		terra.setItemMeta(terraMeta);
		
		ItemStack stone = new ItemStack(Material.STONE);
		ItemMeta stoneMeta = stone.getItemMeta();
		stoneMeta.setDisplayName(ChatColor.GOLD + "Stone Morph");
		stoneMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Active Ability", ChatColor.LIGHT_PURPLE + "Morph into stone for 5 seconds,", ChatColor.LIGHT_PURPLE + "becoming immobile, invulnerable, and", ChatColor.LIGHT_PURPLE + "giving nearby teammates resistance!")));
		stone.setItemMeta(stoneMeta);
		
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		LeatherArmorMeta helmetMeta = (LeatherArmorMeta)helmet.getItemMeta();
		helmetMeta.setDisplayName(ChatColor.GOLD + "Terminus's Helmet");
		helmetMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Terminus's leather helmet.")));
		helmetMeta.setColor(Color.GRAY);
		helmet.setItemMeta(helmetMeta);
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta chestplateMeta = (LeatherArmorMeta)chestplate.getItemMeta();
		chestplateMeta.setDisplayName(ChatColor.GOLD + "Terminus's Chestplate");
		chestplateMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Terminus's leather chestplate.")));
		chestplateMeta.setColor(Color.GRAY);
		chestplate.setItemMeta(chestplateMeta);
		
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		LeatherArmorMeta leggingsMeta = (LeatherArmorMeta)leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Terminus's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Terminus's leather leggings.")));
		leggingsMeta.setColor(Color.GRAY);
		leggings.setItemMeta(leggingsMeta);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta bootsMeta = (LeatherArmorMeta)boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Terminus's Boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Terminus's leather boots.")));
		bootsMeta.setColor(Color.GRAY);
		boots.setItemMeta(bootsMeta);
		
		items.add(terra);
		items.add(stone);
		armor.add(helmet);
		armor.add(chestplate);
		armor.add(leggings);
		armor.add(boots);
		
		return new ControlPointHero(name, lore, items, armor, potions, health, passive, active, quote);
	}
}
