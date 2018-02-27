package com.lsj.ftp.myfiles.service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
/**
 * @author rentutu
 *
 */
/**   
*    
* 项目名称：myfiles-master   
* 类名称：MyFileService   
* 类描述：   
* 创建人：rentutu   
* 创建时间：2018年1月5日 下午3:21:06   
* 修改人：rentutu   
* 修改时间：2018年1月5日 下午3:21:06   
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
	public List<MyFile> getMyFilesTableByPage(int id,int startIndex,int count,String fileName);
	
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
	public List<MyFile> getCustomerFilesTableByPage(int startIndex,int pageCount,String fileName);
	
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
	

	/**   
	 * @Title: searchFiles   
	 * @Description: TODO(通过名字搜索文件)   
	 * @param fileName 文件名
	 * @return      
	 * @return: List<MyFile>      
	 * @throws   
	 */  
	public List<MyFile> searchFiles(String fileName);
	

	/**   
	 * @Title: continueUploadFile   
	 * @Description: TODO(文件续传)   Service
	 * @param file 续传文件
	 * @param fileName 文件名
	 * @param isLast 是否是最后分段
	 * @param fileNameSuffix 临时文件后缀
	 * @param ownerId 管理员Id
	 * @param del_time 定时删除时间
	 * @return      
	 * @return: Map      
	 * @throws   
	 */ 
	public Map continueUploadFile(MultipartFile file,String fileName,UUID fileNameSuffix,boolean isLast,int ownerId,int del_time);
	
	/**   
	 * @Title: continueUpdateFile   
	 * @Description: TODO(文件续传) 更新   
	 * @param file
	 * @param fileName
	 * @param fileNameSuffix
	 * @param isLast
	 * @param ownerId
	 * @return      
	 * @return: Map      
	 * @throws   
	 */ 
	public Map continueUpdateFile(MultipartFile file,String fileName,UUID fileNameSuffix,boolean isLast,int ownerId,int fileId);
	
	
	
	/**   
	 * @Title: shareFile   
	 * @Description: TODO(文件分享)   
	 * @param fileId 分享的文件Id
	 * @param sharePwd 分享密码
	 * @return      
	 * @return: Boolean      
	 * @throws   
	 */ 
	public Map shareFile(int fileId,String sharePwd);
	
	/**   
	 * @Title: shareFileSearch   
	 * @Description: TODO(文件共享查询)   
	 * @param shareId	共享文件Id
	 * @param sharePwd	提取密码
	 * @return      
	 * @return: Map      
	 * @throws   
	 */ 
	public Map shareFileSearch(int shareId,String sharePwd);
	
	/**   
	 * @Title: getShareFile   
	 * @Description: TODO(获取共享文件)   
	 * @param shareId  共享文件Id
	 * @param sharePwd 提取密码
	 * @return      
	 * @return: File      
	 * @throws   
	 */ 
	public File getShareFile(int shareId,String sharePwd);
	
	/**   
	 * @Title: getMyFileByShareId   
	 * @Description: TODO(根据shareId获取myFile对象)   
	 * @param shareId
	 * @return      
	 * @return: MyFile      
	 * @throws   
	 */ 
	public MyFile getMyFileByShareId(int shareId);
		
}
