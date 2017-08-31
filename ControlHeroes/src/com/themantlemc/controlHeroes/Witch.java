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
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

public class Witch {

	String name = "Witch";
	int level = 40; //20
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Witch", ChatColor.AQUA + "A magic class specializing", ChatColor.AQUA+ "in potions."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	
	public ControlPointClass returnClass()
	{
		ItemStack wand = new ItemStack(Material.BLAZE_ROD);
		ItemMeta wandMeta = wand.getItemMeta();
		wandMeta.setDisplayName(ChatColor.GOLD + "Brewing Wand");
		wandMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A magic wand used by", ChatColor.AQUA + "a witch.")));
		wandMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
		wand.setItemMeta(wandMeta);
		
		ItemStack harm = new Potion(PotionType.INSTANT_DAMAGE, 1, true).toItemStack(2);
		
		ItemStack poison = new Potion(PotionType.POISON, 1, true).toItemStack(2);
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta chestplateMeta = (LeatherArmorMeta)chestplate.getItemMeta();
		chestplateMeta.setDisplayName(ChatColor.GOLD + "Witch's Chestplate");
		chestplateMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A leather chestplate worn", ChatColor.AQUA + "by a witch.")));
		chestplateMeta.setColor(Color.GRAY);
		chestplateMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		chestplate.setItemMeta(chestplateMeta);
		
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		LeatherArmorMeta leggingsMeta = (LeatherArmorMeta)leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Witch's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Leather leggings worn", ChatColor.AQUA + "by a witch.")));
		leggingsMeta.setColor(Color.GRAY);
		leggings.setItemMeta(leggingsMeta);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta bootsMeta = (LeatherArmorMeta)boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Witch's boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Leather boots worn", ChatColor.AQUA + "by a witch.")));
		bootsMeta.setColor(Color.GRAY);
		boots.setItemMeta(bootsMeta);
		
		items.add(wand);
		items.add(harm);
		items.add(poison);
		armor.add(chestplate);
		armor.add(leggings);
		armor.add(boots);
		
		return new ControlPointClass(name, level, lore, items, armor, potions);
	}	
}
