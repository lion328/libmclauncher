package com.lion328.libmclauncher.gamelaunch.json;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.lion328.libmclauncher.utils.FileUtil;
import com.lion328.libmclauncher.utils.MinecraftUtil;
import com.lion328.libmclauncher.utils.OSUtil;
import com.lion328.libmclauncher.utils.Util;
import com.lion328.libmclauncher.utils.ZipUtil;

public class GameLibrary {

	private String name;
	private Rule[] rules;
	private ExtractRule extract;
	private Natives natives;
	
	private transient String[] nameData;
	
	public GameLibrary(String name, Rule[] rules, ExtractRule extractrule, Natives natives) {
		this.name = name;
		this.rules = rules;
		extract = extractrule;
		
		splitName();
	}
	
	private void splitName() {
		if(nameData == null){
			nameData = name.split(":");
			if(nameData.length < 3) throw new RuntimeException("Library name is invaild.");
		}
	}
	
	public boolean isNative() {
		return natives != null;
	}
	
	public String getPackage() {
		splitName();
		return nameData[0];
	}
	
	public String getName() {
		splitName();
		return nameData[1];
	}
	
	public String getVersion() {
		splitName();
		return nameData[2];
	}
	
	public File getFile(File basepath) {
		if(!MinecraftUtil.isVaildMinecraftDirectory(basepath)) return null;
		splitName();
		File library = new File(basepath, "libraries" + File.separator + nameData[0].replace('.', File.separatorChar) + File.separator + nameData[1] + File.separator + nameData[2] + File.separator + nameData[1] + "-" + nameData[2] + (isNative() ? ("-" + natives.getCurrentOSNative() + ".jar") : ".jar"));
		if(!library.isFile()) return null;
		return library;
	}
	
	public boolean isAllowed() {
		if(rules == null) return true;
		boolean all = true;
		for(Rule rule : rules) {
			all &= rule.isAllowed();
			if(!all) return false;
		}
		return true;
	}
	
	public boolean extractNatives(File basepath, File extractPath) {
		if(!isNative()) return false;
		if(extractPath.isFile()) return false;
		if(!MinecraftUtil.isVaildMinecraftDirectory(basepath)) return false;
		ZipUtil.extract(getFile(basepath), extractPath);
		if(extract != null) for(String exclude : extract.getExclude()) FileUtil.delete(new File(extractPath, exclude));
		return true;
	}
	
	public String toString() {
		return "GameLibrary[name=\"" + name + "\", rules=" + (rules == null ? "null" : Arrays.asList(rules).toString()) + ", extractRule=" + (extract == null ? "null" : extract) +
				", natives=" + (isNative() ? "null" : natives) + "]";
	}
}

class Natives {
	
	private String linux, windows, osx;
	
	public Natives(String linux, String windows, String osx) {
		this.linux = linux;
		this.windows = windows;
		this.osx = osx;
	}

	public String getLinuxNative() {
		return linux.replace("${arch}", Util.getSystemArchitecture());
	}

	public String getWindowsNative() {
		return windows.replace("${arch}", Util.getSystemArchitecture());
	}

	public String getOSXNative() {
		return osx.replace("${arch}", Util.getSystemArchitecture());
	}
	
	public String getCurrentOSNative() {
		switch(OSUtil.getCurrentOS()) {
		default:
		case UNKNOWN:
		case SOLARIS:
		case LINUX: return getLinuxNative();
		case WINDOWS: return getWindowsNative();
		case MAC: return getOSXNative();
		}
	}
	
	public String toString() {
		return "Natives[linux=\"" + linux + "\", windows=\"" + windows + "\", osx=\"" + osx + "\"]";
	}
}

enum Action { 
	@SerializedName("allow")
	ALLOW("allow"), 
	@SerializedName("disallow")
	DISALLOW("disallow");
	
	private transient String s;
	
	Action(String s) {
		this.s = s;
	}
	
	public String toString() {
		return s;
	}
}

class RuleOS {
	
	private String name;
	private transient OSUtil.OS os;
	
	public RuleOS(OSUtil.OS os) {
		name = OSUtil.getOSAsString(os);
	}
	
	public OSUtil.OS getOS() {
		if(os == null) os = OSUtil.getOSFromString(name);
		return os;
	}
	
	public String toString() {
		return "RuleOS[name=\"" + name + "\"]";
	}
}

class Rule {
	
	private Action action;
	private RuleOS os;
	
	public Rule(Action action, RuleOS os) {
		this.action = action;
		this.os = os;
	}
	
	public boolean isAllowed() {
		boolean b = os == null ? true : OSUtil.getCurrentOS().equals(os.getOS());
		return action.equals(Action.ALLOW) ? b : !b;
	}
	
	public String toString() {
		return "Rule[action=\"" + action.toString() + "\", os=" + (os == null ? "null" : os.toString()) + "]";
	}
}

class ExtractRule {
	
	private List<String> exclude;
	
	public ExtractRule(String[] exclude) {
		this.exclude = Arrays.asList(exclude);
	}
	
	public boolean isExclude(String s) {
		return exclude.contains(s);
	}
	
	public String[] getExclude() {
		return exclude.toArray(new String[exclude.size()]);
	}
	
	public String toString() {
		return "ExtractRule[exclude=" + exclude.toString() + "]";
	}
}