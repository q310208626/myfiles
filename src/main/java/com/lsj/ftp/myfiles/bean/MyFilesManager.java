package com.lsj.ftp.myfiles.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shaojia
 *	文件管理员
 *	管理员注册需要进行审批并且赋予权限
 */
@Component
public class MyFilesManager {
	
	private int id;
	private String account;
	private String password;
	//权限表
	private ManPrivilege manPrivilege;
	//判断管理员是否通过审核，通过审核才能进行管理员操作，0为没有，1为通过
	private int isActivited=0;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ManPrivilege getManPrivilege() {
		return manPrivilege;
	}
	public void setManPrivilege(ManPrivilege manPrivilege) {
		this.manPrivilege = manPrivilege;
	}
	public int getIsActivited() {
		return isActivited;
	}
	public void setIsActivited(int isActivited) {
		this.isActivited = isActivited;
	}
	
}
