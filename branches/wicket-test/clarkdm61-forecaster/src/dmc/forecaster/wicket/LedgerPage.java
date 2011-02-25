package dmc.forecaster.wicket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;

import dmc.forecaster.client.LedgerEntry;
import dmc.forecaster.server.ForecasterServiceImpl;
import dmc.forecaster.shared.FinancialEvent;
import dmc.forecaster.shared.Reoccurrence;
import dmc.forecaster.shared.UserPreference;
import static dmc.forecaster.shared.Utils.*;

public class LedgerPage extends BasePage {
	static final String title = "Ledger Page";
	static Logger log = Logger.getLogger(LedgerPage.class.getName());
	private UserPreference userPrefs = null;
	
	private CompoundPropertyModel<UserPreference> propertyModel = null;
	
	private class FormModel {
		private UserPreference prefs = null;
		public FormModel(UserPreference prefs) {
			this.prefs = prefs;
		}
		public String getStartDt() {
			return dateFormat(prefs.getLedgerStartDate());
		}
		public void setStartDt(String szDate) {
			System.out.println("setting start date="+szDate);
			Date d = new Date(szDate);
			prefs.setLedgerStartDate(d);
		}
		public String getEndDt() {
			return dateFormat(prefs.getLedgerEndDate());
		}
		public void setEndDt(String szDate) {
			Date d = new Date(szDate);
			prefs.setLedgerEndDate(d);
		}
	}
	
	public LedgerPage() {
		log.fine("LedgerPage() constructed");
		// get events, process events into instances
		final ForecasterServiceImpl service = new ForecasterServiceImpl();
		final UserPreference prefs = service.getUserPreference();
		setUserPrefs(prefs);
		propertyModel = new CompoundPropertyModel<UserPreference>(new FormModel(prefs));
		
		DataView<LedgerEntry> ledgerRows = renderTable(service);
		
		//add(new Label("dateRange", "Date range: " + dateFormat(getUserPrefs().getLedgerStartDate()) + " to " + dateFormat(getUserPrefs().getLedgerEndDate())));
		
		Form form = new Form("form") {
			protected void onSubmit() {
				log.fine("onSubmit()");
				service.updateUserPreference(getUserPrefs());
				// actually navigate to self?
				//LedgerPage nextPage = new LedgerPage();
			}
		};
		form.add(new TextField("startDt"));
		form.add(new TextField("endDt"));
		add(form);
		form.setDefaultModel(propertyModel);
		add(ledgerRows);
	}

	private DataView<LedgerEntry> renderTable(
			final ForecasterServiceImpl service) {
		List<FinancialEvent> events = service.getAllEvents();
		log.fine("renderTable(..)");
		// create table
		List<LedgerEntry> ledgerEntries = createLedger(events);
		
		// Create the data view
		DataView<LedgerEntry> ledgerRows = new DataView<LedgerEntry>("ledgerRows", new ListDataProvider<LedgerEntry>(ledgerEntries)) {
			private LedgerEntry lastEntry = null;
			private RowAlternatorModel rowAlternator = new RowAlternatorModel();
			
			/**
			 * Added so when refresh button is used, the lastEntry is properly reset to null. 
			 */
			@Override
			protected void onAfterRender() {
				super.onAfterRender();
				lastEntry = null;
			};
			
			@Override
			protected void populateItem(Item<LedgerEntry> row) {
				LedgerEntry entry = row.getModel().getObject();
				Double balance = lastEntry == null ? 0d : lastEntry.getBalance(); 
				balance += entry.getIncomeAmount();
				balance -= entry.getExpenseAmount();
				entry.setBalance(balance);

				Date date = entry.getDate();
				date.setYear(date.getYear()); // Defect/Issue #12 - removed 100 year adjustment
				
				row.add(new Label("date", dateFormat(date)));
				row.add(new Label("event", entry.getName()));
				row.add(new Label("income", currencyFormat(entry.getIncomeAmount())));
				row.add(new Label("expense", currencyFormat(entry.getExpenseAmount())));
				Label lblBalance = new Label("balance", currencyFormat(entry.getBalance()));
				
				if (entry.getRowColor() != null) {
					row.add( new AttributeModifier("style", true, new StringModel("color:"+entry.getRowColor())) );
				}
				if (entry.getBalance() < 0) {
					lblBalance.add( new AttributeModifier("style", true, new StringModel("color:red")) );
				}
				row.add(lblBalance);
				
				row.add( new AttributeModifier("class", true, rowAlternator) );
				
				lastEntry = entry;
			}
		};
		return ledgerRows;
	}

