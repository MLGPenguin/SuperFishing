package me.Penguin.SuperFishing.crates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.Penguin.SuperFishing.crates.Crates.CrateType;
import me.Penguin.SuperFishing.utils.ItemBuilder;
import me.Penguin.SuperFishing.utils.u;


public abstract class Crate {

	protected boolean locked = true;	
	
	public abstract String getName();
	public abstract List<String> getDescription();
	public abstract CrateType getType();
	public abstract String getColourTheme();
	 
	public boolean getLocked() {
		return locked;
	}
	
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	
	public List<String> generateLore() {
		List<String> lore = new ArrayList<>();
		String col = getColourTheme();
		if (locked) lore.addAll(Arrays.asList("&c&LLocked", ""));
		lore.addAll(getDescription());
		lore.addAll(Arrays.asList("", col+"Possible Rewards:"));
		if (locked) lore.addAll(Arrays.asList("", "%c« Unlock this &ntreasure%c with a &nlockpick%c! »".replaceAll("%c", col)));
		lore = u.cList(lore);
		return lore;
	}
	
	public ItemStack getItem() {
		ItemStack item = new ItemBuilder(Material.CHEST)
				.setLore(generateLore())
				.setName(getName() + (locked ? " &7(Right-Click)":""))
				.setLocname("fishingcrate:" + getType().toString())
				.build();
		return item;
	}
	

	




}
