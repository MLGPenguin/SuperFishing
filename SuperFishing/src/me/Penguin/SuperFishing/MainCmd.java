package me.Penguin.SuperFishing;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Penguin.SuperFishing.GUIs.fishshop;
import me.Penguin.SuperFishing.crates.Crates;
import me.Penguin.SuperFishing.crates.Crates.CrateType;
import me.Penguin.SuperFishing.keys.Key;
import me.Penguin.SuperFishing.objects.Fish;
import me.Penguin.SuperFishing.objects.Fish.FISH;
import me.Penguin.SuperFishing.objects.Settings;
import me.Penguin.SuperFishing.objects.valueList;
import me.Penguin.SuperFishing.utils.m;
import me.Penguin.SuperFishing.utils.perm;
import me.Penguin.SuperFishing.utils.u;

public class MainCmd implements TabExecutor {

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
			if (cmd.equalsIgnoreCase("give")) {
				// fishing give <player> <type> <key|crate> <amount>
				if (!s.hasPermission(perm.admin.get())) return send(s, m.noPermission);					
				if (args.length != 5) return send(s, m.fishinggivesyntax);
				if (!u.isPlayer(args[1])) return send(s, m.invalidPlayerOther(args[1]));
				if (!(args[3].equalsIgnoreCase("key") || args[3].equalsIgnoreCase("crate"))) return send(s, m.fishinggivesyntax);
				if (CrateType.valueOf(args[2]) == null) return send(s, m.fishinggivesyntax);
				if (!u.isInt(args[4])) return send(s, m.fishinggivesyntax);
				
				Player target = Bukkit.getPlayer(args[1]);
				
				if (!u.hasInventorySpace(target)) return send(s, m.targetnoinventoryspace);
				
				boolean key = args[3].equalsIgnoreCase("key");				
				CrateType type = CrateType.valueOf(args[2]);
				int amount = Integer.parseInt(args[4]);
				ItemStack item = key ? new Key(type).getItem() : Crates.getCrate(type).getItem();
				item.setAmount(amount);
				target.getInventory().addItem(item);
				target.sendMessage(u.cc(m.prefix() + "&bReceived a " + type.toString() + (key ? " key" : " crate")));
				return true;				
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
			if (args.length == 1 && s.hasPermission(perm.admin.get())) return u.TabCompleter(args[0], "give", "reload", "getWorldID"); 
			String bmd = args[0];
			// fishing give <player> <type> <key|crate> <amount>
			if (bmd.equalsIgnoreCase("give") && s.hasPermission(perm.admin.get())) {
				switch (args.length) {
				case 3: return u.TabCompleter(args[2], Crates.getCrateNames());
				case 4: return list("key", "crate");
				case 5: return list("1");
				default: return null;
				}				
			}
		}

		return null;
	}


	public List<String> list(String... args){
		return Arrays.asList(args);
	}
	
	public boolean send(CommandSender s, String msg) {
		s.sendMessage(u.cc(msg));
		return true;
	}






}