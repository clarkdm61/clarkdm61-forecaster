package dmc.forecaster.shared;

import java.util.Date;


public class Util {
	//private static final DateTimeFormat ForecasterDateFormat = DateTimeFormat.getFormat("MM/dd/yyyy");
	/**
	 * provide a generic formatter that works with both GWT and other environments
	 */
	public static String dateFormat(Date d) {
		
		if (d == null) return "";
		int mm = d.getMonth() + 1;
		int dd = d.getDate();
		int yy = d.getYear() + 1900;
		return mm+"/"+dd+"/"+yy;
	}
}
