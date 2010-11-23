package dmc.forecaster.wicket;

import org.apache.wicket.protocol.http.HttpSessionStore;
import org.apache.wicket.protocol.http.WebApplication;

public class ForecasterWicketApplication extends WebApplication {

	public ForecasterWicketApplication() {
	}

	@Override
	public Class<ManagerPage> getHomePage() {
		// TODO return page class
		return ManagerPage.class;
	}
	
    @Override
    protected void init()
    {
        super.init();

        // for Google App Engine
        getResourceSettings().setResourcePollFrequency(null);

        // Enable Guice for field injection on Wicket pages.  Unfortunately, constructor injection into
        // pages is not supported.  Supplying ServletModule is optional; it enables usage of @RequestScoped and
        // @SessionScoped, which may not be useful for Wicket applications because the WebPage instances are
        // already stored in session, with their dependencies injected once per session.
       // addComponentInstantiationListener(new GuiceComponentInjector(this, new GuiceModule()));
//        addComponentInstantiationListener(new GuiceComponentInjector(this, new ServletModule(), new GuiceModule()));
    }
    
    @Override
    public HttpSessionStore newSessionStore(){
       return new HttpSessionStore(this);
    }


}
