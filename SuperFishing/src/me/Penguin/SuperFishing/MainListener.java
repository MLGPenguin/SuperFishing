package me.Penguin.SuperFishing;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.Penguin.SuperFishing.GUIs.confirmSell;
import me.Penguin.SuperFishing.GUIs.fishshop;
import me.Penguin.SuperFishing.crates.Crates;
import me.Penguin.SuperFishing.keys.keys;
import me.Penguin.SuperFishing.objects.Fish;
import me.Penguin.SuperFishing.objects.Fish.FISH;
import me.Penguin.SuperFishing.objects.Settings;
import me.Penguin.SuperFishing.utils.m;
import me.Penguin.SuperFishing.utils.u;
import net.milkbowl.vault.economy.Economy;

public class MainListener implements Listener{

	@SuppressWarnings("unused")
	private Main plugin;
	private Economy eco;
	public static HashMap<UUID, fishshop> viewingFishShop = new HashMap<>();
	public static HashMap<UUID, confirmSell> confirming = new HashMap<>();

	public MainListener(Main plugin) {
		this.plugin = plugin;
		this.eco = plugin.eco;
		Bukkit.getPluginManager().registerEvents(this, plugin);		
	}

	@EventHandler
	public void onFishing(PlayerFishEvent e) {		
		if (e.getState() == State.CAUGHT_FISH) {
			Instant start = Instant.now();
			if (Settings.isFishable(e.getHook().getLocation())) {
				Fish fish = chooseRandomFish();
				ItemStack item = fish.getItem(false, null, 0);
				Item caught = (Item) e.getCaught();
				item.setAmount(1);
				caught.setItemStack(item);
				Player p = e.getPlayer();
				switch (fish.getCatchType()) {
				case BASIC: 
					p.playSound(p.getLocation(), Sound.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE, 1, 1);
					break;
				case RARE:
					p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
					p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1, 1);
					break;
				case EXOTIC:
					p.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1, 1);
					p.playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 1);
					break;
				case LEGENDARY:
					p.playSound(p.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1, 1);
					break;
				default: break;					
				}
				Instant end = Instant.now();
				u.bcif(p, u.getTimeMsg("&b", true, start, end));
			}
		}
	}

	@EventHandler
	public void onClickCrate(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {	
			Player p = e.getPlayer();
			ItemStack held = p.getInventory().getItemInMainHand();
			if (Crates.isCrate(held)) {
				e.setCancelled(true);
				//CrateType type = Crates.getType(held);
				/*
				if (p.isSneaking()) {
					while (held.getAmount() > 0) {
						if (u.hasInventorySpace(p)) {
							Crate.openCrate(p, type, 1);
							held.setAmount(held.getAmount()-1);
						} else {
							p.sendMessage("inventory full");
							break;
						}
					}
				} else {
					Crate.openCrate(p, type, 1);
					held.setAmount(held.getAmount()-1);
				}
				*/
				p.getInventory().setItemInMainHand(held);				
				p.sendMessage("Congrats, this is a crate");
			} else if (keys.isKey(held)) {
				e.setCancelled(true);
				p.sendMessage("Congrats, this is a key!");
			}
		}
	}

	
	@EventHandler
	public void removeFromLists(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		UUID uuid = p.getUniqueId();
		if (viewingFishShop.containsKey(uuid)) viewingFishShop.remove(uuid);		
		if (confirming.containsKey(uuid)) confirming.remove(uuid);
	}
	
	@EventHandler
	public void fishShopHandler(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		UUID uuid = p.getUniqueId();
		int slot = e.getSlot();
		if (viewingFishShop.containsKey(uuid)) {
			e.setCancelled(true);
			if (e.getClickedInventory() == e.getView().getTopInventory()) {
				if (!fishshop.paneSlots.contains(slot)) {
					fishshop f = viewingFishShop.get(uuid);
					ItemStack clicked = e.getCurrentItem();
					String locname = clicked.getItemMeta().getLocalizedName();
					if (locname.equals("sellall")) {
						if (f.getTotalFishCount() == 0) {
							p.sendMessage(m.nothingtoSell);
							return;
						} else new confirmSell(f).open(true, null);						
					} else {
						FISH fsh = FISH.valueOf(locname);
						if (f.getAmount(fsh) == 0) {
							p.sendMessage(m.nothingtoSell);
							return;
						} else new confirmSell(f).open(false, FISH.valueOf(locname));
					}
				}
			}
		} else if (confirming.containsKey(uuid)) {
			if (e.getClickedInventory() == e.getView().getTopInventory()) {
				confirmSell c = confirming.get(uuid);
				if (confirmSell.cancels.contains(slot)) {
					new fishshop(p).open();
				} else if (confirmSell.confirms.contains(slot)) sell(c);
			}
			e.setCancelled(true);
		}
	}
	
	public static Fish chooseRandomFish() {
		HashMap<Double, Fish> chances = Main.chances;
		List<Double> list = new ArrayList<Double>(chances.keySet());
		double value = new Random().nextDouble();
		int i = 0;
		while (value > 0 && i < list.size()) {
			value -= list.get(i);
			i++;
		}
		return chances.get(list.get(i-1));		
	}
	
	private void sell(confirmSell c) {
		double price = 0;
		int amountFish = 0;
		Player p = c.getPlayer();
		for (ItemStack x : p.getInventory().getContents()) {
			if (x != null && x.hasItemMeta() && x.getItemMeta().hasLocalizedName() && Fish.fishMaterials.contains(x.getType())) {
				try {
					FISH f = FISH.valueOf(x.getItemMeta().getLocalizedName());
					if (c.isAll()) {
						price += (f.getFish().getPrice() * x.getAmount());
						amountFish += x.getAmount();
						p.getInventory().remove(x);
					}
					else {
						if (c.getFISH() == f) {
							price += (f.getFish().getPrice() * x.getAmount());
							amountFish += x.getAmount();
							p.getInventory().remove(x);
						} else continue;
					}
				} catch (IllegalArgumentException e) {}
			}
		}
		p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
		p.sendMessage(u.cc("&8[&7&LSB&6&LZEN&8] &7You sold &6" + amountFish + " &7fish for &a$" + u.dc(price)));
		eco.depositPlayer(p, price);
		new fishshop(p).open();
	}


}
