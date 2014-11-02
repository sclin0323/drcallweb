package com.drcall.client.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestDate {
	protected DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	protected DateFormat tf = new SimpleDateFormat("HH:mm:ss");
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		  Calendar calendar = Calendar.getInstance();
		    calendar.set(Calendar.YEAR, 2014);
		    calendar.set(Calendar.MONTH, 4-1);
		    calendar.set(Calendar.DAY_OF_MONTH, 27);
		    
	//	    System.out.println(calendar.); 
		   
			// dtf.parse(calendar.getMaximum(Calendar.MINUTE));
	}

}
