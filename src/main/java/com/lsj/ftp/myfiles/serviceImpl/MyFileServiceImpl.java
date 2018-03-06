package com.lsj.ftp.myfiles.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.lsj.ftp.myfiles.bean.ManPrivilege;
import com.lsj.ftp.myfiles.bean.MyFile;
import com.lsj.ftp.myfiles.bean.MyFileDelSchedule;
import com.lsj.ftp.myfiles.bean.MyFileShare;
import com.lsj.ftp.myfiles.bean.MyFilesManager;
import com.lsj.ftp.myfiles.dao.MyFileDao;
import com.lsj.ftp.myfiles.dao.MyFileDelScheduleDao;
import com.lsj.ftp.myfiles.dao.MyFileShareDao;
import com.lsj.ftp.myfiles.dao.MyFilesManDao;
import com.lsj.ftp.myfiles.service.MyFileService;
import com.lsj.ftp.myfiles.service.MyFilesManService;
import com.mysql.fabric.xmlrpc.base.Data;

@Service
public class MyFileServiceImpl implements MyFileService {

	private static Logger logger=Logger.getLogger(MyFileServiceImpl.class);
	@Autowired
	private MyFileDao myFileDao;
	@Autowired
	private MyFilesManDao myFileManDao;
	@Autowired
	private MyFileDelScheduleDao myFileDelScheduleDao;
	@Autowired
	private MyFileShareDao myFileShareDao;
	//private static String savePath = "/home/shaojia/myFiles/upload";
	private static String savePath ="C:\\myfiles\\save";


	@Override
	public List<MyFile> getMyFilesTable(int id) {
		// TODO Auto-generated method stub
		List<MyFile> myFiles = null;
		MyFilesManager myFilesManager = null;

		myFilesManager = myFileManDao.selectMFMById(id);
		// 管理员不存在
		if (myFilesManager == null) {
			return null;
		}
		// 如果管理员为主管理员或者拥有操作全部文件权限
		else if (myFilesManager.getManPrivilege().getMainPVL() == 1
				|| myFilesManager.getManPrivilege().getAllFilesPVL() == 1) {
			myFiles = myFileDao.selectAllMyFiles();
		}
		// 没有权限则只获取自己的文件
		else {
			myFiles = myFileDao.selectMyFilesByOwner(id);
		}
		return myFiles;
	}
	
	

	@Override
	public List<MyFile> getMyFilesTableByPage(int id, int startIndex, int count,String fileName) {
		// TODO Auto-generated method stub
		List<MyFile> myFiles = null;
		MyFilesManager myFilesManager = null;
		myFilesManager = myFileManDao.selectMFMById(id);
		// 管理员不存在
		if (myFilesManager == null) {
			return null;
		}
		// 如果管理员为主管理员或者拥有操作全部文件权限
		else if (myFilesManager.getManPrivilege().getMainPVL() == 1
				|| myFilesManager.getManPrivilege().getAllFilesPVL() == 1) {
			if(fileName==null||fileName.equals("")){
				myFiles = myFileDao.selectAllMyByPage(startIndex, count);
			}else{
				myFiles = myFileDao.selectAllMyByPageAndFileName(startIndex, count, fileName);
			}
			
		}
		// 没有权限则只获取自己的文件
		else {
			if(fileName==null||fileName.equals("")){
				myFiles = myFileDao.selectMyFileByOwnerAndByPage(id, startIndex, count);
			}else{
				myFiles = myFileDao.selectMyFileByOwnerAndByPageAndFileName(id, startIndex, count, fileName);
			}
			
		}
		return myFiles;
	}



	@Override
	public List<MyFile> getCustomerFilesTable() {
		// TODO Auto-generated method stub
		List<MyFile> myFiles = null;
		myFiles=myFileDao.selectAllMyFiles();
		return myFiles;
	}
	

	@Override
	public List<MyFile> getCustomerFilesTableByPage(int startIndex,
			int pageCount,String fileName) {
		// TODO Auto-generated method stub
		List<MyFile> myFiles = null;
		if(fileName==null||fileName.equals("")) {
			myFiles=myFileDao.selectAllMyByPage(startIndex, pageCount);
		}else {
			myFiles=myFileDao.selectAllMyByPageAndFileName(startIndex, pageCount,fileName);
		}
		return myFiles;
	}
	

	@Override
	public int getMyFilesCount() {
		// TODO Auto-generated method stub
		int count=0;
		count=myFileDao.selectMyFilesCount();
		return  count;
	}



