package dmc.forecaster.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;

import dmc.forecaster.shared.FinancialEvent;
import dmc.forecaster.shared.Reoccurrence;

public class FinancialEventDialog extends DialogBox {
	private boolean newEvent;
	private FinancialEvent financialEvent;
	
	private TextBox txtName = new TextBox();
	private TextBox txtDescription = new TextBox();
	private RadioButton rbIncome = new RadioButton("type", "Income");
	private RadioButton rbExpense = new RadioButton("type", "Expense");
	private TextBox txtStartDt = new TextBox();
	private ListBox lbReoccurrence = new ListBox();
	private TextBox txtEndDt = new TextBox();
	private TextBox txtAmount = new TextBox();
	
	public FinancialEventDialog() {
		lbReoccurrence.addItem(Reoccurrence.None.toString());
		lbReoccurrence.addItem(Reoccurrence.Weekly.toString());
		lbReoccurrence.addItem(Reoccurrence.BiWeekly.toString());
		lbReoccurrence.addItem(Reoccurrence.Monthly.toString());
		lbReoccurrence.addItem(Reoccurrence.Yearly.toString());
		lbReoccurrence.addItem(Reoccurrence.BiYearly.toString());
		
		int rows =8, columns=2;
		Grid grid = new Grid(rows, columns);
		grid.setWidget(0, 0, new HTML("Name"));
		grid.setWidget(0, 1, txtName);

		grid.setWidget(1, 0, new HTML("Description"));
		grid.setWidget(1, 1, txtDescription);

		grid.setWidget(2, 0, rbIncome);
		grid.setWidget(2, 1, rbExpense);

		grid.setWidget(3, 0, new HTML("Start"));
		grid.setWidget(3, 1, txtStartDt);

		grid.setWidget(4, 0, new HTML("Reoccurrence"));
		grid.setWidget(4, 1, lbReoccurrence);

		grid.setWidget(5, 0, new HTML("End"));
		grid.setWidget(5, 1, txtEndDt);

		grid.setWidget(6, 0, new HTML("Amount"));
		grid.setWidget(6, 1, txtAmount);


		Button ok = new Button("OK");
		ok.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				FinancialEventDialog.this.hide();
				// callback to parent?
			}
		});
		Button cancel = new Button("Cancel");
		cancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				FinancialEventDialog.this.hide();
			}
		});
		grid.setWidget(7, 0, ok);
		grid.setWidget(7, 1, cancel);

		setWidget(grid);
	}

	public void openForNewEvent() {
		setNewEvent(true);
		setText("Create Event");
		rbExpense.setValue(true);
		lbReoccurrence.setSelectedIndex(0);
		this.show();
	}

	public void openForExistingEvent(FinancialEvent financialEvent) {
		setNewEvent(false);
		setText("Edit Event");
		this.show();
	}

	public boolean isNewEvent() {
		return newEvent;
	}

	public void setNewEvent(boolean newEvent) {
		this.newEvent = newEvent;
		setText(isNewEvent() ? "Create Event" : "Edit Event");
	}

	public FinancialEvent getFinancialEvent() {
		return financialEvent;
	}

	public void setFinancialEvent(FinancialEvent financialEvent) {
		this.financialEvent = financialEvent;
	}

}
