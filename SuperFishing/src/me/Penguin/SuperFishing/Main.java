package me.Penguin.SuperFishing;


import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.Penguin.SuperFishing.objects.Fish;
import me.Penguin.SuperFishing.objects.Fish.FISH;
import me.Penguin.SuperFishing.objects.Settings;
import me.Penguin.SuperFishing.utils.m;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	
	public static HashMap<Double, Fish> chances = new HashMap<>();
	public Economy eco;
	
	@Override
	public void onEnable() {
		
		Settings.setup();
		m.setup();
	
	//	eco = getEconomy();
		
		for (FISH f : FISH.values()) chances.put(f.getFish().getChance(), f.getFish());
		
		
		new MainListener(this);
		new MainCmd(this);		
		
		getConfig().options().copyDefaults();
		saveDefaultConfig();
		
		
		
	}
	
	public Economy getEconomy() {
		if (!(setupEconomy())) {
			getLogger().severe(ChatColor.RED + "Vault and Economy are required!");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
		}	
		return eco;
	}


	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economy = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economy != null) eco = economy.getProvider();
		return (eco != null);
	}


}
		

