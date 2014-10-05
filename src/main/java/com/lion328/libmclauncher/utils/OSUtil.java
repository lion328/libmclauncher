package com.lion328.libmclauncher.utils;

public final class OSUtil {

	public static enum OS { WINDOWS, MAC, LINUX, SOLARIS, UNKNOWN };
	private static OS currentOS = null;
	private static String currentOSstr = null;
	
	public static OS getCurrentOS() {
		if(currentOS == null) {
			String osName = System.getProperty("os.name").toLowerCase();
			if(osName.indexOf("win") > -1) currentOS = OS.WINDOWS;
			else if(osName.contains("mac")) currentOS = OS.MAC;
			else if(osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) currentOS = OS.LINUX;
			else if(osName.contains("sunos")) currentOS = OS.SOLARIS;
			else currentOS = OS.UNKNOWN;
		}
		return currentOS;
	}
	
	public static String getOSAsString(OS os) {
		switch(os) {
		default:
		case UNKNOWN: return "unknown";
		case SOLARIS: return "sunos";
		case LINUX: return "linux";
		case WINDOWS: return "windows";
		case MAC: return "osx";
		}
	}
	
	public static OS getOSFromString(String s) {
		if(s.equalsIgnoreCase("windows")) return OS.WINDOWS;
		else if(s.equalsIgnoreCase("osx")) return OS.MAC;
		else if(s.equalsIgnoreCase("linux")) return OS.LINUX;
		else if(s.equalsIgnoreCase("sunos") || s.equalsIgnoreCase("solaris")) return OS.SOLARIS;
		else return OS.UNKNOWN;
	}
	
	public static String getCurrentOSAsString() {
		if(currentOSstr == null) currentOSstr = getOSAsString(getCurrentOS());
		return currentOSstr;
	}
}
