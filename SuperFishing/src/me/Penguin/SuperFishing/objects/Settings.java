package me.Penguin.SuperFishing.objects;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import me.Penguin.SuperFishing.Main;
import me.Penguin.SuperFishing.utils.u;

public class Settings {

	private static FileConfiguration c;
	
	public static String FishingShopTitle;
	public static List<Region> regions;
	
	public static void setup() {
		c = Main.getPlugin(Main.class).getConfig();
		
		FishingShopTitle = u.cc(c.getString("FishingShopTitle"));
		regions = getRegions();
	}
	
	public static boolean isFishable(Location l) {
		boolean found = false;
		for (Region r : regions) if (r.contains(l)) found = true;
		return found;		
	}
	
	private static Region getRegion(String region) {	
		String path = "Regions." + region;
		try {
		World w = Bukkit.getWorld(UUID.fromString(c.getString(path + ".WorldUID")));
		int x1 = c.getInt(path + ".loc1.x");
		int y1 = c.getInt(path + ".loc1.y");
		int z1 = c.getInt(path + ".loc1.z");
		int x2 = c.getInt(path + ".loc2.x");
		int y2 = c.getInt(path + ".loc2.y");
		int z2 = c.getInt(path + ".loc2.z");
		Location loc1 = new Location(w, x1, y1, z1);
		Location loc2 = new Location(w, x2, y2, z2);
		return new Region(loc1, loc2);
		} catch (NullPointerException e) {
			Bukkit.getLogger().log(Level.SEVERE, "[SuperFishing] Failed to Register Region '" + region + "'");
			Bukkit.getLogger().log(Level.SEVERE, "This may or may not be an error, check your config.");
			return null;
		}
	}
	
	private static Set<String> getRegionNames() {
		try {
			return c.getConfigurationSection("Regions").getKeys(false);
		} catch (NullPointerException e) {
			Bukkit.getLogger().log(Level.SEVERE, "[SuperFishing] config does not contain a region section, please create one");
			return new HashSet<>();
		}
	}
	
	private static List<Region> getRegions() {
		return getRegionNames().stream().map(Settings::getRegion).collect(Collectors.toList());
	}

	
}
