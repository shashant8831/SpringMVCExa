package in.co.springmvc.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Format Input data.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */

public class Util {

	public static Date getDate(String date) {

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date1 = null;

		try {
			date1 = formatter.parse(date);
			System.out.println(date1);
			System.out.println(formatter.format(date1));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return date1;
	}

	public static String getDate(Date indate) {
		String dateString = null;
		SimpleDateFormat sdfr = new SimpleDateFormat("MM/dd/yyyy");
		/*
		 * you can also use DateFormat reference instead of SimpleDateFormat
		 * like this: DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
		 */
		try {
			dateString = sdfr.format(indate);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return dateString;
	}

	public static String convertStringToDate(Date indate) {
		String dateString = null;
		SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
		/*
		 * you can also use DateFormat reference instead of SimpleDateFormat
		 * like this: DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
		 */
		try {
			dateString = sdfr.format(indate);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return dateString;
	}

	public static String getCapital(String val) {
		String str = val;
		StringBuffer buffer = new StringBuffer();
		Matcher m = Pattern
				.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(
						str);
		while (m.find()) {
			m.appendReplacement(buffer, m.group(1).toUpperCase()
					+ m.group(2).toLowerCase());
		}
		return m.appendTail(buffer).toString();

	}

}
