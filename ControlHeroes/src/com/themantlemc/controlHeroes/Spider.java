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

public class Spider {

	String name = "Spider";
	int level = 7;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Spider", ChatColor.AQUA + "A melee class with", ChatColor.AQUA+ "improved jumps."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	
	public ControlPointClass returnClass()
	{
		ItemStack sword = new ItemStack(Material.STONE_SWORD);
		ItemMeta swordMeta = sword.getItemMeta();
		swordMeta.setDisplayName(ChatColor.GOLD + "Webbed Sword");
		swordMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A stone sword used by", ChatColor.AQUA + "a spider.")));
		swordMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		sword.setItemMeta(swordMeta);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		ItemMeta bootsMeta = boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Spider's boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Leather boots worn by", ChatColor.AQUA + "by a spider.")));
		boots.setItemMeta(bootsMeta);
		
		items.add(sword);
		armor.add(boots);
		potions.add(new PotionEffect(PotionEffectType.JUMP, 400000, 1));
		
		return new ControlPointClass(name, level, lore, items, armor, potions);
	}
	
}
