package me.Penguin.SuperFishing.objects;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.Penguin.SuperFishing.utils.MiniItemBuilder;
import me.Penguin.SuperFishing.utils.u;

public class Fish {
	
	private Material type;
	private double chance;
	private Catch CatchType;
	private double price;
	
	public Fish(Material type, double chance, Catch Catch, double price) {
		this.type = type;
		this.chance = chance;
		this.CatchType = Catch;
		this.price = price;
	}
	
	public Material getType() { return type; }
	public double getChance() { return chance; }
	public Catch getCatchType() { return CatchType; }
	public double getPrice() { return price; }
	public ItemStack getItem() {
		return new MiniItemBuilder(type)
				.setName("&6&n" + u.capitaliseFirstLetters(type.toString().replaceAll("_", " ")))
				.addLores(
						"&c" + CatchType.toString() + " &7Catch", 
						"&7Value: &6$" + u.dc(price)						
						)
				.setLocname(type.toString().replaceAll("_", "").toLowerCase() + CatchType.getTier())
				.build();
	}
	
	public enum Catch {
		BASIC(0), 
		RARE(1), 
		EXOTIC(2), 
		MYSTICAL(3);
		
		int tier;
		
		Catch(int tier){
			this.tier = tier;
		}
		public int getTier() { return tier; }
}
	
	
	public enum FISH {		
		cod0(new Fish(Material.COD, 0.173362, Catch.BASIC, 250)),
		cod1(new Fish(Material.COD, 0.0312052, Catch.RARE, 3750)),
		cod2(new Fish(Material.COD, 0.005851, Catch.EXOTIC, 30000)),
		cod3(new Fish(Material.COD, 0.0014042, Catch.MYSTICAL, 150000)),
		salmon0(new Fish(Material.SALMON, 0.1560258, Catch.BASIC, 300)),
		salmon1(new Fish(Material.SALMON, 0.0228838, Catch.RARE, 4500)),
		salmon2(new Fish(Material.SALMON, 0.0046808, Catch.EXOTIC, 36000)),
		salmon3(new Fish(Material.SALMON, 0.0010922, Catch.MYSTICAL, 180000)),
		pufferfish0(new Fish(Material.PUFFERFISH, 0.0520086, Catch.BASIC, 1300)),
		pufferfish1(new Fish(Material.PUFFERFISH, 0.0083214, Catch.RARE, 19500)),
		pufferfish2(new Fish(Material.PUFFERFISH, 0.0014302, Catch.EXOTIC, 156000)),
		pufferfish3(new Fish(Material.PUFFERFISH, 0.0003901, Catch.MYSTICAL, 780000)),
		tropicalfish0(new Fish(Material.TROPICAL_FISH, 0.0390065, Catch.BASIC, 1800)),
		tropicalfish1(new Fish(Material.TROPICAL_FISH, 0.006241, Catch.RARE, 27000)),
		tropicalfish2(new Fish(Material.TROPICAL_FISH, 0.0011702, Catch.EXOTIC, 216000)),
		tropicalfish3(new Fish(Material.TROPICAL_FISH, 0.0002808, Catch.MYSTICAL, 1080000)),
		cookedcod0(new Fish(Material.COOKED_COD, 0.2371593, Catch.BASIC, 300)),
		cookedcod1(new Fish(Material.COOKED_COD, 0.0374462, Catch.RARE, 4500)),
		cookedcod2(new Fish(Material.COOKED_COD, 0.006241, Catch.EXOTIC, 36000)),
		cookedcod3(new Fish(Material.COOKED_COD, 0.0016851, Catch.MYSTICAL, 180000)),
		cookedsalmon0(new Fish(Material.COOKED_SALMON, 0.1625269, Catch.BASIC, 350)),
		cookedsalmon1(new Fish(Material.COOKED_SALMON, 0.0325054, Catch.RARE, 5250)),
		cookedsalmon2(new Fish(Material.COOKED_SALMON, 0.0048758, Catch.EXOTIC, 42000)),
		cookedsalmon3(new Fish(Material.COOKED_SALMON, 0.0012541, Catch.MYSTICAL, 210000)),
		prismarineshard1(new Fish(Material.PRISMARINE_SHARD, 0.0041607, Catch.RARE, 37500)),
		prismarineshard2(new Fish(Material.PRISMARINE_SHARD, 0.0008841, Catch.EXOTIC, 300000)),
		prismarineshard3(new Fish(Material.PRISMARINE_SHARD, 0.0002184, Catch.MYSTICAL, 1500000)),
		prismarinecrystals1(new Fish(Material.PRISMARINE_CRYSTALS, 0.0020803, Catch.RARE, 75000)),
		prismarinecrystals2(new Fish(Material.PRISMARINE_CRYSTALS, 0.0004421, Catch.EXOTIC, 600000)),
		prismarinecrystals3(new Fish(Material.PRISMARINE_CRYSTALS, 0.0001061, Catch.MYSTICAL, 3000000)),
		inksac1(new Fish(Material.INK_SAC, 0.0014978, Catch.RARE, 112500)),
		inksac2(new Fish(Material.INK_SAC, 0.0002925, Catch.EXOTIC, 900000)),
		inksac3(new Fish(Material.INK_SAC, 0.0000737, Catch.MYSTICAL, 4500000)),
		scute1(new Fish(Material.SCUTE, 0.0009362, Catch.RARE, 150000)),
		scute2(new Fish(Material.SCUTE, 0.0001989, Catch.EXOTIC, 1200000)),
		scute3(new Fish(Material.SCUTE, 0.0000468, Catch.MYSTICAL, 6000000)),
		nautilusshell1(new Fish(Material.NAUTILUS_SHELL, 0.000011, Catch.EXOTIC, 15000000)),
		nautilusshell2(new Fish(Material.NAUTILUS_SHELL, 0.0000023, Catch.MYSTICAL, 120000000)),
		turtleegg1(new Fish(Material.TURTLE_EGG, 0.0000015, Catch.MYSTICAL, 300000000));
		
		private Fish fish;
		
		FISH(Fish fish) {
			this.fish = fish;
		}
		
	public Fish getFish() { return fish; }
	
	}
	
	
	

}

