package us.mcempires.empires;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import net.md_5.bungee.api.ChatColor;

public class EmpiresClass
{
	ItemStack classStack = null;
	ItemMeta classMeta = null;
	List<String> classLore = new ArrayList<String>();
	List<ItemStack> classItems = null;
	List<ItemStack> classArmor = null;
	List<PotionEffect> classPotions = null;
	String className = "";
	int requiredLevel = 0;
	int foodCost = 0;
	
	public void setStack(TeamManager team)
	{
		if(team.level >= requiredLevel)
		{
			classStack = new ItemStack(Material.STAINED_CLAY, requiredLevel, (byte)5);
			classMeta = classStack.getItemMeta();
			classMeta.setDisplayName(ChatColor.GREEN + "Equip " + className + "!");
			classMeta.setLore(classLore);
		}
		else
		{
			classStack = new ItemStack(Material.STAINED_CLAY, requiredLevel, (byte)14);
			classMeta = classStack.getItemMeta();
			classMeta.setDisplayName(ChatColor.RED + "You must be at least level " + requiredLevel + "!");
			classMeta.setLore(classLore);
		}
		classStack.setItemMeta(classMeta);
	}
	
	public ItemStack returnInventoryStack(TeamManager team)
	{
		setStack(team);
		return classStack;
	}
	
	public EmpiresClass(String nm, int lvl, int food, List<String> lr, List<ItemStack> itms, List<ItemStack> armr, List<PotionEffect> ptns)
	{
		className = nm;
		requiredLevel = lvl;
		classLore = lr;
		classItems = itms;
		classArmor = armr;
		classPotions = ptns;
		foodCost = food;
	}
}
