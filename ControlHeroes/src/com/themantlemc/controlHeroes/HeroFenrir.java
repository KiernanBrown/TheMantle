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

public class HeroFenrir 
{
	
	String name = "Fenrir";
	int health = 30;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "A cunning werewolf who disables", ChatColor.LIGHT_PURPLE + "enemies with fast, calculated", ChatColor.LIGHT_PURPLE + "attacks."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	String passive = "Killing an enemy grants a temporary speed and jump boost.";
	String active = "Wolf Howl: Gives Strength 1 for 10 seconds, and makes your attacks wither enemies.";
	String quote = "\"By the time you hear me approach you'll already be dead!\"";
	
	public ControlPointHero returnHero()
	{	
		ItemStack fang = new ItemStack(Material.SHEARS);
		ItemMeta fangMeta = fang.getItemMeta();
		fangMeta.setDisplayName(ChatColor.GOLD + "Fenrir's Fang");
		fangMeta.addEnchant(Enchantment.DAMAGE_ALL, 8, true);
		fangMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		fang.setItemMeta(fangMeta);
		
		ItemStack collector = new ItemStack(Material.FISHING_ROD);
		ItemMeta collectorMeta = collector.getItemMeta();
		collectorMeta.setDisplayName(ChatColor.GOLD + "The Collector");
		collectorMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Pull enemies to you with", ChatColor.AQUA + "this powerful grab!")));
		collectorMeta.addEnchant(Enchantment.LURE, 10, true);
		collectorMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		collector.setItemMeta(collectorMeta);
		
		ItemStack howl = new ItemStack(Material.BONE);
		ItemMeta howlMeta = howl.getItemMeta();
		howlMeta.setDisplayName(ChatColor.GOLD + "Wolf Howl");
		howlMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Active Ability", ChatColor.LIGHT_PURPLE + "Channel your wolf strength to", ChatColor.LIGHT_PURPLE + "increase damage and wither enemies!")));
		howl.setItemMeta(howlMeta);
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta chestplateMeta = (LeatherArmorMeta)chestplate.getItemMeta();
		chestplateMeta.setDisplayName(ChatColor.GOLD + "Fenrir's Chestplate");
		chestplateMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A leather chestplate worn", ChatColor.AQUA + "by Fenrir.")));
		chestplateMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		chestplateMeta.setColor(Color.GRAY);
		chestplate.setItemMeta(chestplateMeta);
		
		items.add(fang);
		items.add(collector);
		items.add(howl);
		armor.add(chestplate);
		
		return new ControlPointHero(name, lore, items, armor, potions, health, passive, active, quote);
	}
}
