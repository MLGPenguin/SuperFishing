package me.Penguin.SuperFishing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.Penguin.SuperFishing.GUIs.confirmSell;
import me.Penguin.SuperFishing.GUIs.fishshop;
import me.Penguin.SuperFishing.objects.Crate;
import me.Penguin.SuperFishing.objects.Crate.CrateType;
import me.Penguin.SuperFishing.objects.Fish;
import me.Penguin.SuperFishing.objects.Fish.Catch;
import me.Penguin.SuperFishing.objects.Fish.FISH;
import me.Penguin.SuperFishing.objects.Key;
import me.Penguin.SuperFishing.utils.u;
import net.milkbowl.vault.economy.Economy;

public class MainListener implements Listener{

	private Main plugin;
	private Economy eco;
	public static HashMap<UUID, fishshop> viewingFishShop = new HashMap<>();
	public static HashMap<UUID, confirmSell> confirming = new HashMap<>();

	public MainListener(Main plugin) {
		this.plugin = plugin;
		this.eco = plugin.eco;
		Bukkit.getPluginManager().registerEvents(this, plugin);		
	}


	private void bc(String s) { Bukkit.getPlayer("MLGPenguin").sendMessage(u.hc(s)); }

	@EventHandler
	public void onFishing(PlayerFishEvent e) {		
		if (e.getState() == State.CAUGHT_FISH) {
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
			if (fish.getCatchType() == Catch.BASIC) p.playSound(p.getLocation(), Sound.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE, 1, 1);
			else if (fish.getCatchType() == Catch.RARE) {
				
			} else if (fish.getCatchType() == Catch.EXOTIC) {
				
			}
		}
	}

	@EventHandler
	public void onClickCrate(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR) {	
			Player p = e.getPlayer();
			ItemStack held = p.getInventory().getItemInMainHand();
			if (Crate.isCrate(held)) {
				e.setCancelled(true);
				CrateType type = Crate.getCrateType(held);
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
				p.getInventory().setItemInMainHand(held);				
				p.sendMessage("received some rewards");
			}
		}
	}



	@EventHandler
	public void dontPlaceTheCrateorKeys(BlockPlaceEvent e) {
		Material placed = e.getBlock().getType();
		if (placed == Key.material || placed == Crate.material) {
			ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
			if (hand != null && hand.getItemMeta() != null && hand.getItemMeta().hasLocalizedName()) {
				if (hand.getItemMeta().getLocalizedName() == Crate.locname) {					
					e.setCancelled(true);
					e.getPlayer().sendMessage("[fishing] You can't place that!");
				}
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
						new confirmSell(f).open(true, null);
					} else {
						new confirmSell(f).open(false, FISH.valueOf(locname));
					}
				}
			}
		}
		if (confirming.containsKey(uuid)) {
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
		p.sendMessage("sold " + amountFish + " fish for $" + u.dc(price) + "ish");
		eco.depositPlayer(p, price);
		new fishshop(p).open();
	}


}
