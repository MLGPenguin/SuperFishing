package me.Penguin.SuperFishing.utils;

public enum perm {

	givecrate("superfishing.givecrate");
	
	private String node;
	
	perm(String permNode){
		node = permNode;
	}
	
	public String get() { return node; } 
	
	
}
