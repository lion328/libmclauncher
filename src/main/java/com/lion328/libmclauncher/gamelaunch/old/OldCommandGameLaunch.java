package com.lion328.libmclauncher.gamelaunch.old;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.lion328.libmclauncher.gamelaunch.IGameLaunch;
import com.lion328.libmclauncher.utils.MinecraftUtil;
import com.lion328.libmclauncher.utils.Util;

public class OldCommandGameLaunch implements IGameLaunch {

	private File bin;
	private String username, sessionID, ram;

	public OldCommandGameLaunch(File basepath, String ram) throws Exception {
		if(!MinecraftUtil.isVaildOldMinecraftDirectory(basepath)) throw new Exception("Invaild Minecraft's working directory.");
		this.bin = new File(basepath, "bin");
		this.ram = ram;
	}
	
	@Override
	public void launch() throws Exception {
		List<String> params = new ArrayList<String>();
		params.add(Util.getJavaRuntimePath());
		params.add("-cp");
		
		StringBuilder cpFiles = new StringBuilder();
		for(String jar : MinecraftUtil.OLD_MINECRAFT_JARS) {
			cpFiles.append(jar);
			cpFiles.append(File.pathSeparatorChar);
		}
		params.add(cpFiles.toString());
		
		params.add("-Xmx" + ram);
		params.add("-Djava.library.path=natives");
		params.add("-Dfile.encoding=UTF-8");
		params.add("-Dsun.java2d.noddraw=true");
		params.add("-Dsun.java2d.d3d=false");
		params.add("-Dsun.java2d.opengl=false");
		params.add("-Dsun.java2d.pmoffscreen=false");
		params.add("net.minecraft.client.Minecraft");
		params.add(username);
		if(sessionID != null) params.add(sessionID);
		
		ProcessBuilder pb = new ProcessBuilder(params);
		pb.directory(bin);
		pb.start();
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void setSessionID(String sessionid) {
		sessionID = sessionid;
	}
	
}
