package dmc.forecaster.vaadin;

import java.io.Serializable;
import java.util.Collection;

import com.vaadin.data.util.BeanItemContainer;

import dmc.forecaster.shared.FinancialEvent;

public class FinancialEventContainer extends BeanItemContainer<FinancialEvent>
		implements Serializable {

	private static final long serialVersionUID = 5796015211199025770L;
	

	/**
	 * Natural property order for Person bean. Used in tables and forms.
	 */
	public static final Object[] NATURAL_COL_ORDER = new Object[] {
			"labelString" };

	/**
	 * "Human readable" captions for properties in same order as in
	 * NATURAL_COL_ORDER.
	 */
	public static final String[] COL_HEADERS_ENGLISH = new String[] {
			"Financial Event" };

	public FinancialEventContainer(Class<? super FinancialEvent> type,
			Collection<? extends FinancialEvent> collection)
			throws IllegalArgumentException {
		super(type, collection);
	}

}
