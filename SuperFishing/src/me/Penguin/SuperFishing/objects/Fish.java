package me.Penguin.SuperFishing.objects;

public class Fish {
	
	private fishType type;
	private Catch level;
	private double chance;
	
	public enum Catch {
		// BASIC will not show up underneath the fish, others will show "GOOD CATCH"... ETC.
		BASIC, 
		GOOD, 
		EXCELLENT, 
		OUTSTANDING
		;
		
	}
	
	public enum fishType {
		COD, 
		SALMON, 
		PUFFERFISH, 
		TROPICAL_FISH, 
		COOKED_COD,
		COOKED_SALMON,
		PRISMARINE_SHARD,
		PRISMARINE_CRYSTALS,
		INK_SAC,
		SCUTE,
		NAUTILUS_SHELL,
		TURTLE_EGG
		;	
	}
	
	
	public Fish(fishType type, Catch tier) {
		this.type = type;
		this.level = tier;
	}
	
	

}

