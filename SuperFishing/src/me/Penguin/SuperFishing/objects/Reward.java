package me.Penguin.SuperFishing.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import penguin.supertags.objects.tag;

public class Reward {
	
	private rewardtype rewardType;
	private ItemStack itemreward;
	private String commandreward;
	private tag tagreward;
	private double chance;

	
	//ItemStack
	public Reward(ItemStack item, double chance) {
		this.rewardType = rewardtype.ITEM;
		itemreward = item;
		this.chance = chance;
	}
	/**
	 * Runs a command through the console when won
	 * @param command command to be run, do not include the '/'
	 */
	public Reward(String command, double chance) {
		this.rewardType = rewardtype.COMMAND;
		commandreward = command;
		this.chance = chance;
	}
	
	public Reward(tag tag, double chance) {
		this.rewardType = rewardtype.TAG;
		tagreward = tag;
		this.chance = chance;
	}
	
	
	public double getChance() { return chance; }
	public rewardtype getRewardType() { return rewardType; }
	/**
	 * Gives a reward to player.
	 * Check the players inventory is empty first.
	 * (%p% = players name).
	 * @param p player to give reward to.
	 */
	public void giveReward(Player p) {
		switch (rewardType) {
		case ITEM:
			p.getInventory().addItem(itemreward);
		case COMMAND:
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandreward);
		case TAG:
			p.getInventory().addItem(tagreward.getItem());
		}
	}
	
	/**
	 * get x amountOfRewards from the list of rewards given, duplicates can occur, chances do not have to equal 100.
	 * @param rewards all possible rewards
	 * @param amountOfRewards how many rewards should be returned from this list (Duplicates are possible)
	 * @return a list of amountOfRewards rewards from the rewards list
	 */
	public static List<Reward> getRewards(List<Reward> rewards, int amountOfRewards) {
		double totalchance = 0;
		HashMap<Reward, Double> chancemap = new HashMap<>();
		for (Reward reward : rewards) totalchance += reward.getChance();
		for (Reward reward : rewards) chancemap.put(reward, (reward.getChance() / totalchance));
		List<Reward> list = new ArrayList<>();
		Random r = new Random();		
		for (int i = 0 ; i < amountOfRewards ; i++) {
			double rand = r.nextDouble();
			for (Reward reward : chancemap.keySet()) {
				rand -= chancemap.get(reward);
				if (rand <= 0) {
					list.add(reward);
					break;
				}
			}			
		}
		return list;
	}
	
	
	
	public enum rewardtype {
		ITEM,
		COMMAND,
		TAG;
		
		rewardtype(){
			
		}
	}
	
}



