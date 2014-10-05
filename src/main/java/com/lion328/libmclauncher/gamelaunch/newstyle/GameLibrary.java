package com.lion328.libmclauncher.gamelaunch.newstyle;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.lion328.libmclauncher.utils.FileUtil;
import com.lion328.libmclauncher.utils.MinecraftUtil;
import com.lion328.libmclauncher.utils.OSUtil;
import com.lion328.libmclauncher.utils.ZipUtil;

public class GameLibrary {

	private String name;
	private Rule[] rules;
	private ExtractRule extractRule;
	private Natives natives;
	
	private transient String[] nameData;
	
	public GameLibrary(String name, Rule[] rules, ExtractRule extractrule, Natives natives) {
		this.name = name;
		this.rules = rules;
		extractRule = extractrule;
		
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
		File library = new File(basepath, "libraries" + File.separator + nameData[0].replace('.', File.separatorChar) + File.separator + nameData[1] + File.separator + nameData[2] + File.separator + nameData[1] + "-" + nameData[2] + (isNative() ? ("-" + natives.getCurrentOSNative() + ".jar") : ".jar"));
		if(!library.isFile()) return null;
		return library;
	}
	
	public boolean isAllowed() {
		if(rules == null) return true;
		boolean all = true;
		for(Rule rule : rules) {
			all &= rule.isAllowed();
			if(!all) break;
		}
		return all;
	}
	
	public boolean extractNatives(File basepath, File extractPath) {
		if(!isNative()) return false;
		if(!extractPath.isDirectory() && extractPath.isFile()) return false;
		if(!extractPath.isDirectory()) extractPath.mkdir();
		if(!MinecraftUtil.isVaildMinecraftDirectory(basepath)) return false;
		ZipUtil.extract(getFile(basepath), extractPath);
		for(String exclude : extractRule.getExclude()) FileUtil.delete(new File(extractPath, exclude));
		return true;
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
		return linux.replace("${arch}", System.getProperty("os.arch"));
	}

	public String getWindowsNative() {
		return windows.replace("${arch}", System.getProperty("os.arch"));
	}

	public String getOSXNative() {
		return osx.replace("${arch}", System.getProperty("os.arch"));
	}
	
	public String getCurrentOSNative() {
		switch(OSUtil.getCurrentOS()) {
		default:
		case UNKNOWN:
		case SOLARIS:
		case LINUX: return linux;
		case WINDOWS: return windows;
		case MAC: return osx;
		}
	}
	
}

enum Action { 
	@SerializedName("allow")
	ALLOW, 
	@SerializedName("disallow")
	DISALLOW
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
}

class Rule {
	
	private Action action;
	private RuleOS os;
	
	public Rule(Action action, RuleOS os) {
		this.action = action;
		this.os = os;
	}
	
	public boolean isAllowed() {
		boolean b = os == null ? true : OSUtil.getCurrentOS().equals(os);
		return action.equals(Action.ALLOW) ? b : !b;
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
}