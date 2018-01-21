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
	
	/**   
	 * @Title: getMyFile   
	 * @Description: TODO 获取管理员对应的文件   
	 * @param manId 管理员ID
	 * @return      
	 * @return: ModelAndView      
	 * @throws   
	 */  
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
	
	@RequestMapping(value="/getAllFilesByPage.do")
	public ModelAndView getMyFileByPage(int page,int pageCount,HttpSession session){
		ModelAndView modelAndView=new ModelAndView();
		try{
			int manId=Integer.parseInt(session.getAttribute("userId").toString());
			logger.debug("========userID:"+manId);
			int fileCount=0;
			int startIndex=(page-1)*pageCount;
			fileCount=myFileService.getMyFilesCount();
			List<MyFile> myFileList=myFileService.getMyFilesTableByPage(manId, startIndex, pageCount);
			modelAndView.addObject("MyFileList", myFileList);
			modelAndView.addObject("MyFileList", myFileList);
			modelAndView.addObject("fileCount", fileCount);
			modelAndView.addObject("currentPage", page);
			modelAndView.addObject("totalPage", (fileCount/11)+1);
			modelAndView.setViewName("manager_file_table");
			return modelAndView;
		}catch(NullPointerException e){
			modelAndView.addObject("error", "管理员未登录，请重新登录");
			modelAndView.setViewName("file_manager_error");
			return modelAndView;
		}
		
		

	}
	
	/**   
	 * @Title: getCustomerFileByPage   
	 * @Description: TODO 后去访客列表文件，分页
	 * @param page 页数
	 * @param pageCount 每页的文件数
	 * @return      
	 * @return: ModelAndView      
	 * @throws   
	 */  
	@RequestMapping(value="/getCustomerFileByPage.do")
	@ResponseBody
	public Map getCustomerFileByPage(int page,int pageCount,String fileName){
		Map HashMap=new HashMap();
		int fileCount=0;
		int startIndex=(page-1)*pageCount;
		List<MyFile> myFileList=null;
		logger.debug("fileName========"+fileName);
		myFileList=myFileService.getCustomerFilesTableByPage(startIndex, pageCount,fileName);
		fileCount=myFileService.getMyFilesCount();
	
		HashMap.put("fileLists", myFileList);
		HashMap.put("fileCount", fileCount);
		HashMap.put("currentPage", page);
		HashMap.put("totalPage", (fileCount/11)+1);
		return HashMap;
	}
	
	/**   
	 * @Title: getCustomerFile   
	 * @Description: TODO 获取访客列表全部文件   
	 * @return      
	 * @return: ModelAndView      
	 * @throws   
	 */  
	@RequestMapping(value="/getCustomerFile.do")
	public ModelAndView getCustomerFile(){
		ModelAndView modelAndView=new ModelAndView();
		List<MyFile> myFileList=myFileService.getCustomerFilesTable();
		modelAndView.addObject("MyFileList", myFileList);
		modelAndView.setViewName("customer_file_table");
		return modelAndView;
	}
	
	/**   
	 * @Title: downloadFile   
	 * @Description: TODO 下载文件  
	 * @param fileId 要下载的文件ID
	 * @param request 
	 * @return      
	 * @return: ResponseEntity<byte[]>      
	 * @throws   
	 */  
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
//			设置响应文件

			headers.setContentDispositionFormData("attachment",fileName);
//			设置为字节流
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		}
		try {
//			请求成功并且服务器创建了新的资源
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(downloadFile),    
			        headers, HttpStatus.CREATED);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.debug("IO出错");
			e.printStackTrace();
		} 
		return null;
	}
	
	/**   
	 * @Title: uploadFile   
	 * @Description: TODO 上传文件
	 * @param uploadFile 要上传的文件
	 * @param session
	 * @return      
	 * @return: ModelAndView      
	 * @throws   
	 */  
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
			modelAndView.setViewName("redirect:/myFile/getAllFilesByPage.do?manId="+userId+"&page=1&pageCount=10");
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
	
	/**   
	 * @Title: deleteMyFile   
	 * @Description: TODO 删除文件
	 * @param fileId 要删除的文件的ID
	 * @param session
	 * @return      
	 * @return: ModelAndView      
	 * @throws   
	 */  
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
				modelAndView.setViewName("redirect:/myFile/getAllFilesByPage.do?manId="+userId+"&page=1&pageCount=10");
			}
			//删除结果失败
			else{
				modelAndView.setViewName("file_manager_error");
				modelAndView.addObject("error", resultMap.get("error"));
			}
			return modelAndView;
		}
	}
	
	/**   
	 * @Title: updateFile   
	 * @Description: TODO 重传文件
	 * @param fileId 需要重传的文件Id
	 * @param updateFile 重传的文件
	 * @param session
	 * @return      
	 * @return: ModelAndView      
	 * @throws   
	 */  
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
				modelAndView.setViewName("redirect:/myFile/getAllFilesByPage.do?manId="+userId+"&page=1&pageCount=10");
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
