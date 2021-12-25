package me.Penguin.SuperFishing.GUIs;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.Penguin.SuperFishing.MainListener;
import me.Penguin.SuperFishing.objects.Fish.FISH;
import me.Penguin.SuperFishing.utils.MiniItemBuilder;
import me.Penguin.SuperFishing.utils.u;
import penguin.supertags.Main;

public class confirmSell {
	

	public static List<Integer> confirms = Arrays.asList(0, 1, 2, 9, 10, 11, 18, 19, 20);
	public static List<Integer> cancels = Arrays.asList(6, 7, 8, 15, 16, 17, 24, 25, 26);
	
	
	private Player p;
	private int amountOfFish;
	private double price;
	private fishshop f;
	
	public confirmSell(fishshop f) {	
		this.p = f.getPlayer();
		this.f = f;
	}
	
	public void open(boolean all, FISH notAll) {
		amountOfFish = all?f.getTotalFishCount():f.getAmount(notAll);
		price = all ? f.getWorth() : notAll.getFish().getPrice() * amountOfFish;
		Inventory inv = Bukkit.createInventory(null, 27, u.cc("&aConfirmation Required"));
		ItemStack confirm = new MiniItemBuilder(Material.LIME_STAINED_GLASS_PANE).setName("&a&LCONFIRM").build();
		ItemStack cancel = new MiniItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("&c&LCANCEL").build();
		ItemStack message = new MiniItemBuilder(all?Material.BLACK_STAINED_GLASS_PANE:notAll.getFish().getItem(false, null, 0).getType()).setName("&aConfirmation Required").addLores("&7Are you sure you want to sell",
				"&6" + amountOfFish + "&7 " + (all? "fish" : notAll.getFish().getName()) + "&7 for &a$" + u.dc(price)).build();
		
		for (int i : confirms) inv.setItem(i, confirm);
		for (int i : cancels) inv.setItem(i, cancel);
		inv.setItem(13, message);	
		confirmSell c = this;
		new BukkitRunnable() {			
			@Override
			public void run() {
				p.openInventory(inv);
				MainListener.confirming.put(p.getUniqueId(), c);
			}
		}.runTaskLater(Main.getPlugin(Main.class), 1);
				
	}
	

}

