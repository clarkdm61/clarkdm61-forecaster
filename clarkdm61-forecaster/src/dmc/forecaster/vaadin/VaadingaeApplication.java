package dmc.forecaster.vaadin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.Application;
import com.vaadin.ui.Window;

import dmc.forecaster.client.ForecasterService;
import dmc.forecaster.client.LedgerEntry;
import dmc.forecaster.server.ForecasterServiceImpl;
import dmc.forecaster.shared.FinancialEvent;

public class VaadingaeApplication extends Application implements Serializable {
	
	private static final long serialVersionUID = 3213035017493473755L;
	private static ForecasterService forecasterService;
	
	@Override
	public void init() {
		
		Window mainWindow = new Window("Vaadingae Application");
		mainWindow.setSizeFull();

		 // Create the application data instance
        AppData sessionData = new AppData(this);
        
        // Register it as a listener in the application context
        getContext().addTransactionListener(sessionData);

        
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
}
