// This class is used as the base class for all other classes

package com.themantlemc.controlHeroes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public class ControlPointClass {
	
	// All of this information is set inside the actual class
	ItemStack classStack = null;
	ItemMeta classMeta = null;
	List<String> classLore = new ArrayList<String>();
	List<ItemStack> classItems = null;
	List<ItemStack> classArmor = null;
	List<PotionEffect> classPotions = null;
	String className = "";
	int requiredLevel = 0;
	int foodCost = 0;
	
	// Return the item stack for the class, which will appear in the class menu
	public void setStack(int level, String current)
	{
		// Yellow clay if player has class equipped
		if(current.equals(className))
		{
			classStack = new ItemStack(Material.STAINED_CLAY, requiredLevel, (byte)4);
			classMeta = classStack.getItemMeta();
			classMeta.setDisplayName(ChatColor.GREEN + className + "!");
			classMeta.setLore(classLore);
		}
		
		// Green clay if player can equip class
		else if(level >= requiredLevel)
		{
			classStack = new ItemStack(Material.STAINED_CLAY, requiredLevel, (byte)5);
			classMeta = classStack.getItemMeta();
			classMeta.setDisplayName(ChatColor.GREEN + "Equip " + className + "!");
			classMeta.setLore(classLore);
		}
		
		// Red clay if player is too low level
		else
		{
			classStack = new ItemStack(Material.STAINED_CLAY, requiredLevel, (byte)14);
			classMeta = classStack.getItemMeta();
			classMeta.setDisplayName(ChatColor.RED + "You must be at least level " + requiredLevel + "!");
			classMeta.setLore(classLore);
		}
		classStack.setItemMeta(classMeta);
	}
	
	// Returns the ItemStack that is made by setStack
	public ItemStack returnInventoryStack(PlayerInfo info)
	{
		if(info.changeClass == "")
		{
			setStack(info.level, info.currentClass);
		}
		else
		{
			setStack(info.level, info.changeClass);
		}
		return classStack;
	}
	
	// Constructor
	public ControlPointClass(String nm, int lvl, List<String> lr, List<ItemStack> itms, List<ItemStack> armr, List<PotionEffect> ptns)
	{
		className = nm;
		requiredLevel = lvl;
		classLore = lr;
		classItems = itms;
		classArmor = armr;
		classPotions = ptns;;
	}
}
