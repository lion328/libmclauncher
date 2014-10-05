package com.lion328.libmclauncher.utils;

import java.io.File;

public class Util {

	private static File appdata = null;
	private static String java_path = null;
	
	public static File getAppdata() {
		if(appdata == null) {
			File home = new File(System.getProperty("user.home", "."));
			switch(OSUtil.getCurrentOS()) {
			default:
			case UNKNOWN:
			case SOLARIS:
			case LINUX:
				appdata = home;
				break;
			case WINDOWS:
				String winAppdata = System.getenv("APPDATA");
				appdata = winAppdata == null ? home : new File(winAppdata);
				break;
			case MAC:
				appdata = new File(home, "Library/Application Support/");
				break;
			}
		}
		return appdata;
	}
	
	public static String getJavaRuntimePath() {
		if(java_path == null) java_path = (System.getProperty("java.home") + "/bin/java").replace("/", File.separator);
		return java_path;
	}
	
	public static String getSystemArchitecture() {
		return System.getProperty("os.arch").contains("64") ? "64" : "32";
	}
}
