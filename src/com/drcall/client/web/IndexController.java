package com.drcall.client.web;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.drcall.client.command.AppointCommand;
import com.drcall.client.command.AutoCompletedCommand;
import com.drcall.client.command.FreeExperienceCommand;
import com.drcall.client.command.MessageCommand;
import com.drcall.client.command.ScheduleCommand;
import com.drcall.client.util.DrcallScheduleDay;
import com.drcall.db.dao.Account;
import com.drcall.db.dao.AccountDAO;
import com.drcall.db.dao.Appoint;
import com.drcall.db.dao.AppointDAO;
import com.drcall.db.dao.DivisionDAO;
import com.drcall.db.dao.Doctor;
import com.drcall.db.dao.Family;
import com.drcall.db.dao.FamilyDAO;
import com.drcall.db.dao.FreeExperience;
import com.drcall.db.dao.FreeExperienceDAO;
import com.drcall.db.dao.Hospital;
import com.drcall.db.dao.HospitalDAO;
import com.drcall.db.dao.Member;
import com.drcall.db.dao.MemberDAO;
import com.drcall.db.dao.Message;
import com.drcall.db.dao.MessageDAO;
import com.drcall.db.dao.Schedule;
import com.drcall.db.dao.ScheduleDAO;
import com.google.gson.Gson;

public class IndexController extends BaseController {
	private static final Log log = LogFactory.getLog(IndexController.class);
	
	private static final Integer FREE_EXPERIENCE_TIMES = 1;  
	
