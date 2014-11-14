package com.drcall.client.scheduling;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.drcall.client.web.AppointController;
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

public class MonitorAppFirstNotifyJob extends BaseNotifyJob{
	private static final Log log = LogFactory.getLog(MonitorAppFirstNotifyJob.class);
	
	protected static final Integer STATUS_APP_OK = 0; 
	protected static final Integer STATUS_FIRST_NOFIFY_OK = 1; 
	protected static final Integer STATUS_SECOND_NOTIFY_OK = 2; 
	protected static final Integer STATUS_PREPARE_THIRD_NOTIFY = 3; 
	protected static final Integer STATUS_THIRD_NOTIFY_OK = 4; 
	protected static final Integer STATUS_FINISHED = 5; 
	protected static final Integer STATUS_APP_CANCEL = 6; 
	protected static final Integer STATUS_APP_FAIL = 7; 
	
	private AppointDAO appointDAO;
	private MemberDAO memberDAO;
	private ScheduleDAO scheduleDAO;
	private HospitalDAO hospitalDAO;
	private DoctorDAO doctorDAO;
	private DivisionDAO divisionDAO;

	public void setHospitalDAO(HospitalDAO hospitalDAO) {
		this.hospitalDAO = hospitalDAO;
	}
	public void setDoctorDAO(DoctorDAO doctorDAO) {
		this.doctorDAO = doctorDAO;
	}
	public void setDivisionDAO(DivisionDAO divisionDAO) {
		this.divisionDAO = divisionDAO;
	}
	public void setScheduleDAO(ScheduleDAO scheduleDAO) {
		this.scheduleDAO = scheduleDAO;
	}
	public void setAppointDAO(AppointDAO appointDAO) {
		this.appointDAO = appointDAO;
	}
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	public void executeMethod() {
		List<Appoint> list = appointDAO.findByStatus(STATUS_APP_OK);
		
		for(Appoint appoint : list){

			Member member = memberDAO.findById(appoint.getMember().getMemberId());
			Schedule schedule = scheduleDAO.findById(appoint.getSchedule().getScheduleId());
			Hospital hospital = hospitalDAO.findById(schedule.getHospital().getHospitalId());
			Doctor doctor = doctorDAO.findById(schedule.getDoctor().getDoctorId());
			Division division = divisionDAO.findById(schedule.getDivision().getDivisionId());
			
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
					name+" 您好:\n"+
					"\t謝謝您由Dr. Call進行掛號，此封信是通知您已完成掛號，相關掛號訊息如下：\n\n"+
					"掛號姓名："+patientName+"\n"+
					"掛號院所："+hospitalName+" 院所\n"+
					"掛號科別："+divisionName+"\n"+
					"掛號醫生："+doctorName+"\n"+
					"掛號時間："+date+" "+shiftName+"\n"+
					"掛號號碼："+appNumber+"\n"+
					"將於到診前主動發出訊息通知前往就診，謝謝。"+"\n\n"+
					"文末\t祝\t健康\n\nDr. Call 團隊 敬上";
			
			
			// 發送 Email
			this.saveNotifyEmail(subject, content, email);
			
			// 發送簡訊
			this.saveSystemMessage(subject, content, appoint.getTel());
			
			// 更新狀態
			appoint.setStatus(STATUS_FIRST_NOFIFY_OK);
	
			appointDAO.attachDirty(appoint);
			
		}
		
		
	}
}
