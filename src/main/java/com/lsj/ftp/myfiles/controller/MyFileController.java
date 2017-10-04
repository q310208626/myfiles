package com.lsj.ftp.myfiles.controller;

import java.util.List;
import java.util.Map;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lsj.ftp.myfiles.bean.MyFile;
import com.lsj.ftp.myfiles.service.MyFileService;
import com.lsj.ftp.myfiles.service.MyFilesManService;
import com.lsj.ftp.myfiles.serviceImpl.MyFileServiceImpl;

@Controller
@RequestMapping(value="/myFile")
public class MyFileController {
	Logger logger=Logger.getLogger(MyFileController.class);
	@Autowired
	@Qualifier("myFileServiceImpl")
	private MyFileService myFileService;
	
	@RequestMapping(value="/getAllFiles.do")
	public ModelAndView getMyFile(int manId){
		logger.debug("manId:"+manId);
		ModelAndView modelAndView=new ModelAndView();
		
		List<MyFile> myFileList=myFileService.getMyFilesTable(manId);
		modelAndView.addObject("MyFileList", myFileList);
		modelAndView.setViewName("manager_file_table");
		return modelAndView;
	}
	
	@RequestMapping(value="/uploadFile.do")
	public ModelAndView uploadFile(MultipartFile uploadFile,HttpSession session){
		ModelAndView modelAndView=new ModelAndView();
		Map resultMap=null;
		String userIdString=(String)session.getAttribute("userId");
		if (userIdString==null||userIdString.equals("0")) {
			logger.debug("userId is null");
		}
		int userId=Integer.valueOf(userIdString); 
		resultMap=myFileService.uploadMyFile(uploadFile, userId);
//		myFileService.uploadMyFile(uploadFile, ownerId);
		if(resultMap.get("status").equals("success")){
//			modelAndView.setViewName("redirect:/myFile/getAllFiles.do?manId="+userId);
			modelAndView.setViewName("forward:/to_managerMain.do");
		}
		//失败跳转到失败页面
		else{
			modelAndView.setViewName("file_manager_error");
		}
		return modelAndView;
	}

}
