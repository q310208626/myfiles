package com.lsj.ftp.myfiles.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.lsj.ftp.myfiles.bean.ManPrivilege;
import com.lsj.ftp.myfiles.bean.MyFilesManager;
import com.lsj.ftp.myfiles.dao.ManPrivilegeDao;
import com.lsj.ftp.myfiles.dao.MyFilesManDao;
import com.lsj.ftp.myfiles.service.MyFilesManService;

/**   
*    
* 项目名称：myfiles   
* 类名称：MyFilesManServiceImpl   
* 类描述：文件管理员服务类实现
* 创建人：shaojia   
* 创建时间：Aug 31, 2017 8:57:18 PM   
* 修改人：shaojia   
* 修改时间：Aug 31, 2017 8:57:18 PM   
* 修改备注：   
* @version    
*    
*/
@Service
@Transactional
public class MyFilesManServiceImpl implements MyFilesManService{
	
	@Autowired
	private MyFilesManDao myFilesManDao;
	@Autowired
	private ManPrivilegeDao manPrivilegeDao;
	
	Logger logger=Logger.getLogger(MyFilesManServiceImpl.class);
	private ApplicationContext applicationContext;
	
	/* (non-Javadoc)
	 * @see com.lsj.ftp.myfiles.service.MyFilesManService#resgistFileManager(com.lsj.ftp.myfiles.bean.MyFilesManager)
	 */
	@Override
	public Map registFileManager(MyFilesManager myFilesManager) {
		Map<String, String> resultMap=new HashMap<String, String>(); 
		try {
			applicationContext=new ClassPathXmlApplicationContext("classpath:springmvc.xml");
			ManPrivilege manPrivilege=applicationContext.getBean(ManPrivilege.class);
			
			//临时MyFilesManager
			MyFilesManager tempFilesManager=null;
			
			//查询账户是否已经存在
			tempFilesManager=myFilesManDao.selectMFMByAccount(myFilesManager.getAccount());
			
			//如果临时管理员查询存在
			if(tempFilesManager!=null){
				resultMap.put("status", "error");
				resultMap.put("msg", "用户名已存在");
				if(logger.isDebugEnabled()){
					logger.debug("用户名已存在");
				}
			}else{
				//插入管理员到数据库
				myFilesManDao.insertMFM(myFilesManager);
				//获取插入的管理员Id作为权限主键Id
				manPrivilege.setId(myFilesManager.getId());
				manPrivilegeDao.insertPVL(manPrivilege);
				resultMap.put("status", "success");
				resultMap.put("msg", "注册成功");
				if(logger.isDebugEnabled()){
					logger.debug("注册成功");
				}
			}

		} catch (BeansException e) {
			// TODO Auto-generated catch block
			if(logger.isDebugEnabled()){
				logger.debug("获取javaBean失败"+ManPrivilege.class.getName());
			}
			e.printStackTrace();
		}catch(Exception e){
			if(logger.isDebugEnabled()){
				logger.debug("注册时发生错误");
			}
		}
		return resultMap;
		
	}

	/* (non-Javadoc)
	 * @see com.lsj.ftp.myfiles.service.MyFilesManService#activeFileManager(int, int)
	 */
	@Override
	public Map activeFileManager(int doId, int doneId) {
		// TODO Auto-generated method stub
		Map resultMap=new HashMap<String,String>();
		MyFilesManager doFilesManager=myFilesManDao.selectMFMById(doId);
		MyFilesManager doneFilesManager=myFilesManDao.selectMFMById(doneId);
		
		if(doFilesManager==null){
			if(logger.isDebugEnabled()){
				logger.debug("操作管理员不存在");
			}
			resultMap.put("status", "error");
			resultMap.put("error", "操作管理员不存在");
			return resultMap;
		}
		
		
		if(doneFilesManager==null){
			if(logger.isDebugEnabled()){
				logger.debug("被激活的管理员不存在");
			}
			resultMap.put("status", "error");
			resultMap.put("error", "被激活的管理员不存在");
			return resultMap;
		}
		
		//检查操作管理员是否有授权权限
		if(doFilesManager.getManPrivilege().getMainPVL()==0&&doFilesManager.getManPrivilege().getGrantPVL()==0){
			if(logger.isDebugEnabled()){
				logger.debug("操作管理员不存在授权权限，无法激活用户!");
			}
			resultMap.put("status", "error");
			resultMap.put("error", "操作管理员不存在授权权限，无法激活用户!");
			return resultMap;
		}
		
		doneFilesManager.setIsActivited(1);
		myFilesManDao.updateMFM(doneFilesManager);
		if(logger.isDebugEnabled()){
			logger.debug("管理员激活成功！");
		}
		resultMap.put("status", "success");
		return resultMap;
	}
	
	

