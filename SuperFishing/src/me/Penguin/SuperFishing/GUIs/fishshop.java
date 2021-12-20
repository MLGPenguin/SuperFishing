package me.Penguin.SuperFishing.GUIs;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.Penguin.SuperFishing.objects.Settings;
import me.Penguin.SuperFishing.utils.MiniItemBuilder;

public class fishshop {
	
	public static Set<UUID> viewingFishShop = new HashSet<>();
	
	public static void open(Player p) {
		Inventory inv = Bukkit.createInventory(null, 54, Settings.FishingShopTitle);
		
		inv.setItem(0, new MiniItemBuilder(Material.FISHING_ROD).build());
		
	}

}
