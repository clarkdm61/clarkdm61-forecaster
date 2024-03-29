package dmc.forecaster.vaadin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.CellStyleGenerator;
import com.vaadin.ui.VerticalLayout;

import dmc.forecaster.client.LedgerEntry;
import dmc.forecaster.shared.FinancialEvent;
import dmc.forecaster.shared.LedgerEntryFactory;
import dmc.forecaster.shared.UserPreference;

public class LedgerTab extends CustomComponent {

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private Table ledgerTable;
	@AutoGenerated
	private HorizontalLayout topLayout;
	@AutoGenerated
	private Button btnGo;
	@AutoGenerated
	private PopupDateField endDate;
	@AutoGenerated
	private Label lblEnd;
	@AutoGenerated
	private PopupDateField startDate;
	@AutoGenerated
	private Label lblStart;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public LedgerTab() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// add user code here
		setLedgerRangeFromUserPreference();
		
		btnGo.addListener(new ClickListener() {
			private static final long serialVersionUID = -1341292646661187337L;
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					updateLedgerRangeOnUserPreference();
					createLedger();
				} catch (Exception e) {
					getLogger().log(Level.SEVERE, "Error while handeling Go button.", e);
					// TODO: let user know there was an error (create a common error popup)
					throw new RuntimeException(e);
				}
				
			}
		});
	}

	/**
	 * Update the User Preference from values on the UI
	 */
	private void updateLedgerRangeOnUserPreference() {
		UserPreference upref = AppData.getForecasterService().getUserPreference().deepCopy();
		
		Date startDateValue = (Date) getStartDate().getValue();
		Date endDateValue = (Date) getEndDate().getValue();
		upref.setLedgerStartDate(startDateValue);
		upref.setLedgerEndDate(endDateValue);
		
		AppData.getForecasterService().updateUserPreference(upref);
	}

	/**
	 * Update the start and end date on the UI from User Preference values.
	 */
	private void setLedgerRangeFromUserPreference() {
		UserPreference prefs = AppData.getForecasterService().getUserPreference();
		getStartDate().setValue(prefs.getLedgerStartDate());
		getEndDate().setValue(prefs.getLedgerEndDate());
		
	}

	/**
	 * The receiver of the button click event to create the ledger. 
	 */
	private void createLedger() {
		
		List<FinancialEvent> financialEvents = AppData.getFinancialEventList();
		LedgerEntryFactory factory = new LedgerEntryFactory();
		
		Date startDt = (Date) getStartDate().getValue();
		Date endDt = (Date) getEndDate().getValue();
		ArrayList<LedgerEntry> ledgerEntries = factory.createLedgerEntries(financialEvents, startDt, endDt);
		AppData.setLedgerEntries(ledgerEntries);
		
		LedgerEntryContainer container = new LedgerEntryContainer(LedgerEntry.class, ledgerEntries);
			
		ledgerTable.setContainerDataSource(container);
		ledgerTable.setSelectable(true); // easier to focus attention on a selectable event
		
		// override column formats
		ledgerTable.removeGeneratedColumn("date"); // in case one already exist, otherwise exception occurs
		ledgerTable.addGeneratedColumn("date", new DateColumnGenerator());
		
		ledgerTable.setVisibleColumns(LedgerEntryContainer.NATURAL_COL_ORDER);
		ledgerTable.setColumnHeaders(LedgerEntryContainer.COL_HEADERS_ENGLISH);
		
		// apply style to row based on ledgerEntry.RowColor
		ledgerTable.setCellStyleGenerator(new CellStyleGenerator() {
			private static final long serialVersionUID = -1062010296768544652L;
			@Override
			public String getStyle(Object itemId, Object propertyId) {
				LedgerEntry ledgerEntry = (LedgerEntry) itemId;
				if (ledgerEntry.getRowColor() != null && !ledgerEntry.getRowColor().isEmpty()) {
					return ledgerEntry.getRowColor();
				}
				return null;
			}
		});
		
	}
	
	public PopupDateField getEndDate() {
		return endDate;
	}

	public void setEndDate(PopupDateField endDate) {
		this.endDate = endDate;
	}

	public PopupDateField getStartDate() {
		return startDate;
	}

	public void setStartDate(PopupDateField startDate) {
		this.startDate = startDate;
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("-1px");
		
		// topLayout
		topLayout = buildTopLayout();
		mainLayout.addComponent(topLayout);
		
		// ledgerTable
		ledgerTable = new Table();
		ledgerTable.setImmediate(false);
		ledgerTable.setWidth("100.0%");
		ledgerTable.setHeight("-1px");
		mainLayout.addComponent(ledgerTable);
		
		return mainLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildTopLayout() {
		// common part: create layout
		topLayout = new HorizontalLayout();
		topLayout.setImmediate(false);
		topLayout.setWidth("100.0%");
		topLayout.setHeight("-1px");
		topLayout.setMargin(false);
		
		// lblStart
		lblStart = new Label();
		lblStart.setImmediate(false);
		lblStart.setWidth("-1px");
		lblStart.setHeight("-1px");
		lblStart.setValue("Start");
		topLayout.addComponent(lblStart);
		
		// startDate
		startDate = new PopupDateField();
		startDate.setImmediate(false);
		startDate.setWidth("-1px");
		startDate.setHeight("-1px");
		startDate.setInvalidAllowed(false);
		startDate.setResolution(4);
		topLayout.addComponent(startDate);
		
		// lblEnd
		lblEnd = new Label();
		lblEnd.setImmediate(false);
		lblEnd.setWidth("-1px");
		lblEnd.setHeight("-1px");
		lblEnd.setValue("End");
		topLayout.addComponent(lblEnd);
		
		// endDate
		endDate = new PopupDateField();
		endDate.setImmediate(false);
		endDate.setWidth("-1px");
		endDate.setHeight("-1px");
		endDate.setInvalidAllowed(false);
		endDate.setResolution(4);
		topLayout.addComponent(endDate);
		
		// btnGo
		btnGo = new Button();
		btnGo.setCaption("Go");
		btnGo.setImmediate(true);
		btnGo.setWidth("-1px");
		btnGo.setHeight("-1px");
		topLayout.addComponent(btnGo);
		
		return topLayout;
	}

	/**
	 * TODO: put this is a base class
	 * @return
	 */
	protected Logger getLogger() {
		return Logger.getLogger(getClass().getName());
	}
}

