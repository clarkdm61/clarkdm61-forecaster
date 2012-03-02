package com.example.vaadingae;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import dmc.forecaster.shared.FinancialEvent;

public class FinancialEventEditor extends Window {

	private VerticalLayout mainLayout;
	private FormLayout formLayout;
	private Form form;
	
	private FinancialEvent financialEvent = null;

	public FinancialEventEditor() {
		buildMainLayout();
		setContent(mainLayout);
	}


	private void buildMainLayout() {
		// the main layout and components will be created here
		// mainLayout->form->formLayout->item
		mainLayout = new VerticalLayout();
		formLayout = new FormLayout();
		
		form = new Form();
		form.setLayout(formLayout);
		
		mainLayout.addComponent(form);
		
		mainLayout.addComponent(new Button("Close", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				close();
			}
		}));
	}
	
	public void openForEdit(FinancialEvent financialEvent) {
		
		this.setModal(true);
		
		this.financialEvent = financialEvent;
		this.setCaption("Edit Event");
		BeanItem<FinancialEvent> model = new BeanItem<FinancialEvent>(financialEvent);
		
		form.setItemDataSource(model);
			
		
		form.setWidth("500px");
		form.setHeight("500px");
		
		mainLayout.setWidth("500px");
		mainLayout.setHeight("500px");
		
		formLayout.setWidth("500px");
		formLayout.setHeight("500px");

		this.setPositionX(200);
		this.setPositionY(100);
		VaadingaeApplication.getInstance().setMainWindow(this);

		
		this.setVisible(true);
	}
	
	protected void close() {
		VaadingaeApplication.getInstance().displayMainWindow();
	}


	public FinancialEvent getFinancialEvent() {
		return financialEvent;
	}

}
