package com.drcall.client.web;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.drcall.db.dao.Account;
import com.drcall.db.dao.AccountDAO;
import com.drcall.db.dao.Appoint;
import com.drcall.db.dao.Consume;
import com.drcall.db.dao.ConsumeDAO;
import com.drcall.db.dao.Member;
import com.drcall.db.dao.MemberDAO;

public class PaymentController extends BaseController{
	private static final Log log = LogFactory.getLog(PaymentController.class);

	private static final int TYPE_WEB_CREDIT = 0;
	private static final int TYPE_APP_CREDIT = 1;
	private static final int TYPE_RECOMMEND = 2;

	private MemberDAO memberDAO;
	private ConsumeDAO consumeDAO;
	private AccountDAO accountDAO;

	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}



	public void setConsumeDAO(ConsumeDAO consumeDAO) {
		this.consumeDAO = consumeDAO;
	}


	public ModelAndView choosePayment(HttpServletRequest request, HttpServletResponse response){	
		log.info("choosePayment");

		Principal principal = request.getUserPrincipal();
		Member member= memberDAO.findById(principal.getName());	
		// GET MEMBER BALANCE
		Account account = new Account();
		int balance = account.getBalanceByMember(accountDAO, member);
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("balance", balance);

		String icpOrderId = new SimpleDateFormat("yyMMddHHmmssSS").format(new Date());

		model.put("member", member);
		model.put("icpOrderId", icpOrderId);

		// ACCOUNT RECORD
		model.put("accounts",member.getAccounts());



		ModelAndView mav = new ModelAndView("choosePayment", model);
		return mav;	
	}


	public ModelAndView confirmPayment(HttpServletRequest request, HttpServletResponse response){	

		Principal principal = request.getUserPrincipal();
		Member member= memberDAO.findById(principal.getName());	
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("member", member);


		String type = request.getParameter("type");

		// CREATE CONSUME RECORD
		Consume consume = new Consume();
		consume.setCrtDatetime(new java.sql.Timestamp(new java.util.Date().getTime()));
		consume.setMember(member);
		consume.setType(0);


		if("1".equals(type)){
			model.put("price", "30");
			model.put("memo", "2");
			consume.setSum(30);	
			consume.setPoint(2);

		} else if("2".equals(type)){
			model.put("price", "150");
			model.put("memo", "12");
			consume.setSum(150);	
			consume.setPoint(12);

		} else if("3".equals(type)){
			model.put("price", "300");
			model.put("memo", "26");
			consume.setSum(300);	
			consume.setPoint(26);

		} else if("4".equals(type)){
			model.put("price", "600");
			model.put("memo", "56");
			consume.setSum(600);	
			consume.setPoint(56);

		}

		consume.setStatus(false);

		consumeDAO.save(consume);

		System.out.println("ID:"+consume.getConsumeId());

		model.put("icpOrderId", String.valueOf(consume.getConsumeId()));

		ModelAndView mav = new ModelAndView("confirmPayment", model);
		return mav;	
	}


	
	static final String SUCCESS = "ok";
	public ModelAndView authSuccess(HttpServletRequest request, HttpServletResponse response){	

		
		
		log.info("authSuccess");

		Principal principal = request.getUserPrincipal();
		Member member= memberDAO.findById(principal.getName());	
		Map<String, Object> model = new HashMap<String, Object>();

		String resultCode = request.getParameter("resultCode");
		String icpOrderId = request.getParameter("icpOrderId");

		log.info("resultCode:"+resultCode);



		if(SUCCESS.equals(resultCode)){
			log.info("PAYMENT SUCCESS");
			
			long consumeId = Long.parseLong(icpOrderId);
			Consume consume = consumeDAO.findById(consumeId);
			consume.setStatus(true);
			consumeDAO.attachDirty(consume);
			
			// SAVE ACCOUNT
			Account account = new Account();
			account.depositForCreditPaymentByWeb(accountDAO, consume.getPoint(), member, consume.getSum());

			model.put("member", member);
			model.put("price", String.valueOf(consume.getSum()));
			model.put("point", String.valueOf(consume.getPoint()));
			model.put("orderDate", String.valueOf(consume.getCrtDatetime()));

		}





		ModelAndView mav = new ModelAndView("authSuccess", model);
		return mav;	
	}

}
