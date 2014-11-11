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
