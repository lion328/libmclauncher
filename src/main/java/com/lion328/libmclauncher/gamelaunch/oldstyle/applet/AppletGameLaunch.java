package com.lion328.libmclauncher.gamelaunch.oldstyle.applet;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.lion328.libmclauncher.gamelaunch.IGameLaunch;
import com.lion328.libmclauncher.utils.MinecraftUtil;

public final class AppletGameLaunch implements IGameLaunch {

	private File bin;
	private HashMap<String, String> params = new HashMap<String, String>();
	
	public AppletGameLaunch(File basepath) throws Exception {
		if(!MinecraftUtil.isVaildMinecraftDirectory(basepath)) throw new Exception("Invaild Minecraft's working directory.");
		bin = new File(basepath, "bin");
		setStandAlone(true);
	}
	
	@Override
	public void launch() throws Exception {
		URL[] jars = new URL[MinecraftUtil.MINECRAFT_JARS.length];
		for(int i = 0; i < jars.length; i++) jars[i] = new File(bin, MinecraftUtil.MINECRAFT_JARS[i]).toURI().toURL();
		final URLClassLoader classLoader = new URLClassLoader(jars);
		Applet applet = (Applet)classLoader.loadClass("net.minecraft.client.MinecraftApplet").newInstance();
		
		String nativesPath = new File(bin, "natives").getAbsolutePath();
		System.setProperty("org.lwjgl.librarypath", nativesPath);
	    System.setProperty("net.java.games.input.librarypath", nativesPath);
	    
	    final GameApplet gapp = new GameApplet(applet, params);
	    gapp.setPreferredSize(new Dimension(854, 480));
	    gapp.setBackground(Color.BLACK);
	    
	    JFrame frame = new JFrame();
	    frame.setSize(854, 480);
	    frame.setIconImage(ImageIO.read(this.getClass().getResourceAsStream("/minecraft_icon.png")));
	    frame.setTitle("Minecraft");
	    frame.setLayout(new BorderLayout());
	    frame.add(gapp, "Center");
	    frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	    
	    gapp.launch();
	    
	    new Thread() {
	    	public void run() {
	    		while(gapp.isActive()) {
	    			try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	    		}
	    		try {
					classLoader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
	    }.start();
	}

	public String getParameter(String key) {
		return params.containsKey(key) ? params.get(key) : null;
	}
	
	public void setParameter(String key, String value) {
		params.put(key, value);
	}
	
	public void setUsername(String username) {
		params.put("username", username);
		if(getParameter("sessionid") == null) setSessionID("123456");
	}
	
	public void setSessionID(String sessionid) {
		params.put("sessionid", sessionid);
	}
	
	public void setStandAlone(boolean bool) {
		params.put("stand-alone", String.valueOf(bool));
	}
	
	public void setServer(String host, int port) {
		params.put("server", host);
		params.put("port", String.valueOf(port));
	}
	
	public void setDemo(boolean bool) {
		params.put("demo", String.valueOf(bool));
	}
	
	public void setFullscreen(boolean bool) {
		params.put("fullscreen", String.valueOf(bool));
	}
}