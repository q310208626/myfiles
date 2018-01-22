package com.lsj.ftp.myfiles.bean;

import org.springframework.stereotype.Component;

/**
 * @author shaojia
 *	管理员权限
 *	0：无权限
 *	1：有权限
 */
@Component
public class ManPrivilege {
	private int id;
	//可以上传文件
	private int uploadPVL=0;
	//可以修改‘自己’的文件
	private int updatePVL=0;
	//可以授权管理员（可以添加管理员，并授权，修改权限）
	private int grantPVL=0;
	//所有文件权限，包括修改他人文件
	private int allFilesPVL=0;
	//主管理员权限（不显示出来，初始管理员，有最高权限）
	private int mainPVL=0;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUploadPVL() {
		return uploadPVL;
	}
	public void setUploadPVL(int uploadPVL) {
		this.uploadPVL = uploadPVL;
	}
	public int getUpdatePVL() {
		return updatePVL;
	}
	public void setUpdatePVL(int updatePVL) {
		this.updatePVL = updatePVL;
	}
	public int getGrantPVL() {
		return grantPVL;
	}
	public void setGrantPVL(int grantPVL) {
		this.grantPVL = grantPVL;
	}
	public int getAllFilesPVL() {
		return allFilesPVL;
	}
	public void setAllFilesPVL(int allFilesPVL) {
		this.allFilesPVL = allFilesPVL;
	}
	public int getMainPVL() {
		return mainPVL;
	}
	public void setMainPVL(int mainPVL) {
		this.mainPVL = mainPVL;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Main:"+mainPVL+"\nupload:"+uploadPVL+"\nupdate:"+updatePVL+"\ngrant:"+grantPVL+"\nallFile:"+allFilesPVL;
	}
	
	
}
