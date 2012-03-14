package com.example.vaadingae;

import java.io.Serializable;

import com.vaadin.Application;
import com.vaadin.ui.Window;

import dmc.forecaster.client.ForecasterService;
import dmc.forecaster.server.ForecasterServiceImpl;

public class VaadingaeApplication extends Application implements Serializable {
	
	private static final long serialVersionUID = 3213035017493473755L;
	private static Window mainWindow;
	private static ForecasterService forecasterService;
	
	
	@Override
	public void init() {
		
		mainWindow = new Window("Vaadingae Application");
		mainWindow.setSizeFull();

		TabPanel tabs = new TabPanel();
		
		mainWindow.addComponent(tabs);
		setMainWindow(mainWindow);
		
		setTheme("forecaster");
	}
	
	public static ForecasterService getForecasterService() {
		if (forecasterService == null) {
			forecasterService = new ForecasterServiceImpl();
		}
		return forecasterService;
	}
	
	public static Window getMainWindowStatic() {
		return mainWindow;
	}
	
}
