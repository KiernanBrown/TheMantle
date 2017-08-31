package us.mcempires.empires;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;

import net.md_5.bungee.api.ChatColor;

public class Barbarian 
{
	String name = "Barbarian";
	int level = 12;
	int food = 6;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Barbarian", ChatColor.AQUA + "A melee class that specializes", ChatColor.AQUA+ "in axes and brute force.", ChatColor.DARK_GREEN + "Food Cost: " + food));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	
	public EmpiresClass returnClass()
	{
		ItemStack axe = new ItemStack(Material.STONE_AXE);
		ItemMeta axeMeta = axe.getItemMeta();
		axeMeta.setDisplayName(ChatColor.GOLD + "Barbarian's Axe");
		axeMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A stone axe used by", ChatColor.AQUA + "a barbarian.", ChatColor.LIGHT_PURPLE + "Class Item")));
		axeMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		axe.setItemMeta(axeMeta);
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
		chestplateMeta.setDisplayName(ChatColor.GOLD + "Barbarian's Chestplate");
		chestplateMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A strong leather chestplate worn", ChatColor.AQUA + "by a barbarian.", ChatColor.LIGHT_PURPLE + "Class Item")));
		chestplateMeta.setColor(Color.GRAY);
		chestplateMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		chestplate.setItemMeta(chestplateMeta);
		
		items.add(axe);
		armor.add(chestplate);
		
		return new EmpiresClass(name, level, food, lore, items, armor, potions);
	}
}
