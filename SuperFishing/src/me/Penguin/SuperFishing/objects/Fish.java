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
			return fish.addLores(false,
					"&7Catch Type: " + CatchType.getName(), 
					"&7Sell Price: &a&n" + "$" + u.dc(price), "",
					"&7You have: " + u.dc(amountInInventory)).buildFish();
		} else {
			return fish.addLores(false, "",
					"&7Catch Type: " + CatchType.getName(), "",
					"&7This fish can be sold to the ",
					"&6Fish Merchant &7at spawn!").buildFish();
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
		cod0(new Fish(Material.COD, 0.2911336, Catch.BASIC, 300, true)),
		cod1(new Fish(Material.COD, 0.0069872, Catch.RARE, 12500, true)),
		cod2(new Fish(Material.COD, 0.0003882, Catch.EXOTIC, 225000, true)),
		cod3(new Fish(Material.COD, 0.0000312, Catch.LEGENDARY, 3150000, true)),
		salmon0(new Fish(Material.SALMON, 0.2620203, Catch.BASIC, 350, true)),
		salmon1(new Fish(Material.SALMON, 0.0064049, Catch.RARE, 16500, true)),
		salmon2(new Fish(Material.SALMON, 0.0003494, Catch.EXOTIC, 262500, true)),
		salmon3(new Fish(Material.SALMON, 0.0000279, Catch.LEGENDARY, 3500000, true)),
		pufferfish0(new Fish(Material.PUFFERFISH, 0.2096162, Catch.BASIC, 400, true)),
		pufferfish1(new Fish(Material.PUFFERFISH, 0.0052404, Catch.RARE, 16500, true)),
		pufferfish2(new Fish(Material.PUFFERFISH, 0.0003261, Catch.EXOTIC, 275000, true)),
		pufferfish3(new Fish(Material.PUFFERFISH, 0.0000251, Catch.LEGENDARY, 3850000, true)),
		tropicalfish0(new Fish(Material.TROPICAL_FISH, 0.1979709, Catch.BASIC, 450, true)),
		tropicalfish1(new Fish(Material.TROPICAL_FISH, 0.0046581, Catch.RARE, 18000, true)),
		tropicalfish2(new Fish(Material.TROPICAL_FISH, 0.0003105, Catch.EXOTIC, 300000, true)),
		tropicalfish3(new Fish(Material.TROPICAL_FISH, 0.0000231, Catch.LEGENDARY, 4200000, true)),
		prismarineshard1(new Fish(Material.PRISMARINE_SHARD, 0.0041923, Catch.RARE, 20000, false)),
		prismarineshard2(new Fish(Material.PRISMARINE_SHARD, 0.0002717, Catch.EXOTIC, 340000, false)),
		prismarineshard3(new Fish(Material.PRISMARINE_SHARD, 0.0000204, Catch.LEGENDARY, 4800000, false)),
		prismarinecrystals1(new Fish(Material.PRISMARINE_CRYSTALS, 0.0037265, Catch.RARE, 22500, false)),
		prismarinecrystals2(new Fish(Material.PRISMARINE_CRYSTALS, 0.0002484, Catch.EXOTIC, 375000, false)),
		prismarinecrystals3(new Fish(Material.PRISMARINE_CRYSTALS, 0.0000170, Catch.LEGENDARY, 5750000, false)),
		inksac1(new Fish(Material.INK_SAC, 0.0032607, Catch.RARE, 27000, false)),
		inksac2(new Fish(Material.INK_SAC, 0.0002096, Catch.EXOTIC, 425000, false)),
		inksac3(new Fish(Material.INK_SAC, 0.0000143, Catch.LEGENDARY, 6500000, false)),
		scute1(new Fish(Material.SCUTE, 0.0023291, Catch.RARE, 37000, false)),
		scute2(new Fish(Material.SCUTE, 0.0001553, Catch.EXOTIC, 550000, false)),
		scute3(new Fish(Material.SCUTE, 0.0000115, Catch.LEGENDARY, 8000000, false)),
		nautilusshell2(new Fish(Material.NAUTILUS_SHELL, 0.0000279, Catch.EXOTIC, 5000000, false)),
		nautilusshell3(new Fish(Material.NAUTILUS_SHELL, 0.0000016, Catch.LEGENDARY, 85000000, false)),
		turtleegg3(new Fish(Material.TURTLE_EGG, 0.0000005, Catch.LEGENDARY, 1000000000, false));
		
		private Fish fish;
		
		FISH(Fish fish) {
			this.fish = fish;
		}
		
	public Fish getFish() { return fish; }
	
	}
	
	
	

}

