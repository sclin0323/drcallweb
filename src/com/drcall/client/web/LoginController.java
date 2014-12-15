package com.drcall.client.web;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.servlet.ModelAndView;

import com.drcall.client.command.MemberCommand;
import com.drcall.client.scheduling.MonitorSendEmailJob;
import com.drcall.client.scheduling.MonitorSendMessageJob;
import com.drcall.client.util.SendMailUtil;
import com.drcall.db.dao.Account;
import com.drcall.db.dao.AccountDAO;
import com.drcall.db.dao.FamilyDAO;
import com.drcall.db.dao.Member;
import com.drcall.db.dao.MemberDAO;
import com.drcall.db.dao.MemuDAO;
import com.drcall.db.dao.Recommend;
import com.drcall.db.dao.RecommendDAO;
import com.drcall.db.dao.RoleDAO;
import com.drcall.db.dao.SystemEmail;
import com.drcall.db.dao.SystemEmailDAO;
import com.drcall.db.dao.SystemMessage;
import com.drcall.db.dao.SystemMessageDAO;

public class LoginController extends BaseController {
	private static final Log log = LogFactory.getLog(LoginController.class);
	
	private static final Integer DEFAULT_BALANCE = 2;
	private static final Integer RECOMMEND_BALANCE = 1;
	
	private MemberDAO memberDAO;
	private FamilyDAO familyDAO;
	private MemuDAO memuDAO;
	private RecommendDAO recommendDAO;
	private SystemEmailDAO systemEmailDAO;
	private AccountDAO accountDAO;
	private SystemMessageDAO systemMessageDAO;
	 
	
	public void setSystemMessageDAO(SystemMessageDAO systemMessageDAO) {
		this.systemMessageDAO = systemMessageDAO;
	}

	public void setRecommendDAO(RecommendDAO recommendDAO) {
		this.recommendDAO = recommendDAO;
	}
	
