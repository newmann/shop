<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="pageId" value="app-index-index" scope="request"/>
<!DOCTYPE html>
<html>
<head>
    <title>首页</title>
    <meta name="decorator" content="app_default"/>
    <meta name="description" content="月光茶人" />
    <meta name="keywords" content="月光茶人" />
</head>
<body>

<div class="floor activity-floor">
    <h2 class="activity-floor-title">热门分类</h2>
    <ul class="activity-list category-list clearfix">
        <c:forEach items="${featuredCategoryList}" var="category">
            <li class="activity-item">
                <a class="activity-link" href="<c:url value='/app/product/list/${category.id}.html'/>">
                    <div class="left-b"><img src="<c:url value="${category.imageSmall}"/>"></div>
                    <div class="right-b">
                        <div class="name">${category.name}</div>
                    </div>
                </a>
            </li>
        </c:forEach>
    </ul>
    <div class="activity-clear"></div>
</div>

<div class="floor activity-floor">
    <h2 class="activity-floor-title">热卖单品</h2>
    <ul class="activity-list product-list clearfix">
        <c:forEach items="${featuredProductList}" var="product">
            <li class="activity-item">
                <a class="activity-link" href="<c:url value='/app/product/${product.id}.html'/>">
                    <div class="left-b"><img src="<c:url value="${product.imageSmall}"/>"></div>
                    <div class="right-b">
                        <div class="name">${product.name}</div>
                        <div class="desc">${product.shortDescription}</div>
                    </div>
                </a>
            </li>
        </c:forEach>
    </ul>
    <div class="activity-clear"></div>
</div>

</body>
</html>