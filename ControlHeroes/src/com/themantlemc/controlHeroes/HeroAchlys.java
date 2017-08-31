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
import org.bukkit.potion.PotionEffectType;

public class HeroAchlys {
	String name = "Achlys";
	int health = 30;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "A goddess of misery who spreads", ChatColor.LIGHT_PURPLE + "her contagion across the", ChatColor.LIGHT_PURPLE + "battlefield."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	String passive = "Hitting a player with an arrow has a chance of poisoning them. You received reduced poison durations.";
	String active = "Contagion: Shoots wither skulls in all directions, giving enemies a contagious wither effect!";
	String quote = "\"These infected souls will perish by my own will!\"";
	
	public ControlPointHero returnHero()
	{
		ItemStack plagueBow = new ItemStack(Material.BOW);
		ItemMeta pBowMeta = plagueBow.getItemMeta();
		pBowMeta.setDisplayName(ChatColor.GOLD + "Plague Bow");
		pBowMeta.setLore(new ArrayList<>(Arrays.asList()));
		pBowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		pBowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		pBowMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		plagueBow.setItemMeta(pBowMeta);
		
		ItemStack arrow = new ItemStack(Material.ARROW);
		
		ItemStack contagion = new ItemStack(Material.SUGAR_CANE);
		ItemMeta contagionMeta = contagion.getItemMeta();
		contagionMeta.setDisplayName(ChatColor.GOLD + "Contagion");
		contagionMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Active Ability", ChatColor.AQUA + "Shoots wither skulls !")));
		contagion.setItemMeta(contagionMeta);

		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta chestplateMeta = (LeatherArmorMeta)chestplate.getItemMeta();
		chestplateMeta.setDisplayName(ChatColor.GOLD + "Achlys's Chestplate");
		chestplateMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Achlys's leather chestplate.")));
		chestplateMeta.setColor(Color.fromRGB(0, 33, 9));
		chestplate.setItemMeta(chestplateMeta);
		
		ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
		ItemMeta leggingsMeta = leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Achlys's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Achlys's chainmail leggings.")));
		leggings.setItemMeta(leggingsMeta);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta bootsMeta = (LeatherArmorMeta)boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Achlys's boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Achlys's leather boots.")));
		bootsMeta.setColor(Color.BLACK);
		boots.setItemMeta(bootsMeta);
		
		items.add(plagueBow);
		items.add(arrow);
		items.add(contagion);
		armor.add(chestplate);
		armor.add(leggings);
		armor.add(boots);
		
		return new ControlPointHero(name, lore, items, armor, potions, health, passive, active, quote);
	}
}