	@Override
	public MyFile getMyFileByFileId(int myFileId) {
		// TODO Auto-generated method stub
		MyFile myFile = null;
		if (myFileId <= 0) {
			return null;
		}
		myFile = myFileDao.selectMyFIleById(myFileId);
		return myFile;
	}

	@Override
	public List<MyFile> getMyFileByOwnerId(int ownerId) {
		// TODO Auto-generated method stub
		List<MyFile> myFiles = null;
		MyFilesManager tempMyFileMan = null;

		// 判断文件管理员是否存在
		tempMyFileMan = myFileManDao.selectMFMById(ownerId);
		if (tempMyFileMan == null) {
			return null;
		}

		myFiles = myFileDao.selectMyFilesByOwner(ownerId);
		return myFiles;
	}

	@Override
	public Map updateMyFile(int userId, int fileId, MultipartFile updateFile) {
		// TODO Auto-generated method stub
		Map resultMap = new HashMap<String, String>();
		MyFilesManager myFilesManager = myFileManDao.selectMFMById(userId);
		MyFile myFile = myFileDao.selectMyFIleById(fileId);
		// 如果文件不存在
		if (myFile == null) {
			resultMap.put("status", "error");
			resultMap.put("error", "文件不存在");
			if (logger.isDebugEnabled()) {
				logger.debug("文件不存在");
			}
			return resultMap;
		}

		// 管理员不存在，则不能删除文件
		if (myFilesManager == null) {
			resultMap.put("status", "error");
			resultMap.put("error", "管理员不存在");
			if (logger.isDebugEnabled()) {
				logger.debug("管理员不存在");
			}
			return resultMap;
		}

		// 获取管理员权限
		ManPrivilege manPrivilege = myFilesManager.getManPrivilege();
		try {
			// 如果有主管理员权限或者有修改所有文件权限，则可以更新
			// 没有以上权限，则查看文件是否属于自己，属于则可以删除
			if (manPrivilege.getMainPVL() == 1
					|| manPrivilege.getUpdatePVL() == 1
					||myFile.getOwnerId() == userId) {
				File file = new File(savePath, myFile.getSaveName());
				// 文件不存在，则创建
				if (!file.getParentFile().exists()) {
					file.mkdirs();
				}
				//更改修改者Id,修改时间
				myFile.setLastModifiedId(userId);
				myFile.setLastModifiedDate(new Date());
				myFileDao.updateMyFile(myFile);
				updateFile.transferTo(file);
				myFile.setLastModifiedDate(new Date());
				myFile.setLastModifiedId(myFilesManager.getId());
				myFileDao.updateMyFile(myFile);
				resultMap.put("status", "success");
				if (logger.isDebugEnabled()) {
					logger.debug("文件重传成功");
				}
				return resultMap;
			}
			// 没权限，文件不属于自己，不能重传
			else {
				resultMap.put("status", "error");
				resultMap.put("error", "管理员没有权限");
				if (logger.isDebugEnabled()) {
					logger.debug("管理员没有权限");
				}
			}
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			resultMap.put("status", "error");
			resultMap.put("error", "文件更新出错");
			if (logger.isDebugEnabled()) {
				logger.debug("文件更新出错");
			}
			e.printStackTrace();
		}
		return resultMap;
	}

	@Override
	public Map deleteMyFile(int userId, int MyFileId) {
		// TODO Auto-generated method stub
		Map resultMap = new HashMap<String, String>();
		MyFilesManager myFilesManager = myFileManDao.selectMFMById(userId);
		MyFile myFile = myFileDao.selectMyFIleById(MyFileId);
		// 如果文件不存在
		if (myFile == null) {
			resultMap.put("status", "error");
			resultMap.put("error", "文件不存在");
			return resultMap;
		}

		// 管理员不存在，则不能删除文件
		if (myFilesManager == null) {
			resultMap.put("status", "error");
			resultMap.put("error", "管理员不存在");
			return resultMap;
		}
		// 获取管理员权限
		ManPrivilege manPrivilege = myFilesManager.getManPrivilege();
		// 如果有主管理员权限或者有修改所有文件权限，则可以删除
		//没有权限，如果文件属于自己，也可以删除
		if (manPrivilege.getMainPVL() == 1
				|| manPrivilege.getAllFilesPVL() == 1
				||myFile.getOwnerId() == userId) {
			File deleteFile=new File(savePath,myFile.getSaveName());
			
			//如果文件存在，则删除本地文件
			if(deleteFile.exists()){
				deleteFile.delete();
			}
			
			//删除数据库记录
			myFileDao.deleteMyFile(MyFileId);
			resultMap.put("status", "success");
			return resultMap;
		}else {
			resultMap.put("status", "error");
			resultMap.put("error", "管理员没权限");
		}

		return resultMap;
	}

