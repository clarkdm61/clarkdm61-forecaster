package dmc.forecaster.wicket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import dmc.forecaster.client.LedgerEntry;
import dmc.forecaster.server.ForecasterServiceImpl;
import dmc.forecaster.shared.FinancialEvent;
import dmc.forecaster.shared.Reoccurrence;
import dmc.forecaster.shared.UserPreference;

public class LedgerPage extends BasePage {
	static final String title = "Ledger Page";
	
	private UserPreference userPrefs = null;
	
	public LedgerPage() {
		// get events, process events into instances
		ForecasterServiceImpl service = new ForecasterServiceImpl();
		UserPreference prefs = service.getUserPreference();
		setUserPrefs(userPrefs);
		
		List<FinancialEvent> events = service.getAllEvents();
		
		// create table
		List<LedgerEntry> ledgerEntries = createLedger(events);
		
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

}
