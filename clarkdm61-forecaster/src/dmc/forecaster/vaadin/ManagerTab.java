package dmc.forecaster.vaadin;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.CellStyleGenerator;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

import dmc.forecaster.shared.FinancialEvent;
import dmc.forecaster.shared.Reoccurrence;

public class ManagerTab extends CustomComponent {

	@AutoGenerated
	private VerticalLayout mainLayout;

	@AutoGenerated
	private Accordion managerTabAccordian;

	@AutoGenerated
	private Panel importExportAccordianPanel;

	@AutoGenerated
	private VerticalLayout importExportAccordianLayout;

	@AutoGenerated
	private Upload uploadControl;

	@AutoGenerated
	private Button btnDownload;

	@AutoGenerated
	private Panel manageEventsAccordianPanel;

	@AutoGenerated
	private VerticalLayout manageEventsAccordianLayout;

	@AutoGenerated
	private HorizontalLayout horizontalButtonLayout;

	@AutoGenerated
	private Button btnDelete;

	@AutoGenerated
	private Button btnEdit;

	@AutoGenerated
	private Button btnNew;

	@AutoGenerated
	private Panel managerTablePanel;

	@AutoGenerated
	private VerticalLayout managerTablePanelLayout;

	@AutoGenerated
	private Table managerTable;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private static final long serialVersionUID = -5330676819611696123L;
	
	// The currently selected event. Null when nothing is selected.
	private FinancialEvent selectedEvent = null;
	
	/**
	 * Unable to use the ConfirmDialog.Listener as an anonymous inner class because it
	 * is missing the Serializable implementation. A request for fix has been made to the add-on author.
	 */
	private class DeleteConfirmDialogListener implements ConfirmDialog.Listener, Serializable {
		private static final long serialVersionUID = 1749112843580556004L;

		@Override
		public void onClose(ConfirmDialog dialog) {
			if (dialog.isConfirmed()) {
				doDelete();
			}
		}
		
	}
	
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public ManagerTab() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// add user code here:
		refreshManagerTable();
		
