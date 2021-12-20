package me.Penguin.SuperFishing.objects;

import org.bukkit.configuration.file.FileConfiguration;

import me.Penguin.MainUtil.u;
import penguin.supertags.Main;

public class Settings {

	public static String FishingShopTitle;
	
	public static void setup() {
		FileConfiguration c = Main.getPlugin(Main.class).getConfig();
		
		FishingShopTitle = u.cc(c.getString("FishingShopTitle"));
	}
	

	
}
