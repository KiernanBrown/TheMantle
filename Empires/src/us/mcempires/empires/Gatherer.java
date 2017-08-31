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
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class Gatherer {
	String name = "Gatherer";
	int level = 44;
	int food = 13;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Gatherer", ChatColor.AQUA + "A class equipped with specialized", ChatColor.AQUA+ "tools, but with a drawback.", ChatColor.DARK_GREEN + "Food Cost: " + food));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>(Arrays.asList(new PotionEffect(PotionEffectType.SLOW, 40000, 1)));
	
	public EmpiresClass returnClass()
	{
		ItemStack axe = new ItemStack(Material.IRON_AXE);
		ItemMeta axeMeta = axe.getItemMeta();
		axeMeta.setDisplayName(ChatColor.GOLD + "Gatherer's Axe");
		axeMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "An iron axe used by", ChatColor.AQUA + "a gatherer.", ChatColor.LIGHT_PURPLE + "Class Item")));
		axeMeta.addEnchant(Enchantment.DIG_SPEED, 3, true);
		axe.setItemMeta(axeMeta);
		
		ItemStack pickaxe = new ItemStack(Material.IRON_PICKAXE);
		ItemMeta pickaxeMeta = pickaxe.getItemMeta();
		pickaxeMeta.setDisplayName(ChatColor.GOLD + "Gatherer's Pickaxe");
		pickaxeMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "An iron pickaxe used by", ChatColor.AQUA + "a gatherer.", ChatColor.LIGHT_PURPLE + "Class Item")));
		pickaxeMeta.addEnchant(Enchantment.DIG_SPEED, 3, true);
		pickaxe.setItemMeta(pickaxeMeta);
		
		ItemStack shovel = new ItemStack(Material.IRON_SPADE);
		ItemMeta shovelMeta = shovel.getItemMeta();
		shovelMeta.setDisplayName(ChatColor.GOLD + "Gatherer's Shovel");
		shovelMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "An iron shovel used by", ChatColor.AQUA + "a gatherer.", ChatColor.LIGHT_PURPLE + "Class Item")));
		shovelMeta.addEnchant(Enchantment.DIG_SPEED, 3, true);
		shovel.setItemMeta(shovelMeta);
		
		ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		ItemMeta chestplateMeta = chestplate.getItemMeta();
		chestplateMeta.setDisplayName(ChatColor.GOLD + "Gatherer's Chestplate");
		chestplateMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A chainmail chestplate worn by", ChatColor.AQUA + "a gatherer.", ChatColor.LIGHT_PURPLE + "Class Item")));
		chestplateMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		chestplate.setItemMeta(chestplateMeta);
		
		items.add(axe);
		items.add(pickaxe);
		items.add(shovel);
		armor.add(chestplate);
		
		return new EmpiresClass(name, level, food, lore, items, armor, potions);
	}
}
