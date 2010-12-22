package dmc.forecaster.shared;

import java.util.Date;

public class Utils {
	//private static final DateFormat ForecasterDateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT);
	public static String dateFormat(Date d) {
		if (d == null) return "*";
		return (d.getMonth() + 1) + "/" + d.getDate() + "/" + (d.getYear() + 1900);
	}
	
	public static String currencyFormat(Double amt) {
		if (amt == 0d) {
			return "-";
		}
		return String.format("$%.2f", Math.abs(amt));
	}

}