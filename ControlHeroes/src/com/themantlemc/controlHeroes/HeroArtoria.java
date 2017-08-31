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
import org.bukkit.potion.PotionEffectType;

public class HeroArtoria {
	String name = "Artoria";
	int health = 40;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "A proud warrior that fights", ChatColor.LIGHT_PURPLE + "with honour, and won't go down", ChatColor.LIGHT_PURPLE + "without a fight."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	String passive = "When you run out of Hero Armor, your damage and speed increase, but you burst into flames!";
	String active = "Divine Light: Shoot a beam of divine light, dealing damage to all enemies in its path and setting them on fire for a short amount of time!";
	String quote = "\"Only brute warriors find glory in destruction!\"";
	
	public ControlPointHero returnHero()
	{
		ItemStack excalibur = new ItemStack(Material.GOLD_SWORD);
		ItemMeta excaliburMeta = excalibur.getItemMeta();
		excaliburMeta.setDisplayName(ChatColor.GOLD + "Excalibur");
		excaliburMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		excalibur.setItemMeta(excaliburMeta);
		
		ItemStack divine = new ItemStack(Material.GLOWSTONE);
		ItemMeta divineMeta = divine.getItemMeta();
		divineMeta.setDisplayName(ChatColor.GOLD + "Divine Light");
		divineMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Active Ability", ChatColor.LIGHT_PURPLE + "Shoot a wave of Divine Light", ChatColor.LIGHT_PURPLE + "to damage all enemies in", ChatColor.LIGHT_PURPLE + "your path!")));
		divine.setItemMeta(divineMeta);
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta chestplateMeta = (LeatherArmorMeta)chestplate.getItemMeta();
		chestplateMeta.setDisplayName(ChatColor.GOLD + "Artoria's Chestplate");
		chestplateMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Artoria's leather chestplate.")));
		chestplateMeta.addEnchant(Enchantment.PROTECTION_FIRE, 2, true);
		chestplateMeta.setColor(Color.fromRGB(150, 10, 0));
		chestplate.setItemMeta(chestplateMeta);
		
		ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
		ItemMeta leggingsMeta = leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Artoria's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Artoria's iron leggings.")));
		leggings.setItemMeta(leggingsMeta);
		
		ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
		ItemMeta bootsMeta = boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Artoria's boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Artoria's chainmail boots.")));
		boots.setItemMeta(bootsMeta);
		
		items.add(excalibur);
		items.add(divine);
		armor.add(chestplate);
		armor.add(leggings);
		armor.add(boots);
		
		return new ControlPointHero(name, lore, items, armor, potions, health, passive, active, quote);
	}
}
