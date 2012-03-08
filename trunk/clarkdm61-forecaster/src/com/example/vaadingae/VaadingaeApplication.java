package com.example.vaadingae;

import com.vaadin.Application;
import com.vaadin.ui.Window;

import dmc.forecaster.client.ForecasterService;
import dmc.forecaster.server.ForecasterServiceImpl;

public class VaadingaeApplication extends Application {
	
	private static ForecasterService forecasterService;
	
	
	@Override
	public void init() {
		setTheme("forecaster");
		Window mainWindow = new Window("Vaadingae Application");
		mainWindow.setSizeFull();

		TabPanel tabs = new TabPanel();
		
		mainWindow.addComponent(tabs);
		setMainWindow(mainWindow);
		
	}
	
	public static ForecasterService getForecasterService() {
		if (forecasterService == null) {
			forecasterService = new ForecasterServiceImpl();
		}
		return forecasterService;
	}
	
}
