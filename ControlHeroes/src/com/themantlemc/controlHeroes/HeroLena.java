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

public class HeroLena {
	
	String name = "Lena";
	int health = 25;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "An incredibly agile hero who", ChatColor.LIGHT_PURPLE + "uses her speedy attacks to wear", ChatColor.LIGHT_PURPLE + "down the enemy teams."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	String passive = "Killing an enemy gives a chance of taking any killstreak items that they have.";
	String active = "Pulse Bomb: Throw a Pulse Bomb forward, causing a devastating explosion that damages enemies.";
	String quote = "\"Cheers, love! I'll take it from 'ere!\"";
	
	public ControlPointHero returnHero()
	{			
		ItemStack LenaBow = new ItemStack(Material.IRON_HOE);
		ItemMeta tBowMeta = LenaBow.getItemMeta();
		tBowMeta.setDisplayName(ChatColor.GOLD + "Pulse Pistol");
		tBowMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Right Click to shoot arrows!")));
		tBowMeta.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
		tBowMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
		tBowMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		LenaBow.setItemMeta(tBowMeta);
		
		ItemStack blazeOrb = new ItemStack(Material.MAGMA_CREAM);
		ItemMeta blazeOrbMeta = blazeOrb.getItemMeta();
		blazeOrbMeta.setDisplayName(ChatColor.GOLD + "Blaze Orb");
		blazeOrb.setItemMeta(blazeOrbMeta);
		
		ItemStack pulseBomb = new ItemStack(Material.FIREWORK_CHARGE);
		ItemMeta pulseBombMeta = pulseBomb.getItemMeta();
		pulseBombMeta.setDisplayName(ChatColor.GOLD + "Pulse Bomb");
		pulseBombMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Active Ability", ChatColor.LIGHT_PURPLE + "Throw a devastating Pulse Bomb!")));
		pulseBomb.setItemMeta(pulseBombMeta);

		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta chestplateMeta = (LeatherArmorMeta)chestplate.getItemMeta();
		chestplateMeta.setDisplayName(ChatColor.GOLD + "Lena's Chestplate");
		chestplateMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Lena's leather chestplate.")));
		chestplateMeta.setColor(Color.fromRGB(204, 187, 138));
		chestplate.setItemMeta(chestplateMeta);
		
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		LeatherArmorMeta leggingsMeta = (LeatherArmorMeta)leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Lena's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Lena's leather leggings.")));
		leggingsMeta.setColor(Color.fromRGB(252, 228, 111));
		leggings.setItemMeta(leggingsMeta);
		
		ItemStack boots = new ItemStack(Material.IRON_BOOTS);
		ItemMeta bootsMeta = boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Lena's boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Lena's iron boots.")));
		boots.setItemMeta(bootsMeta);
		
		items.add(LenaBow);
		//items.add(bombs);
		items.add(blazeOrb);
		items.add(pulseBomb);
		armor.add(chestplate);
		armor.add(leggings);
		armor.add(boots);
		potions.add(new PotionEffect(PotionEffectType.SPEED, 400000, 0));
		
		return new ControlPointHero(name, lore, items, armor, potions, health, passive, active, quote);
	}
}
