package us.mcempires.lobby;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class LobbyListener implements Listener 
{
	
	Lobby lobby;
	
	// Disable Join Message and give player Compass
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		event.setJoinMessage(null);
		player.sendMessage(ChatColor.GOLD + "Welcome to The Mantle!");
		ItemStack compass = new ItemStack(Material.COMPASS);
		ItemMeta compassMeta = compass.getItemMeta();
		compassMeta.setDisplayName(ChatColor.GOLD + "Server Selector");
		compassMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Right click this to select", ChatColor.AQUA + "a server")));
		compass.setItemMeta(compassMeta);
		ItemStack noteblock = new ItemStack(Material.NOTE_BLOCK);
		ItemMeta noteblockMeta = noteblock.getItemMeta();
		noteblockMeta.setDisplayName(ChatColor.GOLD + "Music");
		noteblock.setItemMeta(noteblockMeta);
		player.getInventory().clear();
		player.getInventory().addItem(compass);
		player.getInventory().setItem(4, noteblock);
		player.setFoodLevel(20);
		player.setLevel(0);
		player.setGameMode(GameMode.ADVENTURE);
		player.teleport(new Location(player.getWorld(), 0, 70, 0));
		
		// Give the player a how to play book
		/*ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bookMeta = (BookMeta)book.getItemMeta();
		bookMeta.setDisplayName(ChatColor.GOLD + "How to Play");
		bookMeta.setAuthor(ChatColor.LIGHT_PURPLE + "Empires");
		bookMeta.addPage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "  Welcome to Empires!" + ChatColor.RESET + "\nEmpires is a team based strategy game which focuses on resource collection, with a bit of PVP thrown in. This book is designed to give you the basics of how to play the game.");
		bookMeta.addPage("        Objective \nThe goal of Empires is to be the team with the most Empire points at the end of the game. Empire points can be gained in four different ways. \n \n1. Levelling up your Empire. You gain 2 Empire Points every time your Empire levels up.", "2. Killing other players. You will either gain 3 or 5 Empire Points per kill. \n \n 3. Collectors. Every 8 seconds that your Empire has an active collector, you will gain 1 Empire Point.", "4. Gold. Any remaining gold is converted to Empire Points at the end of the game.");
		bookMeta.addPage("     Getting Started", "At the start of the game, the first thing that you are going to want to do is get tools from your gathering tools sign.");
		bookMeta.addPage("And the last page!");
		book.setItemMeta(bookMeta);
		event.getPlayer().getInventory().addItem(book);*/
	}
	
	// Disable Leave Message
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event)
	{
		event.setQuitMessage(null);
	}
	
	// Stop Player from dropping items
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event)
	{
		event.setCancelled(true);
	}
	
	// No Hunger
	@EventHandler
	public void stopHunger(FoodLevelChangeEvent event)
	{
		event.setCancelled(true);
	}
	
	// No Damage
	@EventHandler
	public void stopDamage(EntityDamageEvent event)
	{
		event.setCancelled(true);
	}
	
	// Server Menu
	public void openServerMenu(Player p)
	{
		Inventory serverMenu = Bukkit.createInventory(null, 9, ChatColor.AQUA + "Select a Server!");
		ItemStack empires1 = new ItemStack(Material.STAINED_CLAY, 1, (byte)5);
		ItemMeta empires1Meta = empires1.getItemMeta();
		empires1Meta.setDisplayName(ChatColor.GOLD + "Empires 1");
		empires1Meta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Players: " + lobby.empires1Online + "/24")));
		empires1.setItemMeta(empires1Meta);
		serverMenu.setItem(0, empires1);
		
		ItemStack cp1 = new ItemStack(Material.STAINED_CLAY, 1, (byte)5);
		ItemMeta cp1Meta = cp1.getItemMeta();
		cp1Meta.setDisplayName(ChatColor.GOLD + "Control 1");
		cp1Meta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Players: " + lobby.cp1Online + "/24")));
		cp1.setItemMeta(cp1Meta);
		serverMenu.setItem(1, cp1);
		
		ItemStack cph1 = new ItemStack(Material.STAINED_CLAY, 1, (byte)5);
		ItemMeta cph1Meta = cph1.getItemMeta();
		cph1Meta.setDisplayName(ChatColor.GOLD + "Control Heroes 1");
		cph1Meta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Players: " + lobby.cph1Online + "/24")));
		cph1.setItemMeta(cph1Meta);
		serverMenu.setItem(2, cph1);
		p.openInventory(serverMenu);
		
		ItemStack cm1 = new ItemStack(Material.STAINED_CLAY, 1, (byte)5);
		ItemMeta cm1Meta = cm1.getItemMeta();
		cm1Meta.setDisplayName(ChatColor.GOLD + "ClayMix 1");
		cm1Meta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "Players: " + lobby.cph1Online + "/24")));
		cm1.setItemMeta(cm1Meta);
		serverMenu.setItem(3, cm1);
		p.openInventory(serverMenu);
		
		ItemStack parkour = new ItemStack(Material.STAINED_CLAY, 1, (byte)5);
		ItemMeta parkourMeta = parkour.getItemMeta();
		parkourMeta.setDisplayName(ChatColor.GOLD + "The Parkour Project");
		parkour.setItemMeta(parkourMeta);
		serverMenu.setItem(8, parkour);
		p.openInventory(serverMenu);
	}
	
	// Open Server Menu when player right clicks compass
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		// Player isn't right clicking with an item
		if(event.getAction() == Action.PHYSICAL || event.getItem() == null || event.getItem().getType() == Material.AIR)
		{
			return;
		}
		
		if(event.getItem().getType() == Material.COMPASS)
		{
			openServerMenu(event.getPlayer());
		}
	}
	
	// Change server through inventory
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		if(!ChatColor.stripColor(event.getInventory().getName()).equals("Select a Server!"))
		{
			return;
		}
		
		// Player is selecting a server
		Player p = (Player) event.getWhoClicked();
		event.setCancelled(true);
		
		// Close inventory if player clicks on something other than a server item
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta())
		{
			p.closeInventory();
			return;
		}
		
		switch(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()))
		{
			case "Empires 1": lobby.sendToServer(p, "empires1");
				break;
			case "Control 1": lobby.sendToServer(p, "cp1");
				break;
			case "Control Heroes 1": lobby.sendToServer(p, "cph1");
				break;
			case "ClayMix 1": lobby.sendToServer(p, "cm1");
				break;
			case "The Parkour Project": lobby.sendToServer(p, "tpp");
				break;
		}
	}
	
	// Stop player from breaking blocks
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		event.setCancelled(true);
	}
	
	public LobbyListener(Lobby l)
	{
		lobby = l;
	}
}
