package com.drcall.client.scheduling;

import java.sql.Timestamp;
import java.util.Date;

import com.drcall.db.dao.SystemEmail;
import com.drcall.db.dao.SystemEmailDAO;
import com.drcall.db.dao.SystemMessage;
import com.drcall.db.dao.SystemMessageDAO;

public class BaseNotifyJob {
	
	private SystemMessageDAO systemMessageDAO;
	private SystemEmailDAO systemEmailDAO;
	
	public void setSystemMessageDAO(SystemMessageDAO systemMessageDAO) {
		this.systemMessageDAO = systemMessageDAO;
	}
	
	public void setSystemEmailDAO(SystemEmailDAO systemEmailDAO) {
		this.systemEmailDAO = systemEmailDAO;
	}

	public SystemMessageDAO getSystemMessageDAO() {
		return systemMessageDAO;
	}

	public SystemEmailDAO getSystemEmailDAO() {
		return systemEmailDAO;
	}

	public void saveNotifyEmail(String title, String content, String sendTo){
		
		SystemEmail email = new SystemEmail();
		
		email.setCrtDate(new Timestamp(new Date().getTime()));
		email.setSendTo(sendTo);
		email.setStatus(MonitorSendEmailJob.STATUS_EMAIL_NOT_SEND);
		email.setText(content);
		email.setTitle(title);
		
		systemEmailDAO.save(email);
	}
	
	public void saveSystemMessage(String subject, String content, String mobile){
		
		System.out.println("subject:"+subject);
		System.out.println("content:"+content);
		System.out.println("mobile:"+mobile);
		
		SystemMessage message = new SystemMessage();
		
		message.setContent(content);
		message.setCrtDate(new Timestamp(new Date().getTime()));
		message.setMobile(mobile);
		message.setStatus(MonitorSendMessageJob.STATUS_MESSAGE_NOT_SEND);
		message.setSubject(subject);
		
		systemMessageDAO.save(message);
		
	
	}
	
}
