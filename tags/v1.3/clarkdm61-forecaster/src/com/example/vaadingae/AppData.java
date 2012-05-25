package com.example.vaadingae;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.Application;
import com.vaadin.service.ApplicationContext.TransactionListener;

import dmc.forecaster.client.LedgerEntry;
import dmc.forecaster.shared.FinancialEvent;

/**
 * The pattern for using AppData seems to be analogous to Session data for a Vaadin app.
 * The basis for the datastructure, use of ThreadLocal, and initialization comes directly
 * from the Book of Vaadin.
 * 
 * @author David
 *
 */
public class AppData implements TransactionListener, Serializable {
	
	private static final long serialVersionUID = 9027135772079670233L;
	private Application app;
	private static ThreadLocal<AppData> instance = new ThreadLocal<AppData>();
	private List<FinancialEvent> financialEventList = null;
	private ArrayList<LedgerEntry> ledgerEntries = null;
	

	 public AppData(Application app) {
	        this.app = app;

	        // It's usable from now on in the current request
	        instance.set(this);
	    }

	    @Override
	    public void transactionStart(Application application,
	                                 Object transactionData) {
	        // Set this data instance of this application
	        // as the one active in the current thread. 
	        if (this.app == application)
	            instance.set(this);
	    }

	    @Override
	    public void transactionEnd(Application application,
	                               Object transactionData) {
	        // Clear the reference to avoid potential problems
	        if (this.app == application)
	            instance.set(null);
	    }

		public static List<FinancialEvent> getFinancialEventList() {
			return instance.get().financialEventList;
		}

		public static void setFinancialEventList(List<FinancialEvent> financialEventList) {
			instance.get().financialEventList = financialEventList;
		}

		public static ArrayList<LedgerEntry> getLedgerEntries() {
			return instance.get().ledgerEntries;
		}

		public static void setLedgerEntries(ArrayList<LedgerEntry> ledgerEntries) {
			instance.get().ledgerEntries = ledgerEntries;
		}


}
