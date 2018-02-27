package com.lsj.ftp.myfiles.bean;

import org.springframework.stereotype.Component;

/**   
*    
* 项目名称：myfiles-master   
* 类名称：MyFileShare   
* 类描述：文件分享Bean
* 创建人：rentutu   
* 创建时间：2018年2月26日 下午2:33:45   
* 修改人：rentutu   
* 修改时间：2018年2月26日 下午2:33:45   
* 修改备注：   
* @version    
*    
*/
@Component
public class MyFileShare {
	public int id;
	public int fileId;
	public String sharePwd;
	
	public int getId() {
		return id;
	}
	
	public int getFileId() {
		return fileId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String getSharePwd() {
		return sharePwd;
	}
	public void setSharePwd(String sharePwd) {
		this.sharePwd = sharePwd;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer buffer=new StringBuffer();
		buffer.append("\nid:");
		buffer.append(this.getId());
		buffer.append("\nfileId:");
		buffer.append(this.getFileId());
		buffer.append("\nsharePwd:");
		buffer.append(this.getSharePwd());
		return buffer.toString();
	}
	
	
	
}
