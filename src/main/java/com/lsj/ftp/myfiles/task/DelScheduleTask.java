package com.lsj.ftp.myfiles.task;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lsj.ftp.myfiles.bean.MyFile;
import com.lsj.ftp.myfiles.bean.MyFileDelSchedule;
import com.lsj.ftp.myfiles.dao.MyFileDao;
import com.lsj.ftp.myfiles.dao.MyFileDelScheduleDao;
import com.lsj.ftp.myfiles.serviceImpl.MyFileServiceImpl;

@Component
public class DelScheduleTask extends TimerTask{
	@Autowired
	private MyFileDelScheduleDao myFileDelScheduleDao;
	@Autowired 
	private MyFileDao myFileDao;
	private static String savePath ="E:\\myfiles\\save";
	private Logger logger=Logger.getLogger(DelScheduleTask.class);
	private static SimpleDateFormat simpleDateFormat;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		logger.debug("=========进行定时删除==========");
		simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<MyFileDelSchedule> myFileDelSchedules=myFileDelScheduleDao.selectAllDelSchedule();
		for (MyFileDelSchedule myFileDelSchedule : myFileDelSchedules) {
			MyFile myFile;
			myFile=myFileDao.selectMyFIleById(myFileDelSchedule.getFileId());
			File delScheduleFile=new File(savePath,myFile.getSaveName());
			logger.debug("==================="+myFile.getFileName()+" "+delScheduleFile.getAbsoluteFile()+" is exists:"+delScheduleFile.exists());
			//文件存在，则删除本地存储文件，并删除数据库记录
			if(delScheduleFile.exists()){
				try {
					if(simpleDateFormat.parse(myFileDelSchedule.getDelDate()).compareTo(new Date())<=0){
						logger.debug(simpleDateFormat.parse(myFileDelSchedule.getDelDate()).compareTo(new Date()));
						boolean isDelete=false;
						isDelete=delScheduleFile.delete();
						if(isDelete){
							myFileDelScheduleDao.deleteDelSchedule(myFileDelSchedule.getId());
						}
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//文件不存在，则删除数据库记录
			else{
				myFileDelScheduleDao.deleteDelSchedule(myFileDelSchedule.getId());
			}
		}
		logger.debug("=========定时删除结束==========");
	}
	
}
