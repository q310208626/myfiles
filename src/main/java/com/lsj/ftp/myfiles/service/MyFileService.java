package com.lsj.ftp.myfiles.service;

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
	 * @Description: TODO 获取文件列表
	 * @param id 管理员Id
	 * @return      
	 * @return: List<MyFile>      
	 * @throws   
	 */  
	public List<MyFile> getMyFilesTable(int id);
	
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
	 * @Description: TODO 更新MyFile
	 * @return      
	 * @return: Map      
	 * @throws   
	 */  
	public Map updateMyFile(MyFile myFile);
	
	/**   
	 * @Title: deleteMyFile   
	 * @Description: TODO 通过Id删除MyFile
	 * @param MyFileId 文件Id
	 * @return      
	 * @return: Map      
	 * @throws   
	 */  
	public Map deleteMyFile(int MyFileId);
	

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
	
}
