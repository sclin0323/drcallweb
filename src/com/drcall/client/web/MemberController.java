package com.drcall.client.web;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.drcall.client.command.FamilyCommand;
import com.drcall.client.command.MemberCommand;
import com.drcall.client.command.RecommendCommand;
import com.drcall.client.scheduling.MonitorSendEmailJob;
import com.drcall.client.util.SendMailUtil;
import com.drcall.db.dao.Account;
import com.drcall.db.dao.AccountDAO;
import com.drcall.db.dao.Family;
import com.drcall.db.dao.FamilyDAO;
import com.drcall.db.dao.Member;
import com.drcall.db.dao.MemberDAO;
import com.drcall.db.dao.Recommend;
import com.drcall.db.dao.RecommendDAO;
import com.drcall.db.dao.SystemEmail;
import com.drcall.db.dao.SystemEmailDAO;

public class MemberController extends BaseController {
	private static final Log log = LogFactory.getLog(LoginController.class);
	
	private MemberDAO memberDAO;
	private FamilyDAO familyDAO;
	private RecommendDAO recommendDAO;
	private SystemEmailDAO systemEmailDAO;
	private AccountDAO accountDAO;

	
	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public void setRecommendDAO(RecommendDAO recommendDAO) {
		this.recommendDAO = recommendDAO;
	}

	public void setSystemEmailDAO(SystemEmailDAO systemEmailDAO) {
		this.systemEmailDAO = systemEmailDAO;
	}

	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	
	public void setFamilyDAO(FamilyDAO familyDAO) {
		this.familyDAO = familyDAO;
	}

	// MEMBER MANAGEMENT PAGE
	public ModelAndView management(HttpServletRequest request, HttpServletResponse response){	
		Principal principal = request.getUserPrincipal();
		Map<String, Object> model = new HashMap<String, Object>();
		
		Member member= memberDAO.findById(principal.getName());	
		
		// GET MEMBER BALANCE
		Account account = new Account();
		int balance = account.getBalanceByMember(accountDAO, member);
		model.put("balance", balance);
		
		
		model.put("member", member);
			
		ModelAndView mav = new ModelAndView("memberManagement", model);
		return mav;		
	}
	
	// AJAX UPDATE MEMBER INFORMATION
	public ModelAndView updateMember(HttpServletRequest request, HttpServletResponse response, MemberCommand cmd){
		Member m = memberDAO.findById(cmd.getMemberId());
		
		if(cmd.getBirthYear() != null){
			m.setBirthYear(cmd.getBirthYear());
		}
		if(cmd.getBirthMonth() != null){
			m.setBirthMonth(cmd.getBirthMonth());
		}
		if(cmd.getBirthDay() != null){
			m.setBirthDay(cmd.getBirthDay());
		}		
		if(cmd.getAddress() != null){
			m.setAddress(cmd.getAddress());
		}
		if(cmd.getEmail() != null){
			m.setEmail(cmd.getEmail());
		}  
		if(cmd.getName() != null){
			m.setName(cmd.getName());
		}
		if(cmd.getIdNumber() != null){
			m.setIdNumber(cmd.getIdNumber());
		}
		if(cmd.getGender() != null){
			m.setGender(cmd.getGender());
		}
		
		
		memberDAO.attachDirty(m);
				
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("member", m);
		
		ModelAndView mav = new ModelAndView("jsonView", model);
		return mav;
		
	}
	
	// AJAX DELETE FAMILY MEMBER
	public ModelAndView deleteFamily(HttpServletRequest request, HttpServletResponse response, FamilyCommand cmd){	
		
		Family family= familyDAO.findById(cmd.getFamilyId());	
		familyDAO.delete(family);
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("result", "success");	
		ModelAndView mav = new ModelAndView("jsonView", model);
		
		return mav;
	}
	
