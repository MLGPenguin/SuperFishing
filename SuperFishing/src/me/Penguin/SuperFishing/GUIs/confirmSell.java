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

import me.Penguin.SuperFishing.objects.Fish.FISH;
import me.Penguin.SuperFishing.utils.MiniItemBuilder;
import me.Penguin.SuperFishing.utils.u;

public class confirmSell {
	
	public static Set<UUID> confirming = new HashSet<>();
	public static List<Integer> confirms = Arrays.asList(0, 1, 2, 9, 10, 11, 18, 19, 20);
	public static List<Integer> cancels = Arrays.asList(6, 7, 8, 15, 16, 17, 24, 25, 26);
	
	
	public static void open(Player p, boolean all, FISH notAll, int amountOfFish, double price) {
		Inventory inv = Bukkit.createInventory(null, 27, u.cc("&aConfirmation Required"));
		ItemStack confirm = new MiniItemBuilder(Material.LIME_STAINED_GLASS_PANE).setLocname("" + price).setName("&a&LCONFIRM").build();
		ItemStack cancel = new MiniItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("&c&LCANCEL").build();
		ItemStack message = new MiniItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName("&aConfirmation Required").addLores("&7Are you sure you want to sell",
				"&6" + amountOfFish + "&7 " + (all? "fish" : notAll.getFish().getName()) + "&7 for &a$" + u.dc(price)).build();
		
		for (int i : confirms) inv.setItem(i, confirm);
		for (int i : cancels) inv.setItem(i, cancel);
		inv.setItem(13, message);		
		
		p.openInventory(inv);
		confirming.add(p.getUniqueId());
		
	}
	

}
