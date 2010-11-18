package dmc.forecaster.wicket;

import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEventSink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class BasePage extends WebPage {

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public BasePage(final PageParameters parameters) {

        // Add the simplest type of label
        add(new Label("message", "If you see this message wicket is properly configured and running"));

        // TODO Add your page's components here
    }

//	@Override
//	public <T> void send(IEventSink arg0, Broadcast arg1, T arg2) {
//		// TODO Auto-generated method stub
//
//	}

}
