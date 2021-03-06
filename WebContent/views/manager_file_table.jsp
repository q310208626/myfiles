<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员文件列表</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/myfiles.css">
</head>
<body>
	<div class="row">
        <div class="col-md-6" >
            <div class="input-group" style="display:inline-block;">
            	<form action="" target="frameFile">
            	<div style="display:inline-block;position:relative;" >
            		<input type="text" id="search_input" class="form-control" placeholder="请输入文件名">
            		<a id="clearInput" onclick="clearInput()">X</a>
            	</div>
                <span class="input-group-btn" style="display:inline-block">
                    <input type="submit" class="btn btn-primary" value="搜索" onclick="getFiles(1,10)"></input>
                </span>
                </form>
            </div>
            <button class="btn btn-success button-modal-upload"  onclick="uploadFile()" style="float:right;">上传文件</button>
        </div>
    </div>
    <iframe name="frameFile" style="display:none;"></iframe>
	<div style="height:600px">
	<table class="table table-striped" >
		<thead>
			<tr>
				<th>文件名</th>
				<th>拥有者</th>
				<th>上传时间</th>
				<th>修改时间</th>
				<th>修改者</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="file_tbody">
<%-- 			<c:forEach var="myFile" items="${MyFileList}">
				<tr>
					<td>${myFile.fileName}</td>
					<td>${myFile.ownerId}</td>
					<td>${myFile.createDate}</td>
					<td>${myFile.lastModifiedDate}</td>
					<td>${myFile.lastModifiedId}</td>
					<a id="${myFile.id}" class="btn btn-warning" data-toggle="modal" data-target="#update_modal" >重传</a>
					<td><a id="${myFile.id}" class="btn btn-warning" onclick="updateFile(this)" >重传</a>
						<a class="btn btn-danger"
						href="${pageContext.request.contextPath}/myFile/deleteFile.do?fileId=${myFile.id}">删除</a>
					</td>
				</tr>
			</c:forEach> --%>
		</tbody>
	</table>
	</div>
	
	<!-- 分页插件 -->
		<nav style="text-align: center">
			<ul id="bp-element" class="pagination"></ul>
		</nav>
	
			<!-- upload_modal -->
			<div class="modal fade" id="upload_modal" tabindex="-1">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">上传文件</h4>
						</div>

						<!-- <form 
							id="uploadForm" action="" onclick="" target="frameFile">  -->
							<%-- action="${pageContext.request.contextPath}/myFile/uploadFile.do" --%>
							<div class="modal-body">
								<a class="a-upload">选择文件 <input id="uploadFileInput" type="file"
									name="uploadFile" width="50px" height="50px" onchange="uploadFileChange(this.value)"/>
								</a>
								<input id="uploadFileShow" type="text" value="未选择文件" style="border:none; padding:4px 5px;margin-top:0px;">
								<input id="uplaodPersentShow" type="text" value="0%" style="border:none; padding:4px 5px;margin-top:0px;">
							</div>
							
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">关闭</button>
								<!-- <input type="submit" value="上传"
								class="btn btn-success input-submit-fileupload"> -->
								<select id='del_select' class='btn selectpicker' data-style="btn-info">
									<option value='0'>定时删除</option>
									<option value='0'>永久</option>
									<option value='1'>一天</option>
									<option value='2'>一星期</option>
									<option value='3'>一个月</option>
								</select>
								<input id="uploadButton" type="button" value="上传" onclick="continueUpload('upload')" 
								class="btn btn-success">
							</div>
						<!-- </form> -->
					</div>
				</div>
			</div>
	
	<!-- update-modal -->
	<div class="modal fade" id="update_modal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">更新文件</h4>
				</div>
					<div class="modal-body">
						<a class="a-upload">选择文件 <input type="file" id="updateFileInput" name="updateFile"
							width="50px" height="50px" onchange="updateFileChange(this.value)"/>
						</a>
						<input id="updateFileShow" type="text" value="未选择文件" style="border:none; padding:4px 5px;margin-top:0px;">
						<input id="updatePersentShow" type="text" value="0%" style="border:none; padding:4px 5px;margin-top:0px;">
						<input id='updateId' type='hidden'>
					</div>

					<div class="modal-footer">
						<button id="update_modal_close" type="button" class="btn btn-default" >关闭</button>
						<input id="updateButton" type="button" value="上传" class="btn btn-success" onclick="continueUpload('update')">
					</div>

			</div>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/manager_files.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap-paginator.js"></script>	
<script type="text/javascript">
window.onload = function() { 
	getFiles(1,10);
}
</script>
</html>
