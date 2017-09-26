package com.lsj.ftp.myfiles.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lsj.ftp.myfiles.bean.MyFilesManager;


/**   
*    
* 项目名称：myfiles   
* 类名称：MyFilesManDao   
* 类描述：   
* 创建人：Administrator   
* 创建时间：2017年9月27日 上午2:55:47   
* 修改人：Administrator   
* 修改时间：2017年9月27日 上午2:55:47   
* 修改备注：文件管理员Dao  
* @version 1.0   
*    
*/
@Repository
public interface MyFilesManDao {
	
	public void insertMFM(MyFilesManager myFilesManager);
	
	public void updateMFM(MyFilesManager myFilesManager);
	
	public void deleteMFM(int id);
	
	public MyFilesManager selectMFMById(int id);
	
	public MyFilesManager selectMFMByAccount(String account);

	public List<MyFilesManager> selectMFMIsActivited();
}
