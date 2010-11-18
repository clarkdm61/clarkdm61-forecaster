package dmc.forecaster.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class ForecasterWicketApplication extends WebApplication {

	public ForecasterWicketApplication() {
	}

	@Override
	public Class<BasePage> getHomePage() {
		// TODO return page class
		return BasePage.class;
	}

}
