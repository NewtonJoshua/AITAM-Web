package com.AITAM.demo.dao;

import java.util.Calendar;
import java.util.Date;

public class WorkingDays {
	public int days(Date start, Date end) {

		// Ignore argument check
		Calendar c1 = Calendar.getInstance();

		c1.setTime(start);

		int w1 = c1.get(Calendar.DAY_OF_WEEK);

		c1.add(Calendar.DAY_OF_WEEK, -w1);

		Calendar c2 = Calendar.getInstance();

		c2.setTime(end);

		int w2 = c2.get(Calendar.DAY_OF_WEEK);

		c2.add(Calendar.DAY_OF_WEEK, -w2);

		// end Saturday to start Saturday
		long days = (c2.getTimeInMillis() - c1.getTimeInMillis()) / (1000 * 60 * 60 * 24);
		long daysWithoutWeekendDays = days - (days * 2 / 7);

		// Adjust w1 or w2 to 0 since we only want a count of *weekdays*
		// to add onto our daysWithoutWeekendDays
		if (w1 == Calendar.SUNDAY) {
			w1 = Calendar.MONDAY;
		}

		if (w2 == Calendar.SUNDAY) {
			w2 = Calendar.MONDAY;
		}

		return (int) (daysWithoutWeekendDays - w1 + w2 + 1);
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
