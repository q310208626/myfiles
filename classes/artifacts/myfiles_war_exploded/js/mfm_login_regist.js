/**
 * 管理员登录注册界面js
 */
var app = angular.module('myApp', []);

//记录是否正确，以便登录时进行判断
//登录用户名是否正确
var isLoginACCRight=false;
//登录密码是否正确
var isLoginPWDRight=false;

//登录输入监听
app.controller('loginValidate',function($scope){
	//账户名匹配，字母开头，8到15位长度
	var accountPattern=/^\w[a-zA-Z0-9]{7,14}/;
	//密码匹配，以下字符8-15位长度
	var passwordPattern=/[^a-zA-Z0-9!@#$%&_+]/;
	$scope.$watch('account',function(newValue ,oldValue){
		//输入发生变化
		if(oldValue!=newValue){
			if(newValue==""){
				$scope.warn_login_account="用户名不能为空";
				isLoginACCRight=false;
			}else if(!accountPattern.test(newValue)){
				$scope.warn_login_account="用户名格式错误,字母开头,8-15为数字字母组合";
				isLoginACCRight=false;
			}else{
				$scope.warn_login_account="";
				isLoginACCRight=true;
			}
		}
	});
	
	$scope.$watch('password',function(newValue ,oldValue){
		//输入发生变化
		if(oldValue!=newValue){
			if(newValue==""){
				$scope.warn_login_password="密码不能为空";
				isLoginPWDRight=false;
			}else if(passwordPattern.test(newValue)){
				$scope.warn_login_password="包含非法字符";
				isLoginPWDRight=false;
			}else if(newValue.length<8||newValue.length>15){
				$scope.warn_login_password="长度为8-15个字符";
				isLoginPWDRight=false;
			}else{
				$scope.warn_login_password="";
				isLoginPWDRight=true;
			}
		}
	});
});


//记录是否正确，以便注册时进行判断
//注册用户名是否正确
var isRegistACCRight=false;
//注册密码是否正确
var isRegistPWDRight=false;
//注册确认密码是否正确
var isRegistConfirmPWDRight=false;

//注册输入监听
app.controller('registValidate',function($scope){
    //账户名匹配，字母开头，8到15位长度
	var accountPattern=/^\w[a-zA-Z0-9]{7,14}/;
    //密码匹配，以下字符8-15位长度
	var passwordPattern=/[^a-zA-Z0-9!@#$%&_+]/;
	
	$scope.$watch('account',function(newValue ,oldValue){
		//输入发生变化
		if(oldValue!=newValue){
			if(newValue==""){
				$scope.warn_regist_account="用户名不能为空";
				isRegistACCRight=false;
			}else if(!accountPattern.test(newValue)){
				$scope.warn_regist_account="用户名格式错误,字母开头,8-15为数字字母组合";
				isRegistACCRight=false;
			}else{
				$scope.warn_regist_account="";
				isRegistACCRight=true;
			}
		}
	});
	
	$scope.$watch('password',function(newValue ,oldValue){
		//输入发生变化
		if(oldValue!=newValue){
			if(newValue==""){
				$scope.warn_regist_password="密码不能为空";
				isRegistPWDRight=false;
			}else if(passwordPattern.test(newValue)){
				$scope.warn_regist_password="包含非法字符";
				isRegistPWDRight=false;
			}else if(newValue.length<8||newValue.length>15){
				$scope.warn_regist_password="长度为8-15个字符";
				isRegistPWDRight=false;
			}else{
				$scope.warn_regist_password="";
				isRegistPWDRight=true;
			}
		}
	});
	
	$scope.$watch('confirmPassword',function(newValue ,oldValue){
		//输入发生变化
		if(oldValue!=newValue){
			if(newValue==""){
				$scope.warn_regist_confirmpassword="密码不能为空";
				isRegistConfirmPWDRight=false;
			}else if(passwordPattern.test(newValue)){
				$scope.warn_regist_confirmpassword="包含非法字符";
				isRegistConfirmPWDRight=false;
			}else if(newValue.length<8||newValue.length>15){
				$scope.warn_regist_confirmpassword="长度为8-15个字符";
				isRegistConfirmPWDRight=false;
			}else if(newValue!=$scope.password){
				$scope.warn_regist_confirmpassword="两次密码不一致";
				isRegistConfirmPWDRight=false;
			}else{
				$scope.warn_regist_confirmpassword="";
				isRegistConfirmPWDRight=true;
			}
		}
	});
});

//判断是否可以登录
function canLogin(){
	return isLoginACCRight&&isLoginPWDRight;
}

//判断是否可以注册
function canRegist(){
	return isRegistACCRight&&isRegistPWDRight&&isRegistConfirmPWDRight;
}
