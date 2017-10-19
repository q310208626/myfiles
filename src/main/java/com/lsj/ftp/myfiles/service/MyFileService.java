package com.lsj.ftp.myfiles.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.lsj.ftp.myfiles.bean.MyFile;

/**   
*    
* 项目名称：myfiles   
* 类名称：MyFileService   
* 类描述：MyFile类的Service层   
* 创建人：shaojia   
* 创建时间：Oct 2, 2017 4:02:44 PM   
* 修改人：shaojia   
* 修改时间：Oct 2, 2017 4:02:44 PM   
* 修改备注：   
* @version    
*    
*/
public interface MyFileService {


	
	/**   
	 * @Title: getMyFilesTable   
	 * @Description: TODO 获取管理员文件列表
	 * @param id 管理员Id
	 * @return      
	 * @return: List<MyFile>      
	 * @throws   
	 */  
	public List<MyFile> getMyFilesTable(int id);
	
	/**   
	 * @Title: getMyFilesTableByPage   
	 * @Description: TODO 获取管理员文件列表(分页)
	 * @param id
	 * @param startIndex
	 * @param count
	 * @return      
	 * @return: List<MyFile>      
	 * @throws   
	 */  
	public List<MyFile> getMyFilesTableByPage(int id,int startIndex,int count);
	
	/**   
	 * @Title: getCustomerFilesTable   
	 * @Description: TODO 获取访客文件列表
	 * @return      
	 * @return: List<MyFile>      
	 * @throws   
	 */  
	public List<MyFile> getCustomerFilesTable();
	

	/**   
	 * @Title: getCustomerFilesTableByPage   
	 * @Description: TODO 通过页数获取方可文件列表
	 * @param startIndex 起始下表
	 * @param pageCount 每页多少文件
	 * @return      
	 * @return: List<MyFile>      
	 * @throws   
	 */  
	public List<MyFile> getCustomerFilesTableByPage(int startIndex,int pageCount);
	
	/**   
	 * @Title: getMyFilesCount   
	 * @Description: TODO 获取文件数量   
	 * @return      
	 * @return: int      
	 * @throws   
	 */  
	public int getMyFilesCount();
	
	/**   
	 * @Title: getMyFileByFileId   
	 * @Description: TODO 通过文件Id获取文件
	 * @param myFileId
	 * @return      
	 * @return: MyFile      
	 * @throws   
	 */  
	public MyFile getMyFileByFileId(int myFileId);
	
	/**   
	 * @Title: getMyFileByOwnerId   
	 * @Description: TODO 通过文件管理员Id获取文件列表
	 * @param ownerId
	 * @return      
	 * @return: List<MyFile>      
	 * @throws   
	 */  
	public List<MyFile> getMyFileByOwnerId(int ownerId);

	/**   
	 * @Title: updateMyFile   
	 * @Description: TODO
	 * @param fileId 文件Id
	 * @param updateFile 更新文件
	 * @param userId 操作者Id
	 * @return      
	 * @return: Map      
	 * @throws   
	 */  
	public Map updateMyFile(int userId,int fileId,MultipartFile updateFile);
	
	/**   
	 * @Title: deleteMyFile   
	 * @Description: TODO 通过Id删除MyFile
	 * @param MyFileId 文件Id
	 * @param userId 操作者Id
	 * @return      
	 * @return: Map      
	 * @throws   
	 */  
	public Map deleteMyFile(int userId,int MyFileId);
	

	/**   
	 * @Title: uploadMyFile   
	 * @Description: TODO 上传文件
	 * @param uploadFile 文件
	 * @param ownerId 上传者Id
	 * @return      
	 * @return: Map      
	 * @throws   
	 */  
	public Map uploadMyFile(MultipartFile uploadFile,int ownerId);
	
	/**   
	 * @Title: getDownloadFile   
	 * @Description: TODO 获取要下载的文件
	 * @param myFileId
	 * @return      
	 * @return: File      
	 * @throws   
	 */  
	public File getDownloadFile(int myFileId);
	
	
}
