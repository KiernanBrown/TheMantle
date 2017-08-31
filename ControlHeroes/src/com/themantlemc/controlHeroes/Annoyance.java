package com.themantlemc.controlHeroes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Annoyance {
	
	String name = "Annoyance";
	int level = 8;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.GOLD + "Annoyance", ChatColor.AQUA + "An annoying melee class."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	
	public ControlPointClass returnClass()
	{
		ItemStack sword = new ItemStack(Material.STONE_SWORD);
		ItemMeta swordMeta = sword.getItemMeta();
		swordMeta.setDisplayName(ChatColor.GOLD + "Annoying Sword");
		swordMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A stone sword used by", ChatColor.AQUA + "an annoyance.")));
		swordMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		sword.setItemMeta(swordMeta);
		
		int villagerId = EntityType.VILLAGER.ordinal();
		ItemStack villager = new ItemStack(Material.MONSTER_EGG, 1, (short)villagerId);
		ItemMeta villagerMeta = villager.getItemMeta();
		villagerMeta.setDisplayName(ChatColor.GOLD + "Spawn Villager");
		villager.setItemMeta(villagerMeta);
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemMeta chestplateMeta = chestplate.getItemMeta();
		chestplateMeta.setDisplayName(ChatColor.GOLD + "Annoying Chestplate");
		chestplateMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A leather chestplate worn", ChatColor.AQUA + "by an annoyance.")));
		chestplate.setItemMeta(chestplateMeta);
		
		potions.add(new PotionEffect(PotionEffectType.SPEED, 400000, 1));
		
		items.add(sword);
		items.add(villager);
		armor.add(chestplate);
		
		return new ControlPointClass(name, level, lore, items, armor, potions);
	}
}