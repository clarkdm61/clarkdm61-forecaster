package dmc.forecaster.vaadin;

import java.util.Collection;

import com.vaadin.data.util.BeanItemContainer;

import dmc.forecaster.client.LedgerEntry;

public class LedgerEntryContainer extends BeanItemContainer<LedgerEntry> {

	private static final long serialVersionUID = 5547254294142502605L;
	/**
	 * Natural property order for LedgerEntry bean. Used in tables and forms.
	 */
	public static final Object[] NATURAL_COL_ORDER = new Object[] {
			"date", "name", "incomeAmount", "expenseAmount", "balance" };

	/**
	 * "Human readable" captions for properties in same order as in
	 * NATURAL_COL_ORDER.
	 */
	public static final String[] COL_HEADERS_ENGLISH = new String[] {
		"Date", "Event", "Income", "Expense", "Balance" };

	public LedgerEntryContainer(Class<? super LedgerEntry> type,
			Collection<? extends LedgerEntry> collection)
			throws IllegalArgumentException {
		super(type, collection);
	}
}
