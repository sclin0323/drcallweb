package com.drcall.client.scheduling;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.drcall.client.util.SMSHttpServiceUtil;
import com.drcall.db.dao.SystemMessage;
import com.drcall.db.dao.SystemMessageDAO;

public class MonitorSendMessageJob {
	private static final Log log = LogFactory.getLog(MonitorSendMessageJob.class);
	
	public static final Integer STATUS_MESSAGE_NOT_SEND = 0; 
	public static final Integer STATUS_MESSAGE_SEND_OK = 1; 
	public static final Integer STATUS_MESSAGE_SEND_FAIL = 2; 
	
	private SystemMessageDAO systemMessageDAO;
	
	public void setSystemMessageDAO(SystemMessageDAO systemMessageDAO) {
		this.systemMessageDAO = systemMessageDAO;
	}


	public void executeMethod() {
		List<SystemMessage> lists = systemMessageDAO.findByStatus(STATUS_MESSAGE_NOT_SEND);

		for (SystemMessage systemMessage : lists){
			
			
			
			// TODO Auto-generated method stub
			SMSHttpServiceUtil sms=new SMSHttpServiceUtil();
			String userID="0911399827";	//帳號
			String password="n7qc";	//密碼
			String subject=systemMessage.getSubject();	//簡訊主旨，主旨不會隨著簡訊內容發送出去。用以註記本次發送之用途。可傳入空字串。
			String content=systemMessage.getContent();	//簡訊發送內容
			String mobile=systemMessage.getMobile();	//接收人之手機號碼。格式為: +886912345678或09123456789。多筆接收人時，請以半形逗點隔開( , )，如0912345678,0922333444。
			String sendTime="";	//簡訊預定發送時間。-立即發送：請傳入空字串。-預約發送：請傳入預計發送時間，若傳送時間小於系統接單時間，將不予傳送。格式為YYYYMMDDhhmnss；例如:預約2009/01/31 15:30:00發送，則傳入20090131153000。若傳遞時間已逾現在之時間，將立即發送。
			
			if(sms.getCredit(userID, password)){
				
				System.out.println(new StringBuffer("取得餘額成功，餘額：").append(String.valueOf(sms.getCreditValue())).toString());
			}else{
				
				System.out.println(new StringBuffer("取得餘額失敗，失敗原因：").append(sms.getProcessMsg()).toString());
			}
			
			System.out.println("");
			
			if(sms.sendSMS(userID, password, subject, content, mobile, sendTime)){
				
				systemMessage.setStatus(STATUS_MESSAGE_SEND_OK);	
				System.out.println(new StringBuffer("發送簡訊成功，餘額：").append(String.valueOf(sms.getCreditValue())).append("，簡訊批號：").append(sms.getBatchID()).toString());
			}else{
				
				systemMessage.setStatus(STATUS_MESSAGE_SEND_FAIL);
				System.out.println(new StringBuffer("發送簡訊失敗，失敗原因：").append(sms.getProcessMsg()).toString());
			}
			
			
			systemMessageDAO.attachDirty(systemMessage);
			
		}
		
		
	}




}
