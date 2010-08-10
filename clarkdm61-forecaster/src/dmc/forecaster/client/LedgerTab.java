package dmc.forecaster.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.Table;

import dmc.forecaster.shared.FinancialEvent;
import dmc.forecaster.shared.Reoccurrence;
import dmc.forecaster.shared.UserPreference;

public class LedgerTab extends DockLayoutPanel {
	public static ArrayList<LedgerEntry> ledgerEntries = null;
	private static final TextBox txtStart = new TextBox();
	private static final TextBox txtEnd = new TextBox();
	private static UserPreference userPreference = null;
	
	public LedgerTab() {
		super(Unit.EM); // needed for DockLayoutPanel
		
		Grid topPanel = new Grid(1,5);
		// start date
		topPanel.setWidget(0, 0, new Label("Start "));
		txtStart.setWidth("5em");
		
		setLedgerRangeFromUserPreference();
		
		topPanel.setWidget(0, 1, txtStart);
		// end date
		topPanel.setWidget(0, 2, new Label("End "));
		txtEnd.setWidth("5em");
		topPanel.setWidget(0, 3, txtEnd);
		// button
	    Button btnGo = new Button("Go", new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	updateLedgerRangeOnUserPreference();
	        	createLedger(txtStart.getText(), txtEnd.getText());
	        }
	    });
	    topPanel.setWidget(0, 4, btnGo);
		
		// add to this 
		addNorth(topPanel, 3);
	}

	/**
	 * Get UserPreference, update ledger range on UI 
	 */
	private void setLedgerRangeFromUserPreference() {
		/* enhancement to get range from user pref */
		Clarkdm61_forecaster.forecasterService.getUserPreference(new AsyncCallback<UserPreference>() {
			
			@Override
			public void onSuccess(UserPreference result) {
				userPreference = result;
				Date todayDt = userPreference.getLedgerStartDate();
				Date nextDt = userPreference.getLedgerEndDate();
				txtStart.setText(DateTimeFormat.getShortDateFormat().format(todayDt));
				txtEnd.setText(DateTimeFormat.getShortDateFormat().format(nextDt));
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Critical error: LedgerTab failed to find and/or generate UserPreference");
			}
		});
	}
	
	/**
	 * Update ledger range on UserPreference from UI
	 */
	private void updateLedgerRangeOnUserPreference() {
		
		userPreference.setLedgerStartDate(getStartDt());
		userPreference.setLedgerEndDate(getEndDt());
		
		Clarkdm61_forecaster.forecasterService.updateUserPreference(userPreference, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Critical error: LedgerTab failed to update UserPreference");
			}

			@Override
			public void onSuccess(Void result) {
				
			}
		});
	}
	
	/**
	 * Entry point for button handler to create ledger
	 * @param startDt
	 * @param endDt
	 */
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
		
		// sort entries by start date (note: the balance can't be calculated until this happens)
		Collections.sort(ledgerEntries);
		
		// update ui, and calculate balance while enumerating entries
		Runnable runnableCallBack = new Runnable() {
			public void run() {
				Table table = new Table(createTable(), createOptions());
				ScrollPanel scrollPanel = new ScrollPanel(table);
				LedgerTab.this.add(scrollPanel);
			}
		};

		VisualizationUtils.loadVisualizationApi(runnableCallBack,
				Table.PACKAGE);
	}
	
	/**
	 * prepare data model, and calculate balance fields
	 * @return
	 */
	private AbstractDataTable createTable() {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.DATE, "Date");     //0
		data.addColumn(ColumnType.STRING, "Event");  //1
		data.addColumn(ColumnType.NUMBER, "Income"); //2
		data.addColumn(ColumnType.NUMBER, "Expense");//3
		data.addColumn(ColumnType.NUMBER, "Balance");//4
	
		// the fourth column is not used, it would be extra text
		data.addRows(LedgerTab.ledgerEntries.size());
		
		int row = 0;
		LedgerEntry lastEntry = null;
		for (LedgerEntry entry : LedgerTab.ledgerEntries) {
			Double balance = lastEntry == null ? 0d : lastEntry.getBalance(); 
			balance += entry.getIncomeAmount();
			balance -= entry.getExpenseAmount();
			entry.setBalance(balance);

			Date date = entry.getDate();
			date.setYear(date.getYear()+100); // bug in google's widget maybe?
			
			data.setValue(row, 0, date);
			data.setValue(row, 1, entry.getName());
			data.setValue(row, 2, entry.getIncomeAmount());
			data.setValue(row, 3, entry.getExpenseAmount());
			data.setValue(row, 4, entry.getBalance());
			
			if (entry.getRowColor() != null) {
				modifyRow(row, 5, data, "color:"+entry.getRowColor());
			}
			if (entry.getBalance() < 0) {
				data.setProperty(row, 4, "style", "color:red");
			}
			row++;
			lastEntry = entry;
		}
		
		return data;
	}
	/**
	 * Apply specified style to specified row
	 * @param row index
	 * @param cols number of cols in row
	 * @param data
	 * @param style
	 */
	private void modifyRow(int row, int cols, DataTable data, String style) {
		for (int i = 0; i < cols; i++) {
			data.setProperty(row, i, "style", style);
		}
	}
	
	/**
	 * create table options - disable sorting
	 * @return
	 */
	private Table.Options createOptions() {
		Table.Options options = Table.Options.create();
		options.setSort(Table.Options.Policy.DISABLE);
		options.setWidth("70em");
		//options.setPage(Table.Options.Policy.ENABLE); 
		//options.setPageSize(40);
		options.setAllowHtml(true); // required to enable DataTable styles to be applied
		return options;
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
				if (event.getReoccurrence().equals(Reoccurrence.TwiceYearly)) {
					entry.setRowColor("blue"); // TODO: make colors constants
				}
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
			entry.setRowColor("blue");// TODO: make colors constants
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