	@Override
	public Map uploadMyFile(MultipartFile uploadFile, int ownerId) {
		// TODO Auto-generated method stub
		Map resultMap = new HashMap<String, String>();
		MyFilesManager myFilesManager=myFileManDao.selectMFMById(ownerId);
		
		//管理员检查
		if(myFilesManager==null){
			resultMap.put("status", "error");
			resultMap.put("error", "管理员不存在");
			resultMap.put("code", "100");
			if (logger.isDebugEnabled()) {
				logger.debug("管理员不存在");
			}
			return resultMap;
		}
		
		//权限检查，不是主管理员并且不具备上传权限
		if(myFilesManager.getManPrivilege().getMainPVL()!=1&&myFilesManager.getManPrivilege().getUpdatePVL()!=1){
			resultMap.put("status", "error");
			resultMap.put("error", "管理员没有权限上传文件");
			resultMap.put("code", "100");
			if (logger.isDebugEnabled()) {
				logger.debug("管理员没有权限上传文件");
			}
			return resultMap;
		}
		
//		文件存储
		MyFile myFile = new MyFile();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fileSuffixDateFormat=new SimpleDateFormat("yyyy-MM-dd_HHmmss");

		Date date = new Date();
		String formatDateString=simpleDateFormat.format(date);
		myFile.setCreateDate(formatDateString);
		myFile.setLastModifiedDate(date);
		myFile.setOwnerId(ownerId);
		myFile.setLastModifiedId(ownerId);
		myFile.setSavePath(savePath);
		String fileNmae = uploadFile.getOriginalFilename();
		String saveName= uploadFile.getOriginalFilename()+ fileSuffixDateFormat.format(date);
		myFile.setSaveName(saveName);
		myFile.setFileName(fileNmae);

		try {
			File saveFile = new File(savePath, myFile.getSaveName());
			// 存储路径不存在则创建文件
			if (!saveFile.getParentFile().exists()) {
				saveFile.mkdirs();
			}
			uploadFile.transferTo(saveFile);
		}catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			resultMap.put("status", "error");
			resultMap.put("error", "存储路径有问题");
			resultMap.put("code", "100");
			return resultMap;
		} 
		catch (IllegalStateException |IOException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("status", "error");
			resultMap.put("error", "文件读写出错");
			resultMap.put("code", "100");
			if (logger.isDebugEnabled()) {
				logger.debug("文件读写出错");
			}
			return resultMap;
		}
		myFileDao.insertMyFile(myFile);
		resultMap.put("status", "success");
		resultMap.put("success", "文件存储成功");
		resultMap.put("code", "000");
		if (logger.isDebugEnabled()) {
			logger.debug("文件上传成功");
		}
		return resultMap;
	}



	@Override
	public File getDownloadFile(int myFileId) {
		// TODO Auto-generated method stub
		MyFile myFile=myFileDao.selectMyFIleById(myFileId);
		//如果文件不存在，返回null
		if(myFile==null){
			return null;
		}
		
		File downloadFile=new File(savePath,myFile.getSaveName());
		if(!downloadFile.exists()){
			myFileDao.deleteMyFile(myFileId);
		}
		return downloadFile;
		
	}

	@Override
	public List<MyFile> searchFiles(String fileName) {
		// TODO Auto-generated method stub
		List<MyFile> searchFiles=null;
		searchFiles=myFileDao.selectMyFilesByName(fileName);
		return searchFiles;
	}

	

	@Override
	public Map continueUploadFile(MultipartFile file, String fileName,UUID fileNameSuffix, boolean isLast,int ownerId,int del_time) {
		// TODO Auto-generated method stub
		Map resultMap=new HashMap();
		
		//临时文件名
		String tmpFileName=fileName+fileNameSuffix;
		File tmpSaveFile=new File(savePath,tmpFileName);
			try {
				//如果文件不存在，则创建文件
				if(!tmpSaveFile.exists()){
					tmpSaveFile.createNewFile();
				}
				FileOutputStream fileOutputStream=new FileOutputStream(tmpSaveFile, true);
				fileOutputStream.write(file.getBytes());
				fileOutputStream.flush();
				fileOutputStream.close();
				if(isLast==true){
					resultMap.put("status", 200);
					//myFile对象存储入数据库
					MyFile myFile = new MyFile();
					//存储日期
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					//存储名称后缀
					SimpleDateFormat fileSuffixDateFormat=new SimpleDateFormat("yyyy-MM-dd_HHmmss");
					Date date = new Date();
					String formatDateString=simpleDateFormat.format(date);
					myFile.setCreateDate(formatDateString);
					myFile.setLastModifiedDate(date);
					myFile.setOwnerId(ownerId);
					myFile.setLastModifiedId(ownerId);
					myFile.setSavePath(savePath);
					String fileNmae = fileName;
					String saveName= fileName+ fileSuffixDateFormat.format(date);
					myFile.setSaveName(saveName);
					myFile.setFileName(fileNmae);
					myFileDao.insertMyFile(myFile);
					
					//临时文件重命名
					File saveFile=new File(savePath,saveName);
					tmpSaveFile.renameTo(saveFile);
					//删除临时文件
					tmpSaveFile.delete();
					resultMap.put("msg", "文件传输完成");
					
					//定时删除任务
					//如果不是永久文件
					if(del_time!=0&&(del_time==1||del_time==2||del_time==3)){
						MyFileDelSchedule myFileDelSchedule=new MyFileDelSchedule();
						Calendar calendar=Calendar.getInstance();
						calendar.setTime(date);
						//一天后删除
						if(del_time==1){
							calendar.add(Calendar.DAY_OF_MONTH, 1);
						}
						//一个星期后删除
						else if(del_time==2){
							calendar.add(Calendar.WEEK_OF_MONTH, 1);
						}
						//一个月后删除
						else if(del_time==3){
							calendar.add(Calendar.MONTH, 1);
						}
						myFileDelSchedule.setFileId(myFile.getId());
						myFileDelSchedule.setDelDate(simpleDateFormat.format(calendar.getTime()));
						logger.debug("================="+myFileDelSchedule.getDelDate()+"===============");
						logger.debug("================="+simpleDateFormat.format(calendar.getTime())+"===============");
						myFileDelScheduleDao.insertDelSchedule(myFileDelSchedule);
					}
					
				}
				resultMap.put("status", 200);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				resultMap.put("status",000);
				resultMap.put("msg","文件传输出错");
				e.printStackTrace();
			}
		
		return resultMap;
	}



	@Override
	public Map continueUpdateFile(MultipartFile file, String fileName,
			UUID fileNameSuffix, boolean isLast, int ownerId,int fileId) {
		// TODO Auto-generated method stub
		Map resultMap=new HashMap();
		
		MyFile updateFile=myFileDao.selectMyFIleById(fileId);
		MyFilesManager myFilesManager=myFileManDao.selectMFMById(ownerId);
		
		
		//如果文件不存在
		if(updateFile==null||updateFile.equals("")){
			resultMap.put("status",000);
			resultMap.put("msg","要更新的文件不存在");
			return resultMap;
		}
		
		// 管理员不存在，则不能删除文件
		if (myFilesManager == null) {
			resultMap.put("status", 000);
			resultMap.put("msg","管理员不存在");
			if (logger.isDebugEnabled()) {
				logger.debug("管理员不存在");
			}
			return resultMap;
		}
		
		//校验管理员权限
		// 获取管理员权限
		ManPrivilege manPrivilege = myFilesManager.getManPrivilege();
		
			// 如果有主管理员权限或者有修改所有文件权限，则可以更新
			// 没有以上权限，则查看文件是否属于自己，属于则可以删除
			if (manPrivilege.getMainPVL() != 1&& manPrivilege.getUpdatePVL() == 1&&updateFile.getOwnerId() == ownerId) {
				resultMap.put("status", 000);
				resultMap.put("msg","没有权限");
				return resultMap;
			}
			
		
		//临时文件名
		String tmpFileName=fileName+fileNameSuffix;
		File tmpSaveFile=new File(savePath,tmpFileName);
		
			try {
				//如果文件不存在，则创建文件
				if(!tmpSaveFile.exists()){
					tmpSaveFile.createNewFile();
				}
				FileOutputStream fileOutputStream=new FileOutputStream(tmpSaveFile, true);
				fileOutputStream.write(file.getBytes());
				fileOutputStream.flush();
				fileOutputStream.close();
				if(isLast==true){
					resultMap.put("status", 200);
					//存储日期
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					//存储名称后缀
					SimpleDateFormat fileSuffixDateFormat=new SimpleDateFormat("yyyy-MM-dd_HHmmss");
					Date date = new Date();
					String formatDateString=simpleDateFormat.format(date);
					
					//旧文件
					File oldFile=new File(savePath,updateFile.getSaveName());
					
					//数据库更新
				    updateFile.setLastModifiedDate(date);
				    updateFile.setOwnerId(ownerId);
				    updateFile.setLastModifiedId(ownerId);
				    updateFile.setSavePath(savePath);
					String saveName= updateFile.getFileName()+ fileSuffixDateFormat.format(date);
					updateFile.setSaveName(saveName);
					myFileDao.updateMyFile(updateFile);
					
					//临时文件重新命名
					File saveFile=new File(savePath,saveName);
					oldFile.delete();
					tmpSaveFile.renameTo(saveFile);
/*					//删除旧文件
					oldFile.delete();*/
					//删除临时文件
					tmpSaveFile.delete();
					resultMap.put("msg", "文件传输完成");
				}
				resultMap.put("status", 200);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				resultMap.put("msg","文件传输出错");
				e.printStackTrace();
			}
		
		return resultMap;
	}



	@Override
	public Map shareFile(int fileId,String sharePwd) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		MyFile shareFile=myFileDao.selectMyFIleById(fileId);
		MyFileShare myFileShare=null;
		String shareLink="";
		if(shareFile==null){
			map.put("status", 100);
			map.put("msg", "文件不存在");
		}else{
			myFileShare=new MyFileShare();
			myFileShare.setFileId(fileId);
			myFileShare.setSharePwd(sharePwd);
			try{
				myFileShareDao.insertMyFileShare(myFileShare);
				shareLink="/to_share.do?shareId="+myFileShare.getId();
				map.put("status", 200);
				map.put("msg", "设置成功");
				map.put("shareLink", shareLink);
			}catch(Exception e){
				map.put("status", 100);
				map.put("msg", "内部错误");
			}
		}
		return map;
	}



	@Override
	public Map shareFileSearch(int shareId, String sharePwd) {
		// TODO Auto-generated method stub
		Map resultMap=new HashMap();
		MyFile shareFile=null;
		MyFileShare myFileShare=null;
		
		myFileShare=myFileShareDao.selectMyFileShareById(shareId);
		if(myFileShare==null){
			resultMap.put("status", 100);
			resultMap.put("msg", "文件不存在");
		}else{
			shareFile=myFileDao.selectMyFIleById(myFileShare.getFileId());
			if(shareFile==null){
				resultMap.put("status", 101);
				resultMap.put("msg", "文件不存在");
			}
			else{
				if(!sharePwd.equals(myFileShare.getSharePwd())){
					resultMap.put("status", 102);
					resultMap.put("msg", "提取码不正确");
				}else{
					resultMap.put("status", 200);
					resultMap.put("msg", "共享文件提取成功");
				}
			}
		}
		return resultMap;
	}



	@Override
	public File getShareFile(int shareId,String sharePwd) {
		// TODO Auto-generated method stub
		File shareFile=null;
		MyFileShare myFileShare=null;
		MyFile myFile=null;
		
		myFileShare=myFileShareDao.selectMyFileShareById(shareId);
		
		if(myFileShare.getSharePwd().equals(sharePwd)){
			myFile=myFileDao.selectMyFIleById(myFileShare.getFileId());
			if(myFile!=null){
				shareFile=new File(savePath, myFile.getSaveName());
			}
		}
		return shareFile;
	}



	@Override
	public MyFile getMyFileByShareId(int shareId) {
		// TODO Auto-generated method stub
		logger.debug("==================shareId:"+shareId+"===============");
		MyFile myFile=null;
		MyFileShare myFileShare=null;
		myFileShare=myFileShareDao.selectMyFileShareById(shareId);
		logger.debug("==================myFileShare:"+myFileShare+"===============");
		if(myFileShare!=null){
			myFile=myFileDao.selectMyFIleById(myFileShare.getFileId());
			logger.debug("==================myFile:"+myFile+"===============");
		}
		return myFile;
	}
	

	}
