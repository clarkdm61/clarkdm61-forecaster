package dmc.forecaster.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Clarkdm61_forecaster implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		TabLayoutPanel tabs = new TabLayoutPanel(1.5, Unit.EM);
		tabs.add(new GreeterTab());
		tabs.add(new HTML("Todo: add manager panel"), "Manage");
		tabs.add(new HTML("Todo: add ledger panel"), "Leger");
		tabs.add(new HTML("Todo: add graph panel"), "Graph");
		RootPanel.get().add(tabs);
	}
}
