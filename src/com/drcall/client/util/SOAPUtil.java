package com.drcall.client.util;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public class SOAPUtil {
	
	HashMap<String, String> map = new HashMap<String, String>();
	
	public SOAPUtil(String deptXML) throws SOAPException{

		SOAPMessage msg = formatSoapString(deptXML);
		SOAPBody body = msg.getSOAPBody();
		Iterator<SOAPElement> iterator = body.getChildElements();
		PrintBody(iterator, null);
	}
	
	private SOAPMessage formatSoapString(String soapString) {
		MessageFactory msgFactory;
		try {
			msgFactory = MessageFactory.newInstance();
			SOAPMessage reqMsg = msgFactory.createMessage(new MimeHeaders(),
					new ByteArrayInputStream(soapString.getBytes("UTF-8")));
			reqMsg.saveChanges();
			return reqMsg;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void PrintBody(Iterator<SOAPElement> iterator, String side) {
		while (iterator.hasNext()) {
			SOAPElement element = (SOAPElement) iterator.next();
//			System.out.println("Local Name:" + element.getLocalName());
//			System.out.println("Node Name:" + element.getNodeName());
//			System.out.println("Tag Name:" + element.getTagName());
//			System.out.println("Value:" + element.getValue());
			
			map.put(element.getTagName(), element.getValue());
			
			if (null == element.getValue()
					&& element.getChildElements().hasNext()) {
				PrintBody(element.getChildElements(), side + "-----");
			}
		}
	}
	
	public String getElementValue(String tagName){
		return map.get(tagName);
	}
	
	
}
