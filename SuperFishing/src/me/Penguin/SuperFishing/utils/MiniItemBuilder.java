package me.Penguin.SuperFishing.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class MiniItemBuilder {
	
	private Material mat;
	private int amount;
	private List<String> lore;
	private ItemMeta meta;
	private Map<Enchantment, Integer> enchants;	
	private ItemStack stack;
	private String name = null;
	private boolean hideEnchants, clearEnchants = false;
	private String locname = null;
	private HashMap<NamespacedKey, String> persistentStringData = new HashMap<>();
	
	
	// CONSTRUCTORS
	public MiniItemBuilder(Material type) {		
		mat = type; 
		amount = 1; 
		stack = new ItemStack(type, amount); 
		meta = stack.getItemMeta(); 
		enchants = new HashMap<>(); 
		lore = new ArrayList<>();
	}
	
	public MiniItemBuilder(Material type, int amount) {
		mat = type; 
		this.amount = amount; 
		stack = new ItemStack(type, amount); 
		meta = stack.getItemMeta(); 
		enchants = new HashMap<>();
		lore = new ArrayList<>();
	}
	
	public MiniItemBuilder(ItemStack is) {
		mat = is.getType(); 
		amount = is.getAmount(); 
		stack = is; meta = is.getItemMeta(); 
		enchants = is.getItemMeta().getEnchants().size() == 0 ? new HashMap<>() : is.getItemMeta().getEnchants();
		lore = meta.getLore();
	}

	
	// SETTERS
	public MiniItemBuilder setType(Material type) {
		this.mat = type; return this;
	}
	
	public MiniItemBuilder setName(String name) {
		this.name = name; 
		return this;
	}
	
	public MiniItemBuilder setAmount(int amount) {
		this.amount = amount; return this;
	}

	public MiniItemBuilder hideEnchants(boolean hide) {
		this.hideEnchants = hide; 
		return this;
	}
	
	public MiniItemBuilder setEnchants(Map<Enchantment, Integer> enchants) {
		this.enchants = enchants; return this;
	}
	
	public MiniItemBuilder clearEnchants(boolean clearEnchants) {
		this.clearEnchants = clearEnchants;
		return this;
	}
	
	public MiniItemBuilder addEnchant(Enchantment enchant, int level) {
		this.enchants.put(enchant, level);
		return this;
	}
	
	public MiniItemBuilder addPersistentStringData(NamespacedKey key, String data) {
		persistentStringData.put(key, data);
		return this;
	}
	
	public MiniItemBuilder addLores(String... lores) {
		if (this.lore == null) lore = new ArrayList<>();
		for (String x : lores) lore.add(u.hc(x));
		return this;
	}
	
	public MiniItemBuilder setLocname(String loc) {
		this.locname = loc;
		return this;
	}
	
	// BUILDER
	public ItemStack build() {
		stack.setAmount(amount);
		stack.setType(mat);
		if (enchants.size() > 0) {
			for (Enchantment e : enchants.keySet()) {
				meta.addEnchant(e, enchants.get(e), true);
			}
		}
		if (lore != null && lore.size() > 0) meta.setLore(lore);
		if (clearEnchants) {
			for (Enchantment e : meta.getEnchants().keySet()) meta.removeEnchant(e);			
		}
		if (persistentStringData.size() > 0) {
			for (NamespacedKey key : persistentStringData.keySet()) {
				PersistentDataContainer c = meta.getPersistentDataContainer();
				c.set(key, PersistentDataType.STRING, persistentStringData.get(key));
			}
		}
		if (name != null) meta.setDisplayName(u.hc(name));
		if (hideEnchants) meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		if (locname != null) meta.setLocalizedName(locname);
		stack.setItemMeta(meta);
		return stack;
	}

}
