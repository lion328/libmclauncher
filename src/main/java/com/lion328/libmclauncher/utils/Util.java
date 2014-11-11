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
