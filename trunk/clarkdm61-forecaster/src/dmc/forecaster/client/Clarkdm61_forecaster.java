package dmc.forecaster.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.DateTimeFormat;
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
	public static final GreeterTab greeterTab = new GreeterTab();
	public static final ManageTab manageTab = new ManageTab();
	public static final LedgerTab ledgerTab = new LedgerTab();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		TabLayoutPanel tabs = new TabLayoutPanel(1.5, Unit.EM);
		tabs.add(manageTab, "Manage");
		tabs.add(ledgerTab, "Ledger");
		tabs.add(new HTML("Todo: add graph panel"), "Graph");
		tabs.add(greeterTab, "Greeter");
		
		RootPanel.get().add(tabs);
		
		tabs.setSize("40em", "40em");
		greeterTab.setSize("40em", "35em");
	}
	
 
	public static String dateFormat(Date d) {
		if (d == null) return "";
		return DateTimeFormat.getShortDateFormat().format(d);
	}
}
