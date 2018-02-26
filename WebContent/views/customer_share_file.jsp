<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件分享页面</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/myfiles.css">
</head>
<body>
	<div class="container-narrow">
		<div class="share_container">
		<input type="text"></input>
			<div class="input-group">
				<span class="input-group-addon">文件名</span>
				<input id="share_file_name" type="text"></input>
			</div>
			<div class="input-group">
				<span class="input-group-addon">提取密码</span>
				<input id="share_pwd" type="text"></input>
			</div>
		</div>
	</div>
</body>
</html>