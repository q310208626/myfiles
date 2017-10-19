package com.lsj.ftp.myfiles;
import java.util.List;

import javax.transaction.Transactional;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.lf5.util.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import com.lsj.ftp.myfiles.bean.ManPrivilege;
import com.lsj.ftp.myfiles.bean.MyFile;
import com.lsj.ftp.myfiles.bean.MyFilesManager;
import com.lsj.ftp.myfiles.dao.ManPrivilegeDao;
import com.lsj.ftp.myfiles.dao.MyFileDao;
import com.lsj.ftp.myfiles.dao.MyFilesManDao;
import com.lsj.ftp.myfiles.service.MyFileService;
import com.lsj.ftp.myfiles.service.MyFilesManService;
import com.lsj.ftp.myfiles.serviceImpl.MyFilesManServiceImpl;
import com.mysql.fabric.xmlrpc.base.Data;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:springmvc.xml"})
//@Transactional
public class MyFilesTest {
	
	@Autowired
	private MyFilesManServiceImpl myFilesManServiceImpl;
	
	@Autowired
	@Qualifier("myFileServiceImpl")
	private MyFileService myFileService;
	@Autowired
	private MyFileDao myFileDao;
	private ApplicationContext applicationContext;
	
	
	@Before
	public void before(){
		applicationContext=new ClassPathXmlApplicationContext("classpath:springmvc.xml");
	}
	
	@Test
	public void test(){
		MyFilesManager myFilesManager=applicationContext.getBean(MyFilesManager.class);
		
		MyFile myFile=applicationContext.getBean(MyFile.class);
		File file=new File("D:"+File.separator+"ceshi.txt");
		FileInputStream fin;
		try {
			fin = new FileInputStream(file);
		MultipartFile updateFile;
			updateFile = new MockMultipartFile("ceshi.txt",file.getName(), "text/plain", fin);
			myFileService.updateMyFile(5, 5,updateFile );
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@After
	public void after(){
		
	}
}
