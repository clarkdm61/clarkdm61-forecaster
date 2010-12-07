package dmc.forecaster.wicket;

import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;

import dmc.forecaster.server.ForecasterServiceImpl;
import dmc.forecaster.shared.FinancialEvent;

public class ManagerPage extends BasePage {
	

	public ManagerPage() {
		System.out.println("def const");
		
		Form form = new Form("form") {
			protected void onSubmit() {
				System.out.println("click");
			}
		};
		
		//List<String> events = Arrays.asList(new String[]{"one","two"});
		ForecasterServiceImpl service = new ForecasterServiceImpl();
		List<FinancialEvent> events = service.getAllEvents();
//		ListView eventsView = new ListView("events", events) {
//			static int index = 0;
//			protected void populateItem(ListItem item) {
//				item.add(
//						new ListItem(index++, new PropertyModel(item.getModel(), "description"))
//						);
//			}
//		};
		
		//PropertyModel<FinancialEvent> eventModel = new PropertyModel<FinancialEvent>(events, "description");
		
		ListChoice eventlist = new ListChoice("eventlist",events, new IChoiceRenderer<FinancialEvent>() {

			@Override
			public Object getDisplayValue(FinancialEvent arg0) {
				return arg0.getLabelString();
			}

			@Override
			public String getIdValue(FinancialEvent arg0, int arg1) {
				return arg0.getId().toString();
			}
			
		});
		form.add(eventlist);
		
		add(form);
	}

	/**
	 * no-session constructor
	 * @param parameters
	 */
	public ManagerPage(PageParameters parameters) {
		//super(parameters);
		System.out.println("no-session const");
	}

}
