package dmc.forecaster.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;

import dmc.forecaster.shared.FinancialEvent;

public class SelectableCell extends Label {
	private FinancialEvent financialEvent = null;

	public SelectableCell(FinancialEvent financialEvent, final ManageTab manageTab) {
		setFinancialEvent(financialEvent);
		setText(financialEvent.getLabelString());
		
		if (financialEvent.getEndDt() != null && financialEvent.getEndDt().getTime() < System.currentTimeMillis()) {
			this.addStyleName("oldItem");
		}
		
		addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (manageTab.getSelectedCell() == SelectableCell.this) {
					// same one - just deselect
					manageTab.getSelectedCell().removeStyleName("selectedItem");
					manageTab.setSelectedCell(null);
				} else {
					// change selection 
					SelectableCell.this.addStyleName("selectedItem");
					if (manageTab.getSelectedCell()!=null) manageTab.getSelectedCell().removeStyleName("selectedItem");
					manageTab.setSelectedCell(SelectableCell.this);
				}
			}
		});
	}


	public FinancialEvent getFinancialEvent() {
		return financialEvent;
	}


	public void setFinancialEvent(FinancialEvent financialEvent) {
		this.financialEvent = financialEvent;
	}

}
