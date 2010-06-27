package dmc.forecaster.shared;

import java.util.Date;

public enum Reoccurrence {
	None (0),
	Weekly (1),
	BiWeekly (2),
	Monthly (3),
	Yearly (4),
	TwiceYearly (5); /*warning: twice yearly dates are only approximations*/
	
	private int index;
	private static long SECOND = 1000;
	private static long MINUTE = SECOND*60;
	private static long HOUR = MINUTE*60;
	private static long DAY = HOUR*24;
	private static long WEEK = DAY*7;
	
	private Reoccurrence(int index) {
		this.index = index;
	}
	public int getIndex() {
		return index;
	}
	/**
	 * Return the next date based on Reoccurrence
	 * TODO: Try using GWT's Joda time to calculate Reoccurrence nextDate
	 * @param priorDate
	 * @return
	 */
	public Date getNext(Date priorDate) {
		Date nextDate = new Date(priorDate.getTime());
		int month;
		switch (this) {
			case None: /* do nothing */ ; break;
			case Weekly: 
				nextDate.setTime(nextDate.getTime() + WEEK);
				break;
			case BiWeekly:
				nextDate.setTime(nextDate.getTime() + WEEK*2);
				break;				
			case Monthly: 
				month = nextDate.getMonth()+1;
				if (month < 12) {
					nextDate.setMonth(month);
				} else {
					nextDate.setMonth(0);
					nextDate.setYear(nextDate.getYear()+1);
				}
				break;
			case Yearly:
				nextDate.setYear(nextDate.getYear()+1);
				break;
			case TwiceYearly:
				nextDate.setTime(nextDate.getTime() + DAY*183); // appx 6 moths

		}
		return nextDate;
	}
}
