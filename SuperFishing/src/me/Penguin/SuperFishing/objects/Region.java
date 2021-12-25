package me.Penguin.SuperFishing.objects;

import org.bukkit.Location;
import org.bukkit.World;

public class Region {

	private Location max;
	private Location min;

	public Region(Location corner1, Location corner2) {
		World w = corner1.getWorld();
		int maxX = Math.max(corner1.getBlockX(), corner2.getBlockX());
		int minX = Math.min(corner1.getBlockX(), corner2.getBlockX());

		int maxY = Math.max(corner1.getBlockY(), corner2.getBlockY());
		int minY = Math.min(corner1.getBlockY(), corner2.getBlockY());

		int maxZ = Math.max(corner1.getBlockZ(), corner2.getBlockZ());		
		int minZ = Math.min(corner1.getBlockZ(), corner2.getBlockZ());

		max = new Location(w, maxX, maxY, maxZ);
		min = new Location(w, minX, minY, minZ);		
	}


	public boolean contains(Location loc) {
		if (max.getWorld().getUID().equals(loc.getWorld().getUID())) {
			if (loc.getBlockX() > min.getBlockX() && loc.getBlockX() < max.getBlockX()) {
				if (loc.getBlockY() > min.getBlockY() && loc.getBlockY() < max.getBlockY()) {
					if (loc.getBlockZ() > min.getBlockZ() && loc.getBlockZ() < max.getBlockZ()) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
