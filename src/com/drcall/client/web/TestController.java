package com.drcall.client.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;

import com.drcall.client.command.AppointCommand;
import com.drcall.client.util.AppointUtil;
import com.drcall.db.dao.Appoint;
import com.drcall.db.dao.AppointDAO;
import com.drcall.db.dao.FamilyDAO;
import com.drcall.db.dao.Member;
import com.drcall.db.dao.MemberDAO;
import java.io.File;
import java.io.IOException;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class TestController extends BaseController {
	

	public void appoint(HttpServletRequest request, HttpServletResponse response) throws Exception, ServletException{
		
		System.out.println("========================================");
		
		AppointUtil appointUtil = new AppointUtil();

		String result = appointUtil.requestAppoint();
		
		System.out.println(result);
		
        return;
	}
	

}
