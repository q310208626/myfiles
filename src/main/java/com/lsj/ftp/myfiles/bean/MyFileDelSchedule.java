package com.lsj.ftp.myfiles.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

//定时删除Bean
@Component
public class MyFileDelSchedule {
	private int id;
	private int fileId;
	private String delDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String getDelDate() {
		return delDate;
	}
	public void setDelDate(String delDate) {
		this.delDate = delDate;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer();
		sb.append("id:"+getId());
		sb.append("\nfileId:"+getFileId());
		sb.append("\ndelDate:"+getDelDate());
		return sb.toString();
	}
	
	
	
}
