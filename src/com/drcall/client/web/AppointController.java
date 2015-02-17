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
import org.hibernate.criterion.Restrictions;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.drcall.db.dao.Account;
import com.drcall.db.dao.AccountDAO;
import com.drcall.db.dao.Appoint;
import com.drcall.db.dao.AppointDAO;
import com.drcall.db.dao.Division;
import com.drcall.db.dao.DivisionDAO;
import com.drcall.db.dao.Family;
import com.drcall.db.dao.FamilyDAO;
import com.drcall.db.dao.Hospital;
import com.drcall.db.dao.HospitalDAO;
import com.drcall.db.dao.Member;
import com.drcall.db.dao.MemberDAO;
import com.drcall.db.dao.Schedule;
import com.drcall.db.dao.ScheduleDAO;
import com.drcall.db.dao.SystemEmail;
import com.drcall.db.dao.SystemEmailDAO;
import com.drcall.client.command.AppointCommand;
import com.drcall.client.command.AutoCompletedCommand;
import com.drcall.client.command.ScheduleCommand;
import com.drcall.client.scheduling.MonitorSendEmailJob;
import com.drcall.client.util.AppointUtil;
import com.drcall.client.util.DrcallScheduleDay;
import com.google.gson.Gson;

public class AppointController extends BaseController {
	private static final Log log = LogFactory.getLog(AppointController.class);

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd E");
	protected static final Integer STATUS_APP_OK = 0; 
	protected static final Integer STATUS_FIRST_NOFIFY_OK = 1; 
	protected static final Integer STATUS_SECOND_NOTIFY_OK = 2; 
	protected static final Integer STATUS_PREPARE_THIRD_NOTIFY = 3; 
	protected static final Integer STATUS_THIRD_NOTIFY_OK = 4; 
	protected static final Integer STATUS_FINISHED = 5; 
	protected static final Integer STATUS_APP_CANCEL = 6; 
	protected static final Integer STATUS_APP_FAIL = 7; 

	private HospitalDAO hospitalDAO;
	private DivisionDAO divisionDAO;
	private ScheduleDAO scheduleDAO;
	private MemberDAO memberDAO;
	private FamilyDAO familyDAO;
	private AppointDAO appointDAO;
	private AccountDAO accountDAO;
	private SystemEmailDAO systemEmailDAO;

	
	public void setSystemEmailDAO(SystemEmailDAO systemEmailDAO) {
		this.systemEmailDAO = systemEmailDAO;
	}

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

	public void setHospitalDAO(HospitalDAO hospitalDAO) {
		this.hospitalDAO = hospitalDAO;
	}

	public void setScheduleDAO(ScheduleDAO scheduleDAO) {
		this.scheduleDAO = scheduleDAO;
	}

	public void setDivisionDAO(DivisionDAO divisionDAO) {
		this.divisionDAO = divisionDAO;
	}

	// APPOINTMENT RECORD PAGE
	public ModelAndView record(HttpServletRequest request, HttpServletResponse response){	
		Principal principal = request.getUserPrincipal();
		Member member= memberDAO.findById(principal.getName());	
		
		// GET MEMBER BALANCE
		Account account = new Account();
		int balance = account.getBalanceByMember(accountDAO, member);
		
		Set<Appoint> appoints = member.getAppoints();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("balance", balance);
		model.put("appoints", appoints);
		
		
		// HOSPITAL LIST
		Gson gson = new Gson();
				ArrayList<AutoCompletedCommand> autoCompletedList = new ArrayList<AutoCompletedCommand>();
				List<Hospital> list = hospitalDAO.findAll();
				for (Hospital hospital : list) {
					autoCompletedList.add(new AutoCompletedCommand(hospital.getHospitalId(), hospital.getName()));
				}

				model.put("hospital_list", gson.toJson(autoCompletedList));
		
		ModelAndView mav = new ModelAndView("appointRecord", model);
		return mav;	
	}

	// APPOINTMENT BOOKING PAGE
	public ModelAndView booking(HttpServletRequest request, HttpServletResponse response){	
		// Member
		Principal principal = request.getUserPrincipal();
		Member member= memberDAO.findById(principal.getName());
		// GET MEMBER BALANCE
		Account account = new Account();
		int balance = account.getBalanceByMember(accountDAO, member);
		

		// All hospital
		ArrayList<AutoCompletedCommand> autoCompletedList = new ArrayList<AutoCompletedCommand>();
		List<Hospital> list = hospitalDAO.findAll();
		for(Hospital hospital : list){
			autoCompletedList.add(new AutoCompletedCommand(hospital.getHospitalId(), hospital.getName()));
		}

		String hospitalId = request.getParameter("hospitalId");
		String devisionId = request.getParameter("devisionId");


		Map<String, Object> model = new HashMap<String, Object>();
		Gson gson = new Gson();

		if(hospitalId != null && devisionId != null){

			ArrayList<HashMap<Object, Object>> scheduleDays = getDrcallScheduleDays(hospitalId, devisionId);
			model.put("scheduleDays", scheduleDays);
		}

		model.put("hospital_list", gson.toJson(autoCompletedList));
		model.put("member", member);
		model.put("balance", balance);

		ModelAndView mav = new ModelAndView("appointBooking", model);
		return mav;
	}
	
