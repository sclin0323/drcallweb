package com.drcall.client.scheduling;

import java.sql.Timestamp;
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
import com.drcall.db.dao.Division;
import com.drcall.db.dao.DivisionDAO;
import com.drcall.db.dao.Doctor;
import com.drcall.db.dao.DoctorDAO;
import com.drcall.db.dao.Hospital;
import com.drcall.db.dao.HospitalDAO;
import com.drcall.db.dao.Member;
import com.drcall.db.dao.MemberDAO;
import com.drcall.db.dao.Schedule;
import com.drcall.db.dao.ScheduleDAO;
import com.drcall.db.dao.SystemMessage;
import com.drcall.db.dao.SystemMessageDAO;

public class MonitorAppSecondNotifyJob extends BaseNotifyJob{
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
	private HospitalDAO hospitalDAO;
	private DoctorDAO doctorDAO;
	private DivisionDAO divisionDAO;

	public void setAppointDAO(AppointDAO appointDAO) {
		this.appointDAO = appointDAO;
	}
	
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	public void setScheduleDAO(ScheduleDAO scheduleDAO) {
		this.scheduleDAO = scheduleDAO;
	}

	public void setHospitalDAO(HospitalDAO hospitalDAO) {
		this.hospitalDAO = hospitalDAO;
	}
	public void setDoctorDAO(DoctorDAO doctorDAO) {
		this.doctorDAO = doctorDAO;
	}
	public void setDivisionDAO(DivisionDAO divisionDAO) {
		this.divisionDAO = divisionDAO;
	}
	
	public void executeMethod() {
		
	//	System.out.println("MonitorAppSecondNotifyJob..."+new Date());
		
		log.info("MonitorAppSecondNotifyJob..."+new Date());
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, 1);
		
		List<Appoint> list = appointDAO.findByStatus(STATUS_FIRST_NOFIFY_OK);
		
		for(Appoint appoint : list){
			
			
			log.info(appoint.getAppointId());
			
			Schedule schedule = scheduleDAO.findById(appoint.getSchedule().getScheduleId());
			Member member = memberDAO.findById(appoint.getMember().getMemberId());
			Hospital hospital = hospitalDAO.findById(schedule.getHospital().getHospitalId());
			Doctor doctor = doctorDAO.findById(schedule.getDoctor().getDoctorId());
			Division division = divisionDAO.findById(schedule.getDivision().getDivisionId());
			
			
			//Date date = appointment.getSchedule().getDate();
			
			Calendar appCal = Calendar.getInstance();
			appCal.setTime(appoint.getCrtTime());
			appCal.set(Calendar.HOUR_OF_DAY, 0);
			appCal.set(Calendar.MINUTE, 0);
			appCal.set(Calendar.SECOND, 0);
			
			if (isSameDay(schedule.getDate(), calendar.getTime())){
				
				//log.info("============");
				
				

					String email = member.getEmail();
					String name = member.getName();
					String patientName = appoint.getName();
					String hospitalName = hospital.getName();
					String divisionName = division.getCnName();
					String doctorName = doctor.getName();
					Date date = schedule.getDate();
					int shift = appoint.getShift();
					int appNumber = appoint.getAppNumber();
					
					String shiftName = null;
					if(shift == 0){
						shiftName = "早診";
					} else if(shift == 1){
						shiftName = "午診";
					} else if(shift == 2){
						shiftName = "晚診";
					}
					
					String subject = "Dr.Call 預約掛號成功通知信件";
					
					String content = 
							"Hi " +name+" 先生/小姐\n"+
							"\t您好~~謝謝選用Dr. Call預約掛號通知服務。\n"+
							"\t您日前於Dr. Call進行院所的預約掛號，您的掛號訊息如下。此信是由Dr. Call\n"+
							"發出，提醒您或您的家人朋友明日待就診，記得向工作單位請假與進行工作安排，以利安心就診，\n"+
							"並預祝平安健康。\n\n"+
							"掛號姓名："+patientName+"\n"+
							"掛號院所："+hospitalName+" 院所\n"+
							"掛號科別："+divisionName+"\n"+
							"掛號醫生："+doctorName+"\n"+
							"掛號時間："+date+" "+shiftName+"\n"+
							"掛號號碼："+appNumber+"\n\n"+
							"文末\t祝\t健康\n\nDr. Call 團隊 敬上";

				
				// 發送 Email
				this.saveNotifyEmail(subject, content, member.getEmail());
				
				// 發送簡訊
				this.saveSystemMessage(subject, content, appoint.getTel());
			
				
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
	    
	    //log.info("Year: "+calDateA.get(Calendar.YEAR)+" "+calDateB.get(Calendar.YEAR));
	   // log.info("Month: "+calDateA.get(Calendar.MONTH)+" "+calDateB.get(Calendar.MONTH));
	   // log.info("Day: "+calDateA.get(Calendar.DAY_OF_MONTH)+" "+calDateB.get(Calendar.DAY_OF_MONTH));
	    
	    return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
	            && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
	            &&  calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
	}
}
