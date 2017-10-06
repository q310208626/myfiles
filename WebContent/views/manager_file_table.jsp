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
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/myfiles.css">
</head>
<body>
	<table class="table table-striped">
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
		<tbody>
			<c:forEach var="myFile" items="${MyFileList}">
				<tr>
					<td>${myFile.fileName}</td>
					<td>${myFile.ownerId}</td>
					<td>${myFile.createDate}</td>
					<td>${myFile.lastModifiedDate}</td>
					<td>${myFile.lastModifiedId}</td>
					<%-- <a id="${myFile.id}" class="btn btn-warning" data-toggle="modal" data-target="#update_modal" >重传</a> --%>
					<td><a id="${myFile.id}" class="btn btn-warning" onclick="updateFile(this)" >重传</a>
						<a class="btn btn-danger"
						href="${pageContext.request.contextPath}/myFile/deleteFile.do?fileId=${myFile.id}">删除</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- update-modal -->
	<div class="modal fade" id="update_modal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">更新文件</h4>
				</div>

				<form
					id="update-form" action="${pageContext.request.contextPath}/myFile/updateFile.do?fileId=0"
					enctype="multipart/form-data" method="post">
					<div class="modal-body">
						<a class="a-upload">选择文件 <input type="file" name="updateFile"
							width="50px" height="50px" />
						</a>
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<input type="submit" value="上传" class="btn btn-success input-submit-fileupload">
					</div>
				</form>
			</div>
		</div>
	</div>
	
</body>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.8.0.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/manager_files.js"></script>
</html>