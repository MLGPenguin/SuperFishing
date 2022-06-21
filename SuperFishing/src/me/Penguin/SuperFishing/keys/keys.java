package me.Penguin.SuperFishing.keys;

import java.util.Random;

import org.bukkit.inventory.ItemStack;

import me.Penguin.MainUtil.u;

public class keys {

	public static boolean isKey(ItemStack item) {
		if (u.isItem(item)) {
			return item.getItemMeta().getLocalizedName().contains("fishingkey:");
		}
		return false;
	}
	
	public static int getRandomChance() {
		return (new Random().nextInt(100)+1);
	}
	
}
