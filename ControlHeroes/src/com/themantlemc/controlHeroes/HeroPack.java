package com.themantlemc.controlHeroes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class HeroPack 
{
	String packName = "";
	ControlPointHero hero1;
	ControlPointHero hero2;
	ControlPointHero hero3;
	
	public void addStacks(ItemStack item, Inventory inv, int slot, PlayerInfo pInfo)
	{
		ItemStack pack = item;
		inv.setItem(slot, pack);
		ItemStack heroesStack = null;
		if(pInfo.currentPack.equals(packName))
		{
			heroesStack = new ItemStack(Material.STAINED_CLAY, 1, (byte)5);
		}
		else if(pInfo.unlockedPacks.contains(packName))
		{
			heroesStack = new ItemStack(Material.STAINED_CLAY, 1, (byte)3);
		}
		else
		{
			heroesStack = new ItemStack(Material.STAINED_CLAY, 1, (byte)14);
		}
		
		ItemStack hero1Stack = hero1.returnInventoryStack(heroesStack);
		inv.setItem(slot + 1, hero1Stack);
		ItemStack hero2Stack = hero2.returnInventoryStack(heroesStack);
		inv.setItem(slot + 2, hero2Stack);
		ItemStack hero3Stack = hero3.returnInventoryStack(heroesStack);
		inv.setItem(slot + 3, hero3Stack);
	}
	
	public HeroPack(String nm, ControlPointHero h1, ControlPointHero h2, ControlPointHero h3)
	{
		packName = nm;
		hero1 = h1;
		hero2 = h2;
		hero3 = h3;
	}
}
