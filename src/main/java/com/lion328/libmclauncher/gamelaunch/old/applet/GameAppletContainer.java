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
	private Runnable destroyEvent;
	
	public GameAppletContainer(Applet mcapplet) {
		this(mcapplet, null, null);
	}
	
	public GameAppletContainer(Applet mcapplet, HashMap<String, String> customParams, Runnable destroyEvent) {
		this.applet = mcapplet;
		if(customParams != null) params.putAll(customParams);
		this.destroyEvent = destroyEvent;
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
		destroyEvent.run();
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
