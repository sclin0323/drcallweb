package com.drcall.client.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ImportTestData {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		String url ="jdbc:mysql://54.199.210.243:3306/drcalldb";
		String user="root";
		String password="1qaz2wsx";
		Connection conn = DriverManager.getConnection(url, user, password);
		
		String query = "INSERT INTO DOCTDOR(HOSPITAL_ID, DOCTDOR_ID, NAME, MAIN_DIVISION_ID, VICE_DIVISION_ID) "+" values (?, ?, ?, ?, ?)";
		
		PreparedStatement preparedStmt = conn.prepareStatement(query);
//		preparedStmt.setString(1,"3732023187");
//	    preparedStmt.setString(2,"D001");
//	    preparedStmt.setString(3,"林醫師");
//	    preparedStmt.setInt(4, 0);
//	    preparedStmt.setInt(5, 1);	    
//	    preparedStmt.execute();
//	    
//	    preparedStmt = conn.prepareStatement(query);
//		preparedStmt.setString(1,"3732023187");
//	    preparedStmt.setString(2,"D002");
//	    preparedStmt.setString(3,"陳醫師");
//	    preparedStmt.setInt(4, 0);
//	    preparedStmt.setInt(5, 1);	    
//	    preparedStmt.execute();
//	    
//	    preparedStmt = conn.prepareStatement(query);
//		preparedStmt.setString(1,"3732023187");
//	    preparedStmt.setString(2,"D003");
//	    preparedStmt.setString(3,"王醫師");
//	    preparedStmt.setInt(4, 0);
//	    preparedStmt.setInt(5, 1);	    
//	    preparedStmt.execute();
	    
	    System.out.println("ok...");
	    
	    String query2 = "INSERT INTO SCHEDULE(DOCTDOR_ID, DIVISION_ID, SHIFT, DATE, HOSPITAL_ID)"+" values (?, ?, ?, ?, ?)";
	    
	    preparedStmt = conn.prepareStatement(query);
		preparedStmt.setString(1,"D001");
	    preparedStmt.setInt(2,0);
	    preparedStmt.setInt(3,1);
	    preparedStmt.setString(4, "2014-3-11");
	    preparedStmt.setString(5, "3732023187");
	    preparedStmt.execute();
	    
	    preparedStmt = conn.prepareStatement(query);
		preparedStmt.setString(1,"D002");
	    preparedStmt.setInt(2,0);
	    preparedStmt.setInt(3,1);
	    preparedStmt.setString(4, "2014-3-11");
	    preparedStmt.setString(5, "3732023187");
	    preparedStmt.execute();
	    
	    
	    
	}

}
