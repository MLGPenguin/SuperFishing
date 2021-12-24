package me.Penguin.SuperFishing.GUIs;

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
import me.Penguin.SuperFishing.objects.valueList;
import me.Penguin.SuperFishing.utils.MiniItemBuilder;
import me.Penguin.SuperFishing.utils.u;

public class fishshop {
	
	public static Set<UUID> viewingFishShop = new HashSet<>();
	private static List<Integer> paneSlots = Arrays.asList(4, 5, 6, 7, 8, 17, 36, 37, 38, 39, 40, 41, 42, 43, 44, 46, 47, 48, 50, 51, 52);
	
	public static void open(Player p) {
		Inventory inv = Bukkit.createInventory(null, 54, Settings.FishingShopTitle);
		
		valueList<FISH, Integer> fishes = getFishInInventory(p);
		double totalPrice = getWorth(fishes);
				
		ItemStack sellall = new MiniItemBuilder(Material.GOLD_NUGGET).setName("&a&N&lSELL ALL").addLores("&7Total Price: &6$" + u.dc(totalPrice)).build();
		inv.setItem(45, sellall);
		inv.setItem(53, sellall);
		
		ItemStack pane = new MiniItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName("&0").build();
		for (int i : paneSlots) inv.setItem(i, pane);
		
		inv.setItem(0, FISH.cod0.getFish().getItem(true, p, fishes.get(FISH.cod0)));
		inv.setItem(9, FISH.cod1.getFish().getItem(true, p, fishes.get(FISH.cod1)));
		inv.setItem(18, FISH.cod2.getFish().getItem(true, p, fishes.get(FISH.cod2)));
		inv.setItem(27, FISH.cod3.getFish().getItem(true, p, fishes.get(FISH.cod3)));
		
		inv.setItem(1, FISH.salmon0.getFish().getItem(true, p, fishes.get(FISH.salmon0)));
		inv.setItem(10, FISH.salmon1.getFish().getItem(true, p, fishes.get(FISH.salmon1)));
		inv.setItem(19, FISH.salmon2.getFish().getItem(true, p, fishes.get(FISH.salmon2)));
		inv.setItem(28, FISH.salmon3.getFish().getItem(true, p, fishes.get(FISH.salmon3)));
		
		inv.setItem(2, FISH.pufferfish0.getFish().getItem(true, p, fishes.get(FISH.pufferfish0)));
		inv.setItem(11, FISH.pufferfish1.getFish().getItem(true, p, fishes.get(FISH.pufferfish1)));
		inv.setItem(20, FISH.pufferfish2.getFish().getItem(true, p, fishes.get(FISH.pufferfish2)));
		inv.setItem(29, FISH.pufferfish3.getFish().getItem(true, p, fishes.get(FISH.pufferfish3)));
		
		inv.setItem(3, FISH.tropicalfish0.getFish().getItem(true, p, fishes.get(FISH.tropicalfish0)));
		inv.setItem(12, FISH.tropicalfish1.getFish().getItem(true, p, fishes.get(FISH.tropicalfish1)));
		inv.setItem(21, FISH.tropicalfish2.getFish().getItem(true, p, fishes.get(FISH.tropicalfish2)));
		inv.setItem(30, FISH.tropicalfish3.getFish().getItem(true, p, fishes.get(FISH.tropicalfish3)));
		
		inv.setItem(13, FISH.prismarineshard1.getFish().getItem(true, p, fishes.get(FISH.prismarineshard1)));
		inv.setItem(22, FISH.prismarineshard2.getFish().getItem(true, p, fishes.get(FISH.prismarineshard2)));
		inv.setItem(31, FISH.prismarineshard3.getFish().getItem(true, p, fishes.get(FISH.prismarineshard3)));
		
		inv.setItem(14, FISH.prismarinecrystals1.getFish().getItem(true, p, fishes.get(FISH.prismarinecrystals1)));
		inv.setItem(23, FISH.prismarinecrystals2.getFish().getItem(true, p, fishes.get(FISH.prismarinecrystals2)));
		inv.setItem(32, FISH.prismarinecrystals3.getFish().getItem(true, p, fishes.get(FISH.prismarinecrystals3)));
		
		inv.setItem(15, FISH.inksac1.getFish().getItem(true, p, fishes.get(FISH.inksac1)));
		inv.setItem(24, FISH.inksac2.getFish().getItem(true, p, fishes.get(FISH.inksac2)));
		inv.setItem(33, FISH.inksac3.getFish().getItem(true, p, fishes.get(FISH.inksac3)));
		
		inv.setItem(16, FISH.scute1.getFish().getItem(true, p, fishes.get(FISH.scute1)));
		inv.setItem(25, FISH.scute2.getFish().getItem(true, p, fishes.get(FISH.scute2)));
		inv.setItem(34, FISH.scute3.getFish().getItem(true, p, fishes.get(FISH.scute3)));
		
		inv.setItem(26, FISH.nautilusshell2.getFish().getItem(true, p, fishes.get(FISH.nautilusshell2)));
		inv.setItem(35, FISH.nautilusshell3.getFish().getItem(true, p, fishes.get(FISH.nautilusshell3)));
		
		inv.setItem(49, FISH.turtleegg3.getFish().getItem(true, p, fishes.get(FISH.turtleegg3)));
		
		p.openInventory(inv);
		viewingFishShop.add(p.getUniqueId());
		
	}
	
	
	public static valueList<FISH, Integer> getFishInInventory(Player p) {
		valueList<FISH, Integer> fishes = new valueList<>();
		for (ItemStack x : p.getInventory().getContents()) {
			if (x != null && x.hasItemMeta() && x.getItemMeta().hasLocalizedName() && Fish.fishMaterials.contains(x.getType())) {
				try {
					FISH f = FISH.valueOf(x.getItemMeta().getLocalizedName());
					fishes.addValue(f, x.getAmount());
				} catch (IllegalArgumentException e) {}
			}
		}
		return fishes;
	}
	
	private static double getWorth(valueList<FISH, Integer> fish) {
		double total = 0;
		for (FISH f : fish.keySet()) total += (f.getFish().getPrice() * fish.get(f));
		return total;
	}

	
	
}
