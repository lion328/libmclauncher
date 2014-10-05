package com.lion328.libmclauncher.gamelaunch.newstyle;

import com.google.gson.annotations.SerializedName;

public enum ReleaseType {

	@SerializedName("old_alpha")
	OLD_ALPHA, 
	@SerializedName("old_beta")
	OLD_BETA, 
	@SerializedName("snapshot")
	SNAPSHOT, 
	@SerializedName("release")
	RELEASE;
}
