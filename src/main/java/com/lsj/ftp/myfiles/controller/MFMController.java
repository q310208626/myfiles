package com.lsj.ftp.myfiles.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lsj.ftp.myfiles.bean.ManPrivilege;
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
	public ModelAndView MFMLogin(MyFilesManager myFilesManager,
			HttpSession httpSession) {
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
				modelAndView.addObject(myFilesManager);
				httpSession.setAttribute("userId", resultMap.get("userId"));
			}
		}
		return modelAndView;
	}

	@RequestMapping(value = "/logout.do")
	public ModelAndView MFMLogout(HttpSession httpSession) {
		ModelAndView modelAndView = new ModelAndView();
		httpSession.setAttribute("userId", 0);
		modelAndView.setViewName("index");
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
			} else if (resultMap.get("status").equals("success")) {
				errorMSG = "请等待管理员激活";
				modelAndView.setViewName("login_regist_error");
				modelAndView.addObject("errorMessage", errorMSG);
			}
		}

		return modelAndView;
	}

	@RequestMapping(value = "/getAllMFM.do")
	public ModelAndView getAllMFM(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		List<MyFilesManager> myFilesManagers = null;
		myFilesManagers = myFilesManService.getAllFileManager();
		modelAndView.setViewName("manager_mfm_table");
		modelAndView.addObject("myMFMList", myFilesManagers);
		return modelAndView;
	}

	@RequestMapping(value = "/activityMFM.do")
	public ModelAndView activityMFM(HttpSession session, int doneId) {
		ModelAndView modelAndView = new ModelAndView();
		String userIdString = (String) session.getAttribute("userId");
		Map resultMap = null;
		if (userIdString == null || userIdString.equals("0")) {
			modelAndView.setViewName("mfm_manager_error");
			modelAndView.addObject("error", "操作员未登录");
		} else {
			// 操作员Id
			int userId = Integer.valueOf(userIdString);
			resultMap = myFilesManService.activeFileManager(userId, doneId);
			if (resultMap.get("status").equals("error")) {
				modelAndView.addObject("error", resultMap.get("error"));
				modelAndView.setViewName("mfm_manager_error");
			} else {
				modelAndView.setViewName("redirect:/MFM/getAllMFM.do");
			}

		}
		return modelAndView;
	}

	@RequestMapping(value = "/freezeMFM.do")
	public ModelAndView freezeMFM(HttpSession session, int doneId) {
		ModelAndView modelAndView = new ModelAndView();
		String userIdString = (String) session.getAttribute("userId");
		Map resultMap = null;
		if (userIdString == null || userIdString.equals("0")) {
			modelAndView.setViewName("mfm_manager_error");
			modelAndView.addObject("error", "操作员未登录");
		} else {
			// 操作员Id
			int userId = Integer.valueOf(userIdString);
			resultMap = myFilesManService.freezeFileManager(userId, doneId);
			if (resultMap.get("status").equals("error")) {
				modelAndView.addObject("error", resultMap.get("error"));
				modelAndView.setViewName("mfm_manager_error");
			} else {
				modelAndView.setViewName("redirect:/MFM/getAllMFM.do");
			}

		}
		return modelAndView;
	}

	@RequestMapping(value = "/updatePrivilege.do",method=RequestMethod.POST)
	public ModelAndView updateMFMPrivilege(ManPrivilege manPrivilege,HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		Map resultMap = null;
		String userIdString = (String) session.getAttribute("userId");
		if (userIdString == null || userIdString.equals("0")) {
			modelAndView.setViewName("mfm_manager_error");
			modelAndView.addObject("error", "操作员未登录");
			if (logger.isDebugEnabled()) {
				logger.debug("操作员未登录");
			}
		} else {
			// 操作员Id
			int doId = Integer.valueOf(userIdString);
			resultMap = myFilesManService.updateMFMPrivilege(doId, manPrivilege);
			
			//根据结果集返回视图
			if (resultMap.get("status").equals("success")) {
				modelAndView.setViewName("redirect:/MFM/getAllMFM.do");
				if (logger.isDebugEnabled()) {
					logger.debug("权限更新成功");
				}
			}else{
				modelAndView.setViewName("mfm_manager_error");
				modelAndView.addObject("error",resultMap.get("error"));
				if (logger.isDebugEnabled()) {
					logger.debug("权限更新失败，跳转到失败显示界面");
				}
			}
			
		}
		return modelAndView;
	}

}
