package me.Penguin.SuperFishing;


import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	// EXPORT WITH AUTIL
	
	@Override
	public void onEnable() {
		
		new MainListener(this);
		new MainCmd(this);		
		
		//getConfig().options().copyDefaults();
		//saveDefaultConfig();
		
	}
	

}
