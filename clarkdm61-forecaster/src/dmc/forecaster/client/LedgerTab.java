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
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;

import dmc.forecaster.shared.FinancialEvent;
import dmc.forecaster.shared.Reoccurrence;

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
		
		ledgerGrid.setBorderWidth(1);
		ScrollPanel scrollPanel = new ScrollPanel(ledgerGrid);
		add(scrollPanel);
		
	}
	
	private void createLedger(String startDt, String endDt) {
		ArrayList<LedgerEntry> ledgerEntries = new ArrayList<LedgerEntry>();
		
		// for each FinancialEvent..
		// create instances within date range
		for (FinancialEvent event : Clarkdm61_forecaster.manageTab.getEventList()) {
			// is this reoccurring?
			if (!event.getReoccurrence().equals(Reoccurrence.None)) {
				// if neither start date nor end date are in range, just stop
				if (!isDateInLedgerRange(event.getStartDt()) && !isDateInLedgerRange(event.getEndDt())) {
					break; // skip this event altogether
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
			} else {
				addLedgerEntryIfEventIsInRange(ledgerEntries, event);
			}
		}
		
		// sort entries by start date 
		Collections.sort(ledgerEntries);
		
		// update ui
		ledgerGrid.removeAllRows();
		int row = 0;
		ledgerGrid.setWidget(row, 0, new Label("Date"));
		ledgerGrid.setWidget(row, 1, new Label("Event"));
		ledgerGrid.setWidget(row, 2, new Label("Income"));
		ledgerGrid.setWidget(row, 3, new Label("Expense"));
		ledgerGrid.setWidget(row, 4, new Label("Balance"));
		
		row++;
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

	/**
	 * If event's date is in range, create and add a ledger instance
	 * @param ledgerEntries
	 * @param event
	 */
	private void addLedgerEntryIfEventIsInRange(
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
		return isDateGreaterThanOrEqualToStart(aDate) && isDateLessThanOrEqualToEnd(aDate);
	}
	
	private boolean isDateGreaterThanOrEqualToStart(Date aDate) {
		return aDate.equals(getStartDt()) || aDate.after(getStartDt());
	}
	private boolean isDateLessThanOrEqualToEnd(Date aDate) {
		return aDate.equals(getEndDt()) || aDate.before(getEndDt());
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
