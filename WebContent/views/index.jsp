<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>My Files System</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/myfiles.css">	
</head>
<body>
	<div class="container-narrow">
		<div class="masthead">
			<ul class="nav nav-pills pull-right">
				<li class="active"><a>主页</a></li>
				<li><a href="${pageContext.request.contextPath}/to_manlogin.do">管理员登录</a></li>
			</ul>
			<h3 class="muted" >我的文件</h3>
		</div>
		<hr><!-- 分割线 -->
		<div>
			<%-- <iframe id="customer_file_iframe" src="${pageContext.request.contextPath}/myFile/getCustomerFileByPage.do?page=1&pageCount=10" width="100%"   frameborder="no" scrolling="no" onload="setIframeHeight(this)">
			</iframe> --%>
			<iframe id="customer_file_iframe" src="${pageContext.request.contextPath}/views/customer_file_table.jsp" width="100%"   frameborder="no" scrolling="no" onload="setIframeHeight(this)">
			</iframe>
			
		</div>
	</div>
</body>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
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
		setIframeHeight(document.getElementById('customer_file_iframe'));
	};
</script>
</html>
