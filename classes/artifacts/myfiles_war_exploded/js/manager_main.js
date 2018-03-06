/**
 * 管理主页面js
 */

$(document).ready(function(){
	$('.nav-li').click(function(){
		$(this).addClass("active").siblings().removeClass("active");
	});
});
	
		
