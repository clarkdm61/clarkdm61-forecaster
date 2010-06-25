package dmc.forecaster.client;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;

import dmc.forecaster.shared.FinancialEvent;
import dmc.forecaster.shared.FinancialEventType;
import dmc.forecaster.shared.Reoccurrence;

public class FinancialEventDialog extends DialogBox {
	private ManageTab manageTab = null;
	
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
	
	public FinancialEventDialog(ManageTab manageTab) {
		this.manageTab = manageTab;
		
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
				// validate
				
				FinancialEventDialog.this.hide();
				// callback to parent?
				updateFinancialEventFromScreen();
				FinancialEventDialog.this.manageTab.invokeCreate(getFinancialEvent());
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
		setFinancialEvent(new FinancialEvent());
		setNewEvent(true);
		setText("Create Event");
		txtName.setText("");
		txtDescription.setText("");
		txtStartDt.setText(DateTimeFormat.getShortDateFormat().format(new java.util.Date()));
		txtEndDt.setText("");
		txtEndDt.setEnabled(false);
		rbExpense.setValue(true);
		lbReoccurrence.setSelectedIndex(0);
		txtAmount.setText("");
		this.show();
	}

	public void openForExistingEvent(FinancialEvent financialEvent) {
		setNewEvent(false);
		setText("Edit Event");
		setFinancialEvent(financialEvent);
		
		txtName.setText(financialEvent.getName());
		txtDescription.setText(financialEvent.getDescription());
		txtAmount.setText(financialEvent.getAmount().toString());
		txtStartDt.setText(DateTimeFormat.getShortDateFormat().format(financialEvent.getStartDt()));
		Date endDt = financialEvent.getEndDt();
		String szEndDt = endDt==null ? "" : DateTimeFormat.getShortDateFormat().format(endDt);;
		txtEndDt.setText(szEndDt);
		if (financialEvent.getType() == FinancialEventType.Income) {
			rbIncome.setValue(true);
		} else {
			rbExpense.setValue(true);
		}
				 
				
		lbReoccurrence.setSelectedIndex(financialEvent.getReoccurrence().getIndex());
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
	
	/**
	 * copy field values into local model
	 */
	private void updateFinancialEventFromScreen() {
		getFinancialEvent().setName(txtName.getText());
		getFinancialEvent().setDescription(txtDescription.getText());
		getFinancialEvent().setReoccurrence(Reoccurrence.valueOf(lbReoccurrence.getValue(lbReoccurrence.getSelectedIndex())));
		FinancialEventType type = rbIncome.getValue() ? FinancialEventType.Income : FinancialEventType.Expense;
		getFinancialEvent().setType(type);
		Double amount = new Double(txtAmount.getText());
		getFinancialEvent().setAmount(amount);
		// dates
		getFinancialEvent().setStartDt(new java.util.Date(txtStartDt.getText()));
		if (txtEndDt.getText().length()>1)
			getFinancialEvent().setEndDt(new java.util.Date(txtEndDt.getText()));
	}

}
