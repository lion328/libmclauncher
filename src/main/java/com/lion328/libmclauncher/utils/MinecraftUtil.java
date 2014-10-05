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
