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

public class MinecraftUtil {

	public static final String[] OLD_MINECRAFT_JARS = new String[] {"lwjgl.jar", "lwjgl_util.jar", "jinput.jar", "minecraft.jar"};
	
	private static final String[] MINECRAFT_FOLDERS = new String[] {"libraries", "versions"};
	
	public static boolean isVaildOldMinecraftDirectory(File basepath) {
		File bin = new File(basepath, "bin");
		if(bin.isDirectory() && new File(bin, "natives").isDirectory()) {
			for(String filename : OLD_MINECRAFT_JARS) if(!new File(bin, filename).isFile()) return false;
			return true;
		}
		return false;
	}
	
	public static boolean isVaildMinecraftDirectory(File basepath) {
		for(String folderName : MINECRAFT_FOLDERS) if(!new File(basepath, folderName).isDirectory()) return false;
		return true;
	}
	
	public static File getMinecraftWorkingDirectory() {
		if(OSUtil.getCurrentOS().equals(OSUtil.OS.UNKNOWN)) return new File(Util.getAppdata(), "minecraft");
		return new File(Util.getAppdata(), ".minecraft");
	}
}
