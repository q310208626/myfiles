package com.lsj.ftp.myfiles.dao;

import org.springframework.stereotype.Repository;

import com.lsj.ftp.myfiles.bean.ManPrivilege;

/**
 * @author shaojia
 * 权限dao层
 */
@Repository
public interface ManPrivilegeDao {
	
	public void insertPVL(ManPrivilege manPrivilege);
	public void updatePVL(ManPrivilege manPrivilege);
	public void selectPVL(int id);
	//无删除权限操作，只有插入跟修改
}
