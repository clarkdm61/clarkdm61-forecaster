package dmc.forecaster.shared;

import java.util.Date;

public class Utils {
	//private static final DateFormat ForecasterDateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT);
	public static String dateFormat(Date d) {
		if (d == null) return "*";
		return (d.getMonth() + 1) + "/" + d.getDate() + "/" + (d.getYear() + 1900);
	}
}