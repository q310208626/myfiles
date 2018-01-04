<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>游客文件列表</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/myfiles.css">
</head>
<body>
 	<div class="row">
        <div class="col-md-6">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="请输入文件名">
                <span class="input-group-btn">
                    <button class="btn btn-primary">搜索</button>
                </span>
            </div>
        </div>
    </div>
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
					<td>
						<a id="${myFile.id}" class="btn btn-success" href="${pageContext.request.contextPath}/myFile/downloadFile.do?fileId=${myFile.id}" >下载</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.8.0.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/manager_files.js"></script>
</html>