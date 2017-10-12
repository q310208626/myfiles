<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员人员管理</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/myfiles.css">
</head>
<body>
	<table id="mfm_table" class="table table-striped">
		<thead>
			<tr>
				<td>ID</td>
				<td>账号</td>
				<td>权限</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="myMFM" items="${myMFMList}" varStatus="status">
				<tr>
					<td>${myMFM.id}</td>
					<td>${myMFM.account}</td>
					<td width="50%">
					<!-- 主管理 -->
					<span>主管理</span>
					<c:choose> 
					<c:when test="${myMFM.manPrivilege.mainPVL==1}"  >
						<input type="checkbox" checked="checked" disabled="disabled" value="1">
					</c:when>
					<c:otherwise>
						<input type="checkbox" disabled="disabled" value="0">
					</c:otherwise>
					</c:choose>
					<!-- 上传权限 -->
					<span>上传</span>
					<c:choose> 
					<c:when test="${myMFM.manPrivilege.uploadPVL==1}"  >
						<input type="checkbox" checked="checked" disabled="disabled" value="1">
					</c:when>
					<c:otherwise>
						<input type="checkbox" disabled="disabled" value="0">
					</c:otherwise>
					</c:choose>
					<!-- 重传权限 -->
					<span>重传</span>
					<c:choose> 
					<c:when test="${myMFM.manPrivilege.updatePVL==1}"  >
						<input type="checkbox" checked="checked" disabled="disabled" value="1">
					</c:when>
					<c:otherwise>
						<input type="checkbox" disabled="disabled" value="0">
					</c:otherwise>
					</c:choose>
					<!-- 授权权限 -->
					<span>授权</span>
					<c:choose> 
					<c:when test="${myMFM.manPrivilege.grantPVL==1}"  >
						<input type="checkbox" checked="checked" disabled="disabled" value="1">
					</c:when>
					<c:otherwise>
						<input type="checkbox" disabled="disabled" value="0">
					</c:otherwise>
					</c:choose>
					<!-- 全文件操作权限 -->
					<span>全文件操作</span>
					<c:choose> 
					<c:when test="${myMFM.manPrivilege.allFilesPVL==1}"  >
						<input type="checkbox" checked="checked" disabled="disabled" value="1">
					</c:when>
					<c:otherwise>
						<input type="checkbox" disabled="disabled" value="0">
					</c:otherwise>
					</c:choose>   	 	
					</td>
					<td>
						<a id="${status.index+1}" class="btn btn-warning" onclick="updatePrivilegeModal(this)" >权限修改</a>
						<c:choose>
							<c:when test="${myMFM.isActivited==0}">
								<a class="btn btn-success" href="${pageContext.request.contextPath}/MFM/activityMFM.do?doneId=${myMFM.id}">激活</a>
							</c:when>
							<c:when test="${myMFM.manPrivilege.mainPVL==1}">
							</c:when>
							<c:otherwise>
								<a class="btn btn-warning" href="${pageContext.request.contextPath}/MFM/freezeMFM.do?doneId=${myMFM.id}">注销</a>
							</c:otherwise> 
						</c:choose> 
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
				
			<div class="modal fade" id="mfm_upadte_modal" tabindex="-1">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">权限修改</h4>
						</div>

						<form id="mfm_update_form" action="${pageContext.request.contextPath}/MFM/updatePrivilege.do" method="post" >
							<div class="modal-body">
								<span>ID:</span><input id="mfm_id" name="id" type="text"  border="none"  value="mfmId"  size="6" readonly><br>
								<span>主管理</span><input id="checkbox_mainPVL" name="mainPVL" type="checkbox" >
								<span>上传</span><input id="checkbox_uploadPVL" name="uploadPVL" type="checkbox">
								<span>重传</span><input id="checkbox_updatePVL" name="updatePVL" type="checkbox">
								<span>授权</span><input id="checkbox_grantPVL" name="grantPVL" type="checkbox">
								<span>全文件操作</span><input id="checkbox_allFilePVL" name="allFilesPVL" type="checkbox">
							</div>
							
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">关闭</button>
								<input type="submit" value="修改"
								class="btn btn-success input-submit-fileupload" onclick="updateMFMPrivilege()">
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
	src="${pageContext.request.contextPath}/js/manager_mfm.js"></script>
</html>