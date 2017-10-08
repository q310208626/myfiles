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
	<table class="table table-striped">
		<thead>
			<tr>
				<td>ID</td>
				<td>账号</td>
				<td>权限</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="myMFM" items="${myMFMList}">
				<tr>
					<td>${myMFM.id}</td>
					<td>${myMFM.account}</td>
					<td width="50%">
					<!-- 主管理 -->
					<span>主管理</span>
					<c:choose> 
					<c:when test="${myMFM.manPrivilege.mainPVL==1}"  >
						<input type="checkbox" checked="checked" disabled="disabled" >
					</c:when>
					<c:otherwise>
						<input type="checkbox" disabled="disabled" >
					</c:otherwise>
					</c:choose>
					<!-- 上传权限 -->
					<span>上传</span>
					<c:choose> 
					<c:when test="${myMFM.manPrivilege.uploadPVL==1}"  >
						<input type="checkbox" checked="checked" disabled="disabled" >
					</c:when>
					<c:otherwise>
						<input type="checkbox" disabled="disabled" >
					</c:otherwise>
					</c:choose>
					<!-- 重传权限 -->
					<span>重传</span>
					<c:choose> 
					<c:when test="${myMFM.manPrivilege.updatePVL==1}"  >
						<input type="checkbox" checked="checked" disabled="disabled" >
					</c:when>
					<c:otherwise>
						<input type="checkbox" disabled="disabled" >
					</c:otherwise>
					</c:choose>
					<!-- 授权权限 -->
					<span>授权</span>
					<c:choose> 
					<c:when test="${myMFM.manPrivilege.grantPVL==1}"  >
						<input type="checkbox" checked="checked" disabled="disabled" >
					</c:when>
					<c:otherwise>
						<input type="checkbox" disabled="disabled" >
					</c:otherwise>
					</c:choose>
					<!-- 全文件操作权限 -->
					<span>全文件操作</span>
					<c:choose> 
					<c:when test="${myMFM.manPrivilege.allFilesPVL==1}"  >
						<input type="checkbox" checked="checked" disabled="disabled" >
					</c:when>
					<c:otherwise>
						<input type="checkbox" disabled="disabled" >
					</c:otherwise>
					</c:choose>   	 	
					</td>
					<td>
						<a class="btn btn-warning">权限修改</a>
						<c:choose>
							<c:when test="${myMFM.isActivited==0}">
								<a class="btn btn-success">激活</a>
							</c:when>
							<c:when test="${myMFM.manPrivilege.mainPVL==1}">
							</c:when>
							<c:otherwise>
								<a class="btn btn-warning">注销</a>
							</c:otherwise> 
						</c:choose> 
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>