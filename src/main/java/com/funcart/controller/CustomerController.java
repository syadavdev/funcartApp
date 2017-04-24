package com.funcart.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.funcart.customer.Customer;

@RestController
public class CustomerController {
	
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

	@RequestMapping(value = "/loginDetail",method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void insertLoginDetail(@RequestBody Customer customer){
		System.out.println("Username : "+customer.getUsername());		
	}
	
	@RequestMapping(value = "/signupDetail",method=RequestMethod.POST)
	public void insertSignupDetail(){
		
	}
}