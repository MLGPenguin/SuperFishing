package me.Penguin.SuperFishing;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Penguin.MainUtil.command;
import me.Penguin.MainUtil.command.ctype;
import me.Penguin.MainUtil.u;
import me.Penguin.SuperFishing.GUIs.fishshop;
import me.Penguin.SuperFishing.objects.Crate;
import me.Penguin.SuperFishing.objects.Crate.CrateType;
import me.Penguin.SuperFishing.objects.Settings;
import me.Penguin.SuperFishing.utils.perm;

public class MainCmd implements TabExecutor {

	@SuppressWarnings("unused")
	private Main plugin;

	public MainCmd(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("fishing").setExecutor(this);
		plugin.getCommand("fishingshop").setExecutor(this);
		plugin.getCommand("fishtest").setExecutor(this);
	}





	@Override
	public boolean onCommand(CommandSender s,  Command cmmnd,  String label, String[] args) {		
		if (cmmnd.getName().equalsIgnoreCase("fishing")) {
			String cmd = args[0];
			if (cmd.equalsIgnoreCase("givecrate")) {
				if (s.hasPermission(perm.givecrate.get())) {
					if (args.length == 4) {
						if (command.checkCondition(ctype.PLAYER, args[1])) {
							if (CrateType.valueOf(args[2]) != null) {
								if (command.checkCondition(ctype.INT, args[3])) {
									Player p = u.getPlayer(args[1]);
									CrateType type = CrateType.valueOf(args[2]);
									int amount = Integer.parseInt(args[3]);
									if (u.hasInventorySpace(p)) {
										ItemStack crate = new Crate(type).getItem();
										crate.setAmount(amount);
										p.getInventory().addItem(crate);
										p.sendMessage("received a " + type.toString() + " crate");
										return true;
									}
								}
							}
						}
					}
				}
			} else if (cmd.equalsIgnoreCase("reload")) {
				plugin.reloadConfig();
				Settings.setup();
				s.sendMessage("reloaded");
				return true;
			}
		} else if (cmmnd.getName().equalsIgnoreCase("fishingshop")) {
			fishshop.open((Player) s);
			return true;
		} else if (cmmnd.getName().equalsIgnoreCase("fishtest")) {
			if (s.hasPermission(perm.givecrate.get())) {
				try {
					int amount = Integer.parseInt(args[0]);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete( CommandSender s,  Command cmd, String label,  String[] args) {
		if (cmd.getName().equalsIgnoreCase("fishing")) {
			if (args.length == 1 && s.hasPermission(perm.givecrate.get())) return u.TabCompleter(args[0], "givecrate", "reload"); 
			String bmd = args[0];
			if (bmd.equalsIgnoreCase("givecrate")) {
				if (s.hasPermission(perm.givecrate.get())) {
					if (args.length == 2) return u.TabCompleter(args[1], u.getAllPlayersNames());
					if (args.length == 3) {
						List<String> crates = new ArrayList<>();
						for (CrateType x : CrateType.values()) crates.add(x.toString());
						return u.TabCompleter(args[2], crates);
					}
					if (args.length == 4) return u.TabCompleter(args[3], "1", "2", "16", "64"); 
				}
			}
		}

		return null;
	}








}