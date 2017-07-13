package org.xsnake.cloud.common.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static Date createDate(int year, int month, int date, int hourOfDay, int minute, int second){
		Calendar calendar = Calendar.getInstance();
		calendar.set(year,month,date,hourOfDay,minute,second);
		return calendar.getTime();
	}
	
}
