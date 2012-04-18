package dmc.forecaster.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.Table;

import dmc.forecaster.shared.FinancialEvent;
import dmc.forecaster.shared.LedgerEntryFactory;
import dmc.forecaster.shared.UserPreference;
import dmc.forecaster.shared.Util;

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
				txtStart.setText(Util.dateFormat(todayDt));
				txtEnd.setText(Util.dateFormat(nextDt));
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
		
		List<FinancialEvent> financialEvents =  Clarkdm61_forecaster.manageTab.getEventList();
		LedgerEntryFactory factory = new LedgerEntryFactory();
		
		ledgerEntries = factory.createLedgerEntries(financialEvents, getStartDt(), getEndDt());
		
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
			date.setYear(date.getYear()); // Defect/Issue #12 - removed 100 year adjustment
			
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
	
	@SuppressWarnings("deprecation")
	private Date getStartDt() {
		return new Date(txtStart.getText());
	}
	@SuppressWarnings("deprecation")
	private Date getEndDt() {
		return new Date(txtEnd.getText());
	}
}
