/**
 * Copyright (c) 2014 Waritnan Sookbuntherng
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
