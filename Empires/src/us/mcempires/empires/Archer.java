package us.mcempires.empires;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import net.md_5.bungee.api.ChatColor;

public class Archer
{
	String name = "Archer";
	int level = 16;
	int food = 5;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Archer", ChatColor.AQUA + "A basic ranged class with a", ChatColor.AQUA+ "bow and some armor.", ChatColor.DARK_GREEN + "Food Cost: " + food));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	
	public EmpiresClass returnClass()
	{
		ItemStack bow = new ItemStack(Material.BOW);
		ItemMeta bowMeta = bow.getItemMeta();
		bowMeta.setDisplayName(ChatColor.GOLD + "Archer's Bow");
		bowMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A bow used by an archer.", ChatColor.LIGHT_PURPLE + "Class Item")));
		bow.setItemMeta(bowMeta);
		
		ItemStack arrows = new ItemStack(Material.ARROW, 24);
		ItemMeta arrowMeta = arrows.getItemMeta();
		arrowMeta.setDisplayName(ChatColor.GOLD + "Archer's Arrows");
		bowMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Arrows used by an archer.", ChatColor.LIGHT_PURPLE + "Class Item")));
		arrows.setItemMeta(arrowMeta);
		
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemMeta leggingsMeta = leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Archer's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Leather leggings worn by", ChatColor.AQUA + "an archer.", ChatColor.LIGHT_PURPLE + "Class Item")));
		leggings.setItemMeta(leggingsMeta);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		ItemMeta bootsMeta = boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Archer's Boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Leather boots worn by", ChatColor.AQUA + "an archer.", ChatColor.LIGHT_PURPLE + "Class Item")));
		boots.setItemMeta(bootsMeta);
		
		items.add(bow);
		items.add(arrows);
		armor.add(leggings);
		armor.add(boots);
		
		return new EmpiresClass(name, level, food, lore, items, armor, potions);
	}
}