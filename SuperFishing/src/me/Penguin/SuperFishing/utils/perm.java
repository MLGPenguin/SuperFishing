package me.Penguin.SuperFishing.utils;

public enum perm {

	admin("superfishing.admin");
	
	private String node;
	
	perm(String permNode){
		node = permNode;
	}
	
	public String get() { return node; } 
	
	
}
