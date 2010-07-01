package dmc.forecaster.client;

import java.util.Date;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.AnnotatedTimeLine;

public class GraphTab extends DockLayoutPanel {
	static AnnotatedTimeLine lastTimeLine;
	
	public GraphTab() {
		super(Unit.EM);

		// button on top
		Button btnGenerate = new Button("Generate", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				generateGraph();

			}
		});
		this.addNorth(btnGenerate, 2);

	}

	public void generateGraph() {
		Runnable runnableCallBack = new Runnable() {
			public void run() {
				AnnotatedTimeLine timeLine = new AnnotatedTimeLine(createTable(),
						createOptions(), "110em", "40em");
				GraphTab.this.remove(lastTimeLine);
				GraphTab.this.add(timeLine);
				lastTimeLine=timeLine;
			}
		};

		VisualizationUtils.loadVisualizationApi(runnableCallBack,
				AnnotatedTimeLine.PACKAGE);
	}

	private AnnotatedTimeLine.Options createOptions() {
		// Options options = Options.
		AnnotatedTimeLine.Options options = AnnotatedTimeLine.Options.create();
		options.setDisplayAnnotations(true);
		return options;
	}

	private AbstractDataTable createTable() {
//		each cell is a tuple(row, column, value)
//		c0=date "Event Date"
//		c1,4=number "Balance"
//		c2,5=title "Event Title"
//		c3,6=extra "Income|Expense amount"

		DataTable data = DataTable.create();
		data.addColumn(ColumnType.DATE, "Event Date");
		data.addColumn(ColumnType.NUMBER, "Ledger Balance");
		data.addColumn(ColumnType.STRING, "Event Title");
		data.addColumn(ColumnType.STRING, "Income|Expense amount");
	
		// the fourth column is not used, it would be extra text
		data.addRows(LedgerTab.ledgerEntries.size());
		
		int row = 0;
		for (LedgerEntry entry : LedgerTab.ledgerEntries) {
			Date date = entry.getDate();
			date.setYear(date.getYear()+1); // bug in google's widget maybe?
			
			data.setValue(row, 0, date);
			data.setValue(row, 1, new Double(entry.getBalance()));
			data.setValue(row, 2, entry.getName());
			data.setValue(row, 3, formatAmountLabel(entry));
			row++;
		}
		
		return data;
	}
	public String formatAmountLabel(LedgerEntry entry) {
		if (entry.getIncomeAmount().equals("0")) {
			return "Expense:" + entry.getExpenseAmount();
		} else {
			return "Income: " + entry.getIncomeAmount();
		}
	}

}
