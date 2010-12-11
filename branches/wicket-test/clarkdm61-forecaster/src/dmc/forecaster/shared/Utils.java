package dmc.forecaster.shared;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	private static final DateFormat ForecasterDateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT);
	public static String dateFormat(Date d) {
		if (d == null) return "";
		return ForecasterDateFormat.format(d);
	}

}