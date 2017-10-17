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
	<div style="height: 500px" >
		<table class="table table-striped">
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
		<tbody>
			<c:forEach var="myFile" items="${MyFileList}">
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
			</c:forEach>
		</tbody>
	</table>
	</div>
	<ul id="bp-element" class="pagination"></ul>
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap-paginator.js"></script>	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/manager_files.js"></script>
<script type="text/javascript">
$(function(){
	pageinit();
});

function pageinit(){
    var element = $('#bp-element');
    options = {
    	alignment:"center", //居中显示
        bootstrapMajorVersion:3, //对应的bootstrap版本
        currentPage:"${currentPage}", //当前页数，这里是用的EL表达式，获取从后台传过来的值
        numberOfPages:10, //每页页数
        totalPages:"${fileCount}", //总页数，这里是用的EL表达式，获取从后台传过来的值
        shouldShowPage:true,//是否显示该按钮
        itemTexts: function (type, page, current) {//设置显示的样式，默认是箭头
            switch (type) {
                case "first":
                    return "首页";
                case "prev":
                    return "上一页";
                case "next":
                    return "下一页";
                case "last":
                    return "末页";
                case "page":
                    return page;
            }
        },
        //点击事件
        onPageClicked: function (event, originalEvent, type, page) {
            location.href = "/myfiles/myFile/getCustomerFileByPage.do?page=" + page+"&pageCount="+10;
        }
    };
    element.bootstrapPaginator(options);
}
</script>
</html>