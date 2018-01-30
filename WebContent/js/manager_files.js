/**
 * 管理员文件管理js
 */

//获取要更新的文件Id
/*$('#update_modal').on('show.bs.modal',function(event){
	var id=$(obj).attr("id");
	var button=$(event.relatedTarget)
	var fileId = button.data('fileid');
	var modal = $(this);
	 modal.find('.modal-title').text('更新文件'+id);
})*/

function updateFile(obj){
	var id=$(obj).attr("id");
	var modal=$('#update_modal');
	modal.modal({
		  backdrop:false
		});
	modal.modal('show');
	$('#update-form').attr('action','/myfiles/myFile/updateFile.do?fileId='+id);
	 
}

/*function deleteFile(fileId){
	$.ajax({
		
	})
}*/

function uploadFile(){
	var uploadModal=$('#upload_modal');
	uploadModal.modal({
		  backdrop:false
		});
	uploadModal.modal('show');
}

function uploadFileChange(file){
	var fileShow=$('#uploadFileShow');
	if(file==""||file==null){
		fileShow.val("未选择文件")
	}else{
		fileShow.val(file)
	}
}

function updateFileChange(file){
	var fileShow=$('#updateFileShow');
	if(file==""||file==null){
		fileShow.val("未选择文件")
	}else{
		fileShow.val(file)
	}
}

/*//搜索框方法
function searchFile(){
	var searchFileInput=$('#search_input');
	var fileName=searchFileInput.val();
	if(fileName==''||fileName==null){
		return ;
	}
	var projectName=getRootPath();
	var files_tbody=$('#file_tbody');
	$.ajax({
		url:projectName+"/myFile/mfm_searchFile.do",
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
}*/

function getFiles(page,pageCount){

	var projectName=getRootPath();
	var files_tbody=$('#file_tbody');
	var searchFileName=$('#search_input').val();
	
	$.ajax({
		url:projectName+"/myFile/getAllFilesByPage.do",
		type:"post",
		data:{"fileName":searchFileName,"page":page,"pageCount":10},
		async:true,
		dataType:"json",
		success:function(data){
			if(data.status=="success"){
			files_tbody.html("");
			var myFileList=eval(data.fileLists);
			//文件列表展示
			$.each(myFileList,function(index,item){
				var fileRow=$('<tr></tr>');
				var fileName=item.fileName;
				var createDate=item.createDate;
				var ownerId=item.ownerId;
				var lastModifiedDate=item.lastModifiedDate;
				var lastModifiedId=item.lastModifiedId;
				var operateUpdate=$('<a></a>');
				operateUpdate.attr("id",item.id);
				operateUpdate.attr("class","btn btn-warning");
				operateUpdate.bind('click',function(){
					updateFile(operateUpdate);
				})
				operateUpdate.html("重传");
				
				var oprateDelete=$('<a></a>');
				oprateDelete.attr("id",item.id);
				oprateDelete.attr("class","btn btn-danger");
				oprateDelete.attr("href",getRootPath()+"/myFile/deleteFile.do?fileId="+item.id);
				oprateDelete.html("删除");

				
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
				operateTd.append(operateUpdate);
				operateTd.append(oprateDelete);
				
				fileRow.append(nameTd);
				fileRow.append(createDateTd);
				fileRow.append(ownerIdTd);
				fileRow.append(lastModifiedDateTd);
				fileRow.append(lastModifiedIdTd);
				fileRow.append(operateTd)
				
				files_tbody.append(fileRow);
			});
			
			//pageSelect重新生成
			pageinit(data.currentPage,data.totalPage,searchFileName);
			}
			else{
				alert(data.error);
			}
		},
		error:function(data){
			var myFileList=eval(data);
			alert("获取失败，请按F5刷新");
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
	var searchInput=$('#clearInput');
	searchInput.html("");
	location.reload();
}

function pageinit(currentPage,totalPage,fileName){
    var element = $('#bp-element');
    options = {
    	alignment:"center", //居中显示
        bootstrapMajorVersion:3, //对应的bootstrap版本
        currentPage:currentPage, //当前页数，这里是用的EL表达式，获取从后台传过来的值
        numberOfPages:10, //每页页数
        totalPages:totalPage, //总页数，这里是用的EL表达式，获取从后台传过来的值
        shouldShowPage:true,//是否显示该按钮
        itemTexts: function (type, page, current) {//设置显示的样式，默认是箭头
            switch (type) {
                case "first":
                    return "首页";
                case "prev":
                    return "上一页";
                case "next":
                    return "下一页";
                case "last":
                    return "末页";
                case "page":
                    return page;
            }
        },
        //点击事件
        onPageClicked: function (event, originalEvent, type, page) {
            getFiles(page,10);
        }
    };
    element.bootstrapPaginator(options);
}