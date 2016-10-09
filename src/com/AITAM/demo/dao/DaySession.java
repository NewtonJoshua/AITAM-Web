package com.AITAM.demo.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DaySession {
	public static String daySession() {
		Calendar cal = Calendar.getInstance();

		cal.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat(Messages.getString("DaySession.0")); //$NON-NLS-1$
		String ses = Messages.getString("DaySession.1"); //$NON-NLS-1$
		int sesInt = Integer.parseInt(sdf.format(cal.getTime()));

		if (sesInt < 11) {
			ses = Messages.getString("DaySession.2"); //$NON-NLS-1$
		} else if (sesInt < 17) {
			ses = Messages.getString("DaySession.3"); //$NON-NLS-1$
		} else {
			ses = Messages.getString("DaySession.4"); //$NON-NLS-1$
		}

		return ses;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
