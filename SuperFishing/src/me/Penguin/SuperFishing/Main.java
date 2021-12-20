package me.Penguin.SuperFishing;


import java.util.HashMap;

import org.bukkit.plugin.java.JavaPlugin;

import me.Penguin.SuperFishing.objects.Fish;
import me.Penguin.SuperFishing.objects.Fish.FISH;
import me.Penguin.SuperFishing.objects.Settings;

public class Main extends JavaPlugin {
	
	public static HashMap<Double, Fish> chances = new HashMap<>();
	
	@Override
	public void onEnable() {
	
		for (FISH f : FISH.values()) chances.put(f.getFish().getChance(), f.getFish());
		
		
		new MainListener(this);
		new MainCmd(this);		
		
		getConfig().options().copyDefaults();
		saveDefaultConfig();
		
		Settings.setup();
		
	}
		

}