	private MessageDAO messageDAO;
	private HospitalDAO hospitalDAO;
	private ScheduleDAO scheduleDAO;
	private DivisionDAO divisionDAO;
	private MemberDAO memberDAO;
	private FamilyDAO familyDAO;
	private AppointDAO appointDAO;
	private AccountDAO accountDAO;
	
	
	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}
	public void setAppointDAO(AppointDAO appointDAO) {
		this.appointDAO = appointDAO;
	}
	public void setFamilyDAO(FamilyDAO familyDAO) {
		this.familyDAO = familyDAO;
	}
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	public void setDivisionDAO(DivisionDAO divisionDAO) {
		this.divisionDAO = divisionDAO;
	}
	public void setScheduleDAO(ScheduleDAO scheduleDAO) {
		this.scheduleDAO = scheduleDAO;
	}
	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}
	public void setHospitalDAO(HospitalDAO hospitalDAO) {
		this.hospitalDAO = hospitalDAO;
	}
		
	public ModelAndView load(HttpServletRequest request, HttpServletResponse response){	
		Map<String, Object> model = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		
		
		// MEMBER BASIC INFOMATION
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			Member member = memberDAO.findById(principal.getName());
			model.put("member", member);
			
			// GET MEMBER BALANCE
			Account account = new Account();
			int balance = account.getBalanceByMember(accountDAO, member);
			model.put("balance", balance);
		}
		
		
		
		
		// HOSPITAL LIST
		ArrayList<AutoCompletedCommand> autoCompletedList = new ArrayList<AutoCompletedCommand>();
		List<Hospital> list = hospitalDAO.findAll();
		for (Hospital hospital : list) {
			autoCompletedList.add(new AutoCompletedCommand(hospital.getHospitalId(), hospital.getName()));
		}

		model.put("hospital_list", gson.toJson(autoCompletedList));
			
		ModelAndView mav = new ModelAndView("indexPage", model);
		return mav;		
		
	}
	
	public ModelAndView isLogin(HttpServletRequest request, HttpServletResponse response){	
		Map<String, Object> model = new HashMap<String, Object>();
		
		Principal principal = request.getUserPrincipal();
		
		if(principal == null){
			model.put("isLogin", false);
		} else {
			model.put("isLogin", true);
		}
		
		ModelAndView mav = new ModelAndView("jsonView", model);
		
		return mav;
	}
	

	
	public ModelAndView findDivisionsByHospital(HttpServletRequest request, HttpServletResponse response){		
		
		String hospitalId = request.getParameter("hospitalId");
		Hospital hospital = hospitalDAO.findById(hospitalId);

		log.info("hospitalId: "+hospitalId+" hospital:"+hospital);
		
		Map<String, Object> model = new HashMap<String, Object>();

		
		if(hospital != null){
			model.put("divisions", hospital.getDivisions());
		} else {
			model.put("validHospital",false);
		}
		
		ModelAndView mav = new ModelAndView("jsonView", model);
		return mav;
		
		
	}
	
	public ModelAndView findDoctorsByHospital(HttpServletRequest request, HttpServletResponse response){
		String hospitalId = request.getParameter("hospitalId");
		String devisionId = request.getParameter("devisionId");
		
		Hospital hospital = hospitalDAO.findById(hospitalId);
		
		 Set<Doctor> doctors = new HashSet<Doctor>();
		
		 for(Doctor doctor : hospital.getDoctors()){
			 String did = doctor.getDivision().getDivisionId();
			 if(did.equals(devisionId)){
				 doctors.add(doctor);
			 } 
		 }
		
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("doctors", doctors);
		ModelAndView mav = new ModelAndView("jsonView", model);
		return mav;
	}
	
	public ModelAndView findScheduleBDate(HttpServletRequest request, HttpServletResponse response, ScheduleCommand cmd) throws ParseException{
		
		ArrayList<DrcallScheduleDay> scheduleDayList = new ArrayList<DrcallScheduleDay>();
		
		Criteria criteria = scheduleDAO.getSessionFactory().getCurrentSession().createCriteria(Schedule.class);
		criteria.add(Restrictions.eq("hospital", hospitalDAO.findById(cmd.getHospitalId())));
		criteria.add(Restrictions.eq("division", divisionDAO.findById(cmd.getDivisionId())));
		criteria.add(Restrictions.eq("date", cmd.getDate()));
		
		List<Schedule> scheduleList = criteria.list();
		
		DrcallScheduleDay scheduleDay = new DrcallScheduleDay(df.format(cmd.getDate()));
		
		for(Schedule schedule : scheduleList){
			scheduleDay.addSchedule(schedule);
		}
					
		scheduleDayList.add(scheduleDay);
		
		
		Gson gson = new Gson();
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		log.info(gson.toJson(scheduleDay.getScheduleByShift(cmd.getSelectShift())));
		
		model.put("schedule_shift", gson.toJson(scheduleDay.getScheduleByShift(cmd.getSelectShift())));
		
		ModelAndView mav = new ModelAndView("jsonView", model);
		return mav;
	}

	
	public ModelAndView submitQuickAppointment(HttpServletRequest request, HttpServletResponse response, AppointCommand cmd){
		Map<String, Object> model = new HashMap<String, Object>();
		
			
		// Member
		Principal principal = request.getUserPrincipal();
		Member member= memberDAO.findById(principal.getName());	
		Appoint appoint = new Appoint();
		appoint.setMember(member);
		
		
		// 0: 本人
		if(cmd.getFamilyMemberId() == 0){
			appoint.setName(member.getName());
			appoint.setTel(member.getMemberId());
			
			// KEEP_ID_NUMBER
			if(cmd.isKeepId() == true){	
				member.setIdNumber(cmd.getIdNumber());
				memberDAO.attachDirty(member);
			}
			
		} else {
			Family f = familyDAO.findById(cmd.getFamilyMemberId());
			
			appoint.setName(f.getName());
			appoint.setTel(f.getTel());
			
			// KEEP_ID_NUMBER
			if(cmd.isKeepId() == true){
				f.setIdNumber(cmd.getIdNumber());
				familyDAO.attachDirty(f);
			}
		}
		appoint.setCrtTime(new java.sql.Timestamp(new java.util.Date().getTime()));
		appoint.setNotifyTake(false);
		appoint.setShift(cmd.getShift());
		appoint.setStatus(AppointController.STATUS_APP_OK);
			
		Schedule schedule = scheduleDAO.findById(cmd.getScheduleId());
		appoint.setSchedule(schedule);
		appoint.setType(0); //0: web 1: app
		
		// TEST
		Random ran = new Random();
		int appNum = ran.nextInt(42)+1;
		appoint.setAppNumber(appNum);
		
		appointDAO.save(appoint);
		
		model.put("date", appoint.getSchedule().getDate());
		model.put("doctorName", appoint.getSchedule().getDoctor().getName());
		model.put("appoint", appoint);
		model.put("appointNumber", appNum);
		
		
		// WITHDRAW USER ACCOUNT 
		Account account = new Account();
		account.withdrawByWebAppoint(accountDAO, member);
		
		
		ModelAndView mav = new ModelAndView("jsonView", model);
		return mav;
		
	}
	

	public ModelAndView addMessage(HttpServletRequest request, HttpServletResponse response, MessageCommand cmd){		
		Map<String, Object> model = new HashMap<String, Object>();
		
		if (rpHash(request.getParameter("defaultReal")).equals(request.getParameter("defaultRealHash"))) {
			Message msg = new Message();
			
			
			Principal principal = request.getUserPrincipal();
			if(principal != null) {
				Member m = memberDAO.findById(principal.getName());
				msg.setMember(m);
			}
 			
			
			if(cmd.getName() != null){
				// set Name
			} 
			if(cmd.getTel() != null){
				msg.setTel(cmd.getTel());
			}
			if(cmd.getEmail() != null){
				msg.setEmail(cmd.getEmail());
			}
			if(cmd.getContent() != null){
				msg.setContent(cmd.getContent());
			}
			
			msg.setStatus(0);
			
			messageDAO.save(msg);
		}
		else {
			model.put("isRealPerson", false);
		}
		
		ModelAndView mav = new ModelAndView("jsonView", model);
		
		
		return mav;		
	}
	
	private String rpHash(String value) {
		int hash = 5381;
		value = value.toUpperCase();
		for(int i = 0; i < value.length(); i++) {
			hash = ((hash << 5) + hash) + value.charAt(i);
		}
		return String.valueOf(hash);
	}
	
	private String createIdentifyCode(int length) {
		String code = new String();
		Random random = new Random();
		
		for(int i=0; i<length; i++){
			int index = random.nextInt(16);
			code += Integer.toHexString(index);
		}
		
		
		
		return code;
	}
	
}
