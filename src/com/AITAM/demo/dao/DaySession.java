package com.AITAM.demo.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DaySession {
	public static String daySession(){
		 Calendar cal = Calendar.getInstance();
	    	cal.getTime();
	    	SimpleDateFormat sdf = new SimpleDateFormat("HH");
	    	String ses="null";
	    	int sesInt=Integer.parseInt( sdf.format(cal.getTime()) );
	    	if (sesInt<11){
	    		ses="Morning";
	    	}
	    	else if (sesInt<17){
	    		ses="Noon";
	    	}
	    	else {
	    		ses="Evening";
	    	}
	    	return ses;
	}

}
