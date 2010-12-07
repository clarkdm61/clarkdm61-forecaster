package dmc.forecaster.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.IPageLink;
import org.apache.wicket.markup.html.link.PageLink;


public abstract class BasePage extends WebPage {
	
	public BasePage() {
		
		add(new Label("title",getTitle()));
		
	    add(new PageLink("managerPageLink", new IPageLink() {
	        public Page getPage() {
	            return new ManagerPage();
	        }
	        public Class getPageIdentity() {
	            return ManagerPage.class;
	        }
	    }));
	    
	    add(new PageLink("ledgerPageLink", new IPageLink() {
	        public Page getPage() {
	            return new LedgerPage();
	        }
	        public Class getPageIdentity() {
	            return LedgerPage.class;
	        }
	    }));
	}
	
	protected abstract String getTitle();
	
}
