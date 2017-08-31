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

public class HeroAzazel 
{
	String name = "Azazel";
	int health = 20;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "A sneaky demon that creeps up", ChatColor.LIGHT_PURPLE + "behind the enemy in order to", ChatColor.LIGHT_PURPLE + "wreak havoc."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	String passive = "Backstabbing an enemy does an additional 15% damage, with a chance to set the enemy on fire.";
	String active = "Demonic Slash: All nearby enemies are hit with a blinding strike, dealing damage and giving them Blindness 2 for 6 seconds, and having a chance to ignite the enemy.";
	String quote = "\"I have returned from the depths of hell to do battle with you!\"";
	
	public ControlPointHero returnHero()
	{					
		ItemStack dagger = new ItemStack(Material.IRON_SWORD);
		ItemMeta daggerMeta = dagger.getItemMeta();
		daggerMeta.setDisplayName(ChatColor.GOLD + "Demonic Dagger");
		daggerMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		daggerMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		dagger.setItemMeta(daggerMeta);
		
		ItemStack demonicslash = new ItemStack(Material.FLINT);
		ItemMeta demonicslashMeta = demonicslash.getItemMeta();
		demonicslashMeta.setDisplayName(ChatColor.GOLD + "Demonic Slash");
		demonicslashMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Active Ability", ChatColor.LIGHT_PURPLE + "Damage all enemies in an 8 block", ChatColor.LIGHT_PURPLE + "radius with a powerful, blinding", ChatColor.LIGHT_PURPLE + "slash!")));
		demonicslash.setItemMeta(demonicslashMeta);
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta chestplateMeta = (LeatherArmorMeta)chestplate.getItemMeta();
		chestplateMeta.setDisplayName(ChatColor.GOLD + "Azazel's Chestplate");
		chestplateMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Azazel's leather chestplate.")));
		chestplateMeta.setColor(Color.BLACK);
		chestplate.setItemMeta(chestplateMeta);
		
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		LeatherArmorMeta leggingsMeta = (LeatherArmorMeta)leggings.getItemMeta();
		leggingsMeta.setDisplayName(ChatColor.GOLD + "Azazel's Leggings");
		leggingsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Azazel's leather leggings.")));
		leggingsMeta.setColor(Color.BLACK);
		leggings.setItemMeta(leggingsMeta);
		
		items.add(dagger);
		items.add(demonicslash);
		armor.add(chestplate);
		armor.add(leggings);
		
		return new ControlPointHero(name, lore, items, armor, potions, health, passive, active, quote);
	}
}
