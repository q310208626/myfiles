package com.lsj.ftp.myfiles.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lsj.ftp.myfiles.bean.MyFile;
import com.lsj.ftp.myfiles.bean.MyFilesManager;
import com.lsj.ftp.myfiles.service.MyFileService;

@Controller
public class ToOtherViewController {
	private static String index="index";
	private static String managerLogin="manager_login";
	private static String managerMain="manager_main";
	private static Logger logger=Logger.getLogger(ToOtherViewController.class);
	
	@Autowired
	@Qualifier("myFileServiceImpl")
	private MyFileService myFileService;
	
	//跳转到管理员登陆界面
	@RequestMapping(value="/to_manlogin.do")
	public ModelAndView toManagerLogin(HttpSession httpsession){
		ModelAndView modelAndView=new ModelAndView();
		MyFilesManager myFilesManager=null;
		//根据httpsession判断客户端之前是够登陆过，有则直接登陆
		myFilesManager=(MyFilesManager)httpsession.getAttribute("myFilesManager");
		if(logger.isDebugEnabled()) {
			logger.debug("------------------"+myFilesManager+"---------------");
		}
		
		if(myFilesManager!=null) {
			modelAndView.setViewName("manager_main");
			modelAndView.addObject(myFilesManager);
		}else {
			modelAndView.setViewName("manager_login");
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value="/to_index.do")
	public String toIndex(){
		return index;
	}
	
	@RequestMapping(value="to_managerMain.do")
	public String toMangerMan(){
		return managerMain;
	}
	
	//跳转到文件分享界面
	@RequestMapping(value="to_share.do")
	public ModelAndView toShareView(int shareId){
		ModelAndView modelAndView=new ModelAndView();
		MyFile myFile=null;
		myFile=myFileService.getMyFileByShareId(shareId);
		modelAndView.addObject("fileName", myFile.getFileName());
		modelAndView.setViewName("customer_share_file");
		return modelAndView;
	}
}
