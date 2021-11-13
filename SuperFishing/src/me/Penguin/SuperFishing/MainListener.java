package me.Penguin.SuperFishing;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.Penguin.MainUtil.u;
import me.Penguin.SuperFishing.objects.Crate;
import me.Penguin.SuperFishing.objects.Crate.CrateType;
import me.Penguin.SuperFishing.objects.Key;

public class MainListener implements Listener{

	private Main plugin;

	public MainListener(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}


	private void bc(String s) { Bukkit.getPlayer("MLGPenguin").sendMessage(u.hc(s)); }

	@EventHandler
	public void onFishing(PlayerFishEvent e) {
		ItemStack test = new ItemStack(Material.DIAMOND);
		if (e.getState() == State.CAUGHT_FISH) {
			Item caught = (Item) e.getCaught();
			caught.setItemStack(test);
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


}
