package me.Penguin.SuperFishing.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Penguin.MainUtil.u;


public class ItemBuilder {
	
	private Material material;
	private int amount;
	
	private String displayName;
	private String locname;
	private List<String> lore;
	private HashMap<Enchantment, Integer> enchants;
	
	public ItemBuilder(Material type, int amount) {
		material = type;
		this.amount = amount;
		setDefaults();
	}
	
	public ItemBuilder(Material type) {
		material = type;
		amount = 1;
		setDefaults();
	}
	
	public void setDefaults() {
		this.lore = new ArrayList<>();
		this.enchants = new HashMap<>();
	}
	
	public ItemBuilder setDisplayName(String name) { displayName = u.hc(name); return this; }
	public ItemBuilder setMaterial(Material type) { material = type; return this; }
	public ItemBuilder addLore(String line) { lore.add(u.hc(line)); return this; }
	public ItemBuilder setLore(List<String> newlore) { lore = newlore; return this; }
	public ItemBuilder setAmount(int amount) { this.amount = amount; return this; }
	public ItemBuilder addEnchant(Enchantment enchant, int level) { enchants.put(enchant, level); return this; }
	public ItemBuilder setLocname(String name) { locname = name; return this; }	
	
	
	public ItemStack build() {
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();
		
		if (displayName != null) {
			meta.setDisplayName(u.hc(displayName));
		}
		if (locname != null) {
			meta.setLocalizedName(locname);
		}
		if (lore != null) {
			meta.setLore(lore);
		}
		if (enchants.size() > 0) {
			for (Enchantment enchant : enchants.keySet()) meta.addEnchant(enchant, enchants.get(enchant), true);
		}
		item.setItemMeta(meta);
		return item;
	}
	
}
