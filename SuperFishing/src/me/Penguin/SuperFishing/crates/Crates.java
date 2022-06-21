package me.Penguin.SuperFishing.crates;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import me.Penguin.MainUtil.u;

public class Crates {
	
		
	
	/**
	 * checks if ItemStack is a crate
	 * @param item potential crate
	 * @return whether item is a crate or not
	 */
	public static boolean isCrate(ItemStack item) {
		if (u.isItem(item)) {
			return (item.getItemMeta().getLocalizedName().contains("fishingcrate:"));				
		}
		return false;
	}
	
	public static CrateType getType(ItemStack item) {
		if (isCrate(item)) {
			return CrateType.valueOf(item.getItemMeta().getLocalizedName().split(":")[1]);
		}
		return null;
	}
	
	public enum CrateType {
		FISHERMANS, DRAGONS;
	}
	
	public static Crate getCrate(CrateType type) {
		switch (type) {
		case FISHERMANS: return new CrateFisherman();
		case DRAGONS: return new CrateDragon();
		}
		return null;
	}
	
	public static List<String> getCrateNames(){
		List<String> names = new ArrayList<>();
		for (CrateType t : CrateType.values()) names.add(t.toString());
		return names;
	}
	
}
