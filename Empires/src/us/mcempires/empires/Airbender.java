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

public class Airbender 
{
	String name = "Airbender";
	int level = 22;
	int food = 7;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Airbender", ChatColor.AQUA + "Control the power of air and", ChatColor.AQUA+ "take to the skies with your glider", ChatColor.DARK_GREEN + "Food Cost: " + food));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>(Arrays.asList(new PotionEffect(PotionEffectType.JUMP, 40000, 0)));
	
	public EmpiresClass returnClass()
	{
		ItemStack staff = new ItemStack(Material.STICK);
		ItemMeta staffMeta = staff.getItemMeta();
		staffMeta.setDisplayName(ChatColor.GOLD + "Airbending Staff");
		staffMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A wooden staff used by", ChatColor.AQUA + "an airbender.", ChatColor.LIGHT_PURPLE + "Class Item")));
		staffMeta.addEnchant(Enchantment.KNOCKBACK, 3, true);
		staffMeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
		staff.setItemMeta(staffMeta);
		
		ItemStack glider = new ItemStack(Material.ELYTRA);
		ItemMeta gliderMeta = glider.getItemMeta();
		gliderMeta.setDisplayName(ChatColor.GOLD + "Glider");
		gliderMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A glider used used by", ChatColor.AQUA + "an airbender.", ChatColor.LIGHT_PURPLE + "Class Item")));
		glider.setItemMeta(gliderMeta);
		
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Airbender's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Leather leggings worn by", ChatColor.AQUA + "an airbender.", ChatColor.LIGHT_PURPLE + "Class Item")));
		leggingsMeta.setColor(Color.YELLOW);
		leggings.setItemMeta(leggingsMeta);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Airbender's Boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Leather boots worn by", ChatColor.AQUA + "an airbender.", ChatColor.LIGHT_PURPLE + "Class Item")));
		bootsMeta.setColor(Color.RED);
		bootsMeta.addEnchant(Enchantment.PROTECTION_FALL, 4, true);
		boots.setItemMeta(bootsMeta);
		
		items.add(staff);
		armor.add(glider);
		armor.add(leggings);
		armor.add(boots);
		
		return new EmpiresClass(name, level, food, lore, items, armor, potions);
	}
}
