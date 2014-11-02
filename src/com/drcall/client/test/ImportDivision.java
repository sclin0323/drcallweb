package com.drcall.client.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTransactionManager;

import com.drcall.client.web.AppointController;

public class ImportDivision {
	private static final Log log = LogFactory.getLog(ImportDivision.class);
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		String url ="jdbc:mysql://172.16.40.210:3306/drcalldb";
		String user="root";
		String password="1qaz2wsx";
		Connection conn = DriverManager.getConnection(url, user, password);
		
		String query = " insert into DIVISION(DIVISION_ID, CN_NAME, ACTIVE)"+" values (?, ?, ?)";
		
		File fileDir = new File("C:\\Users\\SamLin\\Google 雲端硬碟\\Project\\drcall\\devistion_list.txt");
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "utf8"));
 
		String str;
 
		while ((str = in.readLine()) != null) {
			String[] tmp = str.split("\t");
			//System.out.println(str+"       "+tmp.length);
			
			String ID = tmp[0];
			String NAME = tmp[1];

			
			log.info(tmp[0]+"_"+tmp[1]);
		    
		    PreparedStatement preparedStmt = conn.prepareStatement(query);
		    preparedStmt.setString(1,ID);
		      preparedStmt.setString(2,NAME);

		      preparedStmt.setBoolean(3,false);
		    
		      try {
				preparedStmt.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		      
		}
 
        in.close();

	}

}
