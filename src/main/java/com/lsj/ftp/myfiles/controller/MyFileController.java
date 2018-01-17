package com.lsj.ftp.myfiles.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lsj.ftp.myfiles.bean.MyFile;
import com.lsj.ftp.myfiles.service.MyFileService;
import com.lsj.ftp.myfiles.service.MyFilesManService;
import com.lsj.ftp.myfiles.serviceImpl.MyFileServiceImpl;
import com.sun.mail.handlers.multipart_mixed;

@Controller
@RequestMapping(value="/myFile")
public class MyFileController {
	Logger logger=Logger.getLogger(MyFileController.class);
	@Autowired
	@Qualifier("myFileServiceImpl")
	private MyFileService myFileService;
	
	@RequestMapping(value="/getAllFiles.do")
	public ModelAndView getMyFile(HttpSession session){
		ModelAndView modelAndView=new ModelAndView();
		try{
			int manId=Integer.parseInt(session.getAttribute("userId").toString());
			logger.debug("========userID:"+manId);
			List<MyFile> myFileList=myFileService.getMyFilesTable(manId);
			modelAndView.addObject("MyFileList", myFileList);
			modelAndView.setViewName("manager_file_table");
			return modelAndView;
		}catch(NullPointerException e){
			modelAndView.addObject("error", "管理员未登录，请重新登录");
			modelAndView.setViewName("file_manager_error");
			return modelAndView;
		}
	}
	
	@RequestMapping(value="/getCustomerFile.do")
	public ModelAndView getCustomerFile(){
		ModelAndView modelAndView=new ModelAndView();
		List<MyFile> myFileList=myFileService.getCustomerFilesTable();
		modelAndView.addObject("MyFileList", myFileList);
		modelAndView.setViewName("customer_file_table");
		return modelAndView;
	}
	
	@RequestMapping(value="/downloadFile.do")
	public ResponseEntity<byte[]> downloadFile(int fileId,HttpServletRequest request){
		File downloadFile=null;
		FileInputStream fileInputStream=null;
		OutputStream outputStream=null;
		downloadFile=myFileService.getDownloadFile(fileId);
		HttpHeaders headers=new HttpHeaders();
		if(downloadFile==null){
			logger.debug("文件不存在");
		}else {
			String fullPath=downloadFile.getPath();
			logger.debug("=============="+fullPath);
			String savePath=fullPath.substring(0, fullPath.lastIndexOf(File.separator));
			String fileName=downloadFile.getName();
			logger.debug("=============="+savePath);
			headers.setContentDispositionFormData("attachment",fileName);
			//设置为常见的下载格式
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		}
		try {
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(downloadFile),    
			        headers, HttpStatus.CREATED);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.debug("IO出错");
			e.printStackTrace();
		} 
		return null;
	}
	
	@RequestMapping(value="/uploadFile.do")
	public ModelAndView uploadFile(MultipartFile uploadFile,HttpSession session){
		ModelAndView modelAndView=new ModelAndView();
		Map resultMap=null;
		String userIdString=(String)session.getAttribute("userId");
		if (userIdString==null||userIdString.equals("0")) {
			logger.debug("userId is null");
			modelAndView.addObject("error", "管理员未登录，请重新登录");
			modelAndView.setViewName("file_manager_error");
			return modelAndView;
		}
		int userId=Integer.valueOf(userIdString); 
		resultMap=myFileService.uploadMyFile(uploadFile, userId);
//		myFileService.uploadMyFile(uploadFile, ownerId);
		if(resultMap.get("status").equals("success")){
//			modelAndView.setViewName("redirect:/myFile/getAllFiles.do?manId="+userId);
			modelAndView.setViewName("redirect:/myFile/getAllFiles.do?manId="+userId);
		}
		//失败跳转到失败页面
		else{
			String errorMSG=(String) resultMap.get("error");
			if(errorMSG!=null) {
				modelAndView.addObject("error", errorMSG);
			}
			modelAndView.setViewName("file_manager_error");
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/deleteFile.do")
	public ModelAndView deleteMyFile(int fileId,HttpSession session){
		ModelAndView modelAndView=new ModelAndView();
		Map resultMap=null;
		String userIdString=(String) session.getAttribute("userId");
		//session中userId不存在，则返回错误页面
		if(userIdString==null||userIdString.equals("")){
			modelAndView.setViewName("file_manager_error");
			modelAndView.addObject("error","用户回话过期");
			return modelAndView;
		}
		else{
			int userId=Integer.valueOf(userIdString);
			resultMap=myFileService.deleteMyFile(userId,fileId);
			//删除结果成功
			if(resultMap.get("status").equals("success")){
				modelAndView.setViewName("forward:/myFile/getAllFiles.do?manId="+userId);
			}
			//删除结果失败
			else{
				modelAndView.setViewName("file_manager_error");
				modelAndView.addObject("error", resultMap.get("error"));
			}
			return modelAndView;
		}
	}
	
	@RequestMapping(value="/updateFile.do")
	public ModelAndView updateFile(int fileId,MultipartFile updateFile,HttpSession session){
		ModelAndView modelAndView=new ModelAndView();
		Map resultMap=null;
		String userIdString=(String) session.getAttribute("userId");
		//session中userId不存在，则返回错误页面
		if(userIdString==null||userIdString.equals("")){
			modelAndView.setViewName("file_manager_error");
			modelAndView.addObject("error","用户回话过期");
			return modelAndView;
		}else{
			int userId=Integer.valueOf(userIdString);
			logger.debug("fileId"+fileId);
			logger.debug("updateFile"+updateFile);
			resultMap=myFileService.updateMyFile(userId, fileId, updateFile);
			//结果成功
			if(resultMap.get("status").equals("success")){
				modelAndView.setViewName("redirect:/myFile/getAllFiles.do?manId="+userId);
			}			
			//删除结果失败
			else{
				modelAndView.setViewName("file_manager_error");
				modelAndView.addObject("error", resultMap.get("error"));
			}
		}	
		return modelAndView;
	}
	
	@RequestMapping(value="/searchFile.do",method=RequestMethod.POST)
	@ResponseBody
	public List<MyFile> searchFile(String fileName){
		List<MyFile> myFileList=myFileService.searchFiles(fileName);
		System.out.println("_____________"+fileName);
		return myFileList;
	}
	
	@RequestMapping(value="mfm_searchFile.do",method=RequestMethod.POST)
	@ResponseBody
	public List<MyFile> mfmSearchFile(String fileName,HttpSession session){
		List<MyFile> myFileList=null;
		logger.debug("========fileName:"+fileName);
		try{
			int manId=Integer.parseInt(session.getAttribute("userId").toString());
			myFileList=myFileService.getMyFilesTable(manId);
			myFileList=myFileList.stream().filter(f->f.getFileName().indexOf(fileName)>=0).collect(Collectors.toList());
			logger.debug("=========filesSize"+myFileList.size());
			return myFileList;
		}catch(NullPointerException e){
			return myFileList;
			
		}
	}

}
