package com.drcall.client.test;

import java.util.Random;

public class TestCode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.print(createIdentifyCode(500));

	}
	
	private static String createIdentifyCode(int length) {
		String code = new String();
		Random random = new Random();
		
		for(int i=0; i<length; i++){
			int index = random.nextInt(15);
			System.out.println(index);
			//code += Integer.toHexString(index);
		}
		
		
		
		return code;
	}

}
