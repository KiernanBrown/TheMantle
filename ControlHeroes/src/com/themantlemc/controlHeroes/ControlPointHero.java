package com.themantlemc.controlHeroes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public class ControlPointHero {
	ItemStack heroStack = null;
	ItemMeta heroMeta = null;
	List<String> heroLore = new ArrayList<String>();
	List<ItemStack> heroItems = null;
	List<ItemStack> heroArmor = null;
	List<PotionEffect> heroPotions = null;
	String heroName = "";
	String activeName;
	String passiveName;
	String passiveAbility;
	String activeAbility;
	String quote;
	int heroHealth;
	
	public void setStack(ItemStack stack)
	{
		ItemMeta stackMeta = stack.getItemMeta();
		stackMeta.setDisplayName(ChatColor.GOLD + heroName);
		stackMeta.setLore(heroLore);
		stack.setItemMeta(stackMeta);
		heroStack = stack;
	}
	
	public ItemStack returnInventoryStack(ItemStack item)
	{
		setStack(item);
		return heroStack;
	}
	
	public ControlPointHero(String nm, List<String> lr, List<ItemStack> itms, List<ItemStack> armr, List<PotionEffect> ptns, int health, String pas, String act, String qt)
	{
		heroName = nm;
		heroLore = lr;
		heroItems = itms;
		heroArmor = armr;
		heroPotions = ptns;
		heroHealth = health;
		passiveAbility = pas;
		activeAbility = act;
		quote = qt;
	}
}
