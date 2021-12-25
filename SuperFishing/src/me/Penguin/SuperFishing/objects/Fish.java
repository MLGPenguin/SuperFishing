package me.Penguin.SuperFishing.objects;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Penguin.SuperFishing.utils.MiniItemBuilder;
import me.Penguin.SuperFishing.utils.u;

public class Fish {
	
	private Material type;
	private double chance, price;
	private Catch CatchType;
	private boolean isFish;
	private String FISHname;
	
	public Fish(Material type, double chance, Catch Catch, double price, boolean isFish) {
		this.type = type;
		this.chance = chance;
		this.CatchType = Catch;
		this.price = price;
		this.isFish = isFish;
		this.FISHname = type.toString().replaceAll("_", "").toLowerCase() + CatchType.getTier();
	}
	
	public Material getType() { return type; }
	public double getChance() { return chance; }
	public Catch getCatchType() { return CatchType; }
	public double getPrice() { return price; }
	public boolean isFish() { return isFish; }
	public String getFISHname() { return FISHname; }
	public String getName() { return CatchType.getColour() + "&n" + u.capitaliseFirstLetters(type.toString().replaceAll("_", " ")); }
	public ItemStack getItem(boolean forShop, Player p, int amountInInventory) {		
		MiniItemBuilder fish = new MiniItemBuilder(type)
				.setName(getName())				
				.setLocname(FISHname);
		if (forShop) {			
			return fish.addLores(
					"&7Catch Type: " + CatchType.getName(), 
					"&7Sell Price: &a&n" + "$" + u.dc(price), "",
					"&7You have: " + u.dc(amountInInventory)).build();
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
	
	public static List<Material> fishMaterials = Arrays.asList(Material.COD, Material.SALMON, Material.PUFFERFISH, Material.TROPICAL_FISH, Material.PRISMARINE_SHARD,
															Material.PRISMARINE_CRYSTALS, Material.INK_SAC, Material.SCUTE, Material.NAUTILUS_SHELL, Material.TURTLE_EGG);
	public enum FISH {		
		cod0(new Fish(Material.COD, 0.2350430, Catch.BASIC, 300, true)),
		cod1(new Fish(Material.COD, 0.03388462, Catch.RARE, 12500, true)),
		cod2(new Fish(Material.COD, 0.0070513, Catch.EXOTIC, 225000, true)),
		cod3(new Fish(Material.COD, 0.0007785, Catch.LEGENDARY, 3150000, true)),
		salmon0(new Fish(Material.SALMON, 0.2115387, Catch.BASIC, 350, true)),
		salmon1(new Fish(Material.SALMON, 0.0310257, Catch.RARE, 16500, true)),
		salmon2(new Fish(Material.SALMON, 0.0063462, Catch.EXOTIC, 262500, true)),
		salmon3(new Fish(Material.SALMON, 0.0006938, Catch.LEGENDARY, 3500000, true)),
		pufferfish0(new Fish(Material.PUFFERFISH, 0.1692310, Catch.BASIC, 400, true)),
		pufferfish1(new Fish(Material.PUFFERFISH, 0.0253846, Catch.RARE, 16500, true)),
		pufferfish2(new Fish(Material.PUFFERFISH, 0.0059231, Catch.EXOTIC, 275000, true)),
		pufferfish3(new Fish(Material.PUFFERFISH, 0.0006262, Catch.LEGENDARY, 3850000, true)),
		tropicalfish0(new Fish(Material.TROPICAL_FISH, 0.1598293, Catch.BASIC, 450, true)),
		tropicalfish1(new Fish(Material.TROPICAL_FISH, 0.0225641, Catch.RARE, 18000, true)),
		tropicalfish2(new Fish(Material.TROPICAL_FISH, 0.0056410, Catch.EXOTIC, 300000, true)),
		tropicalfish3(new Fish(Material.TROPICAL_FISH, 0.0005745, Catch.LEGENDARY, 4200000, true)),
		prismarineshard1(new Fish(Material.PRISMARINE_SHARD, 0.0203077, Catch.RARE, 20000, false)),
		prismarineshard2(new Fish(Material.PRISMARINE_SHARD, 0.0049359, Catch.EXOTIC, 340000, false)),
		prismarineshard3(new Fish(Material.PRISMARINE_SHARD, 0.0005077, Catch.LEGENDARY, 4800000, false)),
		prismarinecrystals1(new Fish(Material.PRISMARINE_CRYSTALS, 0.0180513, Catch.RARE, 22500, false)),
		prismarinecrystals2(new Fish(Material.PRISMARINE_CRYSTALS, 0.0045128, Catch.EXOTIC, 375000, false)),
		prismarinecrystals3(new Fish(Material.PRISMARINE_CRYSTALS, 0.0004231, Catch.LEGENDARY, 5750000, false)),
		inksac1(new Fish(Material.INK_SAC, 0.0157949, Catch.RARE, 2700, false)),
		inksac2(new Fish(Material.INK_SAC, 0.0038077, Catch.EXOTIC, 425000, false)),
		inksac3(new Fish(Material.INK_SAC, 0.0003554, Catch.LEGENDARY, 6500000, false)),
		scute1(new Fish(Material.SCUTE, 0.0112821, Catch.RARE, 37000, false)),
		scute2(new Fish(Material.SCUTE, 0.0028205, Catch.EXOTIC, 550000, false)),
		scute3(new Fish(Material.SCUTE, 0.0002877, Catch.LEGENDARY, 8000000, false)),
		nautilusshell2(new Fish(Material.NAUTILUS_SHELL, 0.0007897, Catch.EXOTIC, 5000000, false)),
		nautilusshell3(new Fish(Material.NAUTILUS_SHELL, 0.0000197, Catch.LEGENDARY, 85000000, false)),
		turtleegg3(new Fish(Material.TURTLE_EGG, 0.0000056, Catch.LEGENDARY, 1000000000, false));
		
		private Fish fish;
		
		FISH(Fish fish) {
			this.fish = fish;
		}
		
	public Fish getFish() { return fish; }
	
	}
	
	
	

}

