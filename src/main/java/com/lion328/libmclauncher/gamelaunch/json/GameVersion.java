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
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lion328.libmclauncher.utils.FileUtil;

public class GameVersion {
	
	public static final Gson GSON_PARSER;
	private String inheritsFrom, id, minecraftArguments, mainClass, assets;
	private Date time, releaseTime;
	private ReleaseType type;
	private GameLibrary[] libraries;
	private int minimumLauncherVersion;
	private volatile GameVersion parent;
	
	public GameVersion(GameVersion o) {
		inheritsFrom = o.inheritsFrom;
		id = o.id;
		minecraftArguments = o.minecraftArguments;
		mainClass = o.mainClass;
		assets = o.assets;
		time = o.time;
		releaseTime = o.releaseTime;
		type = o.type;
		libraries = o.libraries.clone();
		minimumLauncherVersion = o.minimumLauncherVersion;
	}
	
	public GameVersion(File versionsFolder, String currentVersion) throws IOException {
		this(GSON_PARSER.fromJson(FileUtil.readFile(new File(versionsFolder, currentVersion + File.separator + currentVersion + ".json")), GameVersion.class));
		if(inheritsFrom != null) parent = new GameVersion(versionsFolder, inheritsFrom);
	}
	
	public GameVersion(String parentVersion, String id, String mcargs, String mainclass, String assets, Date time, Date rtime, ReleaseType type, GameLibrary[] libs, int minver) {
		inheritsFrom = parentVersion;
		this.id = id;
		minecraftArguments = mcargs;
		mainClass = mainclass;
		this.assets = assets;
		this.time = time;
		releaseTime = rtime;
		this.type = type;
		libraries = libs;
		minimumLauncherVersion = minver;
	}

	public String getParentVersion() {
		return inheritsFrom;
	}
	
	public String getID() {
		return id;
	}

	public String getMinecraftArguments() {
		return minecraftArguments;
	}

	public String getMainClass() {
		return mainClass;
	}

	public String getAssets() {
		return assets;
	}

	public Date getTime() {
		return time;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public ReleaseType getType() {
		return type;
	}

	public GameLibrary[] getLibraries() {
		return libraries;
	}
	
	public GameLibrary[] getAllLibraries() {
		if(parent != null) {
			GameLibrary[] parents = parent.getAllLibraries();
			GameLibrary[] tmp = new GameLibrary[libraries.length + parents.length];
			System.arraycopy(parents, 0, tmp, 0, parents.length);
			System.arraycopy(libraries, 0, tmp, parents.length, libraries.length);
			return tmp;
		}
		return libraries;
	}

	public int getMinimumLauncherVersion() {
		return minimumLauncherVersion;
	}
	
	public GameVersion getParentGameVersion() {
		return parent;
	}
	
	public String toString() {
		return "GameVersion[inheritsFrom=\"" + inheritsFrom + "\", id=\"" + id + "\", minecraftArguments=\"" + minecraftArguments + "\", mainClass=\"" + mainClass + 
				"\", assets=" + assets.toString() + ", time=" + time.toString() + ", releaseTime=" + releaseTime.toString() +
				", type=\"" + type.toString() + "\", libraries=" + Arrays.asList(libraries).toString() + ", minimumLauncherVersion=" + minimumLauncherVersion +
				"]";
	}
	
	static {
		GsonBuilder gb = new GsonBuilder();
		gb.registerTypeAdapter(Date.class, new DateType());
		GSON_PARSER = gb.create();
	}
}