	/**
	 * Entry point for creating the ledger from FinancialEvent list
	 * @param events
	 */
	private List<LedgerEntry> createLedger(List<FinancialEvent> events) {
	
		List<LedgerEntry> ledgerEntries = new ArrayList<LedgerEntry>();
		
		// create ledger entries from event list
		// for each FinancialEvent..
		// create instances within date range
		for (FinancialEvent event : events) {
			// handle reoccurring events differently
			if (!event.getReoccurrence().equals(Reoccurrence.None)) {
				createLedgerEntries(ledgerEntries, event); 
			} else {
				createLedgerEntry(ledgerEntries, event);
			}
		}
		
		// sort entries by start date (note: the balance can't be calculated until this happens)
		Collections.sort(ledgerEntries);

		return ledgerEntries;
	}

	/**
	 * If event's date is in range, create and add a ledger instance
	 * @param ledgerEntries
	 * @param event
	 */
	private void createLedgerEntry(
			List<LedgerEntry> ledgerEntries, FinancialEvent event) {
		if (isDateInLedgerRange(event.getStartDt())) {
			LedgerEntry entry = new LedgerEntry(event.getName(), event.getType(), event.getAmount(), event.getStartDt());
			entry.setRowColor("blue");// TODO: make colors constants
			ledgerEntries.add(entry);	
		}
	}

	/**
	 * Update legerEntries from the reoccurring event within user-specified range 
	 * @param ledgerEntries
	 * @param event
	 */
	private void createLedgerEntries(List<LedgerEntry> ledgerEntries, FinancialEvent event) {
		// if the start date is future, or the end date is past; just stop.
		if (isDateGreaterThanEnd(event.getStartDt()) || isDateLessThanStart(event.getEndDt())) {
			return;
		}
		Date instanceDate = event.getStartDt();
		// we know it's in range, so make instances
		do {
			// is date in range of ledger (could be too soon)?
			if (isDateGreaterThanOrEqualToStart(instanceDate)) {
				// add to ledger
				LedgerEntry entry = new LedgerEntry(event.getName(), event.getType(), event.getAmount(), instanceDate);
				// highlight unusual events
				if (event.getReoccurrence().equals(Reoccurrence.TwiceYearly)
						|| event.getReoccurrence().equals(Reoccurrence.Yearly)
						) {
					entry.setRowColor("blue"); // TODO: make colors constants
				}
				ledgerEntries.add(entry);
			} 
			// increment
			instanceDate = event.getReoccurrence().getNext(instanceDate);
			// stop if incremented instanceDate exceeds ledger end date, or the event's end date
		} while (isDateLessThanOrEqualToEnd(instanceDate) && event.isInDateRange(instanceDate));
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
	
	/**
	 * Returns true if aDate >= Ledger Start Date
	 * @param aDate
	 * @return
	 */
	private boolean isDateGreaterThanOrEqualToStart(Date aDate) {
		return aDate.equals(getStartDt()) || aDate.after(getStartDt());
	}
	
	/**
	 * Returns true if aDate <= Ledger End Date
	 * @param aDate
	 * @return
	 */
	private boolean isDateLessThanOrEqualToEnd(Date aDate) {
		if (aDate == null) {
			// if it's null, it's an endDate, which is always in range
			return true;
		} else {
			return aDate.equals(getEndDt()) || aDate.before(getEndDt());
		}
	}

	/**
	 * Returns true if [start]aDate >= Ledger End Date
	 */
	private boolean isDateGreaterThanEnd(Date aDate) {
		return aDate.after(getEndDt());
	}
	
	/**
	 * Returns true if [end]aDate < Ledger Start Date
	 */
	private boolean isDateLessThanStart(Date aDate) {
		if (aDate == null) {
			return false;
		} else {
			return aDate.before(getStartDt());
		}
	}

	private Date getStartDt() {
		return getUserPrefs().getLedgerStartDate();
	}
	
	
	private Date getEndDt() {
		return getUserPrefs().getLedgerEndDate();
	}

	@Override
	protected String getTitle() {
		
		return title;
	}

	public UserPreference getUserPrefs() {
		return userPrefs;
	}

	public void setUserPrefs(UserPreference userPrefs) {
		this.userPrefs = userPrefs;
	}
	
	public static String currencyFormat(Double amt) {
		if (amt == 0d) {
			return "-";
		}
		return String.format("$%.2f", Math.abs(amt));
	}

}
