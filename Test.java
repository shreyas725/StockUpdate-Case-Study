package exercise;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {
	public Date computeDate(String date) {
		String dateFromat = "MM/dd/yyyy";
		Date computedDate = null;
		boolean isValid = true;
		if (date != null && date.length() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
			sdf.setLenient(false);
			try {
				computedDate = sdf.parse(date);
			} catch (ParseException e) {
				isValid = false;
				e.printStackTrace();
			}
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(computedDate);
		if (isValid && !(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) && !(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY))
			return computedDate;
		else {
			computedDate = getPreviousWorkingDay(new Date());
		}

		return computedDate;
	}
	
	public Date computeDate() {
		Date computedDate = getPreviousWorkingDay(new Date());
		return computedDate;
	}
	
	public Date getPreviousWorkingDay(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        while (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
	        cal.add(Calendar.DAY_OF_MONTH, -1);
	    }

	    return cal.getTime();
	}

	public static void main(String args[]) {
		System.out.println(new Test().computeDate("10/16/2016"));
	}
}
