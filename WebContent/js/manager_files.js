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

//续传
function continueUpload(formId){
	var file=document.getElementById("uploadFileInput").files;
	var form=$('#uploadForm');
	var chunk;
	var isPause=0;
	if(file[0]!=undefined){
		var fileName=file[0].name;
		var fileSize=file[0].size;
		var fileType=file[0].type;
		//分块大小
		var block=1024;
		//分块数量
		var blockNum=Math.ceil(fileSize/block);
		
		startUpload('first');
	}

	
	
	//续传上传方法
	function startUpload(times){
		chunk=window.localStorage.getItem(fileName+'_chunk')||0;
		chunk=parseInt(chunk,10);
		var isLastChunk=(chunk==blockNum-1)?true:false;
		
		
        if (times === 'first' && isLastChunk == true) {
            window.localStorage.setItem(fileName + '_chunk', 0);
            chunk = 0;
            isLastChunk = false;
        }
        
        //分段起始位置
        var blockFrom=chunk*block;
        //分段结束位置
		var blockTo=((chunk+1)*block)>fileSize?fileSize:(chunk+1)*block;
		//上传百分比
		var persent=100*blockFrom/fileSize.toFixed(1);
		//传输数据
		/*var uploadFormData=new FormData($('#uploadForm')[0]);*/
		var uploadFormData=new FormData();
		//uploadFormData.append('uploadFile',fileName.slice(blockFrom,blockTo));
		uploadFormData.append('uploadFile',$('#uploadFileInput')[0].files[0].slice(blockFrom,blockTo));
		uploadFormData.append('fileName',fileName);
		uploadFormData.append('fileSize',fileSize);
		uploadFormData.append('isLast',isLastChunk);
		uploadFormData.append('isFirst', times === 'first' ? true : false);
		
		$.ajax({
			url:getRootPath()+"/myFile/continueUpload.do",
			type:"post",
			processData : false,
			contentType : false,
	        data:uploadFormData,
	        dataType:"json",
			success:function(result){
				result=eval(result);
				//如果传输成功
				if(result.status==200){
					if(chunk==blockNum-1){
						alert('传输完成');
					}else{
						window.localStorage.setItem(fileName + '_chunk', ++chunk);
						if(isPause!=1){
							startUpload();
						}
					}
				}
				
			},
			error:function(){
				alert('传输出错');
			}
			
		})
		
		
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