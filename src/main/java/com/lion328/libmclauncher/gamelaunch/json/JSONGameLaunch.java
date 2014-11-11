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

package com.lion328.libmclauncher.gamelaunch.json;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lion328.libmclauncher.gamelaunch.IGameLaunch;
import com.lion328.libmclauncher.utils.FileUtil;
import com.lion328.libmclauncher.utils.MinecraftUtil;
import com.lion328.libmclauncher.utils.Util;

public class JSONGameLaunch implements IGameLaunch {

	private File basepath, versionDir, jsonFile;
	private String version, ram;
	private GameVersion gameVersion;
	private HashMap<String, String> customParameters = new HashMap<String, String>();
	
	public JSONGameLaunch(File basepath, String version, String ram) throws Exception {
		if(!MinecraftUtil.isVaildMinecraftDirectory(basepath)) throw new Exception("Invaild Minecraft's working directory.");
		this.basepath = basepath;
		this.version = version;
		this.ram = ram;
		versionDir = new File(basepath, "versions" + File.separator + version);
		jsonFile = new File(versionDir, version + ".json");
		if(!versionDir.isDirectory() || !jsonFile.isFile() || !new File(versionDir, version + ".jar").isFile()) throw new Exception("Invaild Minecraft's working directory.");
		GsonBuilder gb = new GsonBuilder();
		gb.registerTypeAdapter(Date.class, new DateType());
		Gson gson = gb.create();
		gameVersion = gson.fromJson(FileUtil.readFile(jsonFile), GameVersion.class);
		setArgument("version_name", version);
		setArgument("game_directory", basepath.getAbsolutePath());
		setArgument("user_properties", "{}");
		setArgument("user_type", gameVersion.getMainClass().equals("net.minecraft.launchwrapper.Launch") ? "legacy" : "mojang");
		setArgument("assets_root", basepath.getAbsolutePath() + File.separator + "assets" + File.separator + (gameVersion.getMainClass().equals("net.minecraft.launchwrapper.Launch") ? "virtual" + File.separator + "legacy" : ""));
		setArgument("game_assets", getArgument("assets_root"));
		if(gameVersion.getAssets() != null) setArgument("assets_index_name", gameVersion.getAssets());
	}
	
	@Override
	public void launch() throws Exception {
		cleanOldNatives();
		File nativesDir = new File(versionDir, version + "-natives-" + System.nanoTime());
		
		ArrayList<String> params = new ArrayList<String>();
		params.add(Util.getJavaRuntimePath());
		params.add("-Xmx" + ram);
		params.add("-Djava.library.path=" + nativesDir.getAbsolutePath());
		params.add("-cp");
		
		StringBuilder sb = new StringBuilder();
		sb.append(new File(versionDir, version + ".jar").getAbsolutePath());
		for(GameLibrary library : gameVersion.getLibraries()) {
			if(!library.isAllowed()) continue;
			if(library.isNative()) library.extractNatives(basepath, nativesDir);
			else {
				sb.append(File.pathSeparatorChar);
				sb.append(library.getFile(basepath).getAbsolutePath());
			}
		}
		params.add(sb.toString());
		params.add(gameVersion.getMainClass());
		
		for(String arg : gameVersion.getMinecraftArguments().split(" ")) {
			if(arg.startsWith("${")) if(customParameters.containsKey(arg)) arg = customParameters.get(arg);
			params.add(arg);
		}
		
		ProcessBuilder pb = new ProcessBuilder(params);
		pb.directory(basepath);
		pb.start();
	}

	public void cleanOldNatives() {
		versionDir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if(pathname.isDirectory() && pathname.getName().matches(version.replace(".", "\\.") + "-natives-\\d+")) FileUtil.delete(pathname);
				return false;
			}
		});
	}

	public void setArgument(String key, String value) {
		customParameters.put("${" + key + "}", value);
	}
	
	public String getArgument(String key) {
		return customParameters.containsKey("${" + key + "}") ? customParameters.get("${" + key + "}") : null;
	}
	
	@Override
	public void setUsername(String username) {
		setArgument("auth_player_name", username);
	}

	@Override
	public void setSessionID(String sessionid) {
		setArgument("auth_access_token", sessionid);
		setArgument("auth_session", sessionid);
	}
}
