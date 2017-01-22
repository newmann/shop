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

	<div data-role="page" data-dom-cache="true" id="${pageId}">
		<%@ include file="header.jsp"%>

		<div data-role="content" class="ui-content" id="content">
			<sitemesh:body/>
		</div>

		<div data-role="footer" data-position="fixed" data-tap-toggle="false">
			<div data-role="navbar">
				<ul>
					<li><a style="font-weight: normal" data-icon="home" data-transition="none" href="<c:url value='/app.html'/>" class="footer-ajax <c:if test="${pageId eq 'app-index-index'}">ui-btn-active ui-state-persist</c:if>">首页</a></li>
					<li><a style="font-weight: normal" data-icon="gear" data-transition="none" href="<c:url value='/app/category.html'/>" class="footer-ajax <c:if test="${pageId eq 'app-category-index'}">ui-btn-active ui-state-persist</c:if>">品类</a></li>
					<li><a style="font-weight: normal" data-icon="cart" data-transition="none" href="<c:url value='/app/cart.html'/>" class="footer-ajax <c:if test="${pageId eq 'app-cart-index'}">ui-btn-active ui-state-persist</c:if>">购物车</a></li>
					<li><a style="font-weight: normal" data-icon="star" data-transition="none" href="<c:url value='/app/topic.html'/>" class="footer-ajax <c:if test="${pageId eq 'app-topic-index'}">ui-btn-active ui-state-persist</c:if>">乐呵呵</a></li>
					<li><a style="font-weight: normal" data-icon="info" data-transition="none" href="<c:url value='/app/info.html'/>" class="footer-ajax <c:if test="${pageId eq 'app-info-index'}">ui-btn-active ui-state-persist</c:if>">我的</a></li>
				</ul>
			</div>
		</div>
	</div>

	<%@ include file="script.jsp"%>

</body>
</html>