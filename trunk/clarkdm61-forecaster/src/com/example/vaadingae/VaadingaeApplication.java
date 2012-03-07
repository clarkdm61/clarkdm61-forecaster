package com.example.vaadingae;

import com.vaadin.Application;
import com.vaadin.ui.Window;

public class VaadingaeApplication extends Application {
	
	
	@Override
	public void init() {
		Window mainWindow = new Window("Vaadingae Application");
		mainWindow.setSizeFull();

		TabPanel tabs = new TabPanel();
		
		mainWindow.addComponent(tabs);
		setMainWindow(mainWindow);
		
	}
	
}
