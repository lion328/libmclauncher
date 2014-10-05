package com.lion328.libmclauncher.gamelaunch.json;

import com.google.gson.annotations.SerializedName;

public enum ReleaseType {

	@SerializedName("old_alpha")
	OLD_ALPHA("old_alpha"),
	@SerializedName("old_beta")
	OLD_BETA("old_beta"),
	@SerializedName("snapshot")
	SNAPSHOT("snapshot"),
	@SerializedName("release")
	RELEASE("release");
	
	private transient String s;
	
	ReleaseType(String s) {
		this.s = s;
	}
	
	public String toString() {
		return s;
	}
}
