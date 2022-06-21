package me.Penguin.SuperFishing.crates;

import java.util.Arrays;
import java.util.List;

import me.Penguin.SuperFishing.crates.Crates.CrateType;

public class CrateFisherman extends Crate {

	@Override
	public String getName() {
		return "&a&lFISHERMANS TREASURE";		
	}

	@Override
	public List<String> getDescription() {
		return Arrays.asList(
				"&7A &nbattle&7 at sea lead many ships to be sunk!",
				"&7Many &nriches&7 were lost at sea that day"
				);
	}

	@Override
	public CrateType getType() {
		return CrateType.FISHERMANS;		
	}

	@Override
	public String getColourTheme() {
		return "&a";		
	}

	
	
	
	

}
