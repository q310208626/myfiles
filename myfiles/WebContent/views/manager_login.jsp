<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Manager Login</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/myfiles.css">
</head>
<body>
	<div class="container-narrow" ng-app="myApp">
		<div class="masthead">
			<ul class="nav nav-pills pull-right">
				<li><a href="to_index.do">主页</a></li>
				<li class="active"><a>管理员登录</a></li>
			</ul>
			<h3 class="muted">管理员登录</h3>
		</div>
		<hr>
		<!-- 分割线 -->
		<!-- tab内容 -->
		<ul class="nav nav-tabs">
			<li class="active"><a href="#login_div" data-toggle="tab">登录</a></li>
			<li><a href="#regist_div" data-toggle="tab">注册</a></li>
		</ul>
		<div class="tab-content">
			<!-- 登录/注册tab -->
			<!-- 登录 -->
			<div class="tab-pane fade in active" id="login_div">
				<form class="form-signin" action="${pageContext.request.contextPath}/MFM/login.do" onsubmit="return canLogin()" ng-controller="loginValidate" >
					<h3 class="muted">登录</h3>
					<input name="account" type="text" placeholder="请输入管理员账户" class="sign_input" ng-model="account"><br>
					<span class="validate_warn">{{warn_login_account}}</span> 
					<input name="password" type="password" placeholder="请输入密码" class="sign_input" ng-model="password"><br>
					<span class="validate_warn">{{warn_login_password}}</span>
					<input type="submit" value="登录" class="sign_input sign_button">
				</form>
			</div>
			<!-- 注册 -->
			<div class="tab-pane fade" id="regist_div">
				<form class="form-signin" action="" onsubmit="return canRegist()" ng-controller="registValidate">
					<h3 class="muted">注册</h3>
					<input type="text" placeholder="请输入管理员账户" class="sign_input" ng-model="account">
					<span class="validate_warn">{{warn_regist_account}}</span> 
					<input type="password" placeholder="请输入密码" class="sign_input" ng-model="password">
					<span class="validate_warn">{{warn_regist_password}}</span>
					<input type="password" placeholder="请再次确认密码" class="sign_input" ng-model="confirmPassword">
					<span class="validate_warn">{{warn_regist_confirmpassword}}</span>
					<input type="button" value="注册" class="sign_input sign_button">
				</form>
			</div>

		</div>
	</div>
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/angular.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/mfm_login_regist.js"></script>
</html>