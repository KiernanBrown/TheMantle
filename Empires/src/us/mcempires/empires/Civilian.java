package us.mcempires.empires;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class Civilian 
{
	String name = "Civilian";
	int level = 1;
	int food = 0;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Civilian", ChatColor.AQUA + "The starting class, which", ChatColor.AQUA+ "has nothing.", ChatColor.DARK_GREEN + "Food Cost: 0"));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	
	public EmpiresClass returnClass()
	{	
		return new EmpiresClass(name, level, food, lore, items, armor, potions);
	}

}
