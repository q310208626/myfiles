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
					<li class="active"><a>文件管理</a></li>
					<li><a>管理员管理</a></li>
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

				<hr>
				<!-- 分割线 -->
				<!-- 管理内容 -->
				<div class="container-narrow">
					<!-- form -->
					<button class="btn btn-success button-modal-upload"
						data-toggle="modal" data-target="#upload_modal">上传文件</button>
					<iframe id="manager_iframe"
						src="${pageContext.request.contextPath}/myFile/getAllFiles.do?manId=${sessionScope.userId}"
						width="100%" height="100%" frameborder="no"></iframe>
				</div>
			</div>

			<!-- upload_modal -->
			<div class="modal fade" id="upload_modal" tabindex="-1">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">上传文件</h4>
						</div>

						<form
							action="${pageContext.request.contextPath}/myFile/uploadFile.do"
							enctype="multipart/form-data" method="post">
							<div class="modal-body">
								<a class="a-upload">选择文件 <input type="file"
									name="uploadFile" width="50px" height="50px" />
								</a>
							</div>
							
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">关闭</button>
								<input type="submit" value="上传"
								class="btn btn-success input-submit-fileupload">
							</div>
						</form>

					</div>
				</div>
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