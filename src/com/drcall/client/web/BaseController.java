package com.drcall.client.web;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public abstract class BaseController extends MultiActionController {
	protected DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	protected DateFormat tf = new SimpleDateFormat("HH:mm:ss");
	
	
	
	
}
