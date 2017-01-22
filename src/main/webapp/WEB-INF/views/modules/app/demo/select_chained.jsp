<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="pageId" value="app-demo-select-chained" scope="request"/>
<c:set var="pageTitle" value="测试级联选择" scope="request"/>
<!DOCTYPE html>
<head>
	<title>测试级联选择</title>
	<meta name="decorator" content="app_default_nofooter"/>
</head>
<body>
<style type="text/css">
	.ui-select {display:inline-block}
</style>
<script src="<c:url value='/static/app/js/jquery.chained/jquery.chained.remote.min.js'/>"></script>
<select id="province" name="province" >
	<option value="">--</option>
	<c:forEach items="${provinceList}" var="province">
		<option value="${province.id}">${province.name}</option>
	</c:forEach>
</select>
<select id="series" name="series"  disabled="disabled">
</select>
<select id="model" name="model"  disabled="disabled">
</select>
<select id="engine" name="engine"  disabled="disabled">
</select>

<script>
	$(function() {
		$("#series").remoteChained({
			parents : "#province",
			url : "/app/demo/ajax-select-chained.html"
		});
		$("#model").remoteChained({
			parents : "#series",
			url : "/app/demo/ajax-select-chained.html"
		});
		$("#engine").remoteChained({
			parents : "#series, #model",
			url : "/app/demo/ajax-select-chained.html"
		});
	});
</script>

</body>
</html>
