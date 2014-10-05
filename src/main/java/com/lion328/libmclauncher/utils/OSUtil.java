package com.lion328.libmclauncher.utils;

public final class OSUtil {

	public static enum OS { WINDOWS, MAC, LINUX, SOLARIS, UNKNOWN };
	private static OS currentOS = null;
	
	public static OS getCurrentOS() {
		if(currentOS == null) {
			String osName = System.getProperty("os.name").toLowerCase();
			if(osName.indexOf("win") > -1) currentOS = OS.WINDOWS;
			else if(osName.indexOf("mac") > -1) currentOS = OS.MAC;
			else if(osName.indexOf("nix") > -1 || osName.indexOf("nux") > -1 || osName.indexOf("aix") > 0) currentOS = OS.LINUX;
			else if(osName.indexOf("sunos") > -1) currentOS = OS.SOLARIS;
			else currentOS = OS.UNKNOWN;
		}
		return currentOS;
	}
	
}
