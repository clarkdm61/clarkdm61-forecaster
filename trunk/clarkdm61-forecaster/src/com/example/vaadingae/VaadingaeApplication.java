package com.example.vaadingae;

import com.vaadin.Application;
import com.vaadin.ui.*;

public class VaadingaeApplication extends Application {
	
	public static Window mainWindow;
	
	private static VaadingaeApplication instance = null;
	
	@Override
	public void init() {
		mainWindow = new Window("Vaadingae Application");
		Label l = new Label("test");
		mainWindow.setSizeFull();
		TabPanel tabs = new TabPanel();
		mainWindow.addComponent(tabs);
		setMainWindow(mainWindow);
		
		instance = this;
	}
	
	public void displayMainWindow() {
		setMainWindow(mainWindow);
	}
	
	public static VaadingaeApplication getInstance() {
		return instance;
	}

}
