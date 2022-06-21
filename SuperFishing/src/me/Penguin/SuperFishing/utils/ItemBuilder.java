package me.Penguin.SuperFishing.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
	
	private Material type;
	private String name, locname;
	private List<String> lore;
	private int amount;
	
	public void setDefaults() {
		type = Material.COAL;
		amount = 1;
		name = null;
		locname = null;
		lore = new ArrayList<>();
	}
	
	public ItemBuilder(Material mat, int amount) {
		setDefaults();
		this.type = mat;
		this.amount = amount;
	}
	
	public ItemBuilder(Material mat) {
		setDefaults();
		this.type = mat;
	}
	
	
	public ItemBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	public ItemBuilder addLore(String line) {
		lore.add(u.cc(line));
		return this;
	}
	
	public ItemBuilder setLore(List<String> list) {
		this.lore = list;
		return this;
	}
	
	public ItemBuilder setLocname(String name) {
		this.locname = name;
		return this;
	}
	
	public ItemBuilder colourLore() {
		lore = u.cList(lore);
		return this;
	}
	
	public ItemStack build() {
		ItemStack item = new ItemStack(type, amount);
		ItemMeta meta = item.getItemMeta();
		if (lore.size() >0) meta.setLore(lore);
		if (name != null) meta.setDisplayName(u.cc(name));
		if (locname != null) meta.setLocalizedName(locname);
		item.setItemMeta(meta);
		return item;
	}
	
	
	
	
	

	
	
	
	
}
