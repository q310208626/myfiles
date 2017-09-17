package com.lsj.ftp.myfiles.controller;

import java.util.Map;

import javax.ws.rs.POST;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lsj.ftp.myfiles.bean.MyFilesManager;
import com.lsj.ftp.myfiles.service.MyFilesManService;
import com.lsj.ftp.myfiles.serviceImpl.MyFilesManServiceImpl;

/**
 * 
 * 项目名称：myfiles 类名称：MFMController 类描述：MyFilesManager 的控制层 创建人：shaojia 创建时间：Sep
 * 3, 2017 1:27:12 PM 修改人：shaojia 修改时间：Sep 3, 2017 1:27:12 PM 修改备注：
 * 
 * @version
 * 
 */
@Controller
@RequestMapping(value = "/MFM")
public class MFMController {

	Logger logger = Logger.getLogger(MFMController.class);

	@Autowired
	@Qualifier("myFilesManServiceImpl")
	private MyFilesManService myFilesManService;

	/**
	 * @Title: MFMLogin
	 * @Description: TODO管理员注册
	 * @param myFilesManager
	 * @return
	 * @return: ModelAndView
	 * @throws
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/login.do")
	public ModelAndView MFMLogin(MyFilesManager myFilesManager) {
		ModelAndView modelAndView = new ModelAndView();
		Map resultMap = null;
		logger.debug("----------------loginFileManager" + myFilesManager);
		logger.debug("----------------myFilesManager" + myFilesManager);
		logger.debug("----------------myFilesManager"
				+ myFilesManager.getAccount());
		logger.debug("----------------myFilesManager"
				+ myFilesManager.getPassword());
		resultMap = myFilesManService.loginFileManager(myFilesManager);
		// 如果resultMap为空，则登录时出错
		if (resultMap == null) {
			modelAndView.setViewName("login_regist_error");
			modelAndView.addObject("errorMessage", "登录出错");
		}
		// 否则，则根据resultMap的status跟msg来判断登录成功与否
		else {
			if (resultMap.get("status").equals("error")) {
				String errorMSG = (String) resultMap.get("msg");
				modelAndView.setViewName("login_regist_error");
				modelAndView.addObject("errorMessage", errorMSG);
			} else if (resultMap.get("status").equals("success")) {
				String successMSG = (String) resultMap.get("msg");
				modelAndView.setViewName("manager_main");
			}
		}
		return modelAndView;
	}

	/**
	 * @Title: MFMRegist
	 * @Description: TODO
	 * @param myFilesManager
	 * @return
	 * @return: ModelAndView
	 * @throws
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/regist.do")
	public ModelAndView MFMRegist(MyFilesManager myFilesManager) {
		ModelAndView modelAndView = new ModelAndView();
		Map resultMap = null;
		String errorMSG = "注册时出错";

		resultMap = myFilesManService.registFileManager(myFilesManager);
		if (resultMap == null) {
			modelAndView.setViewName("login_regist_error");
			modelAndView.addObject("errorMessage", errorMSG);
		} else {
			if (resultMap.get("status").equals("error")) {
				modelAndView.setViewName("login_regist_error");
				errorMSG = (String) resultMap.get("msg");
				modelAndView.addObject("errorMessage", errorMSG);
			}else if(resultMap.get("status").equals("success")){
				errorMSG="请等待管理员激活";
				modelAndView.setViewName("login_regist_error");
				modelAndView.addObject("errorMessage",errorMSG);
			}
		}

		return modelAndView;
	}

}
