/**
 * customer_file_table
 * 访客列表js
 */
//搜索框方法
function searchFile(){
	var searchFileInput=$('#search_input');
	var fileName=searchFileInput.val();
	if(fileName==''||fileName==null){
		return ;
	}
	var projectName=getRootPath();
	var files_tbody=$('#file_tbody');
	$.ajax({
		url:projectName+"/myFile/searchFile.do",
		type:"post",
		data:{"fileName":fileName},
		async:true,
		dataType:"json",
		success:function(data){
			files_tbody.html("");
			var myFileList=eval(data);
			$.each(myFileList,function(index,item){
				var fileRow=$('<tr></tr>');
				var fileName=item.fileName;
				var createDate=item.createDate;
				var ownerId=item.ownerId;
				var lastModifiedDate=item.lastModifiedDate;
				var lastModifiedId=item.lastModifiedId;
				var operate=$('<a></a>');
				operate.attr("id",item.id);
				operate.attr("class","btn btn-success");
				operate.attr("href",getRootPath()+"/myFile/downloadFile.do?fileId="+item.id);
				operate.html("下载");

				
				var nameTd=$('<td></td>');
				var createDateTd=$('<td></td>');
				var ownerIdTd=$('<td></td>');
				var lastModifiedDateTd=$('<td></td>');
				var lastModifiedIdTd=$('<td></td>');
				var operateTd=$('<td></td>');
				
				nameTd.append(fileName);
				ownerIdTd.append(ownerId);
				createDateTd.append(createDate);
				lastModifiedDateTd.append(lastModifiedDate);
				lastModifiedIdTd.append(lastModifiedId);
				operateTd.append(operate);
				
				fileRow.append(nameTd);
				fileRow.append(createDateTd);
				fileRow.append(ownerIdTd);
				fileRow.append(lastModifiedDateTd);
				fileRow.append(lastModifiedIdTd);
				fileRow.append(operateTd)
				
				files_tbody.append(fileRow);
			});
		},
		error:function(data){
			var myFileList=eval(data);
			alert("失败"+myFileList);
		}
	});
}

//获取工程路径
function getRootPath(){
	
	//当前页面
	var curTotalPath=window.document.location.href;
	
	//当前页面路径
	var pathName=window.document.location.pathname;
	
	//当前页面路径起始位置
	var pathNamePos=curTotalPath.indexOf(pathName);
	
	//host
	var hostPath=curTotalPath.substring(0,pathNamePos);
	
	//工程名
	var projectName=pathName.substr(0,pathName.substr(1).indexOf('/')+1);     
	return hostPath+projectName;
}

//清除输入框，刷新页面
function clearInput(){
	var searchInput=$('#search_input');
	searchInput.html("");
	location.reload();
}