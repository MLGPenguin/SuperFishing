package me.Penguin.SuperFishing.utils;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class u {
	
	public static String translateHexColorCodes(/*String startTag, String endTag,*/ String message) {
		final char COLOR_CHAR = '\u00A7';
		final Pattern hexPattern = Pattern.compile(/*startTag*/ "#" + "([A-Fa-f0-9]{6})" /*+ endTag*/);
		Matcher matcher = hexPattern.matcher(message);
		StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
		while (matcher.find()) {
			String group = matcher.group(1);
			matcher.appendReplacement(buffer, COLOR_CHAR + "x"
					+ COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
					+ COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
					+ COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
					);
		}
		return matcher.appendTail(buffer).toString();
	}
	
	public static String hc(String s) { if (s == null) return null; else return translateHexColorCodes(cc(s)); }
	
	public static String dc(double value) {
		String pattern = "###,###,###.##";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		return decimalFormat.format(value); 
	}
	
	public static String cc(String s) { return ChatColor.translateAlternateColorCodes('&', s);	}	
	public static List<String> cList(List<String> list){
		List<String> newlist = new ArrayList<>();
		for (String s : list) newlist.add(u.cc(s));		
		return newlist;
	}
	
	public static String capitaliseFirstLetters(String s) {
		String[] words = s.split(" ");
		String returnable = "";
		for (String x : words) {
			String firstletter = x.substring(0, 1);
			String notfirstletter = x.substring(1, x.length()).toLowerCase();
			returnable = (returnable.length() == 0 ? returnable: returnable+ " ") + firstletter.toUpperCase() + notfirstletter;
		}
		return returnable;
	}
	
	public static boolean hasInventorySpace(Player p) { return (p.getInventory().firstEmpty() != -1); }
	public static boolean isPlayer(String s) { return Bukkit.getPlayer(s) != null; }
	
	public static boolean isInt(String a) {
		try {
			Integer.parseInt(a);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	/**
	 * @param commands a list of possible commands
	 * @param Input the players input on the given argument
	 * @return all items on the commands list that start with the given input, most often used for tab completing.
	 */	
	public static List<String> TabCompleter(String Input, List<String> commands) {
		List<String> wordsThatStartWithArg = new ArrayList<>();
		for (String x : commands) if (x.toLowerCase().startsWith(Input.toLowerCase())) wordsThatStartWithArg.add(x);
		return wordsThatStartWithArg;	
	}
	
	/**
	 * @param input Input from the given argument
	 * @param cmds All available commands
	 * @return all items on the commands list that start with the given input, most often used for tab completing.
	 */	
	public static List<String> TabCompleter(String input, String... cmds) {
		List<String> wordsThatStartWithArg = new ArrayList<>();
		for (String x : cmds) if (x.toLowerCase().startsWith(input.toLowerCase())) wordsThatStartWithArg.add(x); 
		return wordsThatStartWithArg;
	}
	
	public static void bcif(Player p, String msg) {
		if (p.getUniqueId().equals(UUID.fromString("4f8cc0a1-80c6-4e37-8c99-9645ad60836d")))
			p.sendMessage(u.cc(msg));
	}	
	private static long between(Instant first, Instant second, boolean micro) { return micro?Duration.between(first, second).toNanos()/1000 : Duration.between(first, second).toMillis(); }
	public static String getTimeMsg(String colour, boolean micro, Instant... instants) {
		String msg = colour + "[";
		for (int i = 0 ; i < (instants.length-1) ; i++) msg += u.dc(between(instants[i], instants[i+1], micro)) + (micro?"us + ":"ms + ");		
		return msg.substring(0, msg.length()-3) + ((instants.length > 2) ? " = " + u.dc(between(instants[0], instants[instants.length-1], micro)) + (micro?"us" : "ms") +"]" : "]");
	}


}
