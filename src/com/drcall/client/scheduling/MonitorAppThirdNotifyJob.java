package com.drcall.client.scheduling;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

public class MonitorAppThirdNotifyJob {
	private static final Log log = LogFactory.getLog(MonitorAppThirdNotifyJob.class);
	
	protected static final Integer STATUS_APP_OK = 0; 
	protected static final Integer STATUS_FIRST_NOFIFY_OK = 1; 
	protected static final Integer STATUS_SECOND_NOTIFY_OK = 2; 
	protected static final Integer STATUS_PREPARE_THIRD_NOTIFY = 3; 
	protected static final Integer STATUS_THIRD_NOTIFY_OK = 4; 
	protected static final Integer STATUS_FINISHED = 5; 
	protected static final Integer STATUS_APP_CANCEL = 6; 
	protected static final Integer STATUS_APP_FAIL = 7; 
	
	protected DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private static final Integer NOTIFY_BASE = 20;
	
	private AppointDAO appointDAO;
	private BasicDataSource dataSource;
	private String appointmentQuery;
	private JavaMailSender mailSender;
	private MemberDAO memberDAO;
	
	
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	public void setAppointDAO(AppointDAO appointDAO) {
		this.appointDAO = appointDAO;
	}
	
	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}
	

	public void setAppointmentQuery(String appointmentQuery) {
		this.appointmentQuery = appointmentQuery;
	}
	
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}


	public void executeMethod() {
		log.info("MonitorAppThirdNotifyJob....");
		
		// send notification 
		List<Appoint> appList = appointDAO.findByStatus(STATUS_PREPARE_THIRD_NOTIFY);
		for(Appoint app : appList){
			sendNotification(app);
		}
		
		
		// set prepare notification
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List results = jdbcTemplate.queryForList(appointmentQuery, new Object[]{STATUS_FIRST_NOFIFY_OK, STATUS_SECOND_NOTIFY_OK, df.format(new Date())});
		
		Iterator it = results.iterator(); 
		while(it.hasNext()) {  
		    Map map = (Map) it.next(); 
		                           
		    Integer shift = (Integer) map.get("SHIFT");
		    Long appointmentId = (Long) map.get("APPOINT_ID");
		    String email = (String) map.get("EMAIL");
		    Integer appNum = (Integer) map.get("APP_NUMBER");
		    
		  //  log.info(map.get("APPOINT_ID"));
		    
		    log.info("shift:"+shift+" isInShiftTime:"+isInShiftTime(shift));
		    
		    if(isInShiftTime(shift)){
		    	
		    	//log.info(map.get("APPOINT_ID"));
		    	Integer shiftNow = -1;
		    	if(shift == 0){                
		    		shiftNow = (Integer) map.get("MORNING_SHIFT_CALLINGNO");
		    	} else if(shift == 1){
		    		shiftNow = (Integer) map.get("AFTERNOON_SHIFT_CALLINGNO");
		    	} else if(shift == 2){
		    		shiftNow = (Integer) map.get("NIGHT_SHIFT_CALLINGNO");
		    	}
		    	
		    	// 1. 過號直接發
		    	// 2. 小於25號內直接發
		    	//appNum
		    	log.info("appNum:"+appNum+" shiftNow: "+shiftNow);
		    	
		    	if(appNum <= shiftNow ){
	    			// 確認準備發送第三通知 
		    		setPrepareNotification(appointmentId);
	    		} else if((appNum - NOTIFY_BASE) <= shiftNow) {
	    			// 確認準備發送第三通知 
	    			setPrepareNotification(appointmentId);
	    		} else {
	    			continue; // 還不用通知
	    		}
		    	
		    } 
		}  
	}


	private void sendNotification(Appoint app) {
		
		MimeMessage message = mailSender.createMimeMessage();
		Member member = memberDAO.findById(app.getMember().getMemberId());
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,true);

			//helper.setFrom("sclin0323@gmail.com");
			helper.setTo(member.getEmail());
			helper.setSubject("Dr. Call 診間訊息");
			
			
			String content = 
					"Hi " +app.getName()+" 先生/小姐"+
					"\t您好~~謝謝選用Dr. Call預約掛號通知服務。\n"+
					"\t在此通知您，您掛號的院所就診序號為 "+app.getAppNumber()+" 號，可準備前往就診。您可下載\n"+
					"Dr. Call專屬app，可了解即時診間訊息。\n"+
					"提醒您注意交通行車安全，並預祝就診順利、早日康復。\n\n"+
					"Dr. Call團隊 關心您";

			log.info("send email message...");
			
			helper.setText(content);
			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		app.setStatus(STATUS_THIRD_NOTIFY_OK);
		appointDAO.attachDirty(app);
		
	}

	private void setPrepareNotification(Long appointmentId) {

			
		Appoint appoint = appointDAO.findById(appointmentId);
		appoint.setStatus(STATUS_PREPARE_THIRD_NOTIFY);
		appointDAO.attachDirty(appoint);

	}


	private boolean isInShiftTime(Integer shift) {
		
		Date now = new Date();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		int year = calendar.get(Calendar.YEAR);
	    int month = calendar.get(Calendar.MONTH);
	    int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		calendar.set(year, month, day, 11, 30);
		Date noon = calendar.getTime();
		
		calendar.set(year, month, day, 17, 30);
		Date night = calendar.getTime();
		
		if(shift == 0){
			// 早班永不忽略
			return true;
		} else if(shift == 1){
			// 午班在中午11:30分以前忽略
			return now.after(noon); 
		} else if(shift == 2){
			// 晚班在下午 17:30分之前忽略
			return now.after(night);
		}
		
		return false;
	}

}
