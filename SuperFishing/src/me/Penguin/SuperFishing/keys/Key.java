package me.Penguin.SuperFishing.keys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.Penguin.SuperFishing.crates.Crate;
import me.Penguin.SuperFishing.crates.Crates;
import me.Penguin.SuperFishing.crates.Crates.CrateType;
import me.Penguin.SuperFishing.utils.ItemBuilder;

public class Key {
		
	private int chance;
	private boolean revealed;
	private String col, cratename;
	private CrateType type;
	
	public Key(CrateType type) {
		this.chance = keys.getRandomChance();
		this.revealed = false;
		Crate crate = Crates.getCrate(type);
		this.col = crate.getColourTheme();
		this.cratename = crate.getName();
		this.type = type;
	}
	
	public List<String> getLore(){
		List<String> lores =  new ArrayList<>(Arrays.asList(
				"&7This lockpick has the ability to &nUnlock Treasures",
				"&7fished from the &nMagical Lake!",
				"",
				("&8&l* &7Unlocks: " + cratename),
				("&8&l* &7Success Chance: " + (revealed ? chance : col + "&k99"))
				));
		if (!revealed) {
			lores.addAll(Arrays.asList("", "%c« Right Click to reveal the &nSuccess Chance!%c »".replaceAll("%c", col)));
		}
		return lores;
	}
	
	public ItemStack getItem() {
		return new ItemBuilder(Material.TRIPWIRE_HOOK)
				.setName(col+"&lTREASURE LOCKPICK")
				.setLocname("fishingkey:" + type.toString())
				.setLore(getLore())
				.colourLore()
				.build();			
	}
	
	
	
}
