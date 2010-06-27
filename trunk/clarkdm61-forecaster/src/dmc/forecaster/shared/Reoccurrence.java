package dmc.forecaster.shared;

import java.util.Date;

public enum Reoccurrence {
	None (0),
	Weekly (1),
	BiWeekly (2),
	Monthly (3),
	Yearly (4),
	BiYearly (5);
	
	private int index;
	private Reoccurrence(int index) {
		this.index = index;
	}
	public int getIndex() {
		return index;
	}
	/**
	 * Return the next date based on Reoccurrence
	 * @param priorDate
	 * @return
	 */
	public Date getNext(Date priorDate) {
		// TODO: implement Reoccurrence getNext
		return null;
	}
}
