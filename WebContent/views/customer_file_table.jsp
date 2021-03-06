<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>游客文件列表</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/myfiles.css">
</head>
<body>
 	<div class="row">
        <div class="col-md-6">
            <div class="input-group" style="display:block;">
            	 <form action="" target="frameFile" />
            	<div style="display:inline-block;position:relative;text-align:center;" >
            		<input type="text" id="search_input" class="form-control" placeholder="请输入文件名">
            		<a id="clearInput" onclick="clearInput()">X</a>
            	</div>
                <span class="input-group-btn" style="display:inline-block;">
                    <input type="submit" class="btn btn-primary" value="搜索" onclick="getFiles(1,10)"></input>
                    <!-- <button class="btn btn-primary" value="搜索" onclick="getFiles(1,10)">搜索</button> -->
                </span>
                </form>
            </div>
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
					<td>
						<a id="${myFile.id}" class="btn btn-success" href="${pageContext.request.contextPath}/myFile/downloadFile.do?fileId=${myFile.id}" >下载</a>
					</td>
				</tr>
			</c:forEach> --%>
		</tbody>
	</table>
	</div>
	<nav style="text-align: center">
		<ul id="bp-element" class="pagination"></ul>
	</nav>
		<div id="toastDiv" class="toast">
			<span id="tipMsg"></span>
		</div>
	<!-- share modal -->
	<div  id="share_modal" tabindex="-1" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="shareModalClose()">&times;</button>
							<h4 class="modal-title" id="myModalLabel">文件分享</h4>
						</div>
						<div class="modal-body">
							
							<input type="hidden" id="fileIdInput">
							<span style="width: 20%;">分享链接</span>
							<input id="share_link" type="text" style="margin-left:10px;width:70%"></input>
							<a id="shareCopyButton" style="width: 10%;" onclick="shareLinkCopy()" class="btn btn-success" >复制</a>
							<br>
							<span>分享密码</span>
							<input id="share_pwd" type="text" style="margin-left:10px;width: 70%" maxlength="6" ></input>
							
						</div>
						<div class="modal-footer">
								<button type="button" class="btn btn-default"
									onclick="shareModalClose()">关闭</button>
								<input  id="shareSetButton" type="button" value="设置" class="btn btn-primary" onclick=""></input>
					    </div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.2.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap-paginator.js"></script>	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/customer_files.js" defer=true></script>
<script type="text/javascript">
	window.onload = function() { 
		getFiles(1,10);
	}
</script>
</html>
