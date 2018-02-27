package com.lsj.ftp.myfiles.dao;

import org.springframework.stereotype.Repository;

import com.lsj.ftp.myfiles.bean.MyFileShare;

@Repository
public interface MyFileShareDao {
	public void insertMyFileShare(MyFileShare myFileShare);
	public MyFileShare selectMyFileShareById(int id); 
}
