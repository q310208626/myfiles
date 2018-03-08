/**
 * 管理员文件管理js
 */
var uploadIsPause=1;
var uploadTimes=1;
//文件上传Request对象
var uploadRequest;
	

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
	$('#updateId').val(id);
	 
}

//Modal关闭方法
function modalClose(modalId){
	var modalJQId='#'+modalId;
	var modal=$(modalJQId);
	if(typeof uploadRequest ==='object'){
        uploadRequest.abort();
	}
	modal.modal('hide');
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
function continueUpload(operate){
	//上传的文件
	var file;
	//操作的按钮，分为上传跟更新两个按钮
	var operateButton;

	if(operate=='upload'){
		file=$('#uploadFileInput')[0].files[0];
		operateButton=$('#uploadButton');
	}else if(operate=='update'){
		file=$('#updateFileInput')[0].files[0];
		operateButton=$('#uploadButton');
	}
	
	
	if(file!=undefined){
		var fileName=file.name;
		var fileSize=file.size;
		var fileType=file.type;
		
		window.localStorage.setItem("fileName",fileName);
		window.localStorage.setItem("fileSize",fileSize);
		window.localStorage.setItem("fileType",fileType);
		
		//修改按键绑定监听事件
		if(operate=='upload'){
			operateButton.attr('onclick',"uploadOrTemp('upload')");
		}else if(operate=='upload'){
			operateButton.attr('onclick',"uploadOrTemp('update')");
		}
		uploadIsPause=0;
		operateButton.val("暂停");
		startUpload(1,operate);
		
	}	
}

//暂停继续上传
function uploadOrTemp(operate){
	var operateButton;
	if(operate=='upload'){
		operateButton=$('#uploadButton');
	}else if(operate=='update'){
		operateButton=$('#uploadButton');
	}
	
	if(uploadIsPause==1){
		uploadIsPause=0;
		operateButton.val("暂停");
		startUpload(-1,operate);
		
	}else if(uploadIsPause==0){
		uploadIsPause=1;
		operateButton.val("上传");
	}
}

//续传上传方法
/*
* uploadTimes 1表示第一次上传，-1表示续传
* operate  代表操作：1.上传 2.更新
*/
function startUpload(uploadTimes,operate){
	var fileName=window.localStorage.getItem("fileName");
	var fileSize=window.localStorage.getItem("fileSize");
	//分块大小
	var block=1024;
	//分块数量
	var blockNum=Math.ceil(fileSize/block);
	//当前上传块数
	var chunk;


	//获取当前上传文件上传的块数，没有则取0，0表示未上传过该文件
	chunk=window.localStorage.getItem(fileName+'_chunk_'+fileSize)||0;
	chunk=parseInt(chunk,10);

	//如果chunk不是0，代表着之前上传过相同文件
	if(chunk!=0){
        uploadTimes=-1;
	}

	//判断是否是最后一块
	var isLastChunk=(chunk==blockNum-1)?true:false;
	
	var operateButton;
	
	if(operate=='upload'){
		operateButton=$('#uploadButton');
	}else if(operate=='update'){
		operateButton=$('#uploadButton');
	}
	
/*	//如果是第一次上传，则初始化
    if (uploadTimes ==1) {
        window.localStorage.setItem(fileName + '_chunk_'+fileSize, 0);
        chunk = 0;
        uploadTimes=-1;
    }*/

/*    //如果是最后一次上传，则初始化
    if( isLastChunk == true){
        window.localStorage.setItem(fileName + '_chunk_'+fileSize, 0);
        chunk = 0;
        uploadTimes=1;
        isLastChunk = false;
	}*/
    
    //分段起始位置
    var blockFrom=chunk*block;
    //分段结束位置
	var blockTo=((chunk+1)*block)>fileSize?fileSize:(chunk+1)*block;
	//上传百分比
	var persent=(100*blockFrom/fileSize).toFixed(1);
	//传输数据
	/*var uploadFormData=new FormData($('#uploadForm')[0]);*/
	var uploadFormData=new FormData();
	//uploadFormData.append('uploadFile',fileName.slice(blockFrom,blockTo));
	if(operate=='upload'){
		uploadFormData.append('uploadFile',$('#uploadFileInput')[0].files[0].slice(blockFrom,blockTo));
		var del_time=$('#del_select option:selected').val();
		uploadFormData.append('del_time',del_time);
	}else if(operate=='update'){
		uploadFormData.append('uploadFile',$('#updateFileInput')[0].files[0].slice(blockFrom,blockTo));
		uploadFormData.append('fileId',$('#updateId').val());
	}
	uploadFormData.append('fileName',fileName);
	uploadFormData.append('fileSize',fileSize);
	uploadFormData.append('isLast',isLastChunk);
	uploadFormData.append('isFirst', uploadTimes ==1 ? true : false);
	
	if(operate=='upload'){
        uploadRequest=$.ajax({
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

				//如果是第一次上传，记录下该文件
				if(uploadTimes==1){
                    window.localStorage.setItem(fileName + '_chunk_'+fileSize, 0);
					uploadTimes=-1;
                    startUpload(-1,operate);
				}else if(chunk==blockNum-1){
                    //如果传输完成

					$('#uplaodPersentShow').val("100%");
					//设置当前传输块为0
					window.localStorage.setItem(fileName + '_chunk',0);
					uploadTimes=1;
					location.reload();
				}else{
					window.localStorage.setItem(fileName + '_chunk_'+fileSize, ++chunk);
					$('#uplaodPersentShow').val(persent+"%");
					if(uploadIsPause!=1){
						startUpload(-1,operate);
					}
				}
			}else{
				uploadIsPause=1;
				if(result.status=100){
					toastShow(result.msg,1000);
				}
				//设置当前传输块为0
				//window.localStorage.setItem(fileName + '_chunk_'+fileSize,0);
				operateButton.val("上传");
			}
			
		},
		error:function(){
			uploadIsPause=1;
            toastShow("文件上传出错",1000);
			operateButton.val("上传");
		}
	})
	}else if(operate=='update'){
        uploadRequest=$.ajax({
			url:getRootPath()+"/myFile/continueUpdate.do",
			type:"post",
			processData : false,
			contentType : false,		
	        data:uploadFormData,
	        dataType:"json",
			success:function(result){
				result=eval(result);
				//如果传输成功
				if(result.status==200){
					if(uploadTimes==1){
						uploadTimes=-1;
					}
					
					//传输完成
					if(chunk==blockNum-1){
						$('#updatePersentShow').val("100%");
						//设置当前传输块为0
						window.localStorage.setItem(fileName + '_chunk',0);
						uploadTimes=1;
						location.reload();
					}else{
						window.localStorage.setItem(fileName + '_chunk', ++chunk);
						$('#updatePersentShow').val(persent+"%");
						if(uploadIsPause!=1){
							startUpload(-1,operate);
						}
					}
				}else{
					uploadIsPause=1;
					//设置当前传输块为0
					//window.localStorage.setItem(fileName + '_chunk',0);
					operateButton.val("上传");
				}
				
			},
			error:function(){
				uploadIsPause=1;
				//设置当前传输块为0
				operateButton.val("上传");
			}
		})
	}
}




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

//消息展示，类似Toast
function toastShow(msg,time){
    var toast=$('#toastDiv');
    var tipMsg=$('#tipMsg');
    tipMsg.text(msg);
    toast.fadeIn();
    setTimeout(function(){
        toast.fadeOut();
    }, time);
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