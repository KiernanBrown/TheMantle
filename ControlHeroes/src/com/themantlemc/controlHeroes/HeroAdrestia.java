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

public class HeroAdrestia {
	String name = "Adrestia";
	int health = 30;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "A fighter for justice that", ChatColor.LIGHT_PURPLE + "balances the fight in order to", ChatColor.LIGHT_PURPLE + "nullify any advantage."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	String activeName = "Balancing Act";
	String passiveName = "Justice Served";
	String passive = "Killing a player gives you an absorption effect.";
	String active = "Nearby players have all potion effects nullified for 10 seconds. You receive Absorption for 10 seconds.";
	String quote = "\"I shall bring swift justice, for we fight as equals on the battlefield!\"";
	
	public ControlPointHero returnHero()
	{
		ItemStack justice = new ItemStack(Material.IRON_SWORD);
		ItemMeta justiceMeta = justice.getItemMeta();
		justiceMeta.setDisplayName(ChatColor.GOLD + "Justice");
		justiceMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		justice.setItemMeta(justiceMeta);
		
		ItemStack balance = new ItemStack(Material.BEACON);
		ItemMeta balanceMeta = balance.getItemMeta();
		balanceMeta.setDisplayName(ChatColor.GOLD + "Balancing Act");
		balanceMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Active Ability", ChatColor.AQUA + "Nullifies potion effects of all nearby", ChatColor.AQUA + "players when used, and gives", ChatColor.AQUA + "absorption for 10 seconds!")));
		balance.setItemMeta(balanceMeta);
		
		ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
		ItemMeta helmetMeta = helmet.getItemMeta();
		helmetMeta.setDisplayName(ChatColor.GOLD + "Adrestia's Helmet");
		helmetMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Adrestia's diamond helmet.")));
		helmet.setItemMeta(helmetMeta);
		
		ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
		ItemMeta leggingsMeta = leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Adrestia's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Adrestia's iron leggings.")));
		leggings.setItemMeta(leggingsMeta);
		
		ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
		ItemMeta bootsMeta = boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Adrestia's boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Adrestia's chainmail boots.")));
		boots.setItemMeta(bootsMeta);
		
		items.add(justice);
		items.add(balance);
		armor.add(helmet);
		armor.add(leggings);
		armor.add(boots);
		return new ControlPointHero(name, lore, items, armor, potions, health, passive, active, quote);
	}
}
