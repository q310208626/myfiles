package com.lsj.ftp.myfiles.context.listener;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lsj.ftp.myfiles.task.DelScheduleTask;


//定时删除任务启动
@Component
public class DelScheduleListener implements ServletContextListener{
	private Timer timer;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		timer=new Timer();
		timer.schedule(new DelScheduleTask(),0,60*1000);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
	

}