	@Override
	public Map freezeFileManager(int doId, int doneId) {
		// TODO Auto-generated method stub
		Map resultMap=new HashMap<String,String>();
		MyFilesManager doFilesManager=myFilesManDao.selectMFMById(doId);
		MyFilesManager doneFilesManager=myFilesManDao.selectMFMById(doneId);
		
		if(doFilesManager==null){
			if(logger.isDebugEnabled()){
				logger.debug("操作管理员不存在");
			}
			resultMap.put("status", "error");
			resultMap.put("error", "操作管理员不存在");
			return resultMap;
		}
		
		
		if(doneFilesManager==null){
			if(logger.isDebugEnabled()){
				logger.debug("被冻结的管理员不存在");
			}
			resultMap.put("status", "error");
			resultMap.put("error", "被冻结的管理员不存在");
			return resultMap;
		}
		
		//检查操作管理员是否有授权权限
		if(doFilesManager.getManPrivilege().getMainPVL()==0&&doFilesManager.getManPrivilege().getGrantPVL()==0){
			if(logger.isDebugEnabled()){
				logger.debug("操作管理员不存在授权权限，无法冻结用户!");
			}
			resultMap.put("status", "error");
			resultMap.put("error", "操作管理员不存在授权权限，无法冻结用户!");
			return resultMap;
		}

		//如果被注销的是主管理员，则无法被注销
		if(doneFilesManager.getManPrivilege().equals(1)){
			if(logger.isDebugEnabled()){
				logger.debug("主管理员不能被注销");
			}
			resultMap.put("status", "error");
			resultMap.put("error", "主管理员不能被注销");
			return resultMap;
		}
		
		doneFilesManager.setIsActivited(0);
		myFilesManDao.updateMFM(doneFilesManager);
		if(logger.isDebugEnabled()){
			logger.debug("管理员冻结成功！");
		}
		resultMap.put("status", "success");
		return resultMap;
	}

	/* (non-Javadoc)
	 * @see com.lsj.ftp.myfiles.service.MyFilesManService#updateFileManager(int, com.lsj.ftp.myfiles.bean.MyFilesManager)
	 */
	@Override
	public Map updateFileManager(int doId, MyFilesManager myFilesManager) {
		// TODO Auto-generated method stub
		MyFilesManager doFilesManager=myFilesManDao.selectMFMById(doId);
		Map resultMap=new HashMap<String,String>();
		if(doFilesManager==null){
			if(logger.isDebugEnabled()){
				logger.debug("操作管理员不存在");
			}
			resultMap.put("status", "error");
			resultMap.put("error", "操作管理员不存在");
			return resultMap; 
		}
		
		if(doFilesManager.getManPrivilege().getMainPVL()!=1
				&&doFilesManager.getManPrivilege().getUpdatePVL()!=1){
			if(logger.isDebugEnabled()){
				logger.debug("操作管理员不存在修改权限");
			}
			resultMap.put("status", "error");
			resultMap.put("error", "操作管理员不存在");
			return resultMap;
		}

		//如果被注销的是主管理员，则无法被注销
		if(myFilesManager.getManPrivilege().equals(1)){
			if(logger.isDebugEnabled()){
				logger.debug("主管理员权限无法被修改");
			}
			resultMap.put("status", "error");
			resultMap.put("error", "主管理员权限无法被修改");
			return resultMap;
		}


		
		try {
			myFilesManDao.updateMFM(myFilesManager);
			if(logger.isDebugEnabled()){
				logger.debug("管理员信息修改成功");
			}
			resultMap.put("status", "success");
			resultMap.put("success", "管理员信息修改成功");
			return resultMap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(logger.isDebugEnabled()){
				logger.debug("管理员信息修改失败");
			}
			e.printStackTrace();
			resultMap.put("status", "error");
			resultMap.put("error", "发生内部错误");
			return resultMap;
		}
		
	}

