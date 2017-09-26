<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/myfiles.css">
</head>
<body>

	<!-- 管理员页面contain -->
	<div class="row-fluid">
		<!-- 侧边栏 -->
		<div class="span2">
			<div class="well sidebar-nav">
				<ul class="nav nav-list">
					<li class="nav-header">管理选项</li>
					<li class="active"><a>文件管理</a></li>
					<li><a>管理员管理</a></li>
				</ul>
			</div>
		</div>
		<!-- 管理员页面内容 -->
		<div class="span7">
			<div class="container-narrow">
				<!-- 管理员页面内容头部 -->
				<div class="masthead">
					<ul class="nav nav-pills pull-right">
						<li><a href="${pageContext.request.contextPath}/to_index.do">主页</a></li>
						<li><a
							href="${pageContext.request.contextPath}/MFM/logout.do">${myFilesManager.account},注销</a>
						</li>
					</ul>
					<h3 class="muted">管理员页面</h3>
				</div>

				<hr>
				<!-- 分割线 -->
				
				<div class="container">
					
				</div>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.8.0.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</html>