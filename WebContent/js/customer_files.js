/**
 * customer_file_table
 * 访客列表js
 */
//获取文件方法
function getFiles(page,pageCount){

	var projectName=getRootPath();
	var files_tbody=$('#file_tbody');
	var searchFileName=$('#search_input').val();
	
	$.ajax({
		url:projectName+"/myFile/getCustomerFileByPage.do",
		type:"post",
		data:{"fileName":searchFileName,"page":page,"pageCount":10},
		async:true,
		dataType:"json",
		success:function(data){
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
				var operate=$('<a></a>');
				var shareLink=$('<a></a>')
				
				//下载Button属性
				operate.attr("id",item.id);
				operate.attr("class","btn btn-success");
				operate.attr("href",getRootPath()+"/myFile/downloadFile.do?fileId="+item.id);
				operate.html("下载");
				
				//分享Button属性
				shareLink.attr("id","share"+item.id);
				shareLink.attr("class","btn btn-primary");
				shareLink.attr("onclick","shareModalOpen()");
				shareLink.html("分享");

				
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
				operateTd.append(shareLink);
				
				fileRow.append(nameTd);
				fileRow.append(ownerIdTd);
				fileRow.append(createDateTd);
				fileRow.append(lastModifiedDateTd);
				fileRow.append(lastModifiedIdTd);
				fileRow.append(operateTd)
				
				files_tbody.append(fileRow);
			});
			
			//pageSelect重新生成
			pageinit(data.currentPage,data.totalPage,searchFileName);
		},
		error:function(data){
			var myFileList=eval(data);
			alert("失败"+myFileList);
		}
	});
}

function shareModalOpen(){
	var shareModal=$('#share_modal');
	shareModal.modal({
		  backdrop:false
		});
	shareModal.modal('show');
}

function shareModalClose(){
	var shareModal=$('#share_modal');
	shareModal.modal('close');
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

