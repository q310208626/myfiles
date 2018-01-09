package com.lsj.ftp.myfiles.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lsj.ftp.myfiles.bean.ManPrivilege;
import com.lsj.ftp.myfiles.bean.MyFile;
import com.lsj.ftp.myfiles.bean.MyFilesManager;
import com.lsj.ftp.myfiles.dao.MyFileDao;
import com.lsj.ftp.myfiles.dao.MyFilesManDao;
import com.lsj.ftp.myfiles.service.MyFileService;
import com.mysql.fabric.xmlrpc.base.Data;

@Service
public class MyFileServiceImpl implements MyFileService {

	@Autowired
	private MyFileDao myFileDao;
	@Autowired
	private MyFilesManDao myFileManDao;
	//private static String savePath = "/home/shaojia/myFiles/upload";
	private static String savePath ="E:\\myfiles\\save";

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
	public List<MyFile> getCustomerFilesTable() {
		// TODO Auto-generated method stub
		List<MyFile> myFiles = null;
		myFiles=myFileDao.selectAllMyFiles();
		return myFiles;
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
		try {
			// 如果有主管理员权限或者有修改所有文件权限，则可以更新
			// 没有以上权限，则查看文件是否属于自己，属于则可以删除
			if (manPrivilege.getMainPVL() == 1
					|| manPrivilege.getUpdatePVL() == 1
					||myFile.getOwnerId() == userId) {
				File file = new File(savePath, myFile.getSaveName());
				// 文件不存在，则创建
				if (!file.exists()) {
					file.mkdirs();
				}
				updateFile.transferTo(file);
				myFile.setLastModifiedDate(new Date());
				myFile.setLastModifiedId(myFilesManager.getId());
				myFileDao.updateMyFile(myFile);
				resultMap.put("status", "success");
				return resultMap;
			}
			// 没权限，文件不属于自己，不能删除
			else {
				resultMap.put("status", "error");
				resultMap.put("error", "管理员没有权限");
			}
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			resultMap.put("status", "error");
			resultMap.put("error", "文件更新出错");
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
		MyFile myFile = new MyFile();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd_HHmmss");
		Date date = new Date();

		myFile.setCreateDate(date);
		myFile.setLastModifiedDate(date);
		myFile.setOwnerId(ownerId);
		myFile.setLastModifiedId(ownerId);
		myFile.setSavePath(savePath);
		String fileNmae = uploadFile.getOriginalFilename();
		String saveName= uploadFile.getOriginalFilename()+ simpleDateFormat.format(date);
		myFile.setSaveName(saveName);
		myFile.setFileName(fileNmae);

		try {
			File saveFile = new File(savePath, myFile.getSaveName());
			// 存储路径不存在则创建文件
			if (!saveFile.exists()) {
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
			return resultMap;
		}
		myFileDao.insertMyFile(myFile);
		resultMap.put("status", "success");
		resultMap.put("success", "文件存储成功");
		resultMap.put("code", "000");
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
		return downloadFile;
		
	}

	@Override
	public List<MyFile> searchFiles(String fileName) {
		// TODO Auto-generated method stub
		List<MyFile> searchFiles=null;
		searchFiles=myFileDao.selectMyFilesByName(fileName);
		return searchFiles;
	}
	

}
