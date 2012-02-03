package dmc.forecaster.shared;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class Util {
	//private static final DateTimeFormat ForecasterDateFormat = DateTimeFormat.getFormat("MM/dd/yyyy");
	public static String dateFormat(Date d) {
		if (d == null) return "";
		return d.toString();
	}
}