	public void setSystemEmailDAO(SystemEmailDAO systemEmailDAO) {
		this.systemEmailDAO = systemEmailDAO;
	}
	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}
	public void setMemuDAO(MemuDAO memuDAO) {
		this.memuDAO = memuDAO;
	}
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	public void setFamilyDAO(FamilyDAO familyDAO) {
		this.familyDAO = familyDAO;
	}
	
	// test
	private RoleDAO roleDAO;
	

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}
	
	public ModelAndView changePassword(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		
		String memberId = request.getParameter("memberId");
		String email = request.getParameter("email");
		String passwd = request.getParameter("passwd");
	
		Member member = memberDAO.findById(memberId);
		if(member == null){
			model.put("status", false);
		} else {
			if(email.equals(member.getEmail())){
				
				member.setPasswd(passwd);
				
				memberDAO.attachDirty(member);
				
				model.put("status", true);
			} else {
				model.put("status", false);
			}
		}
		
		ModelAndView mav = new ModelAndView("jsonView", model);
		return mav;
	}
	
	public ModelAndView forgetPassword(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		
		String tel = request.getParameter("tel");
		String email = request.getParameter("email");
		
		Member member = memberDAO.findById(tel);
		if(member == null){
			model.put("status", false);
		} else {
			if(email.equals(member.getEmail())){
				model.put("status", true);		
				
				// send mail
				String serverName = request.getServerName();
				String scheme = request.getScheme();
				int serverPort = request.getServerPort();
				String url = scheme+"://"+serverName+":"+serverPort+"/drcallweb/changePassword.jsp?memberId="+tel+"&email="+email;
				String content = "Dear "+member.getName()+" 先生/小姐 您好\n\n"+
						"DrCall 密碼變更通知信件，點選連連結後輸入新帳號密碼\n\n"+
						"密碼變更連結： "+url+" \n"+
						"文末 祝順心 \n\n" +
						"DRCALL 團隊";
				
				SystemEmail systemEmail = new SystemEmail();
				systemEmail.setStatus(MonitorSendEmailJob.STATUS_EMAIL_NOT_SEND);
				systemEmail.setSendTo(email);
				systemEmail.setTitle("[DrCall] 變更密碼信件");
				systemEmail.setText(content);
				systemEmail.setCrtDate(new java.sql.Timestamp(new java.util.Date().getTime()));
				
				systemEmailDAO.save(systemEmail);
				
			} else {
				model.put("status", false);
			}
		}
		
		ModelAndView mav = new ModelAndView("jsonView", model);
		return mav;
	}
	
	public ModelAndView checkMemberId(HttpServletRequest request, HttpServletResponse response, MemberCommand cmd){
		Map<String, Object> model = new HashMap<String, Object>();
		
		Member obj = memberDAO.findById(cmd.getMemberId());
		
		if(obj == null){		
			Member member = new Member();
			member.setMemberId(cmd.getMemberId());
			member.setIdentifyCode(createIdentifyCode(8));
			member.setIsIdentify(false);
			member.setCrtDate(new java.sql.Timestamp(new java.util.Date().getTime()));
			
			// Message via Phone
			// Van您好：這是由Dr. Call寄送的驗證簡訊，驗證碼為xxxxx，輸入後即完成帳號申請。Dr. Call團隊敬上
			
			memberDAO.save(member);
			
			// Send Message
			SystemMessage message = new SystemMessage();
			message.setContent(member.getIdentifyCode());
			message.setMobile(member.getMemberId());
			message.setCrtDate(new Timestamp(new Date().getTime()));
			message.setStatus(MonitorSendMessageJob.STATUS_MESSAGE_NOT_SEND);
			message.setSubject("Drcall Identify Code");
			systemMessageDAO.save(message);
			
			model.put("status", true);
		} else {
			
			if(obj.getIsIdentify() == false){
				// sent message to user phone....
				obj.setIdentifyCode(createIdentifyCode(8));
				
				// Send Message
				SystemMessage message = new SystemMessage();
				message.setContent(obj.getIdentifyCode());
				message.setMobile(obj.getMemberId());
				message.setCrtDate(new Timestamp(new Date().getTime()));
				message.setStatus(MonitorSendMessageJob.STATUS_MESSAGE_NOT_SEND);
				message.setSubject("Drcall Identify Code");
				systemMessageDAO.save(message);
				
				
				memberDAO.attachDirty(obj);
				model.put("status", true);	
			} else if(obj.getIsIdentify() == true){
				
				model.put("status", false);
				
			}
		}
		
		ModelAndView mav = new ModelAndView("jsonView", model);
		return mav;
	}

	public ModelAndView createNewAccount(HttpServletRequest request,
			HttpServletResponse response, MemberCommand cmd) {
		Map<String, Object> model = new HashMap<String, Object>();

		Member member = memberDAO.findById(cmd.getMemberId());
		int balance = DEFAULT_BALANCE;

		// CHECK IDENTIFY CODE
		if (cmd.getIdentifyCode().equals(member.getIdentifyCode()) == false) {

			model.put("status", 0); // 認證碼 輸入有誤!!
			ModelAndView mav = new ModelAndView("jsonView", model);
			return mav;

		}

		// CHECK RECOMMEND
		if (cmd.getIsRecommend() == true) {

			Recommend recommend = recommendDAO.findById(cmd.getRecommendId());

			if (recommend == null) {
				model.put("status", 1); // 推薦碼 輸入有誤!!
				ModelAndView mav = new ModelAndView("jsonView", model);
				return mav;
			} else {
				if (recommend.getStatus() != 0) {
					model.put("status", 2); // 推薦碼 已經使用過，無法再使用。
					ModelAndView mav = new ModelAndView("jsonView", model);
					return mav;
				} else {

					member.setIsRecommend(true);
					member.setRecommendId(cmd.getRecommendId());

					recommend.setStatus(1); // 已建立帳號 等候登入認證完成!!
					recommendDAO.attachDirty(recommend);

					balance += RECOMMEND_BALANCE;
					
					// 新增推薦者回饋
					Account account = new Account();
					account.depositForRecommend(accountDAO, recommend.getMember());
					// 新增被推薦人回饋
					account.depositForBeRecommend(accountDAO, member);
				}
			}
		}

		member.setName(cmd.getName());
		member.setGender(cmd.getGender());
		member.setEmail(cmd.getEmail());
		member.setBirthYear(cmd.getBirthYear());
		member.setBirthMonth(cmd.getBirthMonth());
		member.setBirthDay(cmd.getBirthDay());
		member.setAddress(cmd.getAddress());
		member.setPasswd(cmd.getPasswd());
		member.setCrtDate(new Timestamp(System.currentTimeMillis()));
		member.setActive(true);
		member.setIsIdentify(true);
		member.setIsRecommend(cmd.getIsRecommend());

		memberDAO.attachDirty(member);
		model.put("status", 3);
		
		// SEND NOFIFATION MAIL
		String content = "Hi "+member.getName()+" 先生/小姐 您好\n\n"+
				"歡迎您加入Dr. Call預約掛號通知服務，可立即享受院所就診現況提供的服務，相關帳號資訊如下:\n\n"+
				"帳號： "+member.getMemberId()+" \n"+
				"註冊人: "+member.getName()+" \n"+
				"文末 祝順心 \n\n" +
				"DRCALL 團隊";
		SystemEmail systemEmail = new SystemEmail();
		systemEmail.setStatus(MonitorSendEmailJob.STATUS_EMAIL_NOT_SEND);
		systemEmail.setSendTo(cmd.getEmail());
		systemEmail.setTitle("[DrCall] 歡迎加入DrCall，帳號註冊成功。");
		systemEmail.setText(content);
		systemEmail.setCrtDate(new java.sql.Timestamp(new java.util.Date().getTime()));

		systemEmailDAO.save(systemEmail);
		
		// DEPOSIT FOR CREATE NEW MEMBER POINT
		Account account = new Account();
		account.depositForCreateNewMember(accountDAO, member);
		
		ModelAndView mav = new ModelAndView("jsonView", model);

		return mav;
	}
	
	// AJAX 
	
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
