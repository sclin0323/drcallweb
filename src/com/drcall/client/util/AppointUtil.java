package com.drcall.client.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import com.drcall.db.dao.Appoint;
import com.drcall.db.dao.Schedule;
import com.google.common.io.CharStreams;

public class AppointUtil {

	String id;
	String p_id;
	String datex;
	String apnx;
	String roomx;
	String doctor_id;
	String birth;

	public AppointUtil() {
		/*
		this.id = "23752100";
		this.p_id = "E123456778";
		this.datex = "1030217";
		this.apnx = "2";
		this.roomx = "1";
		this.doctor_id = "
";
		this.birth = "730323";
		*/
		
		this.id = "010102030";
		this.p_id = "E111111111";
		this.datex = "1030912";
		this.apnx = "3";
		this.roomx = "0";
		this.doctor_id = "123";
		this.birth = "929217";
	}
	
	public AppointUtil(String p_id, Appoint appoint, Schedule schedule) {
		this.id = schedule.getHospital().getHospitalId();
		this.p_id = p_id;
		this.datex = schedule.getCnDate();
		this.apnx = String.valueOf((appoint.getShift() + 1));
		this.roomx = schedule.getDivision().getDivisionId();
		this.doctor_id = schedule.getDoctor().getIdentityCode();

		String birth = appoint.getMember().getBirthYear() + ""
				+ appoint.getMember().getBirthYear() + ""
				+ appoint.getMember().getBirthDay();
		this.birth = birth;
	}
	
	

	public String requestAppoint() {
		
		String realXml = getAppointXml();
		
		System.out.println("realXml:");
		System.out.println(realXml);
		
		String urlString = "http://122.116.121.97:81/uv_server1/WebService1.asmx?wsdl";
		
		try{
			MessageFactory msgfac = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
			SOAPMessage message = msgfac.createMessage();

			SOAPPart sp = message.getSOAPPart();
			SOAPEnvelope se = sp.getEnvelope();

			sp.setMimeHeader("Content-Type", "application/soap+xml; charset=utf-8");
			se.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi",null);
			se.setAttributeNS("http://www.w3.org/2001/XMLSchema", "xsd", null);
			se.setAttributeNS("http://www.w3.org/2003/05/soap-envelope", "soap",null);
			
			URL url = new URL(urlString);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			
			
			InputStream stream = new ByteArrayInputStream(realXml.getBytes("UTF-8"));
			
			byte[] buf = new byte[(int) realXml.length()];// 用于存放文件数据的数组
			
			stream.read(buf);
			httpConn.setRequestProperty("Content-Length", String.valueOf(buf.length));
			httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			OutputStream out = httpConn.getOutputStream();
			out.write(buf);
			out.close();
			
			// RESPONSE
			String stringFromStream = CharStreams.toString(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
	        System.out.println(stringFromStream);

	        // PARSER
	        SOAPUtil soapUtil = new SOAPUtil(stringFromStream);
	        String result = soapUtil.getElementValue("uv_registerResult");

	        System.out.println(result);
	        
	        return result;
	        
		} catch(Exception e){
			e.printStackTrace();
			return "ERROR EXCEPTION";
		}

	}

	private String getAppointXml() {

		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://tempuri.org/\">"
				+ "<SOAP-ENV:Body>" 
				+ "<ns1:uv_register>"
				+ "<ns1:id>"+this.id+"</ns1:id>" 
				+ "<ns1:p_id>"+this.p_id+"</ns1:p_id>"
				+ "<ns1:datex>"+this.datex+"</ns1:datex>" 
				+ "<ns1:apnx>"+this.apnx+"</ns1:apnx>"
				+ "<ns1:roomx>"+this.roomx+"</ns1:roomx>"
				+ "<ns1:doctor_id>"+this.doctor_id+"</ns1:doctor_id>"
				+ "<ns1:brith>"+this.birth+"</ns1:brith>" 
				+ "</ns1:uv_register>"
				+ "</SOAP-ENV:Body>" 
				+ "</SOAP-ENV:Envelope>";

		return xml;
	}

}
