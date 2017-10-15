package com.lsj.ftp.myfiles.service;

import java.util.List;
import java.util.Map;
import com.lsj.ftp.myfiles.bean.ManPrivilege;
import com.lsj.ftp.myfiles.bean.MyFilesManager;

/**   
*    
* 项目名称：myfiles   
* 类名称：MyFilesManService   
* 类描述：文件管理员服务（Service）
* 创建人：shaojia   
* 创建时间：Aug 31, 2017 8:31:06 PM   
* 修改人：shaojia   
* 修改时间：Aug 31, 2017 8:31:06 PM   
* 修改备注：   
* @version    
*    
*/
public interface MyFilesManService {
	

 
	/**   
	 * @Title: resgistFileManager   
	 * @Description: TODO注册文件管理员（需要激活）
	 * @param myFilesManager      
	 * @return: Map 返回结果集      
	 * @throws   
	 */  
	public Map registFileManager(MyFilesManager myFilesManager);
	
	/**   
	 * @Title: loginFileManager   
	 * @Description: TODO 文件管理员登录
	 * @param myFilesManager      
	 * @return: Map 返回结果集      
	 * @throws   
	 */  
	public Map loginFileManager(MyFilesManager myFilesManager);

	/**   
	 * @Title: activeFileManager   
	 * @Description: TODO
	 * @param doId	操作者Id(根据Id判断是否有权限修改)
	 * @param doneId 被激活者Id
	 * @return: Map      
	 * @throws   
	 */  
	public Map activeFileManager(int doId,int doneId);
	
	/**   
	 * @Title: freezeFileManager   
	 * @Description: TODO 冻结用户
	 * @param doId
	 * @param doneId      
	 * @return: Map      
	 * @throws   
	 */  
	public Map freezeFileManager(int doId,int doneId);
	
	/**   
	 * @Title: updateFileManager   
	 * @Description: TODO修改一个文件管员信息，包括权限的修改
	 * @param doId	操作者Id(根据Id判断是否有权限修改)
	 * @param myFilesManager 
	 * @return: void      
	 * @throws   
	 */
	public void updateFileManager(int doId,MyFilesManager myFilesManager);
	
	/**   
	 * @Title: selectFileManagerIsActivited   
	 * @Description: TODO 查询还没被激活的管理员
	 * @return      
	 * @return: List<MyFilesManager>      
	 * @throws   
	 */  
	public List<MyFilesManager> selectFileManagerIsActivited();
	
	/**   
	 * @Title: getAllFileManager   
	 * @Description: TODO 获取文件管理员列表
	 * @return      
	 * @return: List<MyFilesManager>      
	 * @throws   
	 */  
	public List<MyFilesManager> getAllFileManager();
	
 
	/**   
	 * @Title: updateMFMPrivilege   
	 * @Description: TODO 更新权限
	 * @param doId 操作员Id
	 * @param manPrivilege 权限
	 * @return      
	 * @return: Map      
	 * @throws   
	 */  
	public Map updateMFMPrivilege(int doId,ManPrivilege manPrivilege);
}
