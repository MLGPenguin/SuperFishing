package me.Penguin.SuperFishing.objects;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.Penguin.MainUtil.u;
import me.Penguin.SuperFishing.objects.Crate.CrateType;

public class Key {
	
	final public static Material material = Material.SHEARS;
	
	private String displayName;
	private CrateType type;
	private List<String> lore;	
	int succesChance;
	
	public static String locname = "IaMaKey";
	
	public Key(CrateType type) {
		this.type = type;
		this.displayName = type.getKeyDisplayName();
		this.succesChance = (int) (Math.round(Math.random() * 100));
				
	}
	
	
	
	public static boolean isKey(ItemStack item) {
		if (u.isItem(item)) {
			return item.getItemMeta().getLocalizedName().equals(locname);
		}
		return false;
	}
	
	
	
}
