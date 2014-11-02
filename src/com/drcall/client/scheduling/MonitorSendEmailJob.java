package com.drcall.client.scheduling;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.drcall.db.dao.Appoint;
import com.drcall.db.dao.AppointDAO;
import com.drcall.db.dao.Member;
import com.drcall.db.dao.MemberDAO;
import com.drcall.db.dao.Schedule;
import com.drcall.db.dao.ScheduleDAO;
import com.drcall.db.dao.SystemEmail;
import com.drcall.db.dao.SystemEmailDAO;

public class MonitorSendEmailJob {
	private static final Log log = LogFactory.getLog(MonitorSendEmailJob.class);
	
	public static final Integer STATUS_EMAIL_NOT_SEND = 0; 
	public static final Integer STATUS_EMAIL_SEND_OK = 1; 
	public static final Integer STATUS_EMAIL_SEND_FAIL = 2; 
	
	private SystemEmailDAO systemEmailDAO;
	private JavaMailSender mailSender;
	
	public void setSystemEmailDAO(SystemEmailDAO systemEmailDAO) {
		this.systemEmailDAO = systemEmailDAO;
	}
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void executeMethod() {
		List<SystemEmail> lists = systemEmailDAO.findByStatus(STATUS_EMAIL_NOT_SEND);

		for (SystemEmail systemEmail : lists){
			MimeMessage message = mailSender.createMimeMessage();
			
			try {
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setTo(systemEmail.getSendTo());
				helper.setSubject(systemEmail.getTitle());
				helper.setText(systemEmail.getText());
				mailSender.send(message);
				
				systemEmail.setStatus(STATUS_EMAIL_SEND_OK);
				systemEmail.setSendDate(new Timestamp(new Date().getTime()));
				
			} catch (MessagingException e) {
				e.printStackTrace();
				
				systemEmail.setStatus(STATUS_EMAIL_SEND_FAIL);
			}
			
			systemEmailDAO.attachDirty(systemEmail);
			
		}
		
		
	}




}
