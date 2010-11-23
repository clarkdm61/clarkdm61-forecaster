package dmc.forecaster.wicket;

import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListChoice;
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
		
		PropertyModel<FinancialEvent> eventModel = new PropertyModel<FinancialEvent>(events, "description");
		
		ListChoice eventlist = new ListChoice("eventlist",eventModel);
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
