package com.lsj.ftp.myfiles.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lsj.ftp.myfiles.bean.MyFilesManager;

/**
 * @author shaojia
 *	文件管理员DaoI
 */
@Repository
public interface MyFilesManDao {
	
	public void insertMFM(MyFilesManager myFilesManager);
	
	public void updateMFM(MyFilesManager myFilesManager);
	
	public void deleteMFM(int id);
	
	public MyFilesManager selectMFMById(int id);
	
	public MyFilesManager selectMFMByAccount(String account);

	public List<MyFilesManager> selectMFMIsActivited();
}
