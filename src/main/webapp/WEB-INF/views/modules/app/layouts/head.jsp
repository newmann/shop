<%@page pageEncoding="UTF-8"%>

<%--jsp 静态态引入 <%@ include file="head.jsp"%> --%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<meta charset="utf-8" />
<meta name="format-detection" content="telephone=no" />
<!-- WARNING: for iOS 7, remove the width=device-width and height=device-height attributes. See https://issues.apache.org/jira/browse/CB-4323 -->
<meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, height=device-height, target-densitydpi=device-dpi" />
<title><sitemesh:title default="欢迎您"/> - 月光茶人</title>
<link rel="shortcut icon" href="<c:url value='/static/app/favicon.ico'/>">
<link rel="stylesheet" href="<c:url value='/static/app/js/jquery.mobile/jquery.mobile-1.4.5.min.css'/>" />
<link rel="stylesheet" href="<c:url value='/static/app/css/main.css?v=${cssVersion}'/>" />
<link rel="stylesheet" href="<c:url value='/static/app/css/cart.css?v=${cssVersion}'/>" />

<script src="<c:url value='/static/app/js/jquery/jquery-1.10.2.min.js'/>"></script>
<script>
    $(document).bind("mobileinit", function() {
        //不支持3D转屏的设备禁止转屏效果
        $.mobile.transitionFallbacks.slide = "none";
        //禁止hover延迟
        $.mobile.buttonMarkup.hoverDelay = "false";
    });
</script>
<script src="<c:url value='/static/app/js/jquery.mobile/jquery.mobile-1.4.5.min.js'/>"></script>
<sitemesh:head/>