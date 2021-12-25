package me.Penguin.SuperFishing;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;

import me.Penguin.MainUtil.command;
import me.Penguin.MainUtil.command.ctype;
import me.Penguin.MainUtil.u;
import me.Penguin.SuperFishing.GUIs.fishshop;
import me.Penguin.SuperFishing.objects.Crate;
import me.Penguin.SuperFishing.objects.Crate.CrateType;
import me.Penguin.SuperFishing.objects.Fish;
import me.Penguin.SuperFishing.objects.Fish.FISH;
import me.Penguin.SuperFishing.objects.Settings;
import me.Penguin.SuperFishing.objects.valueList;
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
				if (s.hasPermission(perm.admin.get())) {
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
				if (s.hasPermission(perm.admin.get())) {
					if (args.length == 1) {
						plugin.reloadConfig();
						Settings.setup();
						s.sendMessage("reloaded");
						return true;
					}
				}
			} else if (cmd.equals("getWorldID")) {
				if (s.hasPermission(perm.admin.get())) {
					if (args.length == 1) {
						if (s instanceof Player) {
							Player p = (Player) s;
							p.sendMessage(p.getWorld().getUID().toString());
							return true;
						}
					}
				}
			}
		} else if (cmmnd.getName().equalsIgnoreCase("fishingshop")) {
			new fishshop((Player) s).open();
			return true;
		} else if (cmmnd.getName().equalsIgnoreCase("fishtest")) {
			if (s.hasPermission(perm.admin.get())) {
				if (s instanceof Player) {
					if (args.length == 1) {
						try {
							Player p = (Player) s;
							int amount = Integer.parseInt(args[0]);
							World w = p.getWorld();
							Location l = p.getLocation();
							valueList<FISH, Integer> map = new valueList<>();
							Instant start = Instant.now();
							for (int i = 0 ; i < amount ; i++) {
								Fish fish = MainListener.chooseRandomFish();
								map.addValue(FISH.valueOf(fish.getFISHname()), 1);
							}
							for (FISH f : map.keySet()) {
								while(map.get(f) > 0) {
									int stacksize = map.get(f) >= 64 ? 64 : map.get(f);
									map.removeVal(f, stacksize, false);
									ItemStack fs = f.getFish().getItem(false, null, 0);
									fs.setAmount(stacksize);
									w.dropItem(l, fs);
									p.playSound(l, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
								}
							}
							Instant end = Instant.now();
							if (p.getName().equals("MLGPenguin")) p.sendMessage(Duration.between(start, end).toMillis() + "ms");
						} catch (NumberFormatException e) {
							return false;
						}
						return true;
					}	
				}
							
			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete( CommandSender s,  Command cmd, String label,  String[] args) {
		if (cmd.getName().equalsIgnoreCase("fishing")) {
			if (args.length == 1 && s.hasPermission(perm.admin.get())) return u.TabCompleter(args[0], "givecrate", "reload", "getWorldID"); 
			String bmd = args[0];
			if (bmd.equalsIgnoreCase("givecrate")) {
				if (s.hasPermission(perm.admin.get())) {
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