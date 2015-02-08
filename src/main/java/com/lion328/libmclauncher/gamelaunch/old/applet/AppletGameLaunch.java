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

package com.lion328.libmclauncher.gamelaunch.old.applet;

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

public class AppletGameLaunch implements IGameLaunch {

	private File bin;
	private HashMap<String, String> params = new HashMap<String, String>();
	
	public AppletGameLaunch(File basepath) throws Exception {
		if(!MinecraftUtil.isVaildOldMinecraftDirectory(basepath)) throw new Exception("Invaild Minecraft's working directory.");
		bin = new File(basepath, "bin");
		setStandAlone(true);
	}
	
	@Override
	public void launch() throws Exception {
		if(Runtime.getRuntime().totalMemory() / 1024 / 1024 < 512) throw new Exception("Memory not enough!");
		URL[] jars = new URL[MinecraftUtil.OLD_MINECRAFT_JARS.length];
		for(int i = 0; i < jars.length; i++) jars[i] = new File(bin, MinecraftUtil.OLD_MINECRAFT_JARS[i]).toURI().toURL();
		final URLClassLoader classLoader = new URLClassLoader(jars);
		Applet applet = (Applet)classLoader.loadClass("net.minecraft.client.MinecraftApplet").newInstance();
		
		String nativesPath = new File(bin, "natives").getAbsolutePath();
		System.setProperty("org.lwjgl.librarypath", nativesPath);
		System.setProperty("net.java.games.input.librarypath", nativesPath);
		
		final GameAppletContainer gapp = new GameAppletContainer(applet, params, new Runnable() {
			public void run() {
				try {
					classLoader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		gapp.setPreferredSize(new Dimension(854, 480));
		gapp.setBackground(Color.BLACK);
		
		JFrame frame = new JFrame();
		frame.setSize(854, 480);
		frame.setIconImage(ImageIO.read(this.getClass().getResourceAsStream("/com/lion328/libmclauncher/resources/minecraft_icon.png")));
		frame.setTitle("Minecraft");
		frame.setLayout(new BorderLayout());
		frame.add(gapp, "Center");
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		gapp.launch();
	}

	public String getParameter(String key) {
		return params.containsKey(key) ? params.get(key) : null;
	}
	
	public void setParameter(String key, String value) {
		params.put(key, value);
	}
	
	@Override
	public void setUsername(String username) {
		params.put("username", username);
		if(getParameter("sessionid") == null) setSessionID("123456"); // if not set session id, username will not work.
	}
	
	@Override
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