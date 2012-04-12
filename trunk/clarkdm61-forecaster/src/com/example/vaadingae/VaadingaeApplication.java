package com.example.vaadingae;

import java.io.Serializable;
import java.util.List;

import com.vaadin.Application;
import com.vaadin.ui.Window;

import dmc.forecaster.client.ForecasterService;
import dmc.forecaster.server.ForecasterServiceImpl;
import dmc.forecaster.shared.FinancialEvent;

public class VaadingaeApplication extends Application implements Serializable {
	
	private static final long serialVersionUID = 3213035017493473755L;
	private static ForecasterService forecasterService;
	private static List<FinancialEvent> financialEventList = null;
	
	
	
	@Override
	public void init() {
		
		Window mainWindow = new Window("Vaadingae Application");
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

	public static List<FinancialEvent> getFinancialEventList() {
		return financialEventList;
	}

	public static void setFinancialEventList(List<FinancialEvent> financialEventList) {
		VaadingaeApplication.financialEventList = financialEventList;
	}
}
