package com.drcall.client.test;

import com.drcall.client.util.AppointUtil;
import com.drcall.db.dao.Appoint;

public class TestAppoint {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		AppointUtil appointUtil = new AppointUtil();

		String result = appointUtil.requestAppoint();
		
		System.out.println(result);
		
	}

}
