package com.lsj.ftp.myfiles.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lsj.ftp.myfiles.bean.MyFileDelSchedule;

@Repository
public interface MyFileDelScheduleDao {
	public List<MyFileDelSchedule> selectAllDelSchedule();
	public MyFileDelSchedule selectDelScheduleByFileId(int fileId);
	public void insertDelSchedule(MyFileDelSchedule myFileDelSchedule);
	public void deleteDelSchedule(int scheduleId);
}
