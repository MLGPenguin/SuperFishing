package me.Penguin.SuperFishing.crates;

import java.util.ArrayList;
import java.util.List;

import me.Penguin.SuperFishing.crates.Crates.CrateType;

public class CrateDragon extends Crate {

	@Override
	public String getName() {
		return "&4&lDRAGONS TREASURE";		
	}

	@Override
	public List<String> getDescription() {
		return new ArrayList<>();		
	}

	@Override
	public CrateType getType() {
		return CrateType.DRAGONS;		
	}

	@Override
	public String getColourTheme() {
		return "&4";		
	}

}
