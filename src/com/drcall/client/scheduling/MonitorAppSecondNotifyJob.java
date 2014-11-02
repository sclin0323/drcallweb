package com.drcall.client.scheduling;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


import com.drcall.db.dao.Appoint;
import com.drcall.db.dao.AppointDAO;
import com.drcall.db.dao.Member;
import com.drcall.db.dao.MemberDAO;
import com.drcall.db.dao.Schedule;
import com.drcall.db.dao.ScheduleDAO;

public class MonitorAppSecondNotifyJob {
	private static final Log log = LogFactory.getLog(MonitorAppSecondNotifyJob.class);
	
	protected static final Integer STATUS_APP_OK = 0; 
	protected static final Integer STATUS_FIRST_NOFIFY_OK = 1; 
	protected static final Integer STATUS_SECOND_NOTIFY_OK = 2; 
	protected static final Integer STATUS_PREPARE_THIRD_NOTIFY = 3; 
	protected static final Integer STATUS_THIRD_NOTIFY_OK = 4; 
	protected static final Integer STATUS_FINISHED = 5; 
	protected static final Integer STATUS_APP_CANCEL = 6; 
	protected static final Integer STATUS_APP_FAIL = 7; 
	
	private AppointDAO appointDAO;
	private ScheduleDAO scheduleDAO;
	private MemberDAO memberDAO;
	private JavaMailSender mailSender;
	
	public void setAppointDAO(AppointDAO appointDAO) {
		this.appointDAO = appointDAO;
	}
	
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	public void setScheduleDAO(ScheduleDAO scheduleDAO) {
		this.scheduleDAO = scheduleDAO;
	}

	public void executeMethod() {
		
	//	System.out.println("MonitorAppSecondNotifyJob..."+new Date());
		
		//log.info("MonitorAppSecondNotifyJob..."+new Date());
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, 1);
		
		List<Appoint> list = appointDAO.findByStatus(STATUS_FIRST_NOFIFY_OK);
		
		for(Appoint appoint : list){
			
			
			//log.info(appoint.getAppointId());
			
			Schedule schedule = scheduleDAO.findById(appoint.getSchedule().getScheduleId());
			Member menber = memberDAO.findById(appoint.getMember().getMemberId());
			//Date date = appointment.getSchedule().getDate();
			
			Calendar appCal = Calendar.getInstance();
			appCal.setTime(schedule.getDate());
			appCal.set(Calendar.HOUR_OF_DAY, 0);
			appCal.set(Calendar.MINUTE, 0);
			appCal.set(Calendar.SECOND, 0);
			
			if (isSameDay(schedule.getDate(), calendar.getTime())){
				
				MimeMessage message = mailSender.createMimeMessage();
				
				try {
					MimeMessageHelper helper = new MimeMessageHelper(message,true);

					//helper.setFrom("sclin0323@gmail.com");
					helper.setTo(menber.getEmail());
					helper.setSubject("Second Notification form Dr.Call");
					helper.setText("Contents..");

					log.info("send email message...");
					mailSender.send(message);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				
				appoint.setStatus(STATUS_SECOND_NOTIFY_OK);
				appointDAO.attachDirty(appoint);

			}			
		}
		
	}
	
	public boolean isSameDay(Date dateA,Date dateB) {
	    Calendar calDateA = Calendar.getInstance();
	    calDateA.setTime(dateA);

	    Calendar calDateB = Calendar.getInstance();
	    calDateB.setTime(dateB);
	    
	    return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
	            && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
	            &&  calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
	}
}
