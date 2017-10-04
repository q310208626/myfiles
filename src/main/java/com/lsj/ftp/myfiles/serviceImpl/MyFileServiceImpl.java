package com.lsj.ftp.myfiles.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
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
	private static String savePath = "/home/shaojia/myFiles/upload";

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
	public Map updateMyFile(MyFile myFile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map deleteMyFile(int MyFileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map uploadMyFile(MultipartFile uploadFile, int ownerId) {
		// TODO Auto-generated method stub
		Map resultMap = new HashMap<String, String>();
		MyFile myFile = new MyFile();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		myFile.setCreateDate(date);
		myFile.setLastModifiedDate(date);
		myFile.setOwnerId(ownerId);
		myFile.setLastModifiedId(ownerId);
		myFile.setSavePath(savePath);
		String fileNmae = uploadFile.getOriginalFilename() + date;
		myFile.setFileName(fileNmae);
		
		try {
			File saveFile = new File(savePath, myFile.getFileName());
			// 存储路径不存在则创建文件
			if (!saveFile.exists()) {
				saveFile.mkdirs();
			}
			uploadFile.transferTo(saveFile);
		} catch (IllegalStateException | IOException e) {
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

}
