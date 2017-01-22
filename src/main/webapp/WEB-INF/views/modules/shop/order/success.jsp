<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:set var="pageId" value="cart-success-page" scope="request"/>
<!DOCTYPE html>
<html>
<head>
    <title>查看订单</title>
    <meta name="decorator" content="shop_default"/>
    <meta name="description" content="月光茶人"/>
    <meta name="keywords" content="月光茶人"/>
</head>
<body>

<!-- content -->
<div class="main-content clearfix">
	<div id="nologin-tip">
		<i></i>您还没有登录！登录后订单将保存到您账号中，方便以后查看！ <a href="#none" class="btn-red-1">立即登录</a>
	</div>
	<div id="cart-success">
        <h1>订单已提交！ 货到付款，立即送餐。</h1>
        <div>请耐心等待。</div>
        <div><a target="_blank" href="<c:url value='/order/${order.id}/${order.cookie.id}.html'/>">查看订单</a></div>
		<div class="go-to-home"><a target="_blank" href="<c:url value='/'/>">继续购物</a></div>
	</div>
	<div>
		<div>美食推荐</div>
	</div>
</div>

<!-- right -->
<div class="main-right">
	<div id="side-cart">
		<div class="title">
			<h2><i></i>其他（她）人还购买</h2>
		</div>
		<div class="content">
			<c:set var="totalCount" value="0" />
			<c:set var="totalPrice" value="0" />
	    	<c:forEach items="${cartItemList}" var="item" varStatus="status">
		    	<dl>
			        <dt><a href="<c:url value='/product/${item.product.id}.html'/>"><img alt="${item.product.name}" src="<c:url value='${item.product.imageSmall}'/>"></a></dt>
			        <dd>
			            <div><a href="<c:url value='/product/${item.product.id}.html'/>">${item.product.name}</a></div>
			            <div class="price"><span>${item.product.price}</span><em>×${item.count}</em></div>
			        </dd>
			    </dl>
			    <c:set var="totalCount" value="${item.count + totalCount}" />
			    <c:set var="totalPrice" value="${(item.count * item.product.price) + totalPrice}" />
	        </c:forEach>
			<div class="total"> 
				共<strong class="count">${totalCount}</strong>件商品<br>
				金额总计：<strong>${totalPrice}</strong>
			</div>
			<div class="btns"><a class="btn-red" href="<c:url value='/cart.html'/>">去购物车结算</a></div>    
		</div>
	</div>
</div>
	   
</body>
</html>
