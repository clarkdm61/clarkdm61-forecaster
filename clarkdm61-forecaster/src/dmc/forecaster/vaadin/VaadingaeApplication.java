package dmc.forecaster.vaadin;

import java.io.Serializable;

import com.vaadin.Application;
import com.vaadin.ui.Window;

public class VaadingaeApplication extends Application implements Serializable {
	
	private static final long serialVersionUID = 3213035017493473755L;
	
	@Override
	public void init() {
		
		Window mainWindow = new Window("Financial Forecaster (v3)");
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
	
}
