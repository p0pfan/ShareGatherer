 package com.favorcollection.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.favorcollection.pojo.UserInfo;
import com.favorcollection.service.Impl.UserServiceImpl;
import com.favorcollection.service.Impl.WeiboTokenImpl;

import weibo4j.http.AccessToken;

@Controller
public class HomeController {
	private static Logger log = Logger.getLogger(HomeController.class);

	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	private WeiboTokenImpl weiboTokenImpl;
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView homepage(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("share");
		return mav;
	}
	
	@RequestMapping(value = "/weiboadmin" , method = RequestMethod.GET)
	public @ResponseBody UserInfo getUserInfoFromWeiBo(){
		AccessToken accesstoken = weiboTokenImpl.getAccessToken("c_c1227@163.com");
		return userServiceImpl.showUser(accesstoken);
	}
		
}