	/* (non-Javadoc)
	 * @see com.lsj.ftp.myfiles.service.MyFilesManService#selectFileManagerIsActivited()
	 */
	@Override
	public List<MyFilesManager> selectFileManagerIsActivited() {
		// TODO Auto-generated method stub
		List<MyFilesManager> activitedMFM;
		activitedMFM=myFilesManDao.selectMFMIsActivited();
		return activitedMFM;
	}

	@Override
	public Map loginFileManager(MyFilesManager myFilesManager) {
		// TODO Auto-generated method stub
		 Map<String, String> resultMap=new HashMap<String, String>();
		//根据账户名查询数据库
		MyFilesManager tempMyFilesManager=myFilesManDao.selectMFMByAccount(myFilesManager.getAccount());
		
		//判断账户是否存在
		if(tempMyFilesManager==null){
			if(logger.isDebugEnabled()){
				logger.debug("账户不存在");
			}
			resultMap.put("status","error");
			resultMap.put("msg", "账户不存在");
			return resultMap;
		}
		
		//判断密码是否正确
		if(!tempMyFilesManager.getPassword().equals(myFilesManager.getPassword())){
			if(logger.isDebugEnabled()){
				logger.debug("密码不正确");
			}
			resultMap.put("status","error");
			resultMap.put("msg", "密码不正确");
			return resultMap;
		}
		
		//判断管理员是否被激活
		if(tempMyFilesManager.getIsActivited()==1){
			if(logger.isDebugEnabled()){
				logger.debug("登录成功");
			}
			resultMap.put("status","success");
			resultMap.put("msg", "登录成功");
			resultMap.put("userId",tempMyFilesManager.getId()+"");
			return resultMap;
		}else {
			if(logger.isDebugEnabled()){
				logger.debug("管理员还没被激活");
			}
			resultMap.put("status","error");
			resultMap.put("msg", "账户还未激活");
			return resultMap;
		}
	}

	@Override
	public List<MyFilesManager> getAllFileManager() {
		// TODO Auto-generated method stub
		List<MyFilesManager> myFilesManagers=null;
		myFilesManagers=myFilesManDao.selectAllMFM();
		return myFilesManagers;
	}

	@Override
	public Map updateMFMPrivilege(int doId, ManPrivilege manPrivilege) {
		// TODO Auto-generated method stub
		Map resultMap=new HashMap<String, String>();
		MyFilesManager doFilesManager=myFilesManDao.selectMFMById(doId);
		MyFilesManager doneFilesManager=myFilesManDao.selectMFMById(manPrivilege.getId());
		ManPrivilege oldDoneManPrivilege=doneFilesManager.getManPrivilege();
		
		if(oldDoneManPrivilege.getMainPVL()==1&&doId==doneFilesManager.getId()) {
			resultMap.put("status", "error");
			resultMap.put("error", "不允许被修改自身的主管理权限");
			return resultMap;
		}else {
			
		if(doFilesManager==null){
			if(logger.isDebugEnabled()){
				logger.debug("操作管理员不存在");
			}
			resultMap.put("status", "error");
			resultMap.put("error", "操作管理员不存在");
			return resultMap;
		}
		
		if(doFilesManager.getManPrivilege().getMainPVL()!=1
				&&doFilesManager.getManPrivilege().getGrantPVL()!=1){
			if(logger.isDebugEnabled()){
				logger.debug("操作管理员不存在授权权限");
			}
			resultMap.put("status", "error");
			resultMap.put("error", "操作管理员不存在授权权限");
			return resultMap;
		}
		
		if(doneFilesManager==null){
			if(logger.isDebugEnabled()){
				logger.debug("被操作管理员不存在");
			}
			resultMap.put("status", "error");
			resultMap.put("error", "操作管理员不存在");
			return resultMap;
		}
		
		//无法修改自己的授权权限
		if(doneFilesManager.getId()==doFilesManager.getId()&&manPrivilege.getGrantPVL()==0) {if(logger.isDebugEnabled()){
			logger.debug("无法修改自己的授权权限");
		}
		resultMap.put("status", "error");
		resultMap.put("error", "无法修改自己的授权权限");
		return resultMap;
		}
		
		}
		
		
		manPrivilegeDao.updatePVL(manPrivilege);
		resultMap.put("status", "success");
		return resultMap;
	}
	
}
