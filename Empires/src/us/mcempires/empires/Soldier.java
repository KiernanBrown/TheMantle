package us.mcempires.empires;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import net.md_5.bungee.api.ChatColor;

public class Soldier
{
	String name = "Soldier";
	int level = 3;
	int food = 3;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Soldier", ChatColor.AQUA + "A basic melee class with a", ChatColor.AQUA+ "wooden sword and some armor.", ChatColor.DARK_GREEN + "Food Cost: " + food));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	
	public EmpiresClass returnClass()
	{
		ItemStack sword = new ItemStack(Material.WOOD_SWORD);
		ItemMeta swordMeta = sword.getItemMeta();
		swordMeta.setDisplayName(ChatColor.GOLD + "Soldier's Sword");
		swordMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A wooden sword used by", ChatColor.AQUA + "a soldier.", ChatColor.LIGHT_PURPLE + "Class Item")));
		sword.setItemMeta(swordMeta);
		
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemMeta leggingsMeta = leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Soldier's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Leather leggings worn by", ChatColor.AQUA + "a soldier.", ChatColor.LIGHT_PURPLE + "Class Item")));
		leggings.setItemMeta(leggingsMeta);
		
		items.add(sword);
		armor.add(leggings);
		
		return new EmpiresClass(name, level, food, lore, items, armor, potions);
	}
}
