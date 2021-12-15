package me.Penguin.SuperFishing.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;

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

}