	// AJAX
	public ModelAndView changeAppointment(HttpServletRequest request, HttpServletResponse response, AppointCommand cmd){
		// Member
		Principal principal = request.getUserPrincipal();
		Member member= memberDAO.findById(principal.getName());	
		
		// FIRST CANCEL
		Appoint oldAppoint = appointDAO.findById(cmd.getAppointId());
		oldAppoint.setStatus(STATUS_APP_CANCEL);
		appointDAO.attachDirty(oldAppoint);
		
		// ADD CREATE NEW
		Appoint appoint = new Appoint();
		appoint.setCrtTime(new java.sql.Timestamp(new java.util.Date().getTime()));
		appoint.setMember(member);
		appoint.setName(oldAppoint.getName());
		Schedule schedule = scheduleDAO.findById(cmd.getScheduleId());
		appoint.setSchedule(schedule);
		appoint.setShift(cmd.getShift());
		appoint.setStatus(STATUS_APP_OK);
		appoint.setTel(oldAppoint.getTel());
		appoint.setType(oldAppoint.getType());
		appoint.setNotifyTake(false);
		
		// TEST
		Random ran = new Random();
		int appNum = ran.nextInt(42)+1;
		appoint.setAppNumber(appNum);
		
		appointDAO.save(appoint);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("date", appoint.getSchedule().getDate());
		model.put("hospitalName", appoint.getSchedule().getHospital().getName());
		model.put("divisionName", appoint.getSchedule().getDivision().getCnName());
		model.put("doctorName", appoint.getSchedule().getDoctor().getName());
		model.put("appointNumber", appNum);
		
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
		
		model.put("schedule_shift", gson.toJson(scheduleDay.getScheduleByShift(cmd.getSelectShift())));
		
		ModelAndView mav = new ModelAndView("jsonView", model);
		return mav;
	}
	
	public ModelAndView submitCancel(HttpServletRequest request, HttpServletResponse response, AppointCommand cmd){	
		Map<String, Object> model = new HashMap<String, Object>();

		Appoint appoint = appointDAO.findById(cmd.getAppointId());


		Date appDate = appoint.getSchedule().getDate();
		Date now = new Date();

		System.out.println(canCancelByDate(appDate, now));
		if(canCancelByDate(appDate, now)){
			appoint.setStatus(STATUS_APP_CANCEL);
			appointDAO.attachDirty(appoint);
			model.put("status", true);
			
			// Send email message
			String content = 
					"Hi " +appoint.getMember().getName()+" 先生/小姐\n"+
					"\t您好~~謝謝選用Dr. Call預約掛號通知服務。\n"+
					"您已更動如下的預約掛號時間，在此通知您知悉與確認。\n"+
					"Dr. Call對預約掛號的刪除，將由帳戶中扣除1點，建議您若預約需刪除，可以行程更動方式進行。若此一行程非您所更動，再請您與Dr. Call團隊聯絡。\n\n"+
					"Dr. Call團隊 關心您";

			
			SystemEmail email = new SystemEmail();
			email.setCrtDate(new Timestamp(new Date().getTime()));
			email.setSendTo(appoint.getMember().getEmail());
			email.setStatus(MonitorSendEmailJob.STATUS_EMAIL_NOT_SEND);
			email.setText(content);
			email.setTitle("Dr. Call 預約刪除");
			
			systemEmailDAO.save(email);
			
		} else {
			model.put("status", false);
		}

		ModelAndView mav = new ModelAndView("jsonView", model);
		return mav;
	}

	private boolean canCancelByDate(Date appDate, Date now) {

		Calendar calDateA = Calendar.getInstance();
	    calDateA.setTime(appDate);

	    Calendar calDateB = Calendar.getInstance();
	    calDateB.setTime(now);

	    if((calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
	            && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
	            &&  calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH)) == false) {

	    	if(appDate.getTime() > now.getTime()){
	    		return true; 
	    	}
	    } else {


	    }

