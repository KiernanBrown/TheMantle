package com.themantlemc.controlHeroes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class HeroKhaos {
	String name = "Khaos";
	int health = 30;
	List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "A devious trickster who sends", ChatColor.LIGHT_PURPLE + "the enemy teams into disarray", ChatColor.LIGHT_PURPLE + "at his will."));
	List<ItemStack> items = new ArrayList<>();
	List<ItemStack> armor = new ArrayList<>();
	List<PotionEffect> potions = new ArrayList<>();
	String passive = "Hitting a player has a chance of scrambling the items in their hotbar.";
	String active = "Entropy: Every nearby enemy is randomly swapped with another nearby enemy, and their hotbar is scrambled.";
	String quote = "\"What will you do when I turn your world upside down?\"";
	
	public ControlPointHero returnHero()
	{
		ItemStack weapon = new ItemStack(Material.STONE_SPADE);
		ItemMeta weaponMeta = weapon.getItemMeta();
		weaponMeta.setDisplayName(ChatColor.MAGIC + "??????");
		weaponMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		weaponMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		weapon.setItemMeta(weaponMeta);
		
		ItemStack entropy = new ItemStack(Material.OBSIDIAN);
		ItemMeta entropyMeta = entropy.getItemMeta();
		entropyMeta.setDisplayName(ChatColor.GOLD + "Entropy");
		entropyMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "Active Ability", ChatColor.LIGHT_PURPLE + "Swaps nearby enemies with other", ChatColor.LIGHT_PURPLE + "enemies and scrambles their", ChatColor.LIGHT_PURPLE + "hotbars!")));
		entropy.setItemMeta(entropyMeta);
		
		ItemStack poison = new Potion(PotionType.POISON, 2, true).toItemStack(1);
		ItemMeta poisonMeta = poison.getItemMeta();
		poisonMeta.setDisplayName(ChatColor.GOLD + "Khaotic Poison");
		poison.setItemMeta(poisonMeta);
		
		ItemStack confusion = new Potion(PotionType.SLOWNESS, 2, true).toItemStack(1);
		ItemMeta confusionMeta = confusion.getItemMeta();
		confusionMeta.setDisplayName(ChatColor.GOLD + "Khaotic Confusion");
		confusionMeta.setLore(new ArrayList<>(Arrays.asList("Confusion: 0:05")));
		confusion.setItemMeta(confusionMeta);
		
		ItemStack jump = new Potion(PotionType.JUMP, 2, true).toItemStack(1);
		ItemMeta jumpMeta = jump.getItemMeta();
		jumpMeta.setDisplayName(ChatColor.GOLD + "Khaotic Jump");
		jump.setItemMeta(jumpMeta);
		
		ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
		ItemMeta helmetMeta = helmet.getItemMeta();
		helmetMeta.setDisplayName(ChatColor.GOLD + "Khaos's Helmet");
		helmetMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "A chainmail helmet worn", ChatColor.AQUA + "by Khaos.")));
		helmet.setItemMeta(helmetMeta);
		
		ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
		ItemMeta bootsMeta = boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.GOLD + "Khaos's Boots");
		bootsMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Chainmail boots worn", ChatColor.AQUA + "by Khaos.")));
		boots.setItemMeta(bootsMeta);
		
		items.add(weapon);
		items.add(entropy);
		items.add(poison);
		items.add(confusion);
		items.add(jump);
		armor.add(helmet);
		armor.add(boots);

		return new ControlPointHero(name, lore, items, armor, potions, health, passive, active, quote);
	}
}
