package dmc.forecaster.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

import dmc.forecaster.shared.FinancialEvent;

public class LedgerTab extends DockLayoutPanel {
	private static final FlexTable ledgerGrid = new FlexTable();

	public LedgerTab() {
		super(Unit.EM); // needed for DockLayoutPanel
		
		HorizontalPanel topPanel = new HorizontalPanel();
		// start date
		topPanel.add(new Label("Start "));
		final TextBox txtStart = new TextBox();
		txtStart.setWidth("5em");
		txtStart.setText(DateTimeFormat.getShortDateFormat().format(new java.util.Date()));
		topPanel.add(txtStart);
		// end date
		topPanel.add(new Label("End "));
		final TextBox txtEnd = new TextBox();
		txtEnd.setWidth("5em");
		txtEnd.setText(DateTimeFormat.getShortDateFormat().format(new java.util.Date()));
		topPanel.add(txtEnd);
		// button
	    Button btnGo = new Button("Go", new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	createLedger(txtStart.getText(), txtEnd.getText());
	        }
	    });
	    topPanel.add(btnGo);
		
		// add to this 
		addNorth(topPanel, 2);
		
		add(ledgerGrid);
		
	}
	
	private void createLedger(String startDt, String endDt) {
		ArrayList<LedgerEntry> ledgerEntries = new ArrayList<LedgerEntry>();
		
		// for each FinancialEvent..
		// create instances within date range
		for (FinancialEvent event : Clarkdm61_forecaster.manageTab.getEventList()) {
			// TODO: this needs to be calculated
			Date date = event.getStartDt();
			LedgerEntry entry = new LedgerEntry(event.getName(), event.getType(), event.getAmount(), date);
			ledgerEntries.add(entry);
		}
		
		// sort by date
		Collections.sort(ledgerEntries);
		
		// update ui
		ledgerGrid.clear();
		int row = 0;
		for (LedgerEntry entry : ledgerEntries) {
			// TODO: calculate balance
			entry.setBalance(0d);
			ledgerGrid.setWidget(row, 0, new Label(entry.getDate().toString()));
			ledgerGrid.setWidget(row, 1, new Label(entry.getName()));
			ledgerGrid.setWidget(row, 2, new Label(entry.getIncomeAmount().toString()));
			ledgerGrid.setWidget(row, 3, new Label(entry.getExpenseAmount().toString()));
			ledgerGrid.setWidget(row, 4, new Label(entry.getBalance().toString()));
		}
		
	}


}
