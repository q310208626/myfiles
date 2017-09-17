package com.lsj.ftp.myfiles;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.lf5.util.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lsj.ftp.myfiles.bean.ManPrivilege;
import com.lsj.ftp.myfiles.bean.MyFilesManager;
import com.lsj.ftp.myfiles.dao.ManPrivilegeDao;
import com.lsj.ftp.myfiles.dao.MyFilesManDao;
import com.lsj.ftp.myfiles.service.MyFilesManService;
import com.lsj.ftp.myfiles.serviceImpl.MyFilesManServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:springmvc.xml"})
//@Transactional
public class MyFilesTest {
	
	@Autowired
	private MyFilesManServiceImpl myFilesManServiceImpl;
	private MyFilesManService myFilesManService;
	
	private ApplicationContext applicationContext;
	
	
	@Before
	public void before(){
		applicationContext=new ClassPathXmlApplicationContext("classpath:springmvc.xml");
	}
	
	@Test
	public void test(){
		MyFilesManager myFilesManager=applicationContext.getBean(MyFilesManager.class);
		myFilesManager.setAccount("linshaojia");
		myFilesManager.setPassword("lin123");
		myFilesManServiceImpl.registFileManager(myFilesManager);
		
	}
	
	@After
	public void after(){
		
	}
}
