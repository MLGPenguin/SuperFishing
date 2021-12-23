package me.Penguin.SuperFishing.GUIs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.Penguin.SuperFishing.objects.Fish;
import me.Penguin.SuperFishing.objects.Fish.FISH;
import me.Penguin.SuperFishing.objects.Settings;
import me.Penguin.SuperFishing.utils.MiniItemBuilder;
import me.Penguin.SuperFishing.utils.u;

public class fishshop {
	
	public static Set<UUID> viewingFishShop = new HashSet<>();
	private static List<Integer> paneSlots = Arrays.asList(4, 5, 6, 7, 8, 17, 36, 37, 38, 39, 40, 41, 42, 43, 44, 46, 47, 48, 50, 51, 52);
	
	public static void open(Player p) {
		Inventory inv = Bukkit.createInventory(null, 54, Settings.FishingShopTitle);
		
		List<Fish> fishes = getFishInInventory(p);
		double totalPrice = getWorth(fishes);
				
		ItemStack sellall = new MiniItemBuilder(Material.GOLD_NUGGET).setName("&a&N&lSELL ALL").addLores("&7Total Price: &6$" + u.dc(totalPrice)).build();
		inv.setItem(45, sellall);
		inv.setItem(53, sellall);
		
		ItemStack pane = new MiniItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName("&0").build();
		for (int i : paneSlots) inv.setItem(i, pane);
		
		p.openInventory(inv);
		viewingFishShop.add(p.getUniqueId());
		
	}
	
	
	private static List<Fish> getFishInInventory(Player p) {
		List<Fish> fishes = new ArrayList<>();
		for (ItemStack x : p.getInventory().getContents()) {
			if (x != null && x.hasItemMeta() && x.getItemMeta().hasLocalizedName() && Fish.fishMaterials.contains(x.getType())) {
				try {
				fishes.add(FISH.valueOf(x.getItemMeta().getLocalizedName()).getFish().setAmount(x.getAmount()));
				} catch (IllegalArgumentException e) {}
			}
		}
		return fishes;
	}
	
	private static double getWorth(List<Fish> fish) {
		double total = 0;
		for (Fish f : fish) total += (f.getPrice() * f.getAmount());
		return total;
	}

	
	
}
