package de.lsn.resource.helper;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import de.lsn.resource.Constants;

public class DateConvenience {

	public static Calendar dateStringToCalendar(String dateAsString) {
		Date birthdayAsDate = null;
		try {
			birthdayAsDate = Constants.SDF_DE.parse(dateAsString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(birthdayAsDate);
		return c;
	}

	public static int yearsSinceGivenDate(String dateAsString) {
		return yearsSinceGivenDate(dateStringToCalendar(dateAsString));
	}

	private static int yearsSinceGivenDate(Calendar dateAsCalendar) {
		int year_now = Calendar.getInstance().get(Calendar.YEAR);
		int year_then = dateAsCalendar.get(Calendar.YEAR);
		return year_now - year_then;
	}
	
}
