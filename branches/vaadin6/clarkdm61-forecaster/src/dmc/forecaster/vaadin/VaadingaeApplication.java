package dmc.forecaster.vaadin;

/**
  	The Forecaster application is a financial assistance helper. It allows 
  	a user to create, edit, delete Financial Events that may be one-time and 
  	reoccurring Income or Expense events. These events can then be used to 
  	generate projections in either a ledger, or graphical format.
    Copyright (C) 2010-2012  David Clark

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
