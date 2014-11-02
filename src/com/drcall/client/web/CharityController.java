package com.drcall.client.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.drcall.db.dao.Appoint;
import com.drcall.db.dao.Member;

public class CharityController extends BaseController{
	
	
	
	public ModelAndView activies(HttpServletRequest request, HttpServletResponse response){	
		Principal principal = request.getUserPrincipal();
		//Member member= memberDAO.findById(principal.getName());	
		//Set<Appoint> appoints = member.getAppoints();
		Map<String, Object> model = new HashMap<String, Object>();
		//model.put("appoints", appoints);
		ModelAndView mav = new ModelAndView("charityActivies", model);
		return mav;	
	}
}
