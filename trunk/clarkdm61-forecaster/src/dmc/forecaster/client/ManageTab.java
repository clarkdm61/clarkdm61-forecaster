package dmc.forecaster.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;

import dmc.forecaster.shared.FinancialEvent;

public class ManageTab extends DockLayoutPanel {
	public static String STATUS_OK = "OK";
	public static String STATUS_WAITING = "Waiting...";
	public static String STATUS_FAIL = "FAILED";

	private SelectableCell selectedCell = null;
	
	public static final HTML status = new HTML("-");
	private static final FinancialEventDialog financialEventDialog = new FinancialEventDialog();
	
	private FlexTable selectableList = new FlexTable();
	private List<FinancialEvent> eventList = null;
	

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
	        	if (getSelectedCell() == null) {
	        		Window.alert("Nothing selected");
	        		return;
	        	}
	        	financialEventDialog.openForExistingEvent(getSelectedCell().getFinancialEvent());
	        }
	    });
	    Button btnDelete = new Button("Delete", new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	if (getSelectedCell() == null) {
	        		Window.alert("Nothing selected");
	        		return;
	        	}
	        	invokeDelete(getSelectedCell().getFinancialEvent());
	        }
	    });

	    Grid buttonGrid = new Grid(1, 4);
	    buttonGrid.setWidget(0, 0, btnNew);
	    buttonGrid.setWidget(0, 1, btnEdit);
	    buttonGrid.setWidget(0, 2, btnDelete);
	    buttonGrid.setWidget(0, 3, status);
	    this.addSouth(buttonGrid, 2);
	    
		ScrollPanel scrollPanel = new ScrollPanel(getSelectableList());
		this.add(scrollPanel);

	    
	    invokeGetAll();
		
	}
	
	public void invokeCreate(FinancialEvent fe) {
		
		status.setText(STATUS_WAITING);
				
		Clarkdm61_forecaster.forecasterService.create(fe, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				status.setText(STATUS_OK);
				
				// TODO: Optimize this block
				System.out.println("invokeCreate - re-initializing list");
				getSelectableList().clear();
				invokeGetAll();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				status.setText(STATUS_FAIL);
				caught.printStackTrace();
			}
		});
	}
	
	public void invokeUpdate(FinancialEvent fe) {
		
		status.setText(STATUS_WAITING);
				
		Clarkdm61_forecaster.forecasterService.update(fe, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				status.setText(STATUS_OK);
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				status.setText(STATUS_FAIL);
				caught.printStackTrace();
			}
		});
	}
	
	public void invokeDelete(FinancialEvent fe) {
		
		status.setText(STATUS_WAITING);
				
		Clarkdm61_forecaster.forecasterService.delete(fe.getId(), new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				status.setText(STATUS_OK);
				
				// TODO: Optimize this block
				System.out.println("invokeCreate - re-initializing list");
				getSelectableList().clear();
				invokeGetAll();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				status.setText(STATUS_FAIL);
				caught.printStackTrace();
			}
		});
	}
	
	public List<FinancialEvent> invokeGetAll() {
		status.setText(STATUS_WAITING);
		final List<FinancialEvent> list = new ArrayList<FinancialEvent>();
		
		Clarkdm61_forecaster.forecasterService.getAllEvents(new AsyncCallback<List<FinancialEvent>>() {
			
			@Override
			public void onFailure(Throwable caught) {
				status.setText(STATUS_FAIL);
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(List<FinancialEvent> result) {
				status.setText(STATUS_OK);
				list.clear();
				list.addAll(result);
				initEventList(list);
			}
			
		});
		return list;
	}
	
	private void initEventList(List<FinancialEvent> list) {
		System.out.println("initEventList() enter");
		setEventList(list);
		// find all
		int row = 0;
		
		// add to grid
		for (final FinancialEvent fe : list) {
			final SelectableCell selectablePanel = new SelectableCell(fe, ManageTab.this);
			getSelectableList().setWidget(row++, 0, selectablePanel);
		}
		System.out.println("initEventList() exit");
	}

	public FlexTable getSelectableList() {
		return selectableList;
	}


	public SelectableCell getSelectedCell() {
		return selectedCell;
	}

	public void setSelectedCell(SelectableCell selectedCell) {
		this.selectedCell = selectedCell;
	}

	public List<FinancialEvent> getEventList() {
		return eventList;
	}

	public void setEventList(List<FinancialEvent> eventList) {
		this.eventList = eventList;
	}

}
