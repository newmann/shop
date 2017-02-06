<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="pageId" value="home-page" scope="request"/>
<!DOCTYPE html>
<html>
<head>
	<title>首页</title>
	<meta name="decorator" content="shop_default"/>
	<meta name="description" content="月光茶人" />
	<meta name="keywords" content="月光茶人" />
</head>
<body>

<%--<img src="/static/images/logo.jpg">--%>


	<!-- content -->
	<div id="" class="main-content clearfix">
		<div class="home-category-list clearfix">
			<c:forEach items="${firstCategoryList}" var="category">
				<div class="item">
					<a class="" href="<c:url value='/product/list/${category.id}.jsp'/>">
						<img width="314px" height="200px" src="<c:url value='${category.image}'/>" alt="${category.name}"/>
					</a>
					<h3 class="title">
						<a title="${category.name}" href="<c:url value='/product/list/${category.id}.jsp'/>">
							<span>${category.name}</span>
						</a>
					</h3>
				</div>
			</c:forEach>
		</div>
	</div>
	<!-- /content -->

	<!-- right -->
	<div class="main-right clearfix">
		<div class="side-block">
			<div class="title">
				<h2><i></i>每日优惠</h2>
			</div>
			<c:forEach items="${featuredHomeDayProductList}" var="product">
				<div class="content">
					<a target="_blank" href="<c:url value='/product/${product.id}.jsp'/>" rel="nofollow"><img width="206px" height="206px" src="<c:url value='${product.image}'/>" alt="${product.name}"/></a>
					<div><a target="_blank" href="<c:url value='/product/${product.id}.jsp'/>">${product.name}</a></div>
					<div class="price-box">
						<c:choose>
							<c:when test="${not empty product.featuredPrice}">
								<span class="price">￥${product.featuredPrice}</span>
								<span class="original-word">原价&nbsp;</span><span class="original-price">￥${product.price}</span>
							</c:when>
							<c:otherwise>
								<span class="price">￥${product.price}</span>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</c:forEach>
		</div>
		<br />
		<div class="side-block">
			<div class="title">
				<h2><i></i>每日特卖</h2>
			</div>
			<c:forEach items="${featuredHomeDayProductList}" var="product">
				<div class="content">
					<a target="_blank" href="<c:url value='/product/${product.id}.jsp'/>" rel="nofollow"><img width="206px" height="206px" src="<c:url value='${product.image}'/>" alt="${product.name}"/></a>
					<div><a target="_blank" href="<c:url value='/product/${product.id}.jsp'/>">${product.name}</a></div>
					<div class="price-box">
						<c:choose>
							<c:when test="${not empty product.featuredPrice}">
								<span class="price">￥${product.featuredPrice}</span>
								<span class="original-word">原价&nbsp;</span><span class="original-price">￥${product.price}</span>
							</c:when>
							<c:otherwise>
								<span class="price">￥${product.price}</span>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<!-- /right -->


</body>
</html>