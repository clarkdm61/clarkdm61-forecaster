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
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;

import dmc.forecaster.shared.FinancialEvent;
import dmc.forecaster.shared.Reoccurrence;

public class LedgerTab extends DockLayoutPanel {
	private static final FlexTable ledgerGrid = new FlexTable();
	private static ArrayList<LedgerEntry> ledgerEntries = null;
	private static final TextBox txtStart = new TextBox();
	private static final TextBox txtEnd = new TextBox();
	
	public LedgerTab() {
		super(Unit.EM); // needed for DockLayoutPanel
		
		Grid topPanel = new Grid(1,5);
		// start date
		topPanel.setWidget(0, 0, new Label("Start "));
		txtStart.setWidth("5em");
		Date todayDt = new Date();
		Date nextDt = new Date( todayDt.getTime()+Reoccurrence.WEEK*12 );
		txtStart.setText(DateTimeFormat.getShortDateFormat().format(todayDt));
		topPanel.setWidget(0, 1, txtStart);
		// end date
		topPanel.setWidget(0, 2, new Label("End "));
		txtEnd.setWidth("5em");
		txtEnd.setText(DateTimeFormat.getShortDateFormat().format(nextDt));
		topPanel.setWidget(0, 3, txtEnd);
		// button
	    Button btnGo = new Button("Go", new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	createLedger(txtStart.getText(), txtEnd.getText());
	        }
	    });
	    topPanel.setWidget(0, 4, btnGo);
		
		// add to this 
		addNorth(topPanel, 2);
		
		ledgerGrid.setStyleName("ledger");
		ScrollPanel scrollPanel = new ScrollPanel(ledgerGrid);
		add(scrollPanel);
		
	}
	
	private void createLedger(String startDt, String endDt) {
		ledgerEntries = new ArrayList<LedgerEntry>();
		
		// for each FinancialEvent..
		// create instances within date range
		for (FinancialEvent event : Clarkdm61_forecaster.manageTab.getEventList()) {
			// handle reoccurring events differently
			if (!event.getReoccurrence().equals(Reoccurrence.None)) {
				createLedgerEntries(ledgerEntries, event); 
			} else {
				createLedgerEntry(ledgerEntries, event);
			}
		}
		
		// sort entries by start date 
		Collections.sort(ledgerEntries);
		
		// update ui
		ledgerGrid.removeAllRows();
		int row = 0;
		createLedgerHeaderCell(row, 0, "Date");
		createLedgerHeaderCell(row, 1, "Event");
		createLedgerHeaderCell(row, 2, "Income");
		createLedgerHeaderCell(row, 3, "Expense");
		createLedgerHeaderCell(row, 4, "Balance");
		
		row++;
		LedgerEntry lastEntry = null;
		for (LedgerEntry entry : ledgerEntries) {
			// capture and set initial balance here (instead of 0d)
			Double balance = lastEntry == null ? 0d : lastEntry.getBalance(); 
			balance += entry.getIncomeAmount();
			balance -= entry.getExpenseAmount();
			entry.setBalance(balance);
			
			createLedgerBodyCell(row, 0, DateTimeFormat.getShortDateFormat().format(entry.getDate())); // TODO: create a common date format
			createLedgerBodyCell(row, 1, entry.getName());
			createLedgerBodyCell(row, 2, entry.getIncomeAmount().toString());
			createLedgerBodyCell(row, 3, entry.getExpenseAmount().toString());
			createLedgerBodyCell(row, 4, entry.getBalance().toString());

			row ++;
			lastEntry = entry;
		}
	}
	
	private void createLedgerHeaderCell(int row, int column, String text) {
		createLedgerCell(row, column, text, "ledger-header");
	}
	private void createLedgerBodyCell(int row, int column, String text) {
		createLedgerCell(row, column, text, "ledger");
	}
	private void createLedgerCell(int row, int column, String text, String style) {
		ledgerGrid.setWidget(row, column, new Label(text));
		ledgerGrid.getCellFormatter().setStyleName(row, column,style);
	}

	/**
	 * Update legerEntries from the reoccurring event within user-specified range 
	 * @param ledgerEntries
	 * @param event
	 */
	private void createLedgerEntries(ArrayList<LedgerEntry> ledgerEntries, FinancialEvent event) {
		// if neither start date nor end date are in range, just stop
		if (!isDateInLedgerRange(event.getStartDt()) && !isDateInLedgerRange(event.getEndDt())) {
			return; // skip this event altogether
		}
		Date instanceDate = event.getStartDt();
		// we know it's in range, so make instances
		do {
			// is date in range of ledger (could be too soon)?
			if (isDateGreaterThanOrEqualToStart(instanceDate)) {
				// add to ledger
				LedgerEntry entry = new LedgerEntry(event.getName(), event.getType(), event.getAmount(), instanceDate);
				ledgerEntries.add(entry);
			} 
			// increment
			instanceDate = event.getReoccurrence().getNext(instanceDate);
			// only stop if incremented instanceDate exceeds end date
		} while (isDateLessThanOrEqualToEnd(instanceDate));
	}

	/**
	 * If event's date is in range, create and add a ledger instance
	 * @param ledgerEntries
	 * @param event
	 */
	private void createLedgerEntry(
			ArrayList<LedgerEntry> ledgerEntries, FinancialEvent event) {
		if (isDateInLedgerRange(event.getStartDt())) {
			LedgerEntry entry = new LedgerEntry(event.getName(), event.getType(), event.getAmount(), event.getStartDt());
			ledgerEntries.add(entry);	
		}
	}
	
	/**
	 * @param aDate
	 * @return true if specified date is between start and end dates (inclusive)
	 */
	private boolean isDateInLedgerRange(Date aDate) {
		if (aDate == null) {
			// if it's null, it's an endDate, which is always in range
			return true;
		} else {
			return isDateGreaterThanOrEqualToStart(aDate) && isDateLessThanOrEqualToEnd(aDate);
		}
	}
	
	private boolean isDateGreaterThanOrEqualToStart(Date aDate) {
		return aDate.equals(getStartDt()) || aDate.after(getStartDt());
	}
	private boolean isDateLessThanOrEqualToEnd(Date aDate) {
		if (aDate == null) {
			// if it's null, it's an endDate, which is always in range
			return true;
		} else {
			return aDate.equals(getEndDt()) || aDate.before(getEndDt());
		}
	}
	
	@SuppressWarnings("deprecation")
	private Date getStartDt() {
		return new Date(txtStart.getText());
	}
	@SuppressWarnings("deprecation")
	private Date getEndDt() {
		return new Date(txtEnd.getText());
	}

}
