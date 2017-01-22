<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="pageId" value="product-list-page" scope="request"/>
<!DOCTYPE html>
<html>
<head>
  <title>首页</title>
  <meta name="decorator" content="shop_default"/>
  <meta name="description" content="月光茶人" />
  <meta name="keywords" content="月光茶人" />
</head>
<body>

<!-- left -->
<div class="main-left">
  <ul class="item-list">
    <c:forEach items="${firstCategoryList}" var="category">
      <li class="item <c:if test="${category.id == categoryId}">active</c:if>"><i></i><a href="<c:url value='/product/list/${category.id}.html'/>">${category.name}</a></li>
    </c:forEach>
  </ul>
</div>

<!-- content -->
<div class="main-content clearfix">
  <div class="product-list clearfix">
    <c:forEach items="${productList}" var="product">
      <div class="item">
        <a href="<c:url value='/product/${product.id}.html'/>" title="${product.name}">
          <img width="250px" height="200px" alt="${product.name}" src="<c:url value='${product.image}'/>"/>
        </a>
        <h3 class="title">
          <a title="${product.name}" href="<c:url value='/product/list/${product.id}.html'/>">
            <span>${product.name}</span>
          </a>
        </h3>
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

</body>
</html>