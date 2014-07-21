package dmc.forecaster.vaadin;

import com.vaadin.addon.touchkit.ui.TabBarView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Container;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Widgetset("dmc.forecaster.Clarkdm61_forecaster")
@Theme("forecaster")
public class ForecasterUI extends UI {

	@Override
	protected void init(VaadinRequest request) {
		// create tab panel with Manager | Ledger | Graph
	      // Set the window or tab title
        getPage().setTitle("Hello Phone!");
       
        // Create the content root layout for the UI
        TabBarView mainView = new TabBarView();
        setContent(mainView);
       
        // Create a view - usually a regular class
        class MyView extends VerticalLayout {
            private static final long serialVersionUID = 3750679255269899661L;

            Table table = new Table("Planets", planetData());

            public MyView() {
                addComponent(new Label("This is a view"));
                table.setWidth("100%");
                table.setPageLength(table.size());
                addComponent(table);
                addComponent(new Button("Go"));
                setSpacing(true);
            }
        }
        mainView.addTab(new MyView(), "Planets");

        // Add some more sub-views
        mainView.addTab(new Label("Dummy"), "Map");
        mainView.addTab(new Label("Dummy"), "Settings");
		
		// separately create the UI/component for each tab
	}

	public Container planetData() {
		// TODO Auto-generated method stub
		Table table = new Table("This is my Table");

		/* Define the names and data types of columns.
		 * The "default value" parameter is meaningless here. */
		table.addContainerProperty("First Name", String.class,  null);
		table.addContainerProperty("Last Name",  String.class,  null);
		table.addContainerProperty("Year",       Integer.class, null);

		/* Add a few items in the table. */
		table.addItem(new Object[] {
		    "Nicolaus","Copernicus",new Integer(1473)}, new Integer(1));
		table.addItem(new Object[] {
		    "Tycho",   "Brahe",     new Integer(1546)}, new Integer(2));
		table.addItem(new Object[] {
		    "Giordano","Bruno",     new Integer(1548)}, new Integer(3));
		table.addItem(new Object[] {
		    "Galileo", "Galilei",   new Integer(1564)}, new Integer(4));
		table.addItem(new Object[] {
		    "Johannes","Kepler",    new Integer(1571)}, new Integer(5));
		table.addItem(new Object[] {
		    "Isaac",   "Newton",    new Integer(1643)}, new Integer(6));

		return table;
	}

}