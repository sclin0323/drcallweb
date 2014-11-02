package com.drcall.client.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestRecommendCode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DateFormat dtf = new SimpleDateFormat("yyMMddHHmmssSS");

		System.out.println(dtf.format(new Date()));
		
	}

}
