<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="pageId" value="cart-success-page" scope="request"/>
<!DOCTYPE html>
<html>
<head>
	<title>商品已成功加入购物车</title>
	<meta name="decorator" content="shop_default"/>
	<meta name="description" content="月光茶人" />
	<meta name="keywords" content="月光茶人" />
</head>
<body>

<!-- content -->
<div class="main-content clearfix">
	<div id="cart-success">
        <h1>商品已成功加入购物车！</h1>
		<span class="go-to-cart"><a class="btn-red" href="<c:url value='/cart.html'/>">去购物车结算</a></span>
		<span class="go-to-shopping">您还可以 <a href="javascript:history.back();">继续购物</a></span>
	</div>
</div>

<!-- right -->
<div class="main-right">
	<div id="side-cart">
		<div class="title">
			<h2><i></i>我的购物车</h2>
		</div>
		<div class="content">
			<c:set var="_totalCount" value="0" />
			<c:set var="_totalPrice" value="0" />
	    	<c:forEach items="${cartItemList}" var="item" varStatus="status">
		    	<dl>
			        <dt><a href="<c:url value='/product/${item.product.id}.html'/>"><img alt="${item.product.name}" src="<c:url value='${item.product.imageSmall}'/>"></a></dt>
			        <dd>
			            <div><a href="<c:url value='/product/${item.product.id}.html'/>">${item.product.name}</a></div>
			            <div class="price-box">
							<c:choose>
							<c:when test="${not empty item.product.featuredPrice}">
								<span class="price">${item.product.featuredPrice}</span>
								<span class="original-word">原价&nbsp;</span><span class="original-price">${item.product.price}</span>
							</c:when>
							<c:otherwise>
								<span class="price">${item.product.price}</span>
							</c:otherwise>
							</c:choose>
						</div>
			        </dd>
			    </dl>
			    <c:set var="_totalCount" value="${item.count + _totalCount}" />
			    <c:set var="_totalPrice" value="${(item.count * item.product.price) + _totalPrice}" />
	        </c:forEach>
			<div class="total"> 
				共<strong class="count">${_totalCount}</strong>件商品<br>
				金额总计：<strong>${_totalPrice}</strong>
			</div>
			<div class="btns"><a class="btn-red" href="<c:url value='/cart.html'/>">去购物车结算</a></div>    
		</div>
	</div>
</div>

</body>
</html>
