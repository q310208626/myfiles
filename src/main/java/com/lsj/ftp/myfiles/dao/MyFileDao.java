package com.lsj.ftp.myfiles.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lsj.ftp.myfiles.bean.MyFile;

/**   
*    
* 项目名称：myfiles   
* 类名称：MyFileDao   
* 类描述：   
* 创建人：Administrator   
* 创建时间：2017年9月27日 上午2:50:22   
* 修改人：Administrator   
* 修改时间：2017年9月27日 上午2:50:22   
* 修改备注：MyFile的Dao接口   
* @version    
*    
*/
@Repository
public interface MyFileDao {
	//
	public void insertMyFile(MyFile myFile);
	public void updateMyFile(MyFile myFile);
	public void deleteMyFile(int fileId);
	public MyFile selectMyFIleById(int fileId);
	public List<MyFile> selectMyFilesByOwner(int ownerId);
	public List<MyFile> selectMyFileByOwnerAndByPage(int ownerId,int startIndex,int count);
	public List<MyFile> selectAllMyFiles();
	public List<MyFile> selectMyFilesByName(String fileName);
	public List<MyFile> selectAllMyByPage(int startIndex,int count);
	public List<MyFile> selectAllMyByPageAndFileName(int startIndex,int count,String fileName);
	public int selectMyFilesCount();
}
