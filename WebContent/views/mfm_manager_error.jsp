<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员操作失败界面</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/myfiles.css">
</head>
<body>
		<div class="container-narrow">
		<div class="error_container">
			<p>${error}</p> 
			<a href="${pageContext.request.contextPath}/myFile/getAllFiles.do" class="btn btn-primary" >返回</a>
		</div>
	</div>
</body>
</html>