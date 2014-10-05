package com.lion328.libmclauncher.gamelaunch.json;

import java.util.Arrays;
import java.util.Date;

public class GameVersion {
	
	private String id, minecraftArguments, mainClass, assets;
	private Date time, releaseTime;
	private ReleaseType type;
	private GameLibrary[] libraries;
	private int minimumLauncherVersion;
	
	public GameVersion(String id, String mcargs, String mainclass, String assets, Date time, Date rtime, ReleaseType type, GameLibrary[] libs, int minver) {
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

	public int getMinimumLauncherVersion() {
		return minimumLauncherVersion;
	}
	
	public String toString() {
		return "GameVersion[id=\"" + id + "\", minecraftArguments=\"" + minecraftArguments + "\", mainClass=\"" + mainClass + 
				"\", assets=" + assets.toString() + ", time=" + time.toString() + ", releaseTime=" + releaseTime.toString() +
				", type=\"" + type.toString() + "\", libraries=" + Arrays.asList(libraries).toString() + ", minimumLauncherVersion=" + minimumLauncherVersion +
				"]";
	}
}
