package dmc.forecaster.client;

import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;

import dmc.forecaster.server.FinancialEventDAO;
import dmc.forecaster.shared.FinancialEvent;

public class ManageTab extends DockLayoutPanel {

	public ManageTab() {
		super(Unit.EM);
		
		// create buttons
	    Button btnNew = new Button("New", new ClickHandler() {
	        public void onClick(ClickEvent event) {
	          Window.alert("Show data entry pop-up");
	        }
	    });
	    Button btnEdit = new Button("Edit", new ClickHandler() {
	        public void onClick(ClickEvent event) {
	          Window.alert("Show data entry pop-up");
	        }
	    });
	    Button btnDelete = new Button("Delete", new ClickHandler() {
	        public void onClick(ClickEvent event) {
	          Window.alert("Show delete confirm pop-up");
	        }
	    });
	    FlowPanel pnlButtons = new FlowPanel();
	    pnlButtons.add(btnNew);
	    pnlButtons.add(btnEdit);
	    pnlButtons.add(btnDelete);
	    this.addSouth(pnlButtons, 2);
		
		// create pop-up window for create and update
		
		// get financial events
	    FinancialEventDAO dao = new FinancialEventDAO();
	    List<FinancialEvent> list = dao.findAll();
		
		
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

}
