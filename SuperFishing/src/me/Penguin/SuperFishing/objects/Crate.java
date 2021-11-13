package me.Penguin.SuperFishing.objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.Penguin.MainUtil.u;
import me.Penguin.SuperFishing.objects.Crate.CrateType;
import penguin.supertags.Main;
import penguin.supertags.objects.tag;

public class Crate {

	final public static Material material = Material.CHEST;

	private String displayName;
	private List<String> lore;
	private CrateType type;
	private static NamespacedKey typekey = new NamespacedKey(Main.getPlugin(Main.class), "cratetype");; 


	public static String locname = "IaMacRAte";


	public Crate(CrateType type) {
		this.type = type;
		this.displayName = type.getCrateDisplayName();
	}

	/**
	 * checks if ItemStack is a crate
	 * @param item potential crate
	 * @return whether item is a crate or not
	 */
	public static boolean isCrate(ItemStack item) {
		if (u.isItem(item)) {
			return (item.getItemMeta().getLocalizedName().equals(locname));				
		}
		return false;
	}


	public static List<Reward> getAllRewards(CrateType crate){
		List<Reward> rewards = new ArrayList<>();
		switch (crate) {
		case fishermans:
			rewards.add(new Reward(new tag("tsunami"), 5));
			rewards.add(new Reward(new tag("nemo"), 1));
		}	
		return rewards;	
	}

	/**
	 * Check if is a crate first using {@link Crate.isCrate}
	 * @param crate Crate Item 
	 * @return the type of crate
	 */
	public static CrateType getCrateType(ItemStack crate) {
		if (isCrate(crate)) {
			PersistentDataContainer c = crate.getItemMeta().getPersistentDataContainer();
			return CrateType.valueOf(c.get(typekey, PersistentDataType.STRING));
		}
		return null;
	}

	public ItemStack getItem() {
		ItemStack crate = new ItemStack(material);
		ItemMeta meta = crate.getItemMeta();
		//TODO make lore
		meta.setDisplayName(displayName);
		meta.getPersistentDataContainer().set(typekey, PersistentDataType.STRING, type.toString());
		meta.setLocalizedName(locname);

		crate.setItemMeta(meta);
		return crate;
	}
	
	/**
	 * Opens a Crate for a player and gives them rewards, however, does not consume the crate.
	 * @param p player receiving the rewards
	 * @param type type of crate to open
	 * @param amountOfRewardsPerCrate amount of rewards from the crate
	 */
	public static void openCrate(Player p, CrateType type, int amountOfRewardsPerCrate) {
		// for every reward from that crate (chance generated and included already)
		for (Reward reward : Reward.getRewards(Crate.getAllRewards(type), amountOfRewardsPerCrate)) {					
			if (p.getInventory().firstEmpty() > -1) {
				reward.giveReward(p);		
			} else { 
				p.sendMessage("inventory is full"); 
				break; 
			}
		}
	}



	public enum CrateType {

		fishermans(1, u.hc("&a&lFisherman's Treasure"), u.hc("&d&nFisherman's Lockpick&r &8[&a&LFisherman's&8]"));


		private int level;
		private String crateDisplayName;
		private String keyDisplayName;

		CrateType(int level,  String crateDisplayName, String keyDisplayName) {
			this.level = level;
			this.crateDisplayName = crateDisplayName;
			this.keyDisplayName = keyDisplayName;		
		}

		public int getLevel() { return level; }
		public String getCrateDisplayName() { return crateDisplayName; }
		public String getKeyDisplayName() { return keyDisplayName; }

	}



}
