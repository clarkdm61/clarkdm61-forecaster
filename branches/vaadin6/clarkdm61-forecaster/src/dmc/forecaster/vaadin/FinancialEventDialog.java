package dmc.forecaster.vaadin;

import java.util.Arrays;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MaskedTextField;
import com.vaadin.ui.NumericField;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Select;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import dmc.forecaster.shared.FinancialEvent;
import dmc.forecaster.shared.FinancialEventType;
import dmc.forecaster.shared.Reoccurrence;

/**
 * Pop-up dialog used to manage FinancialEvent data
 * This is not compatible with the visual editor.
 * @author dclark
 *
 */
public class FinancialEventDialog extends Window {

	private static final long serialVersionUID = 7948064932575370159L;
	private VerticalLayout mainLayout;
	private FormLayout formLayout;
	private Form form;
	private ManagerTab managerTab;
	
	private FinancialEvent financialEvent = null;
	
	/**
	 * Custom field factory to set drop-downs and radio options.
	 * The other fields are automatically generated by the DefaultFieldFactory.
	 */
	private class EditorFieldFactory extends DefaultFieldFactory {
		
		private static final long serialVersionUID = 2818849285165032473L;

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
				field.addItem(Reoccurrence.Weekly);
				field.addItem(Reoccurrence.BiWeekly);
				field.addItem(Reoccurrence.Monthly);
				field.addItem(Reoccurrence.TwiceYearly);
				field.addItem(Reoccurrence.Yearly);
				field.setRequired(true);
				field.setImmediate(true); // so the model is updated when a selection is made. 
				field.setNullSelectionAllowed(false); // removes empty selection
				field.addListener(new Property.ValueChangeListener() {
					@Override
					public void valueChange(ValueChangeEvent event) {
						// TODO: why does this fire 2x?
						Reoccurrence value = (Reoccurrence) event.getProperty().getValue();
						toggleEndDate(value);
					}
				});
				return field;
			} 
//				else if ("amountInt".equals(pid)) {
//				//NumericField field = new NumericField("Amount");
//				MaskedTextField field = new MaskedTextField("Amount", "######");
//				field.setNullRepresentation("");
//				field.setRequired(true);
//				return field;
//			}
			
			return super.createField(item, propertyId, uiContext);
		 }
	}
	
	private EditorFieldFactory editorFieldFactory = new EditorFieldFactory();

	/**
	 * Constructor
	 */
	public FinancialEventDialog(ManagerTab managerTab) {
		this.managerTab = managerTab;
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
		
		//form.setImmediate(true); // validate as data is entered.
		
		mainLayout.addComponent(form);
		
		HorizontalLayout buttonLayout = new HorizontalLayout();
		
		// create Save button and event handler
		buttonLayout.addComponent(new Button("Save", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if (financialEvent.getId() != null) {
					// making transient copy for JDO to work with.
					// TODO: is there a way around this?
					financialEvent = financialEvent.deepCopy();
				}
				try {
					// synch UI data with model data
					form.commit();
					// FIX for Issue 16
					// null out end date only after save - that way if "none" is selected by accident, the endDt isn't lost
					if (financialEvent.getReoccurrence().equals(Reoccurrence.None)) {
						financialEvent.setEndDt(null);
					}
				} catch (RuntimeException e) {
					return;
				}
				AppData.getForecasterService().create(financialEvent);
				managerTab.refreshManagerTable();
				close();
			}
		}));
		
		// create Cancel button and event handler
		buttonLayout.addComponent(new Button("Cancel", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				managerTab.refreshManagerTable();
				close();
			}
		}));
		
		mainLayout.addComponent(buttonLayout);

	}
	
	/**
	 * Open the dialog for the specified event
	 * @param financialEvent
	 */
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
				"amount" // accept decimals since i'm too lazy to mess with field-level validation
				}));
		
		Field f = form.getField("name");
		f.setRequired(true);
		
		formLayout.setMargin(true);
		form.setSizeUndefined();
		mainLayout.setSizeUndefined();
		formLayout.setSizeUndefined();

		this.setPositionX(50);
		this.setPositionY(50);
		
		// disable end date if reoccurrence is none.
		toggleEndDate(financialEvent.getReoccurrence());
	}
	
	/**
	 * Enable/disable End Date field based on selected Reoccurrence.
	 * @param value
	 */
	private void toggleEndDate(Reoccurrence value) {
		boolean enabled = Reoccurrence.None != value;
		Field field = form.getField("endDt");//.setEnabled(enabled);
		if (field != null) {
			field.setEnabled(enabled);
		} else {
			// field is null when form initializes and event is triggered.
		}
	}
	
	/**
	 * Handle close window request.
	 */
	protected void close() {
		getParent().removeWindow(this);
	}

}
