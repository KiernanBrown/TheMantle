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

public class HeroNoiresse 
{
	String name = "Noiresse";
	int health = 30;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "A queen of darkness who", ChatColor.LIGHT_PURPLE + "deprives the enemy teams of", ChatColor.LIGHT_PURPLE + "their senses."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	String passive = "Immune to blindness. Killing an enemy has a chance of giving a Blindness Orb.";
	String active = "Black Hole: Pull nearby enemies towards you for 8 seconds, giving potion effects and dealing damage as they get closer. You cannot move during this time.";
	String quote = "\"This world is contaminated as long as there is still light!\"";
	
	public ControlPointHero returnHero()
	{
		ItemStack voidwand = new ItemStack(Material.STONE_HOE);
		ItemMeta voidwandMeta = voidwand.getItemMeta();
		voidwandMeta.setDisplayName(ChatColor.GOLD + "Void Scythe");
		voidwandMeta.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
		voidwandMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
		voidwand.setItemMeta(voidwandMeta);
		
		ItemStack blackHole = new ItemStack(Material.EYE_OF_ENDER);
		ItemMeta blackHoleMeta = blackHole.getItemMeta();
		blackHoleMeta.setDisplayName(ChatColor.GOLD + "Black Hole");
		blackHoleMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Active Ability", ChatColor.LIGHT_PURPLE + "Pull enemies into you with a", ChatColor.LIGHT_PURPLE + "black hole, but make yourself", ChatColor.LIGHT_PURPLE + "immobile while doing so!")));
		blackHole.setItemMeta(blackHoleMeta);
		
		ItemStack blindnessOrbs = new ItemStack(Material.ENDER_PEARL, 3);
		ItemMeta blindnessOrbsMeta = blindnessOrbs.getItemMeta();
		blindnessOrbsMeta.setDisplayName(ChatColor.GOLD + "Blindness Orb");
		blindnessOrbs.setItemMeta(blindnessOrbsMeta);
		
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		LeatherArmorMeta helmetMeta = (LeatherArmorMeta)helmet.getItemMeta();
		helmetMeta.setDisplayName(ChatColor.GOLD + "Noiresse's Helmet");
		helmetMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Noiresse's leather helmet.")));
		helmetMeta.setColor(Color.BLACK);
		helmet.setItemMeta(helmetMeta);
		
		ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
		ItemMeta leggingsMeta = leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Noiresse's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Noiresse's chainmail leggings.")));
		leggings.setItemMeta(leggingsMeta);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta bootsMeta = (LeatherArmorMeta)boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Noiresse's Boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Noiresse's leather boots.")));
		bootsMeta.setColor(Color.BLACK);
		boots.setItemMeta(bootsMeta);
		
		items.add(voidwand);
		items.add(blindnessOrbs);
		items.add(blackHole);
		armor.add(helmet);
		armor.add(leggings);
		armor.add(boots);
		
		return new ControlPointHero(name, lore, items, armor, potions, health, passive, active, quote);
	}
}
