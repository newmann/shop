<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/version.jsp"%>

<%
	String _baseUrl = request.getContextPath();
	String _cdnUrl = _baseUrl;
%>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="head.jsp"%>
</head>
<body>
	<div data-role="page" id="${pageId}">
		<%@ include file="header.jsp"%>

		<div class="ui-content" role="main">
			<sitemesh:body/>
		</div>
	</div>

	<%@ include file="script.jsp"%>
</body>
</html>