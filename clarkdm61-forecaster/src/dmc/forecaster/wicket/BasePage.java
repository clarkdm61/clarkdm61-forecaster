package dmc.forecaster.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;


public abstract class BasePage extends WebPage {
	
	public BasePage() {
		add(new Label("title","Forecaster on Wicket"));
		//add(new DebugBar("debug"));
	}
	
}
