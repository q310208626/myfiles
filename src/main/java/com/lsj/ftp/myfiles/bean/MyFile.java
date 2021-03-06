package com.lsj.ftp.myfiles.bean;

import java.util.Date;
import org.springframework.stereotype.Component;


/**   
*    
* 项目名称：myfiles   
* 类名称：MyFile   
* 类描述：   
* 创建人：Administrator   
* 创建时间：2017年9月27日 上午2:44:07   
* 修改人：Administrator   
* 修改时间：2017年9月27日 上午2:44:07   
* 修改备注： 文件bean
* @version  1.0  
*    
*/
@Component
public class MyFile {
	
	/**   
	 * @Fields id : TODO  
	 * 文件Id
	 */ 
	private int id;
	
	/**   
	 * @Fields fileName : TODO 
	 * 文件名 
	 */ 
	private String fileName;
	
	/**   
	 * @Fields saveName : TODO 
	 * 服务器存储文件名字 
	 */ 
	private String saveName;
	
	/**   
	 * @Fields savePath : TODO 
	 * 保存路径 
	 */ 
	private String savePath;
	
	/**   
	 * @Fields ownerId : TODO 
	 * 文件拥有者Id 
	 */ 
	private int ownerId;
	
	/**   
	 * @Fields createDate : TODO 
	 * 创建时间 
	 */ 
	private String createDate;
	
	/**   
	 * @Fields lastModifiedDate : TODO 
	 * 最后修改时间 
	 */ 
	private Date lastModifiedDate;
	
	/**   
	 * @Fields lastModifiedId : TODO 
	 * 最后修改人Id 
	 */ 
	private int lastModifiedId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getLastModifiedId() {
		return lastModifiedId;
	}

	public void setLastModifiedId(int lastModifiedId) {
		this.lastModifiedId = lastModifiedId;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer();
		sb.append("id:"+this.getId()+"\n");
		sb.append("fileName:"+this.getFileName()+"\n");
		sb.append("savePath:"+this.getSavePath()+"\n");
		sb.append("craeteDate:"+this.getCreateDate()+"\n");
		sb.append("modifiedDate:"+this.getLastModifiedDate()+"\n");
		sb.append("ownerId:"+this.getOwnerId()+"\n");
		sb.append("modifiedId:"+this.getLastModifiedId()+"\n");
		return sb.toString();
	}
}
