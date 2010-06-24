package dmc.forecaster.client;

import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;

import dmc.forecaster.shared.FinancialEvent;
import dmc.forecaster.shared.FinancialEventType;
import dmc.forecaster.shared.Reoccurrence;

public class ManageTab extends DockLayoutPanel {
	public static String STATUS_OK = "OK";
	public static String STATUS_WAITING = "Waiting...";
	public static String STATUS_FAIL = "FAILED";

	private FinancialEvent selectedEvent = null;
	public static HTML status = new HTML("-");
	private final FinancialEventDialog financialEventDialog = new FinancialEventDialog();
	

	public ManageTab() {
		super(Unit.EM);
		
		// create buttons
	    Button btnNew = new Button("New", new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	financialEventDialog.openForNewEvent();
	        }
	    });
	    Button btnEdit = new Button("Edit", new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	if (getSelectedEvent() == null) {
	        		Window.alert("Nothing selected");
	        		return;
	        	}
	        	financialEventDialog.openForExistingEvent(getSelectedEvent());
	        }
	    });
	    Button btnDelete = new Button("Delete", new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        }
	    });

	    Grid buttonGrid = new Grid(1, 4);
	    buttonGrid.setWidget(0, 0, btnNew);
	    buttonGrid.setWidget(0, 1, btnEdit);
	    buttonGrid.setWidget(0, 2, btnDelete);
	    buttonGrid.setWidget(0, 3, status);
	    this.addSouth(buttonGrid, 2);
		
		// create pop-up window for create and update
		
		// get financial events
//	    FinancialEventDAO dao = new FinancialEventDAO();
	    List<FinancialEvent> list = null;//dao.findAll();
		
		
		// are there no finanial events?
		if (true) {
			// none
			add(new HTML("There are no events to display."));
		} else {
			// add grid
			int rows = list.size();
			int columns = 5;
			Grid table = new Grid(rows, columns);
			for (FinancialEvent fe : list) {
				// table.setText(row, column, text);
				//Row
			}
		}
	}
	
	public void doNew() {
		
		status.setText(STATUS_WAITING);
		
		
		FinancialEvent testEvent = new FinancialEvent("name", "description", null, null, 500.40d, FinancialEventType.Expense, Reoccurrence.None);
		Clarkdm61_forecaster.forecasterService.create(testEvent, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				status.setText(STATUS_OK);
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				status.setText(STATUS_FAIL);
				
			}
		});
	}

	public FinancialEvent getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(FinancialEvent selectedEvent) {
		this.selectedEvent = selectedEvent;
	}

}
