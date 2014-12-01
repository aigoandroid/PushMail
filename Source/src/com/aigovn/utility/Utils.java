package com.aigovn.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
	public static int convertStringDateToTimeStamp(String mDate, String format) {
		try {
			DateFormat formatter = new SimpleDateFormat(format, Locale.US);
			Date _date = formatter.parse(mDate);
			return (int) _date.getTime() / 1000;
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
