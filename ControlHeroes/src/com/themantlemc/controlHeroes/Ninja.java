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

public class Ninja 
{
		
	String name = "Ninja";
	int level = 10;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Ninja", ChatColor.AQUA + "A basic melee class with a", ChatColor.AQUA+ "wooden dagger and armor."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
		
	public ControlPointClass returnClass()
	{
		ItemStack dagger = new ItemStack(Material.WOOD_SWORD);
		ItemMeta daggerMeta = dagger.getItemMeta();
		daggerMeta.setDisplayName(ChatColor.GOLD + "Nordic Dagger");
		daggerMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A wooden dagger used by", ChatColor.AQUA + "a ninja.")));
		daggerMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		daggerMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		dagger.setItemMeta(daggerMeta);
			
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		ItemMeta bootsMeta = boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Ninja's boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Leather boots worn by", ChatColor.AQUA + "by a ninja.")));
		//bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		boots.setItemMeta(bootsMeta);
			
		items.add(dagger);
		armor.add(boots);
		potions.add(new PotionEffect(PotionEffectType.INVISIBILITY, 400000, 2));
		potions.add(new PotionEffect(PotionEffectType.SPEED, 400000, 0));
			
		return new ControlPointClass(name, level, lore, items, armor, potions);
	}
}
