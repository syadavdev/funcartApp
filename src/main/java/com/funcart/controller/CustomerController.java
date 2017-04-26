package com.funcart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.funcart.Dao.CustomerDao;
import com.funcart.domain.Customer;

@RestController
public class CustomerController {
	
	@Autowired
	CustomerDao customerDao;
	
	@RequestMapping(value="/loginPage",method=RequestMethod.GET)
	public ModelAndView getLoginPage(){
		ModelAndView mv = new ModelAndView("loginPage");
		return mv;
	}
	
	@RequestMapping(value="/signupPage",method=RequestMethod.GET)
	public ModelAndView getSignupPage(){
		ModelAndView mv = new ModelAndView("signupPage");
		return mv;
	}

	@RequestMapping(value = "/loginDetail",method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public boolean checkLoginDetail(@RequestBody Customer customer){
		boolean flag = true;
		if(customerDao.checkingCustomer(customer))
			flag = true;
		else
			flag = false;
		return flag;
	}
	
	@RequestMapping(value = "/signupDetail",method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public boolean insertSignupDetail(@RequestBody Customer customer) throws Exception{
		boolean flag = true;
		if(customer.checkSignupDetail()){
			if(customerDao.insertCustomer(customer))
					flag = true;
		}
		else{
			flag = false;
		}
		return flag;
	}
}