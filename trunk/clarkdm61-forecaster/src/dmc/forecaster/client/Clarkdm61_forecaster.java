package dmc.forecaster.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Clarkdm61_forecaster implements EntryPoint {
	
	/**
	 * service proxy
	 */
	public static final ForecasterServiceAsync forecasterService = GWT.create(ForecasterService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		GreeterTab greeterTab = new GreeterTab();
		ManageTab manageTab = new ManageTab();
		
		TabLayoutPanel tabs = new TabLayoutPanel(1.5, Unit.EM);
		tabs.add(manageTab, "Manage");
		tabs.add(new HTML("Todo: add ledger panel"), "Ledger");
		tabs.add(new HTML("Todo: add graph panel"), "Graph");
		tabs.add(greeterTab, "Greeter");
		
		RootPanel.get().add(tabs);
		
		tabs.setSize("25em", "40em");
		greeterTab.setSize("25em", "35em");
	}
}
