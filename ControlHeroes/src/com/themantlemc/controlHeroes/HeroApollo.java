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

public class HeroApollo {
	String name = "Apollo";
	int health = 30;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "A god of light that brings", ChatColor.LIGHT_PURPLE + "utility to his team while", ChatColor.LIGHT_PURPLE + "keeping the enemy in the dark."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	String passive = "When killing an enemy, your nearest teammate has a chance of receiving a random killstreak item.";
	String active = "Enlightenment: All teammates are given night vision and a small health boost. All teammates have a chance of receiving a Light Orb.";
	String quote = "\"You will meet your demise, not by darkness, but by a flash of light!\"";
	
	public ControlPointHero returnHero()
	{
		ItemStack lBow = new ItemStack(Material.BOW);
		ItemMeta lBowMeta = lBow.getItemMeta();
		lBowMeta.setDisplayName(ChatColor.GOLD + "Bow of Light");
		lBowMeta.setLore(new ArrayList<>(Arrays.asList()));
		lBowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
		lBowMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		lBow.setItemMeta(lBowMeta);
		
		ItemStack arrow = new ItemStack(Material.ARROW);
		
		ItemStack staff = new ItemStack(Material.STICK);
		ItemMeta staffMeta = staff.getItemMeta();
		staffMeta.setDisplayName(ChatColor.GOLD + "Staff of Light");
		staffMeta.addEnchant(Enchantment.DAMAGE_ALL, 6, true);
		staffMeta.addEnchant(Enchantment.KNOCKBACK, 2, true);
		staff.setItemMeta(staffMeta);
		
		ItemStack enlightenment = new ItemStack(Material.GLOWSTONE_DUST);
		ItemMeta enlightenmentMeta = enlightenment.getItemMeta();
		enlightenmentMeta.setDisplayName(ChatColor.GOLD + "Enlightenment");
		enlightenmentMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Active Ability", ChatColor.LIGHT_PURPLE + "Give all allies Night Vision,", ChatColor.LIGHT_PURPLE + "a small health boost, and a", ChatColor.LIGHT_PURPLE + "chance to get a Light Orb!")));
		enlightenment.setItemMeta(enlightenmentMeta);
		
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		LeatherArmorMeta helmetMeta = (LeatherArmorMeta)helmet.getItemMeta();
		helmetMeta.setDisplayName(ChatColor.GOLD + "Apollo's Helmet");
		helmetMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Apollo's leather helmet.")));
		helmetMeta.setColor(Color.WHITE);
		helmet.setItemMeta(helmetMeta);
		
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		LeatherArmorMeta leggingsMeta = (LeatherArmorMeta)leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Apollo's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Apollo's leather leggings.")));
		leggingsMeta.setColor(Color.WHITE);
		leggings.setItemMeta(leggingsMeta);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta bootsMeta = (LeatherArmorMeta)boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Apollo's boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Apollo's leather boots.")));
		bootsMeta.setColor(Color.WHITE);
		boots.setItemMeta(bootsMeta);
		
		items.add(staff);
		items.add(lBow);
		items.add(enlightenment);
		items.add(arrow);
		armor.add(helmet);
		armor.add(leggings);
		armor.add(boots);
		return new ControlPointHero(name, lore, items, armor, potions, health, passive, active, quote);
	}
}
