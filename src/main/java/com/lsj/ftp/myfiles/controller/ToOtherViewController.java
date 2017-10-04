package com.lsj.ftp.myfiles.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ToOtherViewController {
	private static String index="index";
	private static String managerLogin="manager_login";
	private static String managerMain="manager_main";
	
	@RequestMapping(value="/to_manlogin.do")
	public String toManagerLogin(){
		return managerLogin;
	}
	
	@RequestMapping(value="/to_index.do")
	public String toIndex(){
		return index;
	}
	
	@RequestMapping(value="to_managerMain.do")
	public String toMangerMan(){
		return managerMain;
	}
}
