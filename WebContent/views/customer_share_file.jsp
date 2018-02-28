<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件分享提取页面</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/myfiles.css">
</head>
<body>
	<div class="container-narrow" style="text-align: center;">
		<div class="share_container">
			<span style="font-size: 50px">文件提取</span>
			<input id="shareIdInput" type="hidden" value="${shareId}">
			<div class="input-group">
				<span class="input-group-addon" style="border: none" >文件名</span>
				<input id="share_file_name" type="text" value="${fileName}" class="form-control"></input>
			</div>
			<div class="input-group">
				<span class="input-group-addon">提取码</span>
				<input id="share_pwd" type="text" class="form-control"></input>
				<a id="getShareFileButton" class="input-group-addon">提取</a>
			</div>
		</div>
	</div>
</body>
</html>