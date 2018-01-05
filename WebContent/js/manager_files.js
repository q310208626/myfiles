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

function uploadFile(){
	var uploadModal=$('#upload_modal');
	uploadModal.modal({
		  backdrop:false
		});
	uploadModal.modal('show');
}

function searchFile(){
	var searchFileInput=$('#search_input');
	var fileName=searchFileInput.val();
	var projectName=getRootPath();
	$.ajax({
		url:projectName+"/myFile/searchFile.do",
		type:"post",
		data:{"fileName":fileName},
		success:function(){
			alert("成功");
		},
		error:function(){
			alert("失败");
		}
	});
}

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