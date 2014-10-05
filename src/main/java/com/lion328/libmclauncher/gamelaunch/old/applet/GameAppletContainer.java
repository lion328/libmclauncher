package com.lion328.libmclauncher.gamelaunch.old.applet;

import java.applet.Applet;
import java.applet.AppletStub;
import java.awt.BorderLayout;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class GameAppletContainer extends Applet implements AppletStub, Runnable {

	private static final long serialVersionUID = 50965766177777469L;
	
	private Applet applet = null;
	private boolean active = false;
	private HashMap<String, String> params = new HashMap<String, String>();
	
	public GameAppletContainer(Applet mcapplet) {
		this(mcapplet, null);
	}
	
	public GameAppletContainer(Applet mcapplet, HashMap<String, String> customParams) {
		this.applet = mcapplet;
		if(customParams != null) params.putAll(customParams);
	}
	
	@Override
	public boolean isActive() {
		return active;
	}
	
	public void launch() {
		applet.setStub(this);
		applet.setSize(getWidth(), getHeight());
		setLayout(new BorderLayout());
		add(applet, "Center");
		applet.init();
		active = true;
		applet.start();
		validate();
	}
	
	@Override
	public void init() {
		applet.init();
	}
	
	@Override
	public void start() {
		if(!active) {
			applet.start();
			active = true;
		}
	}
	
	@Override
	public void stop() {
		if(active) {
			applet.stop();
			active = false;
		}
	}
	
	@Override
	public void destroy() {
		applet.destroy();
	}
	
	@Override
	public void run() {}
	
	@Override
	public void appletResize(int width, int height) {}
	
	public void setParameter(String key, String value) {
		params.put(key, value);
	}
	
	@Override
	public String getParameter(String key) {
		if(params.containsKey(key)) return params.get(key);
		try {
			return super.getParameter(key);
		} catch(Exception e) {
			params.put(key, null);
			return null;
		}
	}
	
	@Override
	public URL getDocumentBase() {
		try {
			return new URL("http://www.minecraft.net/game/");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