		return false;
	}


	public ModelAndView division(HttpServletRequest request, HttpServletResponse response){		
		Map<String, Object> model = new HashMap<String, Object>();

		String hospitalId = request.getParameter("hospitalId");

		if(hospitalId == null){
			log.info("invalid");
			model.put("valid", false);
		} else {
			Hospital hospital = hospitalDAO.findById(hospitalId);
			model.put("divisions", hospital.getDivisions());
		}

		ModelAndView mav = new ModelAndView("jsonView", model);
		return mav;
	}
	
	
	// 確認並送出掛號
	public ModelAndView submit(HttpServletRequest request, HttpServletResponse response, AppointCommand cmd){
		Map<String, Object> model = new HashMap<String, Object>();

		// Member
		Principal principal = request.getUserPrincipal();
		Member member= memberDAO.findById(principal.getName());	

		String select_patient = cmd.getSelectPatient();
		long scheduleId = cmd.getScheduleId();
		
		Appoint appoint = new Appoint();
		appoint.setMember(member);

		String birth;
		if("0".equals(select_patient)){
			appoint.setName(member.getName());
			appoint.setTel(member.getMemberId());
			
			// KEEP_ID_NUMBER
			if(cmd.isKeepId() == true){	
				member.setIdNumber(cmd.getIdNumber());
				memberDAO.attachDirty(member);
			}
			
		} else {
			Family family = familyDAO.findById(Long.parseLong(select_patient));
			appoint.setName(family.getName());
			appoint.setTel(family.getTel());
			
			// KEEP_ID_NUMBER
			if(cmd.isKeepId() == true){
				family.setIdNumber(cmd.getIdNumber());
				familyDAO.attachDirty(family);
			}
		}
		appoint.setCrtTime(new java.sql.Timestamp(new java.util.Date().getTime()));
		appoint.setNotifyTake(false);
		appoint.setShift(cmd.getShift());
		appoint.setStatus(STATUS_APP_OK);
		appoint.setType(0); // 0: web 1: App
		Schedule schedule = scheduleDAO.findById(scheduleId);
		appoint.setSchedule(schedule);
		
		// start to appoint via HIS
		AppointUtil appointUtil = new AppointUtil(cmd.getIdNumber(),appoint, schedule);
		String result = appointUtil.requestAppoint();
		int appNum = -1;
		try {
			appNum = Integer.parseInt(result);
			appoint.setAppNumber(appNum);
			appointDAO.save(appoint);
			
			// WITHDRAW USER ACCOUNT 
			Account account = new Account();
			account.withdrawByWebAppoint(accountDAO, member);
			
		} catch(Exception e) {
			System.out.println("appoint error input...");
		} 

		model.put("date", appoint.getSchedule().getDate());
		model.put("hospitalName", appoint.getSchedule().getHospital().getName());
		model.put("divisionName", appoint.getSchedule().getDivision().getCnName());
		model.put("doctorName", appoint.getSchedule().getDoctor().getName());
		model.put("appointNumber", appNum);

		ModelAndView mav = new ModelAndView("jsonView", model);
		return mav;

	}


	// OTHER
	private ArrayList<HashMap<Object, Object>> getDrcallScheduleDays(String hospitalId, String devisionId) {

		ArrayList<HashMap<Object, Object>> drcallScheduleDays = new ArrayList<HashMap<Object, Object>>();

		//Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

			for(int i=0; i<18; i++){

				Date date = calendar.getTime();
				Criteria criteria = scheduleDAO.getSessionFactory().getCurrentSession().createCriteria(Schedule.class);
				criteria.add(Restrictions.eq("hospital", hospitalDAO.findById(hospitalId)));
				criteria.add(Restrictions.eq("division", divisionDAO.findById(devisionId)));
				criteria.add(Restrictions.eq("date", date));
				List<Schedule> schedules = criteria.list();


				// CREATE A DRCALLSCHEDULEDAY
				HashMap<Object, Object> drcallScheduleDay = new HashMap<Object, Object>();
				drcallScheduleDay.put("date", df.format(date));


				// CREATE DRCALL SCHEDULE
				ArrayList<HashMap<Object, Object>> drcallSchedules = new ArrayList<HashMap<Object, Object>>();
				for(Schedule schedule : schedules){
					// CREATE A DRCALLSCHEDULE
					HashMap<Object, Object> drcallSchedule = new HashMap<Object, Object>();
					drcallSchedule.put("doctorName", schedule.getDoctor().getName());
					drcallSchedule.put("morningShift", schedule.getMorningShift());
					drcallSchedule.put("afternoonShift", schedule.getAfternoonShift());
					drcallSchedule.put("nightShift", schedule.getNightShift());
					drcallSchedule.put("scheduleId",schedule.getScheduleId());
					drcallSchedule.put("hospitalName",schedule.getHospital().getName());
					drcallSchedule.put("divisionName",schedule.getDivision().getCnName());

					drcallSchedules.add(drcallSchedule);
				}

				drcallScheduleDay.put("drcallSchedules", drcallSchedules);

				drcallScheduleDays.add(drcallScheduleDay);

			calendar.add(Calendar.DATE, 1);	// add a day
		}



		return drcallScheduleDays;
	}







}

