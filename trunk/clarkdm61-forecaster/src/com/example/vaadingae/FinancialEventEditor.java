package com.example.vaadingae;

import java.util.Arrays;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Select;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import dmc.forecaster.shared.FinancialEvent;
import dmc.forecaster.shared.FinancialEventType;
import dmc.forecaster.shared.Reoccurrence;

public class FinancialEventEditor extends Window {

	private VerticalLayout mainLayout;
	private FormLayout formLayout;
	private Form form;
	
	private FinancialEvent financialEvent = null;
	
	/**
	 * Customi field factory to set drop-downs and radio options
	 */
	private static class EditorFieldFactory extends DefaultFieldFactory {
		
		public EditorFieldFactory() {
			
		}
		
		 public Field createField(Item item, Object propertyId,
                 Component uiContext) {
			// Identify the fields by their Property ID.
			String pid = (String) propertyId;
			if ("type".equals(pid)) {
				OptionGroup field = new OptionGroup("Type");
				field.addItem(FinancialEventType.Income);
				field.addItem(FinancialEventType.Expense);
				field.setRequired(true);
				return field;
			} else if ("reoccurrence".equals(pid)) {
				Select field = new Select("Reoccurrence");
				field.addItem(Reoccurrence.None);
				field.addItem(Reoccurrence.BiWeekly);
				field.addItem(Reoccurrence.Monthly);
				field.addItem(Reoccurrence.TwiceYearly);
				field.addItem(Reoccurrence.Yearly);
				field.setRequired(true);
				return field;
			}
			
			return super.createField(item, propertyId, uiContext);
		 }
	}
	
	private static EditorFieldFactory editorFieldFactory = new EditorFieldFactory();

	/**
	 * Constructor
	 */
	public FinancialEventEditor() {
		buildMainLayout();
		setContent(mainLayout);
	}


	/**
	 * Build base layout. The rest is performed when model is set.
	 */
	private void buildMainLayout() {
		// the main layout and components will be created here
		// mainLayout->form->formLayout->item
		mainLayout = new VerticalLayout();
		formLayout = new FormLayout();
		
		form = new Form(formLayout, editorFieldFactory);
		
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
		
		form.setVisibleItemProperties(Arrays.asList(new String[]{
				"name",
				"description",
				"type",
				"startDt",
				"reoccurrence",
				"endDt",
				"amount"
				}));
		
		if (financialEvent.getReoccurrence().equals(Reoccurrence.None)) {
			Field f = form.getField("endDt");
			f.setEnabled(false);
		}
		
		formLayout.setMargin(true);
		form.setSizeUndefined();
		mainLayout.setSizeUndefined();
		formLayout.setSizeUndefined();

		this.setPositionX(50);
		this.setPositionY(50);

	}
	
	protected void close() {
		getParent().removeWindow(this);
	}


	public FinancialEvent getFinancialEvent() {
		return financialEvent;
	}

}
