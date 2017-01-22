<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/version.jsp"%>

<%
	/*
	String _serverName = request.getServerName();
	String _serverNamePrefix = _serverName.substring(0, 8);
	Integer _serverPort = request.getServerPort();
	String _contextPath = request.getContextPath();
	String _scheme = request.getScheme();
	String _baseUrl;
	String _cdnUrl;

	//development env
	if (_serverNamePrefix.equals("localhos") || _serverNamePrefix.equals("192.168.") || _serverNamePrefix.equals("10.0.2.2")) {
		_baseUrl = _scheme + "://" + _serverName + ":" + _serverPort + _contextPath;
		_cdnUrl = _baseUrl;
	}
	//product env
	else {
		_baseUrl = _scheme + "://" + _serverName;
		_cdnUrl = _baseUrl;
	}
	*/

	String _baseUrl = request.getContextPath();
	String _cdnUrl = _baseUrl;
%>

<!DOCTYPE html>
<html>
<head>
	<title><sitemesh:title default="欢迎您"/> - 月光茶人</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<sitemesh:head/>
	<link type="text/css" rel="stylesheet" href="<c:url value='/static/css/app/goods.css?v=${cssVersion}'/>" />
	<script type="text/javascript">
		// global object
		iwc = {};
		iwc.baseUrl = "<%=_baseUrl%>";
		iwc.cdnUrl = "<%=_cdnUrl%>";
	</script>
</head>
<body class="viewport" id="${pageId}">

<sitemesh:body/>

</body>
</html>