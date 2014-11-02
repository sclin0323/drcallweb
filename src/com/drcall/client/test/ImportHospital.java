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

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTransactionManager;

import com.drcall.client.web.AppointController;

public class ImportHospital {
	private static final Log log = LogFactory.getLog(ImportHospital.class);
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

		String query = " insert into HOSPITAL(HOSPITAL_ID, NAME, ADDRESS, CITY, PHONE, ACTIVE, IS_CONTRACT)"+" values (?, ?, ?, ?, ?, ?, ?)";
		
		//BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\SamLin\\Google 雲端硬碟\\Project\\drcall\\database_import\\hospital_10.csv"));
		
		//InputStreamReader isr = new InputStreamReader(new FileInputStream("C:\\Users\\SamLin\\Google 雲端硬碟\\Project\\drcall\\database_import\\hospital_10.txt"), "UTF-8");
		//BufferedReader br = new BufferedReader(isr);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\SamLin\\Google 雲端硬碟\\Project\\drcall\\database_import\\hospital_list_all.txt"), "utf8"));
		br.readLine();
		String line = "";
		while ((line = br.readLine()) != null) {
			
			
			try{
				String[] tmp = line.split(",");
				
				String HOSPITAL_ID = tmp[0];
				String NAME = tmp[1];
				String ADDRESS = tmp[2];
				String CITY = tmp[3];
				String PHONE = tmp[4]+tmp[5];
				
				System.out.println(HOSPITAL_ID+"\t"+NAME+"\t"+ADDRESS+"\t"+CITY);
				
			      PreparedStatement preparedStmt = conn.prepareStatement(query);
			      preparedStmt.setString(1,HOSPITAL_ID);
			      preparedStmt.setString(2,NAME);
			      preparedStmt.setString(3,ADDRESS);
			      preparedStmt.setString(4,CITY);
			      preparedStmt.setString(5,PHONE);
			      
			      preparedStmt.setBoolean(6,false);
			    
			      preparedStmt.setBoolean(7,false);
			      preparedStmt.execute();
			} catch(Exception e){
				e.printStackTrace();
			}
			
			
			
		}
 
		


	}

}
