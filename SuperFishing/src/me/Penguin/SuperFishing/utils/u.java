package me.Penguin.SuperFishing.utils;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
