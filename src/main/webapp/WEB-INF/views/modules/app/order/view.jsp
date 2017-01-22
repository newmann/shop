<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="pageId" value="app-order-view" scope="request"/>
<c:set var="pageTitle" value="订单详情" scope="request"/>
<c:set var="showBackButton" value="true" scope="request"/>
<!DOCTYPE html>
<html>
<head>
	<title>查看订单详情</title>
	<meta name="decorator" content="app_default_nofooter"/>
</head>
<body>

<div id="nologin-tip" style="display: none">
	<i></i>您还没有登录！登录查看我的积分 <a href="#none" class="btn-red-1">立即登录</a>
</div>
<div id="order-view">
	<div class="step step-complete" id="step-1">
		<div class="title">
			<strong>收货人信息</strong>
		</div>
		<div class="content">
			<div id="address-t-title">
				<div id="address-t-title-1">${order.addressFullname}&nbsp;&nbsp;${order.addressTelephone}</div>
				<c:set var="areaTitle" value='${fn:replace(order.areaPathNames, "中国/广东省/", "")}'/>
				<c:set var="areaTitle" value='${fn:replace(areaTitle, "/", " ")}'/>
				<div id="address-t-title-2">${areaTitle} ${order.addressDetail}</div>
			</div>
		</div>
	</div>

	<div class="step" id="step-2">
		<div class="title">
			<strong>支付及配送方式</strong>
		</div>
		<div class="content">货到付款，立即送餐。</div>
	</div>

	<div class="step" id="step-3">
		<div class="title">
			<strong>商品清单</strong>
		</div>
		<ul data-role="listview" data-inset="true" class="order-list">
			<c:forEach items="${orderItemList}" var="item" varStatus="status">
				<li>
					<img src="<c:url value="${item.imageSmall}"/>">
					<h2>${item.name}</h2>

					<%-- product attributes and price --%>
					<c:set var="_price" value="${item.price}" />
					<c:if test="${not empty item.attributeList}">
					<p>
						</c:if>
						<c:forEach items="${item.attributeList}" var="attribute" varStatus="status">
							${attribute.attributeItem.name}:${attribute.attributeItemValue.name}<c:if test="${not status.last}">, </c:if>
						</c:forEach>
						<c:if test="${not empty item.attributeList}">
					</p>
					</c:if>
					<p>￥${item.price} x ${item.count}</p>
				</li>
			</c:forEach>
		</ul>
		<h2>总计: ￥${order.totalPrice}</h2>

		<div class="footer-btn-w">
			<div data-role="footer" data-position="fixed">
				<div class="footer-buttons">
					<a style="font-weight: normal" data-role="button" data-icon="home" data-transition="none" href="<c:url value='/app.html'/>">首页</a>&nbsp;&nbsp;&nbsp;
					<a style="font-weight: normal" data-role="button" data-icon="gear" data-transition="none" href="<c:url value='/app/category.html'/>">继续购物</a>
				</div>
			</div>
		</div>
	</div>
</div>

</body>
</html>
