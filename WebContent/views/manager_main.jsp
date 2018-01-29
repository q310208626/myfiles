<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员页面</title>
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
		<div class="span2 col-md-offset-1">
			<div class="well sidebar-nav">
				<ul class="nav nav-list">
					<li class="nav-header">管理选项</li>
					<li class="active nav-li"><a href=""javascript:void(0);" onclick="getFiles(1,10)" >文件管理</a></li>
					<li class="nav-li"><a href="${pageContext.request.contextPath}/MFM/getAllMFM.do" target="manager_iframe_name">管理员管理</a></li>
				</ul>
			</div>
		</div>
		<!-- 管理员页面内容 -->
		<div class="span10">
			<div class="container-narrow">
				<!-- 管理员页面内容头部 -->
				<div class="masthead">
					<ul class="nav nav-pills pull-right">
						<li><a href="${pageContext.request.contextPath}/to_index.do">主页</a></li>
						<li><a
							href="${pageContext.request.contextPath}/MFM/logout.do">注销</a></li>
					</ul>
					<h3 class="muted">管理员页面</h3>
				</div>
				
				<!-- 分割线 -->
				<hr>
				
				<!-- 管理内容 -->
				<!-- <div class="container-narrow"> -->
				<div>
					<iframe id="manager_iframe" name="manager_iframe_name" src="${pageContext.request.contextPath}/views/manager_file_table.jsp" width="100%"  frameborder="no" scrolling="no"></iframe>
				</div>
				
			</div>
			
		</div>
	</div>
	</div>

</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/js/manager_main.js"></script>
<script type="text/javascript">
function setIframeHeight(iframe) {
	if (iframe) {
		var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
		if (iframeWin.document.body) {
		iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
			}
		}
	};

	window.onload = function () {
		setIframeHeight(document.getElementById('manager_iframe'));
	};
</script>	
</html>