	// AJAX ADD FAMILY MEMBER
	public ModelAndView addFamily(HttpServletRequest request, HttpServletResponse response, FamilyCommand cmd){		
		
		Principal principal = request.getUserPrincipal();
		Member member= memberDAO.findById(principal.getName());	
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		if(member.getFamilies().size() < 8){
			Family family = new Family();
			family.setName(cmd.getName());
			family.setTel(cmd.getTel());
			family.setBirthYear(cmd.getBirthYear());
			family.setBirthMonth(cmd.getBirthMonth());
			family.setBirthDay(cmd.getBirthDay());
			family.setMember(member);
			family.setActive(true);
			family.setGender(cmd.getGender());
			family.setIdNumber(cmd.getIdNumber());
			
			familyDAO.save(family);
			model.put("familyMemberId", family.getFamilyId());
			model.put("isOverLimit", false);
		} else {
			model.put("isOverLimit", true);
		}
		
		
		ModelAndView mav = new ModelAndView("jsonView", model);
		
		
		return mav;
	}
	
	// AJAX ADD RECOMMEND
	public ModelAndView addRecommend(HttpServletRequest request, HttpServletResponse response, RecommendCommand cmd){	
		
		Principal principal = request.getUserPrincipal();
		Member member= memberDAO.findById(principal.getName());	
		
		Recommend recommend = new Recommend();
		recommend.setRecommendDate(new Timestamp(new Date().getTime()));
		recommend.setEmail(cmd.getEmail());
		recommend.setName(cmd.getName());
		recommend.setTel(cmd.getTel());
		
		Calendar c = Calendar.getInstance();
        c.setTime(new Date());   //设置当前日期
        c.add(Calendar.DATE, 14); //日期加14
		recommend.setDeadline(c.getTime());
		
		// CREATE RECOMMEND CODE via DATETIME	
		String recommendId = new SimpleDateFormat("yyMMddHHmmssSS").format(new Date());
		recommend.setRecommendId(recommendId);
		recommend.setActive(true);
		recommend.setStatus(0);
		recommend.setMember(member);
		
		recommendDAO.save(recommend);
		
		// send email
		String serverName = request.getServerName();
		String scheme = request.getScheme();
		int serverPort = request.getServerPort();
		String recommendUrl = scheme+"://"+serverName+":"+serverPort+"/drcallweb/login.jsp?isRecommend=true&recommendCode="+recommendId+"&tel="+cmd.getTel();
		
		String content = "Dear "+cmd.getName()+" 先生/小姐 您好\n\n"+
				"您接到此封推薦信，是因為您的朋友 "+member.getName()+" 覺的Dr. Call的服務很棒，基於好東西與好朋友分享，故推薦您加入Dr. Call的服務，您可直接點選如下註冊連結進行註冊，即可啟動Dr. Call院所預約掛號通知服務，了解診間看診現況，節省等候時間與成本。\n\n"+
				"完成註冊後即可立即免費體驗此服務，而推薦您的朋友也可獲得推薦點數喔~~快進行註冊與推薦其它朋友吧~~\n\n"+
				"註冊連結： "+recommendUrl+" \n"+
				"推薦序號："+recommendId+"\n\n"+
				"文末 祝順心 \n\n" +
				"DRCALL 團隊";
		
		SystemEmail systemEmail = new SystemEmail();
		systemEmail.setCrtDate(new Timestamp(new Date().getTime()));
		systemEmail.setStatus(MonitorSendEmailJob.STATUS_EMAIL_NOT_SEND);
		systemEmail.setSendTo(cmd.getEmail());
		systemEmail.setTitle("[DrCall] 歡迎加入 Dr Call, 您的親友 "+member.getName()+" 推薦您，立即加入立即優惠。");
		systemEmail.setText(content);
		
		systemEmailDAO.save(systemEmail);

		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("recommend", recommend);	
		model.put("date", dtf.format(recommend.getRecommendDate()));
		model.put("deadline", df.format(recommend.getDeadline()));
		model.put("recommendId", recommend.getRecommendId());
		
		// 0:waiting 1:success 2:overdue
		if(recommend.getStatus() == 0){
			model.put("status", "已送出推薦，等候中!");
		} else if(recommend.getStatus() == 1){
			model.put("status", "推薦成功!");
		} else if(recommend.getStatus() == 2){
			model.put("status", "推薦逾期!");
		}
		
		ModelAndView mav = new ModelAndView("jsonView", model);

		return mav;
	}
	
	
}
