package me.Penguin.SuperFishing.objects;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.Penguin.SuperFishing.utils.MiniItemBuilder;
import me.Penguin.SuperFishing.utils.u;

public class Fish {
	
	private Material type;
	private double chance, price;
	private Catch CatchType;
	private boolean isFish;
	
	public Fish(Material type, double chance, Catch Catch, double price, boolean isFish) {
		this.type = type;
		this.chance = chance;
		this.CatchType = Catch;
		this.price = price;
		this.isFish = isFish;
	}
	
	public Material getType() { return type; }
	public double getChance() { return chance; }
	public Catch getCatchType() { return CatchType; }
	public double getPrice() { return price; }
	public boolean isFish() { return isFish; }
	public ItemStack getItem(boolean forShop) {		
		MiniItemBuilder fish = new MiniItemBuilder(type)
				.setName(CatchType.getColour() + "&n" + u.capitaliseFirstLetters(type.toString().replaceAll("_", " ")))				
				.setLocname(type.toString().replaceAll("_", "").toLowerCase() + CatchType.getTier());
		if (forShop) {
			return fish.addLores(
					"&7Catch Type: " + CatchType.getName(), 
					"&7Sell Price: &a&n" + "$" + u.dc(price)).build();
		} else {
			return fish.addLores("",
					"&7Catch Type: " + CatchType.getName(), "",
					"&7This fish can be sold to the ",
					"&6Fish Merchant &7at spawn!").build();
		}
	}
	
	public enum Catch {
		BASIC(0, "&c","&cBASIC"), 
		RARE(1, "&3", "&3RARE"), 
		EXOTIC(2, "&d", "&5&ki&dEXOTIC&5&ki"), 
		LEGENDARY(3, "&6", "&c&k8&6LEGENDARY&c&k8");
		
		int tier;
		String colour, catchname;
		
		Catch(int tier, String colour, String catchName) {
			this.tier = tier;
			this.colour = colour;
			this.catchname = catchName;
		}
		public int getTier() { return tier; }
		public String getColour() { return u.cc(colour); }
		public String getName() { return u.cc(catchname); }
}
	
	
	public enum FISH {		
		cod0(new Fish(Material.COD, 0.173362, Catch.BASIC, 250, true)),
		cod1(new Fish(Material.COD, 0.0312052, Catch.RARE, 3750, true)),
		cod2(new Fish(Material.COD, 0.005851, Catch.EXOTIC, 30000, true)),
		cod3(new Fish(Material.COD, 0.0014042, Catch.LEGENDARY, 150000, true)),
		salmon0(new Fish(Material.SALMON, 0.1560258, Catch.BASIC, 300, true)),
		salmon1(new Fish(Material.SALMON, 0.0228838, Catch.RARE, 4500, true)),
		salmon2(new Fish(Material.SALMON, 0.0046808, Catch.EXOTIC, 36000, true)),
		salmon3(new Fish(Material.SALMON, 0.0010922, Catch.LEGENDARY, 180000, true)),
		pufferfish0(new Fish(Material.PUFFERFISH, 0.0520086, Catch.BASIC, 1300, true)),
		pufferfish1(new Fish(Material.PUFFERFISH, 0.0083214, Catch.RARE, 19500, true)),
		pufferfish2(new Fish(Material.PUFFERFISH, 0.0014302, Catch.EXOTIC, 156000, true)),
		pufferfish3(new Fish(Material.PUFFERFISH, 0.0003901, Catch.LEGENDARY, 780000, true)),
		tropicalfish0(new Fish(Material.TROPICAL_FISH, 0.0390065, Catch.BASIC, 1800, true)),
		tropicalfish1(new Fish(Material.TROPICAL_FISH, 0.006241, Catch.RARE, 27000, true)),
		tropicalfish2(new Fish(Material.TROPICAL_FISH, 0.0011702, Catch.EXOTIC, 216000, true)),
		tropicalfish3(new Fish(Material.TROPICAL_FISH, 0.0002808, Catch.LEGENDARY, 1080000, true)),
		cookedcod0(new Fish(Material.COOKED_COD, 0.2371593, Catch.BASIC, 300, true)),
		cookedcod1(new Fish(Material.COOKED_COD, 0.0374462, Catch.RARE, 4500, true)),
		cookedcod2(new Fish(Material.COOKED_COD, 0.006241, Catch.EXOTIC, 36000, true)),
		cookedcod3(new Fish(Material.COOKED_COD, 0.0016851, Catch.LEGENDARY, 180000, true)),
		cookedsalmon0(new Fish(Material.COOKED_SALMON, 0.1625269, Catch.BASIC, 350, true)),
		cookedsalmon1(new Fish(Material.COOKED_SALMON, 0.0325054, Catch.RARE, 5250, true)),
		cookedsalmon2(new Fish(Material.COOKED_SALMON, 0.0048758, Catch.EXOTIC, 42000, true)),
		cookedsalmon3(new Fish(Material.COOKED_SALMON, 0.0012541, Catch.LEGENDARY, 210000, true)),
		prismarineshard1(new Fish(Material.PRISMARINE_SHARD, 0.0041607, Catch.RARE, 37500, false)),
		prismarineshard2(new Fish(Material.PRISMARINE_SHARD, 0.0008841, Catch.EXOTIC, 300000, false)),
		prismarineshard3(new Fish(Material.PRISMARINE_SHARD, 0.0002184, Catch.LEGENDARY, 1500000, false)),
		prismarinecrystals1(new Fish(Material.PRISMARINE_CRYSTALS, 0.0020803, Catch.RARE, 75000, false)),
		prismarinecrystals2(new Fish(Material.PRISMARINE_CRYSTALS, 0.0004421, Catch.EXOTIC, 600000, false)),
		prismarinecrystals3(new Fish(Material.PRISMARINE_CRYSTALS, 0.0001061, Catch.LEGENDARY, 3000000, false)),
		inksac1(new Fish(Material.INK_SAC, 0.0014978, Catch.RARE, 112500, false)),
		inksac2(new Fish(Material.INK_SAC, 0.0002925, Catch.EXOTIC, 900000, false)),
		inksac3(new Fish(Material.INK_SAC, 0.0000737, Catch.LEGENDARY, 4500000, false)),
		scute1(new Fish(Material.SCUTE, 0.0009362, Catch.RARE, 150000, false)),
		scute2(new Fish(Material.SCUTE, 0.0001989, Catch.EXOTIC, 1200000, false)),
		scute3(new Fish(Material.SCUTE, 0.0000468, Catch.LEGENDARY, 6000000, false)),
		nautilusshell2(new Fish(Material.NAUTILUS_SHELL, 0.000011, Catch.EXOTIC, 15000000, false)),
		nautilusshell3(new Fish(Material.NAUTILUS_SHELL, 0.0000023, Catch.LEGENDARY, 120000000, false)),
		turtleegg3(new Fish(Material.TURTLE_EGG, 0.0000015, Catch.LEGENDARY, 300000000, false));
		
		private Fish fish;
		
		FISH(Fish fish) {
			this.fish = fish;
		}
		
	public Fish getFish() { return fish; }
	
	}
	
	
	

}

