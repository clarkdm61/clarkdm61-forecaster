package dmc.forecaster.wicket;

import java.util.List;

import org.apache.wicket.IClusterable;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.model.CompoundPropertyModel;

import dmc.forecaster.server.ForecasterServiceImpl;
import dmc.forecaster.shared.FinancialEvent;

public class ManagerPage extends BasePage {
	public enum LocalAction {ADD, EDIT, DELETE}
	public LocalAction localAction = null;
	
	/**
	 * Constructor
	 */
	public ManagerPage() {
		
		ForecasterServiceImpl service = new ForecasterServiceImpl();
		final List<FinancialEvent> events = service.getAllEvents();
		
		// model wrapper layer 1
		final IClusterable input = new IClusterable() {
			public FinancialEvent event = events.get(0);
			
			public String toString() {
				return event.getLabelString();
			}
		};
		// wrapping model again
		setDefaultModel(new CompoundPropertyModel<IClusterable>(input));
		
		// construct the RadioChoice list of FinancialEvents - events
		// some magic ties the "event" to input.event, and some other magic enables input.event=events.get(0) to work
		RadioChoice<List> eventlist = new RadioChoice("event",events, new IChoiceRenderer<FinancialEvent>() {
			@Override
			public Object getDisplayValue(FinancialEvent arg0) {
				return arg0.getLabelString();
			}

			@Override
			public String getIdValue(FinancialEvent arg0, int arg1) {
				return arg0.getId().toString();
			}
			
		});

		
		Form form = new Form("form") {
			@Override
			protected void onSubmit() {
				System.out.println( localAction + " : " + input);
				switch (localAction) {
					case ADD: openForAdd(); break;
					case EDIT: openForEdit(events.get(0)); break;
					case DELETE: confirmDelete(events.get(0)); break;
				}
			}
		};
		
		// add
		form.add (new Button("btnAdd") {
			public void onSubmit() {
				localAction = LocalAction.ADD;
				System.out.println( "add" );
			}
		});
		// edit
		form.add( new Button("btnEdit") {
			public void onSubmit() {
				localAction = LocalAction.EDIT;
				System.out.println("edit");
			}
		});
		
		// delete
		form.add (new Button("btnDelete") {
			public void onSubmit() {
				localAction = LocalAction.DELETE;
				System.out.println("delete");
			}
		});

		form.add(eventlist);
		add(form);
	}

	protected void openForAdd() {
		// TODO Auto-generated method stub
		
	}

	protected void confirmDelete(FinancialEvent financialEvent) {
		// TODO Auto-generated method stub
		
	}

	protected void openForEdit(FinancialEvent financialEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getTitle() {
		return "Manager Page";
	}

}