		// add event handlers
		addEventHandlers();
	}
	
	/**
	 * Invoke getAllEvents, then update table.
	 */
	public void refreshManagerTable() {
		List<FinancialEvent> list =  AppData.getForecasterService().getAllEvents();
		AppData.setFinancialEventList(list);

		Collections.sort(list);
		
		FinancialEventContainer container = new FinancialEventContainer(FinancialEvent.class, list);
		
		managerTable.setContainerDataSource(container);
		managerTable.setVisibleColumns(FinancialEventContainer.NATURAL_COL_ORDER);
		managerTable.setColumnHeaders(FinancialEventContainer.COL_HEADERS_ENGLISH);
		
		// add CellStyleGenerator to the managerTable so CSS style can be specified dynamically
		// see VAADIN/themes/forecaster/style.css
		managerTable.setCellStyleGenerator(new CellStyleGenerator() {
			
			private static final long serialVersionUID = -1062010296768544652L;

			@Override
			public String getStyle(Object itemId, Object propertyId) {
				FinancialEvent financialEvent = (FinancialEvent) itemId;
				long currTime = System.currentTimeMillis();
				if (financialEvent.getEndDt() != null && financialEvent.getEndDt().getTime() < currTime) {
					// end date specified, and is before now.
					return "oldItem";
				} else if (financialEvent.getReoccurrence() == Reoccurrence.None && financialEvent.getStartDt().getTime() < currTime) {
					// one-time event that is in the past
					return "oldItem";
				}
				return null;
			}
		});
	}
	
	/**
	 * Add event handlers for table item selection, and button clicks
	 */
	protected void addEventHandlers() {
		
		//Handle selection of FinancialEvents in ManagerTab so they can be edited or deleted.
		managerTable.setSelectable(true);
		managerTable.setImmediate(true);
		managerTable.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = -670999562596285771L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				FinancialEvent fe = (FinancialEvent)((Table) event.getProperty()).getValue();
				setSelectedEvent(fe);
			}
			
		});

		// Create a new FinancialEvent.
		btnNew.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = -531250317263359229L;

			@Override
			public void buttonClick(ClickEvent event) {
				FinancialEventDialog financialEventDialog = createFinancialEventDialog();
				// Stuff a new FinancialEvent into the editor dialog
				FinancialEvent newEvent = new FinancialEvent("", "", new java.util.Date(), null, null, null, Reoccurrence.None);
				financialEventDialog.openForEdit(newEvent);
				getWindow().addWindow(financialEventDialog);
			}});
		
		//Edit the currently selected event. Do nothing if no event is selected.
		btnEdit.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = -4971025968218918368L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (getSelectedEvent() != null) {
					FinancialEventDialog financialEventDialog = createFinancialEventDialog();
					financialEventDialog.openForEdit(selectedEvent);
					getWindow().addWindow(financialEventDialog);
					
				}
			}});
		
		
		//Delete the currently selected event.
		btnDelete.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = -8571159641244139564L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (getSelectedEvent() != null) {
					showConfirmDelete();
				}
			}});
		
		//Download events to XML file.
		btnDownload.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				Logger.getLogger(getClass().getName()).fine("Download invoked.");
				EventDownloader resource = new EventDownloader(getApplication());
				getApplication().getMainWindow().open(resource, "_blank");
			}});
		
		// The upload widget
		EventUploader uploader = new EventUploader(this);
		uploadControl.addListener((Upload.FailedListener) uploader);
		uploadControl.addListener((Upload.SucceededListener) uploader);
		uploadControl.setReceiver(uploader);
	}
	
	/**
	 * Create the dialog callback handler, then open the confirm delete dialog.
	 */
	private void showConfirmDelete() {
		DeleteConfirmDialogListener callback = new DeleteConfirmDialogListener();
		ConfirmDialog.show(getParent().getWindow(), "Confirm Delete", "Delete record?", "Yes", "Cancel", callback);
	}

	/**
	 * Perform the delete operation and synch the list.
	 */
	private void doDelete() {
		AppData.getForecasterService().delete(getSelectedEvent().getId());
		refreshManagerTable();		
	}
	
	/**
	 * Instantiate a FinancialEvent dialog with a reference to 'this'
	 * (access to 'this' is inconvenient within anon inner class).
	 * @return
	 */
	private FinancialEventDialog createFinancialEventDialog() {
		return new FinancialEventDialog(this);
	}

	protected FinancialEvent getSelectedEvent() {
		return selectedEvent;
	}

	protected void setSelectedEvent(FinancialEvent selectedEvent) {
		this.selectedEvent = selectedEvent;
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
		
		// managerTabAccordian
		managerTabAccordian = buildManagerTabAccordian();
		mainLayout.addComponent(managerTabAccordian);
		
		return mainLayout;
	}

	@AutoGenerated
	private Accordion buildManagerTabAccordian() {
		// common part: create layout
		managerTabAccordian = new Accordion();
		managerTabAccordian.setImmediate(true);
		managerTabAccordian.setWidth("100.0%");
		managerTabAccordian.setHeight("-1px");
		
		// manageEventsAccordianPanel
		manageEventsAccordianPanel = buildManageEventsAccordianPanel();
		managerTabAccordian.addTab(manageEventsAccordianPanel,
				"Add/Update Events", null);
		
		// importExportAccordianPanel
		importExportAccordianPanel = buildImportExportAccordianPanel();
		managerTabAccordian.addTab(importExportAccordianPanel,
				"Import/Export Events", null);
		
		return managerTabAccordian;
	}

	@AutoGenerated
	private Panel buildManageEventsAccordianPanel() {
		// common part: create layout
		manageEventsAccordianPanel = new Panel();
		manageEventsAccordianPanel.setImmediate(false);
		manageEventsAccordianPanel.setWidth("100.0%");
		manageEventsAccordianPanel.setHeight("-1px");
		
		// manageEventsAccordianLayout
		manageEventsAccordianLayout = buildManageEventsAccordianLayout();
		manageEventsAccordianPanel.setContent(manageEventsAccordianLayout);
		
		return manageEventsAccordianPanel;
	}

	@AutoGenerated
	private VerticalLayout buildManageEventsAccordianLayout() {
		// common part: create layout
		manageEventsAccordianLayout = new VerticalLayout();
		manageEventsAccordianLayout.setImmediate(false);
		manageEventsAccordianLayout.setWidth("100.0%");
		manageEventsAccordianLayout.setHeight("100.0%");
		manageEventsAccordianLayout.setMargin(false);
		
		// managerTablePanel
		managerTablePanel = buildManagerTablePanel();
		manageEventsAccordianLayout.addComponent(managerTablePanel);
		
		// horizontalButtonLayout
		horizontalButtonLayout = buildHorizontalButtonLayout();
		manageEventsAccordianLayout.addComponent(horizontalButtonLayout);
		
		return manageEventsAccordianLayout;
	}

	@AutoGenerated
	private Panel buildManagerTablePanel() {
		// common part: create layout
		managerTablePanel = new Panel();
		managerTablePanel.setImmediate(false);
		managerTablePanel.setWidth("100.0%");
		managerTablePanel.setHeight("-1px");
		
		// managerTablePanelLayout
		managerTablePanelLayout = buildManagerTablePanelLayout();
		managerTablePanel.setContent(managerTablePanelLayout);
		
		return managerTablePanel;
	}

	@AutoGenerated
	private VerticalLayout buildManagerTablePanelLayout() {
		// common part: create layout
		managerTablePanelLayout = new VerticalLayout();
		managerTablePanelLayout.setImmediate(false);
		managerTablePanelLayout.setWidth("100.0%");
		managerTablePanelLayout.setHeight("-1px");
		managerTablePanelLayout.setMargin(false);
		
		// managerTable
		managerTable = new Table();
		managerTable.setImmediate(false);
		managerTable.setWidth("100.0%");
		managerTable.setHeight("-1px");
		managerTablePanelLayout.addComponent(managerTable);
		
		return managerTablePanelLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildHorizontalButtonLayout() {
		// common part: create layout
		horizontalButtonLayout = new HorizontalLayout();
		horizontalButtonLayout.setImmediate(false);
		horizontalButtonLayout.setWidth("100.0%");
		horizontalButtonLayout.setHeight("-1px");
		horizontalButtonLayout.setMargin(false);
		
		// btnNew
		btnNew = new Button();
		btnNew.setCaption("New");
		btnNew.setImmediate(true);
		btnNew.setWidth("-1px");
		btnNew.setHeight("-1px");
		horizontalButtonLayout.addComponent(btnNew);
		
		// btnEdit
		btnEdit = new Button();
		btnEdit.setCaption("Edit");
		btnEdit.setImmediate(true);
		btnEdit.setWidth("-1px");
		btnEdit.setHeight("-1px");
		horizontalButtonLayout.addComponent(btnEdit);
		
		// btnDelete
		btnDelete = new Button();
		btnDelete.setCaption("Delete");
		btnDelete.setImmediate(true);
		btnDelete.setWidth("-1px");
		btnDelete.setHeight("-1px");
		horizontalButtonLayout.addComponent(btnDelete);
		
		return horizontalButtonLayout;
	}

	@AutoGenerated
	private Panel buildImportExportAccordianPanel() {
		// common part: create layout
		importExportAccordianPanel = new Panel();
		importExportAccordianPanel.setImmediate(false);
		importExportAccordianPanel.setWidth("100.0%");
		importExportAccordianPanel.setHeight("-1px");
		
		// importExportAccordianLayout
		importExportAccordianLayout = buildImportExportAccordianLayout();
		importExportAccordianPanel.setContent(importExportAccordianLayout);
		
		return importExportAccordianPanel;
	}

	@AutoGenerated
	private VerticalLayout buildImportExportAccordianLayout() {
		// common part: create layout
		importExportAccordianLayout = new VerticalLayout();
		importExportAccordianLayout.setImmediate(false);
		importExportAccordianLayout.setWidth("100.0%");
		importExportAccordianLayout.setHeight("100.0%");
		importExportAccordianLayout.setMargin(false);
		
		// btnDownload
		btnDownload = new Button();
		btnDownload.setCaption("Export Events");
		btnDownload.setImmediate(true);
		btnDownload.setDescription("Exports events to XML");
		btnDownload.setWidth("-1px");
		btnDownload.setHeight("-1px");
		importExportAccordianLayout.addComponent(btnDownload);
		
		// uploadControl
		uploadControl = new Upload();
		uploadControl.setCaption("Import Events...");
		uploadControl.setImmediate(false);
		uploadControl.setWidth("-1px");
		uploadControl.setHeight("-1px");
		importExportAccordianLayout.addComponent(uploadControl);
		importExportAccordianLayout.setExpandRatio(uploadControl, 5.0f);
		
		return importExportAccordianLayout;
	}


}
