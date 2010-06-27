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
	private final TextBox txtStart = new TextBox();
	private final TextBox txtEnd = new TextBox();
	
	public LedgerTab() {
		super(Unit.EM); // needed for DockLayoutPanel
		
		HorizontalPanel topPanel = new HorizontalPanel();
		// start date
		topPanel.add(new Label("Start "));
		txtStart.setWidth("5em");
		txtStart.setText(DateTimeFormat.getShortDateFormat().format(new java.util.Date()));
		topPanel.add(txtStart);
		// end date
		topPanel.add(new Label("End "));
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
			// TODO: LedgerEntry.date needs to be calculated
			// is this reoccurring?
			if (true) {
				// create instances in range
			} else {
				// break loop if the startDt not in range?
			}
			Date date = event.getStartDt();
			LedgerEntry entry = new LedgerEntry(event.getName(), event.getType(), event.getAmount(), date);
			ledgerEntries.add(entry);
		}
		
		// sort entries by start date 
		Collections.sort(ledgerEntries);
		
		// update ui
		ledgerGrid.clear();
		int row = 0;
		LedgerEntry lastEntry = null;
		for (LedgerEntry entry : ledgerEntries) {
			// capture and set initial balance here (instead of 0d)
			Double balance = lastEntry == null ? 0d : lastEntry.getBalance(); 
			balance += entry.getIncomeAmount();
			balance -= entry.getExpenseAmount();
			entry.setBalance(balance);
			
			ledgerGrid.setWidget(row, 0, new Label(DateTimeFormat.getShortDateFormat().format(entry.getDate()))); // TODO: create a common date format
			ledgerGrid.setWidget(row, 1, new Label(entry.getName()));
			ledgerGrid.setWidget(row, 2, new Label(entry.getIncomeAmount().toString()));
			ledgerGrid.setWidget(row, 3, new Label(entry.getExpenseAmount().toString()));
			ledgerGrid.setWidget(row, 4, new Label(entry.getBalance().toString()));

			row ++;
			lastEntry = entry;
		}
		
	}
	
	private Date getStartDt() {
		return new Date(txtStart.getText());
	}
	private Date getEndDt() {
		return new Date(txtEnd.getText());
	}

}
